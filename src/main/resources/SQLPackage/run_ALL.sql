--PROMPT specify a password for vood as parameter 2;
--DEFINE voodPassword = &2

-- variable voodPassword varchar2(255):='vood';
set echo on
spool G:\Work\Java\Plugins\ADMPluginIDE\Sql\SQLPackage\log.log


--connect "vood"/"&&voodPassword" as NORMAL
connect system/vood@vood:1521/vood as sysdba
--@D:\work\Java\JavaORCL\Developer\dev\src\SQLPacage\test.sql
@G:\Work\Java\Plugins\ADMPluginIDE\Sql\SQLPackage\2_Create_TableSpace.sql
-- сначала надо пользователя создать, потому как потом ему выдаются права на контекст
@G:\Work\Java\Plugins\ADMPluginIDE\Sql\SQLPackage\3_Create_UserVood.sql
@G:\Work\Java\Plugins\ADMPluginIDE\Sql\SQLPackage\1_Create_Context.sql



commit;
exit;


