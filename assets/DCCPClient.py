#!/usr/bin/python

import socket
import sys
import argparse


    
def main():
    
    parser = argparse.ArgumentParser(description='Send data using DCCP protocol.')
    #parser.add_argument('-ip', help='Server IP')
    #parser.add_argument('-port',help='Listening port number')
    parser.add_argument('-data', help='Data to be sent')
  
  
    args=parser.parse_args()
 

    socket.DCCP_SOCKOPT_PACKET_SIZE = 1
    socket.DCCP_SOCKOPT_SERVICE     = 2
    socket.SOCK_DCCP                = 6
    socket.IPPROTO_DCCP             = 33
    socket.SOL_DCCP                 = 269
    packet_size                     = 256
    address                         = ('192.168.10.132',1024)

  
  # Create sockets
    client = socket.socket(socket.AF_INET, socket.SOCK_DCCP, 
                               socket.IPPROTO_DCCP)
 
    client.setsockopt(socket.SOL_DCCP, socket.DCCP_SOCKOPT_PACKET_SIZE, packet_size)
    client.setsockopt(socket.SOL_DCCP, socket.DCCP_SOCKOPT_SERVICE, True)

  # Connect sockets
  
    client.connect(address)
  	
  
    client.sendall(args.data)
    data=client.recv(1024)
    while not data:
        data=client.recv(1024)
    
        print "Echo From Server: ",data
    client.close()

if __name__ == "__main__":
   main()
