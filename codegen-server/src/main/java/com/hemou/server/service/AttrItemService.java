package com.hemou.server.service;


import com.hemou.server.domain.AttrItem;

import java.util.List;

/**
 * 属性选项对象 t_attr_item
 *
 * @author 贺墨于
 * @date 2022-11-07
 */
public interface AttrItemService {

    /**
     * 查询属性选项
     *
     * @param itemId 属性选项主键
     * @return 属性选项
     */
    public AttrItem selectByItemId(Long itemId);

    /**
     * 查询属性选项列表
     *
     * @param attrItem 属性选项
     * @return 属性选项集合
     */
    public List<AttrItem> selectList(AttrItem attrItem);

    /**
     * 新增属性选项
     *
     * @param attrItem 属性选项
     * @return 结果
     */
    public int insert(AttrItem attrItem);

    /**
     * 修改属性选项
     *
     * @param attrItem 属性选项
     * @return 结果
     */
    public int update(AttrItem attrItem);

    /**
     * 删除属性选项
     *
     * @param itemId 属性选项主键
     * @return 结果
     */
    public int removeByItemId(Long itemId);

    /**
     * 批量删除属性选项
     *
     * @param itemIds 需要删除的数据主键集合
     * @return 结果
     */
    public int removeByItemIds(Long[] itemIds);

}