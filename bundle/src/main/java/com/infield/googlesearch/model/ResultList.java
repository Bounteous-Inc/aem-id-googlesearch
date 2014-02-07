/**
 * Copyright Infield Design 2014
 * 
 * @author Gaetan Marmasse <gaetan@infielddesign.com>
 */
package com.infield.googlesearch.model;

import java.util.LinkedList;


public class ResultList {
	
	private String formattedSearchTime;
	private double searchTime;
	private String formattedTotalResults;
	private long totalResults;
	private long totalPages;
	private long startPage;
	private long endPage;
	private long currentTab;
	private LinkedList<ResultItem> resultItems;
	
	/**
	 * Gets the result items
	 * @return List<ResultItem> the list of result items
	 */
	public LinkedList<ResultItem> getResultItems() {
		return resultItems;
	}

	/**
	 * Sets the result Item list
	 * @param resultItems
	 */
	public void setResultItems(LinkedList<ResultItem> resultItems) {
		this.resultItems = resultItems;
	}
	
	/**
	 * Gets the formatted search time
	 * @return
	 */
	public String getFormattedSearchTime() {
		return formattedSearchTime;
	}
	
	/**
	 * Sets the formatted search time
	 * @param formattedSearchTime
	 */
	public void setFormattedSearchTime(String formattedSearchTime){
		this.formattedSearchTime = formattedSearchTime;
	}
	
	/**
	 * Gets the search time
	 * @return
	 */
	public double getSearchTime(){
		return searchTime;
	}
	
	/**
	 * Sets the search Time
	 * @param searchTime
	 */
	public void setSearchTime(double searchTime){
		this.searchTime = searchTime;
	}
	
	/**
	 * Gets the formatted search result count
	 * @return
	 */
	public String getFormattedTotalResults(){
		return formattedTotalResults;
	}
	
	/**
	 * Sets the formatted total result count
	 * @param formattedTotalResults
	 */
	public void setFormattedTotalResults(String formattedTotalResults){
		this.formattedTotalResults = formattedTotalResults;
	}
	
	/**
	 * Gest the total number of results
	 * @return
	 */
	public long getTotalResults(){
		return totalResults;
	}
	
	/**
	 * Sets the total result count
	 * @param totalResults
	 */
	public void setTotalResults(long totalResults){
		this.totalResults = totalResults;
	}

	/**
	 * @return the totalPages
	 */
	public long getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the startPage
	 */
	public long getStartPage() {
		return startPage;
	}

	/**
	 * @param startPage the startPage to set
	 */
	public void setStartPage(long startPage) {
		this.startPage = startPage;
	}

	/**
	 * @return the endPage
	 */
	public long getEndPage() {
		return endPage;
	}

	/**
	 * @param endPage the endPage to set
	 */
	public void setEndPage(long endPage) {
		this.endPage = endPage;
	}

	/**
	 * @return the currentTab
	 */
	public long getCurrentTab() {
		return currentTab;
	}

	/**
	 * @param currentTab the currentTab to set
	 */
	public void setCurrentTab(long currentTab) {
		this.currentTab = currentTab;
	}
}
