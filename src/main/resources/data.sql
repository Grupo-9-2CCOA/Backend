INSERT IGNORE INTO entrega (id, estado)
VALUES (1, 'Pendente'),
       (2, 'Em trânsito'),
       (3, 'Entregue'),
       (4, 'Cancelado');

INSERT IGNORE INTO pagamento (id, estado)
VALUES (1, 'Pendente'),
       (2, 'Pago'),
       (3, 'Cancelado');

INSERT INTO admin (usuario, senha, troca_senha_obrigatoria)
SELECT 'admin', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC', true
WHERE NOT EXISTS (
	SELECT 1 FROM admin
);

