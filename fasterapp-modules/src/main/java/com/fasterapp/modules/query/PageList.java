package com.fasterapp.modules.query;

import lombok.Getter;

import java.util.List;

public class PageList {
    @Getter private List data;
    @Getter private int pageIndex;
    @Getter private int pageSize;
    @Getter private int pageCount;
    @Getter private long total;

    public PageList(int pageIndex, int pageSize, int pageCount, long total, List data) {
        this.data = data;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
        this.pageCount = pageCount;
    }
}
