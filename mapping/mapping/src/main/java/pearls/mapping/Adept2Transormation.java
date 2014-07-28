package pearls.mapping;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Adept2Transormation {
	
	public static BPMNElements test (String path) throws SAXException, IOException, XPathExpressionException, ParserConfigurationException{	

		BPMNElements bpmn = new BPMNElements();
			try{
				
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document adeptXMLFile = dBuilder.parse(path);
				adeptXMLFile.getDocumentElement().normalize();
				NodeList adeptNodes = adeptXMLFile.getElementsByTagName("template");
				
				
				for (int i = 0; i < adeptNodes.getLength(); i++){
					Node node = adeptNodes.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE){
						Element elementsInAdept = (Element) node;
						String processId =elementsInAdept.getAttribute("id"); 
						bpmn.addProcessId(processId);
						getNodes(elementsInAdept, bpmn);
						getEgdes(elementsInAdept, bpmn);

					}
				}
			}catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bpmn;	
		}

	
	public static void getNodes (Element element, BPMNElements bpmn){
		NodeList nodesAdept = element.getElementsByTagName("node");
		for (int j=0; j < nodesAdept.getLength(); j++){
			Node taskAdept = nodesAdept.item(j);
			if(taskAdept.getNodeType() == Node.ELEMENT_NODE){
				Element taskElement = (Element) taskAdept;
				String taskId = taskElement.getAttribute("id");
				Element taskName = (Element) taskElement.getElementsByTagName("name").item(0);
				String taskNameString = taskName.getTextContent().replaceAll("\\s+","");
				TaskBpmn taskBPMN = new TaskBpmn(taskId, taskNameString);
				String nodeType = checkStructuralNodeData (element,taskId );
				taskBPMN.setType(nodeType.replaceAll("\\s+",""));

					
				bpmn.addTask(taskBPMN);
				
			}
		}
	}
	
	public static void getEgdes (Element element, BPMNElements bpmn){
		NodeList edgeAdept = element.getElementsByTagName("edge");
		for(int j= 0; j< edgeAdept.getLength(); j++){
			Node flowAdept = edgeAdept.item(j);
			if (flowAdept.getNodeType() == Node.ELEMENT_NODE){
				Element flowElement = (Element) flowAdept;
				String sourceNodeId = flowElement.getAttribute("sourceNodeID");
				TaskBpmn sourceTask = bpmn.findTaskById(sourceNodeId);
				String destinationNodeId = flowElement.getAttribute("destinationNodeID");
				TaskBpmn targetTask = bpmn.findTaskById(destinationNodeId);
				SequenceFlow flow = new SequenceFlow();
				flow.addId(Integer.toString(j));
				flow.addSourceRef(sourceTask);
				flow.addTargetRef(targetTask);
				bpmn.addFlow(flow);
				
				NodeList edgeValueNodes = flowElement.getElementsByTagName("edgeCode");
				Node value = edgeValueNodes.item(0);
				if (value!=null){
					Element valueElement = (Element) value;
					String valueEdge = valueElement.getTextContent().replaceAll("\\s+","");
					flow.addName(valueEdge);
				}
				
			}
		}
	}
	
	
	
	public static String checkStructuralNodeData (Element element, String nodeId){
		NodeList nodesDataAdept = element.getElementsByTagName("structuralNodeData");
		for (int j=0; j < nodesDataAdept.getLength(); j++){
			Node taskAdept = nodesDataAdept.item(j);
			if(taskAdept.getNodeType() == Node.ELEMENT_NODE){
				Element taskElement = (Element) taskAdept;
				String id = taskElement.getAttribute("nodeID");
				if (id.equals(nodeId)){
					NodeList nodesAdept = taskElement.getElementsByTagName("type");
					for (int i = 0; i < nodesAdept.getLength(); i++){
						Node nodeType = nodesAdept.item(i);
						if(nodeType.getNodeType() == Node.ELEMENT_NODE){
							Element typeElement = (Element) nodeType;
							String type = typeElement.getTextContent();
							return type;
							
						}
					}	
				}
			}
		}
		return null;
	}
}


