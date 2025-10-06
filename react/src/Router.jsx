import { useContext } from "react";
import { BrowserRouter, Routes, Route, Link } from "react-router";
import AuthContext from "./AuthContext";
import Books from "./Books";
import User from "./User";
import Profile from "./Profile";
import Auth from "./Auth";
import Requests from "./Requests";
import "./Router.css";

function Router() {
    const {token, userInfo} = useContext(AuthContext);

    return (
        <BrowserRouter>
            <nav>
                <div className="nav-buttons">
                    <Link to="/">Главная</Link>
                    {userInfo !== null && userInfo.admin &&
                    <>
                        <Link to="/requests">Запросы</Link>
                        <Link to="/allBooks">Все книги</Link>
                    </>
                    }
                </div>
                <div className="nav-buttons">
                    {token === null ?
                        <>
                            <Link to="/register">Зарегистрироваться</Link>
                            <Link to="/login">Войти</Link>
                        </> :
                        <>
                            <Link to="/profile">Профиль</Link>
                        </>
                    }
                </div>
            </nav>
            <Routes>
                <Route path="/" element={<Books showUnavailable={false} />}/>
                <Route path="/allBooks" element={<Books showUnavailable={true} />}/>
                <Route path="/register" element={<Auth isRegistering={true} />}/>
                <Route path="/login" element={<Auth isRegistering={false} />}/>
                <Route path="/profile" element={<Profile/>}/>
                <Route path="/requests" element={<Requests/>}/>
                <Route path="/user/:id" element={<User/>}/>
            </Routes>
        </BrowserRouter>
    );
}

export default Router
