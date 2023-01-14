DROP TABLE IF EXISTS promotions;
CREATE TABLE promotions(
promoID INT NOT NULL AUTO_INCREMENT,
promoOfficeCode VARCHAR(10) NOT NULL,
promoCode VARCHAR(50) NOT NULL,
percentOff DOUBLE NOT NULL,
PRIMARY KEY (promoID),
FOREIGN KEY (promoOfficeCode) REFERENCES offices(officeCode)
);

drop table if exists taxes;
CREATE TABLE taxes(
taxID INT NOT NULL AUTO_INCREMENT,
taxPercentage DOUBLE NOT NULL,
taxImplementationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (taxID));

-- ALTER TABLE taxes RENAME COLUMN taxAmount TO taxPercentage;

drop table if exists officestax;
CREATE TABLE officestax(
officesTaxNumber INT NOT NULL AUTO_INCREMENT,
officesTaxID INT NOT NULL,
officesTaxCode VARCHAR(10) NOT NULL,
PRIMARY KEY (officesTaxNumber),
FOREIGN KEY (officesTaxID) REFERENCES taxes(taxID),
FOREIGN KEY (officesTaxCode) REFERENCES offices(officeCode)
);

DROP TABLE IF EXISTS shipping;
CREATE TABLE shipping(
shipperID INT NOT NULL AUTO_INCREMENT,
shipperName VARCHAR(50) NOT NULL,
PRIMARY KEY (shipperID)
);

drop table if exists shippercentage;
CREATE TABLE shippercentage(
shippercentageID INT NOT NULL AUTO_INCREMENT,
shippingPercentage DOUBLE NOT NULL,
lastNegotiationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
shipOrderNumber INT NOT NULL,
PRIMARY KEY (shippercentageID),
FOREIGN KEY (shipOrderNumber) REFERENCES orders(orderNumber)
);

ALTER TABLE orders ADD orderPromoID INT DEFAULT NULL;
ALTER TABLE orders ADD FOREIGN KEY(orderPromoID) REFERENCES promotions(promoID);

ALTER TABLE orders ADD orderShipperID INT DEFAULT NULL;
ALTER TABLE orders ADD FOREIGN KEY(orderShipperID) REFERENCES shipping(shipperID);

ALTER TABLE orders ADD orderTaxID INT DEFAULT NULL;
ALTER TABLE orders ADD FOREIGN KEY(orderTaxID) REFERENCES taxes(taxID);

-- ALTER TABLE payments ADD invoiceTaxID INT DEFAULT NULL;
-- ALTER TABLE payments ADD FOREIGN KEY(invoiceTaxID) REFERENCES taxes(taxID);

-- -------------------------------------------------------------------------------------
-- DESCRIBE orders;
-- DESCRIBE payments;
-- DESCRIBE taxes;
-- DESCRIBE officestax;
-- DESCRIBE promotions;
-- DESCRIBE shipping;
-- DESCRIBE shippercentage;