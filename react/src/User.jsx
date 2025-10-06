import { useState, useEffect, useContext } from "react";
import { useParams } from "react-router";
import { useNavigate } from "react-router-dom";
import { apiErrorToString } from "./APIUtility";
import AuthContext from "./AuthContext";
import DisplayUser from "./DisplayUser";
import "./User.css";

function User() {
    const navigate = useNavigate();
    const { id } = useParams();

    const [user, setUser] = useState(null);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const {token, userInfo, getUserById} = useContext(AuthContext);

    useEffect(() => {
        setLoading(true);
        if (token !== null && userInfo === null) {
            return;
        }

        if (userInfo !== null && userInfo.id == id) {
            navigate("/profile");
            return;
        }

        if (user !== null && user.id == id) {
            setLoading(false);
            return;
        }

        getUserById(id, (data) => {
                setUser(data);
                setLoading(false);
            }, (error) => {
                setError(apiErrorToString(error));
                setLoading(false);
            })
    }, [token, userInfo, id]);

    return (
        <div id="user-container">
            {loading || error !== null ?
                (loading ?
                    <p>Загрузка пользователя...</p> :
                    <p><strong>Ошибка:</strong> {error}</p>
                ) :
                <DisplayUser user={user} />
            }
        </div>
    );
}

export default User
