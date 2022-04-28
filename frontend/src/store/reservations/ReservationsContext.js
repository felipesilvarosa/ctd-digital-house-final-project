import { createContext, useReducer } from "react"
import { reservationsReducer, reservationsState } from "src/store/reservations"
import axios from "axios"

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

  const clearReservation = async () => {
    dispatch({type: "SET_RESERVATION_LOADING", payload: true})
    try {
      dispatch({type: "CLEAR_RESERVATION"})
    } catch(e) {
      console.error(e)
    } finally {
      dispatch({type: "SET_RESERVATION_LOADING", payload: false})
    }
  }

  const makeReservation = async (data) => {
    dispatch({type: "SET_MAKE_RESERVATION_LOADING", payload: true})
    try {
      await axios.post("/reservations", {
        checkinDate: data.checkIn,
        checkinTime: "14:00",
        checkoutDate: data.checkOut,
        checkoutTime: "11:00",
        clientId: data.clientId,
        productId: data.productId
      })
    } catch(e) {
      console.error(e.message)
    } finally {
      dispatch({type: "SET_MAKE_RESERVATION_LOADING", payload: false})
    }
  }

  const value = {
    reservation: state.reservation,
    loading: state.loading,
    loadingMakeReservation: state.loadingMakeReservation,
    setReservation,
    clearReservation,
    makeReservation
  }

  return (
    <ReservationsContext.Provider value={value}>{children}</ReservationsContext.Provider>
  )
}