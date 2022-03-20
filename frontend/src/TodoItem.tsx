import { useState } from "react";
import { Status, Todo } from "./model";
import './TodoItem.css'

interface TodoItemProps {
    todo: Todo
    onTodoDeletion: () => void;
    onTodoChange: (todos: Array<Todo>) => void;
}

export default function TodoItem(props: TodoItemProps) {

    const [taskToEdit, setTaskToEdit] = useState(props.todo.task);
    const [descriptionToEdit, setDescriptionToEdit] = useState(props.todo.description);
    const [editMode, setEditMode] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");

    const deleteTodo = () => {
        const token = localStorage.getItem("token")
        fetch(`${process.env.REACT_APP_BASE_URL}/todos/${props.todo.id}`, {
            method: 'DELETE',
            headers: {
                "Authorization": "Bearer " + token}
        })
        .then(response => {
                if(response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error("The ToDo could not be deleted!");
            })
        .then(() => props.onTodoDeletion())
        .catch(e => setErrorMessage(e.message));
    };

    const editTodo = () => {
        const token = localStorage.getItem("token")
        fetch(`${process.env.REACT_APP_BASE_URL}/todos/${props.todo.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify({
            "id": props.todo.id,
            "task": taskToEdit,
            "description": descriptionToEdit,
            "status": props.todo.status
        })
    })
        .then(response => {
            if (response.status >= 200 && response.status < 300) {
                return response.json();
            }
            throw new Error("Das ToDo konnte nicht editiert werden");
        })
        .then((todosFromBackend: Array<Todo>) => {
            props.onTodoChange(todosFromBackend);
            setEditMode(false);
        })
        .catch(e => setErrorMessage(e.message));
    }

    const toggle = () => {
        const token = localStorage.getItem("token")
        const newStatus = props.todo.status === Status.OPEN ? Status.DONE : Status.OPEN;
        fetch(`${process.env.REACT_APP_BASE_URL}/todos/${props.todo.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify({
            "id": props.todo.id,
            "task": props.todo.task,
            "description": props.todo.description,
            "status": newStatus
        })
    })
            .then(response => {
                if (response.ok) {
                    return response.json()
                }
                throw new Error("There is nothing to change!")

            })
            .then((todosFromBackend: Array<Todo>) => props.onTodoChange(todosFromBackend))
            .catch(e => setErrorMessage(e.message))
    }

    return (
        <div>
            <div className="error">{errorMessage}</div>
            {
                editMode
                ?
                <div>
                    <input type="text"
                           value={taskToEdit}
                           onChange={ev => setTaskToEdit(ev.target.value)}
                           onKeyUp={ev => {if (ev.keyCode === 13) { editTodo(); }}} />
                    <input type="text"
                           value={descriptionToEdit}
                           onChange={ev => setDescriptionToEdit(ev.target.value)}
                           onKeyUp={ev => {if (ev.keyCode === 13) { editTodo(); }}} />
                    <button onClick={editTodo}>Senden</button>
                </div>
                :
                <div>
                    <span className={props.todo.status === Status.DONE ? 'selected': ''}
                          onClick={toggle}>
                        {props.todo.task}: {props.todo.description}
                    </span>
                    <button id="button-edit" onClick={() => setEditMode(true)}>Edit</button>
                    <button id="button-delete" onClick={deleteTodo}>Löschen</button>
                </div>
            }
        </div>
    )
}
