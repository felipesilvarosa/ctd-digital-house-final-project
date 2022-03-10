import { AuthProvider } from "store/auth"
import { CategoriesProvider } from "store/categories"
import { ProductsProvider } from "store/products"
import { ThemesProvider } from "store/themes"

export const GlobalProvider = ({children}) => {
  return (
    <AuthProvider>
      <CategoriesProvider>
        <ProductsProvider>
          <ThemesProvider>
          {children}
          </ThemesProvider>
        </ProductsProvider>
      </CategoriesProvider>
    </AuthProvider>
  )
}