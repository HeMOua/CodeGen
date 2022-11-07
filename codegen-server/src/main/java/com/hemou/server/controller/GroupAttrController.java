package com.hemou.server.controller;

import com.hemou.server.service.GroupAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 模板组属性Controller
 *
 * @author 贺墨于
 * @date 2022-11-07
 */
@RestController
@RequestMapping("/groupAttr")
public class GroupAttrController {

    @Autowired
    private GroupAttrService groupAttrService;


}