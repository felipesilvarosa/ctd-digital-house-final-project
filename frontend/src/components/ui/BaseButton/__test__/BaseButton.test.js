import { render } from "@testing-library/react"
import { BaseButton } from "components"

describe("BaseButton", () => {
  it("renders", () => {
    const { getByRole } = render(<BaseButton>Botão</BaseButton>)
    const component = getByRole("button")
    expect(component).toBeInTheDocument()
  })
})