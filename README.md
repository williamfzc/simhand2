# simhand2

> UI OPERATOR SERVER ON ANDROID

## 原理

- 基于uiautomator
- 通过提供的restful API，直接通过http请求操作设备

View [RoadMap](roadmap.md) for detail.

## 安装

### 使用安装脚本

- 连接设备
- 运行`install.py`
- 确保python3已安装，并安装相关依赖包
- 脚本会将apk都安装好并启动服务

### 手动

将项目clone到本地，用android studio打开后，运行`StubTestCase`。

或者手动从release上下载`app-debug.apk`与`app-debug-androidTest.apk`并安装到手机上：

```
adb install -t -r app-debug.apk
adb install -t -r app-debug-androidTest.apk
```

安装后需要自行启动：

```
// Run Case
$ adb shell am instrument -w -r   -e debug false -e class 'com.github.williamfzc.simhand2.StubTestCase' com.github.williamfzc.simhand2.test/com.github.williamfzc.simhand2.SHInstrumentationTestRunner
```

## 使用示例

当需要UI交互请求时，只需要对其发送http请求即可。

例如，你希望点击当前页面上text为Camera的元素:

```
// ip 地址根据实际修改
http://192.168.0.10:8080/api/action/click?widgetName=camera
```

当然，它也能够被其他应用从内部调用：

```
http://127.0.0.1:8080/api/action/click?widgetName=camera
```

## 相关项目

[simhand2 manager](https://github.com/williamfzc/simhand2_manager)

## API 文档

### 截图 (get image/png)

```bash
http://127.0.0.1:8080/api/screenshot
```

### 操作

#### 点击

click element which text == 'camera'

```bash
http://127.0.0.1:8080/api/action/click?widgetName=camera
```

#### 存在

check if element which text == 'camera' existed

```bash
http://127.0.0.1:8080/api/action/exist?widgetName=camera
```

#### 系统

```bash
http://127.0.0.1:8080/api/action/system?actionName=turnOnAirplaneMode
```

...
