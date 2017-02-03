-- SECURITY: USER ACCOUNT
DROP TABLE IF EXISTS test.eav_account CASCADE;
CREATE TABLE test.eav_account ( E_MAIL VARCHAR(255) NOT NULL,
						PASSWORD VARCHAR(255) NOT NULL,
						ID SERIAL,
						ENABLED BOOL DEFAULT true);

-- SECURITY: ROLE
DROP TABLE IF EXISTS test.eav_role CASCADE;
CREATE TABLE test.eav_role ( ID SERIAL,
						CODE VARCHAR(20) NOT NULL);

DROP TABLE IF EXISTS test.eav_account_role;
CREATE TABLE test.eav_account_role ( ID_EAV_ACCOUNT bigint NOT NULL,
						ID_EAV_ROLE bigint NOT NULL);

-- JOIN TABLES
DROP TABLE IF EXISTS test.prd_user_product;
CREATE TABLE test.prd_user_product ( ID_PRD_USER bigint NOT NULL,
						ID_PRD_PRODUCT bigint NOT NULL);
