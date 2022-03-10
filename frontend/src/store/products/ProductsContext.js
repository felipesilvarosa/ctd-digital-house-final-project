import axios from "axios"
import { createContext, useReducer } from "react"
import { productsReducer, productsState } from "store/products"

export const ProductsContext = createContext(productsState)

export const ProductsProvider = ({children}) => {

  const [state, dispatch] = useReducer(productsReducer, productsState)

  const setProducts = async () => {
    dispatch({type: "SET_PRODUCTS_LOADING", payload: true})
    try {
      const products = await axios("/api/products")
      dispatch({type: "SET_PRODUCTS", payload: products.data.data.map(category => ({id: category.id, ...category.attributes}))})
    } catch(e) {
      console.error(e)
    } finally {
      dispatch({type: "SET_PRODUCTS_LOADING", payload: false})
    }
  }

  const value = {
    products: state.products,
    loading: state.loading,
    setProducts
  }

  return (
    <ProductsContext.Provider value={value}>{children}</ProductsContext.Provider>
  )
}