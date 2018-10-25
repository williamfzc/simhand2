from .handler import *


ROUTER_LIST = [
    (r"/", MainHandler),
    (r"/device", DeviceHandler),
]
