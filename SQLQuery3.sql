SELECT TOP (1000) [project_id]
      ,[project_name]
      ,[Description]
      ,[Status]
      ,[estimated_launch]
  FROM [PRJ301_Workshop01].[dbo].[tblStartupProjects]

DROP TABLE [dbo].[tblStartupProjects]

CREATE TABLE tblStartupProjects (
    project_id INT PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL CHECK (status IN ('Ideation', 'Development', 'Launch', 'Scaling')),
    estimated_launch DATE NOT NULL
);

INSERT INTO tblStartupProjects (project_id, project_name, description, status, estimated_launch)
VALUES
(1, 'Food Delivery App', 'An app to connect restaurants with customers.', 'Development', '2025-08-01'),
(2, 'AI Chatbot', 'Customer support chatbot using NLP.', 'Ideation', '2025-12-15'),
(3, 'E-Learning Platform', 'Platform for online courses and live sessions.', 'Launch', '2025-07-10'),
(4, 'Smart Home Controller', 'IoT-based smart home management system.', 'Scaling', '2025-06-30'),
(5, 'Freelancer Marketplace', 'A platform connecting freelancers and clients.', 'Development', '2025-10-05');

