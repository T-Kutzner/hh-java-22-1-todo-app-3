export interface Todo {
    id: string;
    task: string;
    description: string;
    status: Status;
}

export enum Status {
    OPEN = 'OPEN',
    DONE = 'DONE'
}