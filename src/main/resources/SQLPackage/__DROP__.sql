BEGIN
  FOR del IN
  (SELECT all_t.table_name AS tn
  FROM all_all_tables all_t
  WHERE all_t.owner = 'VOOD'
  ORDER BY
    CASE
      WHEN all_t.table_name = 'V_BD_INDEXED_COLOMNS'
      THEN 1
      WHEN all_t.table_name = 'V_BD_INDEX'
      THEN 2
      WHEN all_t.table_name = upper('v_Bd_Colomns')
      THEN 3
      WHEN all_t.table_name = upper('v_Bd_Table')
      THEN 4
      WHEN all_t.table_name = upper('v_Bd_Object')
      THEN 5
      WHEN all_t.table_name = upper('v_Bd_Object_Type')
      THEN 6
      ELSE 100
    END
  )
  LOOP
    dbms_output.put_line(del.tn);
    execute immediate 'drop table '||del.tn||'';
  END LOOP;
END;

-------------------------------------------------------------------------------------
drop VIEW VW_CLASS_FOR_TREE;
drop VIEW VW_COLOMN_FOR_TABLE;
