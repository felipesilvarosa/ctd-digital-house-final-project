export const reservationsState = {
  reservation: {},
  loading: false
}

export const reservationsReducer = (state, action) => {
  const { payload, type } = action
  switch (type) {
    case "SET_RESERVATION":
      return {
        ...state,
        reservation: payload
      }
    case "SET_RESERVATION_LOADING":
      return {
        ...state,
        loading: payload
      }
    default:
      throw new Error("Caso não previsto no reducer.")
  }
}