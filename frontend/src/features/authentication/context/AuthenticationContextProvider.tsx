import React, { Dispatch, SetStateAction, useContext, useEffect, useState } from "react"
import { IUser } from "../../../types/types";
import { Navigate, Outlet, useLocation } from "react-router-dom";
import Loader from "../../../components/Loader";

interface IAuthenticationContextType {
    user: IUser | null;
    setUser: Dispatch<SetStateAction<IUser | null>>;
    signIn: (email: string, password: string) => Promise<void>;
    signUp: (email: string, password: string) => Promise<void>;
    signOut: () => void;
}

const AuthenticationContext = React.createContext<IAuthenticationContextType | null>(null);

export const useAuthContext= () => {
    const context = useContext(AuthenticationContext);
  
    if (!context) throw Error("useAuthentication can only be used inside an AuthProvider");
    return context;
}

const AuthenticationContextProvider = () => {
  const [user, setUser] = useState<IUser | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const location = useLocation();

  const isOnAuthPage =
  location.pathname === "/authentication/sign-up" ||
  location.pathname === "/authentication/sign-in" ||
  location.pathname === "/authentication/request-password-reset";

  const signIn = async (email: string, password: string) => {
    const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/authentication/sign-in`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password })
    });

    if(response.ok) {
        const { accessToken } = await response.json();
        localStorage.setItem("accessToken", accessToken);
    } else {
        const { message } = await response.json();
        throw new Error(message);
    }
  };

  const signUp = async(email: string, password: string) => {
    const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/authentication/sign-up`, { 
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password })
    });

    if(response.ok) {
        const { accessToken } = await response.json();
        localStorage.setItem("accessToken", accessToken);
    } else {
        const { message } = await response.json();
        throw new Error(message);
    }
  };

  const signOut = () => {
    localStorage.removeItem("accessToken");
    setUser(null);
  }

  const fetchUser = async () => {
    const accessToken = localStorage.getItem("accessToken");

    if (!accessToken) {
        return;
    }

    setIsLoading(true);
    
    try {
      const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/authentication/current-user`, {
          method: "GET", 
          headers: {
              Authorization: `Bearer ${accessToken}`,
              "Content-Type": "application/json"
          },
      });
  
      if(!response.ok) {
          throw new Error("Authentication failed");
      }
  
      const user = await response.json();
      setUser(user);
    } catch(e) {
        console.log(e);
    } finally {
        setIsLoading(false);
    }
  }

  useEffect(() => {
    if(user) {
        return;
    }

    fetchUser();
  }, [user, location.pathname]);

  if (isLoading) {
    return <Loader />;
  }

  if (!isLoading && !user && !isOnAuthPage) {
    return <Navigate to="/authentication/sign-in" />;
  }

  if (user && !user.emailVerified && location.pathname !== "/authentication/verify-email") {
    return <Navigate to="/authentication/verify-email" />;
  }

  if (user && user.emailVerified && location.pathname == "/authentication/verify-email") {
    return <Navigate to="/" />;
  }

  if(user && isOnAuthPage) {
    return <Navigate to={'/'} />
  }

  return (
    <AuthenticationContext.Provider value={{
        user,
        setUser,
        signUp,
        signIn,
        signOut
    }}>
        <Outlet />
    </AuthenticationContext.Provider>
  )
}

export default AuthenticationContextProvider