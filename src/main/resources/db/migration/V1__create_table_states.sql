CREATE TABLE tb_states (
    id VARCHAR(100) PRIMARY KEY, 
    name VARCHAR(255) NOT NULL,                  
    abbreviation VARCHAR(10) NOT NULL UNIQUE,     
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    updated_at TIMESTAMP                          
);