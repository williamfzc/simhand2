from pyatool import PYAToolkit
from whenconnect import when_connect, when_disconnect
import subprocess
import sys


VERSION = 'v0.1.6'
BASE_URL = r'https://github.com/williamfzc/simhand2/releases/download/{}/{}'
TEST_APK = r'app-debug-androidTest.apk'
MAIN_APK = r'app-debug.apk'

TEST_DL_URL = BASE_URL.format(VERSION, TEST_APK)
MAIN_DL_URL = BASE_URL.format(VERSION, MAIN_APK)

process_dict = {}

def install_sh(device_id):
    print('install simhand2 in {}'.format(device_id))
    pya = PYAToolkit(device_id)
    pya.install_from(url=TEST_DL_URL)
    pya.install_from(url=MAIN_DL_URL)
    print('install simhand2 ok in {}'.format(device_id))
    new_process = subprocess.Popen("adb shell am instrument -w -r   -e debug false -e class 'com.github.williamfzc.simhand2.StubTestCase' com.github.williamfzc.simhand2.test/com.github.williamfzc.simhand2.SHInstrumentationTestRunner")
    process_dict[device_id] = new_process

    new_process.kill()
    sys.exit()


def stop_simhand(device_id):
    p = process_dict[device_id]
    p.kill()
    del process_dict[device_id]


when_connect(device='all', do=install_sh)
when_disconnect(device='all', do=stop_simhand)
