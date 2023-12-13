package com.linmo.oj.common.api;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 * @author ljl
 * @since 2023-11-30 20:08
 */
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private long totalCount;
    private int pageSize;
    private int totalPage;
    private int pageIndex;
    private List<T> list;

    public PageResult() {
    }

    public PageResult(List<T> list, long totalCount, int pageIndex, int pageSize) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalPage = (int)Math.ceil((double)totalCount / (double)pageSize);
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}


