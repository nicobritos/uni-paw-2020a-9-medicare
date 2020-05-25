INSERT INTO system_country (country_id, name)
VALUES ('AR', 'Argentina');

INSERT INTO system_province (province_id, country_id, name)
VALUES (1, 'AR', 'Gran Buenos Aires');
INSERT INTO system_province (province_id, country_id, name)
VALUES (2, 'AR', 'Ciudad Autonoma de Buenos Aires');

INSERT INTO system_locality (province_id, name, locality_id)
VALUES (2, 'Ciudad Autonoma de Buenos Aires', 1);
INSERT INTO system_locality (province_id, name, locality_id)
VALUES (1, 'San Isidro', 3);
INSERT INTO system_locality (province_id, name, locality_id)
VALUES (1, 'Escobar', 11);
INSERT INTO system_locality (province_id, name, locality_id)
VALUES (1, 'Martínez', 2);
INSERT INTO system_locality (province_id, name, locality_id)
VALUES (1, 'Acassuso', 10);
INSERT INTO system_locality (province_id, name, locality_id)
VALUES (1, 'Olivos', 4);
INSERT INTO system_locality (province_id, name, locality_id)
VALUES (1, 'Vicente López', 13);
INSERT INTO system_locality (province_id, name, locality_id)
VALUES (1, 'Florida', 8);
INSERT INTO system_locality (province_id, name, locality_id)
VALUES (1, 'Gral. Pacheco', 7);
INSERT INTO system_locality (province_id, name, locality_id)
VALUES (1, 'Lomas de San Isidro', 14);
INSERT INTO system_locality (province_id, name, locality_id)
VALUES (1, 'Nordelta', 6);
INSERT INTO system_locality (province_id, name, locality_id)
VALUES (1, 'Tigre', 5);
INSERT INTO system_locality (province_id, name, locality_id)
VALUES (1, 'San Fernando', 12);
INSERT INTO system_locality (province_id, name, locality_id)
VALUES (1, 'Dique Luján', 9);

INSERT INTO system_staff_specialty (specialty_id, name)
VALUES (1, 'Oftalmologia');
INSERT INTO system_staff_specialty (specialty_id, name)
VALUES (2, 'Pediatria');
INSERT INTO system_staff_specialty (specialty_id, name)
VALUES (3, 'Otorrinolaringologia');

INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (1, 'Consultorio de Arteaga', 'Pje. de las Ciencias 75 Ed. North Coral 4° "45"', 6, '4871-5412', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (2, 'Consultorio de Biain', 'Ladislao 2 253 P.B. "4"', 2, '4792-2311 y 15-5347-5624', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (3, 'Consultorio de Castello', 'Bv. de Todos los Santos 20', 9, '(054-011) 6091-3380/21', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (4, 'Consultorio de Di Stefano', 'Ladislao 2 253 P.B. "4"', 2, '4792-2311', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (5, 'Consultorio de Diamint', 'Juan N. Madero 1016 P.B.', 12, '4744-1486', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (6, 'Consultorio de Etcheverría', 'C. Bancalari 3901 Paseo Comercial Santa Bárbara 2° "31"', 7,
        '4736-8476 - 4736-8705', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (7, 'Consultorio de Fernández Meijide', 'Urquiza 1925', 8, '4791-8915 y 4796-2012', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (8, 'Consultorio de Fernández Meijide', 'Urquiza 1925', 8, '4791-8915 y 4796-2012', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (9, 'Consultorio de Ferrante', 'Av. Del Libertador 15615 P.B.', 3, '4743-4440', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (10, 'Consultorio de Fuentes', 'Av. de los Lagos 6855 Ed. Puerta Norte I 3° "304"', 6, '(15)5378-5052', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (11, 'Consultorio de González Santos', 'Av. Del Libertador 1927', 12, '4744-5774 y 4549-0145', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (12, 'Consultorio de Gossn', 'Alvear 260 2° "N"', 2, '4792-9508', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (13, 'Consultorio de Gossn', 'Alvear 260 2° "N"', 2, '4792-9508', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (14, 'Consultorio de Jucht', 'Av. Maipú 1229', 13, '4795-4322 y 4796-2672', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (15, 'Consultorio de Kohn', 'Juan B. Alberdi 1535 P.B.', 4, '4794-2861', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (16, 'Consultorio de Lima', 'Albarellos 509 1°', 5, '4731-0526', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (17, 'Consultorio de Marconi', 'Alvear 255 P.B. "A"', 2, '4792-3965 y 4793-1065', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (18, 'Consultorio de Melgem', 'Bv. del Mirador 220 Paseo de la Bahía Studios I 3° "10"', 6,
        '15-3135-3194 15-6590-3003', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (19, 'Consultorio de Menéndez Padrón', 'Rivadavia 1124', 12, '(054-011) 4725-2494', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (20, 'Consultorio de Montana', 'Bv. del Mirador 430 Terraza de la Bahía II 4° "7"', 6,
        '4871-7161 y 15-3681-3922', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (21, 'Consultorio de Muzzin', 'Av. Hipólito Yrigoyen 1123', 7, '4740-6803', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (22, 'Consultorio de Palazzolo', 'Entre Ríos 729', 7, '4740-2551', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (23, 'Consultorio de Pena', 'Av. Fondo de la Legua 577', 14, '4763-7700 y 4735-8100', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (24, 'Consultorio de Pérez Morales', 'Hilarión de la Quintana 782', 8, '4837-3700', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (25, 'Consultorio de Pianciola', 'Av. Santa Fe 436', 10, '(054-011) 4747-5183', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (26, 'Consultorio de Raineri', 'Hipólito Yrigoyen 571', 11, '0348-4421974', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (27, 'Consultorio de Rempel', '25 de Mayo 1247', 12, '4746-5676', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (28, 'Consultorio de Acosta', 'Juncal 2345 2°', 1, '4826-8282', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (29, 'Consultorio de Alezzandrini', 'Av. Córdoba 1830', 1, '4812-3618', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (30, 'Consultorio de Andersson', 'Larrea 1035 3° "A"', 1, '4827-5130 - 4827-5131', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (31, 'Consultorio de Baini', 'José Cubas 3341', 1, '4572-0222', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (32, 'Consultorio de Bar', 'Bulnes 1960 10° "38"', 1, '4823-4336 y 4827-2890', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (33, 'Consultorio de Barreiro', 'Manuel Ugarte 2164 1° "A"', 1, '4788-5180 y 15-5946-9566', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (34, 'Consultorio de Benedetto', 'Murguiondo 4240/44', 1, '4602-3947', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (35, 'Consultorio de Bermejo', 'Av. Santa Fe 1731 2° Cpo. 1° "D"', 1, '4500-8105', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (36, 'Consultorio de Bolatti', 'Arenales 1662 2° "A"', 1, '4813-1879 Fax 4813-1914', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (37, 'Consultorio de Braillard', 'Larrea 1035 3° "A"', 1, '4827-5130 - 4827-5131', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (38, 'Consultorio de Buonsanti', 'Larrea 1332', 1, '4822-1335 y 4824-8943', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (39, 'Consultorio de Carminatti', 'Olleros 2410 1°', 1, '0810-777-3636 y 4777-8232', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (40, 'Consultorio de Cassagne', 'Juncal 2134 P.B. "A"', 1, '4821-7280', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (41, 'Consultorio de Cavallari', 'Av. Entre Ríos 850', 1, '4816-9001/02 y 4381-0281', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (42, 'Consultorio de Cervini', 'Timoteo Gordillo 148', 1, '4641-5382 y 15-3782-2492', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (43, 'Consultorio de Chiapero de Gamio', 'Jorge Newbery 1519 1°', 1, '4774-0064 y 4779-0409', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (44, 'Consultorio de Cid Tesouro', 'Av. García del Río 2879 P.B.', 1, '4701-9122', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (45, 'Consultorio de Coletti', 'Timoteo Gordillo 106', 1, '4641-3331', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (46, 'Consultorio de Cortalezzi', 'Malabia 2283 7° "31"', 1, '4832-2924 y 4834-6503', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (47, 'Consultorio de Cotella', 'Av. Santa Fe 1179 10°', 1, '4811-6779 - 4811-1930', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (48, 'Consultorio de Coussio', 'Av. Córdoba 2019 1°', 1, '4963-5649/50 Fax 4961-8021', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (49, 'Consultorio de Cremona', 'Tomás M. de Anchorena 1183 P.B.', 1, '4962-2208 - 4962-0650', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (50, 'Consultorio de Cuomo', 'Av. Federico Lacroze 2252 7° "A"', 1, '5711-5217 y 4784-5107', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (51, 'Consultorio de Damel', 'Azcuénaga 960 P.B. "A"', 1, '4961-7333', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (52, 'Consultorio de Di Nisio', 'Uruguay 766 4° "24"', 1, '4371-5460/9740 y 4372-8063', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (53, 'Consultorio de Diez Olea', 'Pacheco 2542 6° "C"', 1, '(054-011) 4518-4457', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (54, 'Consultorio de Domínguez', 'Av. Rivadavia 5868 1° "A"', 1, '4431-9861 Fax 4654-7634', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (55, 'Consultorio de Echeverría', 'Maure 1629 7° "30"', 1, '4774-8692', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (56, 'Consultorio de Etcheverry', 'Juncal 2345 2°', 1, '4826-8282', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (57, 'Consultorio de Fernández Muriano', 'Sánchez de Bustamante 1754 P.B. "B"', 1, '4826-6684 y 4821-1227',
        null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (58, 'Consultorio de Fontana', 'Lafinur 2974 P.B. "A"', 1, '4805-1164', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (59, 'Consultorio de Fridrich', 'Larrea 1007 3° "B"', 1, '4824-5891', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (60, 'Consultorio de Galmarini', 'Ayacucho 1268 P.B. "2"', 1, '4821-6625 - 4821-1996', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (61, 'Consultorio de García Soto', 'Av. Cabildo 173', 1, '4771-5736', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (62, 'Consultorio de Gasparini', 'Av. Santa Fe 1707 1°', 1, '4813-1978', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (63, 'Consultorio de Abramovich', 'Av. San Pedrito 168 P.B. "4"', 1, '4612-8265 4637-8504 15-5802-1551', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (64, 'Consultorio de Abudara', 'Av. Del Libertador 4992 P.B. "1"', 1, '4772-4391/0857 y 15-4413-9242', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (65, 'Consultorio de Acerenza', 'Morelos 92', 1, '4633-3691 y 4631-5382', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (66, 'Consultorio de Acuña', 'Av. Avellaneda 2111 P.B.', 1, '4631-9151', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (67, 'Consultorio de Agosta', 'Zamudio 4539', 1, '4572-0930', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (68, 'Consultorio de Aguerre', 'Av. La Plata 241 2° "C"', 1, '4902-0539', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (69, 'Consultorio de Alapont Montoya', 'Céspedes 2410 2° "B"', 1, '4786-1457', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (70, 'Consultorio de Albanese', 'Av. Santa Fe 1955 7° "H"', 1, '4811-6841', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (71, 'Consultorio de Albano', 'Av. Emilio Castro 7284', 1, '4686-0862', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (72, 'Consultorio de Albores', 'Agüero 1972 P.B.', 1, '4822-9648 4829-0705 4821-7320', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (73, 'Consultorio de Alemán', 'Riobamba 1142 P.B.', 1, '4812-6599', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (74, 'Consultorio de Alfie', 'Av. Nazca 74 2° "D"', 1, '4637-1310 y 4903-4794', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (75, 'Consultorio de Alonso', 'Estomba 3005', 1, '4541-8192 y 4545-0675', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (76, 'Consultorio de Altcheh', 'Maza 1032', 1, '4957-3928', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (77, 'Consultorio de Andrade', 'Julián Álvarez 1351 1° "8"', 1, '4831-9826', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (78, 'Consultorio de Antar', 'Arenales 3069 P.B. "A"', 1, '15-3353-9771 15-5966-8492', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (79, 'Consultorio de Aprigliano', 'Av. Olazábal 4777 1°', 1, '4524-1994 y 4521-5635', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (80, 'Consultorio de Arakelian', 'Av. Santa Fe 2534 1° "B"', 1, '5275-0433', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (81, 'Consultorio de Arazi Caillaud', 'Gascón 79', 1, '4981-1935', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (82, 'Consultorio de Attie', 'Av. Crámer 1718 1° "F"', 1, '4783-2504', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (83, 'Consultorio de Balanzat', 'Arcos 2070 P.B. "B"', 1, '4784-8450 - 4784-8439', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (84, 'Consultorio de Ballester', 'Yerbal 2130', 1, '4632-9724 y 4431-8903', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (85, 'Consultorio de Barabini', 'Av. Rivadavia 5170 8° "B"', 1, '4901-3076', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (86, 'Consultorio de Barassi', 'Av. Cabildo 1124 1° "C"', 1, '4784-4633 y 4783-0428', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (87, 'Consultorio de Barletta', 'Marcelo T. de Alvear 1987 3° "A"', 1, '4814-2228 4723-6839 15-4444-6555', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (88, 'Consultorio de Barrera', 'Otamendi 629 1°', 1, '4981-7816 4982-8184 4940-4687', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (89, 'Consultorio de Barvosa', 'Av. Rivadavia 7047 2° "D"', 1, '4611-5392 y 15-4973-5579', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (90, 'Consultorio de Basadoni', 'Tapalqué 6018 1°', 1, '4687-1637', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (91, 'Consultorio de Amaya', 'Cnel. Ramón L. Falcón 2181 2°', 1, '4633-6058 y 4632-4741', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (92, 'Consultorio de Amblard', 'Laprida 1941 P.B. "3"', 1, '4805-7229 y 4755-0607', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (93, 'Consultorio de Ane', 'Castex 3293', 1, '4807-4748', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (94, 'Consultorio de Arauz', 'Tte. Gral. Juan D. Perón 2238 1°', 1, '4954-5929', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (95, 'Consultorio de Azocar', 'Habana 3567', 1, '4504-2706', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (96, 'Consultorio de Badaracco', 'French 3571 P.B.', 1, '4801-2764 - 4801-4285', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (97, 'Consultorio de Barbón', 'Arenales 2714 1° "A"', 1, '4824-7865', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (98, 'Consultorio de Biondi', 'Tucumán 1863', 1, '4813-2721', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (99, 'Consultorio de Cafaro', 'Av. Francisco Beiró 4294 P.B. "B"', 1, '4502-1920', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (100, 'Consultorio de Castro', 'Av. Cabildo 3011 7° "G"', 1, '(15)4960-5226', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (101, 'Consultorio de Ciceran', 'Av. Rivadavia 5170 3° "B"', 1, '4902-3310', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (102, 'Consultorio de Clemente', 'Ávalos 1962 1° "B"', 1, '4521-6651', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (103, 'Consultorio de Cordero', 'Azcuénaga 1064 4° "A"', 1, '4826-1914', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (104, 'Consultorio de Cordero', 'Azcuénaga 1064 4° "A"', 1, '4826-0604', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (105, 'Consultorio de Cremonese', 'Hamburgo 3372', 1, '4574-3057', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (106, 'Consultorio de Cupitó', 'Av. Santa Fe 3071 1° "A"', 1, '4824-6687 4792-9619 4825-4902', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (107, 'Consultorio de Debas', 'Av. Santa Fe 3329 1° Cpo. 6° "B"', 1, '4823-5519', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (108, 'Consultorio de Debas', 'Av. Santa Fe 3329 1° Cpo. 6° "B"', 1, '4823-5519', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (109, 'Consultorio de Del Castillo', 'Av. Del Libertador 4992 P.B. "1"', 1, '4772-0857 - 4772-4391', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (110, 'Consultorio de Diamante', 'Av. Del Libertador 8330 1° "B"', 1, '5550-2304', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (111, 'Consultorio de Dodero', 'Av. Santa Fe 1130 1° "101"', 1, '4814-3648', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (112, 'Consultorio de Fagoaga', 'Junín 917 3° "A"', 1, '4961-6767', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (113, 'Consultorio de Feldman', 'Echeverría 5073 5° "C"', 1, '4522-1430', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (114, 'Consultorio de Ferrantino', 'Av. Monroe 4977 1° "A"', 1, '4523-5790 4524-0095', null);
INSERT INTO office (office_id, name, street, locality_id, phone, email)
VALUES (115, 'Consultorio de Fiora', 'Av. Santa Fe 3307 3° "B"', 1, '4822-6586', null);

INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (56, 1, 'Betty Giselle', 'Arteaga', '4871-5412', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (57, 2, 'Alejandro Ariel', 'Biain', '4792-2311 y 15-5347-5624', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (58, 3, 'María Eugenia', 'Castello', '(054-011) 6091-3380/21', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (59, 4, 'Carlos', 'Di Stefano', '4792-2311', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (60, 5, 'David V.', 'Diamint', '4744-1486', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (61, 6, 'Martiniano', 'Etcheverría', '4736-8476 - 4736-8705', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (62, 7, 'Nicolás', 'Fernández Meijide', '4791-8915 y 4796-2012', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (63, 8, 'Roberto', 'Fernández Meijide', '4791-8915 y 4796-2012', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (64, 9, 'María Grisel', 'Ferrante', '4743-4440', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (65, 10, 'Mariana', 'Fuentes', '(15)5378-5052', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (66, 11, 'Alejandro y Equipo', 'González Santos', '4744-5774 y 4549-0145', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (67, 12, 'Eduardo', 'Gossn', '4792-9508', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (68, 13, 'Osvaldo', 'Gossn', '4792-9508', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (69, 14, 'Ricardo Daniel y Equipo', 'Jucht', '4795-4322 y 4796-2672', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (70, 15, 'Adriana Raquel Lía', 'Kohn', '4794-2861', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (71, 16, 'Jorge Horacio', 'Lima', '4731-0526', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (72, 17, 'Carlos Alberto', 'Marconi', '4792-3965 y 4793-1065', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (73, 18, 'Paula Silvina', 'Melgem', '15-3135-3194 15-6590-3003', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (74, 19, 'María Inés', 'Menéndez Padrón', '(054-011) 4725-2494', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (75, 20, 'Carolina', 'Montana', '4871-7161 y 15-3681-3922', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (76, 21, 'Sergio', 'Muzzin', '4740-6803', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (77, 22, 'Eduardo Daniel', 'Palazzolo', '4740-2551', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (78, 23, 'Tomás Joaquín', 'Pena', '4763-7700 y 4735-8100', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (79, 24, 'Leonardo', 'Pérez Morales', '4837-3700', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (80, 25, 'Agustín', 'Pianciola', '(054-011) 4747-5183', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (81, 26, 'Liliana B.', 'Raineri', '0348-4421974', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (82, 27, 'Marcela Silvina', 'Rempel', '4746-5676', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (83, 28, 'Jorge Eduardo', 'Acosta', '4826-8282', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (84, 29, 'Arturo (H)', 'Alezzandrini', '4812-3618', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (85, 30, 'Pablo A.', 'Andersson', '4827-5130 - 4827-5131', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (86, 31, 'Eduardo Carlos', 'Baini', '4572-0222', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (87, 32, 'Diego Ariel', 'Bar', '4823-4336 y 4827-2890', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (88, 33, 'Sebastián Guido y Equipo', 'Barreiro', '4788-5180 y 15-5946-9566', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (89, 34, 'María Flavia', 'Benedetto', '4602-3947', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (90, 35, 'Cecilia', 'Bermejo', '4500-8105', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (91, 36, 'Mónica', 'Bolatti', '4813-1879 Fax 4813-1914', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (92, 37, 'Julio Néstor', 'Braillard', '4827-5130 - 4827-5131', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (93, 38, 'Jorge Luis y Equipo', 'Buonsanti', '4822-1335 y 4824-8943', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (94, 39, 'Carolina', 'Carminatti', '0810-777-3636 y 4777-8232', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (95, 40, 'Juan Pablo', 'Cassagne', '4821-7280', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (96, 41, 'Oscar Ignacio', 'Cavallari', '4816-9001/02 y 4381-0281', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (97, 42, 'María Gabriela', 'Cervini', '4641-5382 y 15-3782-2492', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (98, 43, 'Susana', 'Chiapero de Gamio', '4774-0064 y 4779-0409', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (99, 44, 'Luis', 'Cid Tesouro', '4701-9122', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (100, 45, 'Liliana Cristina', 'Coletti', '4641-3331', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (101, 46, 'Juan Manuel', 'Cortalezzi', '4832-2924 y 4834-6503', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (102, 47, 'Pedro', 'Cotella', '4811-6779 - 4811-1930', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (103, 48, 'Alejandro', 'Coussio', '4963-5649/50 Fax 4961-8021', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (104, 49, 'Ricardo Horacio', 'Cremona', '4962-2208 - 4962-0650', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (105, 50, 'Alejandro', 'Cuomo', '5711-5217 y 4784-5107', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (106, 51, 'María Laura', 'Damel', '4961-7333', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (107, 52, 'Lorena Alejandra', 'Di Nisio', '4371-5460/9740 y 4372-8063', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (108, 53, 'Ramiro', 'Diez Olea', '(054-011) 4518-4457', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (109, 54, 'Daniel Pedro', 'Domínguez', '4431-9861 Fax 4654-7634', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (110, 55, 'Claudia Liliana', 'Echeverría', '4774-8692', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (111, 56, 'Ignacio', 'Etcheverry', '4826-8282', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (112, 57, 'Manuel', 'Fernández Muriano', '4826-6684 y 4821-1227', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (113, 58, 'Héctor Javier', 'Fontana', '4805-1164', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (114, 59, 'Guillermo Alberto', 'Fridrich', '4824-5891', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (115, 60, 'Ramón Ricardo', 'Galmarini', '4821-6625 - 4821-1996', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (116, 61, 'Gustavo Rafael', 'García Soto', '4771-5736', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (117, 62, 'Beatriz', 'Gasparini', '4813-1978', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (118, 63, 'Mario León', 'Abramovich', '4612-8265 4637-8504 15-5802-1551', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (119, 64, 'Graciela Leonor', 'Abudara', '4772-4391/0857 y 15-4413-9242', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (120, 65, 'Victoria', 'Acerenza', '4633-3691 y 4631-5382', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (121, 66, 'Teresita María', 'Acuña', '4631-9151', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (122, 67, 'Francisco Cayetano', 'Agosta', '4572-0930', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (123, 68, 'Pedro Ernesto', 'Aguerre', '4902-0539', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (124, 69, 'María Isabel', 'Alapont Montoya', '4786-1457', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (125, 70, 'Oscar', 'Albanese', '4811-6841', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (126, 71, 'Lidia', 'Albano', '4686-0862', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (127, 72, 'María Estela', 'Albores', '4822-9648 4829-0705 4821-7320', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (128, 73, 'Miguel', 'Alemán', '4812-6599', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (129, 74, 'Julio Daniel', 'Alfie', '4637-1310 y 4903-4794', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (130, 75, 'Raúl Esteban', 'Alonso', '4541-8192 y 4545-0675', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (131, 76, 'Jaime Marcelo', 'Altcheh', '4957-3928', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (132, 77, 'Marcelo Adrián', 'Andrade', '4831-9826', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (133, 78, 'Florencia', 'Antar', '15-3353-9771 15-5966-8492', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (134, 79, 'Gustavo Marcelo y Equipo', 'Aprigliano', '4524-1994 y 4521-5635', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (135, 80, 'Gabriela', 'Arakelian', '5275-0433', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (136, 81, 'Solange', 'Arazi Caillaud', '4981-1935', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (137, 82, 'Ernesto', 'Attie', '4783-2504', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (138, 83, 'Ana María Clara', 'Balanzat', '4784-8450 - 4784-8439', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (139, 84, 'Leopoldo Pedro', 'Ballester', '4632-9724 y 4431-8903', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (140, 85, 'Lidia Norma', 'Barabini', '4901-3076', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (141, 86, 'Horacio Martín', 'Barassi', '4784-4633 y 4783-0428', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (142, 87, 'Carlos', 'Barletta', '4814-2228 4723-6839 15-4444-6555', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (143, 88, 'Elsa Cristina', 'Barrera', '4981-7816 4982-8184 4940-4687', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (144, 89, 'Pablo', 'Barvosa', '4611-5392 y 15-4973-5579', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (145, 90, 'Hebe Diana', 'Basadoni', '4687-1637', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (146, 91, 'Eduardo F. R.', 'Amaya', '4633-6058 y 4632-4741', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (147, 92, 'Eduardo', 'Amblard', '4805-7229 y 4755-0607', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (148, 93, 'Fernando Juan', 'Ane', '4807-4748', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (149, 94, 'Juan Carlos', 'Arauz', '4954-5929', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (150, 95, 'Jorge Adolfo', 'Azocar', '4504-2706', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (151, 96, 'José N.', 'Badaracco', '4801-2764 - 4801-4285', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (152, 97, 'Cristian Javier', 'Barbón', '4824-7865', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (153, 98, 'Jorge Luis', 'Biondi', '4813-2721', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (154, 99, 'José Ernesto y Equipo', 'Cafaro', '4502-1920', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (155, 100, 'María del Pilar', 'Castro', '(15)4960-5226', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (156, 101, 'Alberto', 'Ciceran', '4902-3310', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (157, 102, 'Maximiliano Pedro H.', 'Clemente', '4521-6651', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (158, 103, 'Leopoldo José', 'Cordero', '4826-1914', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (159, 104, 'Lucila', 'Cordero', '4826-0604', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (160, 105, 'Claudia Julia', 'Cremonese', '4574-3057', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (161, 106, 'Miguel Ángel', 'Cupitó', '4824-6687 4792-9619 4825-4902', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (162, 107, 'Juan', 'Debas', '4823-5519', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (163, 108, 'María Inés', 'Debas', '4823-5519', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (164, 109, 'Marcelo Carlos', 'Del Castillo', '4772-0857 - 4772-4391', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (165, 110, 'Fernando Javier', 'Diamante', '5550-2304', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (166, 111, 'Alberto Eduardo', 'Dodero', '4814-3648', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (167, 112, 'Francisco', 'Fagoaga', '4961-6767', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (168, 113, 'Roberto Gabriel', 'Feldman', '4522-1430', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (169, 114, 'José Mario', 'Ferrantino', '4523-5790 4524-0095', null, null);
INSERT INTO staff (staff_id, office_id, first_name, surname, phone, email, registration_number)
VALUES (170, 115, 'Gerardo', 'Fiora', '4822-6586', null, null);

INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 56);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 57);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 58);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 59);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 60);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 61);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 62);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 63);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 64);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 65);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 66);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 67);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 68);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 69);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 70);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 71);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 72);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 73);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 74);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 75);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 76);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 77);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 78);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 79);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 80);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 81);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 82);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 83);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 84);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 85);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 86);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 87);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 88);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 89);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 90);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 91);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 92);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 93);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 94);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 95);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 96);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 97);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 98);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 99);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 100);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 101);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 102);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 103);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 104);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 105);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 106);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 107);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 108);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 109);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 110);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 111);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 112);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 113);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 114);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 115);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 116);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (1, 117);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 118);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 119);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 120);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 121);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 122);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 123);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 124);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 125);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 126);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 127);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 128);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 129);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 130);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 131);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 132);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 133);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 134);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 135);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 136);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 137);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 138);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 139);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 140);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 141);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 142);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 143);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 144);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (2, 145);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 146);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 147);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 148);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 149);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 150);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 151);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 152);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 153);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 154);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 155);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 156);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 157);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 158);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 159);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 160);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 161);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 162);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 163);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 164);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 165);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 166);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 167);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 168);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 169);
INSERT INTO system_staff_specialty_staff (specialty_id, staff_id)
VALUES (3, 170);
