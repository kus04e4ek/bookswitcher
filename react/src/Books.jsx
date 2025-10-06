import { useState, useEffect, useContext } from "react";
import { getAvailableBooks, createBook, getBooks } from "./BooksAPI";
import { apiErrorToString } from "./APIUtility";
import AuthContext from "./AuthContext";
import DisplayBooks from "./DisplayBooks";
import "./Books.css";

const savedTitle = localStorage.getItem("booksTitle") === null ? "" : localStorage.getItem("booksTitle");
const savedAuthor = localStorage.getItem("booksAuthor") === null ? "" : localStorage.getItem("booksAuthor");
const savedFilterTitle = localStorage.getItem("booksFilterTitle") === null ? "" : localStorage.getItem("booksFilterTitle");
const savedFilterAuthor = localStorage.getItem("booksFilterAuthor") === null ? "" : localStorage.getItem("booksFilterAuthor");

function Books({ showUnavailable = false }) {
    const [books, setBooks] = useState([]);

    const [title, _setTitle] = useState(savedTitle);
    const [author, _setAuthor] = useState(savedAuthor);

    const [filterTitle, _setFilterTitle] = useState(savedFilterTitle);
    const [filterAuthor, _setFilterAuthor] = useState(savedFilterAuthor);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const {token, logout} = useContext(AuthContext);

    const setTitle = (title) => {
        _setTitle(title);
        localStorage.setItem("booksTitle", title);
    };

    const setAuthor = (author) => {
        _setAuthor(author);
        localStorage.setItem("booksAuthor", author);
    };

    const setFilterTitle = (filterTitle) => {
        _setFilterTitle(filterTitle);
        localStorage.setItem("booksFilterTitle", filterTitle);
    };

    const setFilterAuthor = (filterAuthor) => {
        _setFilterAuthor(filterAuthor);
        localStorage.setItem("booksFilterAuthor", filterAuthor);
    };

    const loadData = () => {
        let succesHandler = (data) => {
                setBooks(data);
                setLoading(false);
            };
        let errorHandler = (error) => {
                setError(apiErrorToString(error));
                setLoading(false);
            };

        if (showUnavailable) {
            getBooks(filterTitle, filterAuthor, token, succesHandler, errorHandler);
        } else {
            getAvailableBooks(filterTitle, filterAuthor, succesHandler, errorHandler);
        }
    };

    const sendData = (e) => {
        e.preventDefault();

        createBook(token, logout, title, author, loadData, (error) => setError(apiErrorToString(error)));
    };

    useEffect(loadData, [filterTitle, filterAuthor, showUnavailable]);

    return (
        <div id="book-viewer">
            <h1>Книги</h1>
            {token !== null && !showUnavailable &&
                <div id="book-sender-form-container">
                    <form id="book-sender-form" onSubmit={sendData}>
                        <label>Название:</label>
                        <input onChange={(e) => setTitle (e.target.value)} value={title}  placeholder="Название..."   required />
                        <label>Имя автора:</label>
                        <input onChange={(e) => setAuthor(e.target.value)} value={author} placeholder="Имя автора..." required />
                        <button>Добавить книгу</button>
                    </form>
                </div>
            }
            <div id="book-viewer-content">
                <div id="filter-container">
                    <h3>Фильтры</h3>
                    <label>Название:</label>
                    <input onChange={(e) => setFilterTitle (e.target.value)} value={filterTitle}  placeholder="Название..."   />
                    <label>Имя автора:</label>
                    <input onChange={(e) => setFilterAuthor(e.target.value)} value={filterAuthor} placeholder="Имя автора..." />
                </div>
                <div id="books-container">
                    <h3>Книги</h3>
                    {(loading || error !== null) ?
                        (loading ?
                            <p>Загрузка постов...</p> :
                            <p><strong>Ошибка:</strong> {error}</p>
                        ) :
                        <DisplayBooks books={books} requestData={loadData} />
                    }
                </div>
            </div>
        </div>
    );
}

export default Books
