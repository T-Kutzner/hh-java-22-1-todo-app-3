export default function Logout(){

    const logout = () => {
        localStorage.setItem("token", "")
    }

    return (
        <div>
            <button onClick={logout}>Logout</button>
        </div>
    )
}