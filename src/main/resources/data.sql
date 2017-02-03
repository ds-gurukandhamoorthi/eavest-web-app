-- USERS IN APPLICATION
INSERT INTO test.EAV_ACCOUNT(e_mail, password) VALUES('eav_admin@mailinator.com', 'eav_password');
INSERT INTO test.EAV_ACCOUNT(e_mail, password) VALUES('eav_user@mailinator.com', 'eav_password');

-- DEFINING ROLES
INSERT INTO test.EAV_ROLE(id, code) VALUES('1', 'ADMIN');
INSERT INTO test.EAV_ROLE(id, code) VALUES(2, 'USER');

-- SET ROLES
-- INSERT INTO test.EAV_ACCOUNT_ROLE(id_eav_account, id_eav_role) VALUES();