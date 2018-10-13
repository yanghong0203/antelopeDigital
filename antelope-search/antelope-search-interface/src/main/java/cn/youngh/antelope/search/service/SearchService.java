package cn.youngh.antelope.search.service;

import cn.youngh.antelope.common.vo.SearchResult;

public interface SearchService {
    SearchResult search(String keyword,int pageNum,int pageSize) throws Exception;

}
