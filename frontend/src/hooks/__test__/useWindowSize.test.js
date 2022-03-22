import { render, fireEvent } from "@testing-library/react";
import { useWindowSize } from "src/hooks";

const TestComponent = () => {
  const windowSize = useWindowSize();
  return (
    <>
      <h3 data-testid="window-width">{windowSize.width}</h3>
      <h3 data-testid="window-height">{windowSize.height}</h3>
    </>
  )
}

describe("useWindowSize", () => {
  it("returns the window size", () => {
    const { getByTestId } = render(<TestComponent />)
    expect(getByTestId("window-width")).toHaveTextContent("1024")
    expect(getByTestId("window-height")).toHaveTextContent("768")
  })
  
  it("returns the correct size after window is resized", () => {
    const { getByTestId } = render(<TestComponent />)
    expect(getByTestId("window-width")).toHaveTextContent("1024")
    expect(getByTestId("window-height")).toHaveTextContent("768")
    
    global.innerWidth = 1500
    global.innerHeight = 1200
    fireEvent(window, new Event("resize"))
    
    expect(getByTestId("window-width")).toHaveTextContent("1500")
    expect(getByTestId("window-height")).toHaveTextContent("1200")

  })
})