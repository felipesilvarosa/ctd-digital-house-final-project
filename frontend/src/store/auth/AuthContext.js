import { createContext, useReducer, useCallback } from "react";
import { authReducer, authState } from "src/store/auth"
import { validateEmail } from "src/utils";
import axios from "axios";

export const AuthContext = createContext(authState)


export const AuthProvider = ({children}) => {

  const [state, dispatch] = useReducer(authReducer, authState)

  const registerUser = useCallback(async ({firstName, lastName, email, password, passwordConfirmation}) => {
    const errors = {}

    dispatch({
      type: "USER_LOADING",
      payload: true
    })

    dispatch({
      type: "USER_SIGNUP_ERRORS",
      payload: {}
    })

    try {
      if(firstName.trim() === "") {
        errors.nome = "Campo obrigatório."
      }

      if(lastName.trim() === "") {
        errors.sobrenome = "Campo obrigatório."
      }

      if(!validateEmail(email)){
        errors.email = "E-mail inválido."
      }

      if(password.length < 10 ){
        errors.senha = "Senha inválida."
      }

      if(password !== passwordConfirmation) {
        errors.confirmarSenha = "Precisa ser igual à senha."
      }

      
      if (Object.keys(errors).length > 0) {
        throw new Error("Credenciais inválidas.")
      }
      
      const response = await axios.post("/users", { firstName, lastName, email, password })
      const modeledUser = {
        ...response.data,
        fullName: `${response.data.firstName} ${response.data.lastName}`
      }

      dispatch({
        type: "USER_LOGIN",
        payload: modeledUser
      })
      
      dispatch({
        type: "USER_SIGNUP_ERRORS",
        payload: {}
      })

      dispatch({
        type: "USER_SIGNUP_STATUS_UPDATE",
        payload: "success"
      })

    } catch (e) {
      if (e.response) {
        errors.server = e.response.data.message
      }
      dispatch({ type: "USER_SIGNUP_ERRORS", payload: errors })
      dispatch({ type: "USER_LOGOUT" })
    } finally {
      dispatch({
        type: "USER_LOADING",
        payload: false
      })
    }
  }, [dispatch])

  const userLogin = useCallback(async (email, password) => {
    const errors = {}

    dispatch({
      type: "USER_LOADING",
      payload: true
    })

    dispatch({
      type: "USER_LOGIN_ERRORS",
      payload: {}
    })

    try {
      if(!validateEmail(email)){
        errors.email = "E-mail inválido."
      }

      if(password.length < 10 ){
        errors.senha = "Senha inválida."
      }

      if (Object.keys(errors).length > 0) {
        throw new Error("Credenciais inválidas.")
      }

      const response = await axios.post("/login", { email, password })
      const modeledUser = {
        ...response.data,
        fullName: `${response.data.firstName} ${response.data.lastName}`
      }

      dispatch({
        type: "USER_LOGIN",
        payload: modeledUser
      })

      dispatch({
        type: "USER_LOGIN_ERRORS",
        payload: {}
      })

      dispatch({
        type: "USER_LOGIN_STATUS_UPDATE",
        payload: "success"
      })

    } catch (e) {
      if (e.response) {
        errors.server = e.response.data.error
      }
      dispatch({ type: "USER_LOGIN_ERRORS", payload: errors })
      dispatch({ type: "USER_LOGOUT" })
    } finally {
      dispatch({
        type: "USER_LOADING",
        payload: false
      })
    }
  }, [dispatch])

  const userLogout = useCallback(() => {
    dispatch({ type: "USER_LOGOUT" })
    dispatch({ type: "USER_LOGIN_STATUS_UPDATE", payload: "logout"})
  }, [dispatch])

  const clearLoginErrors = useCallback(() => {
    dispatch({type: "USER_LOGIN_ERRORS", payload: {}})
  }, [dispatch])

  const clearSignupErrors = useCallback(() => {
    dispatch({type: "USER_SIGNUP_ERRORS", payload: {}})
  }, [dispatch])

  const updateLoginStatus = useCallback((status) => {
    dispatch({type: "USER_LOGIN_STATUS_UPDATE", payload: status})
  }, [dispatch])

  const updateSignupStatus = useCallback((status) => {
    dispatch({type: "USER_SIGNUP_STATUS_UPDATE", payload: status})
  }, [dispatch])

  const validateUser = useCallback(async () => {
    try {
      const response = await axios.get("/users/validate")
      const user = response.data
      console.log(user)

      if (user) {
        const modeledUser = {
          ...user,
          fullName: `${user.firstName} ${user.lastName}`
        }
  
        dispatch({
          type: "USER_LOGIN",
          payload: modeledUser
        })
      }
    } catch(e) {
      console.error(e)
    }
  }, [dispatch])

  const value = {
    ...state,
    registerUser,
    userLogin,
    userLogout,
    clearLoginErrors,
    clearSignupErrors,
    updateLoginStatus,
    updateSignupStatus,
    validateUser
  }

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  )
}