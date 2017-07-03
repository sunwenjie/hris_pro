<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.actions.define.ReportHeaderAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
    <!-- Information Manage Form -->
    <div class="box">
        <div class="head">
            <label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="define" key="define.report" /></label>
        </div>
        <div class="inner">
            <div id="messageWrapper">
            </div>
            <div class="top">
            	<logic:empty name="reportHeaderForm" property="encodedId">
					 <input type="button" class="function" name="btnExecute" id="btnExecute" value="<bean:message bundle="public" key="button.excute" />" />
				</logic:empty>
				<logic:notEmpty name="reportHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=ReportHeaderAction.accessAction%>">
						<input type="button" class="function" name="btnExecute" id="btnExecute" value="<bean:message bundle="public" key="button.excute" />" />
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=ReportHeaderAction.accessAction%>">
					 <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
            </div>
            <!-- °üº¬TabÒ³Ãæ -->
        	<jsp:include page="/contents/define/report/extend/manageReportExtend.jsp" flush="true"></jsp:include>
            <div id="append_info">
            
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
	(function($) {
		$('#menu_define_Modules').addClass('current');
		$('#menu_define_Report').addClass('selected');
		
		if ($('.manageReportHeader_form input#subAction').val() == 'viewObject') {
		    disableForm('manageReportHeader_form');
		    $('#pageTitle').html('<bean:message bundle="define" key="define.report" /> <bean:message bundle="public" key="oper.view" />');
		    $('#btnSaveStep1').val('<bean:message bundle="public" key="button.edit" />');
		} 
		
		$('#btnList').click(function(){
			if (agreest())
			link('reportHeaderAction.do?proc=list_object');
		});
	})(jQuery);
</script>