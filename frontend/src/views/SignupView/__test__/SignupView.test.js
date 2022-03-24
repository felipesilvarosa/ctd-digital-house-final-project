import { render } from "@testing-library/react"
import { SignupView } from "src/views"
import { BrowserRouter as Router } from "react-router-dom"
import { GlobalProvider } from "src/store"

describe("SignupView", () => {
  it("renders", () => {
    const { getByTestId } = render(<GlobalProvider><Router><SignupView /></Router></GlobalProvider>)
    const component = getByTestId("signup-view")
    expect(component).toBeInTheDocument()
  })
})