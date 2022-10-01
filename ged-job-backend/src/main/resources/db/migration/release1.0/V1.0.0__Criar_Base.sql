/* Drop Tables */

DROP TABLE IF EXISTS pessoa CASCADE
;

DROP TABLE IF EXISTS documento CASCADE
;

/* Drop Sequences for Autonumber Columns */

DROP SEQUENCE IF EXISTS pessoa_id_seq
;

DROP SEQUENCE IF EXISTS documento_id_seq
;

/* Create Sequences */

CREATE SEQUENCE pessoa_id_seq INCREMENT 1 START 1
;

CREATE SEQUENCE documento_id_seq INCREMENT 1 START 1
;

/* Create Tables */

CREATE TABLE pessoa
(
	id integer NOT NULL DEFAULT NEXTVAL('pessoa_id_seq'),    -- Identificador da pessoa
	nome varchar(200),    -- Nome da pessoa
	cpf_cnpj varchar(14),    -- CPF da pessoa
	logradouro varchar(255),    -- Logradouro da pessoa
	data_criacao DATE			--Data de criação da pessoa
)
;

CREATE TABLE documento
(
	id integer NOT NULL DEFAULT NEXTVAL('documento_id_seq'),    -- Identificador do documento
	nome varchar(200),    		-- Nome do documento
	caminho_archive varchar(255),		-- Caminho de referencia ao documento salvo em um archive
	data_criacao DATE,			--Data de criação do documento
	id_pessoa INTEGER			--Identificador da pessoa a qual o documento pertence
)
;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE pessoa ADD CONSTRAINT pk_pessoa
	PRIMARY KEY (id)
;

ALTER TABLE documento ADD CONSTRAINT pk_documento
	PRIMARY KEY (id)
;

/* Create Foreign Key Constraints */

ALTER TABLE documento ADD CONSTRAINT fk_id_pessoa 
  FOREIGN KEY (id_pessoa) REFERENCES pessoa (id) ON DELETE No Action ON UPDATE No Action;