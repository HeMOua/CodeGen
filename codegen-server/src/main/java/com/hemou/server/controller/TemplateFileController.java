package com.hemou.server.controller;

import com.hemou.server.common.core.controller.BaseController;
import com.hemou.server.common.core.domain.RespResult;
import com.hemou.server.common.core.page.TableDataInfo;
import com.hemou.server.domain.TemplateFile;
import com.hemou.server.service.TemplateFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 模板文件Controller
 *
 * @author 贺墨于
 * @date 2022-11-06
 */
@RestController
@RequestMapping("/tplFile")
public class TemplateFileController extends BaseController {

    @Autowired
    private TemplateFileService templateFileService;

    @GetMapping("/list")
    public TableDataInfo list(TemplateFile file) {
        startPage();
        List<TemplateFile> list = templateFileService.selectList(file);
        return getTableData(list);
    }

    @GetMapping(value = "/{fileId}")
    public RespResult getInfo(@PathVariable Long fileId) {
        return RespResult.success(templateFileService.selectByTplId(fileId));
    }

    @PostMapping
    public RespResult add(@Validated @RequestBody TemplateFile file) {
        return toResult(templateFileService.insert(file));
    }

    @PutMapping
    public RespResult edit(@Validated @RequestBody TemplateFile file) {
        return toResult(templateFileService.update(file));
    }

    @DeleteMapping("/{fileIds}")
    public RespResult remove(@PathVariable Long[] fileIds) {
        return toResult(templateFileService.removeByTplIds(fileIds));
    }
}