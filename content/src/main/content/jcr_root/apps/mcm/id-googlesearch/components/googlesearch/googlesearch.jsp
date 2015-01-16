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
<%@page session="false" import="com.day.cq.wcm.webservicesupport.ConfigurationManager,
                com.day.cq.wcm.webservicesupport.Configuration"%><%
%>
<%
ConfigurationManager cfgMgr = (ConfigurationManager)sling.getService(ConfigurationManager.class);

Configuration configuration = null;
String[] services = pageProperties.getInherited("cq:cloudserviceconfigs", new String[]{});
if(cfgMgr != null) {
    configuration = cfgMgr.getConfiguration("exacttarget", services);
}
%>