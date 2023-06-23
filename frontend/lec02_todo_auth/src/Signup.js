import React from 'react';
import { Container, Grid, Typography, TextField, Button } from "@mui/material";
import { signup } from "./service/ApiService";
import { Link } from "react-router-dom";

const Signup = () => {

  const handleSubmit = (e) => {
    e.preventDefault();
    const data = FormData(e.target);
    const username = data.get("username");
    const password = data.get("password");
    signup({username: username, password: password})
      .then((response) => {
        // 게정생성 성공시 login페이지로 redirect
        window.location.href("/");
      })
  }

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
      <form noValidate onSubmit={handleSubmit}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <Typography component="h1" variant="h5">
              계정 생성
            </Typography>
          </Grid>
          <Grid item xs={12}>
            <TextField
              autoComplete="fname"
              name="username"
              variant="outlined"
              required
              fullWidth
              id="username"
              label="아이디"
              autoFocus
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              name="password"
              label="패스워드"
              type="password"
              id="password"
              autoComplete="current-password"
            />
          </Grid>
          <Grid item xs={12}>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
            >
              계정 생성
            </Button>
          </Grid>
          <Grid item xs={12}>
            <Link to="/login" variant="body2">
              이미 계정이 있습니까? 로그인 하세요.
            </Link>
          </Grid>
        </Grid>
      </form>
    </Container>
  )
};

export default Signup;