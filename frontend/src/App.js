import { TheScaffold } from "components";
import { HomeView, LoginView, SignupView } from "views"
import { BrowserRouter as Router, Route, Routes } from "react-router-dom"
import { GlobalProvider } from "store";

function App() {

  return (
    <Router>
      <GlobalProvider>
        <TheScaffold>
          <Routes>
            <Route path="/" element={<HomeView />} />
            <Route path="/login" element={<LoginView />} />
            <Route path="/signup" element={<SignupView />} />
          </Routes>
        </TheScaffold>
      </GlobalProvider>
    </Router>
  );
}

export default App;
