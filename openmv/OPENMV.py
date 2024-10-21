import sensor, image, time, network, usocket, sys
from pyb import Pin

SSID ='YiCeng'
KEY  ='xmw66668888'
PORT = 8080
HOST = ''
PORT2 = 52014
sensor.reset()
sensor.set_framesize(sensor.QQVGA)
sensor.set_pixformat(sensor.RGB565)
wlan = network.WLAN(network.STA_IF)
wlan.active(True)
wlan.connect(SSID, KEY)
while not wlan.isconnected():
    print('Trying to connect to "{:s}"...'.format(SSID))
    time.sleep_ms(1000)
print("WiFi Connected ", wlan.ifconfig())
def image(s):
    print ('Waiting for connections..')
    client, addr = s.accept()
    client.settimeout(2.0)
    print ('Connected to ' + addr[0] + ':' + str(addr[1]))
    data = client.recv(1024)
    client.send("HTTP/1.1 200 OK\r\n" \
                "Server: OpenMV\r\n" \
                "Content-Type: image/jpeg\r\n\r\n")
    clock = time.clock()
    frame = sensor.snapshot()
    cframe = frame.to_jpeg(quality=35, copy=True)
    client.send(cframe)
    client.close()
while (True):
    s = usocket.socket(usocket.AF_INET, usocket.SOCK_STREAM)
    try:
        s.bind([HOST, PORT])
        s.listen(5)
        s.settimeout(3)
        image(s)
    except OSError as e:
        s.close()
        print("socket error: ", e)