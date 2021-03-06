package de.fhg.iais.kd.hadoop.recommender;

import cascading.flow.Flow;
import de.fhg.iais.kd.hadoop.recommender.flows.BuildInteractionMatrix;
import de.fraunhofer.iais.kd.livlab.bda.config.BdaConstants;

public class batchRecommenderTask3 {

	public static void main(String[] args) {

		// String infile =
		// "/home/livlab/last.fm-data/lastfm-dataset-1K/sample.tsv";
		String infile = BdaConstants.SAMPLE_FILE;
		String workDir = "recommender_flowtest/";

		Flow utilMatrix = BuildInteractionMatrix.getInteractionMatrixFlowTask3(
				infile, workDir);
		utilMatrix.complete();

	}
}
