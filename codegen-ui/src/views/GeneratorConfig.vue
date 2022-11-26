<template>
  <div>
    <Split v-model="split">
      <template #left>
        asdfas
      </template>
      <template #right>
        asdfa
      </template>
    </Split>
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
