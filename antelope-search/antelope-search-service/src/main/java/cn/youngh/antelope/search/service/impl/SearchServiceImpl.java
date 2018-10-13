package cn.youngh.antelope.search.service.impl;

import cn.youngh.antelope.common.vo.SearchResult;
import cn.youngh.antelope.search.dao.SearchDao;
import cn.youngh.antelope.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品搜索服务
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String keyword, int pageNum, int pageSize) throws Exception {
        SolrQuery query = new SolrQuery();
        query.setQuery(keyword);
        if (pageNum <=0){
            pageNum = 1;
        }
        query.setStart((pageNum-1)*pageSize);
        query.setRows(pageSize);
        query.set("df","item_title");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<span style='color:red'>");
        query.setHighlightSimplePost("</span>");
        SearchResult searchResult = searchDao.searchResult(query);
        long recordCount = searchResult.getRecordCount();
        int totalPage =(int) recordCount/pageSize;
        if (recordCount%pageSize >0){
            totalPage++;
        }
        searchResult.setTotalPages(totalPage);
        return searchResult;
    }
}
