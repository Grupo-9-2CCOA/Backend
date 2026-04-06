CREATE TABLE IF NOT EXISTS admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(30) NOT NULL,
    senha VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    telefone VARCHAR(11),
    ativo BOOLEAN
);

CREATE TABLE IF NOT EXISTS endereco (
    id INT AUTO_INCREMENT PRIMARY KEY,
    logradouro VARCHAR(30) NOT NULL,
    numero VARCHAR(30) NOT NULL,
    cep VARCHAR(8) NOT NULL,
    complemento VARCHAR(30),
    id_cliente INT,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

CREATE TABLE IF NOT EXISTS entrega(
    id INT AUTO_INCREMENT PRIMARY KEY,
    estado VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS pagamento(
    id INT AUTO_INCREMENT PRIMARY KEY,
    estado VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS pedido (
    id INT AUTO_INCREMENT,
    id_cliente INT,
    id_endereco INT,
    produto VARCHAR(100) NOT NULL,
    descricao VARCHAR(200),
    valor DECIMAL(10,2) NOT NULL,
    status_entrega INT NOT NULL,
    status_pagamento INT NOT NULL,
    data_pedido DATE,
    PRIMARY KEY (id, id_cliente),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    FOREIGN KEY (id_endereco) REFERENCES endereco(id),
    FOREIGN KEY (status_entrega) REFERENCES entrega(id),
    FOREIGN KEY (status_pagamento) REFERENCES pagamento(id)
);

CREATE TABLE IF NOT EXISTS historico_pedido (
    id INT AUTO_INCREMENT,
    id_pedido INT,
    id_cliente INT,
    status_entrega INT NOT NULL,
    status_pagamento INT NOT NULL,
    data_modificacao DATE,
    PRIMARY KEY (id, id_pedido, id_cliente),
    FOREIGN KEY (id_pedido) REFERENCES pedido(id),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    FOREIGN KEY (status_entrega) REFERENCES entrega(id),
    FOREIGN KEY (status_pagamento) REFERENCES pagamento(id)
    );