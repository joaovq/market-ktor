CREATE TABLE IF NOT EXISTS employer_tb (
  id UUID PRIMARY KEY,
  employer_first_name VARCHAR(1024) NOT NULL,
  employer_last_name VARCHAR(1024) NOT NULL,
  employer_cpf VARCHAR(1024) NOT NULL UNIQUE,
  employer_address VARCHAR(1024) NOT NULL,
  employer_salary DECIMAL(30, 3) NOT NULL,
  user_id UUID NOT NULL UNIQUE,
  CONSTRAINT fk_employer_tb_user_id__id FOREIGN KEY (user_id) REFERENCES user_tb(id) ON DELETE
  SET
    NULL ON
  UPDATE
    CASCADE
);