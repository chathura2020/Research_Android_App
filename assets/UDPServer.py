#--------------------UDP server code-------------------------------------------
import socket
import sys
# Create a UDP socket
x="Log is Empty"
def main(ipAddress,port):
    

    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # Bind the socket to the port
    server_address = (ipAddress,port)
    print >>sys.stderr, 'starting up on %s port %s' % server_address
    x='starting up on %s port %s' % server_address
    sock.bind(server_address)
    x='Client Connected'


    while True:
        x="waiting to receive message"
        print >>sys.stderr, '\nwaiting to receive message'
        data, address = sock.recvfrom(4096)
        
        print >>sys.stderr, 'received %s bytes from %s' % (len(data), address)
        x='received %s bytes from %s' % (len(data), address)
        print >>sys.stderr, data
        
        if data:
            sent = sock.sendto(data, address)
            print >>sys.stderr, 'sent %s bytes back to %s' % (sent, address)
            x='sent %s bytes back to %s' % (sent, address)
            
def getLog():

    return x;

    
