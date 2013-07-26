package com.oxfordmathcenter.breadboards;

import java.util.ArrayList;

import javax.swing.MutableComboBoxModel;
import javax.swing.event.ListDataListener;

public class BreadboardComboBoxModel implements MutableComboBoxModel {

	int selectedIndex_;
	ArrayList<String> items_;
	
	public BreadboardComboBoxModel(String[] strings) {
		selectedIndex_ = 0;  
		items_ = new ArrayList<String>();
		for (int i=0; i < strings.length; i++) {
			items_.add(strings[i]);
		}
	}
	
	public Object getSelectedItem() {
		return items_.get(selectedIndex_);
	}

	@Override
	public void setSelectedItem(Object string) {
		selectedIndex_ = items_.indexOf((String) string);
	}

	public void addListDataListener(ListDataListener arg0) {
		//TODO -- add this...
	}

	public Object getElementAt(int index) {
		return items_.get(index);
	}

	public int getSize() {
		return items_.size();
	}

	public void removeListDataListener(ListDataListener arg0) {
		// TODO -- add this...
	}

	public void addElement(Object string) {
		items_.add((String) string);
	}

	public void insertElementAt(Object string, int index) {
		items_.add(index, (String) string);
	}

	public void removeElement(Object string) {
		items_.remove(string);
	}

	public void removeElementAt(int index) {
		items_.remove(index);
	}
	
}