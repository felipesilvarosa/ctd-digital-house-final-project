import { render } from "@testing-library/react"
import { DropDownMenu } from "src/components"

describe("DropDownMenu", () => {
  it("renders", () => {
    const { getByRole } = render(<DropDownMenu toggle={true}><li></li></DropDownMenu>)
    const component = getByRole("list")
    expect(component).toBeInTheDocument()
  })

  it("doesn't render if not toggled", () => {
    const { queryByRole } = render(<DropDownMenu><li></li></DropDownMenu>)
    expect(queryByRole("list")).toBeNull()
  })
})