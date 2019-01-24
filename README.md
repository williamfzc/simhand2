# simhand2

> Ui operator on Android, via HTTP request.

## 原理

- 基于uiautomator
- 通过提供易于使用的API，**直接**通过http请求操作设备，不再通过pc
- 实现类似[appium-uiautomator2-server](https://github.com/appium/appium-uiautomator2-server)

## 安装

### 使用安装脚本

- 连接设备
- 运行`install.py`
- 确保python3已安装，并安装相关依赖包
- 脚本会将apk都安装好

### 手动

将项目clone到本地，用android studio打开后，运行`StubTestCase`。

或者手动从release上下载`app-debug.apk`与`app-debug-androidTest.apk`并安装到手机上：

```
adb install -t -r app-debug.apk
adb install -t -r app-debug-androidTest.apk
```

## 启动

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
http://192.168.0.10:8080/api/action/click?widgetName=camera&selector=text
```

当然，它也能够被其他应用从内部调用：

```
http://127.0.0.1:8080/api/action/click?widgetName=camera&selector=text
```

> 推荐使用 postman 之类的工具进行调试。

## API 文档

目前着重于支持最常用的几个API。

### 定位方式

目前支持通过id、text、desc定位元素。

```
http://127.0.0.1:8080/api/action/click?widgetName=com.github.williamfzc.demo:id/button2&selector=id
```

### 动作类型

通过id定位元素，并点击：

```
http://127.0.0.1:8080/api/action/click?widgetName=com.github.williamfzc.demo:id/button2&selector=id
```

也可以只是判断是否存在：

```
http://127.0.0.1:8080/api/action/exist?widgetName=com.github.williamfzc.demo:id/button2&selector=id
```

当然不只是能通过id，也可以通过text：

```
http://127.0.0.1:8080/api/action/click?widgetName=wechat&selector=text
```

系统操作（其他系统操作可以参见[代码](./app/src/androidTest/java/com/github/williamfzc/simhand2/ActionHandler/SystemActionHandler.java)：

```
http://127.0.0.1:8080/api/action/system?actionName=pressBack
```
