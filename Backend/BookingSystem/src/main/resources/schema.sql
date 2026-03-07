CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    penalized_until DATE,
    rol VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS resources (
    id UUID PRIMARY KEY,
    status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS reservations (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    resource_id UUID NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(50) NOT NULL
);

