package com.infield.googlesearch.taglib;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.tagext.SimpleTagSupport;
//import org.apache.sling.api.resource.Resource;





import org.apache.sling.api.SlingHttpServletRequest;

import com.day.cq.i18n.I18n;
import com.day.cq.wcm.api.Page;


public final class Functions extends SimpleTagSupport{

	 private Functions() {

	 }

	public static String Translate(Page currentPage, SlingHttpServletRequest slingRequest, String value){
		if (!"".equals(value)){
			Locale pageLocale = currentPage.getLanguage(false);
			//Locale myLocale = new Locale("fr");  
			ResourceBundle resourceBundle = slingRequest.getResourceBundle(pageLocale);   
			I18n i18n = new I18n(resourceBundle);
//			I18n i18n = new I18n(slingRequest);
			return i18n.get(value);
			//return i18n.get(value);
		}
		return "";
	}

}