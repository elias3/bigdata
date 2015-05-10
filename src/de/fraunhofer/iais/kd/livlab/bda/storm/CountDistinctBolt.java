package de.fraunhofer.iais.kd.livlab.bda.storm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import de.ex2.CountDistinctClusterModel;
import de.ex2.CountDistinctContainer;
import de.fraunhofer.iais.kd.livlab.bda.countdistinct.detector.CountDistinctDetecor;

/**
 *
 *
 */
public class CountDistinctBolt extends BaseRichBolt {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	OutputCollector collector;
	private CountDistinctDetecor detector;
	private ConcurrentHashMap<String, CountDistinctContainer> sketchContainerMap;
	private CountDistinctClusterModel clusterModel;

	@Override
	public void prepare(Map conf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		detector = new CountDistinctDetecor();
		sketchContainerMap = new ConcurrentHashMap<String, CountDistinctContainer>();
		clusterModel = new CountDistinctClusterModel();
	}

	@Override
	public void execute(Tuple tuple) {

		String userid = tuple.getStringByField("userid");
		String artid = tuple.getStringByField("artid");
		String artname = tuple.getStringByField("artname");
		CountDistinctContainer container = null;

		String[] result = detector.process(userid, artid, artname);

		if (result != null) {

			if (sketchContainerMap.get(result[1]) == null) {
				// System.out.println("A");
				container = new CountDistinctContainer(result[1]);
				sketchContainerMap.put(result[1], container);
			} else {
				// System.out.println("B");
				container = sketchContainerMap.get(result[1]);
			}
			container.addUser(result[0]);

			if (container.isIncreased()) {
				System.out.println("Artname:" + result[1] + " nofusers:"
						+ container.getCount() + " closest_cluster"
						+ clusterModel.getClosest(container.getSketch()));

				// System.out.println("Userid:" + result[0] + " artname:"
				// + result[1]);
			}

		}

		collector.ack(tuple);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("artname", "clusterid"));

	}

}
