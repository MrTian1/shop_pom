package com.po.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.po.entity.Goods;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements  ISearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public List<Goods> queryByKeyWord(String keyword) {
        SolrQuery solrQuery;
    if (keyword ==null || keyword.trim().equals("")){
        solrQuery=new SolrQuery("*:*");
    }else {
        solrQuery=new SolrQuery("gname:"+keyword +"|| ginfo:"+keyword);
    }
    //设置高亮
        solrQuery.setHighlight(true);//开启高亮
        solrQuery.setHighlightSimplePre("<font color='red'>");//设置前缀
        solrQuery.setHighlightSimplePost("</font>");//设置后缀
        solrQuery.addHighlightField("gname");
        //solrQuery.addHighlightField("ginfo"); add--（追加）

        //高亮的折叠
        solrQuery.setHighlightFragsize(5);//这是设置每次显示的长度
        solrQuery.setHighlightSnippets(3);//这是设置高亮折叠的次数

       /* //设置分页
        solrQuery.setStart((page-1)*pageSize);
        solrQuery.setRows(pageSize);
*/
    //执行查询
        List<Goods> goodsList =new ArrayList<>();
        try {
            QueryResponse query = solrClient.query(solrQuery);
            //通过 QueryResponse对象获取普通的搜索结果
            SolrDocumentList results = query.getResults();
            //通过 QueryResponse对象获取高亮的搜索结果
            //{"1","gname",["华为<font>手机</font>"]}
            //一个关键字搜索出来的结果，是否一定存在高亮？ ————不一定
            //为什么高亮的结果是一个list集合？
            Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();



      for (SolrDocument result : results) {
                Goods goods =new Goods();
               goods.setId(Integer.parseInt(result.get("id")+""));
               goods.setGname(result.get("gname") +"");

               BigDecimal bigDecimal =BigDecimal.valueOf((float) result.get("gprice"));
               goods.setGprice(bigDecimal);
               goods.setGimages(result.get("gimages")+"");
               goods.setGsave(Integer.parseInt(result.get("gsave")+""));

               //判断当前的商品是否存在高亮
                if(highlighting.containsKey(goods.getId()+"")){
                    //当前商品存在高亮
                    Map<String, List<String>> stringListMap = highlighting.get(goods.getId() + "");
                    //获取高亮的字段
                    List<String> gname=stringListMap.get("gname");
                    if (gname !=null){
                        goods.setGname(gname.get(0));
                    }

                }
               goodsList.add(goods);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goodsList;
    }

    /**
     * 将商品数据保存到索引库中
     * @param goods
     * @return
     */
    @Override
    public int addGoods(Goods goods) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", goods.getId() + "");
        document.addField("gname", goods.getGname());
        document.addField("ginfo", goods.getGinfo());
        document.addField("gprice", goods.getGprice().floatValue());
        document.addField("gsave", goods.getGsave());
        document.addField("gimages", goods.getGimages());

        try {
            solrClient.add(document);
            solrClient.commit();
            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
