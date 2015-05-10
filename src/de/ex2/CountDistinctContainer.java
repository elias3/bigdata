package de.ex2;

public class CountDistinctContainer {

	private final String artistName;
	private final CountDistinctSketch sketch;
	private boolean isIncreased = false;
	private int lastCount = 0;

	public CountDistinctContainer(String artName) {
		this.artistName = artName;
		sketch = new CountDistinctSketch();
	}

	public void addUser(String username) {
		int estimateBefore = lastCount;
		sketch.addUser(username);
		int estimateAfter = lastCount = sketch.getEstimate();

		if (estimateAfter > estimateBefore) {
			isIncreased = true;
		} else {
			isIncreased = false;
		}
	}

	public int getCount() {
		return lastCount;
	}

	public boolean isIncreased() {
		return isIncreased;
	}

	public CountDistinctSketch getSketch() {
		return sketch;
	}

	public String getArtname() {
		return artistName;
	}

	public static void main(String[] args) {
		CountDistinctContainer container = new CountDistinctContainer("Jool");
		container.addUser("user0");
		if (container.isIncreased()) {
			System.out.println("Increased ! Count = " + container.getCount());
		}

	}
}
