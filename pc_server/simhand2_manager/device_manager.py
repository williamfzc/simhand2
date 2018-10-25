from .global_namespace import *
import subprocess
import json


SIMHAND_INIT_CMD = (
    r"adb -s {device_id} shell am instrument -w -r   "
    r"-e port {android_port} "
    r"-e pc {local_address} "
    r"-e pcPort {pc_port} "
    r"-e deviceID {device_id} "
    r"-e debug false "
    r"-e class 'com.github.williamfzc.simhand2.StubTestCase' "
    r"com.github.williamfzc.simhand2.test/com.github.williamfzc.simhand2.SHInstrumentationTestRunner"
).format(
    device_id=r'{device_id}',
    android_port=ANDROID_DEVICE_PORT,
    local_address=LOCAL_ADDRESS,
    pc_port=PC_SERVER_PORT,
)


class SHDevice(object):
    def __init__(self, device_id):
        self.device_id = device_id
        self.is_auth = False
        self._sh_process = None

    def __str__(self):
        return '<SHDevice {}>'.format(self.device_id)

    __repr__ = __str__

    def to_json(self):
        return json.dumps({
            'device_id': self.device_id,
            'is_auth': self.is_auth,
        })

    def startup(self):
        real_command = SIMHAND_INIT_CMD.format(device_id=self.device_id)
        logger.info('device simhand startup', cmd=real_command)
        self._sh_process = subprocess.Popen(real_command, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
        logger.info(TAG_DEVICE_CHANGE, msg=self.device_id + ' init finished')

    def terminate(self):
        self._sh_process.terminate()
        self._sh_process.kill()
        self._sh_process = None
        logger.info(TAG_DEVICE_CHANGE, msg=self.device_id + ' terminated')

    def auth(self):
        self.is_auth = True
        logger.info(TAG_DEVICE_CHANGE, msg=self.device_id + ' auth passed')


_device_dict = dict()


def add_device(device_id):
    if device_id not in _device_dict:
        new_device = SHDevice(device_id)
        new_device.startup()
        _device_dict[device_id] = new_device
        return True
    logger.warn(TAG_DEVICE_CHANGE, msg='already existed', id=device_id)
    return False


def remove_device(device_id):
    if device_id not in _device_dict:
        logger.warn(TAG_DEVICE_CHANGE, msg='not existed', id=device_id)
        return False
    sh_device = _device_dict[device_id]
    sh_device.terminate()
    del _device_dict[device_id]
    return True


def auth_device(device_id):
    if device_id not in _device_dict:
        logger.warn(TAG_DEVICE_CHANGE, msg='not existed', id=device_id)
        return False
    sh_device = _device_dict[device_id]
    sh_device.auth()
    return True


def get_available_device(auth=None, to_string=None):
    result_dict = dict()
    if auth:
        result_dict = {_: v for _, v in _device_dict.items() if v.is_auth}
    if to_string:
        result_dict = {_: v.to_json() for _, v in _device_dict.items()}
    return result_dict
