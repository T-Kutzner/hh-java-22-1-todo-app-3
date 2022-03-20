import React from 'react';
import Header from './Header';
import TodoList from './TodoList';
import { BrowserRouter, Routes, Route } from "react-router-dom";

import { Login } from './Login';
import { Register } from './Register';
import Logout from "./Logout";

export default function App() {

    return (
        <div>
            <Header />
            <Logout />
            <BrowserRouter>
                <Routes>
                    <Route path={'/'} element={<TodoList/>}/>
                    <Route path={'/auth/login'} element={<Login/>}/>
                    <Route path={'/register'} element={<Register/>}/>
                </Routes>
            </BrowserRouter>
        </div>
    );
}