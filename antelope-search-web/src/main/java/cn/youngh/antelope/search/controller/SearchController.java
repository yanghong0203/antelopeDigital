package cn.youngh.antelope.search.controller;

import cn.youngh.antelope.common.vo.SearchResult;
import cn.youngh.antelope.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

    @Value("${SEARCH_RESULT_ROWS}")
    private int SEARCH_RESULT_ROWS;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String  search(Model model, String keyword,
                          @RequestParam(name = "page",defaultValue = "1")int pageNum) throws Exception {
        keyword = new String(keyword.getBytes("iso-8859-1"),"utf-8");
        SearchResult searchResult = searchService.search(keyword, pageNum, SEARCH_RESULT_ROWS);
        model.addAttribute("query",keyword);
        model.addAttribute("totalPages",searchResult.getTotalPages());
        model.addAttribute("page",pageNum);
        model.addAttribute("recourdCount",searchResult.getRecordCount());
        model.addAttribute("itemList",searchResult.getItemList());
        return "search";
    }
}
