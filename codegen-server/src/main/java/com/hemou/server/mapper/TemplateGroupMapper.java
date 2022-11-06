package com.hemou.server.mapper;

import com.hemou.server.domain.TemplateGroup;

import java.util.List;

/**
 * 模板组Mapper接口
 *
 * @author 贺墨于
 * @date 2022-11-06
 */
public interface TemplateGroupMapper {

    /**
     * 查询模板组
     *
     * @param groupId 模板组主键
     * @return 模板组
     */
    public TemplateGroup selectByGroupId(Long groupId);

    /**
     * 查询模板组列表
     *
     * @param emplateGroup 模板组
     * @return 模板组集合
     */
    public List<TemplateGroup> selectList(TemplateGroup emplateGroup);

    /**
     * 新增模板组
     *
     * @param emplateGroup 模板组
     * @return 结果
     */
    public int insert(TemplateGroup emplateGroup);

    /**
     * 修改模板组
     *
     * @param emplateGroup 模板组
     * @return 结果
     */
    public int update(TemplateGroup emplateGroup);

    /**
     * 删除模板组
     *
     * @param groupId 模板组主键
     * @return 结果
     */
    public int removeByGroupId(Long groupId);

    /**
     * 批量删除模板组
     *
     * @param groupIds 需要删除的数据主键集合
     * @return 结果
     */
    public int removeByGroupIds(Long[] groupIds);

}