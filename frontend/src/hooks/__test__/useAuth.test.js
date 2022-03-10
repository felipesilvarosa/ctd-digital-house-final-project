import { useAuth } from "hooks"

describe("useAuth", () => {
  it("throws an error when called outside of a context", () => {
    expect(() => {
      useAuth()
    }).toThrowError()
  })
})