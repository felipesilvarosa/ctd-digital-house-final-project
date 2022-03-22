import { render } from "@testing-library/react"
import { DestinationButton } from "src/components"

describe("DestinationButton", () => {
  it("renders", () => {
    const { getByRole } = render(<DestinationButton />)
    const component = getByRole("button")
    expect(component).toBeInTheDocument()
  })

  it("displays the city and the country", () => {
    const { queryByText } = render(<DestinationButton city="Cidade" country="País" />)
    const city = queryByText("Cidade")
    const country = queryByText("País")
    expect(city).toBeInTheDocument()
    expect(country).toBeInTheDocument()
  })
})