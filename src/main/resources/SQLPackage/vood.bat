rem mkdir C:\oracle\db_files\vood
rem mkdir C:\oracle\product\11.1.0\db_1\database
rem mkdir C:\oracle\product\admin\vood\adump
rem mkdir C:\oracle\product\admin\vood\dpdump
rem mkdir C:\oracle\product\admin\vood\pfile
rem mkdir C:\oracle\product\cfgtoollogs\dbca\vood
rem mkdir C:\oracle\product\flash_recovery_area
set ORA_SID=vood
set ORA_HOME=C:\app\tora\product\12.1.0\dbhome_1
set SQL_PLUS=%ORA_HOME%\BIN\sqlplus.exe
set SQL_SCRIPT_PATH=G:\Work\Java\Plugins\ADMPluginIDE\Sql\SQLPackage
set SQL_SCRIPT_NAME=run_ALL.sql


echo SID DB instatce %ORA_SID%
echo Oracle home path %ORA_HOME%
echo Path sqlplus application %SQL_PLUS%
echo Path to SQL script %SQL_SCRIPT_PATH%
echo Path to SQL script %SQL_SCRIPT_NAME%

rem C:\oracle\product\11.1.0\db_1\bin\oradim.exe -new -sid VOOD -startmode manual -spfile
rem C:\oracle\product\11.1.0\db_1\bin\oradim.exe -edit -sid VOOD -startmode auto -srvcstart system
rem C:\oracle\product\11.1.0\db_1\bin\sqlplus /nolog @C:\oracle\db_files\vood\scripts\vood.sql
%SQL_PLUS% /nolog @G:\Work\Java\Plugins\ADMPluginIDE\Sql\SQLPackage\run_ALL.sql

