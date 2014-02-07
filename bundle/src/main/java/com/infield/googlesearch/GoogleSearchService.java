/**
 * Copyright Infield Design 2014
 * 
 * @author Gaetan Marmasse <gaetan@infielddesign.com>
 */
package com.infield.googlesearch;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.osgi.framework.Constants;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.CustomsearchRequestInitializer;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import com.google.api.services.customsearch.model.Search.SearchInformation;
import com.infield.googlesearch.model.ResultItem;
import com.infield.googlesearch.model.ResultList;

@Component(immediate = true, metatype = true)
@Service(GoogleSearchService.class)
@Properties({
	@Property(name = Constants.SERVICE_VENDOR, value = "Infield"),
	@Property(name = Constants.SERVICE_DESCRIPTION, value = "Google Custom Search service")
})
public class GoogleSearchService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	//TODO: set servlet path dynamically here or fix it in servlet class @Property
	public static final String searchServletPath = "/services/SearchServlet";
	
	@Property(label = "Application Name", description = "Be sure to specify the name of your application. "
			+ "If the application name is null or blank, the application will log a warning. "
			+ "Suggested format is 'MyCompany-ProductName/1.0'.")
	private static final String APPLICATIONNAME = "cse.application.name";

	@Property(label = "API Key", description = "API Key for the registered developer project for your application.")
	private static final String APIKEY = "cse.api.key";
	

	@Property(label = "Context", description = "The main search engine ID to scope the search query")
	private static final String CX = "cse.cx";
	
	@Property(label = "Number", description = "Number of search results to return per page (integer)")
	private static final String NUM = "cse.num";
	
	@Property(label = "Pages", description = "Number of pages to show (integer)")
	private static final String PAGES = "cse.pages";
	
	private String apikey;
	private String applicationname;
	private String cx;
	private String pages;
	private Long num;
	private Long pagesToShow;
	
	private Long pagesRight;
	private Long pagesLeft;
	
	private Customsearch customsearch;
	
	
	private LinkedList<ResultItem> resultItems;
	
	 	public String getApikey() {
	        return apikey;
	    }

	    public String getApplicationname() {
	        return applicationname;
	    }
	    
	    public String getCx(){
	    	return cx;
	    }
	    
	    public long getNum(){
	    	return num;
	    }
	    
	    public long getPagesToShow(){
	    	return pagesToShow;
	    }
	    
	    public String getPages(){
	    	return pages;
	    }
	    
	
	static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final long serialVersionUID = 1L;

	
	public GoogleSearchService(){
		
	}
//	
//	public GoogleSearchService(SlingHttpServletRequest request, SlingHttpServletResponse response){
//		this.request = request;
//		this.response = response;
//		
//	}
	
	@Activate
	protected void activate(ComponentContext ctx) throws Exception {
		try {
			this.apikey = PropertiesUtil.toString(ctx.getProperties().get(APIKEY), "");
			this.applicationname = PropertiesUtil.toString(ctx.getProperties().get(APPLICATIONNAME), "");
			this.cx = PropertiesUtil.toString(ctx.getProperties().get(CX), "");
			this.num = PropertiesUtil.toLong(ctx.getProperties().get(NUM), 7l);
			this.pagesToShow = PropertiesUtil.toLong(ctx.getProperties().get(PAGES), 10l);
			this.pagesLeft = new BigDecimal(this.pagesToShow).divide(new BigDecimal(2), RoundingMode.UP).longValue();
			this.pagesRight = new BigDecimal(this.pagesToShow).divide(new BigDecimal(2), RoundingMode.DOWN).longValue();
			log.error("GAETAN 1 => " + JSON_FACTORY);
			
			log.error("GAETAN 3 => " + applicationname);
			this.customsearch = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,	null)
					.setApplicationName(applicationname)
					.setGoogleClientRequestInitializer(new CustomsearchRequestInitializer(apikey))
					.build();
			log.error("GAETAN 6 => " + customsearch);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public ResultList getResults(String q, String currentTab) {
		//TODO: do XSS protection on Q
		//String requestedPath = this.request.getRequestURI();
		//String q = this.request.getParameter("q");
		//String currentTab = request.getParameter("currentTab") != null ? this.request.getParameter("currentTab") : "1";
		ResultList resultList = new ResultList();
		try {
			//TODO: gete data from OSGI config. Hardcoded for now
			this.apikey = "AIzaSyBGXeDegpobGIktqKTZHqgD5moXrCjtECQ";
			this.applicationname = "InfieldDesign-GoogleSearchAEM/1.0";
			this.cx = "009552797787203508820:4sf6qp_bvqu";
			this.num = 7l;
			this.pagesToShow = 10l;
			this.pagesLeft = new BigDecimal(this.pagesToShow).divide(new BigDecimal(2), RoundingMode.UP).longValue();
			this.pagesRight = new BigDecimal(this.pagesToShow).divide(new BigDecimal(2), RoundingMode.DOWN).longValue();
			
			customsearch = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,	null)
			.setApplicationName(this.applicationname)
			.setGoogleClientRequestInitializer(new CustomsearchRequestInitializer(this.apikey))
			.build();
			Long actualPage = Long.valueOf(currentTab);
			com.google.api.services.customsearch.Customsearch.Cse.List list = customsearch.cse().list(q);
			
			list.setNum(this.num);
			list.setStart((this.num * (actualPage - 1)) + 1);
			list.setCx(this.cx);
			Search results = list.execute();
			SearchInformation searchInformation = results.getSearchInformation();
			
			
			this.resultItems = new LinkedList<ResultItem>();
			
			if (searchInformation != null){
				Long totalResults = searchInformation.getTotalResults();
				Long totalPages = new BigDecimal(totalResults).divide(new BigDecimal(this.num), RoundingMode.UP).longValue();
				resultList.setTotalPages(totalPages);
				resultList.setTotalResults(searchInformation.getTotalResults());
				resultList.setFormattedSearchTime(searchInformation.getFormattedSearchTime());
				resultList.setFormattedTotalResults(searchInformation.getFormattedTotalResults());
				resultList.setSearchTime(searchInformation.getSearchTime());
				resultList.setStartPage(getStartPage(actualPage));
				resultList.setEndPage(getEndPage(actualPage,totalPages));
				resultList.setCurrentTab(actualPage);
				
				
				List <Result> searchResults = results.getItems();
				for (Result searchResult: searchResults){
					ResultItem resultItem = new ResultItem();
					resultItem.setCacheId(searchResult.getCacheId());
					resultItem.setDisplayLink(searchResult.getDisplayLink());
					resultItem.setFileFormat(searchResult.getFileFormat());
					resultItem.setFormattedUrl(searchResult.getFormattedUrl());
					resultItem.setHtmlFormattedUrl(searchResult.getHtmlFormattedUrl());
					resultItem.setHtmlSnippet(searchResult.getHtmlSnippet());
					resultItem.setHtmlTitle(searchResult.getHtmlTitle());
					resultItem.setKind(searchResult.getKind());
					//resultItem.setLabels(searchResult.getLabels());
					resultItem.setLink(searchResult.getLink());
					resultItem.setMime(searchResult.getMime());
					resultItem.setSnippet(searchResult.getSnippet());
					resultItem.setTitle(searchResult.getTitle());
					resultItems.add(resultItem);
				}
				
				
				resultList.setResultItems(resultItems);
				
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return resultList;
	}

	private Long getEndPage(Long actualPage, Long totalPages) {
		if (totalPages <= 10l || (actualPage + (pagesRight - 1)) > totalPages) {
			return totalPages;
		}
		if (actualPage + (pagesRight - 1) <= 10l) {
			return 10l;
		}
		return actualPage + (pagesRight - 1);
	}

	private Long getStartPage(Long actualPage) {
		if ((actualPage - pagesLeft) <= 0l) return 1l;
		return actualPage-pagesLeft;
	}

	

}
