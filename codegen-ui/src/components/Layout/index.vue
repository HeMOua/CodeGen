<template>
  <div>
    <header>
      <router-link to="/">
        <img :src="require('@/assets/images/logo.png')" alt="">
      </router-link>
      <Breadcrumb />
      <div class="right">
        <el-button type="primary" plain @click="handleConfig">模板配置</el-button>
      </div>
    </header>
    <main>
      <router-view />
    </main>
  </div>
</template>

<script>
import Breadcrumb from '@/components/Breadcrumb'

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Layout',
  components: {
    Breadcrumb
  },
  methods: {
    handleConfig() {
      this.$router.push('/config/index')
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variable.scss';
header {
  background-color: #fff;
  padding: $padding-v 30px;
  height: $header-height;
  width: 100%;
  box-sizing: border-box;
  box-shadow: 0 2px 2px #eee;
  position: fixed;
  top: 0;
  z-index: 5;
  img {
    height: calc($header-height - 8px);
  }
}
main {
  margin-top: $header-height;
  height: calc(100vh - $header-height);
  position: relative;
}
</style>
