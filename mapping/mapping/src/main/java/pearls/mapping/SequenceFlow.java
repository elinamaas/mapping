package pearls.mapping;

import net.sf.saxon.expr.instruct.TerminationException;

public class SequenceFlow {
	String id;
	String name;
	String type;
	
	TaskBpmn sourceRef;
	TaskBpmn targetRef;
	
	public SequenceFlow (){
		name = "";
	}
	
	public void addName (String edgeValue){
		name = edgeValue;
	}
	
	public String getName (){
		return name;
	}
	
	public void addId (String flowId){
		id =flowId;
	}
	
	public void addSourceRef (TaskBpmn source){
		sourceRef= source;
	}
	
	public void addTargetRef (TaskBpmn target){
		targetRef = target;
	}
	
	public String getId(){
		return id;
	}
	
	public TaskBpmn getSource(){
		return sourceRef;
	}
	
	public TaskBpmn getTarget(){
		return targetRef;
	}
	
	public void setType (String typeEdge){
		this.type = typeEdge;
	}
	
	public String getType (){
		return type;
	}

}
