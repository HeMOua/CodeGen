export function arrayRemove(it, arr) {
  if (!arr || arr.length === 0) {
    return ''
  }
  const flag = arr.indexOf(it)
  if (flag > -1) {
    arr.splice(flag, 1)
    return arr
  }
}

// 回显数据字典
export function selectDictLabel(datas, value) {
  if (value === undefined) {
    return ''
  }
  var actions = []
  Object.keys(datas).some((key) => {
    if (datas[key].value === ('' + value)) {
      actions.push(datas[key].label)
      return true
    }
  })
  if (actions.length === 0) {
    actions.push(value)
  }
  return actions.join('')
}

// 表单重置
export function resetForm(refName) {
  if (this.$refs[refName]) {
    this.$refs[refName].resetFields()
  }
}

/**
 * This is just a simple version of deep copy
 * Has a lot of edge cases bug
 * If you want to use a perfect deep copy, use lodash's _.cloneDeep
 * @param {Object} source
 * @returns {Object}
 */
export function deepClone(source) {
  if (!source && typeof source !== 'object') {
    throw new Error('error arguments', 'deepClone')
  }
  const targetObj = source.constructor === Array ? [] : {}
  Object.keys(source).forEach(keys => {
    if (source[keys] && typeof source[keys] === 'object') {
      targetObj[keys] = deepClone(source[keys])
    } else {
      targetObj[keys] = source[keys]
    }
  })
  return targetObj
}

// 根据索引批量删除元素
export function batchDelete(dataList, indexs) {
  const reverseIndex = indexs.sort().reverse()
  for (const index of reverseIndex) {
    dataList.splice(index, 1)
  }
}

// 判断数据是否有重复值
export function checkDuplicates(array) {
  return new Set(array).size !== array.length
}
