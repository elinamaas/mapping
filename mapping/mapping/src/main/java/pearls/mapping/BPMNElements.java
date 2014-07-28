package pearls.mapping;

import java.util.ArrayList;

public class BPMNElements {
	String processId;
//	String startEventId;
//	String endEventId;
	ArrayList <TaskBpmn> tasks;
	ArrayList<SequenceFlow> flows;
	
	public BPMNElements (){

		tasks = new ArrayList <TaskBpmn>();
		flows = new ArrayList<SequenceFlow>();
	}
	
	public void addTask (TaskBpmn task){
		tasks.add(task);
	}
	
	public void addProcessId (String id){
		processId = id;
	}
	
	public void addFlow (SequenceFlow flow){
		flows.add(flow);
	}
	
	public String getProcessId (){
		return processId;
	}
	
	public ArrayList<TaskBpmn> getTasks(){
		return tasks;
	}
	
	public ArrayList<SequenceFlow> getFlows(){
		return flows;
	}
	
	public TaskBpmn findTaskById (String id){
		TaskBpmn currentTask = null;
		for (TaskBpmn task : tasks){
			if (task.getId().equals(id))
				return task;
		}
		return currentTask;
	}
	
	public void print (){
		System.out.println("tasks: ");
		for (TaskBpmn t : tasks){
			System.out.println(t.id + "  " + t.type);
		}
		System.out.println("Flows: ");
		for (SequenceFlow s : flows){
			System.out.println(s.id + " " + s.sourceRef + " " + s.targetRef);
		}
		
	}

}
