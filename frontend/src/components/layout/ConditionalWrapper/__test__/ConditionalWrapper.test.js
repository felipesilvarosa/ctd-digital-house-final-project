import { render } from "@testing-library/react"
import { ConditionalWrapper } from "src/components"

const TestWrapper = ({children}) => {
  return <div data-testid="wrapper">{children}</div>
}

describe("ConditionalWrapper", () => {
  it("renders if the condition is true", () => {
    const { getByTestId } = render(<ConditionalWrapper condition={true} wrapper={TestWrapper}><div data-testid="content">Teste</div></ConditionalWrapper>)
    const wrapper = getByTestId("wrapper")
    expect(wrapper).toBeInTheDocument()
  })

  it("doesn't render the wrapper if condition is false", () => {
    const { queryByTestId } = render(<ConditionalWrapper condition={false} wrapper={TestWrapper}><div data-testid="content">Teste</div></ConditionalWrapper>)
    expect(queryByTestId("wrapper")).toBeNull()
    expect(queryByTestId("content")).toBeInTheDocument()
  })

  it("renders the fallback if condition is false and a fallback is provided", () => {
    const { queryByTestId } = render(<ConditionalWrapper condition={false} wrapper={TestWrapper} fallback={children => <div data-testid="fallback">{children}</div>}>Teste</ConditionalWrapper>)
    expect(queryByTestId("wrapper")).toBeNull()
    expect(queryByTestId("fallback")).toBeInTheDocument()
  })
})