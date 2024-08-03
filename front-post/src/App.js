import "bootstrap/dist/css/bootstrap.min.css";
import Navigation from "./layouts/Navigation";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { Container } from "react-bootstrap";
import SignIn from "./Pages/SignIn";
import Posts from "./Pages/Posts";
import store from "./store";
import { Provider } from "react-redux";
import { checkForToken } from "./helpers/checkForToken";

checkForToken();


function App() {
  return (
    <Provider store={store}>
      <Router>
        <div>
          <Navigation />
        </div>
        <Container>
          <Routes>
            <Route exact path="/" Component={Posts}></Route>
            <Route exact path="/signin" Component={SignIn}></Route>
            <Route></Route>
          </Routes>
        </Container>
      </Router>
    </Provider>
  );
}

export default App;
