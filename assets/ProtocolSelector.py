#!/usr/bin/python

#from protocolData import ProtocolData

import xml.etree.ElementTree as ET
import socket
import sys

class ProtocolSelector:
    
  
    #Required feature flags
    NONE=0
    RELIABLE=1
    UNRELIABLE=2 #
    HIGH_FREQUENCY=4
    TRIGGERED_FREQUENCY=8
    URGENT_FREQUENCY=16
    LARGE_DATA=32
    MEDIUM_DATA=64 #
    SMALL_DATA=128
    VERY_SMALL_DATA=256
    HIGH_PRIORITY=512
    LOW_PRIORITY=1024
    #End of required feature flags list
  
    transport_features=0x0
    protcol_data=None
  
    def __init__(self):
        self.protcol_data=ProtocolData('/data/data/chathura.research/files/data.xml')
                #we have to give the direct path to the xml on the mobile

    def Add(self,x,y) :
        return x+y
  
        
    def setTransportFeatures(self,features): # 5= 1+4, reliable and high frequency
        print 'Setting transport feature',features
        self.transport_features=features
        
    def selectProtocol(self):
        

        protocol=''
        packetSize=0
        if(self.transport_features&self.RELIABLE):
        #reliable protocols are TCP and SCTP
      
            if(self.transport_features&self.LARGE_DATA):
                
                
                #print 'reliable large'
                packetSize='1024'

            elif(self.transport_features&self.MEDIUM_DATA):
                #print 'reliable medium'
                packetSize='500'

            elif(self.transport_features&self.SMALL_DATA):
                #print 'reliable small'
                packetSize='100'

            elif(self.transport_features&self.VERY_SMALL_DATA):
                #print 'reliable very small'
                packetSize='10'
              
            tcp_throughput=float(self.protcol_data.getThroughput('TCP',packetSize))
            sctp_throughput=float(self.protcol_data.getThroughput('SCTP',packetSize))

            tcp_delay=float(self.protcol_data.getAverageDelay('TCP',packetSize))
            sctp_delay=float(self.protcol_data.getAverageDelay('SCTP',packetSize))

                      
            #selecting based on throughput
            if(tcp_throughput>sctp_throughput and tcp_delay<sctp_delay):
                print 'TCP SELECTED'
                protocol='TCP'
            else:
                print 'SCTP selected'
                protocol='SCTP'
  
  
        elif(self.transport_features&self.UNRELIABLE):
            
            if(self.transport_features&self.LARGE_DATA):
                packetSize='1024'

            elif(self.transport_features&self.MEDIUM_DATA):
                #print 'reliable medium'
                packetSize='500'

            elif(self.transport_features&self.SMALL_DATA):
                packetSize='100'

            elif(self.transport_features&self.VERY_SMALL_DATA):
                packetSize='10'
      
            udp_throughput=float(self.protcol_data.getThroughput('UDP',packetSize))
            dccp_throughput=float(self.protcol_data.getThroughput('DCCP',packetSize))
      
            udp_delay=float(self.protcol_data.getAverageDelay('UDP',packetSize))
            dccp_delay=float(self.protcol_data.getAverageDelay('DCCP',packetSize))
            print 'UDP '+ str(udp_delay)
            print 'DCCP '+str(dccp_delay)
            #selecting based on throughput
            if(dccp_throughput>udp_throughput and dccp_delay<udp_delay):
                print 'DCCP SELECTED'
                protocol='DCCP'
            else:
                print 'UDP selected'
                protocol='UDP'
        return protocol

class ProtocolData:
    throughput=dict()
    averageDelay=dict()
    dropRatio=dict()
    xmldoc=None

    def __init__(self,dataFile):
        self.setDataFile(dataFile)
        self.loadData()
    

    def setDataFile(self,dataFile):
        if(dataFile):
            self.xmldoc=ET.parse(dataFile)   
        else:
            print "Invalid data file path"
  
  
    def loadData(self):
        root=self.xmldoc.getroot()#data tag
        protocol_list=["TCP","UDP","DCCP","SCTP"]
    
        for protocol in protocol_list:
            #print "getting data for the protocol ",protocol
            xpath_string_throughput="./Protocol/"+protocol+"/Node/Throughput/packet"
            xpath_string_delays="./Protocol/"+protocol+"/Node/AverageDelay/packet"
            xpath_string_drop="./Protocol/"+protocol+"/Node/DropRatio/packet"
            #print "XPath for ",protocol, xpath_string_delays
      
            self.throughput[protocol]=dict()
            self.averageDelay[protocol]=dict()
            self.dropRatio[protocol]=dict()
      
      
            for throughputs in root.findall(xpath_string_throughput):
                packetSize=throughputs.attrib['size']
                thrput=throughputs.text
                self.throughput[protocol][packetSize]=thrput
          
            for delays in root.findall(xpath_string_delays):
                packetSize=delays.attrib['size']
                delay=delays.text
                self.averageDelay[protocol][packetSize]=delay
          
          
            for drops in root.findall(xpath_string_drop):
                packetSize=drops.attrib['size']
                drop=drops.text
                self.dropRatio[protocol][packetSize]=drop


    def getThroughput(self,protocol,packetSize):
        if(self.throughput):
            return self.throughput[protocol][packetSize];
    
    def getAverageDelay(self,protocol,packetSize):
        if(self.averageDelay):
            return self.averageDelay[protocol][packetSize]
    
    def getDropRatio(self,protocol,packetSize):
        if(self.dropRatio):
            return self.dropRatio[protocol][packetSize];

    
def mainProtocol(feature_list):
    p=ProtocolSelector()
    p.setTransportFeatures(feature_list)
    selected_protocol=p.selectProtocol()
    
    return selected_protocol
  
# p.setTransportFeatures(p.RELIABLE|p.SMALL_DATA)
#selected_protocol=p.selectProtocol()
#print selected_protocol

#if __name__=='__main__':
#  main()
#=======================ClientFactorty=========================================



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

    def send_data(self,data) :
        
        try:
      
            self.dccp_server_socket.sendall(data)
            data=dccp_server_socket.recv(1024)
            while not data:
                data=dccp_server_socket.recv(1024)
                print "Echo From Server: ",data

        finally:
            print 'DCCP client Socket Close'
            dccp_server_socket.close()
    
  
#------------------------------------------------------------------------------
def mainClient(protocol,ipaddress,port):
    
    networkFactory=NetworkClientFactory()
      
    socket=NetworkClientFactory.get_client(protocol,ipaddress,port)
    respond=socket.send_data("This is a sample data ")
      
    return respond

    





