-- Role
insert into roles (uuid, name, created_at, updated_at) values
  (REPLACE(UUID(), '-', ''), 'ROLE_ADMIN', now(), now()),
  (REPLACE(UUID(), '-', ''), 'ROLE_USER', now(), now());

-- Permission
insert into permissions (uuid, name, description, created_at, updated_at) values
  (REPLACE(UUID(), '-', ''), 'PERMISSION_READ', 'Permission to read data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'PERMISSION_WRITE', 'Permission to write data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'PERMISSION_DELETE', 'Permission to delete data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'ROLE_READ', 'Role to read data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'ROLE_WRITE', 'Role to write data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'ROLE_DELETE', 'Role to delete data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'USER_READ', 'User to read data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'USER_WRITE', 'User to write data', now(), now()),
  (REPLACE(UUID(), '-', ''), 'USER_DELETE', 'User to delete data', now(), now());

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
  ((select id from roles where name = 'ROLE_ADMIN'), (select id from permissions where name = 'USER_DELETE'));
