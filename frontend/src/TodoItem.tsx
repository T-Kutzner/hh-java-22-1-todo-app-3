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
        fetch(`${process.env.REACT_APP_BASE_URL}/todos/${props.todo.id}`, {
            method: 'DELETE'
        })
        .then(response => {
                if(response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error("Das ToDo konnte nicht gelöscht werden");
            })
        .then(() => props.onTodoDeletion())
        .catch(e => setErrorMessage(e.message));
    };

    const fetchToEdit = (todo: Todo) => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todos/${props.todo.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(todo)
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

    const editTodo = () => {
        fetchToEdit({
            id: props.todo.id,
            task: taskToEdit,
            description: descriptionToEdit,
            status: props.todo.status
        });
    }

    const toggle = () => {
        const newStatus = props.todo.status === Status.Open ? Status.Done : Status.Open;
        fetchToEdit({
            id: props.todo.id,
            task: props.todo.task,
            description: props.todo.description,
            status: newStatus
        });
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
                    <span className={props.todo.status === Status.Done ? 'selected': ''}
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
