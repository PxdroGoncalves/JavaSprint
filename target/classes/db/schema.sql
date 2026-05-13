CREATE TABLE beneficiario (
    id_bene NUMBER PRIMARY KEY,
    nm_bene VARCHAR2(100) NOT NULL,
    cpf_bene VARCHAR2(11) UNIQUE NOT NULL,
    dt_nasc DATE NOT NULL,
    email_bene VARCHAR2(120) UNIQUE,
    telefone_bene VARCHAR2(20),
    endereco_bene VARCHAR2(200),
    dt_cadastro DATE NOT NULL,
    senha_bene VARCHAR2(100) NOT NULL
);

CREATE TABLE dentista (
    id_dent NUMBER PRIMARY KEY,
    nm_dent VARCHAR2(100) NOT NULL,
    cro_dent VARCHAR2(30) UNIQUE NOT NULL,
    especialidade VARCHAR2(80),
    email_dent VARCHAR2(120) UNIQUE,
    telefone_dent VARCHAR2(20) NOT NULL,
    dt_cadastro DATE NOT NULL,
    senha_dent VARCHAR2(100) NOT NULL
);

CREATE TABLE integrante_tdb (
    id_integ NUMBER PRIMARY KEY,
    nm_integ VARCHAR2(100) NOT NULL,
    email_integ VARCHAR2(120) UNIQUE NOT NULL,
    cargo VARCHAR2(80),
    dt_cadastro DATE NOT NULL,
    senha_integ VARCHAR2(100) NOT NULL
);

CREATE TABLE patrocinador (
    id_patr NUMBER PRIMARY KEY,
    nm_patr VARCHAR2(100),
    email_patr VARCHAR2(120),
    anonimo CHAR(1) DEFAULT 'N' CHECK (anonimo IN ('S','N')),
    documento VARCHAR2(20)
);

CREATE TABLE caso (
    id_caso NUMBER PRIMARY KEY,
    dt_abertura DATE NOT NULL,
    dt_fechamento DATE,
    st_caso VARCHAR2(30) NOT NULL,
    fk_beneficiario_id_bene NUMBER NOT NULL,
    fk_dentista_id_dent NUMBER,
    fk_integrante_id_integ NUMBER NOT NULL,
    CONSTRAINT fk_caso_beneficiario FOREIGN KEY (fk_beneficiario_id_bene) REFERENCES beneficiario(id_bene),
    CONSTRAINT fk_caso_dentista FOREIGN KEY (fk_dentista_id_dent) REFERENCES dentista(id_dent),
    CONSTRAINT fk_caso_integrante FOREIGN KEY (fk_integrante_id_integ) REFERENCES integrante_tdb(id_integ),
    CONSTRAINT ck_caso_status CHECK (st_caso IN ('ABERTO','PENDENTE','EM_ANDAMENTO','FECHADO','CANCELADO')),
    CONSTRAINT ck_caso_datas CHECK (dt_fechamento IS NULL OR dt_fechamento >= dt_abertura)
);

CREATE TABLE diagnostico (
    id_diag NUMBER PRIMARY KEY,
    ds_diag VARCHAR2(500) NOT NULL,
    dt_diag DATE NOT NULL,
    fk_caso_id_caso NUMBER NOT NULL,
    fk_dentista_id_dent NUMBER NOT NULL,
    CONSTRAINT fk_diag_caso FOREIGN KEY (fk_caso_id_caso) REFERENCES caso(id_caso),
    CONSTRAINT fk_diag_dentista FOREIGN KEY (fk_dentista_id_dent) REFERENCES dentista(id_dent)
);

CREATE TABLE historico_status (
    id_hist NUMBER PRIMARY KEY,
    dt_alteracao TIMESTAMP NOT NULL,
    st_novo VARCHAR2(30) NOT NULL,
    fk_caso_id_caso NUMBER NOT NULL,
    fk_integrante_id_integ NUMBER NOT NULL,
    CONSTRAINT fk_hist_caso FOREIGN KEY (fk_caso_id_caso) REFERENCES caso(id_caso),
    CONSTRAINT fk_hist_integrante FOREIGN KEY (fk_integrante_id_integ) REFERENCES integrante_tdb(id_integ)
);

CREATE TABLE pedido_encaminhamento (
    id_pedido NUMBER PRIMARY KEY,
    dt_pedido DATE NOT NULL,
    st_pedido VARCHAR2(30) NOT NULL,
    fk_caso_id_caso NUMBER NOT NULL,
    fk_dentista_id_dent NUMBER NOT NULL,
    CONSTRAINT fk_pedido_caso FOREIGN KEY (fk_caso_id_caso) REFERENCES caso(id_caso),
    CONSTRAINT fk_pedido_dentista FOREIGN KEY (fk_dentista_id_dent) REFERENCES dentista(id_dent),
    CONSTRAINT ck_pedido_status CHECK (st_pedido IN ('PENDENTE','ACEITO','RECUSADO'))
);

CREATE TABLE doacao (
    id_doac NUMBER PRIMARY KEY,
    tp_doac VARCHAR2(20) NOT NULL,
    vl_doac NUMBER(10,2),
    ds_equipamento VARCHAR2(200),
    dt_doac DATE NOT NULL,
    fk_patrocinador_id_patr NUMBER NOT NULL,
    CONSTRAINT fk_doacao_patrocinador FOREIGN KEY (fk_patrocinador_id_patr) REFERENCES patrocinador(id_patr),
    CONSTRAINT ck_doacao_tipo CHECK (tp_doac IN ('MONETARIO','EQUIPAMENTO'))
);

CREATE TABLE evidencia (
    id_evid NUMBER PRIMARY KEY,
    ds_arquivo VARCHAR2(300) NOT NULL,
    dt_envio DATE NOT NULL,
    fk_beneficiario_id_bene NUMBER NOT NULL,
    fk_caso_id_caso NUMBER,
    CONSTRAINT fk_evid_beneficiario FOREIGN KEY (fk_beneficiario_id_bene) REFERENCES beneficiario(id_bene),
    CONSTRAINT fk_evid_caso FOREIGN KEY (fk_caso_id_caso) REFERENCES caso(id_caso)
);

CREATE TABLE mensagem (
    id_msg NUMBER PRIMARY KEY,
    ds_msg VARCHAR2(500) NOT NULL,
    remetente VARCHAR2(40) NOT NULL,
    dt_envio TIMESTAMP NOT NULL,
    fk_caso_id_caso NUMBER NOT NULL,
    CONSTRAINT fk_msg_caso FOREIGN KEY (fk_caso_id_caso) REFERENCES caso(id_caso)
);
