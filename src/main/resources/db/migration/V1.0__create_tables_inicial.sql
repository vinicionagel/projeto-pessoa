CREATE TABLE pessoa (
                         id SERIAL NOT NULL,
                         nome CHARACTER VARYING(255) NOT NULL,
                         email CHARACTER VARYING(255) NOT NULL,
                         naturalidade CHARACTER VARYING(255) NOT NULL,
                         nacionalidade CHARACTER VARYING(255) NOT NULL,
                         sexo CHARACTER VARYING(45) NOT NULL,
                         cpf CHARACTER VARYING(45) NOT NULL,
                         data_nascimento DATE,
                         create_date_time timestamp,
                         update_date_time timestamp,
                         CONSTRAINT "usuario_pkey" PRIMARY KEY (id),
                         CONSTRAINT unicos UNIQUE (cpf)
);