::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::                                                                           :
:: PDFUnit - Automated PDF Tests                                             :
::                                                                           :
:: Copyright (C) 2016 PDFUnit.com                                            :
::                                                                           :
:: This file is part of the commercial library PDFUnit.                      :
::                                                                           :
:: Legal information__: http://pdfunit.com/en/licenseinfo.html               :
:: Manual_____________: http://pdfunit.com/en/download/index.html            :
:: Contact for license: license[at]pdfunit.com                               :
::                                                                           :
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
:: Verify the installation of PDFUnit.
::

@echo off
setlocal

set CURRENTDIR=%~dp0
set PDFUNIT_HOME=%CURRENTDIR%

::
:: Change the installation directories depending on your situation:
::
set ASPECTJ_HOME=%PDFUNIT_HOME%/lib/aspectj-1.8.7
set BOUNCYCASTLE_HOME=%PDFUNIT_HOME%/lib/bouncycastle-jdk15on-153
set COMMONSCOLLECTIONS_HOME=%PDFUNIT_HOME%/lib/commons-collections4-4.1
set COMMONSLOGGING_HOME=%PDFUNIT_HOME%/lib/commons-logging-1.2
set PDFBOX_HOME=%PDFUNIT_HOME%/lib/pdfbox-2.0.0
set POI_HOME=%PDFUNIT_HOME%/lib/poi-3.14
set TESS4J_HOME=%PDFUNIT_HOME%/lib/tess4j-3.1.0
set VIP_HOME=%PDFUNIT_HOME%/lib/vip-1.0.0
set ZXING_HOME=%PDFUNIT_HOME%/lib/zxing-core-3.2.1

set CLASSPATH=
set CLASSPATH=%ASPECTJ_HOME%/*;%CLASSPATH%
set CLASSPATH=%BOUNCYCASTLE_HOME%/*;%CLASSPATH%
set CLASSPATH=%COMMONSCOLLECTIONS_HOME%/*;%CLASSPATH%
set CLASSPATH=%COMMONSLOGGING_HOME%/*;%CLASSPATH%
set CLASSPATH=%POI_HOME%/*;%CLASSPATH%
set CLASSPATH=%POI_HOME%/lib/*;%CLASSPATH%
set CLASSPATH=%POI_HOME%/ooxml-lib/*;%CLASSPATH%
set CLASSPATH=%PDFBOX_HOME%/*;%CLASSPATH%
set CLASSPATH=%TESS4J_HOME%/*;%CLASSPATH%
set CLASSPATH=%TESS4J_HOME%/lib/*;%CLASSPATH%
set CLASSPATH=%VIP_HOME%/*;%CLASSPATH%
set CLASSPATH=%ZXING_HOME%/*;%CLASSPATH%

:: The folder of PDFUnit-Java:
set CLASSPATH=%PDFUNIT_HOME%;%CLASSPATH%
:: The JAR files of PDFUnit-Java:
set CLASSPATH=%PDFUNIT_HOME%/*;%CLASSPATH%


:: Run installation verification:
java org.verifyinstallation.VIPMain --in verifyInstallation.vip  --out verifyInstallation_result.html

endlocal