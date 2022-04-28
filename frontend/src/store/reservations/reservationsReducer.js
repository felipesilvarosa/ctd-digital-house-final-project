export const reservationsState = {
  reservation: {},
  loading: false,
  loadingMakeReservation: false
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
    case "SET_MAKE_RESERVATION_LOADING":
      return {
        ...state,
        loadingMakeReservation: payload
      }
    case "CLEAR_RESERVATION":
      return {
        ...state,
        reservation: {}
      }
    default:
      throw new Error("Caso não previsto no reducer.")
  }
}