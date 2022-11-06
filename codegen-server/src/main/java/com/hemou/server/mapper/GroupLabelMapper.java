package com.hemou.server.mapper;

import com.hemou.server.domain.GroupLabel;

import java.util.List;

/**
 * 模板组标签Mapper接口
 *
 * @author 贺墨于
 * @date 2022-11-06
 */
public interface GroupLabelMapper {

    /**
     * 查询模板组标签
     *
     * @param labelId 模板组标签主键
     * @return 模板组标签
     */
    public GroupLabel selectByLabelId(Long labelId);

    /**
     * 查询模板组标签列表
     *
     * @param groupLabel 模板组标签
     * @return 模板组标签集合
     */
    public List<GroupLabel> selectList(GroupLabel groupLabel);

    /**
     * 新增模板组标签
     *
     * @param groupLabel 模板组标签
     * @return 结果
     */
    public int insert(GroupLabel groupLabel);

    /**
     * 修改模板组标签
     *
     * @param groupLabel 模板组标签
     * @return 结果
     */
    public int update(GroupLabel groupLabel);

    /**
     * 删除模板组标签
     *
     * @param labelId 模板组标签主键
     * @return 结果
     */
    public int removeByLabelId(Long labelId);

    /**
     * 批量删除模板组标签
     *
     * @param labelIds 需要删除的数据主键集合
     * @return 结果
     */
    public int removeByLabelIds(Long[] labelIds);

}