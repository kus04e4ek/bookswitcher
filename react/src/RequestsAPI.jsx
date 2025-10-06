import { apiFetch, logoutIfUnauthorized } from "./APIUtility";

export function getRequests(token, navigate, onSuccess = () => {}, onError = () => {}) {
    return apiFetch("http://localhost:8080/requests", {
            method: "GET",
            headers: {
                "Authorization": token,
            },
        }, onSuccess, (error) => {
            if (error.status === 401) {
                navigate("/");
                return;
            }

            return onError(error);
        });
}

export function giveBook(token, id, logout, getUserInfo, onSuccess = () => {}, onError = () => {}) {
    return apiFetch("http://localhost:8080/requests/" + id + "/give", {
            method: "PATCH",
            headers: {
                "Authorization": token,
            },
        }, (data) => {
            return getUserInfo(token)
                .then((userInfo) => onSuccess(data, userInfo))
                .catch((error) => onError(error, data));
        }, (error) => {
            if (logoutIfUnauthorized(error, logout)) {
                return;
            }

            return onError(error, null);
        });
}

export function rejectRequest(token, id, logout, getUserInfo, onSuccess = () => {}, onError = () => {}) {
    return apiFetch("http://localhost:8080/requests/" + id + "/reject", {
            method: "PATCH",
            headers: {
                "Authorization": token,
            },
        }, (data) => {
            return getUserInfo(token)
                .then((userInfo) => onSuccess(data, userInfo))
                .catch((error) => onError(error, data));
        }, (error) => {
            if (logoutIfUnauthorized(error, logout)) {
                return;
            }

            return onError(error, null);
        });
}

export function returnBook(token, id, logout, getUserInfo, onSuccess = () => {}, onError = () => {}) {
    return apiFetch("http://localhost:8080/requests/" + id + "/return", {
            method: "PATCH",
            headers: {
                "Authorization": token,
            },
        }, (data) => {
            return getUserInfo(token)
                .then((userInfo) => onSuccess(data, userInfo))
                .catch((error) => onError(error, data));
        }, (error) => {
            if (logoutIfUnauthorized(error, logout)) {
                return;
            }

            return onError(error, null);
        });
}
