export const productsState = {
  products: [],
  product: {},
  loading: false
}

export const productsReducer = (state, action) => {
  const { payload, type } = action
  switch (type) {
    case "SET_PRODUCTS":
      return {
        ...state,
        products: payload
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
    default:
      throw new Error("Caso não previsto no reducer.")
  }
}