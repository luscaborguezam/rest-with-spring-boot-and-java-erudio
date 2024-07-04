-- Geração de Modelo físico
-- Sql ANSI 2003 - brModelo.



CREATE TABLE user_pai (
id_user_pai INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
nome VARCHAR(255) NOT NULL,
sobrenome VARCHAR(255) NOT NULL,
cpf CHAR(11) NOT NULL,
rg VARCHAR(9),
email VARCHAR(255) NOT NULL
);

CREATE TABLE user_filho (
id_uer_filho INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
nome VARCHAR(255) NOT NULL,
sobrenome VARCHAR(255) NOT NULL,
cpf VARCHAR(11),
rg VARCHAR(9),
email VARCHAR(255),
id_user_pai INTEGER,
FOREIGN KEY(id_user_pai) REFERENCES user_pai (id_user_pai)
);

CREATE TABLE configuracao (
id_conf INTEGER PRIMARY KEY,
nivel_escolar INTEGER NOT NULL,
qtd_pergunta INTEGER DEFAULT 6 NOT NULL,
horario_quiz DATETIME NOT NULL,
qtd_notificacao INTEGER DEFAULT 2 NOT NULL,
id_uer_filho INTEGER NOT NULL,
FOREIGN KEY(id_uer_filho) REFERENCES user_filho (id_uer_filho)
);

CREATE TABLE quiz (
id_quiz INTEGER PRIMARY KEY NOT NULL,
data_inicio DATETIME NOT NULL,
data_fim DATETIME,
finalizado INTEGER NOT NULL DEFAULT 0,
id_conf INTEGER NOT NULL,
FOREIGN KEY(id_conf) REFERENCES configuracao (id_conf)
);

CREATE TABLE materia (
id_materia INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
nome VARCHAR(30) NOT NULL
);

CREATE TABLE conf_materia (
id_conf INTEGER NOT NULL,
id_materia INTEGER NOT NULL,
FOREIGN KEY(id_conf) REFERENCES configuracao (id_conf),
FOREIGN KEY(id_materia) REFERENCES materia (id_materia)
);

CREATE TABLE tema_aprendizagem (
id_tema INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
id_materia INTEGER NOT NULL,
tema VARCHAR(255) NOT NULL,
FOREIGN KEY(id_materia) REFERENCES materia (id_materia)
);

CREATE TABLE conteudo (
id_conteudo INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
conteudo TEXT NOT NULL,
id_tema INTEGER NOT NULL,
FOREIGN KEY(id_tema) REFERENCES tema_aprendizagem (id_tema)
);

CREATE TABLE pergunta (
id_perguta INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
pergunta VARCHAR(500) NOT NULL,
resposta VARCHAR(500) NOT NULL,
dificuldade INTEGER NOT NULL,
id_tema INTEGER NOT NULL,
FOREIGN KEY(id_tema) REFERENCES tema_aprendizagem (id_tema)
);

CREATE TABLE quiz_perguntas (
id_perguta INTEGER,
id_quiz INTEGER,
FOREIGN KEY(id_quiz) REFERENCES quiz (id_quiz),
FOREIGN KEY(id_perguta) REFERENCES pergunta (id_perguta)
);

CREATE TABLE resposta_incorreta (
id_reposta_incorreta INTEGER PRIMARY KEY AUTO_INCREMENT,
resposta VARCHAR(500) NOT NULL
);

CREATE TABLE resp_incorreta_tema (
id_tema INTEGER NOT NULL,
id_resposta_incorreta INTEGER NOT NULL,
FOREIGN KEY(id_tema) REFERENCES tema_aprendizagem(id_tema)
);
