package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

public interface CategoryService {

    List<Category> queryCategoryListByPid(Long pid);

    /**
     * 通过多个分类id查询分类
     * @param ids
     * @return
     */
    List<Category> queryByIds(List<Long> ids);
}
