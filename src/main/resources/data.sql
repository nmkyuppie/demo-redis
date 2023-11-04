insert into Role (id, name) values (1, 'Approver'),(2, 'Maker'),(3, 'Viewer');

insert into Scope (id, name, role_id) values (1, 'Approve payment', 1),(2, 'Approve user', 1),(3, 'Approve template', 1),(4, 'Make international payment', 2),(5, 'Assign roles', 2),(6, 'View payments', 3),(7, 'View users', 3);