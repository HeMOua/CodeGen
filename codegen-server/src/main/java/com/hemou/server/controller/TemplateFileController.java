package com.hemou.server.controller;

import com.hemou.server.common.core.controller.BaseController;
import com.hemou.server.service.TemplateFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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


}