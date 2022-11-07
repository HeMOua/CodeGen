package com.hemou.server.controller;

import com.hemou.server.common.core.controller.BaseController;
import com.hemou.server.common.core.domain.RespResult;
import com.hemou.server.common.core.page.TableDataInfo;
import com.hemou.server.domain.AttrItem;
import com.hemou.server.service.AttrItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 属性选项Controller
 *
 * @author 贺墨于
 * @date 2022-11-07
 */
@RestController
@RequestMapping("/attrItem")
public class AttrItemController extends BaseController {

    @Autowired
    private AttrItemService attrItemService;

    @GetMapping("/list")
    public TableDataInfo list(AttrItem attrItem) {
        startPage();
        List<AttrItem> list = attrItemService.selectList(attrItem);
        return getTableData(list);
    }

    @GetMapping(value = "/{attrItemId}")
    public RespResult getInfo(@PathVariable Long attrItemId) {
        return RespResult.success(attrItemService.selectByItemId(attrItemId));
    }

    @PostMapping
    public RespResult add(@Validated @RequestBody AttrItem attrItem) {
        return toResult(attrItemService.insert(attrItem));
    }

    @PutMapping
    public RespResult edit(@Validated @RequestBody AttrItem attrItem) {
        return toResult(attrItemService.update(attrItem));
    }

    @DeleteMapping("/{attrItemIds}")
    public RespResult remove(@PathVariable Long[] attrItemIds) {
        return toResult(attrItemService.removeByItemIds(attrItemIds));
    }
}