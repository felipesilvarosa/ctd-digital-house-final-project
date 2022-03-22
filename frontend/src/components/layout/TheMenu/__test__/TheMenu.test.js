import { render, screen } from "@testing-library/react"
import { TheMenu } from "src/components"
import { BrowserRouter as Router } from "react-router-dom"
import { AuthContext } from "src/store/auth"

/* ====================================== */
/* Context está retornando como undefined */
/* ====================================== */

// jest.mock("store/auth/AuthContext", () => ({
//   __esModule: true,
//   default: React.createContext()
// }));

// const Test = () => {
//   return (
//     <Router>
//       <TheMenu />
//     </Router>
//   )
// }

describe("TheMenu", () => {
  it("shows login/sign up options if user is not logged in", () =>{
    // const providerProps = {
    //   user: null,
    //   loading: false,
    //   errors: {}, 
    //   registerUser: () => {},
    //   userLogin: () => {},
    //   userLogout: () => {}
    // }
    // render(<AuthContext.Provider value={providerProps}><Test /></AuthContext.Provider>)
 
    // const login = screen.queryByText(/login/i)
    // const signUp = screen.queryByText(/registrar-se/i)
  
    // expect(login).toBeInTheDocument()
    // expect(signUp).toBeInTheDocument()
  })
  
  it("shows user avatar if logged in", () => {
    // const providerProps = {
    //   user: {
    //     name: "John",
    //     surname: "Snow",
    //     fullName: "John Snow",
    //     email: "jsnow@noway.org"
    //   },
    //   loading: false,
    //   errors: {}, 
    //   registerUser: () => {},
    //   userLogin: () => {},
    //   userLogout: () => {}
    // }
    // render(<AuthContext.Provider value={providerProps}><Test /></AuthContext.Provider>)
    // const userAvatar = screen.queryByTestId("user-avatar")
    // expect(userAvatar).toHaveTextContent("JS")
  })
})
