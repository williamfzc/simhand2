import tornado.ioloop
import tornado.web

from simhand2_manager.router import ROUTER_LIST
from simhand2_manager.detector import start_device_detect


def make_app():
    return tornado.web.Application(ROUTER_LIST)


if __name__ == "__main__":
    app = make_app()
    app.listen(8888)
    start_device_detect()
    tornado.ioloop.IOLoop.current().start()
