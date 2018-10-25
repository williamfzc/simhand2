from whenconnect import when_connect, when_disconnect, start_detect
from .device_manager import add_device, remove_device


def start_device_detect():
    when_connect(device='all', do=add_device)
    when_disconnect(device='all', do=remove_device)
    start_detect(with_log=False)
