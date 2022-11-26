<template>
  <div class="pr-20">
    <el-row class="toolbar clear mb-10" :gutter="10">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAddRow"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table
      ref="table"
      class="table"
      :data="tableData"
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
          <el-select v-model="scope.row.attrType" placeholder="请选择输入类型" @change="attrTypeChange($event, scope.$index)" @focus="attrTypeFocus">
            <el-option v-for="item in attrItemTypeList" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="属性默认值">
        <template slot-scope="{row}">
          <el-input v-if="canShow(row, 'text')" v-model="row.defaultValue" placeholder="无默认值" />
          <el-input v-else-if="canShow(row, 'textarea')" v-model="row.defaultValue" placeholder="无默认值" />
          <el-select v-else-if="canShow(row, 'single')" v-model="row.defaultValue" placeholder="无默认值">
            <el-option v-for="item in row.items" :key="item.value" :value="item.value" :label="item.name" />
          </el-select>
          <el-select v-else-if="canShow(row, 'multi')" v-model="row.defaultValue" multiple placeholder="无默认值">
            <el-option v-for="item in row.items" :key="item.value" :value="item.value" :label="item.name" />
          </el-select>
          <el-input-number v-else-if="canShow(row, 'number')" v-model="row.defaultValue" controls-position="right" />
          <el-select v-else-if="canShow(row, 'boolean')" v-model="row.defaultValue" placeholder="无默认值" no-match-text="无默认值">
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

    <!-- 添加属性选项对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-row class="toolbar clear" :gutter="10" style="margin-bottom: 5px;">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAddItemRow"
          >新增选项</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="attrMultiple"
            @click="handleDeleteItemRow"
          >删除选项</el-button>
        </el-col>
      </el-row>
      <el-table
        ref="table"
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
import { deepClone, batchDelete, checkDuplicates } from '@/utils'

export default {
  name: 'TemplateAttr',
  data() {
    return {
      lock: false,
      selectIndex: -1,
      selectList: [],
      itemSelectList: [],
      title: '',
      open: false,
      multiple: true,
      attrMultiple: true,
      rules: {
        attrName: [
          { required: true, message: '属性名称不能为空', trigger: 'blur' }
        ],
        attrVar: [
          { required: true, message: '数形变量不能为空', trigger: 'blur' }
        ]
      },
      tableData: [],
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
      ],
      itemList: undefined
    }
  },
  methods: {
    canShow(row, type) {
      return !this.lock && row.attrType === type || row.lastType === type
    },
    attrTypeFocus() {
      this.lock = true
    },
    attrTypeChange(type, index) {
      this.selectIndex = index
      if (['single', 'multi'].indexOf(type) !== -1) {
        const typeMap = { 'single': '单选', 'multi': '多选' }
        this.reset()
        this.tableData[index].defaultValue = ''
        this.title = `录入${typeMap[type]}选项属性`
        this.open = true
      } else if (type === 'number') {
        if (isNaN(+this.tableData[index].defaultValue)) {
          this.tableData[index].defaultValue = ''
        } else {
          this.tableData[index].defaultValue = +this.tableData[index].defaultValue
        }
      } else if (type === 'boolean') {
        this.tableData[index].defaultValue = Boolean(this.tableData[index].defaultValue)
      }
      this.lock = false
      this.tableData[index].lastType = type
    },
    handleAddRow() {
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
      this.tableData.push(item)
    },
    handleAddItemRow() {
      const item = {
        name: undefined,
        value: undefined,
        description: undefined
      }
      this.itemList.push(item)
    },
    handleDeleteItemRow() {
      const index = this.itemSelectList
      this.$modal.confirm('是否确认删除序号为"' + index.map(i => i + 1) + '"的属性选项？').then(() => {
        batchDelete(this.itemList, index)
      }).catch(() => {})
    },
    handleDelete() {
      const index = this.selectList
      this.$modal.confirm('是否确认删除序号为"' + index.map(i => i + 1) + '"的属性项？').then(() => {
        batchDelete(this.tableData, index)
      }).catch(() => {})
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      const selectList = []
      for (const item of selection) {
        selectList.push(this.tableData.indexOf(item))
      }
      this.selectList = selectList
      this.multiple = !selection.length
    },
    // 多选框选中数据
    handleItemSelectionChange(selection) {
      const selectList = []
      for (const item of selection) {
        selectList.push(this.tableData.indexOf(item))
      }
      this.itemSelectList = selectList
      this.attrMultiple = !selection.length
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
      this.tableData[this.selectIndex].items = this.itemList.filter(item => {
        return item.name && item.value
      })
      this.open = false
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.itemList = deepClone(this.tableData[this.selectIndex].items)
    }
  }
}
</script>

<style lang="scss" scoped>
.table {
  ::v-deep .el-input-number {
    width: 100%;
  }
}
</style>
