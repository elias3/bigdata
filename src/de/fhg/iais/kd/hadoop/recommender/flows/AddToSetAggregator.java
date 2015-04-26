package de.fhg.iais.kd.hadoop.recommender.flows;

import cascading.flow.FlowProcess;
import cascading.operation.Aggregator;
import cascading.operation.AggregatorCall;
import cascading.operation.BaseOperation;
import cascading.tuple.Fields;
import cascading.tuple.TupleEntry;
import de.ex1.UserSet;

public class AddToSetAggregator extends
BaseOperation<AddToSetAggregator.Context> implements
Aggregator<AddToSetAggregator.Context> {

	public AddToSetAggregator(Fields fields) {
		super(1, fields); // one input field, output fields named as in
		// constructor call
	}

	public class Context {
		private UserSet set = null;

		public Context() {
			set = new UserSet();
		}

		public UserSet getSet() {
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
		context.getSet().add(arguments.getString("uid"));
	}

	@Override
	public void complete(FlowProcess flowProcess,
			AggregatorCall<Context> aggregatorCall) {
		// TODO Auto-generated method stub

	}

}
