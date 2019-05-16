package learning.perceptron;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

	public class SparseVector implements Iterable<Integer>, Serializable
	{
		protected HashMap<Integer,Double> values;
		protected int size;
				
		public SparseVector(int size)
		{
			this.size=size;
			values=new HashMap<Integer,Double>();
		}
		
		public String toString()
		{
			StringBuffer sb=new StringBuffer();
			for(int f:this)
			{
				sb.append(f);
				sb.append(":");
				sb.append(getValue(f));
				sb.append(" ");
			}
			return(sb.toString());
		}
		
		public void setValue(int i,double v)
		{
			assert ((i>0) && (i<size));
			if (v==0)
			{
				if (values.containsKey(i))
					values.remove(i);			
			}
			else
			{
				values.put(i,v);
			}
		}
		
		public double getValue(int i)
		{
			if (values.containsKey(i))
				return(values.get(i));
			return(0.0);
		}

		public double computeDOT(SparseVector v)
		{
			double score=0;
			for(Integer f:v)
			{
				score+=v.getValue(f)*getValue(f);
			}
			return(score);
		}
		
		/**
		 * Permet d'it�rer sur les index des valeurs non nulles du vecteur
		 */
		public Iterator<Integer> iterator()
		{
			return(values.keySet().iterator());
		}
		
		/**
		 * add
		 */
		public void addVector(SparseVector v2,double scalaire)
		{
			for(int f:v2)
			{
				double v=getValue(f);
				setValue(f,v+v2.getValue(f)*scalaire);
			}
		}
		
		public int size() {return(size);}

		public void setSize(int size_vector) {
			size=size_vector;
		}
		
	}
