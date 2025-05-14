CREATE TABLE transaction (
    id UUID PRIMARY KEY,
    society_id UUID NOT NULL,
    amount NUMERIC(10) NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    description TEXT,
    title VARCHAR(255),
    date DATE NOT NULL,
    FOREIGN KEY (society_id) REFERENCES society(id)
);
