package de.ex2;

import de.fraunhofer.iais.kd.livlab.bda.clustermodel.ClusterModel;
import de.fraunhofer.iais.kd.livlab.bda.clustermodel.ClusterModelFactory;

public class CountDistinctClusterModel {

	public String getClosest(CountDistinctSketch sketch) {
		double minimalDistance = 1.0;
		String closestString = "";
		ClusterModel model = ClusterModelFactory
				.readFromCsvResource("resources/centers_1000_iter_2_50.csv");
		for (String key : model.getKeys()) {
			String vector = model.get(key);

			if (vector.length() == 2001) { // csv model 0,0,1,1, ... for 1000
				// users (including user_000)
				CountDistinctSketch otherSketch = new CountDistinctSketch();

				for (int id = 1; id <= 1000; id++) {

					if (vector.charAt(2 * id) == '1') {
						String uid = "user_" + String.format("%06d", id); // transform
						// id 1
						// to
						// user_001
						// etc.
						otherSketch.addUser(uid);
					}
				}
				double currentDistance = sketch.distanceTo(otherSketch);
				if (minimalDistance > currentDistance) {
					minimalDistance = currentDistance;
					closestString = key;
				}
			}

		}

		return closestString;
	}

	public static void main(String[] args) {
		CountDistinctClusterModel model = new CountDistinctClusterModel();
		CountDistinctSketch sketch = new CountDistinctSketch();
		sketch.addUser("user_001000");
		// sketch.addUser("user_000017");
		sketch.addUser("user_000018");
		// sketch.addUser("user_000019");

		System.out.println(model.getClosest(sketch));
	}

}
