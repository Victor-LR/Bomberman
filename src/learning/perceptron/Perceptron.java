package learning.perceptron;


public class Perceptron implements BinaryClassifier
{
	protected SparseVector parameters;
	protected int nb_iterations;
	protected double epsilon;
	
	
	public Perceptron(double epsilon, int nb_iterations, SparseVector param)
	{
		this.epsilon=epsilon;
		this.nb_iterations=nb_iterations;
		this.parameters=param;		
	}
	
	
	public Perceptron(double epsilon, int taille){
		this.epsilon=epsilon;
		parameters=new SparseVector(taille*taille*3);
		for(int i=0;i<taille*taille*3;i++){
			parameters.setValue(i, Math.random()*2-1);
		}	
	}
	
	public String toString(){
		return epsilon+"   "+parameters.toString();
	}
	
	
	@Override
	public double getScore(SparseVector v) 
	{
		return parameters.computeDOT(v);
	}

	
	@Override
	public void train(LabeledSet training_set) 
	{
		//System.out.println("éééé" +training_set.size());
		for(int i=0;i<nb_iterations ;i++){
			
			for (int j=0;j<training_set.size();j++){
				
				SparseVector v=training_set.getVector(j);
				double reel=training_set.getLabel(j);
				double predicted=getScore(v);
				double error=reel -predicted;
				parameters.addVector(v,epsilon*error);
				
				}
		}
	}
	
	
	public void setNb_iteration(int val){
		nb_iterations=val;
	}
	
	public void bruiter(double val){
		for (int i=0;i<parameters.size;i++){
			parameters.setValue(i, parameters.getValue(i)+val);
		}
	}

	
	public Perceptron tPlusUn(LabeledSet training_set) {
		Perceptron t_Plus = new Perceptron(this.epsilon,training_set.size_vectors);
		for (int i = 0; i < this.parameters.size; i++) {
			double max = Double.NEGATIVE_INFINITY;
			for (int j = 0; j < training_set.size(); j++)
				if (training_set.getVector(j).getValue(i) > max)
					max = training_set.getVector(j).getValue(i);
			t_Plus.parameters.setValue(i,Math.max(max, this.parameters.getValue(i)));
		}
		return t_Plus;
	}

}