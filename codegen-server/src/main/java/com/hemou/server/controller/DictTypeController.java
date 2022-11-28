package com.hemou.server.controller;


import com.hemou.server.common.constants.Constants;
import com.hemou.server.common.core.controller.BaseController;
import com.hemou.server.common.core.domain.RespResult;
import com.hemou.server.common.core.page.TableDataInfo;
import com.hemou.server.domain.DictType;
import com.hemou.server.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dict/type")
public class DictTypeController extends BaseController {
    @Autowired
    private DictTypeService dictTypeService;

    @GetMapping("/list")
    public TableDataInfo list(DictType dictType) {
        startPage();
        List<DictType> list = dictTypeService.selectDictTypeList(dictType);
        return getTableData(list);
    }

    /**
     * 查询字典类型详细
     */
    @GetMapping(value = "/{dictId}")
    public RespResult getInfo(@PathVariable Long dictId) {
        return RespResult.success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @PostMapping
    public RespResult add(@Validated @RequestBody DictType dict) {
        if (Constants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return RespResult.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        return toResult(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @PutMapping
    public RespResult edit(@Validated @RequestBody DictType dict) {
        if (Constants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return RespResult.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        return toResult(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/{dictIds}")
    public RespResult remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return RespResult.success();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public RespResult optionselect() {
        List<DictType> dictTypes = dictTypeService.selectDictTypeAll();
        return RespResult.success(dictTypes);
    }
}
