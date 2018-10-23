import requests
import os
import tempfile
import structlog
import subprocess

# TODO check if is installed ( need download or not )
# TODO github release download failed sometimes

GITHUB_BASE_DOWNLOAD_URL = r'https://github.com/williamfzc/simhand2/releases/download'

# use github version tag, eg: v0.1.0
TARGET_VERSION = r'v0.1.0'

ANDROID_TEMP_DIR_PATH = '/data/local/tmp'
MAIN_APP_NAME = r'app-debug.apk'
TEST_APP_NAME = r'app-debug-androidTest.apk'

MAIN_APP_DL_URL = '{}/{}/{}'.format(GITHUB_BASE_DOWNLOAD_URL, TARGET_VERSION, MAIN_APP_NAME)
TEST_APP_DL_URL = '{}/{}/{}'.format(GITHUB_BASE_DOWNLOAD_URL, TARGET_VERSION, TEST_APP_NAME)
MAIN_APP_TARGET_PATH = '{}/{}'.format(ANDROID_TEMP_DIR_PATH, MAIN_APP_NAME)
TEST_APP_TARGET_PATH = '{}/{}'.format(ANDROID_TEMP_DIR_PATH, TEST_APP_NAME)

TEMP_FILE_PATH = os.path.join(os.path.dirname(__file__), 'tempfile.apk')

logger = structlog.get_logger()


def download_resource(target_url, file_obj):
    logger.info('START DOWNLOAD', url=target_url, to=file_obj.name)
    r = requests.get(target_url)
    file_obj.write(r.content)
    logger.info('DOWNLOAD OK', url=target_url)


def push_device(file_path, target_file_path):
    base_cmd = ['adb', 'push', file_path, target_file_path]
    logger.info('EXEC CMD', cmd=base_cmd)
    return_code = subprocess.call(base_cmd)
    if return_code:
        return False
    return True


def install(target_apk_path):
    base_cmd = ['adb', 'shell', 'pm', 'install', '-t', '-r', target_apk_path]
    logger.info('EXEC CMD', cmd=base_cmd)
    return_code = subprocess.call(base_cmd)
    if return_code:
        return False
    return True


def start_server():
    base_cmd = ['adb', 'shell', 'am', 'instrument', '-w', '-r', '-e', 'debug', 'false', '-e', 'class',
                'com.github.williamfzc.simhand2.StubTestCase',
                'com.github.williamfzc.simhand2.test/android.support.test.runner.AndroidJUnitRunner']
    process = subprocess.Popen(base_cmd)
    return process


def dl_and_push_and_install(dl_url, target_path):
    with open(TEMP_FILE_PATH, 'wb+') as temp:
        download_resource(dl_url, temp)
    push_device(temp.name, target_path)
    install(target_path)


if __name__ == '__main__':
    dl_and_push_and_install(MAIN_APP_DL_URL, MAIN_APP_TARGET_PATH)
    dl_and_push_and_install(TEST_APP_DL_URL, TEST_APP_TARGET_PATH)

    # should never shut down
    server_process = start_server()
    server_process.wait(timeout=3600)
