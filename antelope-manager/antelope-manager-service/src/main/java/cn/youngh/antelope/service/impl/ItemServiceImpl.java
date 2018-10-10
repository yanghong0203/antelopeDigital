package cn.youngh.antelope.service.impl;

import cn.youngh.antelope.common.utils.IDUtils;
import cn.youngh.antelope.common.vo.E3Result;
import cn.youngh.antelope.common.vo.EasyUIDataGridResult;
import cn.youngh.antelope.dao.TbItemDescMapper;
import cn.youngh.antelope.dao.TbItemMapper;
import cn.youngh.antelope.pojo.TbItem;
import cn.youngh.antelope.pojo.TbItemDesc;
import cn.youngh.antelope.pojo.TbItemExample;
import cn.youngh.antelope.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("itemServiceImpl")
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Override
    public E3Result getItemById(Long itemId) {
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        return E3Result.ok(item);
    }

    @Override
    public EasyUIDataGridResult getItemList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        TbItemExample tbItemExample =  new TbItemExample();
        List<TbItem> itemList = itemMapper.selectByExample(tbItemExample);
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        PageInfo pageInfo = new PageInfo(itemList);
        easyUIDataGridResult.setRows(itemList);
        easyUIDataGridResult.setTotal(pageInfo.getTotal());
        return easyUIDataGridResult;
    }

    @Override
    public E3Result saveItem(TbItem item,String desc) {
        long id = IDUtils.genItemId();
        Date date = new Date();
        item.setId(id);
        //商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte)1);
        itemMapper.insert(item);
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        itemDescMapper.insert(itemDesc);
        return E3Result.ok();
    }

    @Override
    public E3Result updateItem(TbItem item, String desc) {
        item.setUpdated(new Date());
        itemMapper.updateByPrimaryKeySelective(item);

        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(new Date());
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);
        return E3Result.ok();
    }

    /**
     * 删除商品
     * 商品状态，1-正常，2-下架，3-删除
     * @param itemId
     * @return
     */
    @Override
    public E3Result deleteItem(Long itemId) {
        itemMapper.updateStatusByItemId(itemId,3);
        return E3Result.ok();
    }

    /**
     * 商品下架
     * 商品状态，1-正常，2-下架，3-删除
     * @param itemId
     * @return
     */
    @Override
    public E3Result instockItem(Long itemId) {
        itemMapper.updateStatusByItemId(itemId,2);
        return E3Result.ok();
    }

    /**
     * 商品上架
     * 商品状态，1-正常，2-下架，3-删除
     * @param itemId
     * @return
     */
    @Override
    public E3Result reshelfItem(Long itemId) {
        itemMapper.updateStatusByItemId(itemId,1);
        return E3Result.ok();
    }

    @Override
    public E3Result getItemDescById(Long itemId) {
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        return E3Result.ok(itemDesc);

    }

}
