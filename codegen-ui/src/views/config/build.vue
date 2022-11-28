<template>
  <div class="build-wrapper">
    <div class="nav-wrapper">
      <ul class="nav">
        <li :class="{active: isActive('tplInfo')}" @click="handleActive('tplInfo')">模板信息</li>
        <li :class="{active: isActive('tplAttr')}" @click="handleActive('tplAttr')">属性</li>
        <li :class="{active: isActive('tplCode')}" @click="handleActive('tplCode')">代码</li>
        <li :class="{active: isActive('tplDoc')}" @click="handleActive('tplDoc')">使用文档</li>
      </ul>
    </div>
    <div class="content-layout">
      <!-- 模板信息 -->
      <div class="content tplInfo" :class="{active: isActive('tplInfo')}">
        <div class="app-container">
          <el-card>
            <div slot="header">
              <span class="title">模板信息</span>
            </div>
            <div>
              <el-form ref="form" :model="form" :rules="rules" label-width="120px">
                <el-form-item label="模板名称" prop="groupName">
                  <el-input v-model="form.groupName" />
                </el-form-item>
                <el-form-item label="介绍" prop="description">
                  <el-input v-model="form.description" type="textarea" />
                </el-form-item>
                <el-form-item label="图片">
                  <el-upload ref="upload" class="upload" action="#" list-type="picture-card" :auto-upload="false">
                    <i slot="default" class="el-icon-plus" />
                    <div slot="file" slot-scope="{file}">
                      <el-image style="width: 100%; height: 100%" :src="file.url" fit="cover" />
                      <span class="el-upload-list__item-actions">
                        <span
                          class="el-upload-list__item-preview"
                          @click="handlePictureCardPreview(file)"
                        >
                          <i class="el-icon-zoom-in" />
                        </span>
                        <span
                          v-if="!disabled"
                          class="el-upload-list__item-delete"
                          @click="handleRemove(file)"
                        >
                          <i class="el-icon-delete" />
                        </span>
                      </span>
                    </div>
                  </el-upload>
                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </div>
      </div>

      <!-- 模板属性 -->
      <div class="content tplAttr" :class="{active: isActive('tplAttr')}">
        <div class="app-container">
          <el-row class="toolbar clear mb-10" :gutter="10">
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleAddProp"
              >新增</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                :disabled="multiple.prop"
                @click="handleDelProp"
              >删除</el-button>
            </el-col>
          </el-row>

          <el-table
            ref="propTable"
            class="table"
            :data="form.globalProps"
            style="width: 100%"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="#" width="55">
              <template slot-scope="scope">
                <span>{{ scope.$index + 1 }}</span>
              </template>
            </el-table-column>
            <el-table-column property="attrName" label="属性名称">
              <template slot-scope="{row}">
                <el-input v-model="row.attrName" type="text" placeholder="请输入属性名称" />
              </template>
            </el-table-column>
            <el-table-column property="attrVar" label="属性变量">
              <template slot-scope="{row}">
                <el-input v-model="row.attrVar" type="text" placeholder="请输入属性变量" />
              </template>
            </el-table-column>
            <el-table-column property="attrType" label="输入类型">
              <template slot-scope="scope">
                <el-select v-model="scope.row.attrType" placeholder="请选择输入类型" @change="attrTypeChange($event, scope.$index)" @focus="lock = true">
                  <el-option v-for="item in attrItemTypeList" :key="item.value" :value="item.value" :label="item.label" />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="属性默认值">
              <template slot-scope="{row}">
                <el-input v-if="isTypeShow(row, 'text')" v-model="row.defaultValue" placeholder="无默认值" />
                <el-input v-else-if="isTypeShow(row, 'textarea')" v-model="row.defaultValue" placeholder="无默认值" />
                <el-select v-else-if="isTypeShow(row, 'single')" v-model="row.defaultValue" placeholder="无默认值">
                  <el-option v-for="item in row.items" :key="item.value" :value="item.value" :label="item.name" />
                </el-select>
                <el-select v-else-if="isTypeShow(row, 'multi')" v-model="row.defaultValue" multiple placeholder="无默认值">
                  <el-option v-for="item in row.items" :key="item.value" :value="item.value" :label="item.name" />
                </el-select>
                <el-input-number v-else-if="isTypeShow(row, 'number')" v-model="row.defaultValue" controls-position="right" />
                <el-select v-else-if="isTypeShow(row, 'boolean')" v-model="row.defaultValue" placeholder="无默认值" no-match-text="无默认值">
                  <el-option v-for="item in booleanList" :key="item.value" :value="item.value" :label="item.label" />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column property="description" label="属性说明">
              <template slot-scope="{row}">
                <el-input v-model="row.description" type="text" placeholder="请输入属性说明" />
              </template>
            </el-table-column>
            <el-table-column property="notNull" label="必填" width="90" align="center">
              <template slot-scope="{row}">
                <el-checkbox v-model="row.notNull" />
              </template>
            </el-table-column>
            <el-table-column property="configKey" label="配置键" width="90" align="center">
              <template slot-scope="{row}">
                <el-checkbox v-model="row.configKey" />
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>

      <!-- 模板代码 -->
      <div class="content tplCode" :class="{active: isActive('tplCode')}">
        <Split v-model="split" :min="minWidth + 'px'">
          <template #trigger>
            <div class="ivu-split-trigger ivu-split-trigger-vertical" style="width: 0; border: 0; border-left: 1px solid #eee;">
              <div class="ivu-split-trigger-bar-con vertical" />
            </div>
          </template>
          <template #left>
            <div class="filter-container">
              <el-input
                v-model="filterText"
                placeholder="根据文件名过滤"
              />
              <el-button icon="el-icon-aim" circle size="small" />
            </div>
            <el-tree
              :data="treeData"
              node-key="id"
              default-expand-all
              draggable
              :style="minWidth + 'px'"
              :allow-drop="allowDrop"
              :allow-drag="allowDrag"
              @node-contextmenu="(a, b, c, e) => handleContextEvent(e)"
            >
              <div slot-scope="{ node }" class="tree-node">
                <span class="tree-node-label">
                  <SvgIcon icon-class="java" class-name="tree-node-icon" />
                  <span>{{ node.label }}</span>
                </span>
                <span class="tree-node-opera" @click.stop="handleContextEvent($event, true)">
                  <SvgIcon icon-class="dot" class-name="tree-node-icon" />
                </span>
                <div class="tree-node-menu" @click.stop>
                  <ul>
                    <li v-if="node.data.type == 'folder'" @click="handleAddNode(node, 'file')">
                      <SvgIcon icon-class="file" class-name="tree-node-icon" />新建模板
                    </li>
                    <li v-if="node.data.type == 'folder'" class="divide-b">
                      <SvgIcon icon-class="folder" class-name="tree-node-icon" @click="handleAddNode(node, 'folder')" />新建目录
                    </li>
                    <li v-if="node.level != 1">
                      <SvgIcon icon-class="delete" class-name="tree-node-icon" @click="handleDelete(Node)" />删除
                    </li>
                    <li v-if="node.level == 1">
                      <SvgIcon icon-class="empty" class-name="tree-node-icon" @click="handleEmptyTree" />清空
                    </li>
                    <li v-if="node.level != 1" class="divide-t">
                      <SvgIcon icon-class="setting" class-name="tree-node-icon" @click="handleUpdateNode(node)" />设置
                    </li>
                  </ul>
                </div>
              </div>
            </el-tree>
          </template>
          <template #right>
            <div class="editor-wrapper">
              <el-skeleton class="p-20" :rows="8" />
            </div>
          </template>
        </Split>
      </div>

      <!-- 模板文档 -->
      <div class="content tplDoc" :class="{active: isActive('tplDoc')}">
        <mavon-editor v-model="form.manual" style="height: 100%" />
      </div>
    </div>

    <!-- 预览图片 -->
    <el-dialog :visible.sync="open.preview">
      <img width="100%" :src="imageUrl" alt="">
    </el-dialog>

    <!-- 添加属性选项对话框 -->
    <el-dialog :title="title" :visible.sync="open.item" width="680px" append-to-body>
      <el-row class="toolbar" :gutter="10" style="margin-bottom: 5px;">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAddItem"
          >新增选项</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple.item"
            @click="handleDelItem"
          >删除选项</el-button>
        </el-col>
      </el-row>
      <el-table
        ref="itemTable"
        :data="itemList"
        style="width: 100%"
        @selection-change="handleItemSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="#" width="55">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column property="name" label="选项名称">
          <template slot-scope="{row}">
            <el-input v-model="row.name" type="text" placeholder="请输入选项名称" />
          </template>
        </el-table-column>
        <el-table-column property="value" label="选项值">
          <template slot-scope="{row}">
            <el-input v-model="row.value" type="text" placeholder="选项值" />
          </template>
        </el-table-column>
        <el-table-column property="description" label="选项说明">
          <template slot-scope="{row}">
            <el-input v-model="row.description" type="text" placeholder="选项说明" />
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitItem">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { deepClone, batchDelete, checkDuplicates, arrayRemove } from '@/utils'

export default {
  name: 'ConfigBuild',
  data() {
    return {
      // 控制属性
      split: 0.2,
      active: 'tplCode',
      minWidth: 160,
      open: {
        preview: false,
        item: false
      },
      // 模板信息属性
      imageUrl: '',
      disabled: false,
      form: {
        groupName: undefined,
        description: undefined,
        manual: undefined,
        images: undefined,
        globalProps: []
      },
      itemList: [],
      rules: {
        groupName: [
          { required: true, message: '请输入模板名称', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入模板介绍', trigger: 'blur' }
        ],
        attrName: [
          { required: true, message: '属性名称不能为空', trigger: 'blur' }
        ],
        attrVar: [
          { required: true, message: '数形变量不能为空', trigger: 'blur' }
        ]
      },
      // 模板代码属性
      filterText: undefined,
      treeData: [{ label: '_project.enName', type: 'folder' }],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      // 模板属性
      lock: false,
      title: '',
      selectIndex: -1,
      selectList: {
        prop: [],
        item: []
      },
      multiple: {
        prop: true,
        item: true
      },
      attrItemTypeList: [
        { label: '文本', value: 'text' },
        { label: '文本域', value: 'textarea' },
        { label: '单选', value: 'single' },
        { label: '多选', value: 'multi' },
        { label: '整数', value: 'number' },
        { label: '布尔', value: 'boolean' }
      ],
      booleanList: [
        { label: '是', value: true },
        { label: '否', value: false }
      ]
    }
  },
  created() {
    document.body.addEventListener('click', this.closeContextMenu)
  },
  methods: {
    // 控制方法
    isActive(item) {
      return this.active === item
    },
    handleActive(item) {
      this.active = item
    },
    // 模板信息方法
    handleRemove(file) {
      arrayRemove(file, this.$refs.upload.uploadFiles)
    },
    handlePictureCardPreview(file) {
      this.imageUrl = file.url
      this.open.preview = true
    },
    // 模板代码方法
    addCloseContextEvent() {
      document.body.addEventListener('click', this.closeContextMenu)
    },
    handleContextEvent(event, flag) {
      this.closeContextMenu()
      let elem
      if (flag) {
        elem = event.target.parentNode
        if (elem.classList.contains('tree-node-opera')) {
          elem = elem.parentNode
        } else if (elem.classList.contains('svg-icon')) {
          elem = elem.parentNode.parentNode
        }
      } else {
        elem = event.$el
      }
      elem = elem.querySelector('.tree-node-menu')
      if (elem.style.display !== 'block') {
        elem.style.display = 'block'
        this.addCloseContextEvent()
      }
    },
    closeContextMenu() {
      document.querySelectorAll('.tree-node-menu').forEach(item => {
        item.style.display = 'none'
      })
      document.body.removeEventListener('click', this.closeContextMenu)
    },
    allowDrop(draggingNode, dropNode, type) {
      return dropNode.level !== 1
    },
    allowDrag(draggingNode) {
      return draggingNode.level !== 1
    },
    handleAddNode(node) {
      console.log(node)
    },
    // 模板属性方法
    isTypeShow(row, type) {
      return !this.lock && row.attrType === type || row.lastType === type
    },
    attrTypeChange(type, index) {
      this.selectIndex = index
      if (['single', 'multi'].indexOf(type) !== -1) {
        const typeMap = { 'single': '单选', 'multi': '多选' }
        this.reset()
        this.form.globalProps[index].defaultValue = ''
        this.title = `录入${typeMap[type]}选项属性`
        this.open.item = true
      } else if (type === 'number') {
        if (isNaN(+this.form.globalProps[index].defaultValue)) {
          this.form.globalProps[index].defaultValue = ''
        } else {
          this.form.globalProps[index].defaultValue = +this.form.globalProps[index].defaultValue
        }
      } else if (type === 'boolean') {
        this.form.globalProps[index].defaultValue = Boolean(this.form.globalProps[index].defaultValue)
      }
      this.lock = false
      this.form.globalProps[index].lastType = type
    },
    handleAddProp() {
      const item = {
        attrName: undefined,
        attrVar: undefined,
        attrType: 'text',
        defaultValue: undefined,
        items: [],
        description: undefined,
        notNull: false,
        configKey: true,
        lastType: 'text'
      }
      this.form.globalProps.push(item)
    },
    handleAddItem() {
      const item = {
        name: undefined,
        value: undefined,
        description: undefined
      }
      this.itemList.push(item)
    },
    handleDelItem() {
      const index = this.selectList.item
      this.$modal.confirm('是否确认删除序号为"' + index.map(i => i + 1) + '"的属性选项？').then(() => {
        batchDelete(this.itemList, index)
      }).catch(() => {})
    },
    handleDelProp() {
      const index = this.selectList.prop
      this.$modal.confirm('是否确认删除序号为"' + index.map(i => i + 1) + '"的属性项？').then(() => {
        batchDelete(this.form.globalProps, index)
      }).catch(() => {})
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      const selectList = []
      for (const item of selection) {
        selectList.push(this.form.globalProps.indexOf(item))
      }
      this.selectList.prop = selectList
      this.multiple.prop = !selection.length
    },
    // 多选框选中数据
    handleItemSelectionChange(selection) {
      const selectList = []
      for (const item of selection) {
        selectList.push(this.itemList.indexOf(item))
      }
      this.selectList.item = selectList
      this.multiple.item = !selection.length
    },
    submitItem() {
      const values = this.itemList.filter(item => item.value).map(item => item.value)
      if (!values || values.length === 0) {
        this.$modal.notifyWarning('请输入有效的选项值!')
        return
      }
      if (checkDuplicates(values)) {
        this.$modal.notifyWarning('不允许存在重复的选项值!')
        return
      }
      this.form.globalProps[this.selectIndex].items = this.itemList.filter(item => {
        return item.name && item.value
      })
      this.open.item = false
    },
    cancel() {
      this.open.item = false
      this.reset()
    },
    reset() {
      this.itemList = deepClone(this.form.globalProps[this.selectIndex].items)
    }
  }
}
</script>

<style lang="scss" scoped>
.build-wrapper {
  height: 100%;
  .nav-wrapper {
    height: 100%;
    float: left;
    position: relative;
    z-index: 1;
    border-right: 1px solid #eee;
    box-shadow: 2px 0 6px #eeeef9;
    ul.nav {
      width: 30px;
      height: 100%;
      background-color: #fff;
      li {
        cursor: pointer;
        line-height: 30px;
        width: 100%;
        padding: 20px 0;
        writing-mode: tb;
        list-style: none;
        letter-spacing: 5px;
        font-size: 16px;
        border-bottom: 1px solid #dcdfe6;
        &:last-child {
          border-bottom: 0;
        }
        &.active {
          background-color: #ecf5ff;
          color: #409eff;
        }
      }
    }
  }
  .content-layout {
    overflow: hidden;
    height: 100%;
    position: relative;
    .content {
      position: absolute;
      height: 100%;
      width: 100%;
      transition: transform .3s ease;
      transform: translateX(-3000px);
      &.active {
        transform: translateX(0);
      }
      ::v-deep {
        .left-pane {
          box-shadow: 2px 0 5px #eee;
        }
        .right-pane {
          padding-left: 6px;
        }
      }
    }
  }
}

.tplInfo {
  .title {
    font-weight: bold;
  }
  .upload {
    ::v-deep .el-upload-list__item {
      & > div {
        width: 100%;
        height: 100%;
      }
    }
  }
}

.tplAttr .table {
  ::v-deep .el-input-number {
    width: 100%;
  }
}
.tplCode {
  .el-tree {
    height: calc(100% - 42px);
    width: 100%;
    overflow-x: auto;
    ::v-deep .el-tree-node {
      .el-tree-node__children{
        overflow: visible;
      }
    }
  }
  .filter-container {
    padding: 4px 8px;
    display: flex;

    ::v-deep {
      .el-input {
        margin-right: 4px;
        flex-grow: 1;
      }
      .el-input__inner {
        height: 34px;
        line-height: 34px;
      }
    }
  }
  .editor-wrapper {
    height: 100%;
    width: 100%;
    overflow: auto;
  }
  ::v-deep .el-tree-node__content {
    position: relative;
  }
  .tree-node {
    display: flex;
    flex: 1;
    padding-right: 8px;
    align-items: center;
    justify-content: space-between;
    .tree-node-icon {
      height: 16px;
      width: 16px;
      margin-right: 4px;
    }
    .tree-node-opera {
      display: none;
    }
    &:hover .tree-node-opera {
      display: inline;
    }
    .tree-node-menu {
      position: absolute;
      top: 26px;
      right: 8px;
      border: 1px solid #ebeef5;
      background: #fff;
      box-shadow: 0 0 2px rgba(0, 0, 0, 0.1);
      width: 160px;
      z-index: 1;
      overflow: hidden;
      border-radius: 5px;
      display: none;
      padding: 4px 0;
      ul {
        list-style: none;
        li {
          padding: 0 12px;
          padding: 6px 12px;
          &:hover {
            background-color: #f5f7fa;
          }
        }
        li.divide-b {
          border-bottom: 1px solid #eee;
        }
        li.divide-t {
          border-top: 1px solid #eee;
        }
      }
    }
  }
}
</style>
