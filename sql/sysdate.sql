SELECT SYSDATE FROM DUAL;

SELECT * FROM ra_customer_trx_all WHERE ROWNUM < 11;

SELECT * FROM ap_suppliers;

SELECT * FROM ap_supplier_sites_all;

SELECT * FROM ra_customer_trx_lines_all;

-- Changes done as part of Q1 2024 Release
SELECT SYSDATE FROM DUAL;

-- Changes done as part of Q1 2024 Release for User Story 124556
SELECT * FROM xcsc_gari_staging_table WHERE ROWNUM < 100;

-- Changes done for Q12024 Release on AR
SELECT * FROM ar_payment_schedules;
/
