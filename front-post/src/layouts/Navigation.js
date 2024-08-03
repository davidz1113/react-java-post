import React, { Fragment } from "react";
import { Nav, Navbar, NavDropdown } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux";
import { NavLink } from "react-router-dom";
import { logOutUser } from "../actions/authActions";

export default function Navigation() {
  const loggedIn = useSelector((state) => state.auth.loggedIn);
  const user = useSelector((state) => state.auth.user);
  const dispatch = useDispatch();

  return (
    <Navbar bg="dark" variant="dark" expand="lg" style={{ padding: "8px" }}>
      <Navbar.Brand as={NavLink} to={"/"}>
        React Java
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="main-menu" />
      <Navbar.Collapse id="main-menu">
        <Nav className="mr-auto">
          {loggedIn && (<Nav.Link>Create Post</Nav.Link>)}
        </Nav>
        <Nav className="ms-auto">
          {!loggedIn ? (
            <Fragment>
              <Nav.Link>Create account</Nav.Link>
              <Nav.Link as={NavLink} to={"/signin"}>
                Sign In
              </Nav.Link>
            </Fragment>
          ) : (
            <NavDropdown title={user.sub} id="user-menu" className="ml-auto">
              <NavDropdown.Item>My posts</NavDropdown.Item>
              <NavDropdown.Item onClick={()=> dispatch(logOutUser())}>Logout</NavDropdown.Item>
            </NavDropdown>
          )}
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
}
