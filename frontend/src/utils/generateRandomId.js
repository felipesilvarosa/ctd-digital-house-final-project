export const generateRandomId = (length = 32) => {
  const chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
  let str = chars[Math.floor(Math.random() * (chars.length - 10))]
  for (let i = 0; i < length; i++) {
    str = str + chars[Math.floor(Math.random() * chars.length)]
  }
  return str
}