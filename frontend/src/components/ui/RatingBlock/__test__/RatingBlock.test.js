import { render } from "@testing-library/react"
import { RatingBlock } from "src/components"

describe("RatingBlock", () => {
  it("shows the correct rating and text value", () => {
    const { getByText } = render(<RatingBlock rating={5} />)
    expect(getByText("5")).toBeInTheDocument()
    expect(getByText(/mediano/i)).toBeInTheDocument()
  })

  it("shows fallback when rating is not passed", () => {
    const { getByText } = render(<RatingBlock />)
    expect(getByText("-")).toBeInTheDocument()
    expect(getByText(/sem avaliação/i)).toBeInTheDocument()
  })
})