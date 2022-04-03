import { createContext, useReducer } from "react"
import { reservationsReducer, reservationsState } from "src/store/reservations"

export const ReservationsContext = createContext(reservationsState)

export const ReservationsProvider = ({children}) => {

  const [state, dispatch] = useReducer(reservationsReducer, reservationsState)

  const setReservation = async ({product, user, checkIn, checkOut}) => {
    dispatch({type: "SET_RESERVATION_LOADING", payload: true})
    try {
      dispatch({type: "SET_RESERVATION", payload: {product, user, checkIn, checkOut}})
    } catch(e) {
      console.error(e)
    } finally {
      dispatch({type: "SET_RESERVATION_LOADING", payload: false})
    }
  }

  const value = {
    reservation: state.reservation,
    loading: state.loading,
    setReservation,
  }

  return (
    <ReservationsContext.Provider value={value}>{children}</ReservationsContext.Provider>
  )
}