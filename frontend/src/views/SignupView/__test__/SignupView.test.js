import { render } from "@testing-library/react"
import { SignupView } from "views"
import { BrowserRouter as Router } from "react-router-dom"
import { GlobalProvider } from "store"

describe("SignupView", () => {
  it("renders", () => {
    const { getByTestId } = render(<GlobalProvider><Router><SignupView /></Router></GlobalProvider>)
    const component = getByTestId("signup-view")
    expect(component).toBeInTheDocument()
  })
})