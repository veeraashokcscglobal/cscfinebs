DECLARE
CURSOR c_objs
IS
SELECT dba_obj.owner
       ,dba_obj.object_type
       ,dba_obj.object_name
       ,dba_obj.object_name||DECODE(dba_obj.object_type, 'PACKAGE', '.pks', 'PACKAGE BODY', '.pkb') file_name
  FROM dba_objects dba_obj
 WHERE (dba_obj.object_name LIKE 'XSCP%'
        OR dba_obj.object_name LIKE 'XCSC%'
        OR dba_obj.object_name LIKE 'XXCSC%')
   AND dba_obj.owner IN ('APPS', 'XCSC')
   AND dba_obj.object_type IN ('PACKAGE', 'PACKAGE BODY');

lc_clob CLOB;
ln_cnt  NUMBER := 0;

BEGIN

 FOR r_objs IN c_objs
 LOOP
    -- Collating Source Code into CLOB Type
    FOR r_code IN (SELECT text 
                     FROM dba_source 
                    WHERE type = r_objs.object_type
                      AND name = r_objs.object_name)
    LOOP
       lc_clob := lc_clob||r_code.text;
       ln_cnt := ln_cnt + 1;
       IF ln_cnt = 1
       THEN
          lc_clob := REPLACE(UPPER(lc_clob), 'PACKAGE', 'CREATE OR REPLACE');
       END IF;
    END LOOP;
    BEGIN
    INSERT INTO xcsc_objects_table (object_owner
                                    ,object_type
                                    ,object_name
                                    ,source_file_name
                                    ,source_code    
                                    ,process_status)
                            VALUES (r_objs.owner
                                    ,r_objs.object_type
                                    ,r_objs.object_name
                                    ,r_objs.file_name
                                    ,lc_clob
                                    ,'L');
    EXCEPTION
    WHEN OTHERS
    THEN
       DBMS_OUTPUT.PUT_LINE('Unexpected error while inserting into XCSC_OBJECTS_TABLE - '||SQLERRM);
    END;
    
    -- Resetting Variable Values
    lc_clob := NULL;
    ln_cnt  := 0;
    
 END LOOP;
 COMMIT;
EXCEPTION
WHEN OTHERS
THEN
   DBMS_OUTPUT.PUT_LINE('Unexpected error in the main call - '||SQLERRM);
END;