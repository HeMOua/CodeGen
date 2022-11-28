package com.hemou.server.service.impl;

import com.hemou.server.common.constants.Constants;
import com.hemou.server.domain.DictData;
import com.hemou.server.domain.DictType;
import com.hemou.server.mapper.DictDataMapper;
import com.hemou.server.mapper.DictTypeMapper;
import com.hemou.server.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Service
public class DictTypeServiceImpl implements DictTypeService {
    @Autowired
    private DictTypeMapper dictTypeMapper;

    @Autowired
    private DictDataMapper dictDataMapper;

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public List<DictType> selectDictTypeList(DictType dictType) {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Override
    public List<DictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<DictData> selectDictDataByType(String dictType) {
        return dictDataMapper.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    @Override
    public DictType selectDictTypeById(Long dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    @Override
    public DictType selectDictTypeByType(String dictType) {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }

    /**
     * 批量删除字典类型信息
     *
     * @param dictIds 需要删除的字典ID
     */
    @Override
    public void deleteDictTypeByIds(Long[] dictIds) {
        for (Long dictId : dictIds) {
            DictType dictType = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
                throw new RuntimeException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
            dictTypeMapper.deleteDictTypeById(dictId);
        }
    }

    /**
     * 新增保存字典类型信息
     *
     * @param dict 字典类型信息
     * @return 结果
     */
    @Override
    public int insertDictType(DictType dict) {
        return dictTypeMapper.insertDictType(dict);
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dict 字典类型信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDictType(DictType dict) {
        DictType oldDict = dictTypeMapper.selectDictTypeById(dict.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dict.getDictType());
        return dictTypeMapper.updateDictType(dict);
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public String checkDictTypeUnique(DictType dict) {
        long dictId = ObjectUtils.isEmpty(dict.getDictId()) ? -1L : dict.getDictId();
        DictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
        if (ObjectUtils.isEmpty(dictType) && dictType.getDictId() != dictId) {
            return Constants.NOT_UNIQUE;
        }
        return Constants.UNIQUE;
    }
}
