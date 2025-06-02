# Frontend Implementation Guide for Google OAuth2 with React

## 1. Project Setup

First, make sure you have a React project set up. If not, create one:

```bash
npm create vite@latest my-app -- --template react
cd my-app
npm install
```

## 2. Install Required Dependencies

```bash
npm install react-router-dom axios jwt-decode
```

## 3. Create OAuth2 Components

### Create OAuth2 Login Button Component

Create a file `src/components/GoogleLoginButton.jsx`:

```jsx
import React from "react";

const GoogleLoginButton = () => {
  const handleGoogleLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/google";
  };

  return (
    <button
      onClick={handleGoogleLogin}
      className="google-login-btn"
      style={{
        backgroundColor: "#fff",
        color: "#757575",
        border: "1px solid #ddd",
        borderRadius: "4px",
        padding: "10px 20px",
        display: "flex",
        alignItems: "center",
        gap: "10px",
        cursor: "pointer",
        fontWeight: "500",
        boxShadow: "0 1px 3px rgba(0,0,0,0.12)",
      }}
    >
      <svg
        width="18"
        height="18"
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 48 48"
      >
        <path
          fill="#FFC107"
          d="M43.611,20.083H42V20H24v8h11.303c-1.649,4.657-6.08,8-11.303,8c-6.627,0-12-5.373-12-12c0-6.627,5.373-12,12-12c3.059,0,5.842,1.154,7.961,3.039l5.657-5.657C34.046,6.053,29.268,4,24,4C12.955,4,4,12.955,4,24c0,11.045,8.955,20,20,20c11.045,0,20-8.955,20-20C44,22.659,43.862,21.35,43.611,20.083z"
        />
        <path
          fill="#FF3D00"
          d="M6.306,14.691l6.571,4.819C14.655,15.108,18.961,12,24,12c3.059,0,5.842,1.154,7.961,3.039l5.657-5.657C34.046,6.053,29.268,4,24,4C16.318,4,9.656,8.337,6.306,14.691z"
        />
        <path
          fill="#4CAF50"
          d="M24,44c5.166,0,9.86-1.977,13.409-5.192l-6.19-5.238C29.211,35.091,26.715,36,24,36c-5.202,0-9.619-3.317-11.283-7.946l-6.522,5.025C9.505,39.556,16.227,44,24,44z"
        />
        <path
          fill="#1976D2"
          d="M43.611,20.083H42V20H24v8h11.303c-0.792,2.237-2.231,4.166-4.087,5.571c0.001-0.001,0.002-0.001,0.003-0.002l6.19,5.238C36.971,39.205,44,34,44,24C44,22.659,43.862,21.35,43.611,20.083z"
        />
      </svg>
      Sign in with Google
    </button>
  );
};

export default GoogleLoginButton;
```

### Create OAuth2 Redirect Handler Component

Create a file `src/components/OAuth2RedirectHandler.jsx`:

```jsx
import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";

const OAuth2RedirectHandler = () => {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const getToken = () => {
      const params = new URLSearchParams(location.search);
      const token = params.get("token");

      if (token) {
        // Store token in localStorage
        localStorage.setItem("token", token);

        // Validate token with backend
        axios
          .get(
            `http://localhost:8080/api/v1/oauth2/validate-token?token=${token}`
          )
          .then((response) => {
            if (response.data.valid) {
              // Store user info
              localStorage.setItem(
                "user",
                JSON.stringify({
                  id: response.data.userId,
                  username: response.data.username,
                  email: response.data.email,
                  firstName: response.data.firstName,
                  lastName: response.data.lastName,
                  roles: response.data.roles,
                  avatarUrl: response.data.avatarUrl,
                })
              );

              // Redirect to home page or dashboard
              navigate("/");
            } else {
              setError("Invalid token");
              localStorage.removeItem("token");
              setLoading(false);
            }
          })
          .catch((err) => {
            console.error("Token validation error:", err);
            setError("Failed to validate token");
            localStorage.removeItem("token");
            setLoading(false);
          });
      } else {
        setError("No token found in the URL");
        setLoading(false);
      }
    };

    getToken();
  }, [location, navigate]);

  if (loading) {
    return (
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          height: "100vh",
        }}
      >
        <div className="spinner">Loading...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
          height: "100vh",
        }}
      >
        <h2>Authentication Error</h2>
        <p>{error}</p>
        <button onClick={() => navigate("/login")}>Back to Login</button>
      </div>
    );
  }

  return null;
};

export default OAuth2RedirectHandler;
```

## 4. Setup Routes in App.jsx

Update your main App component:

```jsx
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import OAuth2RedirectHandler from "./components/OAuth2RedirectHandler";
import Login from "./pages/Login";
import Home from "./pages/Home";
import PrivateRoute from "./components/PrivateRoute";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
        <Route
          path="/"
          element={
            <PrivateRoute>
              <Home />
            </PrivateRoute>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
```

## 5. Create a PrivateRoute Component

Create a file `src/components/PrivateRoute.jsx`:

```jsx
import React from "react";
import { Navigate } from "react-router-dom";

const PrivateRoute = ({ children }) => {
  const token = localStorage.getItem("token");

  if (!token) {
    // Redirect to login if not authenticated
    return <Navigate to="/login" />;
  }

  return children;
};

export default PrivateRoute;
```

## 6. Create a Login Page

Create a file `src/pages/Login.jsx`:

```jsx
import React from "react";
import GoogleLoginButton from "../components/GoogleLoginButton";

const Login = () => {
  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        height: "100vh",
        gap: "20px",
      }}
    >
      <h1>Fashion Store</h1>
      <div
        style={{
          padding: "30px",
          borderRadius: "8px",
          boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
          backgroundColor: "#fff",
          width: "300px",
          textAlign: "center",
        }}
      >
        <h2>Sign In</h2>
        <p>Choose your login method:</p>

        <div style={{ marginTop: "20px", marginBottom: "20px" }}>
          <GoogleLoginButton />
        </div>

        <div style={{ marginTop: "20px" }}>
          {/* Regular login form could go here */}
        </div>
      </div>
    </div>
  );
};

export default Login;
```

## 7. Create a Simple Home Page

Create a file `src/pages/Home.jsx`:

```jsx
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const userStr = localStorage.getItem("user");
    if (userStr) {
      setUser(JSON.parse(userStr));
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    navigate("/login");
  };

  if (!user) {
    return <div>Loading...</div>;
  }

  return (
    <div style={{ padding: "20px", maxWidth: "800px", margin: "0 auto" }}>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          marginBottom: "30px",
        }}
      >
        <h1>Fashion Store</h1>
        <div style={{ display: "flex", alignItems: "center", gap: "15px" }}>
          {user.avatarUrl && (
            <img
              src={user.avatarUrl}
              alt="Profile"
              style={{ width: "40px", height: "40px", borderRadius: "50%" }}
            />
          )}
          <div>
            <div>
              {user.firstName} {user.lastName}
            </div>
            <div style={{ fontSize: "0.8rem", color: "#666" }}>
              {user.email}
            </div>
          </div>
          <button
            onClick={handleLogout}
            style={{
              backgroundColor: "#f44336",
              color: "white",
              border: "none",
              padding: "8px 16px",
              borderRadius: "4px",
              cursor: "pointer",
            }}
          >
            Logout
          </button>
        </div>
      </div>

      <div>
        <h2>Welcome to Fashion Store!</h2>
        <p>You have successfully logged in using Google OAuth2.</p>

        <div style={{ marginTop: "20px" }}>
          <h3>Your Profile:</h3>
          <p>
            <strong>Username:</strong> {user.username}
          </p>
          <p>
            <strong>Email:</strong> {user.email}
          </p>
          <p>
            <strong>Roles:</strong> {user.roles.join(", ")}
          </p>
        </div>
      </div>
    </div>
  );
};

export default Home;
```

## 8. Setup Axios Interceptors for JWT

Create a file `src/services/api.js`:

```jsx
import axios from "axios";

const API_URL = "http://localhost:8080";

const api = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Add a request interceptor to include JWT token in headers
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add a response interceptor to handle token expiration
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      // Token expired or invalid
      localStorage.removeItem("token");
      localStorage.removeItem("user");
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export default api;
```

## 9. Create Auth Service

Create a file `src/services/auth.service.js`:

```jsx
import api from "./api";

const AuthService = {
  login: async (username, password) => {
    try {
      const response = await api.post("/api/v1/auth/login", {
        username,
        password,
      });
      if (response.data.token) {
        localStorage.setItem("token", response.data.token);
        localStorage.setItem("user", JSON.stringify(response.data.user));
      }
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  logout: () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
  },

  getCurrentUser: () => {
    const userStr = localStorage.getItem("user");
    if (userStr) return JSON.parse(userStr);
    return null;
  },

  isAuthenticated: () => {
    return localStorage.getItem("token") !== null;
  },
};

export default AuthService;
```

## 10. Testing the Integration

1. Start your Spring Boot backend
2. Start your React frontend with `npm run dev`
3. Navigate to the login page
4. Click on the "Sign in with Google" button
5. Complete the Google authentication
6. You should be redirected back to your application's home page with your Google profile information displayed
