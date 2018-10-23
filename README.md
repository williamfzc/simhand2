# simhand2

> UI OPERATOR SERVER ON ANDROID

## Install

### Use install script

- Connect your device and run `install.py`. 
- Make sure python3 and adb work well. some requirement may needed.
- And, your server is on :)

### Manual 

Open this project with android studio, and run `StubTestCase`.

Or follow steps below:

```
// Push Main Apk
$ adb push F:\simhand2\app\build\outputs\apk\debug\app-debug.apk /data/local/tmp/com.github.williamfzc.simhand2
$ adb shell pm install -t -r "/data/local/tmp/com.github.williamfzc.simhand2"

// Push TestCase Apk
$ adb push F:\simhand2\app\build\outputs\apk\androidTest\debug\app-debug-androidTest.apk /data/local/tmp/com.github.williamfzc.simhand2.test
$ adb shell pm install -t -r "/data/local/tmp/com.github.williamfzc.simhand2.test"

// Run Case
$ adb shell am instrument -w -r   -e debug false -e class 'com.github.williamfzc.simhand2.StubTestCase' com.github.williamfzc.simhand2.test/android.support.test.runner.AndroidJUnitRunner
```

## Usage

When need UI communication, Just send a http request. 

Click widget which named 'camera':

```
http://192.168.0.10:8080/api/action/click?widgetName=camera
```

Also, request from android inside would work well:

```
http://127.0.0.1:8080/api/action/click?widgetName=camera
```

## API Document

Still building. Offer what we actually need only.

### Screen Shot

screenshot (get image/png)

```bash
http://127.0.0.1:8080/api/screenshot
```

### Action

click

click element which text == 'camera'

```bash
http://127.0.0.1:8080/api/action/click?widgetName=camera
```

exist

check if element which text == 'camera' existed

```bash
http://127.0.0.1:8080/api/action/exist?widgetName=camera
```

### System

```bash
http://127.0.0.1:8080/api/action/system?actionName=turnOnAirplaneMode
```
