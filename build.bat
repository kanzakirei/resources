chcp 65001
set FILE_NAME=CustomModelData

rem mkdir %FILE_NAME%

rem xcopy /E /Y WeaponMechanicsResourcePack %FILE_NAME%
rem xcopy /E /Y KRSRV_CustomModelData %FILE_NAME%

rem powershell compress-archive -Path '%FILE_NAME%/*' -DestinationPath '%FILE_NAME%.zip' -Force

rem call commit.bat