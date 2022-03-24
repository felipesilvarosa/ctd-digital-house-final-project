import { createContext, useReducer, useCallback } from "react";
import { authReducer, authState } from "src/store/auth"
import { validateEmail } from "src/utils";
import axios from "axios";

export const AuthContext = createContext(authState)


export const AuthProvider = ({children}) => {

  const [state, dispatch] = useReducer(authReducer, authState)

  const registerUser = useCallback(async ({name, surname, email, password, passwordConfirmation}) => {
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
      if(name.trim() === "") {
        errors.nome = "Campo obrigatório."
      }

      if(surname.trim() === "") {
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
      
      const response = await axios.post("/api/users", { name, surname, email, password })
      
      const modeledUser = {
        ...response.data.data.attributes,
        id: response.data.data.id,
        fullName: `${response.data.data.attributes.name} ${response.data.data.attributes.surname}`
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
        errors.server = e.response.data.error
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

      const response = await axios.post("/api/login", { email, password })

      const modeledUser = {
        ...response.data.user,
        fullName: `${response.data.user.name} ${response.data.user.surname}`
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

  const value = {
    ...state,
    registerUser,
    userLogin,
    userLogout,
    clearLoginErrors,
    clearSignupErrors,
    updateLoginStatus,
    updateSignupStatus
  }

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  )
}