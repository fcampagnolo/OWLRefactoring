package Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JCheckBox;

public class createListCheckBox {

	static public Collection<JCheckBox> createListCheckBoxOwlClass(Collection<String> listName){
		
		Collection<JCheckBox> lCB = new ArrayList<>();
		
		for (String s : listName) {
			JCheckBox chb = new JCheckBox(s);
			lCB.add(chb);
		}
		
		return lCB;
	}
	
}

