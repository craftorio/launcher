#!/bin/sh
# SEE https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javapackager.html

JAVA_HOME=`/usr/libexec/java_home -v 1.8`
APP_DIR_NAME=MinecraftLauncher.app

[[ -d build/libs/bundles ]] && rm -rf build/libs/bundles

#-deploy -Bruntime=/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home \
javapackager \
  -deploy -Bruntime=${JAVA_HOME} \
  -native image \
  -srcdir build/libs \
  -srcfiles launcher.jar \
  -outdir build/libs \
  -outfile ${APP_DIR_NAME} \
  -name "MinecraftLauncher" \
  -title "MinecraftLauncher" \
  -appclass net.launcher.run.Launcher \
  -nosign \
  -v

cp -r "${JAVA_HOME}/bin" "build/libs/bundles/${APP_DIR_NAME}/Contents/PlugIns/Java.runtime/Contents/Home/jre/"

create-dmg --text-size 12 --disk-image-size 512 --volname Craftorio --background "package/macosx/background.png" --window-pos 200 120 --window-size 700 420 --icon-size 70 --icon "MinecraftLauncher.app" 204 233 --hide-extension "MinecraftLauncher.app" --app-drop-link 498 233 build/libs/bundles/Craftorio.dmg build/libs/bundles/MinecraftLauncher.app
