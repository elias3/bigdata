package de.fhg.iais.kd.hadoop.recommender.flows;

import cascading.flow.FlowProcess;
import cascading.operation.Aggregator;
import cascading.operation.AggregatorCall;
import cascading.operation.BaseOperation;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntry;
import de.ex1.UserSetMatrix;
import de.fraunhofer.iais.kd.livlab.bda.clustermodel.UserSetClusterModel;

public class FindClosestClusterAggregator extends
BaseOperation<FindClosestClusterAggregator.Context> implements
Aggregator<FindClosestClusterAggregator.Context> {

	public FindClosestClusterAggregator(Fields fields) {
		super(1, fields); // one input field, output fields named as in
		// constructor call
	}

	public class Context {
		private UserSetMatrix set = null;

		public Context() {
			set = new UserSetMatrix();
		}

		public UserSetMatrix getUserSet() {
			return set;
		}
	}

	@Override
	public void start(FlowProcess flowProcess,
			AggregatorCall<Context> aggregatorCall) {

		Context context = new Context();
		aggregatorCall.setContext(context);
	}

	@Override
	public void aggregate(FlowProcess flowProcess,
			AggregatorCall<Context> aggregatorCall) {
		TupleEntry arguments = aggregatorCall.getArguments();
		Context context = aggregatorCall.getContext();
		context.getUserSet().add(arguments.getString("uid"));
	}

	@Override
	public void complete(FlowProcess flowProcess,
			AggregatorCall<Context> aggregatorCall) {
		Context context = aggregatorCall.getContext();
		Tuple result = new Tuple();
		UserSetClusterModel clusterModel = new UserSetClusterModel();
		String closest = clusterModel.findClosestCluster(context.getUserSet());
		if (closest.compareTo("") == 0) {
			result.add("No closest cluster !");
		} else {
			result.add(closest);
		}

		aggregatorCall.getOutputCollector().add(result);
	}

}
