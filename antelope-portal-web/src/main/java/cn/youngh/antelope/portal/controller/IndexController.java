package cn.youngh.antelope.portal.controller;

import cn.youngh.antelope.content.service.ContentService;
import cn.youngh.antelope.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    @Value("${index.lunbo}")
    private Long indexLunbo;

    @Autowired
    ContentService contentService;

    @RequestMapping("/index.html")
    public String showIndex(Model model){
        List<TbContent> contentList = contentService.getContentListByContentCategoryId(indexLunbo);
        model.addAttribute("ad1List",contentList);
        return "index";
    }

}
