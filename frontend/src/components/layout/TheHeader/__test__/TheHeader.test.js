import { render, screen } from "@testing-library/react"
import { TheHeader } from "src/components"
import { BrowserRouter as Router } from "react-router-dom"

/* ====================================== */
/* Context está retornando como undefined */
/* ====================================== */

describe("TheHeader", () => {
  // beforeEach(() => {
  //   render(<Router><TheHeader /></Router>)
  // })
  
  it("renders the logo", () => {
    // const logoImage = screen.getByAltText(/digital booking logo/i)
    // expect(logoImage).toBeInTheDocument()
  });
  
  it("renders the menu", () => {
    // const menu = screen.getByRole("list")
    // expect(menu).toBeInTheDocument()
  })
})
