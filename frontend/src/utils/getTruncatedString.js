export const getTruncatedString = (str, maxLen = 80) => {
  return str.length <= maxLen ? str : str.match(new RegExp(`^.{${maxLen}}\\w*`))
}