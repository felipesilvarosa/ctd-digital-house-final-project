import { TheFooter } from "src/components"
import { render } from "@testing-library/react"

describe("TheFooter", () => {
  it("renders", () => {
    const { queryByTestId } = render(<TheFooter />)
    const component = queryByTestId("the-footer")
    expect(component).toBeInTheDocument()
  })
})