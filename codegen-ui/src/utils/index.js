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
