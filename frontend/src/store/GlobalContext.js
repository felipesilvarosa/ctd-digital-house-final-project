import { AuthProvider } from "src/store/auth";
import { CategoriesProvider } from "src/store/categories";
import { ProductsProvider } from "src/store/products";
import { DestinationProvider } from "src/store/destinations";
import { ReservationsProvider } from "src/store/reservations";

export const GlobalProvider = ({ children }) => {
  return (
    <AuthProvider>
      <CategoriesProvider>
        <ProductsProvider>
          <ReservationsProvider>
            <DestinationProvider>
              {children}
            </DestinationProvider>
          </ReservationsProvider>
        </ProductsProvider>
      </CategoriesProvider>
    </AuthProvider>
  );
};
