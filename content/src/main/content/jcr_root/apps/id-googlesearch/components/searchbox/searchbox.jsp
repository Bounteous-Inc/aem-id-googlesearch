<%@ include file="/apps/id-googlesearch/components/global.jsp" %>
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
<%@page session="false" %>
<%@ taglib prefix="funk" uri="http://functions.infield.com/bundles/cq/1.0"%>
<%@ taglib prefix="infield"
	uri="http://googlesearch.infield.com/bundles/cq/1.0"%>

<infield:inheritance resource="${resource}" propertyName="resultPage" defaultValue="" />

<form class="search" action="${inheritedPropertyValue}.html" method="get">
	<input class="submit_search" type="submit" value="${funk:Translate(currentPage, slingRequest, 'Search')}" name="Submit">
	<input type="text" class="input" name="q" value="${funk:Translate(currentPage, slingRequest, 'Search')}" onfocus="if(this.value=='${funk:Translate(currentPage, slingRequest, 'Search')}')this.value='';" onblur="if(this.value.trim()=='')this.value='${funk:Translate(currentPage, slingRequest, 'Search')}';" />
	<input type="hidden" name="currentTab" value="1" />
	<script>String.prototype.trim=function(){return(this.replace(/(^\s+)|(\s+$)/g, ""))}</script>
</form>
<div style="clear:both;"></div>