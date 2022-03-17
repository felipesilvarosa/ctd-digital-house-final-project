import { AuthProvider } from "store/auth";
import { CategoriesProvider } from "store/categories";
import { ProductsProvider } from "store/products";
import { ThemesProvider } from "store/themes";
import { DestinationProvider } from "store/destinations";

export const GlobalProvider = ({ children }) => {
  return (
    <AuthProvider>
      <CategoriesProvider>
        <ProductsProvider>
          <ThemesProvider>
            <DestinationProvider>
              {children}
            </DestinationProvider>
          </ThemesProvider>
        </ProductsProvider>
      </CategoriesProvider>
    </AuthProvider>
  );
};
