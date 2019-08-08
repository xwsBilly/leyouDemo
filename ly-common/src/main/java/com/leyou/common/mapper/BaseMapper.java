package com.leyou.common.mapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * BaseMapper
 * 整合的一个基础mapper
 * tk.mybatis.mapper.common.special.InsertListMapper 这个接口限制主键id的属性名称必须为`id`
 * tk.mybatis.mapper.additional.insert.InsertListMapper 这个接口没有限制主键
 *
 * @author xieweisong
 * @date 2019/8/6 14:31
 */
@RegisterMapper
public interface BaseMapper<T, PK> extends Mapper<T>, IdListMapper<T, PK>, InsertListMapper<T> {
}
