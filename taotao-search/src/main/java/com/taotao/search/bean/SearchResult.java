package com.taotao.search.bean;

import java.util.List;

public class SearchResult {
    
    private Long total;
    
    private List<Item> list;
    
    public SearchResult() {
    }

    public SearchResult(Long total, List<Item> list) {
        this.total = total;
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }
}
