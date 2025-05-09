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
('min_horas_cancelacion', '24'),
('max_horas_por_dia', '4'),
('max_horas_seguidas', '2'),
('max_horas_totales_reservadas', '10'),
('max_recibos_pendientes_para_moroso', '2');

	
DELETE FROM USUARIO;
INSERT INTO USUARIO (nombre, dni, password, rol, estado, recibos_pendientes) VALUES
('Sara Luna', '12345678M', 'password123', 'SOCIO', 'ACTIVO', 0),
('Jonay García', '99999999H', 'password123', 'NO_SOCIO', 'ACTIVO', 0);

DELETE FROM INSTALACION;
INSERT INTO INSTALACION (nombre, tipo, aforo_maximo, estado, precio_hora) VALUES
('Pista de Tenis 1', 'tenis', 4, 'DISPONIBLE', 4.50);

DELETE FROM PERIODO_INSCRIPCION;
INSERT INTO PERIODO_INSCRIPCION (nombre, fecha_inicio_socios, fecha_fin_socios, fecha_fin_no_socios) VALUES
('Periodo abierto', '2025-04-01', '2025-05-01', '2025-06-01'),
('Periodo cerrado', '2025-05-01', '2025-06-01', '2025-07-01');

DELETE FROM ACTIVIDAD;
INSERT INTO ACTIVIDAD (nombre, descripcion, instalacion_id, aforo_maximo, coste_socio, coste_no_socio, fecha_inicio, fecha_fin, dias, hora_inicio, hora_fin, periodo_inscripcion_id) VALUES
('Torneo abierto de tenis 1', 'Competencia amateur', 1, 2, 5.00, 10.00, '2025-07-01', '2025-07-01', 'Sábado,Domingo', '09:00', '14:00', 1),
('Torneo abierto de tenis 2', 'Competencia amateur', 1, 10, 5.00, 10.00, '2025-07-01', '2025-07-01', 'Sábado,Domingo', '09:00', '14:00', 1),
('Torneo cerrado de tenis 1', 'Competencia amateur', 1, 10, 6.00, 12.00, '2025-07-01', '2025-07-01', 'Lunes,Martes', '09:00', '11:00', 2);



DELETE FROM RESERVA_INSTALACION;


DELETE FROM INSCRIPCION_ACTIVIDAD;
INSERT INTO INSCRIPCION_ACTIVIDAD (usuario_id, actividad_id, pagado) VALUES
(2, 1, TRUE);

DELETE FROM PAGO;
INSERT INTO PAGO (usuario_id, monto, concepto, fecha_pago) VALUES
(1, 30.00, 'Cuota mensual', '2025-02-01'),
(2, 15.00, 'Reserva Tenis', '2025-02-10');

DELETE FROM LISTA_ESPERA;
