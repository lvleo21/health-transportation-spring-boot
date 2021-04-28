INSERT into roles (role) SELECT 'OPERADOR' WHERE 'OPERADOR' NOT IN (SELECT role FROM roles);
INSERT into roles (role) SELECT 'GESTOR' WHERE 'GESTOR' NOT IN (SELECT role FROM roles);
INSERT into roles (role) SELECT 'ADMIN' WHERE 'ADMIN' NOT IN (SELECT role FROM roles);