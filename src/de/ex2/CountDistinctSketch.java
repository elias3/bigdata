package de.ex2;

import de.fraunhofer.iais.kd.livlab.bda.countdistinct.Sketch;

public class CountDistinctSketch {

	private static final int MAX_HASH_SIZE = 1024; // otherwise the bit set
	// calculation will be very
	// inefficient
	private final Sketch sketch;

	public CountDistinctSketch() {
		sketch = new Sketch(0);
	}

	public void addUser(String username) {
		sketch.setBit(Math.abs(username.hashCode()) % MAX_HASH_SIZE);
	}

	public int getEstimate() {
		int m = MAX_HASH_SIZE;// sketch.getSketch().length();
		int us = m - sketch.cardinality();
		// System.out.println("m  = " + m + " us = " + us);
		double result = -((double) m) * Math.log((double) us / (double) m);

		return (int) result;
	}

	public Sketch getSketch() {
		return sketch;
	}

	public CountDistinctSketch(Sketch sketch) {
		this.sketch = sketch;
	}

	public double distanceTo(CountDistinctSketch other) {

		int distinctA = this.getEstimate();
		int distinctB = other.getEstimate();
		int distinctAB = new CountDistinctSketch(this.sketch.orSketch(other
				.getSketch())).getEstimate();

		return (double) (distinctA + distinctB - distinctAB)
				/ (double) distinctAB;
	}

	public static void main(String[] args) {
		CountDistinctSketch countDistance;
		countDistance = new CountDistinctSketch();
		countDistance.addUser("a");
		countDistance.addUser("b");
		countDistance.addUser("c");
		countDistance.addUser("d");
		countDistance.addUser("e");
		countDistance.addUser("f");
		countDistance.addUser("g");
		countDistance.addUser("h");
		countDistance.addUser("i");
		countDistance.addUser("j");
		countDistance.addUser("k");
		countDistance.addUser("l");
		countDistance.addUser("m");
		countDistance.addUser("n");
		countDistance.addUser("n");
		countDistance.addUser("n");

		System.out.println("Estimate = " + countDistance.getEstimate());
	}
}
