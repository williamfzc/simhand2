# UIOServer

> UI OPERATOR SERVER ON ANDROID

## Usage

would better run with android studio, or follow steps below:

```
// Push Main Apk
$ adb push F:\UIOServer\app\build\outputs\apk\debug\app-debug.apk /data/local/tmp/com.github.williamfzc.uioserver
$ adb shell pm install -t -r "/data/local/tmp/com.github.williamfzc.uioserver"

// Push TestCase Apk
$ adb push F:\UIOServer\app\build\outputs\apk\androidTest\debug\app-debug-androidTest.apk /data/local/tmp/com.github.williamfzc.uioserver.test
$ adb shell pm install -t -r "/data/local/tmp/com.github.williamfzc.uioserver.test"

// Run Case
$ adb shell am instrument -w -r   -e debug false -e class 'com.github.williamfzc.uioserver.StubTestCase' com.github.williamfzc.uioserver.test/android.support.test.runner.AndroidJUnitRunner
```
