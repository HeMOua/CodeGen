package com.hemou.server.service;

import com.hemou.server.domain.DictData;
import com.hemou.server.domain.DictType;

import java.util.List;

public interface DictTypeService
{
    /**
     * 根据条件分页查询字典类型
     * 
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    public List<DictType> selectDictTypeList(DictType dictType);

    /**
     * 根据所有字典类型
     * 
     * @return 字典类型集合信息
     */
    public List<DictType> selectDictTypeAll();

    /**
     * 根据字典类型查询字典数据
     * 
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<DictData> selectDictDataByType(String dictType);

    /**
     * 根据字典类型ID查询信息
     * 
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    public DictType selectDictTypeById(Long dictId);

    /**
     * 根据字典类型查询信息
     * 
     * @param dictType 字典类型
     * @return 字典类型
     */
    public DictType selectDictTypeByType(String dictType);

    /**
     * 批量删除字典信息
     * 
     * @param dictIds 需要删除的字典ID
     */
    public void deleteDictTypeByIds(Long[] dictIds);

    /**
     * 新增保存字典类型信息
     * 
     * @param dictType 字典类型信息
     * @return 结果
     */
    public int insertDictType(DictType dictType);

    /**
     * 修改保存字典类型信息
     * 
     * @param dictType 字典类型信息
     * @return 结果
     */
    public int updateDictType(DictType dictType);

    /**
     * 校验字典类型称是否唯一
     * 
     * @param dictType 字典类型
     * @return 结果
     */
    public String checkDictTypeUnique(DictType dictType);
}
