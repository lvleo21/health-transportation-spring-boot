INSERT into roles (role)
SELECT 'OPERADOR' WHERE 'OPERADOR' NOT IN (SELECT role FROM roles);
INSERT into roles (role)
SELECT 'GESTOR' WHERE 'GESTOR' NOT IN (SELECT role FROM roles);
INSERT into roles (role)
SELECT 'ADMIN' WHERE 'ADMIN' NOT IN (SELECT role FROM roles);

CREATE OR REPLACE FUNCTION insert_log()
RETURNS TRIGGER
AS'
BEGIN
INSERT INTO log (after_operation, before_operation, created_at, operation, table_name, created_by)

VALUES (NEW, OLD, now(), TG_OP, TG_RELNAME, NEW.last_modified_By);

RETURN NEW;
END '
LANGUAGE plpgsql;


DROP TRIGGER IF EXISTS trigger_create_log ON vehicles;

CREATE TRIGGER trigger_create_log
    AFTER INSERT OR
UPDATE OR
DELETE
ON vehicles
    FOR EACH ROW
    EXECUTE PROCEDURE insert_log();
