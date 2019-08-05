package com.leyou.item.service.impl;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.MyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * SpecificationServiceImpl
 *
 * @author xieweisong
 * @date 2019/8/4 14:44
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecGroup> querySpecGroupByCid(Long cid) {
        // 查询条件
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        // 根据SpecGroup中的非空参数进行查询
        List<SpecGroup> groupList = specGroupMapper.select(specGroup);
        // 未查到商品规格组
        if (CollectionUtils.isEmpty(groupList)) {
            throw new MyException(ExceptionEnum.SPECIFICATION_GROUP_NOT_FOUND);
        }
        return groupList;
    }

    /**
     * 未实现增、删、改
     */

    @Override
    public List<SpecParam> querySpecParamList(Long gid, Long cid, Boolean searching) {
        // 查询条件
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        // 根据SpecParam中的非空参数进行查询
        List<SpecParam> paramList = specParamMapper.select(specParam);
        // 未查到商品规格参数
        if (CollectionUtils.isEmpty(paramList)) {
            throw new MyException(ExceptionEnum.SPECIFICATION_GROUP_NOT_FOUND);
        }
        return paramList;
    }
}
