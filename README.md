Projeto de Geração da Tabela do Campeonato Paulista 2016 para a Matéria **Laboratório de Banco de Dados**
do curso de Análise e Desenvolvimento de Sistemas da FATEC ZL.
Professor: **Leandro Colevati dos Santos**
Tarde - 2º Semestre 2016

Projeto realizado por:

- **FERNANDO MORAES OLVIEIRA**

O histórico e a documentação podem ser acompanhados no [**wiki** do projeto](https://bitbucket.org/fatec2016/campeonatopaulista/wiki/).

A **documentação** deste sistema também se encontra na pasta "Documentos" juntamente com as tabelas do banco de dados (pousada.sql).

**OBS.:** Para a conexão no banco, trocar o usuário e senha do Microsoft SQL Server
na Classe GenericDao (package persistence).


```
#!sql

/*
Laboratório de Banco de Dados
Prof. M.Sc. Leandro Colevati dos Santos

AVALIAÇÃO 1

"Campeonato Paulista 2016"
*/

USE Northwind

IF (OBJECT_ID('Campeonato') IS NOT NULL)
	DROP DATABASE Campeonato
GO

CREATE DATABASE Campeonato
GO
USE Campeonato

IF (OBJECT_ID('Times') IS NOT NULL)
  DROP TABLE Times
GO

CREATE TABLE Times(
CodigoTime INT IDENTITY NOT NULL, 
NomeTime VARCHAR(100) NOT NULL, 
Cidade VARCHAR(50) NOT NULL,
Estadio VARCHAR(30) NOT NULL
PRIMARY KEY(CodigoTime))

INSERT INTO Times VALUES 
('Esporte Clube Água Santa', 'Diadema', 'Distrital do Inamar'), 
('Grêmio Osasco Audax', 'Osasco', 'José Liberatti'), 
('Botafogo Futebol Clube', 'Ribeirão Preto', 'Santa Cruz'), 
('Capivariano Futebol Clube', 'Capivari', 'Arena Capivari'), 
('Sport Club Corinthians Paulista', 'São Paulo', 'Arena Corinthians'), 
('Associação Ferroviária de Esportes', 'Araraquara', 'Fonte Luminosa'), 
('Ituano Futebol Clube', 'Itu', 'Novelli Júnior'), 
('Clube Atlético Linense', 'Lins', 'Gilberto Siqueira Lopes'), 
('Mogi Mirim Esporte Clube', 'Mogi Mirim', 'Vail Chaves'), 
('Grêmio Novorizontino', 'Novo Horizonte', 'Jorge Ismael de Biasi'), 
('Oeste Futebol Clube', 'Itápolis', 'Amaros'), 
('Sociedade Esportiva Palmeiras', 'São Paulo', 'Allianz Parque'), 
('Associação Atlética Ponte Preta', 'Campinas', 'Moisés Lucarelli'), 
('Red Bull Brasil', 'Campinas', 'Moisés Lucarelli'), 
('Rio Claro Futebol Clube', 'Rio Claro', 'Augusto Schmidt Filho'), 
('Santos Futebol Clube', 'Santos', 'Vila Belmiro'), 
('Esporte Clube São Bento', 'Sorocaba', 'Walter Ribeiro'), 
('São Bernardo Futebol Clube', 'São Bernardo do Campo', 'Primeiro de Maio'), 
('São Paulo Futebol Clube', 'São Paulo', 'Morumbi'), 
('Esporte Clube XV de Novembro', 'Piracicaba', 'Barão de Serra Negra')


IF (OBJECT_ID('Grupos') IS NOT NULL)
  DROP TABLE Grupos
GO

CREATE TABLE Grupos(
Grupo CHAR(1), 
CodigoTime INT NOT NULL 
FOREIGN KEY (CodigoTime) 
REFERENCES Times(CodigoTime))

IF (OBJECT_ID('Jogos') IS NOT NULL)
  DROP TABLE Jogos
GO

CREATE TABLE Jogos(
CodigoTimeA INT, 
CodigoTimeB INT, 
GolsTimeA INT, 
GolsTimeB INT, 
Data DATE
FOREIGN KEY (CodigoTimeA) 
REFERENCES Times(CodigoTime), 
FOREIGN KEY (CodigoTimeB) 
REFERENCES Times(CodigoTime))

```
```
#!sql

/*
1) Procedure que divide os times nos quatro grupos, preenchendo, aleatoriamente 
(exceção da regra: Coritnthians, Palmeiras, Santos e São Paulo NÃO PODEM estar no mesmo grupo).
*/

IF (OBJECT_ID('sp_grupos') IS NOT NULL)
  DROP PROCEDURE sp_grupos
GO

CREATE PROCEDURE sp_grupos 
AS DECLARE 	@numTimes INT, 
			@numGrupos INT, 
			@numTimesPorGrupo INT, 
			@contador INT,
			@grupo CHAR,
			@sqlTimes VARCHAR(MAX),
			@sqlGrupo VARCHAR(MAX)

SELECT @numTimes = COUNT(CodigoTime) FROM Times
SET @numGrupos = 4
SET @numTimesPorGrupo = @numTimes / @numGrupos
SET @contador = 1;

SET @sqlTimes = 'INSERT INTO Grupos(CodigoTime)
							SELECT TOP 1 t.CodigoTime 
							FROM Times AS t 
							LEFT JOIN Grupos AS g 
								ON t.CodigoTime = g.CodigoTime
								WHERE t.nomeTime NOT LIKE ''%Corinthians%'' AND 
									t.nomeTime NOT LIKE ''%Palmeiras%'' AND 
									t.nomeTime NOT LIKE ''%Santos%'' AND 
									t.nomeTime NOT LIKE ''%São Paulo%'' AND
									g.CodigoTime IS NULL 
								ORDER BY NEWID()'

WHILE @contador <= @numTimes - @numGrupos 
BEGIN
	SET @grupo = NULL
	IF @contador <= 4
	BEGIN
		SET @grupo = 'A'
		EXEC(@sqlTimes)
		SET @sqlGrupo = 'UPDATE Grupos SET Grupo = ''' + @grupo + ''' WHERE Grupo IS NULL'
		EXEC (@sqlGrupo)
	END
	ELSE
	BEGIN
		IF @contador > 4 AND @contador <= 8
		BEGIN
			SET @grupo = 'B'
			EXEC(@sqlTimes)
			SET @sqlGrupo = 'UPDATE Grupos SET Grupo = ''' + @grupo + ''' WHERE Grupo IS NULL'
			EXEC (@sqlGrupo)
		END
		ELSE
		BEGIN
			IF @contador > 8 AND @contador <= 12
			BEGIN
				SET @grupo = 'C'
				EXEC(@sqlTimes)
				SET @sqlGrupo = 'UPDATE Grupos SET Grupo = ''' + @grupo + ''' WHERE Grupo IS NULL'
				EXEC (@sqlGrupo)
			END
			ELSE
			BEGIN
				IF @contador > 12
				BEGIN
					SET @grupo = 'D'
					EXEC(@sqlTimes)
					SET @sqlGrupo = 'UPDATE Grupos SET Grupo = ''' + @grupo + ''' WHERE Grupo IS NULL'
					EXEC (@sqlGrupo)
				END
			END
		END
	END
	SET @contador = @contador + 1
END

SET @contador = 1;
SET @sqlTimes = 'INSERT INTO Grupos(CodigoTime)
							SELECT TOP 1 t.CodigoTime 
							FROM Times AS t 
							LEFT JOIN Grupos AS g 
								ON t.CodigoTime = g.CodigoTime
								WHERE t.nomeTime LIKE ''%Corinthians%'' AND 
									t.nomeTime LIKE ''%Palmeiras%'' AND 
									t.nomeTime LIKE ''%Santos%'' AND 
									t.nomeTime LIKE ''%São Paulo%'' OR
									g.CodigoTime IS NULL 
								ORDER BY NEWID()'
WHILE @contador <= 4 
BEGIN
	SET @grupo = NULL
	IF @contador = 1
	BEGIN
		SET @grupo = 'A'
		EXEC(@sqlTimes)
		SET @sqlGrupo = 'UPDATE Grupos SET Grupo = ''' + @grupo + ''' WHERE Grupo IS NULL'
		EXEC (@sqlGrupo)
	END
	ELSE
	BEGIN
		IF @contador = 2
		BEGIN
			SET @grupo = 'B'
			EXEC(@sqlTimes)
			SET @sqlGrupo = 'UPDATE Grupos SET Grupo = ''' + @grupo + ''' WHERE Grupo IS NULL'
			EXEC (@sqlGrupo)
		END
		ELSE
		BEGIN
			IF @contador = 3
			BEGIN
				SET @grupo = 'C'
				EXEC(@sqlTimes)
				SET @sqlGrupo = 'UPDATE Grupos SET Grupo = ''' + @grupo + ''' WHERE Grupo IS NULL'
				EXEC (@sqlGrupo)
			END
			ELSE
			BEGIN
				IF @contador = 4
				BEGIN
					SET @grupo = 'D'
					EXEC(@sqlTimes)
					SET @sqlGrupo = 'UPDATE Grupos SET Grupo = ''' + @grupo + ''' WHERE Grupo IS NULL'
					EXEC (@sqlGrupo)
				END
			END
		END
	END
	SET @contador = @contador + 1
END

/*
TESTE
*/

IF (OBJECT_ID('sp_grupos') IS NOT NULL)
	DELETE FROM Grupos
	EXEC sp_grupos
	SELECT * FROM Grupos
GO

```
```
#!sql

/*
2) Procedure que gera as rodadas dos jogos: 
A primeira fase ocorrerá em 15 datas seguidas, 
sempre rodada cheia (os 10 jogos com todos os 20 times), 
aos domingos e quartas
*/

IF (OBJECT_ID('sp_jogos') IS NOT NULL)
  DROP PROCEDURE sp_jogos
GO

CREATE PROCEDURE sp_jogos (@dtInicio SMALLDATETIME)
AS DECLARE 	@numTimes INT,
			@rodadas INT,
			@timesRodada INT,
			@dtFinal SMALLDATETIME, 
			@sqlDomFaseA VARCHAR(MAX),
			@sqlQuaFaseA VARCHAR(MAX),
			@sqlDomFaseB VARCHAR(MAX),
			@sqlQuaFaseB VARCHAR(MAX),
			@sqlDomFaseC VARCHAR(MAX),
			@sqlQuaFaseC VARCHAR(MAX),
			@sqlData SMALLDATETIME
			
SET @rodadas = 15
SELECT @numTimes = COUNT(CodigoTime) FROM Times

SET @sqlDomFaseA = 'INSERT Jogos(CodigoTimeA, CodigoTimeB)
							SELECT a.CodigoTime, b.CodigoTime 
							FROM (
								SELECT TOP 1 g.CodigoTime 
								FROM Grupos AS g 
								LEFT JOIN Jogos AS j 
								ON g.CodigoTime = j.CodigoTimeA AND j.Data IS NULL
								WHERE j.CodigoTimeA IS NULL AND g.Grupo = ''A'') a
							JOIN (
								SELECT TOP 1 g.CodigoTime 
								FROM Grupos AS g 
								LEFT JOIN Jogos AS j 
								ON g.CodigoTime = j.CodigoTimeB AND j.Data IS NULL
								WHERE j.CodigoTimeB IS NULL AND g.Grupo = ''B''
								ORDER BY NEWID()) b
							ON a.CodigoTime IS NOT NULL AND b.CodigoTime IS NOT NULL'


SET @sqlQuaFaseA = 'INSERT Jogos(CodigoTimeA, CodigoTimeB)
							SELECT a.CodigoTime, b.CodigoTime 
							FROM (
								SELECT TOP 1 g.CodigoTime 
								FROM Grupos AS g 
								LEFT JOIN Jogos AS j 
								ON g.CodigoTime = j.CodigoTimeA AND j.Data IS NULL
								WHERE j.CodigoTimeA IS NULL AND g.Grupo = ''C''
								ORDER BY NEWID()) a
							JOIN (
								SELECT TOP 1 g.CodigoTime 
								FROM Grupos AS g 
								LEFT JOIN Jogos AS j 
								ON g.CodigoTime = j.CodigoTimeB AND j.Data IS NULL
								WHERE j.CodigoTimeB IS NULL AND g.Grupo = ''D''
								ORDER BY NEWID()) b
							ON a.CodigoTime IS NOT NULL AND b.CodigoTime IS NOT NULL'

SET @sqlDomFaseB = 'INSERT Jogos(CodigoTimeA, CodigoTimeB)
							SELECT a.CodigoTime, b.CodigoTime 
							FROM (
								SELECT TOP 1 g.CodigoTime 
								FROM Grupos AS g 
								LEFT JOIN Jogos AS j 
								ON g.CodigoTime = j.CodigoTimeA AND j.Data IS NULL
								WHERE j.CodigoTimeA IS NULL AND g.Grupo = ''A'') a
							JOIN (
								SELECT TOP 1 g.CodigoTime 
								FROM Grupos AS g 
								LEFT JOIN Jogos AS j 
								ON g.CodigoTime = j.CodigoTimeB AND j.Data IS NULL
								WHERE j.CodigoTimeB IS NULL AND g.Grupo = ''D''
								ORDER BY NEWID()) b
							ON a.CodigoTime IS NOT NULL AND b.CodigoTime IS NOT NULL'

SET @sqlQuaFaseB = 'INSERT Jogos(CodigoTimeA, CodigoTimeB)
							SELECT a.CodigoTime, b.CodigoTime 
							FROM (
								SELECT TOP 1 g.CodigoTime 
								FROM Grupos AS g 
								LEFT JOIN Jogos AS j 
								ON g.CodigoTime = j.CodigoTimeA AND j.Data IS NULL
								WHERE j.CodigoTimeA IS NULL AND g.Grupo = ''B''
								ORDER BY NEWID()) a
							JOIN (
								SELECT TOP 1 g.CodigoTime 
								FROM Grupos AS g 
								LEFT JOIN Jogos AS j 
								ON g.CodigoTime = j.CodigoTimeB AND j.Data IS NULL
								WHERE j.CodigoTimeB IS NULL AND g.Grupo = ''C''
								ORDER BY NEWID()) b
							ON a.CodigoTime IS NOT NULL AND b.CodigoTime IS NOT NULL'

SET @sqlDomFaseC = 'INSERT Jogos(CodigoTimeA, CodigoTimeB)
							SELECT a.CodigoTime, b.CodigoTime 
							FROM (
								SELECT TOP 1 g.CodigoTime 
								FROM Grupos AS g 
								LEFT JOIN Jogos AS j 
								ON g.CodigoTime = j.CodigoTimeA AND j.Data IS NULL
								WHERE j.CodigoTimeA IS NULL AND g.Grupo = ''A'') a
							JOIN (
								SELECT TOP 1 g.CodigoTime 
								FROM Grupos AS g 
								LEFT JOIN Jogos AS j 
								ON g.CodigoTime = j.CodigoTimeB AND j.Data IS NULL
								WHERE j.CodigoTimeB IS NULL AND g.Grupo = ''C''
								ORDER BY NEWID()) b
							ON a.CodigoTime IS NOT NULL AND b.CodigoTime IS NOT NULL'

SET @sqlQuaFaseC = 'INSERT Jogos(CodigoTimeA, CodigoTimeB)
							SELECT a.CodigoTime, b.CodigoTime 
							FROM (
								SELECT TOP 1 g.CodigoTime 
								FROM Grupos AS g 
								LEFT JOIN Jogos AS j 
								ON g.CodigoTime = j.CodigoTimeA AND j.Data IS NULL
								WHERE j.CodigoTimeA IS NULL AND g.Grupo = ''B''
								ORDER BY NEWID()) a
							JOIN (
								SELECT TOP 1 g.CodigoTime 
								FROM Grupos AS g 
								LEFT JOIN Jogos AS j 
								ON g.CodigoTime = j.CodigoTimeB AND j.Data IS NULL
								WHERE j.CodigoTimeB IS NULL AND g.Grupo = ''D''
								ORDER BY NEWID()) b
							ON a.CodigoTime IS NOT NULL AND b.CodigoTime IS NOT NULL'

SET @dtFinal = DATEADD(Day, @rodadas * 2.5, @dtInicio)
WHILE @dtInicio <= @dtFinal
BEGIN
	SET @timesRodada = 1
	WHILE @timesRodada <= @numTimes/4
	BEGIN
		IF (DATEPART( DW,@dtInicio )) = 1
		BEGIN
			EXEC(@sqlDomFaseA)
			IF @timesRodada = @numTimes/4
			BEGIN
				UPDATE Jogos SET Data = @dtInicio WHERE Data IS NULL;	
			END
		END
		ELSE
		BEGIN
			IF (DATEPART( DW,@dtInicio )) = 4
			BEGIN
				EXEC(@sqlQuaFaseA)
				IF @timesRodada = @numTimes/4
				BEGIN
					UPDATE Jogos SET Data = @dtInicio WHERE Data IS NULL;	
				END
			END
		END
		SET @timesRodada = @timesRodada + 1
	END
	SET @dtInicio = DATEADD(Day, 1, @dtInicio)
END

SET @dtFinal = DATEADD(Day, @rodadas * 2.2, @dtInicio)
WHILE @dtInicio <= @dtFinal
BEGIN
	SET @timesRodada = 1
	WHILE @timesRodada <= @numTimes/4
	BEGIN
		IF (DATEPART( DW,@dtInicio )) = 1
		BEGIN
			EXEC(@sqlDomFaseB)
			IF @timesRodada = @numTimes/4
			BEGIN
				UPDATE Jogos SET Data = @dtInicio WHERE Data IS NULL;	
			END
		END
		ELSE
		BEGIN
			IF (DATEPART( DW,@dtInicio )) = 4
			BEGIN
				EXEC(@sqlQuaFaseB)
				IF @timesRodada = @numTimes/4
				BEGIN
					UPDATE Jogos SET Data = @dtInicio WHERE Data IS NULL;	
				END
			END
		END
		SET @timesRodada = @timesRodada + 1
	END
	SET @dtInicio = DATEADD(Day, 1, @dtInicio)
END

SET @dtFinal = DATEADD(Day, @rodadas * 2.2, @dtInicio)
WHILE @dtInicio <= @dtFinal
BEGIN
	SET @timesRodada = 1
	WHILE @timesRodada <= @numTimes/4
	BEGIN
		IF (DATEPART( DW,@dtInicio )) = 1
		BEGIN
			EXEC(@sqlDomFaseC)
			IF @timesRodada = @numTimes/4
			BEGIN
				UPDATE Jogos SET Data = @dtInicio WHERE Data IS NULL;	
			END
		END
		ELSE
		BEGIN
			IF (DATEPART( DW,@dtInicio )) = 4
			BEGIN
				EXEC(@sqlQuaFaseC)
				IF @timesRodada = @numTimes/4
				BEGIN
					UPDATE Jogos SET Data = @dtInicio WHERE Data IS NULL;	
				END
			END
		END
		SET @timesRodada = @timesRodada + 1
	END
	SET @dtInicio = DATEADD(Day, 1, @dtInicio)
END

/*
TESTE
*/

IF (OBJECT_ID( 'sp_jogos' ) IS NOT NULL)
	DELETE FROM Jogos
	DECLARE @dtInicio SMALLDATETIME
	SET @dtInicio = CONVERT( SMALLDATETIME,'01/09/2016',103 )
	EXEC sp_jogos @dtInicio
	SELECT * FROM Jogos
GO

```