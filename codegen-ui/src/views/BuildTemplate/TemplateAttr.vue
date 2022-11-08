<template>
  <div>
    <el-row class="toolbar clear" :gutter="10" style="margin-bottom: 5px;">
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
        <template slot-scope="{row}">
          <el-select v-model="row.attrType" placeholder="请选择输入类型">
            <el-option v-for="item in attrItemTypeList" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="属性默认值">
        <template slot-scope="{row}">
          <el-input v-if="row.attrType == 'text'" :v-model="row.defaultValue" placeholder="无默认值" />
          <el-input v-if="row.attrType == 'textarea'" :v-model="row.defaultValue" placeholder="无默认值" />
          <el-select v-if="row.attrType == 'radio'" v-model="row.defaultValue" placeholder="无默认值">
            <el-option v-for="item in attrItemTypeList" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
          <el-select v-if="row.attrType == 'checkbox'" v-model="row.defaultValue" multiple placeholder="无默认值">
            <el-option v-for="item in attrItemTypeList" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
          <el-input-number v-if="row.attrType == 'number'" v-model="row.defaultValue" controls-position="right" />
          <el-select v-if="row.attrType == 'boolean'" v-model="row.defaultValue" placeholder="无默认值">
            <el-option v-for="item in booleanList" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column property="introduce" label="属性说明">
        <template slot-scope="{row}">
          <el-input v-model="row.introduce" type="text" placeholder="请输入属性说明" />
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

    <!-- <vxe-table
      class="table"
      resizable
      :align="'center'"
      :data="tableData"
      :scroll-y="{enabled: false}"
      :edit-config="{trigger: 'click', mode: 'row'}"
    >
      <vxe-column type="checkbox" width="60" />
      <vxe-column field="attrName" title="属性名称" :edit-render="{autofocus: '.vxe-input--inner'}">
        <template #default="{ row }">
          <el-input v-model="row.attrName" type="text" placeholder="请输入属性名称" />
        </template>
        <template #edit="{ row }">
          <el-input v-model="row.attrName" type="text" placeholder="请输入属性名称" />
        </template>
      </vxe-column>
      <vxe-column field="attrVar" title="属性变量" :edit-render="{autofocus: '.vxe-input--inner'}">
        <template #default="{ row }">
          <el-input v-model="row.attrVar" type="text" placeholder="请输入属性变量" />
        </template>
        <template #edit="{ row }">
          <el-input v-model="row.attrVar" type="text" placeholder="请输入属性变量" />
        </template>
      </vxe-column>
      <vxe-column field="attrType" title="输入类型" :edit-render="{}">
        <template #default="{ row }">
          <el-select v-model="row.attrType" placeholder="请选择输入类型">
            <el-option v-for="item in attrItemTypeList" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </template>
        <template #edit="{ row }">
          <el-select v-model="row.attrType" placeholder="请选择输入类型">
            <el-option v-for="item in attrItemTypeList" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </template>
      </vxe-column>
      <vxe-column title="属性默认值" :edit-render="{}">
        <template #default="{ row }">
          <el-input v-if="row.attrType == 'text'" :value="row.defaultValue" placeholder="无默认值" />
          <el-input v-if="row.attrType == 'textarea'" :value="row.defaultValue" placeholder="无默认值" />
          <el-select v-if="row.attrType == 'radio'" :value="row.defaultValue" placeholder="无默认值">
            <el-option v-for="item in attrItemTypeList" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
          <el-select v-if="row.attrType == 'checkbox'" :value="row.defaultValue" multiple placeholder="无默认值">
            <el-option v-for="item in attrItemTypeList" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
          <el-input-number v-if="row.attrType == 'number'" :value="row.defaultValue" controls-position="right" />
          <el-select v-if="row.attrType == 'boolean'" :value="row.defaultValue" placeholder="无默认值">
            <el-option v-for="item in booleanList" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </template>
        <template #edit="{ row }">
          <el-input v-if="row.attrType == 'text'" :v-model="row.defaultValue" placeholder="无默认值" />
          <el-input v-if="row.attrType == 'textarea'" :v-model="row.defaultValue" placeholder="无默认值" />
          <el-select v-if="row.attrType == 'radio'" v-model="row.defaultValue" placeholder="无默认值">
            <el-option v-for="item in attrItemTypeList" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
          <el-select v-if="row.attrType == 'checkbox'" v-model="row.defaultValue" multiple placeholder="无默认值">
            <el-option v-for="item in attrItemTypeList" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
          <el-input-number v-if="row.attrType == 'number'" v-model="row.defaultValue" controls-position="right" />
          <el-select v-if="row.attrType == 'boolean'" v-model="row.defaultValue" placeholder="无默认值">
            <el-option v-for="item in booleanList" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </template>
      </vxe-column>
      <vxe-column field="introduce" title="属性说明" :edit-render="{autofocus: '.vxe-input--inner'}">
        <template #default="{ row }">
          <el-input v-model="row.introduce" type="text" placeholder="请输入属性说明" />
        </template>
        <template #edit="{ row }">
          <el-input v-model="row.introduce" type="text" placeholder="请输入属性说明" />
        </template>
      </vxe-column>
      <vxe-column field="notNull" title="必填" :edit-render="{}">
        <template #default="{ row }">
          <el-checkbox :value="row.notNull" />
        </template>
        <template #edit="{ row }">
          <el-checkbox v-model="row.notNull" />
        </template>
      </vxe-column>
      <vxe-column field="configKey" title="配置键" :edit-render="{}">
        <template #default="{ row }">
          <el-checkbox :value="row.configKey" />
        </template>
        <template #edit="{ row }">
          <el-checkbox v-model="row.configKey" />
        </template>
      </vxe-column>
    </vxe-table> -->
  </div>
</template>

<script>
export default {
  name: 'TemplateAttr',
  data() {
    return {
      multiple: false,
      tableData: [
        {
          attrType: 'text',
          defaultValue: undefined
        }
      ],
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
    handleDelete() {
      console.log(this.tableData)
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
