
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

