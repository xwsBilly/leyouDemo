package com.leyou.common.vo;

import lombok.Data;

import java.util.List;

/**
 * 分页的视图对象(view object)
 * @param <T>
 */
@Data
public class PageResultVO<T> {
    // 总条数
    private Long total;
    // 总页数
    private Integer totalPage;
    //当前页的数据
    private List<T> items;

    public PageResultVO() {
    }

    public PageResultVO(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResultVO(Long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }
}
