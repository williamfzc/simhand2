from .global_namespace import *


class SHDevice(object):
    def __init__(self, device_id):
        self.device_id = device_id

    def startup(self):
        logger.info(TAG_DEVICE_CHANGE, msg=self.device_id + ' init finished')

    def terminate(self):
        logger.info(TAG_DEVICE_CHANGE, msg=self.device_id + ' terminated')


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
