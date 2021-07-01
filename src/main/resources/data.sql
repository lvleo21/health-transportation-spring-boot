INSERT into roles (role)
SELECT 'OPERADOR' WHERE 'OPERADOR' NOT IN (SELECT role FROM roles);
INSERT into roles (role)
SELECT 'GESTOR' WHERE 'GESTOR' NOT IN (SELECT role FROM roles);
INSERT into roles (role)
SELECT 'ADMIN' WHERE 'ADMIN' NOT IN (SELECT role FROM roles);


--
CREATE OR REPLACE FUNCTION insert_log()
RETURNS trigger AS
'BEGIN IF (TG_OP = ''DELETE'') THEN
	INSERT INTO log (after_operation, before_operation, created_at, operation, table_name, created_by)
	VALUES (NEW, OLD, now(), TG_OP, TG_RELNAME, OLD.last_modified_By);
    RETURN NEW;
ELSIF (TG_OP = ''UPDATE'' or TG_OP = ''INSERT'') THEN
	INSERT INTO log (after_operation, before_operation, created_at, operation, table_name, created_by)
	VALUES (NEW, OLD, now(), TG_OP, TG_RELNAME, NEW.last_modified_By);
    RETURN NEW;
END IF;
RETURN NULL;
END;'
LANGUAGE plpgsql;
---

-- DROP TRIGGERS
DROP TRIGGER IF EXISTS trigger_create_log_vehicle ON vehicles;
DROP TRIGGER IF EXISTS trigger_create_log_driver ON drivers;
DROP TRIGGER IF EXISTS trigger_create_log_user ON users;
DROP TRIGGER IF EXISTS trigger_create_log_travel ON travels;
DROP TRIGGER IF EXISTS trigger_create_log_prefecture ON prefectures;
DROP TRIGGER IF EXISTS trigger_create_log_passenger ON passengers;
DROP TRIGGER IF EXISTS trigger_create_log_order_reset_password ON order_reset_passwords;
DROP TRIGGER IF EXISTS trigger_create_log_location ON locations;
DROP TRIGGER IF EXISTS trigger_create_log_health_center ON health_centers;
DROP TRIGGER IF EXISTS trigger_create_log_address ON adresses;
-- END DROP TRIGGERS

-- TRIGGERS
CREATE TRIGGER trigger_create_log_vehicle
    AFTER INSERT OR
UPDATE OR
DELETE
ON vehicles
    FOR EACH ROW
    EXECUTE PROCEDURE insert_log();

CREATE TRIGGER trigger_create_log_driver
    AFTER INSERT OR
UPDATE OR
DELETE
ON drivers
    FOR EACH ROW
    EXECUTE PROCEDURE insert_log();

CREATE TRIGGER trigger_create_log_user
    AFTER INSERT OR
UPDATE OR
DELETE
ON users
    FOR EACH ROW
    EXECUTE PROCEDURE insert_log();

CREATE TRIGGER trigger_create_log_travel
    AFTER INSERT OR
UPDATE OR
DELETE
ON travels
    FOR EACH ROW
    EXECUTE PROCEDURE insert_log();

CREATE TRIGGER trigger_create_log_prefecture
    AFTER INSERT OR
UPDATE OR
DELETE
ON prefectures
    FOR EACH ROW
    EXECUTE PROCEDURE insert_log();

CREATE TRIGGER trigger_create_log_passenger
    AFTER INSERT OR
UPDATE OR
DELETE
ON passengers
    FOR EACH ROW
    EXECUTE PROCEDURE insert_log();

CREATE TRIGGER trigger_create_log_order_reset_password
    AFTER INSERT OR
UPDATE OR
DELETE
ON order_reset_passwords
    FOR EACH ROW
    EXECUTE PROCEDURE insert_log();

CREATE TRIGGER trigger_create_log_location
    AFTER INSERT OR
UPDATE OR
DELETE
ON locations
    FOR EACH ROW
    EXECUTE PROCEDURE insert_log();

CREATE TRIGGER trigger_create_log_health_center
    AFTER INSERT OR
UPDATE OR
DELETE
ON health_centers
    FOR EACH ROW
    EXECUTE PROCEDURE insert_log();

CREATE TRIGGER trigger_create_log_address
    AFTER INSERT OR
UPDATE OR
DELETE
ON adresses
    FOR EACH ROW
    EXECUTE PROCEDURE insert_log();
-- END TRIGGERS
