import { makeInitials } from "src/utils"

describe("makeInitials", () => {
  it("returns JS if input is João da Silva", () => {
    expect(makeInitials("João da Silva")).toBe("JS")
  })

  it("returns ?? if input is invalid", () => {
    expect(makeInitials(null)).toBe("??")
    expect(makeInitials([])).toBe("??")
    expect(makeInitials(123)).toBe("??")
    expect(makeInitials({})).toBe("??")
  })
})