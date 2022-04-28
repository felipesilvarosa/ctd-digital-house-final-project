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
      dispatch({type: "SET_PRODUCTS", payload: products})
    } catch(e) {
      console.error(e)
    } finally {
      dispatch({type: "SET_PRODUCTS_LOADING", payload: false})
    }
  }

  const setPolicies = async () => {
    try {
      const response = await axios("/policies")
      const policies = response.data
      dispatch({type: "SET_POLICIES", payload: policies})
    } catch(e) {
      console.error(e)
    }
  }

  const createNewProduct = async (formData) => {
    dispatch({type: "SET_CREATE_PRODUCT_LOADING", payload: true})
    try {
      await axios.post("/products", formData)
      const response = await axios("/products")
      const products = response.data
      dispatch({type: "SET_PRODUCTS", payload: products})
    } catch(e) {
      console.error(e)
    } finally {
      dispatch({type: "SET_CREATE_PRODUCT_LOADING", payload: false})
    }
  }

  const searchProducts = async (searchParams) => {
    dispatch({type: "SET_PRODUCTS_LOADING", payload: true})

    let queryString = Object.entries(searchParams)
      .filter(param => param[1] !== "null" && param[1] !== null && param[1] !== "undefined")
      .map(param => `${param[0]}=${param[1]}`)
      .join("&")
    queryString = `?${queryString}`

    try {
      const response = await axios.get("/products/search" + queryString)
      const products = response.data
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
    availablePolicies: state.availablePolicies,
    loadingCreateProduct: state.loadingCreateProduct,
    setPolicies,
    setProducts,
    findProductById,
    createNewProduct,
    searchProducts
  }

  return (
    <ProductsContext.Provider value={value}>{children}</ProductsContext.Provider>
  )
}