export const destinationState = {
  destinations: [],
  filteredDestinations: [],
  loading: false
}

export const destinationReducer = (state, action) => {
  const { payload, type } = action
  switch (type) {
    case "SET_DESTINATIONS":
      return {
        ...state,
        destinations: payload
      }
    case "SET_FILTERED_DESTINATIONS":
      return {
        ...state,
        filteredDestinations: payload
      }
    case "SET_DESTINATIONS_LOADING":
      return {
        ...state,
        loading: payload
      }
    default:
      throw new Error("Caso não previsto no reducer.")
  }
}