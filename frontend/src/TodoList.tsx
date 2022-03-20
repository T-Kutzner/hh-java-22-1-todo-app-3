import { useEffect, useState } from "react"
import { Todo } from "./model";
import TodoForm from "./TodoForm";
import TodoItem from "./TodoItem";
import './TodoList.css';

export default function TodoList() {

    const [todos, setTodos] = useState([] as Array<Todo>);
    const [errorMessage, setErrorMessage] = useState("");
    const[deleteErrorMessage, setDeleteErrorMessage] = useState('');

    const fetchAll = () => {
        const token = localStorage.getItem("token")
        fetch(`${process.env.REACT_APP_BASE_URL}/todos`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        })
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error("The ToDos could not be retrieved!")
            })

            .then((todosFromBackend: Array<Todo>)  => setTodos(todosFromBackend))
            .catch(e => setErrorMessage(e.message))
    }

    const deleteChecked = () => {
        const token = localStorage.getItem("token")
        fetch(`${process.env.REACT_APP_BASE_URL}/todos`, {
            method: 'DELETE',
            headers: {
                "Authorization": "Bearer " + token
            }
        })
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error("The ToDo could not be deleted!")
            })
            .then((todosFromBackend: Array<Todo>)  => setTodos(todosFromBackend))
            .catch(e => setErrorMessage(e.message))
    }

    useEffect(() => {
        setTimeout(() => setErrorMessage(''), 10000)
        fetchAll();
    }, []);

    return (
        <div className="todo-list">
            <div className="error">{errorMessage}</div>
            <div>
                <TodoForm onTodoCreation={setTodos} />
            </div>
            <div>
                <button id="button-delete" onClick={deleteChecked}>Alle abgehakten löschen</button>
            </div>
            <ul>
                {todos.map(todo => <li key={todo.id}><TodoItem todo={todo} onTodoDeletion={fetchAll} onTodoChange={setTodos} /></li>)}
            </ul>
        </div>
    )
}
