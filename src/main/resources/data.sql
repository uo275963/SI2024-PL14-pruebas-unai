--Datos para carga inicial de la base de datos

--Para giis.demo.tkrun:
--delete from carreras;
--insert into carreras(id,inicio,fin,fecha,descr) values 
--	(100,'2016-10-05','2016-10-25','2016-11-09','finalizada'),
--	(101,'2016-10-05','2016-10-25','2016-11-10','en fase 3'),
--	(102,'2016-11-05','2016-11-09','2016-11-20','en fase 2'),
--	(103,'2016-11-10','2016-11-15','2016-11-21','en fase 1'),
--	(104,'2016-11-11','2016-11-15','2016-11-22','antes inscripcion');

	
DELETE FROM CONFIGURACION;	
INSERT INTO CONFIGURACION (clave, valor) VALUES
('hora_apertura', '09:00'),
('hora_cierre', '21:00'),
('reserva_antelacion_max_dias', '15'),
('max_horas_por_dia', '4'),
('max_horas_seguidas', '2'),
('max_horas_totales_reservadas', '10'),
('max_recibos_pendientes_para_moroso', '2');

	
DELETE FROM USUARIO;
INSERT INTO USUARIO (nombre, dni, password, rol, estado, recibos_pendientes) VALUES
('Juan Pérez', '12345678A', 'password123', 'SOCIO', 'ACTIVO', 0),
('María Gómez', '87654321B', 'password456', 'SOCIO', 'MOROSO', 2),
('Carlos Sánchez', '11223344C', 'adminpass', 'ADMIN', 'ACTIVO', 0),
('Ana López', '44332211D', 'password789', 'NO_SOCIO', 'ACTIVO', 0);

DELETE FROM INSTALACION;
INSERT INTO INSTALACION (nombre, tipo, aforo_maximo, estado, precio_hora) VALUES
('Piscina Olímpica', 'piscina', 50, 'DISPONIBLE', 3.00),
('Pista de Tenis 1', 'tenis', 4, 'DISPONIBLE', 4.50),
('Sala de Spinning', 'spinning', 20, 'DISPONIBLE', 2.00);

DELETE FROM PERIODO_INSCRIPCION;
INSERT INTO PERIODO_INSCRIPCION (nombre, fecha_inicio_socios, fecha_fin_socios, fecha_fin_no_socios) VALUES
('Cuatrimestre 1', '2025-01-01', '2025-03-31', '2025-04-30'),
('Cuatrimestre 2', '2025-05-01', '2025-07-30', '2025-08-30');

DELETE FROM ACTIVIDAD;
INSERT INTO ACTIVIDAD (nombre, descripcion, instalacion_id, aforo_maximo, coste_socio, coste_no_socio, fecha_inicio, fecha_fin, dias, hora_inicio, hora_fin, periodo_inscripcion_id) VALUES
('Spinning Intensivo', 'Clase de spinning avanzada', 3, 15, 10.00, 15.00, '2025-03-01', '2025-03-31', 'Lunes,Miércoles,Viernes', '18:00', '19:00', 2),
('Torneo de Tenis', 'Competencia amateur', 2, 8, 5.00, 10.00, '2025-04-10', '2025-04-15', 'Sábado,Domingo', '09:00', '14:00', 1),
('Torneo de Padel', 'Competencia amateur', 2, 1, 6.00, 12.00, '2025-04-10', '2025-04-15', 'Lunes,Martes', '09:00', '11:00', 1);


DELETE FROM RESERVA_INSTALACION;
INSERT INTO RESERVA_INSTALACION (usuario_id, instalacion_id, fecha, hora_inicio, hora_fin, pagado) VALUES
(1, 1, '2025-02-25', '10:00', '11:00', TRUE),
(2, 2, '2025-02-26', '11:00', '12:00', FALSE),
(2, 2, '2025-03-31', '11:00', '12:00', FALSE),
(1, 2, '2025-03-27', '12:00', '13:00', TRUE),
(4, 3, '2025-03-24', '09:00', '11:00', FALSE),
(3, 3, '2025-03-25', '15:00', '16:00', TRUE);

DELETE FROM INSCRIPCION_ACTIVIDAD;
INSERT INTO INSCRIPCION_ACTIVIDAD (usuario_id, actividad_id, pagado) VALUES
(1, 1, TRUE),
(4, 2, FALSE);

DELETE FROM PAGO;
INSERT INTO PAGO (usuario_id, monto, concepto, fecha_pago) VALUES
(1, 30.00, 'Cuota mensual', '2025-02-01'),
(2, 15.00, 'Reserva Tenis', '2025-02-10');