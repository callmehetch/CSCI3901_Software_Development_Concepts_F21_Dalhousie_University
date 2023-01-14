-- 1)
-- -----------------------------------------------------------------------------------
SELECT c.customerNumber, c.customerName FROM customers c 
INNER JOIN employees e ON c.salesRepEmployeeNumber = e.employeeNumber
INNER JOIN offices o ON o.officeCode = e.officeCode AND c.city!=o.city;

-- 2)
-- --------------------------------------------------------------------------------
SELECT od.orderNumber FROM orderdetails od INNER JOIN products p ON p.productCode=od.productCode AND od.priceEach<p.MSRP GROUP BY od.orderNumber;


-- 3)
-- ----------------------------------------------------------------------------------
SELECT p.productLine FROM products p
INNER JOIN (SELECT od.productCode, sum(od.priceEach*od.quantityOrdered) saleValue, sum(od.quantityOrdered) totalValue FROM orderdetails od 
INNER JOIN orders o ON o.orderNumber = od.orderNumber WHERE o.orderDate BETWEEN '2003-01-01' AND '2003-12-31' GROUP BY od.productCode) t 
on p.productCode = t.productCode ORDER BY (saleValue-(t.totalValue*p.buyPrice))/(t.totalValue*p.buyPrice) DESC LIMIT 1;  

-- 4)
-- -----------------------------------------------------------------------------------
SELECT e.employeeNumber, e.firstName, e.lastName FROM customers c 
INNER JOIN employees e ON c.salesRepEmployeeNumber = e.employeeNumber
INNER JOIN orders o ON o.customerNumber = c.customerNumber
INNER JOIN orderdetails od ON o.orderNumber = od.orderNumber AND YEAR(o.orderDate) = '2004' 
GROUP BY employeeNumber 
ORDER BY sum(od.quantityOrdered*od.priceEach) DESC LIMIT 5;



-- 5)
-- ---------------------------------------------------------------------------------------------------------------------------------
SELECT e.employeeNumber, e.firstName, e.lastName FROM employees e 
INNER JOIN (SELECT c.salesRepEmployeeNumber salesRep, sum(od.quantityOrdered*od.priceEach) sales2003 from customers c 
INNER JOIN orders o ON c.customerNumber=o.customerNumber
INNER JOIN orderdetails od ON o.orderNumber=od.orderNumber 
AND YEAR(o.orderDate) = '2003' GROUP BY c.salesRepEmployeeNumber) s1 ON s1.salesRep = e.employeeNumber
INNER JOIN (SELECT c.salesRepEmployeeNumber salesRep, SUM(od.quantityOrdered*od.priceEach) sales2004 FROM customers c
INNER JOIN orders o ON c.customerNumber=o.customerNumber
INNER JOIN orderdetails od ON o.orderNumber=od.orderNumber 
AND YEAR(o.orderDate) = '2004' GROUP BY c.salesRepEmployeeNumber) s2 ON s1.salesRep = s2.salesRep 
AND s1.sales2003<s2.sales2004;