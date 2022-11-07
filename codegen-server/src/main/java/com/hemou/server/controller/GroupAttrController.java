package com.hemou.server.controller;

import com.hemou.server.common.core.controller.BaseController;
import com.hemou.server.common.core.domain.RespResult;
import com.hemou.server.common.core.page.TableDataInfo;
import com.hemou.server.domain.GroupAttr;
import com.hemou.server.service.GroupAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 模板组属性Controller
 *
 * @author 贺墨于
 * @date 2022-11-07
 */
@RestController
@RequestMapping("/groupAttr")
public class GroupAttrController extends BaseController {

    @Autowired
    private GroupAttrService groupAttrService;

    @GetMapping("/list")
    public TableDataInfo list(GroupAttr groupAttr) {
        startPage();
        List<GroupAttr> list = groupAttrService.selectList(groupAttr);
        return getTableData(list);
    }

    @GetMapping(value = "/{groupAttrId}")
    public RespResult getInfo(@PathVariable Long groupAttrId) {
        return RespResult.success(groupAttrService.selectByAttrId(groupAttrId));
    }

    @PostMapping
    public RespResult add(@Validated @RequestBody GroupAttr groupAttr) {
        return toResult(groupAttrService.insert(groupAttr));
    }

    @PutMapping
    public RespResult edit(@Validated @RequestBody GroupAttr groupAttr) {
        return toResult(groupAttrService.update(groupAttr));
    }

    @DeleteMapping("/{groupAttrIds}")
    public RespResult remove(@PathVariable Long[] groupAttrIds) {
        return toResult(groupAttrService.removeByAttrIds(groupAttrIds));
    }
}