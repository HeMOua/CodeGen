package com.hemou.server.controller;

import com.hemou.server.common.core.controller.BaseController;
import com.hemou.server.common.core.domain.RespResult;
import com.hemou.server.common.core.page.TableDataInfo;
import com.hemou.server.domain.DictData;
import com.hemou.server.service.DictDataService;
import com.hemou.server.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/dict/data")
public class DictDataController extends BaseController {
    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private DictTypeService dictTypeService;

    @GetMapping("/list")
    public TableDataInfo list(DictData dictData) {
        startPage();
        List<DictData> list = dictDataService.selectDictDataList(dictData);
        return getTableData(list);
    }

    /**
     * 查询字典数据详细
     */
    @GetMapping(value = "/{dictCode}")
    public RespResult getInfo(@PathVariable Long dictCode) {
        return RespResult.success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public RespResult dictType(@PathVariable String dictType) {
        return RespResult.success(dictTypeService.selectDictDataByType(dictType));
    }

    /**
     * 新增字典类型
     */
    @PostMapping
    public RespResult add(@Validated @RequestBody DictData dict) {
        return toResult(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @PutMapping
    public RespResult edit(@Validated @RequestBody DictData dict) {
        return toResult(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/{dictCodes}")
    public RespResult remove(@PathVariable Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return RespResult.success();
    }
}
