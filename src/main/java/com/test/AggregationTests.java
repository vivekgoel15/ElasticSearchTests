package com.test;

import java.io.IOException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * This class contains tests on aggregation operations in elastic search.
 * @author vigoel
 *
 */
public class AggregationTests
{
	private static Client client;
	private static final String index = "users";
	private static final String type = "user";
	
    public static void main( String[] args ) throws IOException
    {
		SearchResponse response = null;
		
    	Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "tc_cluster").build();
    	client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
    	SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
    	searchRequestBuilder.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery()));
    	
    	// Aggregations of fields by AGE
//    	searchRequestBuilder.addAggregation(AggregationBuilders.terms("group_by_age").field("age"));
    	
    	// AVG
//    	searchRequestBuilder.addAggregation(AggregationBuilders.avg("avg_salary").field("salary"));
    	
    	// MAX
//    	searchRequestBuilder.addAggregation(AggregationBuilders.max("max_salary").field("salary"));
    	
    	// Statistics
//    	searchRequestBuilder.addAggregation(AggregationBuilders.stats("stats_salary").field("salary"));
    	
    	// Extended Statistics
//    	searchRequestBuilder.addAggregation(AggregationBuilders.extendedStats("extended_stats_salary").field("salary"));
    	
    	// Range
//    	searchRequestBuilder.addAggregation(AggregationBuilders.range("range").addRange(10000, 50000).field("salary"));

    	// Include terms
//    	searchRequestBuilder.addAggregation(AggregationBuilders.terms("group_by_name").include("[a-zA-Z]{3}").field("name"));
    	
    	// HISTOGRAM AGGREGATION
//    	searchRequestBuilder.addAggregation(AggregationBuilders.histogram("histogram").field("salary").interval(10000));
    	
    	// Multi-bucket aggregations
//    	searchRequestBuilder.addAggregation(AggregationBuilders.range("range").addRange(10000, 50000).field("salary")
//    			.subAggregation(AggregationBuilders.terms("group_by_age").field("age")));

    	searchRequestBuilder = searchRequestBuilder.setFrom(0).setSize(100);
//				.setExplain(true);
		
		System.out.println("ElasticSearch Query: \n" + searchRequestBuilder.toString());
		
		response = searchRequestBuilder.execute().actionGet();

		System.out.println("Response:" + response.toString());
    }

}
