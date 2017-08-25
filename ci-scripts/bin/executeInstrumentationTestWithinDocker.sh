#!/bin/bash

# Create new device
if [ "$1" == "arm" ]
then
        echo "no" | /usr/local/android-sdk/tools/android create avd -f -n test -t android-23 --abi google_apis/armeabi-v7a
        echo "no" | /usr/local/android-sdk/tools/emulator64-arm -avd test -noaudio -no-window -gpu off -verbose -qemu -usbdevice tablet -vnc :0 &> /var/log/emulator.log &
else
        # Create the kvm node (required --privileged)
        if [ ! -e /dev/kvm ]; then
                mknod /dev/kvm c 10 $(grep '\<kvm\>' /proc/misc | cut -f 1 -d' ')
        fi
        echo "no" | /usr/local/android-sdk/tools/android create avd -f -n test -t android-23 --abi google_apis/x86
        echo "no" | /usr/local/android-sdk/tools/emulator64-x86 -avd test -noaudio -no-window -gpu off -verbose -qemu -enable-kvm -usbdevice tablet -vnc :0 &> /var/log/emulator.log &
fi

# Wait for device boot
adb wait-for-device
A=$(adb shell getprop sys.boot_completed | tr -d '\r')
while [ "$A" != "1" ]; do
        sleep 2
        A=$(adb shell getprop sys.boot_completed | tr -d '\r')
done
adb shell input keyevent 82

# Execute test
./gradlew build jacocoTestReport
./gradlew check
./gradlew grantDebugPermissions
./gradlew connectedCheck
