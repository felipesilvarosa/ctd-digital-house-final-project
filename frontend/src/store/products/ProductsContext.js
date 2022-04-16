import axios from "axios"
import { createContext, useReducer } from "react"
import { productsReducer, productsState } from "src/store/products"

export const ProductsContext = createContext(productsState)

export const ProductsProvider = ({children}) => {

  const [state, dispatch] = useReducer(productsReducer, productsState)

  const findProductById = async (id) => {
    dispatch({ type: "SET_PRODUCTS_LOADING", payload: true })
    try {
      const response = await axios(`/products/${id}`)
      const product = response.data
      dispatch({ type: "SET_PRODUCT", payload: product })
    } catch (e) {
      console.error(e)
    } finally {
      dispatch({ type: "SET_PRODUCTS_LOADING", payload: false })
    }
  }

  const setProducts = async () => {
    dispatch({type: "SET_PRODUCTS_LOADING", payload: true})
    try {
      const response = await axios("/products")
      const products = response.data
      console.log(products)
      dispatch({type: "SET_PRODUCTS", payload: products})
    } catch(e) {
      console.error(e)
    } finally {
      dispatch({type: "SET_PRODUCTS_LOADING", payload: false})
    }
  }

  const value = {
    products: state.products,
    product: state.product,
    loading: state.loading,
    setProducts,
    findProductById
  }

  return (
    <ProductsContext.Provider value={value}>{children}</ProductsContext.Provider>
  )
}