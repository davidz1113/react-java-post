import axios from "axios";
import { LOGIN_ENDPOINT } from "../helpers/endpoints";
import { jwtDecode } from "jwt-decode";
import { SET_CURRENT_USER } from "./types";
import setAuthToken from "../helpers/setAuthToken";

export const loginUser = (userData) => (dispatch) => {
  console.log(userData);

  return new Promise((resolve, reject) => {
    axios
      .post(LOGIN_ENDPOINT, userData, {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
      })
      .then((res) => {
        const { authorization, userId } = res.headers;
        localStorage.setItem("token", authorization);

        //create a function to axios automatically
        setAuthToken(authorization);

        const decodeToken = jwtDecode(authorization);
        dispatch(setCurrentUser({ user: decodeToken, loggedIn: true }));

        resolve(res);
      })
      .catch((err) => {
        reject(err);
      });
  });
};

export const setCurrentUser = ({ user, loggedIn }) => {
  return {
    type: SET_CURRENT_USER,
    payload: {
      user,
      loggedIn,
    },
  };
};

export const logOutUser = () => (dispatch) => {
  localStorage.removeItem("token");

  setAuthToken(false);

  dispatch(setCurrentUser({ user: {}, loggedIn: false }));
};
