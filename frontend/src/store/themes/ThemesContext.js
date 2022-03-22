import { createContext, useReducer } from "react"
import { themesReducer, themesState } from "src/store/themes"

export const ThemesContext = createContext(themesState)

export const ThemesProvider = ({children}) => {

  const [state, dispatch] = useReducer(themesReducer, themesState)

  const toggleTheme = () => {
    const themePairs = new Map([
      ["light", "dark"],
      ["dark", "light"]
    ])
    const newTheme = themePairs.get(state.theme) ?? "light"

    dispatch({type: "SET_THEME", payload: newTheme})
    document.body.setAttribute("data-theme", newTheme)
  }

  const value = {
    theme: state.theme,
    toggleTheme
  }

  return (
    <ThemesContext.Provider value={value}>{children}</ThemesContext.Provider>
  )
}