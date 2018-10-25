import tornado.web
from .global_namespace import *


class BaseHandler(tornado.web.RequestHandler):
    def end_with_json(self, code, data=None, message=None):
        request_url = self.request.uri
        result_dict = {
            'code': code,
            'data': data or {},
            'message': message or {},
        }
        logger.info('REQUEST END', code=code, data=data, message=message, request_url=request_url)
        self.finish(result_dict)


class MainHandler(BaseHandler):
    def get(self):
        self.end_with_json(FLAG_OK)


class DeviceHandler(BaseHandler):
    def get(self):
        self.end_with_json(FLAG_OK)


__all__ = [
    'MainHandler',
    'DeviceHandler',
]
