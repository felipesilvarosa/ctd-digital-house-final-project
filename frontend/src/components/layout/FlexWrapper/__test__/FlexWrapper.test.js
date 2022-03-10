import { render } from "@testing-library/react"
import { FlexWrapper } from "components"

describe("FlexWrapper", () => {
  it("renders", () => {
    const { getByTestId } = render(<FlexWrapper data-testid="component"></FlexWrapper>)
    const component = getByTestId("component")
    expect(component).toBeInTheDocument()
  })
})