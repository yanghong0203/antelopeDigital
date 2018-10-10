package cn.youngh.antelope.content.service.impl;

import cn.youngh.antelope.common.vo.E3Result;
import cn.youngh.antelope.common.vo.EasyUITreeNode;
import cn.youngh.antelope.content.service.ContentCategoryService;
import cn.youngh.antelope.dao.TbContentCategoryMapper;
import cn.youngh.antelope.pojo.TbContentCategory;
import cn.youngh.antelope.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;
    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> contentCategoryList = contentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> easyUITreeNodeList = new ArrayList<>();
        for(TbContentCategory contentCategory : contentCategoryList){
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(contentCategory.getId());
            easyUITreeNode.setText(contentCategory.getName());
            easyUITreeNode.setState(contentCategory.getIsParent() ? "closed":"open");
            easyUITreeNodeList.add(easyUITreeNode);
        }
        return easyUITreeNodeList;
    }

    @Override
    public E3Result addContentCategor(Long parentId, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setIsParent(false);
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
        contentCategory.setSortOrder(1);
        //状态。可选值:1(正常),2(删除)
        contentCategory.setStatus(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        contentCategoryMapper.insert(contentCategory);
        // 更新父节点的isParent值为true
        TbContentCategory parentCategory = new TbContentCategory();
        parentCategory.setId(parentId);
        parentCategory.setIsParent(true);
        contentCategoryMapper.updateByPrimaryKeySelective(parentCategory);
        return E3Result.ok(contentCategory);
    }

    @Override
    public E3Result deleteContentCategor(Long contentCategoryId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(contentCategoryId);
        // 获取此节点的子节点
        List<TbContentCategory> contentCategoryList = contentCategoryMapper.selectByExample(example);
        // contentCategoryList 不为空证明有子节点
        if (contentCategoryList != null && contentCategoryList.size()!=0){
            // 遍历子节点，寻找子节点的子节点
            for (TbContentCategory contentCategory : contentCategoryList){
                deleteContentCategor(contentCategory.getId());
            }
            contentCategoryMapper.deleteByPrimaryKey(contentCategoryId);
        }else {
            // 删除节点
            contentCategoryMapper.deleteByPrimaryKey(contentCategoryId);
        }
        return E3Result.ok();
    }

    @Override
    public E3Result updateContentCategor(Long contentCategoryId, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(contentCategoryId);
        contentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        return E3Result.ok();
    }

}
