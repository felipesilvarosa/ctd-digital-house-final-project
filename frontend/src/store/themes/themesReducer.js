export const themesState = {
  theme: "light"
}

export const themesReducer = (state, action) => {
  const { payload, type } = action
  switch (type) {
    case "SET_THEME":
      return {
        ...state,
        theme: payload
      }
    default:
      throw new Error("Caso não previsto no reducer.")
  }
}