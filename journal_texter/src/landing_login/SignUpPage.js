import React, {useState} from 'react';
import TextBox from "../general_components/TextBox";
import '../css/LoginPage.css'
import {useHistory} from "react-router-dom";

function SignUpPage() {
    const [username, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [passwordConfirmation, setPasswordConfirmation] = useState("");
    const history = useHistory();

    function handleClick() {
        console.log(username);
        console.log(password);
        console.log(passwordConfirmation);
        history.push('/login');
    }

    return (
        <div className="loginBox">
            <h1>JournalTexter</h1>
            <div className="textboxes">
                <TextBox text="Username" change={setUserName} type="text"/>
                <TextBox text="Password" change={setPassword} type="password"/>
                <TextBox text="Confirm Password" change={setPasswordConfirmation} type="password"/>
            </div>
            <p className="button">
                <button onClick={handleClick}>Submit</button>
            </p>
        </div>
    );
}

export default SignUpPage;