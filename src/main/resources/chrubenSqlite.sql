-- Habilitar el soporte de claves foráneas (en SQLite vienen desactivadas por defecto)
PRAGMA foreign_keys = ON;

-- ---------------------------------------------------------
-- Eliminación de tablas existentes
-- ---------------------------------------------------------
DROP TABLE IF EXISTS member_role;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS team_category;
DROP TABLE IF EXISTS playing_position;

-- ---------------------------------------------------------
-- Creación de tablas
-- ---------------------------------------------------------
CREATE TABLE playing_position (
    position_id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name          TEXT NOT NULL,
    CONSTRAINT uq_playing_position_name UNIQUE (name)
);

CREATE TABLE team_category (
    category_id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name          TEXT NOT NULL,
    CONSTRAINT uq_team_category_name UNIQUE (name)
);

CREATE TABLE department (
    department_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name          TEXT NOT NULL,
    CONSTRAINT uq_department_name UNIQUE (name)
);

-- Un rol pertenece a un departamento (ej. "Assistant Coach" en "U-12 Academy")
CREATE TABLE role (
    role_id       INTEGER PRIMARY KEY AUTOINCREMENT,
    role_name     TEXT NOT NULL,
    department_id INTEGER NOT NULL,
    CONSTRAINT uq_role_name_department UNIQUE (role_name, department_id),
    CONSTRAINT fk_role_department FOREIGN KEY (department_id)
        REFERENCES department (department_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- ---------------------------------------------------------
-- Socios
-- ---------------------------------------------------------
CREATE TABLE member (
    member_id    INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name   TEXT NOT NULL,
    last_name    TEXT NOT NULL,
    birth_year   INTEGER NOT NULL,
    join_date    TEXT NOT NULL, -- Formato recomendado: 'YYYY-MM-DD'
    leave_date   TEXT NULL,     -- Formato recomendado: 'YYYY-MM-DD'
    position_id  INTEGER NULL,
    category_id  INTEGER NULL,
    CONSTRAINT fk_member_position FOREIGN KEY (position_id)
        REFERENCES playing_position (position_id)
        ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_member_category FOREIGN KEY (category_id)
        REFERENCES team_category (category_id)
        ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT ck_member_dates CHECK (leave_date IS NULL OR leave_date >= join_date),
    CONSTRAINT ck_member_birth_year CHECK (birth_year BETWEEN 1900 AND 2100)
);

CREATE INDEX idx_member_name ON member (last_name, first_name);
CREATE INDEX idx_member_category ON member (category_id);

-- ---------------------------------------------------------
-- Asignación de roles a socios (histórico)
-- ---------------------------------------------------------
CREATE TABLE member_role (
    member_role_id INTEGER PRIMARY KEY AUTOINCREMENT,
    member_id      INTEGER NOT NULL,
    role_id        INTEGER NOT NULL,
    start_date     TEXT NOT NULL, -- Formato recomendado: 'YYYY-MM-DD'
    end_date       TEXT NULL,     -- Formato recomendado: 'YYYY-MM-DD'
    CONSTRAINT uq_member_role_start UNIQUE (member_id, role_id, start_date),
    CONSTRAINT fk_member_role_member FOREIGN KEY (member_id)
        REFERENCES member (member_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_member_role_role FOREIGN KEY (role_id)
        REFERENCES role (role_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT ck_member_role_dates CHECK (end_date IS NULL OR end_date >= start_date)
);

CREATE INDEX idx_member_role_role ON member_role (role_id);

-- ---------------------------------------------------------
-- Inserción de datos iniciales
-- ---------------------------------------------------------
INSERT INTO playing_position (position_id, name) VALUES
    (1, 'Center Forward'),
    (2, 'Goaltender'),
    (3, 'Defenseman');

INSERT INTO team_category (category_id, name) VALUES
    (1, 'Senior A'),
    (2, 'Women''s A'),
    (3, 'Veterans');

INSERT INTO department (department_id, name) VALUES
    (1, 'Sports'),
    (2, 'Board of Directors'),
    (3, 'U-12 Academy');

INSERT INTO role (role_id, role_name, department_id) VALUES
    (101, 'Team Captain', (SELECT department_id FROM department WHERE name = 'Sports')),
    (102, 'Board Member', (SELECT department_id FROM department WHERE name = 'Board of Directors')),
    (103, 'Assistant Coach', (SELECT department_id FROM department WHERE name = 'U-12 Academy')),
    (104, 'Treasurer', (SELECT department_id FROM department WHERE name = 'Board of Directors'));