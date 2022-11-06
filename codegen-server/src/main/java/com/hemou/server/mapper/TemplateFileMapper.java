package com.hemou.server.mapper;

import com.hemou.server.domain.TemplateFile;

import java.util.List;

/**
 * 模板文件Mapper接口
 *
 * @author 贺墨于
 * @date 2022-11-06
 */
public interface TemplateFileMapper {

    /**
     * 查询模板文件
     *
     * @param tplId 模板文件主键
     * @return 模板文件
     */
    public TemplateFile selectByTplId(Long tplId);

    /**
     * 查询模板文件列表
     *
     * @param emplateFile 模板文件
     * @return 模板文件集合
     */
    public List<TemplateFile> selectList(TemplateFile emplateFile);

    /**
     * 新增模板文件
     *
     * @param emplateFile 模板文件
     * @return 结果
     */
    public int insert(TemplateFile emplateFile);

    /**
     * 修改模板文件
     *
     * @param emplateFile 模板文件
     * @return 结果
     */
    public int update(TemplateFile emplateFile);

    /**
     * 删除模板文件
     *
     * @param tplId 模板文件主键
     * @return 结果
     */
    public int removeByTplId(Long tplId);

    /**
     * 批量删除模板文件
     *
     * @param tplIds 需要删除的数据主键集合
     * @return 结果
     */
    public int removeByTplIds(Long[] tplIds);

}