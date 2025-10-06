import { useContext, useState } from "react";
import { NavLink } from "react-router";
import { giveBook, rejectRequest, returnBook } from "./RequestsAPI";
import { apiErrorToString } from "./APIUtility";
import AuthContext from "./AuthContext";
import DisplayBook from "./DisplayBook";
import "./DisplayRequests.css";

function toDisplayStatus(status) {
    switch(status) {
        case "SENT":
            return "Запрос отправлен";

        case "REJECTED":
            return "Запрос отказан";

        case "GAVE":
            return "Книга отдана";

        case "RETURNED":
            return "Книга возвращена";
    }
    return "Неизвестный статус: " + status;
}

function DisplayRequests({ requests }) {
    const [errors, setErrors] = useState(requests.map(() => null));

    const {token, userInfo, logout, getUserInfo} = useContext(AuthContext);

    const errorHandler = (error, requestIndex) => {
            let newError = "";
            if (error.status === 409) {
                newError = "Неправильный статус запроса";
            } else {
                newError = apiErrorToString(error);
            }

            setErrors(errors.map((error, index) => {
                if (index !== requestIndex) {
                    return error;
                }
                return newError;
            }));
        };

    const giveBookCustom = (request, index) => giveBook(token, request.id, logout, getUserInfo, () => {}, (error) => errorHandler(error, index));

    const rejectRequestCustom = (request, index) => rejectRequest(token, request.id, logout, getUserInfo, () => {}, (error) => errorHandler(error, index));

    const returnBookCustom = (request, index) => returnBook(token, request.id, logout, getUserInfo, () => {}, (error) => errorHandler(error, index));

    const displayGiveReject = requests.map(request => userInfo !== null && request.status === "SENT" && userInfo.id !== request.user.id && userInfo.books.some((v) => v.id === request.book.id) && userInfo.books.find((v) => v.id === request.book.id).holderId === userInfo.id);
    const displayReturn = requests.map(request => userInfo !== null && request.status === "GAVE" && request.userId === userInfo.id);
    const displayButtons = requests.map((request, index) => displayGiveReject[index] || displayReturn[index]);

    return (
        <div className="requests-container">
            {requests.map((request, index) => (
                <div key={request.id} className="request">
                    <div className="request-main-content">
                        <div>
                            {(userInfo === null || request.user.id !== userInfo.id) &&
                                <p><strong>Отправитель:</strong> <NavLink to={"/user/" + request.user.id}>{request.user.username}</NavLink></p>
                            }
                            <p><strong>Статус:</strong> {toDisplayStatus(request.status)}</p>
                        </div>
                        {displayButtons[index] &&
                            <div className="request-buttons">
                                {displayGiveReject[index] &&
                                    <>
                                        <button onClick={() => giveBookCustom(request, index)}>Отдать книгу</button>
                                        <button onClick={() => rejectRequestCustom(request, index)}>Отказаться отдавать</button>
                                    </>
                                }
                                {displayReturn[index] &&
                                    <button onClick={() => returnBookCustom(request, index)}>Вернуть книгу</button>
                                }
                            </div>
                        }
                        {errors[index] !== null &&
                            <p>{errors[index]}</p>
                        }
                    </div>
                    <h3>Книга</h3>
                    <DisplayBook book={request.book} showInteractive={false} />
                </div>
            ))}
        </div>
    );
}

export default DisplayRequests
