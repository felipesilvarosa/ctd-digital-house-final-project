import { render, waitFor } from "@testing-library/react"
import userEvent from "@testing-library/user-event"
import { useReducer } from "react"
import { authReducer, authState } from "src/store/auth"

const TestComponent = () => {
  const [state, dispatch] = useReducer(authReducer, authState)
  const john = { name: "John", surname: "Cena", email: "johncena@invisible.com" }
  
  return (
    <>
      <button onClick={() => dispatch({type: "USER_LOGIN", payload: john})}>CLICK</button>
      <button onClick={() => dispatch({type: "USER_LOGOUT"})}>LOGOUT</button>
      { 
        state.user ? (
          <>
            <p>{state.user.name}</p> 
            <p>{state.user.surname}</p>
            <p>{state.user.email}</p>
          </>
        ) : (
          <p>offline</p>
        )
      }
    </>
  )
}

describe("authReducer", () => {
  it("returns user data when USER_LOGIN action is called", () => {

    const { getByText } = render(<TestComponent />)

    userEvent.click(getByText("CLICK"))
    waitFor(() => {
      expect(getByText("John")).toBeInTheDocument()
      expect(getByText("Cena")).toBeInTheDocument()
      expect(getByText("johncena@invisible.com")).toBeInTheDocument()
    })
  })

  it("logs user out when USER_LOGOUT action is called", () => {

    const { getByText } = render(<TestComponent />)

    userEvent.click(getByText("CLICK"))
    waitFor(() => {
      expect(getByText("John")).toBeInTheDocument()
      expect(getByText("Cena")).toBeInTheDocument()
      expect(getByText("johncena@invisible.com")).toBeInTheDocument()
    })
    userEvent.click(getByText("LOGOUT"))
    waitFor(() => {
      expect(getByText("offline")).toBeInTheDocument()
    })
  })
})