<%@page import="com.kan.base.web.renders.define.ReportRender"%>
<%@page import="com.kan.base.domain.define.ReportHeaderVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final ReportHeaderVO reportHeaderVO = ( ReportHeaderVO )request.getAttribute( "reportHeaderForm" );
%>

<div id="messageWrapper">
	<logic:present name="MESSAGE_HEADER_GROUP">
		<logic:present name="MESSAGE">
			<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
				<bean:write name="MESSAGE" />
    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
			</div>
		</logic:present>
	</logic:present>
</div>
<html:form action="reportHeaderAction.do?proc=modify_object_add_group" styleClass="manageReportDetail_form_group">
	<input type="hidden" name="reportHeaderId" id="reportHeaderId" class="reportHeaderId" value="<bean:write name="reportHeaderForm" property="reportHeaderId" />" />
	<fieldset>
		<ol class="auto">
			<li class="required">
				<label><em>* </em><bean:message bundle="public" key="required.field" /></label>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.column" /><em> *</em></label> 
				<select name="columnId" id="columnId" class="manageReportDetail_form_group_columnId">
					<option value="0">«Î—°‘Ò</option>
				</select>
			</li>	
			<li style="display:none;">
				<label><bean:message bundle="define" key="define.report.detail.column.count" /><em> *</em></label>
				<html:select property="statisticsColumns" styleClass="manageReportDetail_form_group_statisticsColumns">
					 <html:optionsCollection property="statisticsColumnses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>	
		</ol>
		<ol class="auto" id="groupListOL">
			<%=ReportRender.getReportHeaderGroupColumn( request, reportHeaderVO ) %>
		</ol>
	</fieldset>	
</html:form>

<script type="text/javascript">
	(function($) {
		<logic:notEmpty name="MESSAGE">
			messageWrapperFada();
		</logic:notEmpty>
	})(jQuery);
</script>
