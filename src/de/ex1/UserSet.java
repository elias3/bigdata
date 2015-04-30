package de.ex1;

import java.util.HashSet;
import java.util.Set;

public class UserSet {

	private Set<String> userSet = null;

	public UserSet() {
		userSet = new HashSet<String>();
	}

	public void add(String username) {
		userSet.add(username);
	}

	public double distanceTo(UserSet other) {
		int unionSize = other.getSet().size() + this.userSet.size();
		int intersectionSize = 0;

		if (unionSize == 0) {
			return 0.0;
		}

		for (String x : userSet) {
			if (other.getSet().contains(x)) {
				intersectionSize++;
				unionSize--;
			}
		}

		return (double) intersectionSize / unionSize;
	}

	public Set<String> getSet() {
		return userSet;
	}

	@Override
	public String toString() {

		int i = 0;
		String str = "";

		str += "[";

		for (String user : this.userSet) {
			if (i < this.userSet.size() - 1) {
				str += user + ", ";
			} else {
				str += user;
			}
			i++;
		}

		str += "]";

		return str;
	}

	public static void main(String[] args) {
		UserSet setA, setB;
		setA = new UserSet();
		setB = new UserSet();
		setA.add("Nedal");
		setA.add("Elias");
		setA.add("Jool");
		setA.add("Huebsch");
		setB.add("Nedal");
		setB.add("Zebra");

		System.out.println("Distance from A to B is " + setA.distanceTo(setB));
	}
}
