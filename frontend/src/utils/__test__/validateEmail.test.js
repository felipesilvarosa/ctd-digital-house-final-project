import { validateEmail } from "src/utils"

describe("validateEmail", () => {
  it("expects to be falsy if input is joaomariajose", () => {
    expect(validateEmail("joaomariajose")).toBeFalsy()
  })
  it("expects to be falsy if input is joaomariajose@joao", () => {
    expect(validateEmail("joaomariajose@joao")).toBeFalsy()
  })
  it("expects to be defined if input is joaomariajose@joao.com", () => {
    expect(validateEmail("joaomariajose@joao.com")).toBeDefined()
  })

})