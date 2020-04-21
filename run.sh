#!/bin/bash
first_device=`adb devices | awk  'NR==2' | awk  '{print $1}'`
packageName="com.malin.pagersnaphelper"
echo "apk will install to "${first_device}
adb -s ${first_device} uninstall ${packageName} >/dev/null
echo "uninstall ${packageName} from "${first_device}
echo "install ${packageName} to "${first_device}
./gradlew -q app:installDebug -x lint --parallel --offline --continue &&
adb -s ${first_device} shell am start ${packageName}/.MainActivity
