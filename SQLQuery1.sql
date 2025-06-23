USE master
go

CREATE DATABASE PRJ301_Workshop01
go

USE [PRJ301_Workshop01]
go

CREATE TABLE tblUsers
(
	Username varchar(50) primary key,
	Name varchar(100) not null,
	Password varchar(255) not null,
	Role varchar(20) not null
)

CREATE TABLE tblStartupProjects
(
	project_id int primary key,
	project_name varchar(100) not null,
	Description text,
	Status varchar(20) not null,
	estimated_launch DATE not null
)

INSERT INTO [dbo].[tblUsers] (Username, Name, Password, Role)
VALUES
	('tungnd', 'Nguyen Duy Tung', 'founder', 'Founder'),
	('vuta', 'Tran Anh Vu', 'mem1', 'Team Member'),
	('longat', 'An Thanh Long', 'mem2', 'Teeam Member')