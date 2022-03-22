import { render, waitFor, act } from "@testing-library/react"
import event from "@testing-library/user-event"
import { InputGroup } from "src/components"
import { Formik } from "formik"

describe("InputGroup", () => {
  it("renders", () =>{
    const { getByTestId } = render(<Formik><InputGroup id="test" label="Teste" inputType="text" /></Formik>)
    const component = getByTestId("input-group")
    expect(component).toBeInTheDocument()
  })

  it("allows text to be input", async () => {
    const { getByTestId } = render(<Formik initialValues={{ test: '' }}><InputGroup id="test" label="Teste" inputType="text" data-testid="input" /></Formik>)
    const component = getByTestId("input")
    event.type(component, "texto{space}de{space}teste")
    await waitFor(() => {
      expect(component).toHaveValue("texto de teste")
    })
  })

  it("allows you to show the password", async () => {
    const { getByTestId, getByRole } = render(<Formik initialValues={{ test: '' }}><InputGroup showPassword id="test" label="Teste" inputType="password" data-testid="input" /></Formik>)
    const input = getByTestId("input")
    const button = getByRole("button")
    expect(input).toHaveAttribute("type", "password")
    
    act(() => {
      event.click(button)
    })

    await waitFor(() => {
      expect(input).toHaveAttribute("type", "text")
    })
    
  })
})