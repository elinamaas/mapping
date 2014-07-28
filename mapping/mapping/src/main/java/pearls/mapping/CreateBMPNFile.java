package pearls.mapping;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import net.sf.saxon.evpull.EndElementEvent;
import net.sf.saxon.evpull.StartElementEvent;

public class CreateBMPNFile {
	private String bpmnFile;

	public void setFile(String configFile) {
		this.bpmnFile = configFile;
	}

	@SuppressWarnings("restriction")
	public void saveConfig(BPMNElements bpmn) throws Exception {
		// create an XMLOutputFactory
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		// create XMLEventWriter
		XMLEventWriter eventWriter = outputFactory
				.createXMLEventWriter(new FileOutputStream(bpmnFile));
		// create an EventFactory
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent oneTab = eventFactory.createDTD("\n\t");
		XMLEvent newLine = eventFactory.createDTD("\n");
		// create and write Start Tag
		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);
		eventWriter.add(oneTab);

		// create config open tag
		StartElement configStartElement = eventFactory.createStartElement("",
				"", "definitions");
		
		eventWriter.add(configStartElement);
		Attribute xmlnsAttribute = eventFactory.createAttribute("xmlns", "http://www.omg.org/spec/BPMN/20100524/MODEL");
		eventWriter.add(xmlnsAttribute);
		Attribute bpmndi = eventFactory.createAttribute("xmlns:bpmndi", "http://www.omg.org/spec/BPMN/20100524/DI");
		eventWriter.add(bpmndi);
		Attribute omgdc = eventFactory.createAttribute("xmlns:omgdc", "http://www.omg.org/spec/DD/20100524/DC");
		eventWriter.add(omgdc);
		Attribute omgdi = eventFactory.createAttribute("xmlns:omgdi","http://www.omg.org/spec/DD/20100524/DI");
		eventWriter.add(omgdi);
		Attribute signavio = eventFactory.createAttribute("xmlns:signavio","http://www.signavio.com");
		eventWriter.add(signavio);
		Attribute xsi = eventFactory.createAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		eventWriter.add(xsi);
		Attribute exporter = eventFactory.createAttribute("exporter", "Signavio Process Editor, http://www.signavio.com");
		eventWriter.add(exporter);
		Attribute exporterVersion = eventFactory.createAttribute("exporterVersion", "8.0.2");
		eventWriter.add(exporterVersion);
		Attribute expressionLanguage = eventFactory.createAttribute("expressionLanguage", "http://www.w3.org/1999/XPath");
		eventWriter.add(expressionLanguage);
		Attribute id = eventFactory.createAttribute("id", "sid-ccd4e011-9bb6-4efa-aff1-48ce038f9b86");
		eventWriter.add(id);
		Attribute targetNamespace = eventFactory.createAttribute("targetNamespace", "http://www.signavio.com/bpmn20");
		eventWriter.add(targetNamespace);
		Attribute typeLanguage = eventFactory.createAttribute("typeLanguage", "http://www.w3.org/2001/XMLSchema");
		eventWriter.add(typeLanguage);
		Attribute schemaLocation = eventFactory.createAttribute("xsi:schemaLocation", "http://www.omg.org/spec/BPMN/20100524/MODEL http://www.omg.org/spec/BPMN/2.0/20100501/BPMN20.xsd");
		eventWriter.add(schemaLocation);
		

		eventWriter.add(newLine);
		// Write the different nodes
		//forLOOp
		createProcess (eventWriter, bpmn);
		createNotation(eventWriter, bpmn);
		eventWriter.add(eventFactory.createEndElement("", "", "definitions"));
		eventWriter.add(newLine);
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}

	@SuppressWarnings("restriction")
	private void createProcess(XMLEventWriter eventWriter, BPMNElements bpmn) throws XMLStreamException {

		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent newLine = eventFactory.createDTD("\n");
		XMLEvent tab1 = eventFactory.createDTD("\t");
		XMLEvent tab2 = eventFactory.createDTD("\t\t");
		// create Start node
		StartElement sElement = eventFactory.createStartElement("","", "process");
		eventWriter.add(tab1);
		eventWriter.add(sElement);
		Attribute idProcess = eventFactory.createAttribute("id", bpmn.getProcessId());
		eventWriter.add(idProcess);
		eventWriter.add(newLine);
		//create StartTask
//		eventWriter.add(tab2);
//		eventWriter.add(tab1);
		
		//create tasks
		ArrayList<TaskBpmn> tasks = bpmn.getTasks();
		for (TaskBpmn task : tasks){
			if (task.getType().equals("NT_STARTFLOW")){
				StartElement startStartTask = eventFactory.createStartElement("", "", "startEvent");
				eventWriter.add(tab2);
				eventWriter.add(startStartTask);
				Attribute startTaskId = eventFactory.createAttribute("id", task.getId());
				eventWriter.add (startTaskId);
				Attribute startTaskName = eventFactory.createAttribute("name", task.getName());
				eventWriter.add (startTaskName);
				EndElement endStartTask = eventFactory.createEndElement("", "", "startEvent");
				eventWriter.add(endStartTask);
				eventWriter.add(newLine);
				
			}
			else if (task.getType().equals("NT_ENDFLOW")){
				//create EndTask
				StartElement startEndTask = eventFactory.createStartElement("", "", "endEvent");
				eventWriter.add(tab2);
//				eventWriter.add(tab2);
//				eventWriter.add(tab1);
				eventWriter.add(startEndTask);
				Attribute endTaskId = eventFactory.createAttribute("id", task.getId());
				eventWriter.add (endTaskId);
				Attribute endTaskName = eventFactory.createAttribute("name", task.getName());
				eventWriter.add (endTaskName);
				EndElement endEndTask = eventFactory.createEndElement("", "", "endEvent");
				eventWriter.add(endEndTask);
				eventWriter.add(newLine);
				
			}else if(task.getType().equals("NT_NORMAL")){
				
				StartElement taskBPMN = eventFactory.createStartElement("", "", "task");
				eventWriter.add(tab2);
//				eventWriter.add(tab1);
				eventWriter.add(taskBPMN);
				
				Attribute idTaskBPMN = eventFactory.createAttribute("id", task.getId());
				eventWriter.add(idTaskBPMN);
				Attribute nameTaskBPMN = eventFactory.createAttribute("name", task.getName());
				eventWriter.add(nameTaskBPMN);
				
				
				EndElement endTask = eventFactory.createEndElement("", "", "task");
				eventWriter.add(endTask);
				eventWriter.add(newLine);
			}else if(task.getType().equals("NT_AND_SPLIT")){
				
				StartElement taskBPMN = eventFactory.createStartElement("", "", "parallelGateway");
				eventWriter.add(tab2);
//				eventWriter.add(tab1);
				eventWriter.add(taskBPMN);
				
				Attribute gatewayDirection = eventFactory.createAttribute("gatewayDirection", "Diverging");
				eventWriter.add(gatewayDirection);
				
				Attribute idTaskBPMN = eventFactory.createAttribute("id", task.getId());
				eventWriter.add(idTaskBPMN);
				Attribute nameTaskBPMN = eventFactory.createAttribute("name", "");
				eventWriter.add(nameTaskBPMN);
				eventWriter.add(newLine);
				
				ArrayList<String> sourceNodes = getSourceNodes(bpmn, task.getId());
				for (String node : sourceNodes){
					StartElement source = eventFactory.createStartElement("", "", "outgoing");
					
					eventWriter.add(tab2);
					eventWriter.add(tab2);
					eventWriter.add(source);
					//Create Content
			        Characters characters = eventFactory.createCharacters(node);
			        eventWriter.add(characters);
			        
			        EndElement endOutgoing = eventFactory.createEndElement("", "", "outgoing");
			        
					eventWriter.add(endOutgoing);
					eventWriter.add(newLine);
				}
				
				ArrayList<String> destinationNodes = getDestinationsNodes(bpmn, task.getId());
				for (String node : destinationNodes){
					StartElement source = eventFactory.createStartElement("", "", "incoming");
					
					eventWriter.add(tab2);
					eventWriter.add(tab2);
					eventWriter.add(source);
					//Create Content
			        Characters characters = eventFactory.createCharacters(node);
			        eventWriter.add(characters);
			        EndElement endIncoming = eventFactory.createEndElement("", "", "incoming");
					eventWriter.add(endIncoming);
					eventWriter.add(newLine);
				}
				
				
				EndElement endTask = eventFactory.createEndElement("", "", "parallelGateway");
				eventWriter.add(tab2);
//				eventWriter.add(tab1);
				eventWriter.add(endTask);
				eventWriter.add(newLine);
				
			}else if (task.getType().equals("NT_AND_JOIN")){
				StartElement taskBPMN = eventFactory.createStartElement("", "", "parallelGateway");
//				eventWriter.add(newLine);
				eventWriter.add(tab2);
//				eventWriter.add(tab1);
				eventWriter.add(taskBPMN);
				
				Attribute gatewayDirection = eventFactory.createAttribute("gatewayDirection", "Converging");
				eventWriter.add(gatewayDirection);
				
				Attribute idTaskBPMN = eventFactory.createAttribute("id", task.getId());
				eventWriter.add(idTaskBPMN);
				Attribute nameTaskBPMN = eventFactory.createAttribute("name", "");
				eventWriter.add(nameTaskBPMN);
				eventWriter.add(newLine);
				
				ArrayList<String> sourceNodes = getSourceNodes(bpmn, task.getId());
				for (String node : sourceNodes){
					StartElement source = eventFactory.createStartElement("", "", "outgoing");
					eventWriter.add(newLine);
					eventWriter.add(tab2);
					eventWriter.add(tab2);
					eventWriter.add(source);
					//Create Content
			        Characters characters = eventFactory.createCharacters(node);
			        eventWriter.add(characters);
			        EndElement endOutgoing = eventFactory.createEndElement("", "", "outgoing");
					eventWriter.add(endOutgoing);
					eventWriter.add(newLine);
				}
				
				ArrayList<String> destinationNodes = getDestinationsNodes(bpmn, task.getId());
				for (String node : destinationNodes){
					StartElement source = eventFactory.createStartElement("", "", "incoming");
					eventWriter.add(newLine);
					eventWriter.add(tab2);
					eventWriter.add(tab2);
					eventWriter.add(source);
					//Create Content
			        Characters characters = eventFactory.createCharacters(node);
			        eventWriter.add(characters);
			        EndElement endIncoming = eventFactory.createEndElement("", "", "incoming");
			        eventWriter.add(tab2);
					eventWriter.add(endIncoming);
					eventWriter.add(newLine);
				}
				
				
				EndElement endTask = eventFactory.createEndElement("", "", "parallelGateway");
				eventWriter.add(endTask);
				eventWriter.add(newLine);
			}else if (task.getType().equals("NT_XOR_JOIN")){
				StartElement taskBPMN = eventFactory.createStartElement("", "", "exclusiveGateway");
				eventWriter.add(tab2);
//				eventWriter.add(tab1);
				eventWriter.add(taskBPMN);
				
				Attribute gatewayDirection = eventFactory.createAttribute("gatewayDirection", "Converging");
				eventWriter.add(gatewayDirection);
				
				Attribute idTaskBPMN = eventFactory.createAttribute("id", task.getId());
				eventWriter.add(idTaskBPMN);
				Attribute nameTaskBPMN = eventFactory.createAttribute("name", "");
				eventWriter.add(nameTaskBPMN);
				eventWriter.add(newLine);
				
				ArrayList<String> sourceNodes = getSourceNodes(bpmn, task.getId());
				for (String node : sourceNodes){
					StartElement source = eventFactory.createStartElement("", "", "outgoing");
					eventWriter.add(tab2);
					eventWriter.add(tab1);
					eventWriter.add(source);
					//Create Content
			        Characters characters = eventFactory.createCharacters(node);
			        eventWriter.add(characters);
			        EndElement endOutgoing = eventFactory.createEndElement("", "", "outgoing");
					eventWriter.add(endOutgoing);
					eventWriter.add(newLine);
				}
				
				ArrayList<String> destinationNodes = getDestinationsNodes(bpmn, task.getId());
				for (String node : destinationNodes){
					StartElement source = eventFactory.createStartElement("", "", "incoming");
					eventWriter.add(tab2);
					eventWriter.add(tab1);
					eventWriter.add(source);
					//Create Content
			        Characters characters = eventFactory.createCharacters(node);
			        eventWriter.add(characters);
			        EndElement endIncoming = eventFactory.createEndElement("", "", "incoming");
					eventWriter.add(endIncoming);
					eventWriter.add(newLine);
				}
				
				
				EndElement endTask = eventFactory.createEndElement("", "", "exclusiveGateway");
				eventWriter.add(tab2);
				eventWriter.add(endTask);
				eventWriter.add(newLine);
			}else if(task.getType().equals("NT_XOR_SPLIT")){
				
				StartElement taskBPMN = eventFactory.createStartElement("", "", "exclusiveGateway");
				eventWriter.add(tab2);
//				eventWriter.add(tab1);
				eventWriter.add(taskBPMN);
				
				Attribute gatewayDirection = eventFactory.createAttribute("gatewayDirection", "Diverging");
				eventWriter.add(gatewayDirection);
				
				Attribute idTaskBPMN = eventFactory.createAttribute("id", task.getId());
				eventWriter.add(idTaskBPMN);
				Attribute nameTaskBPMN = eventFactory.createAttribute("name", "");
				eventWriter.add(nameTaskBPMN);
				eventWriter.add(newLine);
				
				ArrayList<String> sourceNodes = getSourceNodes(bpmn, task.getId());
				for (String node : sourceNodes){
					StartElement source = eventFactory.createStartElement("", "", "outgoing");
					eventWriter.add(tab2);
					eventWriter.add(tab1);
					eventWriter.add(source);
					//Create Content
			        Characters characters = eventFactory.createCharacters(node);
			        eventWriter.add(characters);
			        
			        EndElement endOutgoing = eventFactory.createEndElement("", "", "outgoing");
					eventWriter.add(endOutgoing);
					eventWriter.add(newLine);
				}
				
				ArrayList<String> destinationNodes = getDestinationsNodes(bpmn, task.getId());
				for (String node : destinationNodes){
					StartElement source = eventFactory.createStartElement("", "", "incoming");
					eventWriter.add(tab2);
					eventWriter.add(tab1);
					eventWriter.add(source);
					//Create Content
			        Characters characters = eventFactory.createCharacters(node);
			        eventWriter.add(characters);
			        EndElement endIncoming = eventFactory.createEndElement("", "", "incoming");
					eventWriter.add(endIncoming);
					eventWriter.add(newLine);
				}
				
				
				EndElement endTask = eventFactory.createEndElement("", "", "exclusiveGateway");
				eventWriter.add(tab2);
				eventWriter.add(endTask);
				eventWriter.add(newLine);
				
			}else if (task.getType().equals("NT_STARTLOOP")){
				StartElement taskBPMN = eventFactory.createStartElement("", "", "exclusiveGateway");
				eventWriter.add(tab2);
//				eventWriter.add(tab1);
				eventWriter.add(taskBPMN);
				
				Attribute gatewayDirection = eventFactory.createAttribute("gatewayDirection", "Converging");
				eventWriter.add(gatewayDirection);
				
				Attribute idTaskBPMN = eventFactory.createAttribute("id", task.getId());
				eventWriter.add(idTaskBPMN);
				Attribute nameTaskBPMN = eventFactory.createAttribute("name", "");
				eventWriter.add(nameTaskBPMN);
				eventWriter.add(newLine);
				
				ArrayList<String> sourceNodes = getSourceNodes(bpmn, task.getId());
				for (String node : sourceNodes){
					StartElement source = eventFactory.createStartElement("", "", "outgoing");
					eventWriter.add(tab2);
					eventWriter.add(tab1);
					eventWriter.add(source);
					//Create Content
			        Characters characters = eventFactory.createCharacters(node);
			        eventWriter.add(characters);
			        EndElement endOutgoing = eventFactory.createEndElement("", "", "outgoing");
					eventWriter.add(endOutgoing);
					eventWriter.add(newLine);
				}
				
				ArrayList<String> destinationNodes = getDestinationsNodes(bpmn, task.getId());
				for (String node : destinationNodes){
					StartElement source = eventFactory.createStartElement("", "", "incoming");
					eventWriter.add(tab2);
					eventWriter.add(tab1);
					eventWriter.add(source);
					//Create Content
			        Characters characters = eventFactory.createCharacters(node);
			        eventWriter.add(characters);
			        EndElement endIncoming = eventFactory.createEndElement("", "", "incoming");
					eventWriter.add(endIncoming);
					eventWriter.add(newLine);
				}
				
				
				EndElement endTask = eventFactory.createEndElement("", "", "exclusiveGateway");
				eventWriter.add(tab2);
				eventWriter.add(endTask);
				eventWriter.add(newLine);
			}else if(task.getType().equals("NT_ENDLOOP")){
				
				StartElement taskBPMN = eventFactory.createStartElement("", "", "exclusiveGateway");
				eventWriter.add(tab2);
//				eventWriter.add(tab1);
				eventWriter.add(taskBPMN);
				
				Attribute gatewayDirection = eventFactory.createAttribute("gatewayDirection", "Diverging");
				eventWriter.add(gatewayDirection);
				
				Attribute idTaskBPMN = eventFactory.createAttribute("id", task.getId());
				eventWriter.add(idTaskBPMN);
				Attribute nameTaskBPMN = eventFactory.createAttribute("name", "");
				eventWriter.add(nameTaskBPMN);
				eventWriter.add(newLine);
				
				ArrayList<String> sourceNodes = getSourceNodes(bpmn, task.getId());
				for (String node : sourceNodes){
					StartElement source = eventFactory.createStartElement("", "", "outgoing");
					eventWriter.add(tab2);
					eventWriter.add(tab1);
					eventWriter.add(source);
					//Create Content
			        Characters characters = eventFactory.createCharacters(node);
			        eventWriter.add(characters);
			        
			        EndElement endOutgoing = eventFactory.createEndElement("", "", "outgoing");
			        eventWriter.add(tab2);
					eventWriter.add(endOutgoing);
					eventWriter.add(newLine);
				}
				
				ArrayList<String> destinationNodes = getDestinationsNodes(bpmn, task.getId());
				for (String node : destinationNodes){
					StartElement source = eventFactory.createStartElement("", "", "incoming");
					eventWriter.add(tab2);
					eventWriter.add(tab1);
					eventWriter.add(source);
					//Create Content
			        Characters characters = eventFactory.createCharacters(node);
			        eventWriter.add(characters);
			        EndElement endIncoming = eventFactory.createEndElement("", "", "incoming");
					eventWriter.add(endIncoming);
					eventWriter.add(newLine);
				}
				
				
				EndElement endTask = eventFactory.createEndElement("", "", "exclusiveGateway");
				eventWriter.add(tab2);
				eventWriter.add(endTask);
				eventWriter.add(newLine);
				
			}
			
		}
		
		
		//create flows
		ArrayList<SequenceFlow> flows = bpmn.getFlows();
		for(SequenceFlow flow : flows){
			
			StartElement startFlow = eventFactory.createStartElement("", "", "sequenceFlow");
			eventWriter.add(tab2);
			eventWriter.add(tab1);
			eventWriter.add(startFlow);
			
			Attribute idFlow = eventFactory.createAttribute("id", flow.getId());
			eventWriter.add(idFlow);
			Attribute nameFlow = eventFactory.createAttribute("name", flow.getName());
			eventWriter.add(nameFlow);
			
			Attribute source = eventFactory.createAttribute("sourceRef", flow.getSource().getId());
			eventWriter.add(source);
			Attribute target = eventFactory.createAttribute("targetRef", flow.getTarget().getId());
			eventWriter.add(target);
					
			EndElement endFlow = eventFactory.createEndElement("", "", "startFlow");
			eventWriter.add(endFlow);
			eventWriter.add(newLine);
			
		}

		
		// create End node
		EndElement eElement = eventFactory.createEndElement("", "", "process");
		eventWriter.add(tab2);
		eventWriter.add(eElement);
		eventWriter.add(newLine);
		


	}
	
	@SuppressWarnings("restriction")
	private void createNotation (XMLEventWriter eventWriter, BPMNElements bpmn) throws XMLStreamException{
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab1 = eventFactory.createDTD("\t");
		XMLEvent tab2 = eventFactory.createDTD("\t\t");
		// create Start node
		StartElement sElement = eventFactory.createStartElement("","", "bpmndi:BPMNDiagram");
		eventWriter.add(tab2);
		eventWriter.add(sElement);
		String id = "graphicaRepresentation" + bpmn.getProcessId();
		Attribute diagram = eventFactory.createAttribute("id", id);
		eventWriter.add(diagram);
		eventWriter.add(end);
		
		//create BPMNPlane
		eventWriter.add(tab2);
		eventWriter.add(tab1);
		StartElement startPlane = eventFactory.createStartElement("", "", "bpmndi:BPMNPlane");
		eventWriter.add(startPlane);
		
		Attribute processId = eventFactory.createAttribute("bpmnElement", bpmn.getProcessId());
		eventWriter.add(processId);
		
		String idPlane = "plane" + bpmn.getProcessId();
		Attribute planeId = eventFactory.createAttribute("id", idPlane);
		eventWriter.add(planeId);
		double x = 150;
		double y= 150;
		//create BPMNShape for start 
//		createBPMNShapeTask(eventFactory, eventWriter, bpmn.getProcessId(), bpmn.getStartEventId(), x, y);
//		x = x+120;

		ArrayList<TaskBpmn> tasks = bpmn.getTasks();
		for (TaskBpmn task : tasks){
			
			createBPMNShapeTask(eventFactory, eventWriter, bpmn.getProcessId(), task.id, x, y);
			x= x+120;
			//for all shapes height = 60.0; weight = 60.0
			task.setBounds(60.0, 60.0, x, y);
			
		}
		
		//create BPMNShape for end task
//		createBPMNShapeTask(eventFactory, eventWriter, bpmn.getProcessId(), bpmn.getEndEventId(), x, y);
//		x = x+120;
		createBPMNFlow (eventFactory, eventWriter, bpmn);
		
//		ArrayList<SequenceFlow> flows = bpmn.getFlows();
		
		

		
		EndElement endPlane = eventFactory.createEndElement("", "", "bpmndi:BPMNPlane");
		eventWriter.add(endPlane);
		eventWriter.add(end);

		// create End node
		EndElement eElement = eventFactory.createEndElement("", "", "bpmndi:BPMNDiagram");
		eventWriter.add(tab2);
		eventWriter.add(eElement);
		eventWriter.add(end);
		
		
	}
	
	public ArrayList<String> getDestinationsNodes(BPMNElements bpmn, String id){
		ArrayList<SequenceFlow> flows = bpmn.getFlows();
		ArrayList<String> destinationNodes = new ArrayList<String>();
		for (SequenceFlow flow: flows){
			if(flow.getSource().getId().equals(id)){
				
				String destinationNodeId = flow.getTarget().getId();
				destinationNodes.add(destinationNodeId);
				
			}
			
		}
		return destinationNodes;
	}
	
	public ArrayList<String> getSourceNodes(BPMNElements bpmn, String id){
		ArrayList<SequenceFlow> flows = bpmn.getFlows();
		ArrayList<String> sourceNodes = new ArrayList<String>();
		for (SequenceFlow flow: flows){
			if(flow.getTarget().getId().equals(id)){
				
				String sourceNodeId = flow.getSource().getId();
				sourceNodes.add(sourceNodeId);
				
			}
			
		}
		return sourceNodes;
	}
	
	
	@SuppressWarnings("restriction")
	public void createBPMNFlow (XMLEventFactory eventFactory, XMLEventWriter eventWriter, BPMNElements bpmn) throws XMLStreamException{

		ArrayList<SequenceFlow> flows = bpmn.getFlows();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab1 = eventFactory.createDTD("\t");
		XMLEvent tab2 = eventFactory.createDTD("\t\t");
		// create Start node
		for (SequenceFlow flow: flows){
			StartElement sElement = eventFactory.createStartElement("","", "bpmndi:BPMNEdge");
			eventWriter.add(tab2);
			eventWriter.add(sElement);
			
			String edgeId = flow.getId()+"_gui";
			Attribute elementId = eventFactory.createAttribute("bpmnElement", flow.getId());
			eventWriter.add(elementId);
			Attribute shapeIdAttribute = eventFactory.createAttribute("id", edgeId);
			eventWriter.add(shapeIdAttribute);
			
			StartElement startFromPoint = eventFactory.createStartElement("", "", "omgdi:waypoint");
			eventWriter.add(startFromPoint);
			Double xValueFrom = flow.getSource().getX() + flow.getSource().getWeight();
			Attribute fromX = eventFactory.createAttribute("x", Double.toString(xValueFrom));
			eventWriter.add(fromX);
			Double yValueFrom = flow.getSource().getY() +(flow.getSource().getHeight()/2.0);
			Attribute fromY = eventFactory.createAttribute("y", Double.toString(yValueFrom));
			eventWriter.add(fromY);
			EndElement endFromPoint = eventFactory.createEndElement("", "", "omgdi:waypoint");
			eventWriter.add(endFromPoint);
			
			StartElement startToPoint = eventFactory.createStartElement("", "", "omgdi:waypoint");
			eventWriter.add(startToPoint);
			Double xValueTo = flow.getTarget().getX();
			Attribute toX = eventFactory.createAttribute("x", Double.toString(xValueTo));
			eventWriter.add(toX);
			Double yValueTo = flow.getTarget().getY() +(flow.getSource().getHeight()/2.0);
			Attribute toY = eventFactory.createAttribute("y", Double.toString(yValueTo));
			eventWriter.add(toY);
			EndElement endToPoint = eventFactory.createEndElement("", "", "omgdi:waypoint");
			eventWriter.add(endToPoint);
			
			EndElement eElement = eventFactory.createEndElement("", "", "bpmndi:BPMNEdge");
			eventWriter.add(tab2);
			eventWriter.add(eElement);
			eventWriter.add(end);
		}
		
	}
	
	
	@SuppressWarnings("restriction")
	public void createBPMNShapeTask (XMLEventFactory eventFactory, XMLEventWriter eventWriter, String processId, String id, double x, double y) throws XMLStreamException{

		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab1 = eventFactory.createDTD("\t");
		XMLEvent tab2 = eventFactory.createDTD("\t\t");
		// create Start node
		StartElement sElement = eventFactory.createStartElement("","", "bpmndi:BPMNShape");
		eventWriter.add(tab2);
		eventWriter.add(sElement);
		String spapeId = id+"_gui";
		Attribute elementId = eventFactory.createAttribute("bpmnElement", id);
		eventWriter.add(elementId);
		Attribute shapeIdAttribute = eventFactory.createAttribute("id", spapeId);
		eventWriter.add(shapeIdAttribute);
		
//        <bpmndi:BPMNLabel labelStyle="sid-bf141e34-0717-4fd7-b0dd-28002b421f44">
//		StartElement startLabel = eventFactory.createStartElement("","", "bpmndi:BPMNLabel");
//		eventWriter.add(startLabel);
//		String labelId = "style" + processId;
//		Attribute labelStyle = eventFactory.createAttribute("labelStyle", labelId);
//		eventWriter.add(labelStyle);

			StartElement startBound = eventFactory.createStartElement("","", "omgdc:Bounds");
			eventWriter.add(startBound);
			Attribute height = eventFactory.createAttribute("height", "60.0");
			eventWriter.add(height);
			Attribute width = eventFactory.createAttribute("width", "60.0");
			eventWriter.add(width);
			Attribute xCoordinate = eventFactory.createAttribute("x", Double.toString(x));
			eventWriter.add(xCoordinate);
			Attribute yCoordinate = eventFactory.createAttribute("y", Double.toString(y));
			eventWriter.add(yCoordinate);
			EndElement endBound = eventFactory.createEndElement("", "", "omgdc:Bounds");
			eventWriter.add(tab2);
			eventWriter.add(endBound);
		
		
//		EndElement endLabel = eventFactory.createEndElement("", "", "bpmndi:BPMNLabel");
//		eventWriter.add(tab2);
//		eventWriter.add(endLabel);

		// create End node
		EndElement eElement = eventFactory.createEndElement("", "", "bpmndi:BPMNShape");
		eventWriter.add(tab2);
		eventWriter.add(eElement);
		eventWriter.add(end);
	}

}