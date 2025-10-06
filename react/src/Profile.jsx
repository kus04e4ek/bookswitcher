import { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { apiErrorToString } from "./APIUtility";
import AuthContext from "./AuthContext";
import DisplayUser from "./DisplayUser";
import "./Profile.css";

function Profile() {
    const navigate = useNavigate();

    const [user, setUser] = useState(null);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const {token, userInfo, userInfoError, logout} = useContext(AuthContext);

    useEffect(() => {
        setLoading(true);
        if (token === null) {
            navigate("/login");
            return;
        }
        if (userInfo === null) {
            if (userInfoError !== null) {
                setError(apiErrorToString(userInfoError));
                setLoading(false);
                return;
            }
            return;
        }

        setUser(userInfo);
        setLoading(false);
    }, [token, userInfo, userInfoError]);

    return (
        <div id="profile-container">
            <button onClick={() => {
                logout();
                navigate("/");
            }}>Logout</button>
            {(loading || error !== null) ?
                (loading ?
                    <p>Загрузка постов...</p> :
                    <p><strong>Ошибка:</strong> {error}</p>
                ) :
                <DisplayUser user={user} />
            }
        </div>
    );
}

export default Profile
