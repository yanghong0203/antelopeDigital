package cn.youngh.antelope.content.service;

import cn.youngh.antelope.common.vo.E3Result;
import cn.youngh.antelope.common.vo.EasyUIDataGridResult;
import cn.youngh.antelope.pojo.TbContent;

import java.util.List;

public interface ContentService {
    EasyUIDataGridResult getContentList(Long contentCategoryId,int pageNum,int pageSize);
    E3Result addContent(TbContent content);
    List<TbContent> getContentListByContentCategoryId(Long contentCategoryId);
    E3Result updateContent(TbContent content);
    E3Result deleteContent(Long contentId);
}
