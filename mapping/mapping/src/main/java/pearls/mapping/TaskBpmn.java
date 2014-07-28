package pearls.mapping;

public class TaskBpmn {
	String id;
	String type;
	String name;
	Double weight;
	Double height;
	Double xCoordinate;
	Double yCoordinate;
//	boolean start;
//	boolean end;

	
	public TaskBpmn(String taskId, String taskName) {
		id = taskId;
		name = taskName;
		weight = null;
		height = null;
		xCoordinate = null;
		yCoordinate = null;
//		start = false;
//		end = false;
		
	}
	
	public String getId(){
		return id;
	}
	
	public String getType(){
		return type;
	}
	
	public String getName(){
		return name;
	}
	
	public void setType (String typeNode){
		this.type=typeNode;
	}
	public void setBounds (Double w, Double h, Double x, Double y){
		this.height = h;
		this.weight = w;
		this.xCoordinate = x;
		this.yCoordinate = y;
		
	}
	
//	public void setStartTask (){
//		this.start = true;
//	}
//	
//	public void setEndTask(){
//		this.end = true;
//	}
	
//	public boolean getStartTask(){
//		return this.start;
//	}
//	
//	public boolean getEndTask(){
//		return this.end;
//	}
	
	public Double getWeight(){
		return this.weight;
	}
	
	public Double getHeight (){
		return this.height;
	}
	
	public Double getX(){
		return this.xCoordinate;
	}
	
	public Double getY(){
		return this.yCoordinate;
	}
}
