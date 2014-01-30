<%@ include file="/apps/id-googleseacrh/global.jsp" %>
<%@page session="false" %>

<form class="search" action="/search.html" method="get">
<input class="submit_search" type="submit" value="Search" name="Submit">
	<input type="text" class="input" name="q" value="Search" onfocus="if(this.value=='Search')this.value='';" onblur="if(this.value.trim()=='')this.value='Search';" />
	<script>String.prototype.trim=function(){return(this.replace(/(^\s+)|(\s+$)/g, ""))}</script>
</form>  