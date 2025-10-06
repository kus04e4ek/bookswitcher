import DisplayBooks from "./DisplayBooks";
import DisplayReviews from "./DisplayReviews";
import DisplayRequests from "./DisplayRequests";
import "./DisplayUser.css";

function DisplayUser({ user }) {
    return (
        <div className="user-container">
            <div className="user-info">
                <h1>{user.username}</h1>
                <p>Роль: {user.admin ? "Админ" : "Пользователь"}</p>
                <p>Город: {user.city}</p>
            </div>
            {user.books !== null && user.books.length !== 0 &&
                <div className="user-additional-info">
                    <h3>Книги, выложенные пользователем</h3>
                    <DisplayBooks books={user.books} />
                </div>
            }
            {user.requestsGot !== null && user.requestsGot.length !== 0 &&
                <div className="user-additional-info">
                    <h3>Полученные запросы</h3>
                    <DisplayRequests requests={user.requestsGot} />
                </div>
            }
            {user.requestsSent !== null && user.requestsSent.length !== 0 &&
                <div className="user-additional-info">
                    <h3>Отправленные запросы</h3>
                    <DisplayRequests requests={user.requestsSent} />
                </div>
            }
            {user.reviews !== null && user.reviews.length !== 0 &&
                <div className="user-additional-info">
                    <h3>Отзывы пользователя</h3>
                    <DisplayReviews reviews={user.reviews} />
                </div>
            }
        </div>
    );
}

export default DisplayUser
