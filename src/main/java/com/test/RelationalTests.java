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
 * This class contains tests on nested and parent/child relationships in elastic search.
 * @author vigoel
 *
 */
public class RelationalTests
{
	private static Client client;
	private static final String index = "contacts";
	private static final String type = "contact";
	
    public static void main( String[] args ) throws IOException
    {
		SearchResponse response = null;
		
    	Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "tc_cluster").build();
    	client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
    	SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
    	
		/* Nested Relationship Query */
		searchRequestBuilder.setQuery(QueryBuilders.nestedQuery("references.comments", 
				QueryBuilders.termQuery("references.comments.comment", "test")));
						
		System.out.println("ElasticSearch Query:\n" + searchRequestBuilder.toString());
		
		response = searchRequestBuilder.execute().actionGet();

		System.out.println("Response:\n" + response.toString());
				
    }

}
