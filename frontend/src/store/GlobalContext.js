import { AuthProvider } from "src/store/auth";
import { CategoriesProvider } from "src/store/categories";
import { ProductsProvider } from "src/store/products";
import { ThemesProvider } from "src/store/themes";
import { DestinationProvider } from "src/store/destinations";

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
