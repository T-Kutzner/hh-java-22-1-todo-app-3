import { useEffect, useState } from "react"
import { Todo } from "./model";
import TodoForm from "./TodoForm";
import TodoItem from "./TodoItem";
import './TodoList.css';

export default function TodoList() {

    const [todos, setTodos] = useState([] as Array<Todo>);
    const [errorMessage, setErrorMessage] = useState("");

    const fetchAll = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todos`)
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error("Die ToDos konnten nicht abgerufen werden")
            })

            .then((todosFromBackend: Array<Todo>)  => setTodos(todosFromBackend))
            .catch(e => setErrorMessage(e.message))
    }

    const deleteChecked = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todos`, { method: 'DELETE' })
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error("Das ToDo konnte nicht gelöscht werden")
            })
            .then((todosFromBackend: Array<Todo>)  => setTodos(todosFromBackend))
            .catch(e => setErrorMessage(e.message))
    }

    useEffect(() => {
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
