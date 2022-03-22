import axios from "axios"
import { createContext, useReducer } from "react"
import { productsReducer, productsState } from "src/store/products"

export const ProductsContext = createContext(productsState)

export const ProductsProvider = ({children}) => {

  const [state, dispatch] = useReducer(productsReducer, productsState)

  const findProductById = async (id) => {
    dispatch({ type: "SET_PRODUCTS_LOADING", payload: true })
    try {
      const response = await axios(`/api/products/${id}`)
      const product = {
        ...response.data.data.attributes,
        id: response.data.data.id
      }
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
    product: state.product,
    loading: state.loading,
    setProducts,
    findProductById
  }

  return (
    <ProductsContext.Provider value={value}>{children}</ProductsContext.Provider>
  )
}