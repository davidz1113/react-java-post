import { Card, Col, Container, Row } from "react-bootstrap";
import SignInForm from "../components/forms/SignInForm";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import validator from "validator";
import { isObjEmpty } from "../helpers/helper";
import { loginUser } from "../actions/authActions";
import { useNavigate } from "react-router-dom";

export default function SignIn() {
  const [errors, setErrors] = useState({});
  const dispatch = useDispatch();
  const loggedIn = useSelector((state) => state.auth.loggedIn);
  const navigate = useNavigate();

  useEffect(() => {
    //when the component starts
    if (loggedIn) {
      navigate("/");
    }
  });

  const login = ({ email, password }) => {
    const errors = {};
    setErrors(errors);

    if (!validator.isEmail(email)) {
      errors.email = "Please enter a valid email";
    }

    if (validator.isEmpty(password)) {
      errors.password = "Please enter a password";
    }

    if (!isObjEmpty(errors)) {
      setErrors(errors);
      return;
    }

    dispatch(loginUser({ email, password }))
      .then((response) => {})
      .catch((error) => {});
  };

  return (
    <Container className="mt-5">
      <Row>
        <Col sm="12" md={{ span: 8, offset: 2 }} lg={{ span: 6, offset: 3 }}>
          <Card body>
            <h3>Sign In</h3>
            <SignInForm errors={errors} onSubmitCallBack={login} />
            <div className="mt-3">
              <Link to={"/signup"}> Don't you have an account? please sign up! </Link>
            </div>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}
