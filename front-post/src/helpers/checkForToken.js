import { jwtDecode } from "jwt-decode";
import setAuthToken from "./setAuthToken";
import store from "../store";
import { logOutUser, setCurrentUser } from "../actions/authActions";

export const checkForToken = () => {
  if (localStorage.getItem("token")) {
    const token = localStorage.getItem("token");
    setAuthToken(token);

    const decoded = jwtDecode(token);

    store.dispatch(setCurrentUser({ user: decoded, loggedIn: true }));

    const currentTime = Math.floor(Date.now() / 1000);

    if (decoded.exp < currentTime) {
      store.dispatch(logOutUser());
      window.location.href = "/signin";
    }
  }
};
