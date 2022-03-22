import { useContext } from "react"
import { CategoriesContext } from "src/store/categories"

export const useCategories = () => {
  const context = useContext(CategoriesContext)

  if (!context) {
    throw new Error("O hook useCategories somente pode ser utilizado dentro do contexto CategoriesContext")
  }

  return context
}