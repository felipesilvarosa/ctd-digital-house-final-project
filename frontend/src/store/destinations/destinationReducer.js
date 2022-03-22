export const destinationState = {
  destination: [],
  loading: false
}

export const destinationReducer = (state, action) => {
  const { payload, type } = action
  switch (type) {
    case "SET_DESTINATION":
      return {
        ...state,
        destination: payload
      }
    case "SET_DESTINATION_LOADING":
      return {
        ...state,
        loading: payload
      }
    default:
      throw new Error("Caso não previsto no reducer.")
  }
}