package com.leyou.user.mapper;

import com.leyou.user.pojo.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * UserMapper
 *
 * @author Billy
 * @date 2019/9/19 10:40
 */
@Repository
public interface UserMapper extends Mapper<User> {
}
