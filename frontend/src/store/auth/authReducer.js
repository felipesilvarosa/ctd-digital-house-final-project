export const authState = {
  user: null,
  loading: false,
  loginErrors: {},
  signupErrors: {},
  loginStatus: null,
  signupStatus: null
}

export const authReducer = (state, action) => {
  const { type, payload } = action

  switch(type) {
    case "USER_LOGIN_STATUS_UPDATE":
      return {
        ...state,
        loginStatus: payload
      }

    case "USER_SIGNUP_STATUS_UPDATE":
      return {
        ...state,
        signupStatus: payload
      }

    case "USER_LOGIN":
      return {
        ...state,
        user: payload
      }

    case "USER_LOADING":
      return {
        ...state,
        loading: payload
      }

    case "USER_LOGOUT":
      return {
        ...state,
        user: null
      }

    case "USER_LOGIN_ERRORS":
      return {
        ...state,
        loginErrors: payload
      }

    case "USER_SIGNUP_ERRORS":
      return {
        ...state,
        signupErrors: payload
      }

    default:
      throw new Error("Caso não previsto no reducer.")
  }
}