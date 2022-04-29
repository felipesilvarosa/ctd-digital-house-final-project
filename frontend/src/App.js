import { TheScaffold } from "src/components";
import { NotFoundView, HomeView, LoginView, SignupView, ProductDetailsView, ReservationDetailsView, ProtectedView, ProductCreationView } from "src/views"
import { BrowserRouter as Router, Route, Routes } from "react-router-dom"
import { GlobalProvider } from "src/store";

function App() {

  return (
    <Router>
      <GlobalProvider>
        <TheScaffold>
          <Routes>
            <Route path="*" element={<NotFoundView />} />
            <Route path="/" element={<HomeView />} />
            <Route path="/login" element={<LoginView />} />
            <Route path="/signup" element={<SignupView />} />
            <Route path="/products/:id" element={<ProductDetailsView />} />
            <Route path="/reserve/:id" element={<ProtectedView type="USER"><ReservationDetailsView /></ProtectedView>} />
            <Route path="/cadastrar" element={<ProtectedView type="ADMIN"><ProductCreationView /></ProtectedView>} />
          </Routes>
        </TheScaffold>
      </GlobalProvider>
    </Router>
  );
}

export default App;
