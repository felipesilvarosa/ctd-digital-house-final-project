import { render } from "@testing-library/react"
import { BrowserRouter as Router } from "react-router-dom"
import { TheScaffold } from "src/components"
import { GlobalProvider } from "src/store"

describe("TheScaffold", () => {

  it("renders the header", () => {
    const { queryByTestId } = render(<GlobalProvider><Router><TheScaffold /></Router></GlobalProvider>)
    const header = queryByTestId("the-header")
    expect(header).toBeInTheDocument()
  })
  
  it("renders the main area", () => {
    const { queryByTestId } = render(<GlobalProvider><Router><TheScaffold /></Router></GlobalProvider>)
    const main = queryByTestId("main")
    expect(main).toBeInTheDocument()
  })
  
  it("renders the footer", () => {
    const { queryByTestId } = render(<GlobalProvider><Router><TheScaffold /></Router></GlobalProvider>)
    const footer = queryByTestId("the-footer")
    expect(footer).toBeInTheDocument()
  })
})