<template>
  <div>
    <el-input-number v-model="num" @change="test" />
  </div>
</template>

<script>
export default {
  name: 'GeneratorConfig',
  data() {
    return {
      num: 'sd'
    }
  },
  methods: {
    test(val) {
      console.log(val)
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
