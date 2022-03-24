import { render, waitFor } from "@testing-library/react"
import event from "@testing-library/user-event"
import { HomeSearchBar } from "src/components"
import { act } from "react-dom/test-utils"

describe("HomeSearchBar", () => {
  it("renders", () => {
    const { getByTestId } = render(<HomeSearchBar data-testid="component" />)
    const component = getByTestId("component")
    expect(component).toBeInTheDocument()
  })

  it("shows the destination drop-down when the destination input is focused", () => {
    const { getByTestId } = render(<HomeSearchBar />)
    const field = getByTestId("destination-field")
    act(() => {
      event.click(field)
    })
    expect(getByTestId("destination-dropdown")).toBeInTheDocument()
  })

  it("shows the destination in the destination input field when it is clicked", () => {
    const { getByTestId, getByText } = render(<HomeSearchBar />)
    const field = getByTestId("destination-field")
    act(() => {
      event.click(field)
    })
    waitFor(() => {
      const button = getByText(/porto alegre/i)
      act(() => {
        event.click(button)
      })
      expect(field).toHaveProperty("value", "Porto Alegre, Brasil")
    })
  })
})