import axios from "axios"
import { createContext, useReducer } from "react"
import { destinationReducer, destinationState } from "src/store/destinations"

export const DestinationContext = createContext(destinationState)

export const DestinationProvider = ({children}) => {

  const [state, dispatch] = useReducer(destinationReducer, destinationState)

  const setDestinations = async () => {
    dispatch({type: "SET_DESTINATIONS_LOADING", payload: true})
    try {
      const response = await axios("/destinations")
      const destinations = response.data
      dispatch({type: "SET_DESTINATIONS", payload: destinations})
      dispatch({type: "SET_FILTERED_DESTINATIONS", payload: destinations})
    } catch(e) {
      console.error(e)
    } finally {
      dispatch({type: "SET_DESTINATIONS_LOADING", payload: false})
    }
  }

  const filterDestinations = (query) => {
    dispatch({ 
      type: "SET_FILTERED_DESTINATIONS", 
      payload: state.destinations.filter(
        (v) =>
          v.city.toLowerCase().includes(query.toLowerCase()) ||
          v.country.toLowerCase().includes(query.toLowerCase())
      )
    })
  }

  const value = {
    destinations: state.destinations,
    filteredDestinations: state.filteredDestinations,
    loading: state.loading,
    setDestinations,
    filterDestinations
  }

  return (
    <DestinationContext.Provider value={value}>{children}</DestinationContext.Provider>
  )
}