import { apiFetch, logoutIfUnauthorized } from "./APIUtility";

function getFilterString(filterTitle, filterAuthor) {
    let filterString = "";
    if (filterTitle !== "") {
        if (filterString !== "") {
            filterString += "&";
        }
        filterString += "title=" + filterTitle;
    }
    if (filterAuthor !== "") {
        if (filterString !== "") {
            filterString += "&";
        }
        filterString += "author=" + filterAuthor;
    }

    return filterString === "" ? "" : "?" + filterString;
}

export function getBooks(filterTitle, filterAuthor, token, onSuccess = () => {}, onError = () => {}) {
    return apiFetch("http://localhost:8080/books" + getFilterString(filterTitle, filterAuthor), {
            headers: {
                "Authorization": token,
            },
        }, onSuccess, (error) => {
            if (logoutIfUnauthorized(error, logout)) {
                return;
            }

            return onError(error);
        });
}

export function getAvailableBooks(filterTitle, filterAuthor, onSuccess = () => {}, onError = () => {}) {
    return apiFetch("http://localhost:8080/books/available" + getFilterString(filterTitle, filterAuthor), {}, onSuccess, onError);
}

export function createBook(token, logout, title, author, onSuccess = () => {}, onError = () => {}) {
    return apiFetch("http://localhost:8080/books", {
            method: "POST",
            headers: {
                "Authorization": token,
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ title: title, author: author })
        }, onSuccess, (error) => {
            if (logoutIfUnauthorized(error, logout)) {
                return;
            }

            return onError(error);
        });
}

export function requestBook(token, id, logout, getUserInfo, onSuccess = () => {}, onError = () => {}) {
    return apiFetch("http://localhost:8080/requests/book/" + id, {
            method: "POST",
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

export function sendReview(token, id, review, logout, getUserInfo, onSuccess = () => {}, onError = () => {}) {
    return apiFetch("http://localhost:8080/reviews", {
            method: "POST",
            headers: {
                "Authorization": token,
                "Content-Type": "application/json",
            },
            body: JSON.stringify({bookId: id, review: review})
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
