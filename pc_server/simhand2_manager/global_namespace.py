# global namespace
import structlog
import socket

logger = structlog.get_logger()

# http flag
FLAG_HTTP_OK = 1000
FLAG_HTTP_ERROR = 1001

# logger tag
TAG_DEVICE_CHANGE = 'DEVICE CHANGE'
TAG_REQUEST_END = 'REQUEST END'


def get_local_ip():
    host_name = socket.gethostname()
    return socket.gethostbyname(host_name)


# config
PC_SERVER_PORT = 8888
ANDROID_DEVICE_PORT = 8080
LOCAL_ADDRESS = get_local_ip()
