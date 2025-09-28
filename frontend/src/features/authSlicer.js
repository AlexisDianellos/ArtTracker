import { createSlice } from '@reduxjs/toolkit';

const initialToken = localStorage.getItem('token');

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    isLoggedIn: !!initialToken,
    token: initialToken,
  },
  reducers: {
    logout: (state) => {
      state.isLoggedIn = false;
      state.token = null;
      localStorage.removeItem('token');
    },
    login: (state, action) => {
      state.isLoggedIn = true;
      state.token = action.payload;
      localStorage.setItem('token', action.payload);
    }
  },
});

export const { logout,login } = authSlice.actions;
export default authSlice.reducer;
