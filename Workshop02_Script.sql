use master

use PRJ301_Workshop02

create table tblUsers (
	username varchar(50) primary key,
	name varchar(100) not null,
	password varchar(255) not null,
	role varchar(20) not null
)

create table tblExamCategories (
	category_id int identity(1,1) primary key,
	category_name varchar(50) not null,
	description text
)

create table tblExams (
	exam_id int identity(1,1) primary key,
	exam_tittle varchar(100) not null,
	subject varchar(50) not null,
	category_id int,
	total_marks int not null,
	duration int not null,
	FOREIGN KEY (category_id) REFERENCES tblExamCategories(category_id)
)

create table tblQuestions(
	question_id int identity(1,1) primary key,
	exam_id int ,
	question_text text not null,
	option_a varchar(100) not null,
	option_b varchar(100) not null,
	option_c varchar(100) not null,
	option_d varchar(100) not null,
	correct_option char(1) not null,
	FOREIGN KEY (exam_id) REFERENCES tblExams(exam_id)
)
USE master
CREATE DATABASE PRJ301_Workshop02