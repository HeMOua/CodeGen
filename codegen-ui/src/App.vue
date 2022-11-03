<template>
  <div>
    <div class="header">
      <Breadcrumb class="breadcrumb-container" />
      <router-link class="item" to="/templateConfig">模板配置</router-link>
      <router-link class="item" to="/generatorConfig">代码生成</router-link>
    </div>
    <div class="app-container">
      <router-view />
    </div>
  </div>
</template>
<script>
import Breadcrumb from '@/components/Breadcrumb'
export default {
  components: { Breadcrumb },
  data() {
    return {}
  }
}
</script>
<style lang="scss" scoped>
.header {
  position: relative;
  display: flex;
  justify-content: center;
  .breadcrumb-container {
    position: absolute;
    top: 30%;
    left: 50px;
  }
  & .item {
    font-size: 28px;
    padding: 20px;
    &.router-link-active::after {
      content: '';
      margin-top: 6px;
      display: block;
      background-color: #409EFF;
      height: 3px;
      border-radius: 3px;
    }
  }
}
</style>
