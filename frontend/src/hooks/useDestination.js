import { useContext } from "react"
import { DestinationContext } from "src/store/destinations"

export const useDestination = () => {
  const context = useContext(DestinationContext)

  if (!context) {
    throw new Error("O hook useDestination somente pode ser utilizado dentro do contexto DestinationContext")
  }

  return context
}