#--------------------UDP server code-------------------------------------------
import socket

import sys
# Create a UDP socket
#x="Log is Empty"


class UDPServer :
    
    
    addressList=[]
    clientCount=0
    sock=socket.socket();
   
    def static_var(self,varname, value):
        def decorate(func):
            setattr(func, varname, value)
            return func
        return decorate
   
    
    def getConnection(self,ipAddress,port) :

        x='Log Details :'
        
        try:
            
            self.sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    
        
    # Bind the socket to the port
            server_address = (ipAddress,port)
            print >>sys.stderr, 'starting up on %s port %s' % server_address
            x=x+'\nstarting up on %s port %s' % server_address
            self.sock.bind(server_address)
            x=x+'\nClient Connected'
        except:
            print 'Socket is already up'
        
        print'Return'
        return self.sock
    
    def recevieDataFromClient(self,socket):
    
        log='empty';
        hasAddress=False
        print >>sys.stderr, '\nwaiting to receive message'
        data, address = socket.recvfrom(4096)
        
        if(self.clientCount==0):
            self.addressList.insert(0,address)
            self.clientCount=self.clientCount+1
        else:
            for exAddress in self.addressList:
               
                if exAddress==address:
                    hasAddress=True
                    break
            
            if(hasAddress==False):    
                self.addressList.insert(self.clientCount,address)
                self.clientCount=self.clientCount+1   
        
        print >>sys.stderr, 'received %s bytes from %s' % (len(data), address)
        log=log+'\nreceived %s bytes from %s' % (len(data), address)
        print >>sys.stderr, data
        log=log+'\nRECEVIED DATA :'+data
        self.test=data;
        return self.addressList
    
    def sendDataToClient(self,socket,data,address):
    
        
        socket.sendto(data, address)
        
    def closeServerConnection(self,socket):
        
        socket.close();
    
    def getAddressList(self):
        return self.addressList
    
    def getClientCount(self):
        return self.clientCount        
    

def getConnection(ipAddress,port):
    sock=UDPServer.getConnection(ipAddress,port);
    return sock

def resivieDataFromClient(sock):
    addressList=UDPServer.recevieDataFromClient(sock)

    return addressList

def sendDataToClient(socket,data,addressList):

    UDPServer.sendDataToClient(socket,data,addressList[0])
    

    
#def CreateObject(ipAddress,port):
    
    #udp=UDPServer();
    
    
    
    #clientAddress=udp.recevieDataFromClient(sock)
    
    
    #udp.sendDataToClient(sock,"this is my sample",clientAddress[0])
    
    # Create two threads as follows
    #try:
    #thread.start_new_thread(getConnection,(ipAddress,port,))
      
    #except:
   #    print "Error: unable to start thread"
     
    

            


    

    





    
        
