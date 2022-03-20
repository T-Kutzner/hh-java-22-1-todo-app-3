import { useState, useEffect } from "react"
import { Todo } from "./model";
import './TodoForm.css';

interface TodoFormProps {
    onTodoCreation: (todos: Array<Todo>) => void;
}

export default function TodoForm(props: TodoFormProps) {

    const [task, setTask] = useState(localStorage.getItem('task') ?? '');
    const [description, setDescription] = useState(localStorage.getItem('description') ?? '');
    const [errorMessage, setErrorMessage] = useState("");

    useEffect(() => {
        setTimeout(() => setErrorMessage(''), 10000)
        localStorage.setItem('task', task)
        localStorage.setItem('description', description)
    } , [task, description]);

    const addTask = () => {
        const token = localStorage.getItem("token")
        setTask('')
        setDescription('')

        fetch(`${process.env.REACT_APP_BASE_URL}/todos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify({
                "task": task,
                "description": description
            })
        })
        .then(response => {
            if (response.status >= 200 && response.status < 300) {
                return response.json();
            }
            throw new Error("The ToDo could not be added!")
        })
        .then((todosFromBackend: Array<Todo>) => {
            props.onTodoCreation(todosFromBackend);
        })
        .catch(e => setErrorMessage(e.message))
    }

    return (
        <div>
            <div className="error">{errorMessage}</div>
            <input id="text-input" type="text" placeholder="Aufgabe" value={task} onChange={ev => setTask(ev.target.value)} />
            <input id="text-input" type="text" placeholder="Beschreibung" value={description} onChange={ev => setDescription(ev.target.value)} />
            <button id="button-send" data-testid="add-button" onClick={addTask} className="send-button">add ToDo</button>
        </div>
    )
}