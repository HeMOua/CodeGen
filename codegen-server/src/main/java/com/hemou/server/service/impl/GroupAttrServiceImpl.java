package com.hemou.server.service.impl;

import com.hemou.server.domain.GroupAttr;
import com.hemou.server.mapper.GroupAttrMapper;
import com.hemou.server.service.GroupAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板组属性Service业务层处理
 *
 * @author 贺墨于
 * @date 2022-11-07
 */
@Service
public class GroupAttrServiceImpl implements GroupAttrService {

    @Autowired
    private GroupAttrMapper groupAttrMapper;

    /**
     * 查询模板组属性
     *
     * @param attrId 模板组属性主键
     * @return 模板组属性
     */
    @Override
    public GroupAttr selectByAttrId(Long attrId) {
        return groupAttrMapper.selectByAttrId(attrId);
    }

    /**
     * 查询模板组属性列表
     *
     * @param groupAttr 模板组属性
     * @return 模板组属性
     */
    @Override
    public List<GroupAttr> selectList(GroupAttr groupAttr) {
        return groupAttrMapper.selectList(groupAttr);
    }

    /**
     * 新增模板组属性
     *
     * @param groupAttr 模板组属性
     * @return 结果
     */
    @Override
    public int insert(GroupAttr groupAttr) {
        return groupAttrMapper.insert(groupAttr);
    }

    /**
     * 修改模板组属性
     *
     * @param groupAttr 模板组属性
     * @return 结果
     */
    @Override
    public int update(GroupAttr groupAttr) {
        return groupAttrMapper.update(groupAttr);
    }

    /**
     * 删除模板组属性
     *
     * @param attrId 模板组属性主键
     * @return 结果
     */
    @Override
    public int removeByAttrId(Long attrId) {
        return groupAttrMapper.removeByAttrId(attrId);
    }

    /**
     * 批量删除模板组属性
     *
     * @param attrIds 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int removeByAttrIds(Long[] attrIds) {
        return groupAttrMapper.removeByAttrIds(attrIds);
    }
}

