package cn.youngh.antelope.search.service.impl;

import cn.youngh.antelope.common.vo.E3Result;
import cn.youngh.antelope.common.vo.SearchItem;
import cn.youngh.antelope.common.vo.SearchResult;
import cn.youngh.antelope.search.dao.SearchDao;
import cn.youngh.antelope.search.mapper.ItemMapper;
import cn.youngh.antelope.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  SearchItem SolrDocument里面的field
 *  id
 *  item_title
 *  item_sell_point
 *  item_price
 *  item_image
 *  item_category_name
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;


    @Override
    public E3Result importAllItem() {
        // 查询商品列表，返回所有商品
        List<SearchItem> itemList = itemMapper.getItemList();

        for (SearchItem searchItem : itemList) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            String image = searchItem.getImage().split(",")[0];
            document.addField("item_image", image);
            document.addField("item_category_name", searchItem.getCategory_name());
            try {
                solrServer.add(document);
            } catch (Exception e) {
                return E3Result.build(500, "数据写入异常");
            }
        }
        try {
            solrServer.commit();
        } catch (Exception e) {
            return E3Result.build(500, "数据提交异常");
        }
        return E3Result.ok();
    }
}