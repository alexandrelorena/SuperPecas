INSERT INTO carros (nome_do_modelo, fabricante, codigo_unico) VALUES
('Toyota Corolla', 'Toyota', 'TOY1234'),
('Ford Mustang', 'Ford', 'FOR5678'),
('Honda Civic', 'Honda', 'HON9012');

INSERT INTO pecas (nome, descricao, numero_de_serie, fabricante, modelo_do_carro, carro_id) VALUES
('Pneu', 'Pneu de alta performance', 'SN123', 'Michelin', 'Toyota Corolla', 1),
('Motor', 'Motor potente para Mustang', 'SN456', 'Ford', 'Ford Mustang', 2),
('Bateria', 'Bateria de longa duração', 'SN789', 'ACDelco', 'Honda Civic', 3);