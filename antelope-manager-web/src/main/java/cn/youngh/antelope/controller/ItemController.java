package cn.youngh.antelope.controller;

import cn.youngh.antelope.common.vo.E3Result;
import cn.youngh.antelope.common.vo.EasyUIDataGridResult;
import cn.youngh.antelope.pojo.TbItem;
import cn.youngh.antelope.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows){
        EasyUIDataGridResult easyUIDataGridResult = itemService.getItemList(page, rows);
        return easyUIDataGridResult;
    }

    @RequestMapping("/item/save")
    @ResponseBody
    public E3Result saveItem(TbItem item,String desc){
        return itemService.saveItem(item,desc);
    }

    @RequestMapping("/rest/item/query/item/desc/{itemId}")
    @ResponseBody
    public E3Result getItemDescById(@PathVariable Long itemId){
        return itemService.getItemDescById(itemId);
    }
    @RequestMapping("/rest/item/param/item/query/{itemId}")
    @ResponseBody
    public E3Result getItemById(@PathVariable Long itemId){
       return itemService.getItemById(itemId);
    }
    @RequestMapping("/rest/item/update")
    @ResponseBody
    public E3Result updateItem(TbItem item,String desc){
        return itemService.updateItem(item,desc);
    }

    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public E3Result deleteItem(@RequestParam("ids") Long itemId){
        return itemService.deleteItem(itemId);
    }

    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public E3Result instockItem(@RequestParam("ids") Long itemId){
        return itemService.instockItem(itemId);
    }

    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public E3Result reshelfItem(@RequestParam("ids") Long itemId){
        return itemService.reshelfItem(itemId);
    }

}
