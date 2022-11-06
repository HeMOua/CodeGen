package com.hemou.server.controller;

import com.hemou.server.common.core.controller.BaseController;
import com.hemou.server.common.core.domain.RespResult;
import com.hemou.server.common.core.page.TableDataInfo;
import com.hemou.server.domain.GroupLabel;
import com.hemou.server.service.GroupLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 模板组标签Controller
 *
 * @author 贺墨于
 * @date 2022-11-06
 */
@RestController
@RequestMapping("/groupLabel")
public class GroupLabelController extends BaseController {

    @Autowired
    private GroupLabelService groupLabelService;

    @GetMapping("/list")
    public TableDataInfo list(GroupLabel label) {
        startPage();
        List<GroupLabel> list = groupLabelService.selectList(label);
        return getTableData(list);
    }

    @GetMapping(value = "/{labelId}")
    public RespResult getInfo(@PathVariable Long labelId) {
        return RespResult.success(groupLabelService.selectByLabelId(labelId));
    }

    @PostMapping
    public RespResult add(@Validated @RequestBody GroupLabel label) {
        return toResult(groupLabelService.insert(label));
    }

    @PutMapping
    public RespResult edit(@Validated @RequestBody GroupLabel label) {
        return toResult(groupLabelService.update(label));
    }

    @DeleteMapping("/{labelIds}")
    public RespResult remove(@PathVariable Long[] labelIds) {
        return toResult(groupLabelService.removeByLabelIds(labelIds));
    }
}