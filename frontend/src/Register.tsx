import {useState} from "react";


export const Register = () => {

    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const[token, setToken] = useState("")

    const register = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/users`, {
            method: "POST",
            body: JSON.stringify({
                "email": email,
                "password": password
            }),
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Something went wrong!")
                }
                return response.text()
            })
    }

    return (
        <div>
            <input type={"text"} placeholder={"e-Mail"} value={email} onChange={e => setEmail(e.target.value)}/><br/>
            <input type={"text"} placeholder={"password"} value={password} onChange={e => setPassword(e.target.value)}/><br/>
            <button onClick={register}>Register</button>
        </div>
    )
}