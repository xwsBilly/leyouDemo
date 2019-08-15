package com.leyou.search.pojo;

/**
 * SearchRequest
 *
 * @author xieweisong
 * @date 2019/8/13 17:39
 */
public class SearchRequest {
    /**
     * 关键字
     */
    private String key;
    /**
     * 当前页
     */
    private Integer page;
    /**
     * 默认查询数量
     */
    public static final int DEFAULT_SIZE = 20;
    /**
     * 默认查询页码
     */
    public static final int DEFAULT_PAGE = 1;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if (page == null) {
            return DEFAULT_PAGE;
        }
        // 获取页码时，不能小于1
        return Math.max(DEFAULT_PAGE,page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public int getDefaultSize() {
        return DEFAULT_SIZE;
    }
}
