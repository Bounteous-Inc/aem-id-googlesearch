/**
 * Copyright Infield Design 2014
 * 
 * @author Gaetan Marmasse <gaetan@infielddesign.com>
 */
package com.infield.googlesearch.model;

import java.util.List;

public class ResultList {
	
	private String formattedSearchTime;
	private double searchTime;
	private String formattedTotalResults;
	private long totalResults;
	private List<ResultItem> resultItems;
	
	/**
	 * Gets the result items
	 * @return List<ResultItem> the list of result items
	 */
	public List<ResultItem> getResultItems() {
		return resultItems;
	}

	/**
	 * Sets the result Item list
	 * @param resultItems
	 */
	public void setResultItems(List<ResultItem> resultItems) {
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
}
