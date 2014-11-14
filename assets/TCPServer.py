#------------TCP Server code------------------------------------------

import socket
import sys




def maintcp(ipAddress,port) :

    result=getConnection(ipAddress,port);

    return result;

def getConnection (ipAddress,port):
    
    result='Log Details'
    # Create a TCP/IP socket
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Bind the socket to the port
    server_address = (ipAddress,port)
    print >>sys.stderr, 'starting up on %s port %s' % server_address
    result=result+'\nstarting up on %s port %s' % server_address
    sock.bind(server_address)

    # Listen for incoming connections
    sock.listen(1)


    # Wait for a connection
    print >>sys.stderr, 'waiting for a connection'
    connection, client_address = sock.accept()
    try:
        print >>sys.stderr, 'connection from %s port %s' % client_address
	result=result+'\nconnection from %s port %s' % client_address
        # Receive the data in small chunks and retransmit it
        totData=''
        while True:
            data = connection.recv(1024)
            totData=totData+data
            print >>sys.stderr, 'received "%s"' % data
			
            if data:
                print >>sys.stderr, 'sending data back to the client'
		#result=result+'\nsending data back to the client'
                connection.sendall(data)
            else:
                print >>sys.stderr, 'no more data from', client_address
		result=result+'\nRESIVED DATA : "%s"' % totData
                break
            
    finally:
        # Clean up the connection
        connection.close()
	
    return result	
