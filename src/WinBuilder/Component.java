package WinBuilder;

public abstract class Component {
	
	protected String name;	
	protected long size;
	
	public Component(String name) {
		this.name = name;
	}

	public abstract void Add(Component component);
	public abstract void Display(int depth);	
}