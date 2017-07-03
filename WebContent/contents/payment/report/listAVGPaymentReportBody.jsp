<%@ page import="com.kan.hro.web.actions.biz.payment.PaymentReportAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label><bean:message bundle="payment" key="payment.avg.report" /></label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchPaymentReport_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="paymentReportAction.do?proc=list_object" method="post" styleClass="searchPaymentReport_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="paymentReportHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="paymentReportHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="paymentReportHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="paymentReportHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="titleNameList" id="titleNameList" value="" />
				<input type="hidden" name="titleIdList" id="titleIdList" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label>
							<html:text property="employeeId" maxlength="10" styleClass="search_employeeId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
							</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="search_employeeNameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
							</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="search_employeeNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.begin.monthly" /></label>
							<html:select property="monthlyBegin" styleClass="search_monthlyBegin">
								<html:optionsCollection property="monthlys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.end.monthly" /></label>
							<html:select property="monthlyEnd" styleClass="search_monthlyEnd">
								<html:optionsCollection property="monthlys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	
	<!-- Information Search Result -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div class="top">
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
				 <kan:auth right="export" action="<%=PaymentReportAction.ACCESS_ACTION%>">
					<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm( 'searchPaymentReport_form', 'downloadObjects', null, null );"><img src="images/appicons/excel_16.png" /></a> 
				 </kan:auth> 
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/payment/report/table/listAVGPaymentReportTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// JS of the List 系统自动生成js代码 
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Reports').addClass('selected');
		$('#menu_salary_Payment_AVG_Report').addClass('selected');
		
		kanList_init();
		kanCheckbox_init();
		$('#titleNameList').val(getTabTitle());
		$('#titleIdList').val(getTabDB());
	})(jQuery);
	
	function resetForm() {
	   var oForm = $('.searchPaymentReport_form');
	   oForm.find('input:visible').val('');
	   oForm.find('select:visible').filter(':hidden').val('0');
	};
	
	function getTabTitle(){
		var oArray = new Array();
		var oTTFirstTr = $('#_fixTableMain table thead tr th');
		oTTFirstTr.each( function (i){
			oArray[i] = $(this).find('span').html();
		});
		return oArray;
	};
	
	function getTabDB(){
		var oArray = new Array();
		var oTTFirstTr = $('#_fixTableMain table thead tr th');
		oTTFirstTr.each( function (i){
			oArray[i] = $(this).find('span').attr('id');
		});
		return oArray;
	};
	
	function pageInit(){
		
	}
</script>
