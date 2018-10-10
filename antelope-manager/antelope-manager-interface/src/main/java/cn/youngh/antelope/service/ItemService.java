package cn.youngh.antelope.service;

import cn.youngh.antelope.common.vo.E3Result;
import cn.youngh.antelope.common.vo.EasyUIDataGridResult;
import cn.youngh.antelope.pojo.TbItem;

public interface ItemService {
    E3Result getItemById(Long itemId);
    EasyUIDataGridResult getItemList(int pageNum,int pageSize);
    E3Result saveItem(TbItem item,String desc);
    E3Result updateItem(TbItem item,String desc);
    E3Result deleteItem(Long itemId);
    E3Result instockItem(Long itemId);
    E3Result reshelfItem(Long itemId);
    E3Result getItemDescById(Long itemId);

}
