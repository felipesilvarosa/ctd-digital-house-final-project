import { useContext } from "react"
import { ThemesContext } from "src/store/themes"

export const useThemes = () => {
  const context = useContext(ThemesContext)

  if (!context) {
    throw new Error("O hook useTheme somente pode ser utilizado dentro do contexto ThemesContext")
  }

  return context
}