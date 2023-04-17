package WinBuilder;

import javax.swing.tree.DefaultMutableTreeNode;

public class Adapter {
	public Adapter(){}
	public static DefaultMutableTreeNode ToJTree(Composite root) {
		DefaultMutableTreeNode rootValue = new DefaultMutableTreeNode(root.name);
		
		
		for(Component eachComponent : root.retrieveChildrens()) {
			recursive(rootValue, eachComponent);
		}
		
		
		return rootValue;
	}

	private static void recursive(DefaultMutableTreeNode r_val, Component root) {
		if(root instanceof Leaf) {
			return  ;
		}
		else if(root instanceof Composite) {
			DefaultMutableTreeNode val = new DefaultMutableTreeNode(root.name);
			
			r_val.add(val);
			for(Component component : ((Composite) root).retrieveChildrens()) {
				recursive(val,component);
			}
		}
	}
}
