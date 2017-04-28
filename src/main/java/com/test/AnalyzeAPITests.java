package com.test;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * This class contains tests on various aspects of analyze API in elastic search.
 * @author vigoel
 *
 */
public class AnalyzeAPITests
{
	private static Client client;
    public static void main( String[] args ) throws IOException
    {
    	Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "tc_cluster").build();
    	client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
    	
    	// STANDARD
    	analyzeText("share your experience with NoSql & big data technologies", "standard", null, null, null);
    	
    	// WHITESPACE
    	analyzeText("share your experience with NoSql & big data technologies", "whitespace", null, null, null);
    	
    	// STOP
    	analyzeText("share your experience with NoSql & big data technologies", "stop", null, null, null);
    	
    	// KEYWORD
    	analyzeText("share your experience with NoSql & big data technologies", "keyword", null, null, null);
    	
    	// PATTERN
    	analyzeText("breaking.-.some.-.text", "pattern", "pattern1", null, null);
    	
    	// UAX URL EMAIL
    	analyzeText("john.smith@example.com http://example.com?q=bar", null, "uax_url_email", null, null);
    	
    	// PATH HIERARCHY
    	analyzeText("/usr/local/var/log/elasticsearch.log", null, "path_hierarchy", null, null);
    	
    	// Custom analyzer
    	analyzeText("testing a paragraph including an and the stopwords", "stop1", null, null, "stopwords");
    	
    	// Reverse Token Filter
    	analyzeText("Reverse token filter", null, "standard", "reverse", null);
    	
    	// Length Token Filter (First create filter using curl and delete after testing)
//    	analyzeText("a small word and a longerword", null, "standard", "my-length-filter", "length");
    	
    	// Unique Token Filter
    	analyzeText("first second first second third", null, "standard", "unique", null);
    	
    	// UNICODE Token Filter
    	analyzeText("ünicode", null, "standard", "asciifolding", null);
    	
    	// Ngrams Filter
    	analyzeText("spaghetti", "ng1", null, null, "ng");
    	
    	// Shingles Filter
    	analyzeText("foo bar baz", "shingle1", null, null, "shingle");
    }

	private static void analyzeText(String input, String analyzer, String tokenizer, String tokenFilter, String index) throws IOException {
		System.out.println("=======================================");
		AnalyzeRequest request = (new AnalyzeRequest(index, input));
		System.out.println("Request Text:"+request.text().toString());
		if (analyzer!=null) {
			System.out.println("Analyzer:"+analyzer);
			request.analyzer(analyzer);
		}
		if (tokenizer!=null) {
			System.out.println("Tokenizer:"+tokenizer);
			request.tokenizer(tokenizer);
		}
		if (tokenFilter!=null) {
			System.out.println("Token Filter:"+tokenFilter);
			request.tokenFilters(tokenFilter);
		}
    	AnalyzeResponse response = client.admin().indices().analyze(request).actionGet();
//    	String responseString = response.toXContent(jsonBuilder().startObject(), ToXContent.EMPTY_PARAMS).prettyPrint().string();
//    	JsonParser parser = new JsonParser();
//      JsonObject json = parser.parse(responseString).getAsJsonObject();
//    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    	String jsonOutput = gson.toJson(json);
//    	System.out.println("JSON Response:\n"+jsonOutput);
    	List<AnalyzeResponse.AnalyzeToken> tokens = response.getTokens();
    	System.out.println("Tokens:");
    	for (AnalyzeResponse.AnalyzeToken token : tokens)
    	{
    	    System.out.println(token.getTerm().toString());
    	}
	}
}
