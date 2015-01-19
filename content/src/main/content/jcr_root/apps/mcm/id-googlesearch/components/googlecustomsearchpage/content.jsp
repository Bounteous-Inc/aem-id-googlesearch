<%@ include file="/apps/id-googlesearch/components/global.jsp"%>
<%--
/*************************************************************************
 * Copyright 2015 Infield Design
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
 * @author Ankit Gubrani <ankit.gubrani@infielddesign.com>
 *
 *************************************************************************/
--%>
<%@include file="/libs/cq/cloudserviceconfigs/components/configpage/init.jsp"%>
<%@include file="/libs/cq/cloudserviceconfigs/components/configpage/hideeditok.jsp"%>
<%@page session="false" contentType="text/html"
        pageEncoding="utf-8"
        import="com.day.cq.i18n.I18n, org.apache.sling.api.resource.ModifiableValueMap"%>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %>
<sling:defineObjects/>
<% I18n i18n = new I18n(slingRequest);
	ModifiableValueMap googleCustomSearchValues = resource.adaptTo(ModifiableValueMap.class);
	Node googleCustomSearchNode = resource.adaptTo(Node.class);
	boolean isConnected = googleCustomSearchValues.get("isConnected", false);
	if(!isConnected) {
		googleCustomSearchValues.put("isConnected", isConnected);
    }
	resource.getResourceResolver().commit();
%>

<div>
    <div>
        <h3><%= i18n.get("Basic Settings")%></h3>
        <ul>
            <li>
                <div class="li-bullet">
                    <strong><%= i18n.get("Application Name:")%></strong>
                    ${properties.appname}
                </div>
            </li>
            <li>
                <div class="li-bullet">
                    <strong><%= i18n.get("API Key: ")%></strong>${properties.apikey}
                </div>
            </li>
            <li>
                <div class="li-bullet">
                    <strong><%= i18n.get("Context Number:")%> </strong>${properties.cx}
                </div>
            </li>
            <li class="config-successful-message when-config-successful" style="display: none"><%= i18n.get("Google Custom Search configuration is successful.")%><br>
                <%=i18n.get("Please apply the configuration to your")%> <a href="/siteadmin"><%= i18n.get("website")%></a> <%= i18n.get("You can now add Google Custom Search to your site")%></li>

        </ul>
    </div>
</div>
