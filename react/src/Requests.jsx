import { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { getRequests } from "./RequestsAPI";
import { apiErrorToString } from "./APIUtility";
import AuthContext from "./AuthContext";
import DisplayRequests from "./DisplayRequests";
import "./Requests.css";

function Requests() {
    const navigate = useNavigate();

    const [requests, setRequests] = useState([]);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const {token, userInfo} = useContext(AuthContext);

    const loadData = () => {
        getRequests(token, navigate, (data) => {
            setRequests(data);
            setLoading(false);
        }, (error) => {
            setError(apiErrorToString(error));
            setLoading(false);
        });
    };

    useEffect(() => {
        if (token !== null && userInfo === null) {
            return;
        }

        if (userInfo === null || !userInfo.admin) {
            navigate("/");
            return;
        }

        loadData();
    }, [token, userInfo]);

    return (
        <div id="requests-viewer">
            <h1>Запросы</h1>
            <div id="requests-container">
                {(loading || error !== null) ?
                    (loading ?
                        <p>Загрузка запросов...</p> :
                        <p><strong>Ошибка:</strong> {error}</p>
                    ) :
                    <DisplayRequests requests={requests} />
                }
            </div>
        </div>
    );
}

export default Requests
