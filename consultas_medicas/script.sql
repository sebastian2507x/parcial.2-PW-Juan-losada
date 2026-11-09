CREATE DATABASE IF NOT EXISTS consultas_medicas;
USE consultas_medicas;

DROP TABLE IF EXISTS consultas;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS medicos;

CREATE TABLE medicos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(60) NOT NULL,
  especialidad VARCHAR(60)
);

CREATE TABLE usuarios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(40) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  nombre VARCHAR(40) NOT NULL,
  rol VARCHAR(20) NOT NULL,
  medico_id BIGINT,
  FOREIGN KEY (medico_id) REFERENCES medicos(id)
);

CREATE TABLE consultas (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre_paciente VARCHAR(40) NOT NULL,
  motivo VARCHAR(100) NOT NULL,
  consultorio INT NOT NULL,
  hora_inicio VARCHAR(5),
  hora_fin VARCHAR(5),
  medico_id BIGINT,
  FOREIGN KEY (medico_id) REFERENCES medicos(id)
);

INSERT INTO medicos (nombre, especialidad) VALUES
('Santiago valencia', 'Medicina general'),
('Isabella pino', 'Medica'),
('Nicol covaleda', 'Enfermero');

INSERT INTO usuarios (username, password, nombre, rol, medico_id) VALUES
('admin', '$2a$10$hbj6eo2gRP4wLV7LtRyv.e/9e/6.hlddr.4bvWP1KQUrvilhffG9W', 'Administrador', 'ADMIN', NULL),
('medico1', '$2a$10$X3bUuUV0qddYuYI0SM6hwOxtIb2IQWDzYiEbaOLncPk50i9ENRWGO', 'Santiago valencia', 'MEDICO', 1),
('medico2', '$2a$10$X3bUuUV0qddYuYI0SM6hwOxtIb2IQWDzYiEbaOLncPk50i9ENRWGO', 'Isabella pino', 'MEDICO', 2),
('paciente1', '$2a$10$XtEzFUfJsiQ.hGvcCmnK3eW5VFKsSvF55J8RkmxjK5NJB21XcaKsy', 'Ana arce', 'PACIENTE', NULL),
('paciente2', '$2a$10$XtEzFUfJsiQ.hGvcCmnK3eW5VFKsSvF55J8RkmxjK5NJB21XcaKsy', 'Luis trujillo', 'PACIENTE', NULL);

INSERT INTO consultas (nombre_paciente, motivo, consultorio, hora_inicio, hora_fin, medico_id) VALUES
('Ana arce', 'Dolor de cabeza', 101, '08:00', '08:30', 1),
('Ana arce', 'Control de vacunas', 202, '10:00', '10:30', 2),
('Luis Trujillo', 'Revision general', 101, '09:00', '09:30', 1),
('Luis Trujillo', 'Dolor en el pecho', 303, '14:00', '14:45', 3);
