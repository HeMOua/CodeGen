package com.hemou.server.service.impl;

import com.hemou.server.domain.AttrItem;
import com.hemou.server.mapper.AttrItemMapper;
import com.hemou.server.service.AttrItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 属性选项Service业务层处理
 *
 * @author 贺墨于
 * @date 2022-11-07
 */
@Service
public class AttrItemServiceImpl implements AttrItemService {

    @Autowired
    private AttrItemMapper attrItemMapper;

    /**
     * 查询属性选项
     *
     * @param itemId 属性选项主键
     * @return 属性选项
     */
    @Override
    public AttrItem selectByItemId(Long itemId) {
        return attrItemMapper.selectByItemId(itemId);
    }

    /**
     * 查询属性选项列表
     *
     * @param attrItem 属性选项
     * @return 属性选项
     */
    @Override
    public List<AttrItem> selectList(AttrItem attrItem) {
        return attrItemMapper.selectList(attrItem);
    }

    /**
     * 新增属性选项
     *
     * @param attrItem 属性选项
     * @return 结果
     */
    @Override
    public int insert(AttrItem attrItem) {
        return attrItemMapper.insert(attrItem);
    }

    /**
     * 修改属性选项
     *
     * @param attrItem 属性选项
     * @return 结果
     */
    @Override
    public int update(AttrItem attrItem) {
        return attrItemMapper.update(attrItem);
    }

    /**
     * 删除属性选项
     *
     * @param itemId 属性选项主键
     * @return 结果
     */
    @Override
    public int removeByItemId(Long itemId) {
        return attrItemMapper.removeByItemId(itemId);
    }

    /**
     * 批量删除属性选项
     *
     * @param itemIds 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int removeByItemIds(Long[] itemIds) {
        return attrItemMapper.removeByItemIds(itemIds);
    }
}

