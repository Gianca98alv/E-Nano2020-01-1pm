import SimpleHTTPServer
import SocketServer
import sys

PORT = 9000

Handler = SimpleHTTPServer.SimpleHTTPRequestHandler

httpd = SocketServer.TCPServer(("", PORT), Handler)

print("serving at port", PORT)
httpd.serve_forever() # maneja request hasta recibir un request de shutdown()