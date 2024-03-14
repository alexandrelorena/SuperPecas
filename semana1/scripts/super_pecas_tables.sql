CREATE DATABASE SuperPecas;

USE SuperPecas;

CREATE TABLE IF NOT EXISTS carros (
    id INT NOT NULL AUTO_INCREMENT,
    nome_do_modelo VARCHAR(45) NULL,
    fabricante VARCHAR(45) NULL,
    codigo_unico varchar(45) NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX `codigo_unico_UNIQUE` (codigo_unico)
);

CREATE TABLE IF NOT EXISTS pecas (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(45) NULL,
    descricao VARCHAR(45) NULL,
    numero_de_serie VARCHAR(45) NULL,
    fabricante VARCHAR(45) NULL,
    modelo_do_carro VARCHAR(45) NULL,
    carro_id INT,
    PRIMARY KEY (id),
    UNIQUE INDEX `numero_de_serie_UNIQUE` (numero_de_serie),
    CONSTRAINT fk_pecas_carros
    FOREIGN KEY (carro_id) REFERENCES carros(id)    
);
