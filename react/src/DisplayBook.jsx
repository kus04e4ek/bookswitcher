import { useState, useContext } from "react";
import { NavLink } from "react-router";
import { requestBook, sendReview } from "./BooksAPI";
import { apiErrorToString } from "./APIUtility";
import AuthContext from "./AuthContext";
import DisplayReviews from "./DisplayReviews";
import "./DisplayBook.css";

function DisplayBook({ book, requestData = () => {}, showInteractive = true }) {
    const [message, setMessage] = useState(null);
    const [error, setError] = useState(null);

    const [review, setReview] = useState("");

    const {token, userInfo, logout, getUserInfo} = useContext(AuthContext);

    const requestBookCustom = () => {
        requestBook(token, book.id, logout, getUserInfo, () => {
                setMessage("Книга удачно запрошена");
            }, (error) => {
                if (error.status === 409) {
                    setError("Книга уже запрошена");
                } else {
                    setError(apiErrorToString(error));
                }
            });
    };

    const sendReviewCustom = (e) => {
        e.preventDefault();

        sendReview(token, book.id, review, logout, getUserInfo, () => {
                requestData();
            }, (error) => {
                if (error.status === 409) {
                    setError("Отзыв уже оставлен");
                } else {
                    setError(apiErrorToString(error));
                }
            });
    };

    const canInteract = showInteractive && userInfo !== null && userInfo.id !== book.userId;
    const displayReviewForm = canInteract && !userInfo.reviews.some((v) => v.bookId === book.id);
    const displayReviews = book.reviews !== null && book.reviews.length !== 0;
    const displayReviewContainer = displayReviewForm || displayReviews;

    return (
        <div className="book">
            <div className="book-main-content">
                <div>
                    <p><strong>Название:</strong> {book.title}</p>
                    <p><strong>Автор:</strong> {book.author}</p>
                    {book.user !== null &&
                        <p><strong>Пользователь:</strong> <NavLink to={"/user/" + book.user.id}>{book.user.username}</NavLink></p>
                    }
                </div>
                {canInteract && !userInfo.requestsSent.some((v) => v.bookId === book.id) &&
                    <div>
                        <button onClick={() => requestBookCustom()}>Запросить книгу</button>
                    </div>
                }
            </div>
            {displayReviewContainer &&
                <div className="reviews-all-container">
                    <h4>Отзывы</h4>
                    {displayReviewForm && 
                        <form className="review-form" onSubmit={(e) => sendReviewCustom(e)}>
                            <label><strong>Отзыв:</strong></label>
                            <input onChange={(e) => setReview(e.target.value)} value={review} placeholder="Ваш отзыв..." required />
                            <button>Отправить отзыв</button>
                        </form>
                    }
                    {displayReviews &&
                        <div className="reviews-container">
                            <DisplayReviews reviews={book.reviews} />
                        </div>
                    }
                </div>
            }
            {error !== null ?
                <p>{error}</p> :
                (message !== null &&
                    <p>{message}</p>
                )
            }
        </div>
    );
}

export default DisplayBook
