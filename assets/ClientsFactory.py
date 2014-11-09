#!/usr/bin/python

import socket
import sys

class NetworkClientFactory(object):
  
  def get_client(client_type,ip_address,port):
    if(client_type=='TCP'):
      return TCPClient(ip_address,port)
    elif(client_type=='UDP'):
      return UDPClient(ip_address,port)
    elif(client_type=='DCCP'):
      pass
    elif(client_type=='SCTP'):
      pass
    
    
    
  get_client=staticmethod(get_client)
  
  
  

class TCPClient(object):
  
  
  def __init__(self,ip,port):
    # Create a TCP/IP socket
    print " Create a TCP/IP socket"
    
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    
    self.ip_address=ip
    self.port=port
    
    # Connect the socket to the port where the server is listening
    print "Connect the socket to the port where the server is listening"
    server_address = (self.ip_address,self.port)

    print "Connecting To Server"
    sock.connect(server_address)
    self.tcp_server_socket=sock
    print "Connected To Server"
  
  def send_data(self,data):
    try:
    
      # Send data
      message = data
      print >>sys.stderr, 'sending "%s"' % message
      self.tcp_server_socket.sendall(message)

      amount_received = 0
      amount_expected = len(message)
    
      while amount_received < amount_expected:
        data = self.tcp_server_socket.recv(16)
        amount_received += len(data)
        print >>sys.stderr, 'received "%s"' % data

    finally:
      print >>sys.stderr, 'closing socket'
      self.tcp_server_socket.close()

#------------------------------------------------------------------------------

class UDPClient(object):
  
  
  def __init__(self,ip,port):
    # Create a TCP/IP socket
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    
    self.ip_address=ip
    self.port=port
    
    # Connect the socket to the port where the server is listening
    server_address = (self.ip_address,self.port)
    
    sock.connect(server_address)
    self.udp_server_socket=sock
    
  
  def send_data(self,data):
    try:
    
      # Send data
      message = data
      print >>sys.stderr, 'sending "%s"' % message
      self.tcp_server_socket.sendall(message)

      amount_received = 0
      amount_expected = len(message)
    
      while amount_received < amount_expected:
        data = self.tcp_server_socket.recv(16)
        amount_received += len(data)
        print >>sys.stderr, 'received "%s"' % data

    finally:
      print >>sys.stderr, 'closing socket'
      self.tcp_server_socket.close()

#------------------------------------------------------------------------------

def main(protocol,datasize):

  networkFactory=NetworkClientFactory()
  
  socket=NetworkClientFactory.get_client(protocol,'10.0.2.2',5500)
  socket.send_data("This is a sample data")
  


    
