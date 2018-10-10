package cn.youngh.antelope.content.service;

import cn.youngh.antelope.common.vo.E3Result;
import cn.youngh.antelope.common.vo.EasyUITreeNode;

import java.util.List;

public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCatList(Long parentId);
    E3Result addContentCategor(Long parentId,String name);
    E3Result deleteContentCategor(Long contentCategoryId);
    E3Result updateContentCategor(Long contentCategoryId,String name);
}
