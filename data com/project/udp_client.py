from socket import *

serverName = 'napakkerd.thddns.net'
serverPort = 6610

clientSocket = socket(AF_INET, SOCK_DGRAM)

message = input("Input lowercase sentence: ")

clientSocket.sendto(message.encode(), (serverName, serverPort))

modifiedMessage, serverAddress = clientSocket.recvfrom(2048)

print(modifiedMessage.decode())

clientSocket.close()