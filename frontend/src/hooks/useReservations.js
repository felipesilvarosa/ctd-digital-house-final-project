import { useContext } from "react"
import { ReservationsContext } from "src/store/reservations"

export const useReservations = () => {
  const context = useContext(ReservationsContext)

  if (!context) {
    throw new Error("O hook useTheme somente pode ser utilizado dentro do contexto ReservationsContext")
  }

  return context
}