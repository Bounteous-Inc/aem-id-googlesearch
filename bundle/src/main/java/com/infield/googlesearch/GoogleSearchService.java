/**
 * Copyright Infield Design 2014
 * 
 * @author Gaetan Marmasse <gaetan@infielddesign.com>
 */
package com.infield.googlesearch;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.jcr.api.SlingRepository;
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


@Component(
		label = "Google Custom Search Service", 
		description = "Service used to call Google Custom Services.",
		immediate = true, 
		metatype = true)
@Service(GoogleSearchService.class)
@Properties({
	@Property(name = Constants.SERVICE_VENDOR, value = "Infield"),
	@Property(name = Constants.SERVICE_DESCRIPTION, value = "Google Custom Search service")
	 , 
	 @Property(
	 label = "Application Name", 
	 name = "cse.application.name", 
	 value = "",
	 description = "Be sure to specify the name of your application. "
	 + "If the application name is null or blank, the application "
	 + "will log a warning. Suggested format is "
	 + "'MyCompany-ProductName/1.0'.")
	 ,
	 @Property(
	 label = "API Key",
	 name = "cse.api.key", 
	 value = "", 
	 description = "API Key for the registered developer project for your application.")
	 ,
	 @Property(
	 label = "Context",
	 name = "cse.cx",
	 value = "", 
	 description = "The main search engine ID to scope the search query")
	 ,
	 @Property(
	 label = "Number", 
	 name = "cse.num",
	 value = "", 
	 description = "Number of search results to return per page (integer)")
	 ,
	 @Property(
	 label = "Pages", 
	 name = "cse.pages",
	 value = "10", 
	 description = "Number of pages to show (integer)")
})
public class GoogleSearchService  {

	private final Logger log = LoggerFactory.getLogger(getClass());

	public static String apikey = "";
	public static String applicationname = "";
	public static String cx = "";
	public static Long num = 7l;
	public static Long pagesToShow = 10l;
	public static Long pagesRight;
	public static Long pagesLeft;
	private Customsearch customsearch;
	private LinkedList<ResultItem> resultItems;

	static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final long serialVersionUID = 1L;

	
	@Reference
	private SlingRepository repository;

	@Activate
	protected void activate(ComponentContext componentContext){
		configure(componentContext.getProperties());
	}
	
	
	protected void configure(Dictionary<?, ?> properties) {
		GoogleSearchService.apikey = PropertiesUtil.toString(properties.get("cse.api.key"), apikey);
		GoogleSearchService.applicationname = PropertiesUtil.toString(properties.get("cse.application.name"), applicationname);
		GoogleSearchService.cx = PropertiesUtil.toString(properties.get("cse.cx"),cx);
		GoogleSearchService.num = PropertiesUtil.toLong(properties.get("cse.num"), num);
		GoogleSearchService.pagesToShow = PropertiesUtil.toLong(properties.get("cse.pages"), pagesToShow);
		GoogleSearchService.pagesLeft = new BigDecimal(pagesToShow).divide(new BigDecimal(2), RoundingMode.UP).longValue();
		GoogleSearchService.pagesRight = new BigDecimal(pagesToShow).divide(new BigDecimal(2), RoundingMode.DOWN).longValue();
	 }


	public ResultList getResults(String q, String currentTab) {
		//TODO: do XSS protection on Q
		ResultList resultList = new ResultList();

		try {
			customsearch = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
			.setApplicationName(applicationname)
			.setGoogleClientRequestInitializer(new CustomsearchRequestInitializer(apikey))
			.build();
			Long actualPage = Long.valueOf(currentTab);
			com.google.api.services.customsearch.Customsearch.Cse.List list = customsearch.cse().list(q);
			
			list.setNum(num);
			list.setStart((num * (actualPage - 1)) + 1);
			list.setCx(cx);
			Search results = list.execute();
			SearchInformation searchInformation = results.getSearchInformation();

			resultItems = new LinkedList<ResultItem>();

			if (searchInformation != null){
				Long totalResults = searchInformation.getTotalResults();
				Long totalPages = new BigDecimal(totalResults).divide(new BigDecimal(num), RoundingMode.UP).longValue();
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