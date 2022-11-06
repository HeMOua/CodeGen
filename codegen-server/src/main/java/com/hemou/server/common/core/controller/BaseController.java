package com.hemou.server.common.core.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hemou.server.common.constants.Constant;
import com.hemou.server.common.core.domain.RespResult;
import com.hemou.server.common.core.page.TableDataInfo;
import com.hemou.server.common.utils.ServletUtils;

import java.util.List;

public class BaseController {

    // 开始分页
    protected void startPage() {
        Integer pageNum = ServletUtils.getParameterToInt(Constant.PAGE_NUM);
        Integer pageSize = ServletUtils.getParameterToInt(Constant.PAGE_SIZE);
        if (ObjectUtil.isNotNull(pageNum) && ObjectUtil.isNotNull(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
        }
    }

    // 返回分页数据
    protected TableDataInfo getTableData(List<?> list) {
        TableDataInfo tableData = new TableDataInfo();
        tableData.setCode(Constant.SUCCESS);
        tableData.setRows(list);
        tableData.setTotal(new PageInfo<>(list).getTotal());
        return tableData;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected RespResult toResult(int rows) {
        return rows > 0 ? RespResult.success() : RespResult.error();
    }
}