package com.pbd.project.util;

import java.util.List;

public class Pagination<T>{

    private int size;
    private int page;
    private long numberOfPages;
    private List<T> query;

    public Pagination(int size, int page, long numberOfPages, List<T> query) {
        this.size = size;
        this.page = page;
        this.numberOfPages = numberOfPages;
        this.query = query;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(long numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public List<T> getQuery() {
        return query;
    }

    public void setQuery(List<T> query) {
        this.query = query;
    }
}
