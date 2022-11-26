<template>
  <div class="build-container">
    <div class="nav center">已编辑</div>
    <el-button-group class="toolbar">
      <el-button icon="el-icon-caret-right">预览</el-button>
      <el-button icon="el-icon-s-promotion">提交</el-button>
    </el-button-group>
    <el-tabs tab-position="left" class="ml-10">
      <el-tab-pane label="模板信息"><TemplateInfo /></el-tab-pane>
      <el-tab-pane label="属性"><TemplateAttr /></el-tab-pane>
      <el-tab-pane label="代码"><TemplateCode /></el-tab-pane>
      <el-tab-pane label="使用文档">Task</el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import TemplateInfo from './TemplateInfo.vue'
import TemplateAttr from './TemplateAttr.vue'
import TemplateCode from './TemplateCode.vue'

export default {
  name: 'BuildTemplate',
  components: {
    TemplateInfo, TemplateAttr, TemplateCode
  }
}
</script>

<style lang="scss" scoped>
.build-container {
  position: relative;
  .nav {
    width: 100%;
    position: absolute;
    top: -40px;
    z-index: -1;
  }
  .toolbar {
    position: absolute;
    right: 20px;
    top: -45px;
    ::v-deep .el-button {
      padding: 8px 15px;
    }
  }
}
.el-tabs {
  ::v-deep .el-tabs__item {
    padding: 10px 10px 10px 0;
    width: 24px;
    height: unset;
    line-height: unset;
  }
  ::v-deep .el-tabs__nav {
    word-wrap: break-word;
    white-space: unset;
  }
}
</style>
