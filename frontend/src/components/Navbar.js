import React,{useState,useEffect} from 'react'
import { Link,useNavigate  } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { logout } from "../features/authSlicer";

const Navbar = () => {
  const isLoggedIn = useSelector((state) => state.auth.isLoggedIn);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  
  const handleLogout = () => {
    dispatch(logout());   // upd Redux state - login logout functionality w localstorage, redux
    navigate("/auth");    // redirect to login page
  };

  return (
    <nav className="bg-gray-900 text-white px-6 py-4 shadow-md sticky top-0 z-50">
      <div className="max-w-7xl mx-auto flex justify-between items-center">
        <Link to="/art" className="text-2xl font-bold tracking-wide">
          ArtTracker
        </Link>

        <div className="flex space-x-6">  
          <Link
            to="/art"
            className="hover:text-pink-400 transition-colors duration-300"
          >
            Gallery
          </Link>
          <Link
            to="/auth"
            className="hover:text-pink-400 transition-colors duration-300"
          >
            {isLoggedIn ? (
            <button
              onClick={handleLogout}
              className="hover:text-pink-400 transition-colors duration-300"
            >
              Logout
            </button>
          ) : (
            <Link
              to="/auth"
              className="hover:text-pink-400 transition-colors duration-300"
            >
              Login
            </Link>
          )}
          </Link>
        </div>
      </div>
    </nav>
  );
};


export default Navbar