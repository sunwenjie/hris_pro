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
	<logic:present name="MESSAGE_HEADER_SORT">
		<logic:present name="MESSAGE">
			<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
				<bean:write name="MESSAGE" />
    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
			</div>
		</logic:present>
	</logic:present>
</div>
<html:form action="reportHeaderAction.do?proc=modify_object_add_sort" styleClass="manageReportDetail_form_sort">
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
				<select name="columnId" id="columnId" class="manageReportDetail_form_sort_columnId">
					<option value="0">«Î—°‘Ò</option>
				</select>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.detail.sort" /><em> *</em></label> 
				<html:select property="sortColumns" styleClass="manageReportDetail_form_sort_sortColumns">
					<html:optionsCollection property="sortColumnses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>	
		</ol>
		<ol class="auto">
			<%=ReportRender.getReportHeaderSortColumn( request, reportHeaderVO ) %>
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
