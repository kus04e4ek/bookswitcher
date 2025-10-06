import { useEffect, useState } from "react";
import { apiFetch, logoutIfUnauthorized } from "./APIUtility";
import AuthContext from "./AuthContext";

const savedToken = localStorage.getItem("token");

function AuthProvider({children}) {
    const [token, _setToken] = useState(savedToken);
    const [userInfo, setUserInfo] = useState(null);
    const [userInfoError, setUserInfoError] = useState(null);

    const setToken = async (token, onSuccess = () => {}, onError = () => {}) => {
        _setToken(token);
        localStorage.setItem("token", token);

        return await getUserInfo(token, onSuccess, onError);
    };

    const register = (username, password, city, onSuccess = () => {}, onError = () => {}) => {
        return apiFetch("http://localhost:8080/users", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    "username": username,
                    "password": password,
                    "city": city
                }),
            }, (data) => {
                return setToken(data.token)
                    .then((userInfo) => onSuccess(data, userInfo))
                    .catch((error) => onError(error, data));
            }, (error) => onError(error, null));
    };

    const login = (username, password, onSuccess = () => {}, onError = () => {}) => {
        return apiFetch("http://localhost:8080/users/token", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ "username": username, "password": password }),
            }, (data) => {
                return setToken(data.token)
                    .then((userInfo) => onSuccess(data, userInfo))
                    .catch((error) => onError(error, data));
            }, (error) => onError(error, null));
    };

    const logout = () => {
        setToken(null);
        setUserInfo(null);
        localStorage.removeItem("token");
    };

    const getUserById = (id, onSuccess = () => {}, onError = () => {}) => {
        if (userInfo !== null && id === userInfo.id) {
            return onSuccess(userInfo);
        }

        return apiFetch("http://localhost:8080/users/" + id, {}, onSuccess, onError);
    };

    const getUserInfo = (token, onSuccess = () => {}, onError = () => {}) => {
        if (token === null) {
            return;
        }

        return apiFetch("http://localhost:8080/users/token", {
                headers: {
                    "Authorization": token,
                },
            }, (data) => {
                setUserInfo(data);
                return onSuccess(data);
            }, (error) => {
                if (logoutIfUnauthorized(error, logout)) {
                    return;
                }
                setUserInfoError(error);
                return onError(error);
            });
    };

    useEffect(() => {
        getUserInfo(token);
    }, []);

    return (
        <AuthContext.Provider value={{token, userInfo, userInfoError, register, login, logout, getUserById, getUserInfo}}>
            {children}
        </AuthContext.Provider>
    );
}

export default AuthProvider
