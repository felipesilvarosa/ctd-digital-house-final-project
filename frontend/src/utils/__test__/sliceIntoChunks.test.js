import { sliceIntoChunks } from "src/utils"

describe("sliceIntoChunks", () => {
  it("returns [[1, 4, 7], [2, 5], [3, 6]] when the function receives [1, 2, 3, 4, 5, 6, 7]", () => {
    expect(sliceIntoChunks([1, 2, 3, 4, 5, 6, 7], 3)).toMatchObject([[1, 4, 7], [2, 5], [3, 6]])
  })
})