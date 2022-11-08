<template>
  <div>
    <el-row class="toolbar clear" :gutter="10" style="margin-bottom: 5px;">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
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
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column property="attrName" label="属性名称" />
      <el-table-column property="attrVar" label="属性变量" />
      <el-table-column property="attrType" label="输入类型" />
      <el-table-column property="defaultValue" label="属性默认值" />
      <el-table-column property="introduce" label="属性说明" />
      <el-table-column property="notNull" label="必填" width="90" align="center" />
      <el-table-column property="configKey" label="配置键" width="90" align="center" />
    </el-table>

    <!-- 添加或修改菜单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="属性名称" prop="attrName">
              <el-input v-model="form.attrName" type="text" placeholder="请输入属性名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="属性变量" prop="attrVar">
              <el-input v-model="form.attrVar" type="text" placeholder="请输入属性变量" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入类型" prop="attrType">
              <el-select v-model="form.attrType" placeholder="请选择输入类型">
                <el-option v-for="item in attrItemTypeList" :key="item.value" :value="item.value" :label="item.label" @change="typeChange" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="属性默认值" prop="defaultValue">
              <el-input v-if="form.attrType == 'text'" v-model="form.defaultValue" placeholder="无默认值" />
              <el-input v-else-if="form.attrType == 'textarea'" v-model="form.defaultValue" placeholder="无默认值" />
              <el-select v-else-if="form.attrType == 'radio'" v-model="form.radioDefaultValue" placeholder="无默认值">
                <el-option v-for="item in attrItemTypeList" :key="'a' + item.value" :value="item.value" :label="item.label" />
              </el-select>
              <el-select v-else-if="form.attrType == 'checkbox'" v-model="form.checkboxDefaultValue" multiple placeholder="无默认值">
                <el-option v-for="item in attrItemTypeList" :key="'b' + item.value" :value="item.value" :label="item.label" />
              </el-select>
              <el-input-number v-else-if="form.attrType == 'number'" v-model="form.numberDefaultValue" controls-position="right" />
              <el-select v-else-if="form.attrType == 'boolean'" v-model="form.boolDefaultValue" placeholder="无默认值">
                <el-option v-for="item in booleanList" :key="item.value" :value="item.value" :label="item.label" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="必填" prop="notNull">
              <el-switch v-model="form.notNull" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="配置键" prop="configKey">
              <el-switch v-model="form.configKey" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAdd">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'TemplateAttr',
  data() {
    return {
      title: '',
      open: false,
      multiple: true,
      form: {},
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
        { label: '单选', value: 'radio' },
        { label: '多选', value: 'checkbox' },
        { label: '整数', value: 'number' },
        { label: '布尔', value: 'boolean' }
      ],
      booleanList: [
        { label: '是', value: true },
        { label: '否', value: false }
      ]
    }
  },
  methods: {
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加模板属性'
    },
    handleDelete() {
      console.log(this.tableData)
    },
    typeChange() {
      this.form.defaultValue = undefined
    },
    submitAdd() {

    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        attrName: undefined,
        attrVar: undefined,
        attrType: 'text',
        defaultValue: undefined,
        textDefaultValue: undefined,
        textareaDefaultValue: undefined,
        radioDefaultValue: undefined,
        checkboxDefaultValue: undefined,
        numberDefaultValue: undefined,
        boolDefaultValue: undefined,
        introduce: undefined,
        notNull: false,
        configKey: true
      }
      this.resetForm('form')
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
