package es.us.isa.rester.inputs.fixed;

import java.util.Iterator;
import java.util.List;

import es.us.isa.rester.inputs.ITestDataGenerator;

/** Sequential iterator on a list of input values of type <T>
 * 
 * @author Sergio Segura
 *
 */
public class InputValueIterator<T> implements ITestDataGenerator{ 

    private List<T> values;
    private Iterator<T> iterator;
    
    public InputValueIterator(List<T> values) {
    	this.values = values;
    	this.iterator = values.iterator();
    }

	public Object nextValue() {
		Object value=null;
		
		if (iterator.hasNext())
			value= iterator.next();
		
		return value;
	}
	
	public String nextValueAsString() {
		return nextValue().toString();
	} 
	
	public void resetIterator() {
		iterator = values.iterator();
	}   
}
