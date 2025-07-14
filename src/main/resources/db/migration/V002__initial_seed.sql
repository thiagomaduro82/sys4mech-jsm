-- Role
insert into roles (uuid, name, created_at, updated_at) values
  (REPLACE(UUID(), '-', ''), 'ROLE_ADMIN', now(), now()),
  (REPLACE(UUID(), '-', ''), 'ROLE_USER', now(), now());

-- Permission
insert into permissions (uuid, name, description, created_at, updated_at) values
  (REPLACE(UUID(), '-', ''), 'PERMISSION_READ', 'View the permissions data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'PERMISSION_WRITE', 'Write the permissions data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'PERMISSION_DELETE', 'Delete the permissions data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'ROLE_READ', 'View the roles data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'ROLE_WRITE', 'Write the roles data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'ROLE_DELETE', 'Delete the roles data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'USER_READ', 'View the users data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'USER_WRITE', 'Write the users data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'USER_DELETE', 'Delete the roles data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'EMPLOYEE_READ', 'View the employees data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'EMPLOYEE_WRITE', 'Write the employees data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'EMPLOYEE_DELETE', 'Delete the employees data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'CUSTOMER_READ', 'View the customers data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'CUSTOMER_WRITE', 'Write the customers data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'CUSTOMER_DELETE', 'Delete the customers data', now(), now());

-- Role-Permission mapping
insert into role_permissions (role_id, permission_id) values
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'PERMISSION_READ')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'PERMISSION_WRITE')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'PERMISSION_DELETE')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'ROLE_READ')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'ROLE_WRITE')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'ROLE_DELETE')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'USER_READ')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'USER_WRITE')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'USER_DELETE')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'EMPLOYEE_READ')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'EMPLOYEE_WRITE')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'EMPLOYEE_DELETE')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'CUSTOMER_READ')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'CUSTOMER_WRITE')),
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'CUSTOMER_DELETE'));
