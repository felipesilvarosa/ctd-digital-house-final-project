import { render, screen } from "@testing-library/react"
import { UserAvatar } from "components"

describe("UserAvatar", () => {
  it("renders the correct initials", () => {
    render(<UserAvatar fullName="João da Silva" />)
    const initials = screen.getByText("JS")
    expect(initials).toBeInTheDocument()
  });
  
  it("renders ?? if user's name is invalid", () => {
    render(<UserAvatar fullName={12} />)
    const initials = screen.getByText("??")
    expect(initials).toBeInTheDocument()
  })
})