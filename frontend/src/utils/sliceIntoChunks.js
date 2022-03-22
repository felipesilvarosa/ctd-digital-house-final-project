export const sliceIntoChunks = (arr, numberOfChunks) => {
  if (!arr.length) return
  
  const res = Array(numberOfChunks).fill("").map(() => [])

  for (let i = 0; i < arr.length; i++) {
    res[i%numberOfChunks].push(arr[i])
  }
  return res;
}