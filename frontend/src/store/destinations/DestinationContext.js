import axios from "axios"
import { createContext, useReducer } from "react"
import { destinationReducer, destinationState } from "src/store/destinations"

export const DestinationContext = createContext(destinationState)

export const DestinationProvider = ({children}) => {

  const [state, dispatch] = useReducer(destinationReducer, destinationState)

  const setDestination = async () => {
    dispatch({type: "SET_DESTINATION_LOADING", payload: true})
    try {
      const destination = await axios("/api/destinations")
      dispatch({type: "SET_DESTINATION", payload: destination.data.data.map(destination => ({id: destination.id, ...destination.attributes}))})
    } catch(e) {
      console.error(e)
    } finally {
      dispatch({type: "SET_DESTINATION_LOADING", payload: false})
    }
  }

  const value = {
    destination: state.destination,
    loading: state.destination,
    setDestination
  }

  return (
    <DestinationContext.Provider value={value}>{children}</DestinationContext.Provider>
  )
}