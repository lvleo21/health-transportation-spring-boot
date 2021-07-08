-- INSERT ROLES
INSERT into roles (role)
SELECT 'OPERADOR' WHERE 'OPERADOR' NOT IN (SELECT role FROM roles);
INSERT into roles (role)
SELECT 'GESTOR' WHERE 'GESTOR' NOT IN (SELECT role FROM roles);
INSERT into roles (role)
SELECT 'ADMIN' WHERE 'ADMIN' NOT IN (SELECT role FROM roles);


-- PROCEDURES
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

CREATE OR REPLACE FUNCTION set_passenger_in_travel()
RETURNS trigger AS
'BEGIN

	IF (TG_OP = ''DELETE'') THEN
		UPDATE passengers
		SET in_travel = ( CASE WHEN in_travel = true then false else true END)
		WHERE id = OLD.passenger_id;
		RETURN NEW;
	ELSIF (TG_OP = ''INSERT'') THEN
		UPDATE passengers
		SET in_travel = ( CASE WHEN in_travel = true then false else true END)
		WHERE id = NEW.passenger_id;
		RETURN NEW;
	END IF;
	RETURN NEW;

END;'
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION change_all_passenger_in_travel()
RETURNS trigger AS
'BEGIN

    IF (TG_OP = ''INSERT'') THEN
        UPDATE passengers
        SET in_travel = true
        FROM (SELECT passenger_id, travel_id from locations where travel_id = NEW.id) AS subquery
        WHERE id = subquery.passenger_id;
        RETURN NEW;
    ELSIF(TG_OP = ''UPDATE'' AND NEW.status = ''CONCLUIDO'') THEN
        UPDATE passengers
        SET in_travel = false
        FROM (SELECT passenger_id,
                     travel_id
              from locations
              where travel_id = NEW.id) AS subquery
        WHERE id = subquery.passenger_id;
        RETURN NEW;
    END IF;
    RETURN NEW;

END;'
LANGUAGE plpgsql;
---

-- DROP TRIGGERS
DROP TRIGGER IF EXISTS trigger_create_log_vehicle ON vehicles;
DROP TRIGGER IF EXISTS trigger_create_log_driver ON drivers;
DROP TRIGGER IF EXISTS trigger_create_log_user ON users;
DROP TRIGGER IF EXISTS trigger_create_log_travel ON travels;
DROP TRIGGER IF EXISTS trigger_change_all_passenger_in_travel ON travels;
DROP TRIGGER IF EXISTS trigger_create_log_prefecture ON prefectures;
DROP TRIGGER IF EXISTS trigger_create_log_passenger ON passengers;
DROP TRIGGER IF EXISTS trigger_create_log_order_reset_password ON order_reset_passwords;
DROP TRIGGER IF EXISTS trigger_create_log_location ON locations;
DROP TRIGGER IF EXISTS trigger_set_passenger_in_travel ON locations;
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

CREATE TRIGGER trigger_set_passenger_in_travel
    AFTER INSERT OR DELETE
ON locations
    FOR EACH ROW
    EXECUTE PROCEDURE set_passenger_in_travel();

CREATE TRIGGER trigger_change_all_passenger_in_travel
    AFTER UPDATE OR DELETE
ON travels
    FOR EACH ROW
    EXECUTE PROCEDURE change_all_passenger_in_travel();
-- END TRIGGERS


-- DROP VIEWS
DROP VIEW IF EXISTS ExportLocationsView;
DROP VIEW IF EXISTS TravelsPerMonthByCurrentYear;
DROP VIEW IF EXISTS LocationsPerNeighborhood;
-- END DROP VIEWS

-- VIEWS
-- GET A LIST OF LOCATIONS
CREATE OR REPLACE VIEW ExportLocationsView as
SELECT
    t.id AS travel_id, p.name, l.category, p.date_of_birth, p.rg, p.sus,
    CONCAT (a.public_place, ', ', a.number, ', ',a.neighborhood) as address, l.transition,
    l.destination_hospital, p.cell_phone
FROM
    locations l
INNER JOIN passengers p ON l.passenger_id = p.id
INNER JOIN adresses a ON p.address_id = a.id
INNER JOIN travels t ON l.travel_id  = t.id;

-- GET A QUANTITY OF TRAVELS PER MONTH IN CURRENT YEAR
CREATE OR REPLACE VIEW TravelsPerMonthByCurrentYear AS
SELECT
    row_number() OVER () as id,
    COUNT(EXTRACT(MONTH FROM departure_date)),
    EXTRACT(MONTH FROM departure_date) as month,
    (SELECT date_part('year', (SELECT current_timestamp)) as current_year),
    hc.id as health_center_id
FROM
    travels t, health_centers hc
WHERE
    EXTRACT(YEAR FROM departure_date) = (SELECT date_part('year', (SELECT current_timestamp))) and t.health_center_id = hc.id
GROUP BY
    EXTRACT(MONTH FROM departure_date), hc.id;

-- GET LOCATIONS PER HEALTHCENTER AND NEIGHBORHOOD
CREATE OR REPLACE VIEW LocationsPerNeighborhood AS

SELECT
    COUNT(a.neighborhood), a.neighborhood, hc.id AS health_center_id
FROM
    locations l , travels t, passengers p, adresses a, health_centers hc
WHERE
    t.status = 'CONCLUIDO' AND l.travel_id  = t.id AND l.passenger_id = p.id AND p.address_id = a.id AND t.health_center_id = hc.id
GROUP BY
    a.neighborhood, hc.id
ORDER BY
    a.neighborhood;
-- ENDVIEWS