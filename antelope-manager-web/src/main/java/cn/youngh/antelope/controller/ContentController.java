package cn.youngh.antelope.controller;

import cn.youngh.antelope.common.vo.E3Result;
import cn.youngh.antelope.common.vo.EasyUIDataGridResult;
import cn.youngh.antelope.content.service.ContentService;
import cn.youngh.antelope.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContentController {
    @Autowired
    ContentService contentService;
    @RequestMapping("content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(@RequestParam("categoryId") Long contentCategoryId,
                                               @RequestParam("page") int pageNum,
                                               @RequestParam("rows") int pageSize){
        return contentService.getContentList(contentCategoryId,pageNum,pageSize);
    }
    @RequestMapping("rest/content/edit")
    @ResponseBody
    public E3Result updateContent(TbContent content){
        return contentService.updateContent(content);
    }
    @RequestMapping("content/save")
    @ResponseBody
    public E3Result saveContent(TbContent content){
        return contentService.addContent(content);
    }
    @RequestMapping("content/delete")
    @ResponseBody
    public E3Result deleteContent(@RequestParam("ids") Long contentId){
        return contentService.deleteContent(contentId);
    }

}
