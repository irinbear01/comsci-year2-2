from socket import *

serverName = '10.107.15.16'
serverPort = 4040

clientSocket = socket(AF_INET, SOCK_STREAM)

# เชื่อมต่อไปที่ server
clientSocket.connect((serverName, serverPort))

sentence = input('Input lowercase sentence: ')
clientSocket.send(sentence.encode())

modifiedSentence = clientSocket.recv(1024)
print('From Server:', modifiedSentence.decode())

clientSocket.close()