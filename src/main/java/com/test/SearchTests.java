package com.test;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * This class contains tests on various search aspects such as Queries, Filters, scoring boosting etc. in elastic search.
 * @author vigoel
 *
 */
public class SearchTests
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
    	
		/* ************ Test Data for Queries and Filters ************* */
		
//    	searchRequestBuilder.setQuery(QueryBuilders.termQuery("lastname", "goel"));   // try with value 'aalok'
    	
//		searchRequestBuilder.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("firstname", "vivek")));
		
//		searchRequestBuilder.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termsQuery("firstname", "vivek", "vikrant")));
		
//		searchRequestBuilder.setQuery(QueryBuilders.boolQuery().mustNot(QueryBuilders.termsQuery("firstname", "vivek", "vikrant")));
		
//		searchRequestBuilder.setQuery(QueryBuilders.prefixQuery("firstname", "vi"));
		
//		searchRequestBuilder.setQuery(QueryBuilders.wildcardQuery("email", "*singh*"));
		
//		searchRequestBuilder.setQuery(QueryBuilders.regexpQuery("firstname", "[a-zA-Z0-9]{5}"));
		
//		searchRequestBuilder.setQuery(new IdsQueryBuilder().addIds("2", "4", "6", "8"));
		
//		searchRequestBuilder.setQuery(QueryBuilders.rangeQuery("id").gte(2).lte(4));
		
//		searchRequestBuilder.setQuery(QueryBuilders.fuzzyQuery("firstname", "ive").fuzziness(Fuzziness.TWO));
		
//		searchRequestBuilder.setQuery(QueryBuilders.queryStringQuery("firstname=vivek OR lastname=singh"));
		
//		searchRequestBuilder.setQuery(QueryBuilders.queryStringQuery("+singh -8888888888"));

//		searchRequestBuilder.setQuery(QueryBuilders.matchQuery("lastname", "aalok singh"));
		
//		searchRequestBuilder.setQuery(QueryBuilders.multiMatchQuery("singh", "lastname", "firstname"));
		
//		searchRequestBuilder.setQuery(QueryBuilders.boolQuery()
//				.must(QueryBuilders.rangeQuery("id").gte(2).lte(8))
//				.should(QueryBuilders.wildcardQuery("firstname", "*i*"))
//				.should(QueryBuilders.wildcardQuery("lastname", "*i*"))
//				.minimumShouldMatch("1"));
		
//		searchRequestBuilder.setQuery(QueryBuilders.boolQuery().must(
//				QueryBuilders.filteredQuery(QueryBuilders.regexpQuery("firstname", "[a-zA-Z0-9]*"), 
//						FilterBuilders.andFilter(
//								FilterBuilders.inFilter("firstname", "tarun", "amit", "harjeet"), 
//								FilterBuilders.missingFilter("telephone")))));
				
		// Phrase Query
//		searchRequestBuilder.setQuery(QueryBuilders.nestedQuery("references.comments", 
//				QueryBuilders.matchPhraseQuery("references.comments.comment", "is a test")));
		
    	// Score Boosting when a specific condition is matched
//		searchRequestBuilder.setQuery(QueryBuilders.boolQuery()
//			.should(QueryBuilders.wildcardQuery("firstname", "*i*").boost(2.0f))
//			.should(QueryBuilders.wildcardQuery("lastname", "*i*")))
//			.addSort(SortBuilders.scoreSort());

    	// Boosting by each field
//		searchRequestBuilder.setQuery(QueryBuilders.multiMatchQuery("singh", "lastname", "firstname^0.5"));
    	
    	/* ************ End Test Data for Queries and Filters ************* */
    	
    	
		// Add Limits and Sorting to query 
//		searchRequestBuilder = searchRequestBuilder.setFrom(0).setSize(100).addSort(SortBuilders.fieldSort("firstname").order(SortOrder.ASC))
//				.setExplain(true);
						
		System.out.println("ElasticSearch Query:\n" + searchRequestBuilder.toString());
		
		response = searchRequestBuilder.execute().actionGet();

		System.out.println("Response:\n" + response.toString());
		
    }

}
