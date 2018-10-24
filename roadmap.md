# simhand2

## 设计

设备管理在自动化测试里一直是很关键的一环。它作为最基础的一层，同时需要稳定且容易扩展。之前陆陆续续做过一些demo项目，但最后都没有落地。

### [RMS4A](https://github.com/williamfzc/RMS4A)

这是最早的一版。大致的设计：

- 脱离PC：不再强制需要通过PC进行设备管控，减少对PC的依赖；
- 服务化：所有设备上都是独立的服务器，通过restfulAPI进行通信。每台设备都作为一个服务提供者存在而不是单纯的被管者

整套系统使用golang编写（golang交叉编译的包能够在android系统上运行，可以据此搭建服务器）。当时遇到最大的问题：

- android系统上对于非应用进程的保活
- 对于android系统的操作能力有限，只能执行linux命令

### [simhand](https://github.com/williamfzc/simhand)

在该项目中，手机不再承载过多功能，恢复到了传统的电脑支配手机的模式。在PC上开启restful服务，再将外部请求作用到手机上。这个版本解决了前者提到的两个问题：

- 非应用进程的保活
    - 手机上不再需要apk及相关程序的运行，所有的操作都由PC支配，保活也无从谈起。

- 操作能力有限
    - 直接使用了一个[uiautomator封装库](https://github.com/openatx/uiautomator2)，理论上支持所有uiautomator能操作的场景。

但，这种做法同样存在问题：

- uiautomator封装库不够稳定。个人感觉为了提供python wrapper还是牺牲了一些稳定性，在长时间待机与保活上有些瑕疵
- 设备内部应用如果希望操作本地界面需要通过PC转发请求，效率较低

### simhand2

updating ...
