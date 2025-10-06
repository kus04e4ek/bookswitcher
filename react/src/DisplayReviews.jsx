import { NavLink } from "react-router";
import DisplayBook from "./DisplayBook";
import "./DisplayReviews.css";

function DisplayReviews({ reviews }) {
    return (
        <>
            {reviews.map(review => (
                review.book === null ?
                    <div key={review.id} className="review">
                        {review.user !== undefined &&
                            <strong><NavLink to={"/user/" + review.userId}>{review.user.username}</NavLink>:</strong>
                        }
                        <p>{review.review}</p>
                        {review.error !== undefined ?
                            <p>{review.error}</p> :
                            (review.message !== undefined &&
                                <p>{review.message}</p>
                            )
                        }
                    </div> :
                    <div key={review.id} className="review-book">
                        <DisplayBook book={{...review.book, reviews: [{...review, book: null}]}} />
                    </div>
            ))}
        </>
    );
}

export default DisplayReviews
