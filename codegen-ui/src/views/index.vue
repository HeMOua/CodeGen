<template>
  <div>
    <span v-for="i in 5" :key="i" ref="box" class="box" @click="test">{{ i }}</span>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Index',
  data() {
    return {}
  },
  created() {
  },
  methods: {
    test() {
      console.log(this.$box)
    }
  }
}
</script>

<style lang="scss" scoped>
.box {
  padding: 20px;
  height: 20px;
  box-sizing: border-box;
}
</style>
