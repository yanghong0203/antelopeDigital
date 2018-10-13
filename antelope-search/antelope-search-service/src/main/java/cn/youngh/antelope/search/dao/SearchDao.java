package cn.youngh.antelope.search.dao;

import cn.youngh.antelope.common.vo.SearchItem;
import cn.youngh.antelope.common.vo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  SearchItem SolrDocument里面的field
 *  id
 *  item_title
 *  item_sell_point
 *  item_price
 *  item_image
 *  item_category_name
 */
@Component
public class SearchDao {


    @Autowired
    private SolrServer solrServer;
    public SearchResult searchResult(SolrQuery query) throws Exception {
        SearchResult searchResult = new SearchResult();
        QueryResponse response = solrServer.query(query);
        SolrDocumentList documentList = response.getResults();

        // 获取高亮
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        searchResult.setRecordCount((long)documentList.getNumFound());
        List<SearchItem> searchItemList = new ArrayList<>();
        for (SolrDocument document : documentList){
            SearchItem searchItem = new SearchItem();

            searchItem.setId((String)document.get("id"));
            Map<String, List<String>> map = highlighting.get((String) document.get("id"));
            List<String> item_title = map.get("item_title");
            if (item_title!=null && item_title.size()!=0){
                searchItem.setTitle(item_title.get(0));
            }else {
                searchItem.setTitle((String)document.get("item_title"));
            }
            searchItem.setSell_point((String)document.get("item_sell_point"));
            searchItem.setPrice((Long)document.get("item_price"));
            searchItem.setCategory_name((String)document.get("item_category_name"));
            searchItem.setImage((String)document.get("item_image"));
            searchItemList.add(searchItem);
        }
        searchResult.setItemList(searchItemList);
        return searchResult;
    }

}
