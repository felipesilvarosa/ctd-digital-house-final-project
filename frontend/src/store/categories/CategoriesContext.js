import axios from "axios"
import { createContext, useReducer } from "react"
import { categoriesReducer, categoriesState } from "src/store/categories"

export const CategoriesContext = createContext(categoriesState)

export const CategoriesProvider = ({children}) => {

  const [state, dispatch] = useReducer(categoriesReducer, categoriesState)

  const setCategories = async () => {
    dispatch({type: "SET_CATEGORIES_LOADING", payload: true})
    try {
      const categories = await axios("/api/categories")
      dispatch({type: "SET_CATEGORIES", payload: categories.data.data.map(category => ({id: category.id, ...category.attributes}))})
    } catch(e) {
      console.error(e)
    } finally {
      dispatch({type: "SET_CATEGORIES_LOADING", payload: false})
    }
  }

  const value = {
    categories: state.categories,
    loading: state.loading,
    setCategories
  }

  return (
    <CategoriesContext.Provider value={value}>{children}</CategoriesContext.Provider>
  )
}