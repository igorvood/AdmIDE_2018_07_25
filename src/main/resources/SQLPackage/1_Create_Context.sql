CREATE OR REPLACE CONTEXT vood_USER USING set_voodcontext_value;

CREATE OR REPLACE PROCEDURE set_voodcontext_value (/*con IN VARCHAR2
,*/ par IN VARCHAR2
, val IN VARCHAR2
)
AS
BEGIN
DBMS_SESSION.SET_CONTEXT ( 'vood_USER', par, val );
END;
/
GRANT EXECUTE ON set_voodcontext_value TO vood;
/
