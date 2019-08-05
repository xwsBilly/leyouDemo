package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * 商品分类mapper
 * IdListMapper<Category, Long> 扩展多个id查询的方法
 */
@Repository
public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category, Long> {

}
