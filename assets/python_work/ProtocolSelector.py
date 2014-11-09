#!/usr/bin/python

from DataReader import *


Object = Service


class ProtocolSelector:
  
  #Required feature flags
	  NONE=0
  	RELIABLE=1
  	UNRELIABLE=2 #
  	HIGH_FREQUENCY=4
  	LOW_FREQUENCY=8
  	LARGE_DATA=16
  	MEDIUM_DATA=32 #
  	SMALL_DATA=64
  	VERY_SMALL_DATA=128
  	HIGH_PRIORITY=256
  	LOW_PRIORITY=512
  #End of required feature flags list
  
  	transport_features=0x0
  	protcol_data=None
  
    def Add(x,y) :
      return x+y

  	def __init__(self):
    	self.protcol_data=ProtocolData('data.xml')
  
  
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
	      	if(tcp_throughput>sctp_throughput):
	        	print 'TCP SELECTED'
	        	protocol='TCP'
	      	else:
	        	print 'SCTP selected'
	        	protocol='SCTP'
  
  
    	elif(self.transport_features&self.UNRELIABLE):
      		#unreliables are DCCP and UDP
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
      
      		udp_throughput=float(self.protcol_data.getThroughput('UDP',packetSize))
      		dccp_throughput=float(self.protcol_data.getThroughput('DCCP',packetSize))
      
      		udp_delay=float(self.protcol_data.getAverageDelay('UDP',packetSize))
      		dccp_delay=float(self.protcol_data.getAverageDelay('DCCP',packetSize))
      
	      	#selecting based on throughput
	      	if(dccp_throughput>udp_throughput):
	        	print 'DCCP SELECTED'
	        	protocol='DCCP'
	      	else:
	        	print 'UDP selected'
	        	protocol='UDP'
    	return protocol
#end def select
    

    
def main():
	p=ProtocolSelector()
	p.setTransportFeatures(p.UNRELIABLE|p.MEDIUM_DATA)
	selected_protocol=p.selectProtocol()
	return selected_protocol

 # p.setTransportFeatures(p.RELIABLE|p.SMALL_DATA)
 #selected_protocol=p.selectProtocol()
 #print selected_protocol

 #if __name__=='__main__':
#  main()