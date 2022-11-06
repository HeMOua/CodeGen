package com.hemou.server.service.impl;

import com.hemou.server.domain.TemplateFile;
import com.hemou.server.mapper.TemplateFileMapper;
import com.hemou.server.service.TemplateFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板文件Service业务层处理
 *
 * @author 贺墨于
 * @date 2022-11-06
 */
@Service
public class TemplateFileServiceImpl implements TemplateFileService {

    @Autowired
    private TemplateFileMapper emplateFileMapper;

    /**
     * 查询模板文件
     *
     * @param tplId 模板文件主键
     * @return 模板文件
     */
    @Override
    public TemplateFile selectByTplId(Long tplId) {
        return emplateFileMapper.selectByTplId(tplId);
    }

    /**
     * 查询模板文件列表
     *
     * @param emplateFile 模板文件
     * @return 模板文件
     */
    @Override
    public List<TemplateFile> selectList(TemplateFile emplateFile) {
        return emplateFileMapper.selectList(emplateFile);
    }

    /**
     * 新增模板文件
     *
     * @param emplateFile 模板文件
     * @return 结果
     */
    @Override
    public int insert(TemplateFile emplateFile) {
        return emplateFileMapper.insert(emplateFile);
    }

    /**
     * 修改模板文件
     *
     * @param emplateFile 模板文件
     * @return 结果
     */
    @Override
    public int update(TemplateFile emplateFile) {
        return emplateFileMapper.update(emplateFile);
    }

    /**
     * 删除模板文件
     *
     * @param tplId 模板文件主键
     * @return 结果
     */
    @Override
    public int removeByTplId(Long tplId) {
        return emplateFileMapper.removeByTplId(tplId);
    }

    /**
     * 批量删除模板文件
     *
     * @param tplIds 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int removeByTplIds(Long[] tplIds) {
        return emplateFileMapper.removeByTplIds(tplIds);
    }
}

