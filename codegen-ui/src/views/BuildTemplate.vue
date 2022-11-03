<template>
  <div>
    <el-tabs tab-position="left">
      <el-tab-pane label="模板信息"><TemplateInfo /></el-tab-pane>
      <el-tab-pane label="属性">Config</el-tab-pane>
      <el-tab-pane label="代码">Role</el-tab-pane>
      <el-tab-pane label="使用文档">Task</el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import TemplateInfo from './template/TemplateInfo.vue'

export default {
  name: 'BuildTemplate',
  components: {
    TemplateInfo
  }
}
</script>

<style lang="scss" scoped>
</style>
