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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<SpecGroup> querySpecGroupAllByCid(Long cid) {
        // 查询规格组
        List<SpecGroup> specGroups = querySpecGroupByCid(cid);
        // 查询当前分类下的参数，而不是组内
        List<SpecParam> specParams = querySpecParamList(null, cid, null);

        // 先把规格参数变成map，map的key为规格组id，map的值是组下的所有参数
        Map<Long, List<SpecParam>> paramMap = new HashMap<>();
        for (SpecParam specParam : specParams) {
            if (!paramMap.containsKey(specParam.getGroupId())){
                // 这个组id在map中不存在，则新增一个list
                paramMap.put(specParam.getGroupId(),new ArrayList<>());
            }
            paramMap.get(specParam.getGroupId()).add(specParam);
        }

        //填充params到groups中
        for (SpecGroup specGroup : specGroups) {
            specGroup.setParams(paramMap.get(specGroup.getId()));
        }

        return specGroups;
    }
}
