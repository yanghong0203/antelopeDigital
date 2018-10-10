package cn.youngh.antelope.service;

import cn.youngh.antelope.common.vo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(Long parentId);
}
