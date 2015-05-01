package de.ex1;

public class UserSetMatrix extends UserSet {

	final static int SIZE_OF_USER_SET = 1000;

	private boolean userVector[] = null;

	public boolean[] getUserVector() {
		return userVector;
	}

	public UserSetMatrix() {
		userVector = new boolean[SIZE_OF_USER_SET];
	}

	@Override
	public void add(String username) {
		userVector[Integer.parseInt(username.split("_")[1]) - 1] = true;
		userSet.add(username);
	}

	@Override
	public String toString() {

		String str = "";

		int i = 0;
		for (; i < SIZE_OF_USER_SET - 1; i++) {
			str += (userVector[i] ? "1" : "0") + ",";
		}
		str += userVector[i] ? "1" : "0";

		return str;
	}

	public double distanceTo(String vector) {
		String[] vec = vector.split(" ");
		int unionSize = 0;
		int intersectionSize = 0;
		for (int i = 0; i < vec.length - 1; i++) {
			if (vec[i].compareTo("1") == 0 && this.userVector[i] == true) {
				intersectionSize++;
			}
			if (vec[i].compareTo("1") == 0 || this.userVector[i]) {
				unionSize++;
			}
		}
		return 1.0 - (double) intersectionSize / unionSize;
	}
}
