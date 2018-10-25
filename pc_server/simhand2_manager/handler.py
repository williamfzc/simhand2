import tornado.web
from . import device_manager
from .global_namespace import *


class BaseHandler(tornado.web.RequestHandler):
    def end_with_json(self, code, data=None, message=None):
        request_url = self.request.uri
        result_dict = {
            'code': code,
            'data': data or {},
            'message': message or {},
        }
        logger.info(TAG_REQUEST_END, code=code, data=data, message=message, request_url=request_url)
        self.finish(result_dict)


class MainHandler(BaseHandler):
    def get(self):
        self.end_with_json(FLAG_HTTP_OK)


class DeviceHandler(BaseHandler):
    def get(self):
        # get usable devices
        device_dict = device_manager.get_available_device(auth=True, to_string=True)
        self.end_with_json(FLAG_HTTP_OK, data=device_dict)

    def post(self):
        # confirm device connection
        target_device_id = self.get_argument('deviceID', '')
        device_manager.auth_device(target_device_id)
        self.end_with_json(FLAG_HTTP_OK)


__all__ = [
    'MainHandler',
    'DeviceHandler',
]
