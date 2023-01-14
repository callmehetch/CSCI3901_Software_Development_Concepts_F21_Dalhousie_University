-- SET FOREIGN_KEY_CHECKS=0; -- to disable them
-- DELETE FROM shipping;
-- SET FOREIGN_KEY_CHECKS=1; -- to re-enable them
-- INSERT INTO shipping VALUES (1, 'South Atlantic Logistics');
-- INSERT INTO shipping VALUES (2, 'North Carolina Movers');
-- INSERT INTO shipping VALUES (3, 'Seattle Shippers');

SET FOREIGN_KEY_CHECKS=0; -- to disable them
DELETE FROM shipping;
SET FOREIGN_KEY_CHECKS=1; -- to re-enable them
INSERT INTO shipping(shipperName) VALUES ('South Atlantic Logistics');
INSERT INTO shipping(shipperName) VALUES ('North Carolina Movers');
INSERT INTO shipping(shipperName) VALUES ('Seattle Shippers');

-- SET FOREIGN_KEY_CHECKS=0; -- to disable them
-- DELETE from shippercentage;
-- SET FOREIGN_KEY_CHECKS=1; -- to re-enable them
-- INSERT INTO shippercentage (shippercentageID, shippingPercentage, shipPercentshipperID) VALUES (101, 22.3,1);
-- INSERT INTO shippercentage (shippercentageID, shippingPercentage, shipPercentshipperID) VALUES (102, 18,2);
-- INSERT INTO shippercentage (shippercentageID, shippingPercentage, shipPercentshipperID) VALUES (103, 15.5,3);

INSERT INTO taxes(taxPercentage) VALUES (30);
INSERT INTO taxes(taxPercentage) VALUES (20.5);
INSERT INTO taxes(taxPercentage) VALUES (45.99);

DELETE FROM officestax;
INSERT INTO officestax(officesTaxID, officesTaxCode) VALUES (3,1);
INSERT INTO officestax(officesTaxID, officesTaxCode) VALUES (2,2);
INSERT INTO officestax(officesTaxID, officesTaxCode) VALUES (1,6);
INSERT INTO officestax(officesTaxID, officesTaxCode) VALUES (2,7);
INSERT INTO officestax(officesTaxID, officesTaxCode) SELECT 2, officeCode from offices WHERE city="NYC";
INSERT INTO officestax(officesTaxID, officesTaxCode) SELECT 3, officeCode from offices WHERE city="Tokyo";
INSERT INTO officestax(officesTaxID, officesTaxCode) SELECT 2, officeCode from offices WHERE city="Paris";
INSERT INTO officestax(officesTaxID, officesTaxCode) SELECT 1, officeCode from offices WHERE city="Paris";
-- -----------------------------------------------------------------------------------------------------------------
-- SELECT * FROM officestax;
-- SELECT * FROM shipping;
-- SELECT * FROM shippercentage;
-- SELECT * FROM offices;
-- SELECT * FROM taxes;