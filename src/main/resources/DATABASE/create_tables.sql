PRAGMA foreign_keys = ON;

-- Tabela lista
CREATE TABLE IF NOT EXISTS lista (
    id_lista INTEGER PRIMARY KEY AUTOINCREMENT,
    tipo_lista TEXT NOT NULL UNIQUE
);

INSERT INTO lista (tipo_lista) VALUES ('blackList'), ('grayList'), ('whiteList');

-- Tabela participante
CREATE TABLE IF NOT EXISTS participante (
    id_participante INTEGER PRIMARY KEY AUTOINCREMENT,
    id_lista INTEGER NOT NULL DEFAULT 3,
    profile_pic TEXT,
    banner_pic TEXT,
    biografia TEXT,
    username TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    genero TEXT NOT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_lista) REFERENCES lista(id_lista)
);

-- Tabela organizacao
CREATE TABLE IF NOT EXISTS organizacao (
    id_organizacao INTEGER PRIMARY KEY AUTOINCREMENT,
    id_participante INTEGER NOT NULL,
    nome TEXT NOT NULL,
    cnpj TEXT NOT NULL UNIQUE,
    FOREIGN KEY (id_participante) REFERENCES participante(id_participante)
);

-- Tabela post
CREATE TABLE IF NOT EXISTS post (
    id_post INTEGER PRIMARY KEY AUTOINCREMENT,
    id_autor INTEGER NOT NULL,
    titulo TEXT NOT NULL,
    descricao TEXT,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_autor) REFERENCES participante(id_participante)
);

-- Tabela reacaoPost
CREATE TABLE IF NOT EXISTS reacaoPost (
    id_reacao INTEGER PRIMARY KEY AUTOINCREMENT,
    id_reacaoPost INTEGER NOT NULL,
    id_autor_reacao INTEGER NOT NULL,
    reaction TEXT NOT NULL,
    FOREIGN KEY (id_reacaoPost) REFERENCES post(id_post),
    FOREIGN KEY (id_autor_reacao) REFERENCES participante(id_participante)
);

-- Tabela midia
CREATE TABLE IF NOT EXISTS midia (
    id_midia INTEGER PRIMARY KEY AUTOINCREMENT,
    id_post INTEGER NOT NULL,
    midia_ref TEXT NOT NULL,
    FOREIGN KEY (id_post) REFERENCES post(id_post)
);

-- Tabela evento
CREATE TABLE IF NOT EXISTS evento (
    id_evento INTEGER PRIMARY KEY AUTOINCREMENT,
    id_organizacao INTEGER NOT NULL,
    id_post INTEGER NOT NULL,
    data_hora_inicio DATETIME NOT NULL,
    data_hora_fim DATETIME NOT NULL,
    local TEXT NOT NULL,
    link TEXT NOT NULL,
    FOREIGN KEY (id_organizacao) REFERENCES organizacao(id_organizacao),
    FOREIGN KEY (id_post) REFERENCES post(id_post)
);

-- Tabela checklist
CREATE TABLE IF NOT EXISTS checklist (
    id_checklist INTEGER PRIMARY KEY AUTOINCREMENT,
    id_participante INTEGER NOT NULL,
    id_post INTEGER NOT NULL,
    presente INTEGER NOT NULL CHECK (presente IN (0,1)),
    FOREIGN KEY (id_participante) REFERENCES participante(id_participante),
    FOREIGN KEY (id_post) REFERENCES post(id_post)
);

-- Tabela comentario
CREATE TABLE IF NOT EXISTS comentario (
    id_comentario INTEGER PRIMARY KEY AUTOINCREMENT,
    id_post INTEGER NOT NULL,
    id_autor_comentario INTEGER NOT NULL,
    titulo_comentario TEXT,
    descricao_comentario TEXT,
    FOREIGN KEY (id_post) REFERENCES post(id_post),
    FOREIGN KEY (id_autor_comentario) REFERENCES participante(id_participante)
);

-- Tabela reacaoComentario
CREATE TABLE IF NOT EXISTS reacaoComentario (
    id_reacao INTEGER PRIMARY KEY AUTOINCREMENT,
    id_reacaoComentario INTEGER NOT NULL,
    id_autor_reacao INTEGER NOT NULL,
    reaction TEXT NOT NULL,
    FOREIGN KEY (id_reacaoComentario) REFERENCES comentario(id_comentario),
    FOREIGN KEY (id_autor_reacao) REFERENCES participante(id_participante)
);

-- Tabela administrador
CREATE TABLE IF NOT EXISTS administrador (
    id_administrador INTEGER PRIMARY KEY AUTOINCREMENT,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

-- Tabela denuncia
CREATE TABLE IF NOT EXISTS denuncia (
    id_denuncia INTEGER PRIMARY KEY AUTOINCREMENT,
    id_relator INTEGER NOT NULL,
    id_relatado INTEGER NOT NULL,
    id_administrador INTEGER,
    id_post INTEGER,
    titulo TEXT NOT NULL,
    motivo TEXT NOT NULL,
    status TEXT NOT NULL DEFAULT 'Pendente',
    data_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_inicio_analise TIMESTAMP,
    data_fim_analise TIMESTAMP,
    FOREIGN KEY (id_relator) REFERENCES participante(id_participante),
    FOREIGN KEY (id_relatado) REFERENCES participante(id_participante),
    FOREIGN KEY (id_administrador) REFERENCES administrador(id_administrador),
    FOREIGN KEY (id_post) REFERENCES post(id_post)
);

-- Tabela banido
CREATE TABLE IF NOT EXISTS banido (
    id_banido INTEGER PRIMARY KEY AUTOINCREMENT,
    id_administrador INTEGER NOT NULL,
    id_participante_banido INTEGER NOT NULL,
    id_denuncia INTEGER NOT NULL,
    motivo TEXT NOT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_participante_banido) REFERENCES participante(id_participante),
    FOREIGN KEY (id_administrador) REFERENCES administrador(id_administrador),
    FOREIGN KEY (id_denuncia) REFERENCES denuncia(id_denuncia)
);

-- Tabela suspenso
CREATE TABLE IF NOT EXISTS suspenso (
    id_suspenso INTEGER PRIMARY KEY AUTOINCREMENT,
    id_administrador INTEGER NOT NULL,
    id_participante_suspenso INTEGER NOT NULL,
    id_denuncia INTEGER NOT NULL,
    motivo TEXT NOT NULL,
    data_hora_inicio DATETIME NOT NULL,
    data_hora_fim DATETIME NOT NULL,
    FOREIGN KEY (id_administrador) REFERENCES administrador(id_administrador),
    FOREIGN KEY (id_participante_suspenso) REFERENCES participante(id_participante),
    FOREIGN KEY (id_denuncia) REFERENCES denuncia(id_denuncia)
);