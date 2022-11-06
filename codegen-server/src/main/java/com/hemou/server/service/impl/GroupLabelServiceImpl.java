package com.hemou.server.service.impl;

import com.hemou.server.domain.GroupLabel;
import com.hemou.server.mapper.GroupLabelMapper;
import com.hemou.server.service.GroupLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板组标签Service业务层处理
 *
 * @author 贺墨于
 * @date 2022-11-06
 */
@Service
public class GroupLabelServiceImpl implements GroupLabelService {

    @Autowired
    private GroupLabelMapper groupLabelMapper;

    /**
     * 查询模板组标签
     *
     * @param labelId 模板组标签主键
     * @return 模板组标签
     */
    @Override
    public GroupLabel selectByLabelId(Long labelId) {
        return groupLabelMapper.selectByLabelId(labelId);
    }

    /**
     * 查询模板组标签列表
     *
     * @param groupLabel 模板组标签
     * @return 模板组标签
     */
    @Override
    public List<GroupLabel> selectList(GroupLabel groupLabel) {
        return groupLabelMapper.selectList(groupLabel);
    }

    /**
     * 新增模板组标签
     *
     * @param groupLabel 模板组标签
     * @return 结果
     */
    @Override
    public int insert(GroupLabel groupLabel) {
        return groupLabelMapper.insert(groupLabel);
    }

    /**
     * 修改模板组标签
     *
     * @param groupLabel 模板组标签
     * @return 结果
     */
    @Override
    public int update(GroupLabel groupLabel) {
        return groupLabelMapper.update(groupLabel);
    }

    /**
     * 删除模板组标签
     *
     * @param labelId 模板组标签主键
     * @return 结果
     */
    @Override
    public int removeByLabelId(Long labelId) {
        return groupLabelMapper.removeByLabelId(labelId);
    }

    /**
     * 批量删除模板组标签
     *
     * @param labelIds 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int removeByLabelIds(Long[] labelIds) {
        return groupLabelMapper.removeByLabelIds(labelIds);
    }
}

