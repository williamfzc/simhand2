import tornado.ioloop
import tornado.web

from simhand2_manager.router import ROUTER_LIST
from simhand2_manager.detector import start_device_detect
from simhand2_manager.global_namespace import *


def make_app():
    return tornado.web.Application(ROUTER_LIST)


if __name__ == "__main__":
    app = make_app()
    app.listen(PC_SERVER_PORT)
    start_device_detect()
    logger.info('SERVER START', port=PC_SERVER_PORT)
    tornado.ioloop.IOLoop.current().start()
