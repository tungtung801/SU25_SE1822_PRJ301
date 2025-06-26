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

CREATE TABLE tblStartupProjects (
    project_id INT PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL CHECK (status IN ('Ideation', 'Development', 'Launch', 'Scaling')),
    estimated_launch DATE NOT NULL
)

INSERT INTO [dbo].[tblUsers] (Username, Name, Password, Role)
VALUES
	('tung', 'Nguyen Duy Tung', '1', 'Founder'),
	('vu', 'Tran Anh Vu', '3', 'Team Member'),
	('long', 'An Thanh Long', '2', 'Teeam Member')

	INSERT INTO tblStartupProjects (project_id, project_name, description, status, estimated_launch)
VALUES
(1, 'Food Delivery App', 'An app to connect restaurants with customers.', 'Development', '2025-08-01'),
(2, 'AI Chatbot', 'Customer support chatbot using NLP.', 'Ideation', '2025-12-15'),
(3, 'E-Learning Platform', 'Platform for online courses and live sessions.', 'Launch', '2025-07-10'),
(4, 'Smart Home Controller', 'IoT-based smart home management system.', 'Scaling', '2025-06-30'),
(5, 'Freelancer Marketplace', 'A platform connecting freelancers and clients.', 'Development', '2025-10-05');