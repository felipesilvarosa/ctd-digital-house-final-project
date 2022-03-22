import { useContext } from "react"
import { AuthContext } from "src/store/auth"

export const useAuth = () => {
  const context = useContext(AuthContext)

  if (!context) {
    throw new Error("O hook useAuth somente pode ser utilizado dentro do contexto AuthContext")
  }

  return context
}