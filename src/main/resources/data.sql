INSERT INTO admin (usuario, senha, precisa_trocar_senha)
SELECT 'admin', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC', true
WHERE NOT EXISTS (
    SELECT 1 FROM admin WHERE usuario = 'admin'
);