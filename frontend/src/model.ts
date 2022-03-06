export interface Todo {
    id: string;
    task: string;
    description: string;
    status: Status;
}

export enum Status {
    Open = 'Open',
    Done = 'Done'
}