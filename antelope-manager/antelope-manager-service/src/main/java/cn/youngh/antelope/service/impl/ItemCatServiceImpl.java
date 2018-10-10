package cn.youngh.antelope.service.impl;

import cn.youngh.antelope.common.vo.EasyUITreeNode;
import cn.youngh.antelope.dao.TbItemCatMapper;
import cn.youngh.antelope.pojo.TbItemCat;
import cn.youngh.antelope.pojo.TbItemCatExample;
import cn.youngh.antelope.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {
        TbItemCatExample itemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = itemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> itemCatList = itemCatMapper.selectByExample(itemCatExample);
        List<EasyUITreeNode> easyUITreeNodeList = new ArrayList<>();
        for(TbItemCat itemCat : itemCatList){
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(itemCat.getId());
            easyUITreeNode.setText(itemCat.getName());
            easyUITreeNode.setState(itemCat.getIsParent() == true ?  "closed" : "open");
            easyUITreeNodeList.add(easyUITreeNode);
        }
        return easyUITreeNodeList;
    }
}
