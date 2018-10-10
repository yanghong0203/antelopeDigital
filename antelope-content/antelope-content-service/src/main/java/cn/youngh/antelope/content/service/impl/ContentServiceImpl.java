package cn.youngh.antelope.content.service.impl;

import cn.youngh.antelope.common.jedis.JedisClient;
import cn.youngh.antelope.common.jedis.JedisClientPool;
import cn.youngh.antelope.common.utils.JsonUtils;
import cn.youngh.antelope.common.vo.E3Result;
import cn.youngh.antelope.common.vo.EasyUIDataGridResult;
import cn.youngh.antelope.content.service.ContentService;
import cn.youngh.antelope.dao.TbContentMapper;
import cn.youngh.antelope.pojo.TbContent;
import cn.youngh.antelope.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Value("${index}")
    private String INDEX;

    @Value("${index.lunbo}")
    private String INDEX_LUNBO;

    @Autowired
    TbContentMapper contentMapper;

    @Autowired
    JedisClient jedisClient;
    @Override
    public EasyUIDataGridResult getContentList(Long contentCategoryId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCategoryId);
        List<TbContent> contentList = contentMapper.selectByExampleWithBLOBs(example);
        PageInfo pageInfo = new PageInfo(contentList);
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setTotal(pageInfo.getTotal());
        easyUIDataGridResult.setRows(contentList);
        return easyUIDataGridResult;
    }

    @Override
    public E3Result addContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
        jedisClient.hdel(INDEX,INDEX_LUNBO);
        return E3Result.ok();
    }

    @Override
    public List<TbContent> getContentListByContentCategoryId(Long contentCategoryId) {
        String contentListString = null;
        try {
            contentListString = jedisClient.hget(INDEX,INDEX_LUNBO);
        }catch (Exception e){
            System.out.println("未获取到redis缓存");
        }
        List<TbContent> contentList;
        if (StringUtils.isBlank(contentListString)){
            TbContentExample example = new TbContentExample();
            TbContentExample.Criteria criteria = example.createCriteria();
            criteria.andCategoryIdEqualTo(contentCategoryId);
            contentList = contentMapper.selectByExampleWithBLOBs(example);
            try {
                jedisClient.hset(INDEX,INDEX_LUNBO,JsonUtils.objectToJson(contentList));
            }catch (Exception e){
                System.out.println("添加缓存失败");
            }
        }else {
            contentList = JsonUtils.jsonToList(contentListString,TbContent.class);
        }
        return contentList;
    }

    @Override
    public E3Result updateContent(TbContent content) {
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKeySelective(content);
        jedisClient.hdel(INDEX,INDEX_LUNBO);
        return E3Result.ok();
    }

    @Override
    public E3Result deleteContent(Long contentId) {
        contentMapper.deleteByPrimaryKey(contentId);
        jedisClient.hdel(INDEX,INDEX_LUNBO);
        return E3Result.ok();
    }
}
