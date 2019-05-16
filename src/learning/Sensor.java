package learning;

import learning.perceptron.SparseVector;
import agents.AgentAction;
import map.GameState;

public class Sensor 
{
	protected StateSensor state_sensor;
	
	public Sensor(StateSensor ss)
	{
		this.state_sensor = ss;
	}
	public int size()
	{
		return(state_sensor.size()*4+1);
	}
	
	public SparseVector getVector(StateSensor ss, AgentAction action)
	{
		SparseVector vec1 = new SparseVector(size());
		SparseVector vec2 = ss.
		
		vec1.setValue(0,1.0);
		int pos = action.getAction() * state_sensor.size()+1;
		
		System.out.println("vec2");
		System.out.println(vec2);
		
		for(int f : vec2){
			vec1.setValue(pos+f,vec2.getValue(f));
		}
		
		System.out.println("vec1");
		System.out.println(vec1);
		
		return(vec1);
	}
}
