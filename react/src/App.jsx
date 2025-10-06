import AuthProvider from "./AuthProvider";
import Router from "./Router";

function App() {
    return (
        <AuthProvider>
            <Router/>
        </AuthProvider>
    );
}

export default App
