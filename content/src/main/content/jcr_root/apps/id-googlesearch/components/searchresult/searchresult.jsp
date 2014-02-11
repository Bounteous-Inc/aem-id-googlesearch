<%@ include file="/apps/id-googlesearch/components/global.jsp"%>
<%--
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
--%>
<%@page session="false"%>

<%@ taglib prefix="infield"
	uri="http://googlesearch.infield.com/bundles/cq/1.0"%>
<cq:includeClientLib
	categories="infield.components.id-googlesearch.searchresult" />

<c:if test="${not empty properties.showSearchBox}">
	<div class="over_ipup_bl">
		<form action="<cq:requestURL><cq:removeParam name="q"/><cq:removeParam name="currentTab"/></cq:requestURL>">
			<input class="inpute_search" type=text id="q" name="q" value="${param.q}">
			<input class="submit_search" type="submit" value="Search" name="Submit">
		</form>
	</div>
</c:if>

<c:if test="${not empty param.q}">
	<infield:customsearch q="${param.q}"
		currentTab="${empty param.currentTab ? 1 : param.currentTab}"
		numberOfResults="${empty properties.numberOfResults ? 10 : properties.numberOfResults}"
		numberOfPages="${empty properties.numberOfPages ? 7 : properties.numberOfPages}" />
	<h1>Total Results for ${param.q}: ${resultList.totalResults}</h1>
	<h2> ${resultList.totalPages} </h2>
	<h2> ${resultList.pagesToShow} </h2>
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

<div id="pagination" class="pagination">
	<c:if test="${param.currentTab > 1}">
		<a class="gm_tablink"
			href="<cq:requestURL><cq:removeParam name="currentTab"/><cq:addParam name="currentTab" value="${param.currentTab -1}"/></cq:requestURL>">&lt;
			Previous</a>
	</c:if>
	<c:forEach begin="${resultList.startPage}" end="${resultList.endPage}"
		varStatus="loop">
		<c:choose>
			<c:when test="${param.currentTab == loop.index}">
				<a class="gm_tablink gm_tablink_num active_page" href="#"
					onClick="return false;" />${loop.index}</a>
			</c:when>
			<c:otherwise>
				<a class="gm_tablink"
					href="<cq:requestURL><cq:removeParam name="currentTab"/><cq:addParam name="currentTab" value="${loop.index}"/></cq:requestURL>">${loop.index}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${param.currentTab < reultList.pagesToShow}">
		<a class="gm_tablink"
			href="<cq:requestURL><cq:removeParam name="currentTab"/><cq:addParam name="currentTab" value="${param.currentTab + 1}"/></cq:requestURL>">&gt;
			Next</a>
	</c:if>
</div>




