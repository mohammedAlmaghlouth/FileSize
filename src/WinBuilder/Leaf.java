package WinBuilder;

public class Leaf extends Component {
	
	public Leaf(String name, long size) {
		super(name);
		this.size = size;
	}

	@Override
	public void Add(Component component) {
		System.out.println("Error");	
	}

	@Override
	public void Display(int depth) {
		for(int iteration=0; iteration<depth ; iteration++) {
			System.out.print(" ");
		}
		System.out.println(name + " " + size);
	}
}