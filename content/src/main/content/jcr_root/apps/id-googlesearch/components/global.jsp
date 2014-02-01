<%@ page session="false" %>
<%@ page import="com.day.cq.wcm.api.WCMMode" %>
<%@ page import="com.adobe.granite.xss.XSSAPI" %>
<%@ page import="com.day.cq.i18n.I18n" %>
<%@ page import="javax.jcr.Node" %>
<%@include file="/libs/foundation/global.jsp"%>
<%
        final I18n i18n = new I18n(slingRequest);
        boolean isEdit = WCMMode.fromRequest(request) == WCMMode.EDIT;
        boolean isDesign = WCMMode.fromRequest(request) == WCMMode.DESIGN;
%>
<c:set var="isEdit" value="<%= isEdit %>" />
<c:set var="isDesign" value="<%= isDesign %>" />