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
<cq:includeClientLib
	categories="infield.components.id-googlesearch.searchbox" />

<infield:inheritance resource="${resource}" propertyName="resultPage" defaultValue="" />

<form class="search" action="${inheritedPropertyValue}.html" method="get">
	<fieldset>
         <div class="input_box">
             <label for="h-search-field" style="display: none;">${funk:Translate(currentPage, slingRequest, 'Search')}</label>
             <input class="cq-auto-clear" type="text" value="${funk:Translate(currentPage, slingRequest, 'Search')}" name="q" id="h-search-field"/>
         </div>
         <input type="button" onclick="this.form.submit();" class="btn"/>
     </fieldset>
	<input type="hidden" name="currentTab" value="1" />
	<script>String.prototype.trim=function(){return(this.replace(/(^\s+)|(\s+$)/g, ""))}</script>
</form>
<div style="clear:both;"></div>