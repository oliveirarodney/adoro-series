use master 

alter database bancodeseries set single_user with rollback immediate
go

drop database bancodeseries
go

create database bancodeseries
go

use bancodeseries
go

create table tipoUsuario(
	codTipoUsuario int primary key identity(1,1),
	nomeTipoUsuario varchar(10)
);

create table usuario(
	codUsuario int primary key identity(1,1),
	tipoUsuario int not null,
	username varchar(50) unique not null,
	email varchar(200) not null,
	senha varchar(max) not null,

	constraint fk_tipo_usuario foreign key(tipoUsuario) references tipoUsuario(codTipoUsuario)
);

create table categorias(
	codCategoria int primary key identity(1,1),
	nomeCategoria varchar(20) not null
);

create table tipoPrograma(
	codTipoPrograma int primary key identity(1,1),
	nomeTipoPrograma varchar(20) not null
);

create table programatv(
	codPrograma int primary key identity(1,1),
	codCategoria int not null,
	codTipoPrograma int not null,
	nomePrograma varchar(100) not null,
	imagemPrograma varbinary(max) not null
	foreign key (codCategoria) references categorias(codCategoria),
	constraint fk_tipo_programa foreign key (codTipoPrograma) references tipoPrograma(codTipoPrograma)
);

create table temporadas(
	codTemporada int not null,
	codPrograma int not null,
	nomeTemporada varchar(50) not null
	constraint pk_temporadas primary key(codTemporada, codPrograma),
	constraint fk_programa foreign key(codPrograma) references programatv(codPrograma)
);

create table episodios(
	codEpisodio int not null,
	codTemporada int not null,
	codPrograma int not null,
	dataExibicao date,
	nomeEpisodio varchar(200) not null
	constraint pk_episodios primary key(codEpisodio, codTemporada, codPrograma),
	constraint fk_temporada foreign key(codTemporada, codPrograma) references temporadas(codTemporada, codPrograma)
);

create table episodiosAssistidos(
	codUsuario int not null,
	codEpisodio int not null,
	codTemporada int not null,
	codPrograma int not null,
	notaEpisodio int
	constraint pk_episodiosAssistidos primary key(codUsuario, codEpisodio, codTemporada, codPrograma),
	constraint fk_episodios foreign key(codEpisodio, codTemporada, codPrograma) references episodios(codEpisodio, codTemporada, codPrograma),
	constraint fk_usuarios foreign key (codUsuario) references usuario(codUsuario)
);

create table favoritos(
	codUsuario int not null,
	codPrograma int not null,
	constraint pk_favoritos primary key(codUsuario, codPrograma),
	constraint fk_fav_usuarios foreign key (codUsuario) references usuario(codUsuario),
	constraint fk_fav_programa foreign key (codPrograma) references programatv(codPrograma)
);

insert into tipoUsuario values
('admin'),
('usuario')

insert into usuario values
(1, 'rod'	 , 'rod'	, 'rod'	  ),
(1, 'pedro' , 'pedro'	, 'pedro' ),
(2, 'thales', 'thales'	, 'thales'),
(2, 'mayk'	 , 'mayk'	, 'mayk'  )

insert into categorias values
('Terror'),
('Suspense'),
('Comedia'),
('Drama'),
('Variedades')

insert into tipoPrograma values
('S�ries'),
('Programas')

insert into programatv values
(1, 1, 'Supernatural', (select * from openrowset(bulk 'C:\\supernatural.jpg',	single_blob) as programaimage)),
(3, 1, 'Friends'	 , (select * from openrowset(bulk 'C:\\friends.jpg',		single_blob) as programaimage)),
(4, 1, 'Breaking Bad', (select * from openrowset(bulk 'C:\\breaking_bad.jpg',	single_blob) as programaimage)),
(3, 2, 'T� no Ar'	 , (select * from openrowset(bulk 'C:\\ta_no_ar.jpg',		single_blob) as programaimage)),
(5, 2, 'Masterchef'	 , (select * from openrowset(bulk 'C:\\masterchef.png',		single_blob) as programaimage))

insert into temporadas values
(1, 1, '1� Temporada'),
(2, 1, '2� Temporada'),
(3, 1, '3� Temporada'),
(4, 1, '4� Temporada'),
(1, 2, '1� Temporada'),
(2, 2, '2� Temporada'),
(3, 2, '3� Temporada'),
(4, 2, '4� Temporada'),
(1, 3, '1� Temporada'),
(2, 3, '2� Temporada'),
(3, 3, '3� Temporada'),
(4, 3, '4� Temporada'),
(1, 4, '1� Temporada'),
(2, 4, '2� Temporada'),
(3, 4, '3� Temporada'),
(4, 4, '4� Temporada'),
(1, 5, '1� Temporada'),
(2, 5, '2� Temporada'),
(3, 5, '3� Temporada'),
(4, 5, '4� Temporada')

insert into episodios values
(1, 1, 1, '07-01-2015', 'Epis�dio 1'),
(2, 1, 1, '14-01-2015', 'Epis�dio 2'),
(3, 1, 1, '21-01-2015', 'Epis�dio 3'),
(4, 1, 1, '07-02-2015', 'Epis�dio 4'),
(5, 1, 1, '14-02-2015', 'Epis�dio 5'),
(6, 1, 1, '21-02-2015', 'Epis�dio 6'),
(7, 1, 1, '07-03-2015', 'Epis�dio 7'),
(8, 1, 1, '14-03-2015', 'Epis�dio 8'),
(1, 2, 1, '07-01-2016', 'Epis�dio 1'),
(2, 2, 1, '14-01-2016', 'Epis�dio 2'),
(3, 2, 1, '21-01-2016', 'Epis�dio 3'),
(4, 2, 1, '07-02-2016', 'Epis�dio 4'),
(5, 2, 1, '14-02-2016', 'Epis�dio 5'),
(6, 2, 1, '21-02-2016', 'Epis�dio 6'),
(7, 2, 1, '07-03-2016', 'Epis�dio 7'),
(8, 2, 1, '14-03-2016', 'Epis�dio 8'),
(1, 3, 1, '07-01-2017', 'Epis�dio 1'),
(2, 3, 1, '14-01-2017', 'Epis�dio 2'),
(3, 3, 1, '21-01-2017', 'Epis�dio 3'),
(4, 3, 1, '07-02-2017', 'Epis�dio 4'),
(5, 3, 1, '14-02-2017', 'Epis�dio 5'),
(6, 3, 1, '21-02-2017', 'Epis�dio 6'),
(7, 3, 1, '07-03-2017', 'Epis�dio 7'),
(8, 3, 1, '14-03-2017', 'Epis�dio 8'),
(1, 4, 1, '07-01-2018', 'Epis�dio 1'),
(2, 4, 1, '14-01-2018', 'Epis�dio 2'),
(3, 4, 1, '21-01-2018', 'Epis�dio 3'),
(4, 4, 1, '07-02-2018', 'Epis�dio 4'),
(5, 4, 1, '14-02-2018', 'Epis�dio 5'),
(6, 4, 1, '21-02-2018', 'Epis�dio 6'),
(7, 4, 1, '07-03-2018', 'Epis�dio 7'),
(8, 4, 1, '14-03-2018', 'Epis�dio 8'),
(1, 1, 2, '07-01-2015', 'Epis�dio 1'),
(2, 1, 2, '14-01-2015', 'Epis�dio 2'),
(3, 1, 2, '21-01-2015', 'Epis�dio 3'),
(4, 1, 2, '07-02-2015', 'Epis�dio 4'),
(5, 1, 2, '14-02-2015', 'Epis�dio 5'),
(6, 1, 2, '21-02-2015', 'Epis�dio 6'),
(7, 1, 2, '07-03-2015', 'Epis�dio 7'),
(8, 1, 2, '14-03-2015', 'Epis�dio 8'),
(1, 2, 2, '07-01-2016', 'Epis�dio 1'),
(2, 2, 2, '14-01-2016', 'Epis�dio 2'),
(3, 2, 2, '21-01-2016', 'Epis�dio 3'),
(4, 2, 2, '07-02-2016', 'Epis�dio 4'),
(5, 2, 2, '14-02-2016', 'Epis�dio 5'),
(6, 2, 2, '21-02-2016', 'Epis�dio 6'),
(7, 2, 2, '07-03-2016', 'Epis�dio 7'),
(8, 2, 2, '14-03-2016', 'Epis�dio 8'),
(1, 3, 2, '07-01-2017', 'Epis�dio 1'),
(2, 3, 2, '14-01-2017', 'Epis�dio 2'),
(3, 3, 2, '21-01-2017', 'Epis�dio 3'),
(4, 3, 2, '07-02-2017', 'Epis�dio 4'),
(5, 3, 2, '14-02-2017', 'Epis�dio 5'),
(6, 3, 2, '21-02-2017', 'Epis�dio 6'),
(7, 3, 2, '07-03-2017', 'Epis�dio 7'),
(8, 3, 2, '14-03-2017', 'Epis�dio 8'),
(1, 4, 2, '07-01-2018', 'Epis�dio 1'),
(2, 4, 2, '14-01-2018', 'Epis�dio 2'),
(3, 4, 2, '21-01-2018', 'Epis�dio 3'),
(4, 4, 2, '07-02-2018', 'Epis�dio 4'),
(5, 4, 2, '14-02-2018', 'Epis�dio 5'),
(6, 4, 2, '21-02-2018', 'Epis�dio 6'),
(7, 4, 2, '07-03-2018', 'Epis�dio 7'),
(8, 4, 2, '14-03-2018', 'Epis�dio 8'),
(1, 1, 3, '07-01-2015', 'Epis�dio 1'),
(2, 1, 3, '14-01-2015', 'Epis�dio 2'),
(3, 1, 3, '21-01-2015', 'Epis�dio 3'),
(4, 1, 3, '07-02-2015', 'Epis�dio 4'),
(5, 1, 3, '14-02-2015', 'Epis�dio 5'),
(6, 1, 3, '21-02-2015', 'Epis�dio 6'),
(7, 1, 3, '07-03-2015', 'Epis�dio 7'),
(8, 1, 3, '14-03-2015', 'Epis�dio 8'),
(1, 2, 3, '07-01-2016', 'Epis�dio 1'),
(2, 2, 3, '14-01-2016', 'Epis�dio 2'),
(3, 2, 3, '21-01-2016', 'Epis�dio 3'),
(4, 2, 3, '07-02-2016', 'Epis�dio 4'),
(5, 2, 3, '14-02-2016', 'Epis�dio 5'),
(6, 2, 3, '21-02-2016', 'Epis�dio 6'),
(7, 2, 3, '07-03-2016', 'Epis�dio 7'),
(8, 2, 3, '14-03-2016', 'Epis�dio 8'),
(1, 3, 3, '07-01-2017', 'Epis�dio 1'),
(2, 3, 3, '14-01-2017', 'Epis�dio 2'),
(3, 3, 3, '21-01-2017', 'Epis�dio 3'),
(4, 3, 3, '07-02-2017', 'Epis�dio 4'),
(5, 3, 3, '14-02-2017', 'Epis�dio 5'),
(6, 3, 3, '21-02-2017', 'Epis�dio 6'),
(7, 3, 3, '07-03-2017', 'Epis�dio 7'),
(8, 3, 3, '14-03-2017', 'Epis�dio 8'),
(1, 4, 3, '07-01-2018', 'Epis�dio 1'),
(2, 4, 3, '14-01-2018', 'Epis�dio 2'),
(3, 4, 3, '21-01-2018', 'Epis�dio 3'),
(4, 4, 3, '07-02-2018', 'Epis�dio 4'),
(5, 4, 3, '14-02-2018', 'Epis�dio 5'),
(6, 4, 3, '21-02-2018', 'Epis�dio 6'),
(7, 4, 3, '07-03-2018', 'Epis�dio 7'),
(8, 4, 3, '14-03-2018', 'Epis�dio 8'),
(1, 1, 4, '07-01-2015', 'Epis�dio 1'),
(2, 1, 4, '14-01-2015', 'Epis�dio 2'),
(3, 1, 4, '21-01-2015', 'Epis�dio 3'),
(4, 1, 4, '07-02-2015', 'Epis�dio 4'),
(5, 1, 4, '14-02-2015', 'Epis�dio 5'),
(6, 1, 4, '21-02-2015', 'Epis�dio 6'),
(7, 1, 4, '07-03-2015', 'Epis�dio 7'),
(8, 1, 4, '14-03-2015', 'Epis�dio 8'),
(1, 2, 4, '07-01-2016', 'Epis�dio 1'),
(2, 2, 4, '14-01-2016', 'Epis�dio 2'),
(3, 2, 4, '21-01-2016', 'Epis�dio 3'),
(4, 2, 4, '07-02-2016', 'Epis�dio 4'),
(5, 2, 4, '14-02-2016', 'Epis�dio 5'),
(6, 2, 4, '21-02-2016', 'Epis�dio 6'),
(7, 2, 4, '07-03-2016', 'Epis�dio 7'),
(8, 2, 4, '14-03-2016', 'Epis�dio 8'),
(1, 3, 4, '07-01-2017', 'Epis�dio 1'),
(2, 3, 4, '14-01-2017', 'Epis�dio 2'),
(3, 3, 4, '21-01-2017', 'Epis�dio 3'),
(4, 3, 4, '07-02-2017', 'Epis�dio 4'),
(5, 3, 4, '14-02-2017', 'Epis�dio 5'),
(6, 3, 4, '21-02-2017', 'Epis�dio 6'),
(7, 3, 4, '07-03-2017', 'Epis�dio 7'),
(8, 3, 4, '14-03-2017', 'Epis�dio 8'),
(1, 4, 4, '07-01-2018', 'Epis�dio 1'),
(2, 4, 4, '14-01-2018', 'Epis�dio 2'),
(3, 4, 4, '21-01-2018', 'Epis�dio 3'),
(4, 4, 4, '07-02-2018', 'Epis�dio 4'),
(5, 4, 4, '14-02-2018', 'Epis�dio 5'),
(6, 4, 4, '21-02-2018', 'Epis�dio 6'),
(7, 4, 4, '07-03-2018', 'Epis�dio 7'),
(8, 4, 4, '14-03-2018', 'Epis�dio 8'),
(1, 1, 5, '07-01-2015', 'Epis�dio 1'),
(2, 1, 5, '14-01-2015', 'Epis�dio 2'),
(3, 1, 5, '21-01-2015', 'Epis�dio 3'),
(4, 1, 5, '07-02-2015', 'Epis�dio 4'),
(5, 1, 5, '14-02-2015', 'Epis�dio 5'),
(6, 1, 5, '21-02-2015', 'Epis�dio 6'),
(7, 1, 5, '07-03-2015', 'Epis�dio 7'),
(8, 1, 5, '14-03-2015', 'Epis�dio 8'),
(1, 2, 5, '07-01-2016', 'Epis�dio 1'),
(2, 2, 5, '14-01-2016', 'Epis�dio 2'),
(3, 2, 5, '21-01-2016', 'Epis�dio 3'),
(4, 2, 5, '07-02-2016', 'Epis�dio 4'),
(5, 2, 5, '14-02-2016', 'Epis�dio 5'),
(6, 2, 5, '21-02-2016', 'Epis�dio 6'),
(7, 2, 5, '07-03-2016', 'Epis�dio 7'),
(8, 2, 5, '14-03-2016', 'Epis�dio 8'),
(1, 3, 5, '07-01-2017', 'Epis�dio 1'),
(2, 3, 5, '14-01-2017', 'Epis�dio 2'),
(3, 3, 5, '21-01-2017', 'Epis�dio 3'),
(4, 3, 5, '07-02-2017', 'Epis�dio 4'),
(5, 3, 5, '14-02-2017', 'Epis�dio 5'),
(6, 3, 5, '21-02-2017', 'Epis�dio 6'),
(7, 3, 5, '07-03-2017', 'Epis�dio 7'),
(8, 3, 5, '14-03-2017', 'Epis�dio 8'),
(1, 4, 5, '07-01-2018', 'Epis�dio 1'),
(2, 4, 5, '14-01-2018', 'Epis�dio 2'),
(3, 4, 5, '21-01-2018', 'Epis�dio 3'),
(4, 4, 5, '07-02-2018', 'Epis�dio 4'),
(5, 4, 5, '14-02-2018', 'Epis�dio 5'),
(6, 4, 5, '21-02-2018', 'Epis�dio 6'),
(7, 4, 5, '07-03-2018', 'Epis�dio 7'),
(8, 4, 5, '14-03-2018', 'Epis�dio 8')

insert into episodiosAssistidos values
(1, 1, 1, 1, 3),
(1, 2, 1, 1, 4),
(1, 3, 1, 1, 4),
(1, 4, 1, 1, 5),
(1, 5, 1, 1, 5),
(1, 6, 1, 1, 5),
(1, 7, 1, 1, 4),
(1, 8, 1, 1, 2),
(1, 1, 2, 1, 5),
(1, 2, 2, 1, 4),
(1, 3, 2, 1, 5),
(1, 4, 2, 1, 5),
(1, 5, 2, 1, 4),
(1, 6, 2, 1, 4),
(1, 7, 2, 1, 5),
(1, 8, 2, 1, 5),
(1, 1, 3, 1, 5),
(1, 2, 3, 1, 4),
(1, 3, 3, 1, 5),
(1, 4, 3, 1, 3),
(1, 5, 3, 1, 3),
(1, 6, 3, 1, 4),
(1, 7, 3, 1, 5),
(1, 8, 3, 1, 5),
(1, 1, 4, 1, 5),
(1, 2, 4, 1, 5),
(1, 3, 4, 1, 4),
(1, 4, 4, 1, 4),
(1, 5, 4, 1, 3),
(1, 6, 4, 1, 5),
(1, 7, 4, 1, 5),
(1, 8, 4, 1, 5),
(1, 1, 1, 2, 4),
(1, 2, 1, 2, 5),
(1, 3, 1, 2, 3),
(1, 4, 1, 2, 4),
(1, 5, 1, 2, 4),
(1, 6, 1, 2, 5),
(1, 7, 1, 2, 5),
(1, 8, 1, 2, 5),
(1, 1, 2, 2, 4),
(1, 2, 2, 2, 2),
(1, 3, 2, 2, 5),
(1, 4, 2, 2, 4),
(1, 5, 2, 2, 5),
(1, 6, 2, 2, 5),
(1, 7, 2, 2, 4),
(1, 8, 2, 2, 4),
(1, 1, 3, 2, 5),
(1, 2, 3, 2, 5),
(1, 3, 3, 2, 5),
(1, 4, 3, 2, 4),
(1, 5, 3, 2, 5),
(1, 6, 3, 2, 3),
(1, 7, 3, 2, 3),
(1, 8, 3, 2, 4),
(1, 1, 4, 2, 5),
(1, 2, 4, 2, 5),
(1, 3, 4, 2, 5),
(1, 4, 4, 2, 5),
(1, 5, 4, 2, 4),
(1, 6, 4, 2, 4),
(1, 7, 4, 2, 3),
(1, 8, 4, 2, 5),
(1, 1, 1, 3, 5),
(1, 2, 1, 3, 5),
(1, 3, 1, 3, 4),
(1, 4, 1, 3, 5),
(1, 5, 1, 3, 3),
(1, 6, 1, 3, 4),
(1, 7, 1, 3, 4),
(1, 8, 1, 3, 5),
(1, 1, 2, 3, 5),
(1, 2, 2, 3, 5),
(1, 3, 2, 3, 4),
(1, 4, 2, 3, 2),
(1, 5, 2, 3, 5),
(1, 6, 2, 3, 4),
(1, 7, 2, 3, 5),
(1, 8, 2, 3, 5),
(1, 1, 3, 3, 4),
(1, 2, 3, 3, 4),
(1, 3, 3, 3, 5),
(1, 4, 3, 3, 5),
(1, 5, 3, 3, 5),
(1, 6, 3, 3, 4),
(1, 7, 3, 3, 5),
(1, 8, 3, 3, 3),
(1, 1, 4, 3, 3),
(1, 2, 4, 3, 4),
(1, 3, 4, 3, 5),
(1, 4, 4, 3, 5),
(1, 5, 4, 3, 5),
(1, 6, 4, 3, 5),
(1, 7, 4, 3, 4),
(1, 8, 4, 3, 4),
(1, 1, 1, 4, 3),
(1, 2, 1, 4, 5),
(1, 3, 1, 4, 5),
(1, 4, 1, 4, 5),
(1, 5, 1, 4, 4),
(1, 6, 1, 4, 5),
(1, 7, 1, 4, 3),
(1, 8, 1, 4, 4),
(1, 1, 2, 4, 4),
(1, 2, 2, 4, 5),
(1, 3, 2, 4, 5),
(1, 4, 2, 4, 5),
(1, 5, 2, 4, 4),
(1, 6, 2, 4, 2),
(1, 7, 2, 4, 5),
(1, 8, 2, 4, 4),
(1, 1, 3, 4, 5),
(1, 2, 3, 4, 5),
(1, 3, 3, 4, 4),
(1, 4, 3, 4, 4),
(1, 5, 3, 4, 5),
(1, 6, 3, 4, 5),
(1, 7, 3, 4, 5),
(1, 8, 3, 4, 4),
(1, 1, 4, 4, 5),
(1, 2, 4, 4, 3),
(1, 3, 4, 4, 3),
(1, 4, 4, 4, 4),
(1, 5, 4, 4, 5),
(1, 6, 4, 4, 5),
(1, 7, 4, 4, 5),
(1, 8, 4, 4, 5),
(1, 1, 1, 5, 4),
(1, 2, 1, 5, 4),
(1, 3, 1, 5, 3),
(1, 4, 1, 5, 5),
(1, 5, 1, 5, 5),
(1, 6, 1, 5, 5),
(1, 7, 1, 5, 4),
(1, 8, 1, 5, 5),
(1, 1, 2, 5, 3),
(1, 2, 2, 5, 4),
(1, 3, 2, 5, 4),
(1, 4, 2, 5, 5),
(1, 5, 2, 5, 5),
(1, 6, 2, 5, 5),
(1, 7, 2, 5, 4),
(1, 8, 2, 5, 2),
(1, 1, 3, 5, 5),
(1, 2, 3, 5, 4),
(1, 3, 3, 5, 5),
(1, 4, 3, 5, 5),
(1, 5, 3, 5, 4),
(1, 6, 3, 5, 4),
(1, 7, 3, 5, 5),
(1, 8, 3, 5, 5),
(1, 1, 4, 5, 5),
(1, 2, 4, 5, 4),
(1, 3, 4, 5, 5),
(1, 4, 4, 5, 3),
(1, 5, 4, 5, 3),
(1, 6, 4, 5, 4),
(1, 7, 4, 5, 5),
(1, 8, 4, 5, 5),
(2, 1, 1, 1, 5),
(2, 2, 1, 1, 5),
(2, 3, 1, 1, 4),
(2, 4, 1, 1, 4),
(2, 5, 1, 1, 3),
(2, 6, 1, 1, 5),
(2, 7, 1, 1, 5),
(2, 8, 1, 1, 5),
(2, 1, 2, 1, 4),
(2, 2, 2, 1, 5),
(2, 3, 2, 1, 3),
(2, 4, 2, 1, 4),
(2, 5, 2, 1, 4),
(2, 6, 2, 1, 5),
(2, 7, 2, 1, 5),
(2, 8, 2, 1, 5),
(2, 1, 3, 1, 4),
(2, 2, 3, 1, 2),
(2, 3, 3, 1, 5),
(2, 4, 3, 1, 4),
(2, 5, 3, 1, 5),
(2, 6, 3, 1, 5),
(2, 7, 3, 1, 4),
(2, 8, 3, 1, 4),
(2, 1, 4, 1, 5),
(2, 2, 4, 1, 5),
(2, 3, 4, 1, 5),
(2, 4, 4, 1, 4),
(2, 5, 4, 1, 5),
(2, 6, 4, 1, 3),
(2, 7, 4, 1, 3),
(2, 8, 4, 1, 4),
(2, 1, 1, 2, 5),
(2, 2, 1, 2, 5),
(2, 3, 1, 2, 5),
(2, 4, 1, 2, 5),
(2, 5, 1, 2, 4),
(2, 6, 1, 2, 4),
(2, 7, 1, 2, 3),
(2, 8, 1, 2, 5),
(2, 1, 2, 2, 5),
(2, 2, 2, 2, 5),
(2, 3, 2, 2, 4),
(2, 4, 2, 2, 5),
(2, 5, 2, 2, 3),
(2, 6, 2, 2, 4),
(2, 7, 2, 2, 4),
(2, 8, 2, 2, 5),
(2, 1, 3, 2, 5),
(2, 2, 3, 2, 5),
(2, 3, 3, 2, 4),
(2, 4, 3, 2, 2),
(2, 5, 3, 2, 5),
(2, 6, 3, 2, 4),
(2, 7, 3, 2, 5),
(2, 8, 3, 2, 5),
(2, 1, 4, 2, 4),
(2, 2, 4, 2, 4),
(2, 3, 4, 2, 5),
(2, 4, 4, 2, 5),
(2, 5, 4, 2, 5),
(2, 6, 4, 2, 4),
(2, 7, 4, 2, 5),
(2, 8, 4, 2, 3),
(2, 1, 1, 3, 3),
(2, 2, 1, 3, 4),
(2, 3, 1, 3, 5),
(2, 4, 1, 3, 5),
(2, 5, 1, 3, 5),
(2, 6, 1, 3, 5),
(2, 7, 1, 3, 4),
(2, 8, 1, 3, 4),
(2, 1, 2, 3, 3),
(2, 2, 2, 3, 5),
(2, 3, 2, 3, 5),
(2, 4, 2, 3, 5),
(2, 5, 2, 3, 4),
(2, 6, 2, 3, 5),
(2, 7, 2, 3, 3),
(2, 8, 2, 3, 4),
(2, 1, 3, 3, 4),
(2, 2, 3, 3, 5),
(2, 3, 3, 3, 5),
(2, 4, 3, 3, 5),
(2, 5, 3, 3, 4),
(2, 6, 3, 3, 2),
(2, 7, 3, 3, 5),
(2, 8, 3, 3, 4),
(2, 1, 4, 3, 5),
(2, 2, 4, 3, 5),
(2, 3, 4, 3, 4),
(2, 4, 4, 3, 4),
(2, 5, 4, 3, 5),
(2, 6, 4, 3, 5),
(2, 7, 4, 3, 5),
(2, 8, 4, 3, 4),
(2, 1, 1, 4, 5),
(2, 2, 1, 4, 3),
(2, 3, 1, 4, 3),
(2, 4, 1, 4, 4),
(2, 5, 1, 4, 5),
(2, 6, 1, 4, 5),
(2, 7, 1, 4, 5),
(2, 8, 1, 4, 5),
(2, 1, 2, 4, 4),
(2, 2, 2, 4, 4),
(2, 3, 2, 4, 3),
(2, 4, 2, 4, 5),
(2, 5, 2, 4, 5),
(2, 6, 2, 4, 5),
(2, 7, 2, 4, 4),
(2, 8, 2, 4, 5),
(2, 1, 3, 4, 3),
(2, 2, 3, 4, 4),
(2, 3, 3, 4, 4),
(2, 4, 3, 4, 5),
(2, 5, 3, 4, 5),
(2, 6, 3, 4, 5),
(2, 7, 3, 4, 4),
(2, 8, 3, 4, 2),
(2, 1, 4, 4, 5),
(2, 2, 4, 4, 4),
(2, 3, 4, 4, 5),
(2, 4, 4, 4, 5),
(2, 5, 4, 4, 4),
(2, 6, 4, 4, 4),
(2, 7, 4, 4, 5),
(2, 8, 4, 4, 5),
(2, 1, 1, 5, 5),
(2, 2, 1, 5, 4),
(2, 3, 1, 5, 5),
(2, 4, 1, 5, 3),
(2, 5, 1, 5, 3),
(2, 6, 1, 5, 4),
(2, 7, 1, 5, 5),
(2, 8, 1, 5, 5),
(2, 1, 2, 5, 5),
(2, 2, 2, 5, 5),
(2, 3, 2, 5, 4),
(2, 4, 2, 5, 4),
(2, 5, 2, 5, 3),
(2, 6, 2, 5, 5),
(2, 7, 2, 5, 5),
(2, 8, 2, 5, 5),
(2, 1, 3, 5, 4),
(2, 2, 3, 5, 5),
(2, 3, 3, 5, 3),
(2, 4, 3, 5, 4),
(2, 5, 3, 5, 4),
(2, 6, 3, 5, 5),
(2, 7, 3, 5, 5),
(2, 8, 3, 5, 5),
(2, 1, 4, 5, 4),
(2, 2, 4, 5, 2),
(2, 3, 4, 5, 5),
(2, 4, 4, 5, 4),
(2, 5, 4, 5, 5),
(2, 6, 4, 5, 5),
(2, 7, 4, 5, 4),
(2, 8, 4, 5, 4),
(3, 1, 1, 1, 5),
(3, 2, 1, 1, 5),
(3, 3, 1, 1, 5),
(3, 4, 1, 1, 4),
(3, 5, 1, 1, 5),
(3, 6, 1, 1, 3),
(3, 7, 1, 1, 3),
(3, 8, 1, 1, 4),
(3, 1, 2, 1, 5),
(3, 2, 2, 1, 5),
(3, 3, 2, 1, 5),
(3, 4, 2, 1, 5),
(3, 5, 2, 1, 4),
(3, 6, 2, 1, 4),
(3, 7, 2, 1, 3),
(3, 8, 2, 1, 5),
(3, 1, 3, 1, 5),
(3, 2, 3, 1, 5),
(3, 3, 3, 1, 4),
(3, 4, 3, 1, 5),
(3, 5, 3, 1, 3),
(3, 6, 3, 1, 4),
(3, 7, 3, 1, 4),
(3, 8, 3, 1, 5),
(3, 1, 4, 1, 5),
(3, 2, 4, 1, 5),
(3, 3, 4, 1, 4),
(3, 4, 4, 1, 2),
(3, 5, 4, 1, 5),
(3, 6, 4, 1, 4),
(3, 7, 4, 1, 5),
(3, 8, 4, 1, 5),
(3, 1, 1, 2, 4),
(3, 2, 1, 2, 4),
(3, 3, 1, 2, 5),
(3, 4, 1, 2, 5),
(3, 5, 1, 2, 5),
(3, 6, 1, 2, 4),
(3, 7, 1, 2, 5),
(3, 8, 1, 2, 3),
(3, 1, 2, 2, 3),
(3, 2, 2, 2, 4),
(3, 3, 2, 2, 5),
(3, 4, 2, 2, 5),
(3, 5, 2, 2, 5),
(3, 6, 2, 2, 5),
(3, 7, 2, 2, 4),
(3, 8, 2, 2, 4),
(3, 1, 3, 2, 3),
(3, 2, 3, 2, 5),
(3, 3, 3, 2, 5),
(3, 4, 3, 2, 5),
(3, 5, 3, 2, 4),
(3, 6, 3, 2, 5),
(3, 7, 3, 2, 3),
(3, 8, 3, 2, 4),
(3, 1, 4, 2, 4),
(3, 2, 4, 2, 5),
(3, 3, 4, 2, 5),
(3, 4, 4, 2, 5),
(3, 5, 4, 2, 4),
(3, 6, 4, 2, 2),
(3, 7, 4, 2, 5),
(3, 8, 4, 2, 4),
(3, 1, 1, 3, 5),
(3, 2, 1, 3, 5),
(3, 3, 1, 3, 4),
(3, 4, 1, 3, 4),
(3, 5, 1, 3, 5),
(3, 6, 1, 3, 5),
(3, 7, 1, 3, 5),
(3, 8, 1, 3, 4),
(3, 1, 2, 3, 5),
(3, 2, 2, 3, 3),
(3, 3, 2, 3, 3),
(3, 4, 2, 3, 4),
(3, 5, 2, 3, 5),
(3, 6, 2, 3, 5),
(3, 7, 2, 3, 5),
(3, 8, 2, 3, 5),
(3, 1, 3, 3, 4),
(3, 2, 3, 3, 4),
(3, 3, 3, 3, 3),
(3, 4, 3, 3, 5),
(3, 5, 3, 3, 5),
(3, 6, 3, 3, 5),
(3, 7, 3, 3, 4),
(3, 8, 3, 3, 5),
(3, 1, 4, 3, 3),
(3, 2, 4, 3, 4),
(3, 3, 4, 3, 4),
(3, 4, 4, 3, 5),
(3, 5, 4, 3, 5),
(3, 6, 4, 3, 5),
(3, 7, 4, 3, 4),
(3, 8, 4, 3, 2),
(3, 1, 1, 4, 5),
(3, 2, 1, 4, 4),
(3, 3, 1, 4, 5),
(3, 4, 1, 4, 5),
(3, 5, 1, 4, 4),
(3, 6, 1, 4, 4),
(3, 7, 1, 4, 5),
(3, 8, 1, 4, 5),
(3, 1, 2, 4, 5),
(3, 2, 2, 4, 4),
(3, 3, 2, 4, 5),
(3, 4, 2, 4, 3),
(3, 5, 2, 4, 3),
(3, 6, 2, 4, 4),
(3, 7, 2, 4, 5),
(3, 8, 2, 4, 5),
(3, 1, 3, 4, 5),
(3, 2, 3, 4, 5),
(3, 3, 3, 4, 4),
(3, 4, 3, 4, 4),
(3, 5, 3, 4, 3),
(3, 6, 3, 4, 5),
(3, 7, 3, 4, 5),
(3, 8, 3, 4, 5),
(3, 1, 4, 4, 4),
(3, 2, 4, 4, 5),
(3, 3, 4, 4, 3),
(3, 4, 4, 4, 4),
(3, 5, 4, 4, 4),
(3, 6, 4, 4, 5),
(3, 7, 4, 4, 5),
(3, 8, 4, 4, 5),
(3, 1, 1, 5, 4),
(3, 2, 1, 5, 2),
(3, 3, 1, 5, 5),
(3, 4, 1, 5, 4),
(3, 5, 1, 5, 5),
(3, 6, 1, 5, 5),
(3, 7, 1, 5, 4),
(3, 8, 1, 5, 4),
(3, 1, 2, 5, 5),
(3, 2, 2, 5, 5),
(3, 3, 2, 5, 5),
(3, 4, 2, 5, 4),
(3, 5, 2, 5, 5),
(3, 6, 2, 5, 3),
(3, 7, 2, 5, 3),
(3, 8, 2, 5, 4),
(3, 1, 3, 5, 5),
(3, 2, 3, 5, 5),
(3, 3, 3, 5, 5),
(3, 4, 3, 5, 5),
(3, 5, 3, 5, 4),
(3, 6, 3, 5, 4),
(3, 7, 3, 5, 3),
(3, 8, 3, 5, 5),
(3, 1, 4, 5, 5),
(3, 2, 4, 5, 5),
(3, 3, 4, 5, 4),
(3, 4, 4, 5, 5),
(3, 5, 4, 5, 3),
(3, 6, 4, 5, 4),
(3, 7, 4, 5, 4),
(3, 8, 4, 5, 5),
(4, 1, 1, 1, 5),
(4, 2, 1, 1, 5),
(4, 3, 1, 1, 4),
(4, 4, 1, 1, 2),
(4, 5, 1, 1, 5),
(4, 6, 1, 1, 4),
(4, 7, 1, 1, 5),
(4, 8, 1, 1, 5),
(4, 1, 2, 1, 4),
(4, 2, 2, 1, 4),
(4, 3, 2, 1, 5),
(4, 4, 2, 1, 5),
(4, 5, 2, 1, 5),
(4, 6, 2, 1, 4),
(4, 7, 2, 1, 5),
(4, 8, 2, 1, 3),
(4, 1, 3, 1, 3),
(4, 2, 3, 1, 4),
(4, 3, 3, 1, 5),
(4, 4, 3, 1, 5),
(4, 5, 3, 1, 5),
(4, 6, 3, 1, 5),
(4, 7, 3, 1, 4),
(4, 8, 3, 1, 4),
(4, 1, 4, 1, 3),
(4, 2, 4, 1, 5),
(4, 3, 4, 1, 5),
(4, 4, 4, 1, 5),
(4, 5, 4, 1, 4),
(4, 6, 4, 1, 5),
(4, 7, 4, 1, 3),
(4, 8, 4, 1, 4),
(4, 1, 1, 2, 4),
(4, 2, 1, 2, 5),
(4, 3, 1, 2, 5),
(4, 4, 1, 2, 5),
(4, 5, 1, 2, 4),
(4, 6, 1, 2, 2),
(4, 7, 1, 2, 5),
(4, 8, 1, 2, 4),
(4, 1, 2, 2, 5),
(4, 2, 2, 2, 5),
(4, 3, 2, 2, 4),
(4, 4, 2, 2, 4),
(4, 5, 2, 2, 5),
(4, 6, 2, 2, 5),
(4, 7, 2, 2, 5),
(4, 8, 2, 2, 4),
(4, 1, 3, 2, 5),
(4, 2, 3, 2, 3),
(4, 3, 3, 2, 3),
(4, 4, 3, 2, 4),
(4, 5, 3, 2, 5),
(4, 6, 3, 2, 5),
(4, 7, 3, 2, 5),
(4, 8, 3, 2, 5),
(4, 1, 4, 2, 4),
(4, 2, 4, 2, 4),
(4, 3, 4, 2, 3),
(4, 4, 4, 2, 5),
(4, 5, 4, 2, 5),
(4, 6, 4, 2, 5),
(4, 7, 4, 2, 4),
(4, 8, 4, 2, 5),
(4, 1, 1, 3, 3),
(4, 2, 1, 3, 4),
(4, 3, 1, 3, 4),
(4, 4, 1, 3, 5),
(4, 5, 1, 3, 5),
(4, 6, 1, 3, 5),
(4, 7, 1, 3, 4),
(4, 8, 1, 3, 2),
(4, 1, 2, 3, 5),
(4, 2, 2, 3, 4),
(4, 3, 2, 3, 5),
(4, 4, 2, 3, 5),
(4, 5, 2, 3, 4),
(4, 6, 2, 3, 4),
(4, 7, 2, 3, 5),
(4, 8, 2, 3, 5),
(4, 1, 3, 3, 5),
(4, 2, 3, 3, 4),
(4, 3, 3, 3, 5),
(4, 4, 3, 3, 3),
(4, 5, 3, 3, 3),
(4, 6, 3, 3, 4),
(4, 7, 3, 3, 5),
(4, 8, 3, 3, 5),
(4, 1, 4, 3, 5),
(4, 2, 4, 3, 5),
(4, 3, 4, 3, 4),
(4, 4, 4, 3, 4),
(4, 5, 4, 3, 3),
(4, 6, 4, 3, 5),
(4, 7, 4, 3, 5),
(4, 8, 4, 3, 5),
(4, 1, 1, 4, 4),
(4, 2, 1, 4, 5),
(4, 3, 1, 4, 3),
(4, 4, 1, 4, 4),
(4, 5, 1, 4, 4),
(4, 6, 1, 4, 5),
(4, 7, 1, 4, 5),
(4, 8, 1, 4, 5),
(4, 1, 2, 4, 4),
(4, 2, 2, 4, 2),
(4, 3, 2, 4, 5),
(4, 4, 2, 4, 4),
(4, 5, 2, 4, 5),
(4, 6, 2, 4, 5),
(4, 7, 2, 4, 4),
(4, 8, 2, 4, 4),
(4, 1, 3, 4, 5),
(4, 2, 3, 4, 5),
(4, 3, 3, 4, 5),
(4, 4, 3, 4, 4),
(4, 5, 3, 4, 5),
(4, 6, 3, 4, 3),
(4, 7, 3, 4, 3),
(4, 8, 3, 4, 4),
(4, 1, 4, 4, 5),
(4, 2, 4, 4, 5),
(4, 3, 4, 4, 5),
(4, 4, 4, 4, 5),
(4, 5, 4, 4, 4),
(4, 6, 4, 4, 4),
(4, 7, 4, 4, 3),
(4, 8, 4, 4, 5),
(4, 1, 1, 5, 5),
(4, 2, 1, 5, 5),
(4, 3, 1, 5, 4),
(4, 4, 1, 5, 5),
(4, 5, 1, 5, 3),
(4, 6, 1, 5, 4),
(4, 7, 1, 5, 4),
(4, 8, 1, 5, 5),
(4, 1, 2, 5, 5),
(4, 2, 2, 5, 5),
(4, 3, 2, 5, 4),
(4, 4, 2, 5, 2),
(4, 5, 2, 5, 5),
(4, 6, 2, 5, 4),
(4, 7, 2, 5, 5),
(4, 8, 2, 5, 5),
(4, 1, 3, 5, 4),
(4, 2, 3, 5, 4),
(4, 3, 3, 5, 5),
(4, 4, 3, 5, 5),
(4, 5, 3, 5, 5),
(4, 6, 3, 5, 4),
(4, 7, 3, 5, 5),
(4, 8, 3, 5, 3),
(4, 1, 4, 5, 3),
(4, 2, 4, 5, 4),
(4, 3, 4, 5, 5),
(4, 4, 4, 5, 5),
(4, 5, 4, 5, 5),
(4, 6, 4, 5, 5),
(4, 7, 4, 5, 4),
(4, 8, 4, 5, 4)
             
DROP LOGIN bancodeseries
go           
             
DROP USER bancodeseries
GO           
             
CREATE LOGIN bancodeseries WITH PASSWORD = 'bancodeseries'
GO           
             
CREATE USER bancodeseries FOR LOGIN bancodeseries
GO           
             
GRANT        
       ALTER,AUTHENTICATE,
       CONNECT,
       DELETE,
       EXECUTE,
       INSERT,
       SELECT,
       UPDATE
TO bancodeseries;
GO           
             
DENY         
       ALTER ANY APPLICATION ROLE,
       ALTER ANY ASYMMETRIC KEY,
       ALTER ANY CERTIFICATE,
       ALTER ANY DATABASE DDL TRIGGER,
       ALTER ANY ROLE,
       ALTER ANY SCHEMA,
       ALTER ANY SYMMETRIC KEY,
       ALTER ANY USER,
       CHECKPOINT,
       CREATE ASYMMETRIC KEY,
       CREATE CERTIFICATE,
       CREATE CONTRACT,
       CREATE ROLE,
       CREATE SYMMETRIC KEY,
	   VIEW DATABASE STATE,
       VIEW DEFINITION
             
TO bancodeseries;
GO           
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             