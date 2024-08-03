import React, { useState } from "react";
import { Form, Button, Alert } from "react-bootstrap";

export default function SignInForm({ errors, onSubmitCallBack }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const submitForm = (e) => {
    e.preventDefault();
    onSubmitCallBack({ email, password });
  };

  return (
    <Form onSubmit={submitForm}>
      <Form.Group controlId="email">
        <Form.Label>Email</Form.Label>
        <Form.Control
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="Write your email"
          isInvalid={errors.email}
        />
        <Form.Control.Feedback type="invalid">{errors.email}</Form.Control.Feedback>
      </Form.Group>

      <Form.Group controlId="password" className="mt-3">
        <Form.Label>Password</Form.Label>
        <Form.Control
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Write your password"
          isInvalid={errors.password}
        />
        <Form.Control.Feedback type="invalid">{errors.password}</Form.Control.Feedback>
      </Form.Group>

    <Button className="mt-4" variant="primary" type="submit">Sign In</Button>

    </Form>
  );
}
