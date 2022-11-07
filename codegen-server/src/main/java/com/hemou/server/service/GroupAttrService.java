package com.hemou.server.service;


import com.hemou.server.domain.GroupAttr;

import java.util.List;

/**
 * 模板组属性对象 t_group_attr
 *
 * @author 贺墨于
 * @date 2022-11-07
 */
public interface GroupAttrService {

    /**
     * 查询模板组属性
     *
     * @param attrId 模板组属性主键
     * @return 模板组属性
     */
    public GroupAttr selectByAttrId(Long attrId);

    /**
     * 查询模板组属性列表
     *
     * @param groupAttr 模板组属性
     * @return 模板组属性集合
     */
    public List<GroupAttr> selectList(GroupAttr groupAttr);

    /**
     * 新增模板组属性
     *
     * @param groupAttr 模板组属性
     * @return 结果
     */
    public int insert(GroupAttr groupAttr);

    /**
     * 修改模板组属性
     *
     * @param groupAttr 模板组属性
     * @return 结果
     */
    public int update(GroupAttr groupAttr);

    /**
     * 删除模板组属性
     *
     * @param attrId 模板组属性主键
     * @return 结果
     */
    public int removeByAttrId(Long attrId);

    /**
     * 批量删除模板组属性
     *
     * @param attrIds 需要删除的数据主键集合
     * @return 结果
     */
    public int removeByAttrIds(Long[] attrIds);

}