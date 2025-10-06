export function createAPIError(status, message) {
    return {status: status, message: message};
}

export function apiFetch(url, init, onSuccess, onError) {
    return fetch(url, init)
        .then(response => {
            if (!response.ok) {
                return response.text()
                    .then(error => {
                        return onError(createAPIError(response.status, error));
                    })
                    .catch(error => {
                        return onError(createAPIError(response.status, error.message));
                    });
            }
            return response.json()
                .then(data => {
                    return onSuccess(data);
                })
                .catch(error => {
                    return onError(createAPIError(response.status, error.message));
                });
        })
        .catch(error => {
            return onError(createAPIError(null, error.message));
        });
}

export function apiErrorToString(error) {
    if (error.status !== null) {
        return error.status + ": " + error.message;
    }
    return error.message;
}

export function logoutIfUnauthorized(error, logout) {
    if (error.status === 401) {
        logout();
        return true;
    }
    return false;
}
