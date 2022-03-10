import { render } from "@testing-library/react"
import { LoginView } from "views"
import { BrowserRouter as Router } from "react-router-dom"
import { GlobalProvider } from "store"

describe("LoginView", () => {
  it("renders", () => {
    const { getByTestId } = render(<GlobalProvider><Router><LoginView /></Router></GlobalProvider>)
    const component = getByTestId("login-view")
    expect(component).toBeInTheDocument()
  })
})