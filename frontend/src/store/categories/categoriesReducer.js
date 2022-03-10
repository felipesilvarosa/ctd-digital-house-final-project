export const categoriesState = {
  categories: [],
  loading: false
}

export const categoriesReducer = (state, action) => {
  const { payload, type } = action
  switch (type) {
    case "SET_CATEGORIES":
      return {
        ...state,
        categories: payload
      }
    case "SET_CATEGORIES_LOADING":
      return {
        ...state,
        loading: payload
      }
    default:
      throw new Error("Caso não previsto no reducer.")
  }
}