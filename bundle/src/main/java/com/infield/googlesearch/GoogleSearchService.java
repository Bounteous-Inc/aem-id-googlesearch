/*************************************************************************
 * Copyright 2014 Infield Design
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Gaetan Marmasse <gaetan@infielddesign.com>
 * 
 *************************************************************************/

package com.infield.googlesearch;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.GeneralSecurityException;
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
})
public class GoogleSearchService  {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private static String apikey = "";
	private static String applicationname = "";
	private static String cx = "";
	private long num = 7;
	private long pagesToShow = 10;

	private Long pagesRight;
	private Long pagesLeft;
	private Customsearch customsearch;
	private LinkedList<ResultItem> resultItems;

	static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final long serialVersionUID = 1;


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
	}


	public ResultList getResults(String q, String currentTab, long num, long pagesToShow) {

		this.num = num;
		this.pagesToShow = pagesToShow;

		//TODO: do XSS protection on Q
		ResultList resultList = new ResultList().setResultItems(new LinkedList<ResultItem>());

		this.pagesLeft = (this.pagesToShow - (pagesToShow % 2)) / 2;
		this.pagesRight = this.pagesToShow - this.pagesLeft;

		// currentPage starts from 1
		Long currentPage = Long.valueOf(currentTab);

		Customsearch.Cse.List list;
		Search results = null;

		try {
			customsearch = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
			.setApplicationName(applicationname)
			.setGoogleClientRequestInitializer(new CustomsearchRequestInitializer(apikey))
			.build();

			list = customsearch.cse().list(q)
					.setNum(this.num)
					.setStart((this.num * (currentPage - 1)) + 1)
					.setCx(cx);

			results = list.execute();

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (GeneralSecurityException e) {
			log.error(e.getMessage(), e);
		} finally {

			SearchInformation searchInformation = (results != null) ? results.getSearchInformation() : null;

			this.resultItems = new LinkedList<ResultItem>();



			if (searchInformation != null){
				Long totalResults = searchInformation.getTotalResults();

				Long totalPages = (totalResults - (totalResults % this.num))/this.num + 1;

				this.pagesToShow = (totalPages > this.pagesToShow) ? this.pagesToShow : totalPages;
				
				resultList.setTotalPages(totalPages)
				.setPagesToShow(this.pagesToShow)
				.setTotalResults(searchInformation.getTotalResults())
				.setFormattedSearchTime(searchInformation.getFormattedSearchTime())
				.setFormattedTotalResults(searchInformation.getFormattedTotalResults())
				.setSearchTime(searchInformation.getSearchTime())
				.setStartPage(getStartPage(currentPage))
				.setEndPage(getEndPage(currentPage))
				.setCurrentTab(currentPage);

				// this ternary is used to  
				List<Result> searchResults = results.getItems() != null ? results.getItems() : new LinkedList<Result>();

				for (Result searchResult : searchResults){
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
		}

		return resultList;
	}

	private long getEndPage(long currentPage) {
		
		this.pagesLeft = (pagesToShow - (pagesToShow % 2)) / 2;
		this.pagesRight = pagesToShow - this.pagesLeft;
		
		long cp = currentPage - 1;
		long startPage = cp - this.pagesLeft;
		long endPage = cp + this.pagesRight;

		startPage = (startPage < 0) ? 0 : startPage;
		endPage = (endPage > pagesToShow - 1) ? pagesToShow - 1: endPage;

		while ((endPage - startPage) < pagesToShow 
				&& (startPage == 0)
				&& endPage < pagesToShow - 1) {
			endPage++;
		}

		return endPage + 1;
	}

	private long getStartPage(long currentPage) {
		
		this.pagesLeft = (pagesToShow - (pagesToShow % 2)) / 2;
		this.pagesRight = pagesToShow - this.pagesLeft;
		
		long cp = currentPage - 1;
		long startPage = cp - this.pagesLeft;
		long endPage = cp + this.pagesRight;

		startPage = (startPage < 0) ? 0 : startPage;
		endPage = (endPage > pagesToShow - 1) ? pagesToShow - 1: endPage;

		while ((endPage - startPage) < pagesToShow 
				&& (endPage == pagesToShow - 1)
				&& startPage > 0) {
			startPage--;
		}

		return startPage + 1;
	}
}