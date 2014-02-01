<%@ include file="/apps/id-googlesearch/components/global.jsp" %>
<%@page session="false" %>

<cq:includeClientLib categories="infield.components.id-googlesearch.searchresult" />
<div class="homeFrame" align=center>
	<div class="left_part">
		<div class="main_bl">
			<div class="over_ipup_bl" >
				<input class="inpute_search" type=text id="q" name="q" value="${param.q}" onkeypress="return runSearch(event, this);">
				<a class="lens" onclick="return runClickSearch(this);"></a>
			</div>
			<br>
			<br>
			<div class="gm_results">
				<b class="search_res"></b>
				<div class="under_search_sh"></div>
				<div id="results"></div>
				<div id="paginator" class="paginator"></div>
				<br>
			</div>
		</div>
	</div>
</div>