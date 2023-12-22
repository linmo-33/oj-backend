package com.linmo.oj.common.api;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 分页参数
 * @author ljl
 * @since 2023-12-07 14:13
 */
public class Pager implements Serializable {
    private @Min(
            value = 1L,
            message = "pageIndex必须大于或者等于1"
    ) Integer pageIndex;
    private @Min(
            value = 1L,
            message = "pageSize必须大于或者等于1"
    ) Integer pageSize;
    private String orderBy;

    public Pager() {
    }

    public Integer getPageIndex() {
        return this.pageIndex == null ? 1 : this.pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        if (this.pageSize == null) {
            return 10;
        } else {
            return this.pageSize > 100 ? 100 : this.pageSize;
        }
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}

