# global namespace
import structlog

logger = structlog.get_logger()

# http flag
FLAG_OK = 1000
FLAG_ERROR = 1001

# logger tag
TAG_DEVICE_CHANGE = 'DEVICE CHANGE'
