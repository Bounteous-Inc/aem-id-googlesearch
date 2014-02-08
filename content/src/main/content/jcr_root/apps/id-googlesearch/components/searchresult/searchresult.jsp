<%@ include file="/apps/id-googlesearch/components/global.jsp"%>
<%@page session="false"%>

<%@ taglib prefix="infield"
	uri="http://googlesearch.infield.com/bundles/cq/1.0"%>
<cq:includeClientLib
	categories="infield.components.id-googlesearch.searchresult" />

<c:if test="${not empty properties.showSearchBox}">
	<div class="over_ipup_bl">
		<input class="inpute_search" type=text id="q" name="q"
			value="${param.q}" onkeypress="return runSearch(event, this);">
		<a class="lens" onclick="return runClickSearch(this);"></a>
	</div>
</c:if>
<c:if test="${not empty param.q}">
	<infield:customsearch q="${param.q}" currentTab="1" />
	<h1>Total Results for ${param.q}: ${resultList.totalResults}</h1>
	<ul class="resultList">
		<c:forEach items="${resultList.resultItems}" var="resultItem"
			varStatus="loop">
			<li class="resultItem"><a href="${resultItem.link}"
				title="${resultItem.title}">${resultItem.title}</a><br />
				<p>${resultItem.htmlSnippet}</p>
				<p>${resultItem.htmlFormattedUrl}</p></li>
		</c:forEach>
</c:if>

</ul>
<div class="homeFrame" align=center>
	<div class="left_part">
		<div class="main_bl">
			<div id="paginator" class="paginator"></div>
		</div>
	</div>
</div>



