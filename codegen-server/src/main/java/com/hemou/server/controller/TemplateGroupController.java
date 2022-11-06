package com.hemou.server.controller;

import com.hemou.server.common.core.controller.BaseController;
import com.hemou.server.common.core.domain.RespResult;
import com.hemou.server.common.core.page.TableDataInfo;
import com.hemou.server.domain.TemplateGroup;
import com.hemou.server.service.TemplateGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 模板组Controller
 *
 * @author 贺墨于
 * @date 2022-11-06
 */
@RestController
@RequestMapping("/tplGroup")
public class TemplateGroupController extends BaseController {

    @Autowired
    private TemplateGroupService templateGroupService;

    @GetMapping("/list")
    public TableDataInfo list(TemplateGroup group) {
        startPage();
        List<TemplateGroup> list = templateGroupService.selectList(group);
        return getTableData(list);
    }

    @GetMapping(value = "/{groupId}")
    public RespResult getInfo(@PathVariable Long groupId) {
        return RespResult.success(templateGroupService.selectByGroupId(groupId));
    }

    @PostMapping
    public RespResult add(@Validated @RequestBody TemplateGroup group) {
        return toResult(templateGroupService.insert(group));
    }

    @PutMapping
    public RespResult edit(@Validated @RequestBody TemplateGroup group) {
        return toResult(templateGroupService.update(group));
    }

    @DeleteMapping("/{groupIds}")
    public RespResult remove(@PathVariable Long[] groupIds) {
        return toResult(templateGroupService.removeByGroupIds(groupIds));
    }
}