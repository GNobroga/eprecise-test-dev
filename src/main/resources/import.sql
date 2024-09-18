DROP TABLE IF EXISTS tb_cities;
DROP TABLE IF EXISTS tb_states;

CREATE TABLE tb_states (
    id VARCHAR(100) PRIMARY KEY, 
    name VARCHAR(255) NOT NULL,                  
    abbreviation VARCHAR(10) NOT NULL UNIQUE,     
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    updated_at TIMESTAMP                          
);

CREATE TABLE tb_cities (
    id VARCHAR(100) PRIMARY KEY,       
    name VARCHAR(255) NOT NULL,       
    state_id VARCHAR(100) NOT NULL, 
    population BIGINT,                 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   
    updated_at TIMESTAMP,
    CONSTRAINT fk_state
        FOREIGN KEY (state_id) 
        REFERENCES tb_states(id)
);


INSERT INTO tb_states (id, name, abbreviation, updated_at) VALUES
('123e4567-e89b-12d3-a456-426614174010', 'Acre', 'AC', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174011', 'Alagoas', 'AL', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174012', 'Amapá', 'AP', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174013', 'Amazonas', 'AM', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174014', 'Bahia', 'BA', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174015', 'Ceará', 'CE', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174016', 'Distrito Federal', 'DF', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174017', 'Espírito Santo', 'ES', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174018', 'Goiás', 'GO', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174019', 'Maranhão', 'MA', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174020', 'Mato Grosso', 'MT', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174021', 'Mato Grosso do Sul', 'MS', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174022', 'Minas Gerais', 'MG', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174023', 'Pará', 'PA', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174024', 'Paraíba', 'PB', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174025', 'Paraná', 'PR', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174026', 'Pernambuco', 'PE', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174027', 'Piauí', 'PI', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174028', 'Rio de Janeiro', 'RJ', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174029', 'Rio Grande do Norte', 'RN', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174030', 'Rio Grande do Sul', 'RS', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174031', 'Rondônia', 'RO', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174032', 'Roraima', 'RR', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174033', 'Santa Catarina', 'SC', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174034', 'São Paulo', 'SP', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174035', 'Sergipe', 'SE', CURRENT_DATE),
('123e4567-e89b-12d3-a456-426614174036', 'Tocantins', 'TO', CURRENT_DATE);

INSERT INTO tb_cities (id, name, state_id, population, created_at, updated_at) VALUES
('a1b2c3d4-5678-90ab-cdef-1234567890ab', 'Rio Branco', '123e4567-e89b-12d3-a456-426614174010', 407319, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('b1c2d3e4-6789-01ab-cdef-234567890abc', 'Cruzeiro do Sul', '123e4567-e89b-12d3-a456-426614174010', 88622, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);