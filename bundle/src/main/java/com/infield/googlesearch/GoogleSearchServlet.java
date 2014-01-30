package com.infield.googlesearch;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.servlet.ServletException;

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

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.CustomsearchRequestInitializer;
import com.google.api.services.customsearch.model.Search;
import com.google.api.services.customsearch.model.Search.SearchInformation;

@Component(
	metatype = true,
	label = "Google Custom Search Servlet", 
	description = "Servlet used to call Google Custom Services."
)
@Service
@Properties(
	{
		@Property(
			name = "service.pid",
			value = "com.infield.googlesearch.GoogleSearchServlet"
		),
		@Property(
			name = "service.vendor",
			value = "Infield"
		),
		@Property(
			name = "sling.servlet.paths",
			value = {GoogleSearchServlet.searchServletPath, GoogleSearchServlet.KBSearchServletPath},
			propertyPrivate = true
		),
		@Property(
			name = "sling.servlet.methods",
			value = "GET",
			propertyPrivate = true
		) 
	}
)
public class GoogleSearchServlet extends SlingAllMethodsServlet {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	public static final String searchServletPath = "/services/SearchServlet";
	public static final String KBSearchServletPath = "/services/KBSearchServlet";
	
	@Property(label = "Application Name", description = "Be sure to specify the name of your application. "
			+ "If the application name is null or blank, the application will log a warning. "
			+ "Suggested format is 'MyCompany-ProductName/1.0'.")
	private static final String APPLICATION_NAME = "cse.application.name";

	@Property(label = "API Key", description = "API Key for the registered developer project for your application.")
	private static final String API_KEY = "cse.api.key";

	@Property(label = "Context", description = "The main search engine ID to scope the search query")
	private static final String CX = "cse.cx";
	
	@Property(label = "KB Context", description = "The KB (knowledge base) search engine ID to scope the search query")
	private static final String KB_CX = "cse.kb_cx";
	
	@Property(label = "Number", description = "Number of search results to return (integer)")
	private static final String NUM = "cse.num";
	
	@Property(label = "Pages", description = "Number of pages to show (integer)")
	private static final String PAGES = "cse.pages";
	
	private String api_key;
	private String application_name;
	private String cx;
	private String kb_cx;
	private Long num;
	private Long pagesToShow;
	
	private Long pagesRight;
	private Long pagesLeft;
	
	private Customsearch customsearch;
	
	static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) 
			throws ServletException, IOException {
		String requestedPath = request.getRequestURI();
		String q = request.getParameter("q");
		String currentTab = request.getParameter("currentTab") != null ? request.getParameter("currentTab") : "1";
		JSONObject jsonObject = new JSONObject();
		try {
			Long actualPage = Long.valueOf(currentTab);
			com.google.api.services.customsearch.Customsearch.Cse.List list = customsearch.cse().list(q);
			list.setNum(num);
			list.setStart((num * (actualPage - 1)) + 1);
			list.setCx(requestedPath.equals(searchServletPath) ? cx : kb_cx);
			Search results = list.execute();
			SearchInformation searchInformation = results.getSearchInformation();
			Long totalResults = searchInformation.getTotalResults();
			Long totalPages = new BigDecimal(totalResults).divide(new BigDecimal(num), RoundingMode.UP).longValue();
			jsonObject.put("startPage", getStartPage(actualPage));
			jsonObject.put("endPage", getEndPage(actualPage,totalPages));
			jsonObject.put("totalPages", totalPages);
			jsonObject.put("currentTab", actualPage);
			jsonObject.put("results", results.getItems());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(jsonObject.toString());
	}

	private Long getEndPage(Long actualPage, Long totalPages) {
		if (totalPages <= pagesToShow || (actualPage + (pagesRight - 1)) > totalPages) {
			return totalPages;
		}
		if (actualPage + (pagesRight - 1) <= pagesToShow) {
			return pagesToShow;
		}
		return actualPage + (pagesRight - 1);
	}

	private Long getStartPage(Long actualPage) {
		if ((actualPage - pagesLeft) <= 0l) return 1l;
		return actualPage-pagesLeft;
	}

	protected void activate(ComponentContext ctx) {
		try {
			api_key = PropertiesUtil.toString(ctx.getProperties().get(API_KEY), "");
			application_name = PropertiesUtil.toString(ctx.getProperties().get(APPLICATION_NAME), "");
			cx = PropertiesUtil.toString(ctx.getProperties().get(CX), "");
			kb_cx = PropertiesUtil.toString(ctx.getProperties().get(KB_CX), "");
			num = PropertiesUtil.toLong(ctx.getProperties().get(NUM), 7l);
			pagesToShow = PropertiesUtil.toLong(ctx.getProperties().get(PAGES), 10l);
			pagesLeft = new BigDecimal(pagesToShow).divide(new BigDecimal(2), RoundingMode.UP).longValue();
			pagesRight = new BigDecimal(pagesToShow).divide(new BigDecimal(2), RoundingMode.DOWN).longValue();
			customsearch = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,	null)
					.setApplicationName(application_name)
					.setGoogleClientRequestInitializer(new CustomsearchRequestInitializer(api_key))
					.build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
