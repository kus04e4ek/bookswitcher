import DisplayBook from "./DisplayBook";
import "./DisplayBooks.css";

function DisplayBooks({ books, requestData = () => {} }) {
    return (
        <>
            {books.map(book => (
                <div className="book-container" key={book.id}>
                    <DisplayBook book={book} requestData={requestData} />
                </div>
            ))}
        </>
    );
}

export default DisplayBooks
