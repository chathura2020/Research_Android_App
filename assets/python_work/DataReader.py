#!/usr/bin/python


import xml.etree.ElementTree as ET

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
    
    
def main():
  print "main called"
  p=ProtocolData()
  p.setDataFile('data.xml')
  p.loadData()
  print p.getThroughput('TCP','500')
  


if __name__=="__main__":
  main()