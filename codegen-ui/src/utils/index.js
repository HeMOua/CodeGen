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
