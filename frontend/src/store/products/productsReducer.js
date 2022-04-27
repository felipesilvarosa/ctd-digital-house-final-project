export const productsState = {
  products: [],
  product: {},
  availablePolicies: [],
  loading: false,
  loadingMakeReservation: false
}

export const productsReducer = (state, action) => {
  const { payload, type } = action
  switch (type) {
    case "SET_PRODUCTS":
      return {
        ...state,
        products: payload
      }
    case "SET_POLICIES":
      return {
        ...state,
        availablePolicies: payload
      }
    case "SET_PRODUCT":
      return {
        ...state,
        product: payload
      }
    case "SET_PRODUCTS_LOADING":
      return {
        ...state,
        loading: payload
      }
    case "SET_MAKE_RESERVATION_LOADING":
      return {
        ...state,
        loadingMakeReservation: payload
      }
    default:
      throw new Error("Caso não previsto no reducer.")
  }
}