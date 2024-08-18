DROP DATABASE IF EXISTS tabajara;

CREATE DATABASE tabajara;
\c tabajara;

CREATE TABLE anotacao (
    id SERIAL NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    data_hora TIMESTAMP NOT NULL DEFAULT NOW(),
    descricao TEXT NOT NULL DEFAULT '',
    cor CHAR(7) NOT NULL DEFAULT '#000000',
    foto bytea NOT NULL,

    PRIMARY KEY (id)
);