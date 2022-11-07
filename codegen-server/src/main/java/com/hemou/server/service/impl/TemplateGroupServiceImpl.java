package com.hemou.server.service.impl;

import com.hemou.server.domain.TemplateGroup;
import com.hemou.server.mapper.TemplateGroupMapper;
import com.hemou.server.service.TemplateGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板组Service业务层处理
 *
 * @author 贺墨于
 * @date 2022-11-06
 */
@Service
public class TemplateGroupServiceImpl implements TemplateGroupService {

    @Autowired
    private TemplateGroupMapper templateGroupMapper;

    /**
     * 查询模板组
     *
     * @param groupId 模板组主键
     * @return 模板组
     */
    @Override
    public TemplateGroup selectByGroupId(Long groupId) {
        return templateGroupMapper.selectByGroupId(groupId);
    }

    /**
     * 查询模板组列表
     *
     * @param templateGroup 模板组
     * @return 模板组
     */
    @Override
    public List<TemplateGroup> selectList(TemplateGroup templateGroup) {
        return templateGroupMapper.selectList(templateGroup);
    }

    /**
     * 新增模板组
     *
     * @param templateGroup 模板组
     * @return 结果
     */
    @Override
    public int insert(TemplateGroup templateGroup) {
        return templateGroupMapper.insert(templateGroup);
    }

    /**
     * 修改模板组
     *
     * @param templateGroup 模板组
     * @return 结果
     */
    @Override
    public int update(TemplateGroup templateGroup) {
        return templateGroupMapper.update(templateGroup);
    }

    /**
     * 删除模板组
     *
     * @param groupId 模板组主键
     * @return 结果
     */
    @Override
    public int removeByGroupId(Long groupId) {
        return templateGroupMapper.removeByGroupId(groupId);
    }

    /**
     * 批量删除模板组
     *
     * @param groupIds 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int removeByGroupIds(Long[] groupIds) {
        return templateGroupMapper.removeByGroupIds(groupIds);
    }
}

