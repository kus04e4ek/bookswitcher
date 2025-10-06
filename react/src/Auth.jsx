import { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { apiErrorToString } from "./APIUtility";
import AuthContext from "./AuthContext";
import "./Auth.css";

const savedUsername = localStorage.getItem("authUsername") === null ? "" : localStorage.getItem("authUsername");
const savedPassword = localStorage.getItem("authPassword") === null ? "" : localStorage.getItem("authPassword");
const savedCity = localStorage.getItem("authCity") === null ? "" : localStorage.getItem("authCity");

function Auth({ isRegistering }) {
    const navigate = useNavigate();

    const [username, _setUsername] = useState(savedUsername);
    const [password, _setPassword] = useState(savedPassword);
    const [city, _setCity] = useState(savedCity);

    const [error, setError] = useState(null);

    const {token, register, login} = useContext(AuthContext);

    const findAllInputChildren = (onFound) => {
        for (let child of document.getElementById("auth-form").children) {
            if (child.tagName === "INPUT") {
                onFound(child);
            }
        }
    };

    const deleteCustomValidity = () =>  findAllInputChildren(((child) => child.setCustomValidity("")));

    const setUsername = (username) => {
        deleteCustomValidity();
        _setUsername(username);
        localStorage.setItem("authUsername", username);
    };

    const setPassword = (password) => {
        deleteCustomValidity();
        _setPassword(password);
        localStorage.setItem("authPassword", password);
    };

    const setCity = (city) => {
        deleteCustomValidity();
        _setCity(city);
        localStorage.setItem("authCity", city);
    };

    const preFormAction = (isButtonRegistering) => {
        if (isRegistering != isButtonRegistering) {
            deleteCustomValidity();
            findAllInputChildren(((child) => child.required = false));
        }
    };

    const formAction = async (isButtonRegistering) => {
        if (isRegistering != isButtonRegistering) {
            findAllInputChildren(((child) => child.required = true));
            navigate(isButtonRegistering ? "/register" : "/login");
            return;
        }
    
        return isRegistering ?
            await register(username, password, city, () => navigate("/profile"), (error) => {
                if (error.status === 409) {
                    const username = document.getElementById("username");
                    username.setCustomValidity("Имя пользователя уже занято");
                    username.reportValidity();
                    return;
                }
                setError(apiErrorToString(error));
            }) :
            await login(username, password, () => navigate("/profile"), (error) => {
                if (error.status === 401) {
                    const username = document.getElementById("username");
                    const password = document.getElementById("password");

                    const message = "Неправильное имя пользователя или пароль";
                    username.setCustomValidity(message);
                    password.setCustomValidity(message);

                    username.reportValidity();
                    return;
                }
                setError(apiErrorToString(error));
            });
    };

    useEffect(() => {
        if (token !== null) {
            navigate("/profile");
            return;
        }
    }, [token]);

    return (
        <form className="window" id="auth-form">
            <h1>{isRegistering ? "Регистрация" : "Вход"}</h1>
            <label>Имя пользователя:</label>
            <input id="username" onChange={(e) => setUsername(e.target.value)} value={username} placeholder="Имя пользователя..." required/>
            <label>Пароль:</label>
            <input id="password" onChange={(e) => setPassword(e.target.value)} value={password} placeholder="Пароль..." type="password" required/>
            {isRegistering &&
                <>
                    <label>Город:</label>
                    <input id="city" onChange={(e) => setCity(e.target.value)} value={city} placeholder="Город..." required/>
                </>
            }
            <button onClick={() => preFormAction(false)} formAction={() => formAction(false)}>Войти</button>
            <button onClick={() => preFormAction(true)} formAction={() => formAction(true)}>Зарегистрироваться</button>
            {error !== null &&
                <p><strong>Ошибка:</strong> {error}</p>
            }
        </form>
    );
}

export default Auth
