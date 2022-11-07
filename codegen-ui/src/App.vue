<template>
  <div>
    <div class="header">
      <router-link class="item" :class="{active: isActive}" to="/templateConfig">模板配置</router-link>
      <router-link class="item" to="/generatorConfig">代码生成</router-link>
    </div>
    <div class="app-container">
      <Breadcrumb class="breadcrumb-container" />
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
  },
  computed: {
    isActive() {
      return this.$route.path === '/buildTemplate'
    }
  }
}
</script>
<style lang="scss" scoped>
.header {
  position: relative;
  display: flex;
  justify-content: center;
  & .item {
    font-size: 28px;
    padding: 20px 20px 0;
    &.router-link-active::after, &.active::after {
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
