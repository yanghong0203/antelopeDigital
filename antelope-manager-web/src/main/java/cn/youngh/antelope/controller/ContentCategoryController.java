package cn.youngh.antelope.controller;

import cn.youngh.antelope.common.vo.E3Result;
import cn.youngh.antelope.common.vo.EasyUITreeNode;
import cn.youngh.antelope.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;
    @RequestMapping("content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(name = "id",defaultValue = "0") Long parentId){
        return contentCategoryService.getContentCatList(parentId);

    }
    @RequestMapping("content/category/create")
    @ResponseBody
    public E3Result createContentCategor(Long parentId, String name){
        return contentCategoryService.addContentCategor(parentId,name);

    }

    @RequestMapping("content/category/delete")
    @ResponseBody
    public E3Result deleteContentCategor(@RequestParam("id") Long contentCategorId){
        return contentCategoryService.deleteContentCategor(contentCategorId);
    }
    @RequestMapping("content/category/update")
    @ResponseBody
    public E3Result updateContentCategor(@RequestParam("id") Long contentCategorId,String name){
        return contentCategoryService.updateContentCategor(contentCategorId,name);
    }
}
