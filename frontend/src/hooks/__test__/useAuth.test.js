import { useAuth } from "src/hooks"

describe("useAuth", () => {
  it("throws an error when called outside of a context", () => {
    expect(() => {
      useAuth()
    }).toThrowError()
  })
})