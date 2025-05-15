CREATE TABLE task (
    taskId UUID PRIMARY KEY,
    taskName VARCHAR(255) NOT NULL,
    taskDescription TEXT,
    taskStatus VARCHAR(50) NOT NULL,
    createdBy UUID NOT NULL,
    assignedTo UUID,
    deadline DATE,
    CONSTRAINT fk_createdBy FOREIGN KEY (createdBy) REFERENCES organizer(id),
    CONSTRAINT fk_assignedTo FOREIGN KEY (assignedTo) REFERENCES organizer(id)
);