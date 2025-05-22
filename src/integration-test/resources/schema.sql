-- Suppression des tables existantes
DROP TABLE IF EXISTS VERSEMENTS_ALLOCATIONS;
DROP TABLE IF EXISTS ALLOCATIONS_ENFANTS;
DROP TABLE IF EXISTS ALLOCATIONS_NAISSANCE;
DROP TABLE IF EXISTS VERSEMENTS;
DROP TABLE IF EXISTS ALLOCATIONS;
DROP TABLE IF EXISTS ENFANTS;
DROP TABLE IF EXISTS ALLOCATAIRES;

-- Création des tables
CREATE TABLE ALLOCATAIRES (
                              NUMERO BIGINT PRIMARY KEY,
                              NO_AVS VARCHAR(13) NOT NULL,
                              PRENOM VARCHAR(100) NOT NULL,
                              NOM VARCHAR(100) NOT NULL
);

CREATE TABLE ENFANTS (
                         NUMERO BIGINT PRIMARY KEY,
                         NOM VARCHAR(100) NOT NULL,
                         PRENOM VARCHAR(100) NOT NULL,
                         DATE_NAISSANCE DATE NOT NULL
);

CREATE TABLE ALLOCATIONS (
                             NUMERO BIGINT PRIMARY KEY,
                             MONTANT DECIMAL(10,2) NOT NULL,
                             CANTON VARCHAR(2) NOT NULL,
                             DATE_DEBUT DATE NOT NULL,
                             DATE_FIN DATE
);

CREATE TABLE VERSEMENTS (
                            NUMERO BIGINT PRIMARY KEY,
                            MONTANT DECIMAL(10,2) NOT NULL,
                            DATE_VERSEMENT DATE NOT NULL,
                            MOIS_VERSEMENT DATE NOT NULL,
                            FK_ALLOCATAIRES BIGINT NOT NULL,
                            FOREIGN KEY (FK_ALLOCATAIRES) REFERENCES ALLOCATAIRES(NUMERO)
);

CREATE TABLE ALLOCATIONS_ENFANTS (
                                     NUMERO BIGINT PRIMARY KEY,
                                     FK_ENFANTS BIGINT NOT NULL,
                                     FK_ALLOCATIONS BIGINT NOT NULL,
                                     FOREIGN KEY (FK_ENFANTS) REFERENCES ENFANTS(NUMERO),
                                     FOREIGN KEY (FK_ALLOCATIONS) REFERENCES ALLOCATIONS(NUMERO)
);

CREATE TABLE VERSEMENTS_ALLOCATIONS (
                                        NUMERO BIGINT PRIMARY KEY,
                                        FK_VERSEMENTS BIGINT NOT NULL,
                                        FK_ALLOCATIONS_ENFANTS BIGINT NOT NULL,
                                        FOREIGN KEY (FK_VERSEMENTS) REFERENCES VERSEMENTS(NUMERO),
                                        FOREIGN KEY (FK_ALLOCATIONS_ENFANTS) REFERENCES ALLOCATIONS_ENFANTS(NUMERO)
);

CREATE TABLE ALLOCATIONS_NAISSANCE (
                                       NUMERO BIGINT PRIMARY KEY,
                                       FK_VERSEMENTS BIGINT,
                                       FK_ENFANTS BIGINT,
                                       MONTANT DECIMAL(10,2),
                                       FOREIGN KEY (FK_VERSEMENTS) REFERENCES VERSEMENTS(NUMERO),
                                       FOREIGN KEY (FK_ENFANTS) REFERENCES ENFANTS(NUMERO)
);