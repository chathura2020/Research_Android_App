#--------------------UDP server code-------------------------------------------
import socket
import thread
import sys
# Create a UDP socket
#x="Log is Empty"

def main(ipAddress,port):

    result=getConnection(ipAddress,port);

    return result;
    
    # Create two threads as follows
    #try:
    #thread.start_new_thread(getConnection,(ipAddress,port,))
      
    #except:
   #    print "Error: unable to start thread"
     
    

            


    
def getConnection(ipAddress,port) :

    x='Log Details :'
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # Bind the socket to the port
    server_address = (ipAddress,port)
    print >>sys.stderr, 'starting up on %s port %s' % server_address
    x=x+'\nstarting up on %s port %s' % server_address
    sock.bind(server_address)
    x=x+'\nClient Connected'

    #while True:
    x=x+"\nwaiting to receive message"
    print >>sys.stderr, '\nwaiting to receive message'
    data, address = sock.recvfrom(4096)
        
    print >>sys.stderr, 'received %s bytes from %s' % (len(data), address)
    x=x+'\nreceived %s bytes from %s' % (len(data), address)
    print >>sys.stderr, data
    x=x+'\nRECEVIED DATA :'+data   
    if data:
        sent = sock.sendto(data, address)
        print >>sys.stderr, 'sent %s bytes back to %s' % (sent, address)
        x=x+'\nsent %s bytes back to %s' % (sent, address)
	
	sock.close()	

    return x
    
    
