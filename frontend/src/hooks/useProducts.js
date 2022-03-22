import { useContext } from "react"
import { ProductsContext } from "src/store/products"

export const useProducts = () => {
  const context = useContext(ProductsContext)

  if (!context) {
    throw new Error("O hook useProducts somente pode ser utilizado dentro do contexto ProductsContext")
  }

  return context
}