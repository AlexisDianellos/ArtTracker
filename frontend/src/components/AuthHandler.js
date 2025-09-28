import React, { useState } from "react";
import api from "../api/axiosConfig";
import { useDispatch } from "react-redux";
import { login } from "../features/authSlicer";

const AuthHandler = () => {
  const dispatch = useDispatch();
  
  const [mode, setMode] = useState("login"); // "signup" | "login" | "verify"
  const [postForm, setPostform] = useState({
    email: "",
    password: "",
    username: "",
    code: "",
  });
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setPostform((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const showError = (err) => {
    const data = err?.response?.data;
    // backend might return a string, or { error }, or { message }
    const text = typeof data === "string" ? data : data?.error || data?.message;
    setMessage(text || "Something went wrong");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");

    try {
      if (mode === "signup") {
        await api.post("/auth/signup", {
          email: postForm.email,
          password: postForm.password,
          username: postForm.username,
        });
        setMessage("Signup successful! Check your email for the verification code.");
        setPostform((p) => ({ ...p, password: "", username: "" }));
        setMode("verify");
        return;
      }

      if (mode === "verify") {
        // IMPORTANT: the backend likely expects 'code', not 'verificationCode'
        await api.post("/auth/verify", {
          email: postForm.email,
          code: postForm.code,
        });
        setMessage("Verification successful! You can now sign in.");
        setPostform((p) => ({ ...p, password: "", code: "" }));
        setMode("login");
        return;
      }

      // login
      const res = await api.post("/auth/login", {
        email: postForm.email,
        password: postForm.password,
      });

      // Expect { token, expiration }
      const { token, expiresIn } = res?.data || {};

      if (!token) {
        setMessage("❌ No token received from server.");
        return;
      }
            dispatch(login(token)); 

      // Store token (and optionally set default header for future requests)

      setMessage(`✅ Logged in! Token exp: ${expiresIn ?? "unknown"}`);
      // clear password from state
      setPostform((p) => ({ ...p, password: "" }));
    } catch (err) {
      showError(err);
    }
  };

  return (
    <div style={{ maxWidth: 360, margin: "2rem auto", fontFamily: "sans-serif" }}>
      <div style={{ marginBottom: 8, textTransform: "uppercase", fontWeight: 700 }}>
        {mode}
      </div>
      <form onSubmit={handleSubmit} style={{ display: "grid", gap: 8 }}>
        {mode === "signup" && (
          <>
            <input
              type="text"
              name="username"
              placeholder="Username"
              value={postForm.username}
              onChange={handleChange}
            />
            <input
              type="password"
              name="password"
              placeholder="Password"
              value={postForm.password}
              onChange={handleChange}
            />
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={postForm.email}
              onChange={handleChange}
            />
          </>
        )}

        {mode === "verify" && (
          <>
            <input
              type="text"
              name="code"
              placeholder="Verification Code"
              value={postForm.code}
              onChange={handleChange}
            />
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={postForm.email}
              onChange={handleChange}
            />
          </>
        )}

        {mode === "login" && (
          <>
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={postForm.email}
              onChange={handleChange}
            />
            <input
              type="password"
              name="password"
              placeholder="Password"
              value={postForm.password}
              onChange={handleChange}
            />
          </>
        )}

        <button type="submit">submit</button>
        <div style={{ minHeight: 24 }}>{message}</div>

        {/* Quick mode switcher for testing */}
        <div style={{ display: "flex", gap: 8 }}>
          <button type="button" onClick={() => setMode("signup")}>signup</button>
          <button type="button" onClick={() => setMode("verify")}>verify</button>
          <button type="button" onClick={() => setMode("login")}>login</button>
        </div>
      </form>
    </div>
  );
};

export default AuthHandler;
