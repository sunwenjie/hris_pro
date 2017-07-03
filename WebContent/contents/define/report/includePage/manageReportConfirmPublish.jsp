<%@page import="com.kan.base.web.renders.define.ReportRender"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.domain.define.ReportHeaderVO"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.web.renders.security.GroupRender"%>
<%@page import="com.kan.base.web.renders.security.PositionRender"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final ReportHeaderVO reportHeaderVO = ( ReportHeaderVO )request.getAttribute( "reportHeaderForm" );
	String positionIds = null;
	String positionGroupIds = null;
	
	if( KANUtil.filterEmpty( reportHeaderVO.getPositionIds() ) != null )
	{
	   positionIds = reportHeaderVO.getPositionIds();
	}
	
	if( KANUtil.filterEmpty( reportHeaderVO.getPositionGroupIds() ) != null )
	{
	   positionGroupIds = reportHeaderVO.getPositionGroupIds();
	}
%>

<div id="messageWrapper">
	<logic:present name="MESSAGE_HEADER_PUBLISH">
		<logic:present name="MESSAGE">
			<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
				<bean:write name="MESSAGE" />
    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
			</div>
		</logic:present>
	</logic:present>
</div>
<html:form action="reportHeaderAction.do?proc=modify_object_publish" styleClass="manageReportHeader_form_publish">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="subAction" id="subAction" value="<bean:write name="reportHeaderForm" property="subAction" />"/>
	<input type="hidden" name="reportHeaderId" id="reportHeaderId" value="<bean:write name="reportHeaderForm" property="encodedId" />"/>
	<fieldset>
		<ol class="auto">
			<li class="required">
				<label><em>* </em><bean:message bundle="public" key="required.field" /></label>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.report.detail.release.module" /><em> *</em></label> 
				<html:select property="moduleType" styleClass="manageReportHeader_form_publish_moduleType">
					 <html:optionsCollection property="moduleTypes" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.report.detail.release.scope" /><em> *</em></label> 
				<html:select property="isPublic" styleClass="manageReportHeader_form_publish_isPublic">
					 <html:optionsCollection property="isPublics" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<p>
			<div class="buttom"></div>
		<p>	
		<div class="tabMenu">
			<ul>
				<li id="tab_Menu1" onClick="change_Tab(1,3)" class="first hover">1£º<bean:message bundle="security" key="security.position" /></li>
				<li id="tab_Menu2" onClick="change_Tab(2,3)">2£º<bean:message bundle="security" key="security.position.grade" /></li>
				<li id="tab_Menu3" onClick="change_Tab(3,3)">3£º<bean:message bundle="security" key="security.position.group" /></li>
			</ul>
		</div>
		<div id="tab_Content1" class="kantab kantree">
			<ol id="positionOL" class="static">
				<%=PositionRender.getPositionTreeByJsonString( request, positionIds )%>
			</ol>
		</div>
		<div id="tab_Content2" class="kantab" style="display: none">
			<ol id="positionGradeOL" class="auto">
				<%=ReportRender.getPositionGradeMultipleChoice( request, reportHeaderVO )%>
			</ol>
		</div>
		<div id="tab_Content3" class="kantab" style="display: none">
			<ol id="positionGroupOL" class="auto">
				<%=GroupRender.getGroupMultipleChoiceByJsonString( request, positionGroupIds )%>
			</ol>
		</div>
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		<logic:notEmpty name="MESSAGE">
			messageWrapperFada();
		</logic:notEmpty>
	})(jQuery);
</script>
