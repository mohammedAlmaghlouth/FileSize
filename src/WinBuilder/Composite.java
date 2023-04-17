package WinBuilder;

import java.util.ArrayList;

public class Composite extends Component{
	
	private ArrayList<Component> allChildrens = new ArrayList<Component>();
	
	public Composite(String name) {
		super(name);
	}
	
	public ArrayList<Component> retrieveChildrens(){
		return allChildrens;
	}
	
	@Override
	public void Add(Component c) {
		allChildrens.add(c);
	}
	
	@Override
	public void Display(int depth) {
		for(int iteration=0; iteration < depth ; iteration++) {System.out.print(" ");}
		System.out.println("--" + name);

		for(Component component : allChildrens) {
			component.Display(depth + 3);
		}
		
	}
}