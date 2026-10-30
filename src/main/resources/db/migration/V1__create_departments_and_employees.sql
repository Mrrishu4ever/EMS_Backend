CREATE TABLE departments (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE employees (
    id            BIGSERIAL PRIMARY KEY,
    first_name    VARCHAR(100)   NOT NULL,
    last_name     VARCHAR(100)   NOT NULL,
    email         VARCHAR(150)   NOT NULL UNIQUE,
    phone         VARCHAR(20),
    hire_date     DATE           NOT NULL,
    salary        NUMERIC(12, 2) NOT NULL CHECK (salary > 0),
    status        VARCHAR(20)    NOT NULL,
    department_id BIGINT         NOT NULL REFERENCES departments (id),
    created_at    TIMESTAMPTZ    NOT NULL DEFAULT now(),
    updated_at    TIMESTAMPTZ    NOT NULL DEFAULT now()
);

CREATE INDEX idx_employees_department ON employees (department_id);
CREATE INDEX idx_employees_status ON employees (status);

INSERT INTO departments (name, description) VALUES
    ('Engineering', 'Software development and IT'),
    ('Human Resources', 'People and culture'),
    ('Finance', 'Accounting and payroll');