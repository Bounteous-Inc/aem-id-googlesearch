package com.infield.googlesearch.util;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.sling.api.resource.Resource;


public final class Functions extends SimpleTagSupport{
	
	 private Functions() {
		 
	 }
	
	public static String getInheritedProperty(String propertyName, String currentPagePath){
		if (currentPagePath != null && propertyName != null){
			return currentPagePath;
		}
		return propertyName;
	}

}
