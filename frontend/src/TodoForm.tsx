import { useState } from "react"
import { Todo } from "./model";
import './TodoForm.css';

interface TodoFromProps {
    onTodoCreation: (todos: Array<Todo>) => void;
}

export default function TodoForm(props: TodoFromProps) {

    const [task, setTask] = useState('');
    const [description, setDescription] = useState('');
    const [errorMessage, setErrorMessage] = useState("");

    const addTask = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                task: task,
                description: description
            })
        })
        .then(response => {
            if (response.status >= 200 && response.status < 300) {
                return response.json();
            }
            throw new Error("Das ToDo konnte nicht hinzugefügt werden")
        })
        .then((todosFromBackend: Array<Todo>) => {
            setTask('');
            setDescription('');
            props.onTodoCreation(todosFromBackend);
        })
        .catch(e => setErrorMessage(e.message))
    }

    return (
        <div>
            <div className="error">{errorMessage}</div>
            <input id="text-input" type="text" placeholder="Aufgabe" value={task} onChange={ev => setTask(ev.target.value)} />
            <input id="text-input" type="text" placeholder="Beschreibung" value={description} onChange={ev => setDescription(ev.target.value)} className="description-field" />
            <button id="button-send" data-testid="add-button" onClick={addTask} className="send-button">Senden</button>
        </div>
    )
}