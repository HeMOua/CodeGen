<template>
  <div class="app-wrapper">
    <header>
      <router-link>
        <img :src="require('@/assets/images/logo.png')" alt="">
      </router-link>
    </header>
    <main>
      <router-view />
    </main>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Layout'
}
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variable.scss';
header {
  padding: 4px 30px;
  height: $header-height;
  box-sizing: border-box;
  box-shadow: 0 2px 2px #eee;
  img {
    height: calc($header-height - 8px);
  }
}
main {
  height: calc(100vh - $header-height);
}
</style>
