
/*************************************************************************
*
* Copyright 2014 Infield Design
* All Rights Reserved.
* __________________
*
*  Author Gaetan Marmasse <gaetan@infielddesign.com>
**************************************************************************/
package com.infield.googlesearch.taglib;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.jsp.JspException;

import org.apache.sling.api.scripting.SlingScriptHelper;
import org.osgi.service.component.ComponentContext;

import com.infield.googlesearch.GoogleSearchService;
import com.infield.googlesearch.model.ResultItem;
import com.infield.googlesearch.model.ResultList;


public class GoogleSearchTag extends SimpleTagSupport {

	private String q;
	private String currentTab;
	
	
	@Override
	public void doTag() throws JspException, IOException {
		if (this.q.length() > 0 && this.currentTab.length() > 0){
			GoogleSearchService googleService = new GoogleSearchService();
			ResultList resultList = googleService.getResults(q, currentTab);
			LinkedList<ResultItem> resultItems = resultList.getResultItems();
			getJspContext().setAttribute("resultList",resultList);
			getJspContext().setAttribute("resultItems",resultItems);
		}
	}
	
	public String getQ(){
		return q;
	}
	public void setQ(String q){
		this.q = q;
	}
	public String getCurrentTab(){
		return currentTab;
	}
	public void setCurrentTab(String currentTab){
		this.currentTab = currentTab;
	}
}
