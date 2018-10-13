package cn.youngh.antelope.controller;

import cn.youngh.antelope.common.vo.E3Result;
import cn.youngh.antelope.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchItemController {
    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("index/item/import")
    @ResponseBody
    public E3Result importItemList(){
        return searchItemService.importAllItem();
    }

}
