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
      return DCCPClient(ip_address,port)
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
        data = self.tcp_server_socket.recv(60)
        amount_received += len(data)
        print >>sys.stderr, 'received "%s"' % data

    finally:
      print >>sys.stderr, 'closing socket'
      self.tcp_server_socket.close()

    return data  

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
      self.udp_server_socket.sendall(message)

      amount_received = 0
      amount_expected = len(message)
    
      while amount_received < amount_expected:
        data = self.udp_server_socket.recv(60)
        amount_received += len(data)
        print >>sys.stderr, 'received "%s"' % data

    finally:
      print >>sys.stderr, 'closing socket'
      self.udp_server_socket.close()
    return data  

#------------------------------------------------------------------------------

class DCCPClient:

   def __init__(self,ip,port):

    socket.DCCP_SOCKOPT_PACKET_SIZE = 1
    socket.DCCP_SOCKOPT_SERVICE     = 2
    socket.SOCK_DCCP                = 6
    socket.IPPROTO_DCCP             = 33
    socket.SOL_DCCP                 = 269
    packet_size                     = 256
    address                         = (ip,port)

    # Create sockets
    client = socket.socket(socket.AF_INET, socket.SOCK_DCCP, 
                               socket.IPPROTO_DCCP)

    client.setsockopt(socket.SOL_DCCP, socket.DCCP_SOCKOPT_PACKET_SIZE, packet_size)
    client.setsockopt(socket.SOL_DCCP, socket.DCCP_SOCKOPT_SERVICE, True)

    # Connect sockets
  
    client.connect(address)
    self.dccp_server_socket=client

  def send_data(self,data):
    try:
      
      self.dccp_server_socket.sendall(data)
      data=client.recv(1024)
      while not data:
        data=client.recv(1024)
        print "Echo From Server: ",data

    finally:
      print 'DCCP client Socket Close'
      client.close()
    
  
#------------------------------------------------------------------------------
def main(protocol,ipaddress,datasize,port):

  networkFactory=NetworkClientFactory()
  
  socket=NetworkClientFactory.get_client(protocol,ipaddress,port)
  respond=socket.send_data("This is a sample data")
  
  return respond

    
