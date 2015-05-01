package de.fraunhofer.iais.kd.livlab.bda.clustermodel;

import de.ex1.UserSetMatrix;

public class UserSetClusterModel {
	private ClusterModel model = null;

	public UserSetClusterModel() {
		model = ClusterModelFactory
				.readFromCsvResource("resources/centers_1000_iter_2_50.csv");
	}

	public String findClosestCluster(UserSetMatrix userset) {

		double smallestDistance = 1.0;
		String smallestKey = "";
		double distance;
		for (String key : model.getKeys()) {
			String vector = model.get(key);
			// System.out.println(vector);
			distance = userset.distanceTo(vector);
			// System.out.println(distance);
			if (distance < smallestDistance) {
				smallestKey = key;
				smallestDistance = distance;
			}

			// for (String value2 : values) {

			// if (value2.compareTo("1") == 0) {
			// String num = Integer.toString(i);
			// String user = "user_";
			// int numZeros = 6 - num.length();
			// for (int j = 0; j < numZeros; j++) {
			// user += "0";
			// }
			// user += num;
			// set.add(user);
			// }
			// userset.distanceTo(set);
			// i++;
			// }
		}
		return smallestKey;
	}

	public static void main(String[] args) {
		UserSetClusterModel clusterModel = new UserSetClusterModel();
		UserSetMatrix userset = new UserSetMatrix();
		userset.add("user_001000");
		// userset.add("user_000017");
		userset.add("user_000018");
		// userset.add("user_000019");

		System.out.println(clusterModel.findClosestCluster(userset));
	}
}
