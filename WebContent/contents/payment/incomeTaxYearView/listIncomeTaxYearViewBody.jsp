<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<div class="box searchForm toggableForm" id="incomeTaxYear-information">
		<div class="head"><label id="itleLable"><bean:message bundle="payment" key="payment.tax.year.view" /></label></div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="incomeTaxYearViewAction.do?proc=list_object" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="incomeTaxYearViewHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="incomeTaxYearViewHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="incomeTaxYearViewHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="incomeTaxYearViewHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="payment" key="payment.tax.year" /></label>
							<html:select property="year" styleClass="incomeTaxYearViewForm_year">
								<html:optionsCollection label="mappingValue" property="years" value="mappingId"/>
							</html:select>
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label>
							<html:text property="employeeId" styleClass="incomeTaxYearViewForm_employeeId" />
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
							</label>
							<html:text property="employeeNameZH" styleClass="incomeTaxYearViewForm_employeeNameZH" />
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
							</label>
							<html:text property="employeeNameEN" styleClass="incomeTaxYearViewForm_employeeNameEN" />
						</li>
						<li>
							<label><bean:message bundle="payment" key="payemnt.tax.not.less.then" /><img src="images/tips.png" title="<bean:message bundle="payment" key="payemnt.tax.not.less.then.tips" />"></label>
							<html:text property="taxAmountPersonalMin" styleClass="incomeTaxYearViewForm_taxAmountPersonalMin" />
						</li>
						<li>
							<label><bean:message bundle="payment" key="payemnt.tax.not.more.then" /></label>
							<html:text property="taxAmountPersonalMax" styleClass="incomeTaxYearViewForm_taxAmountPersonalMax" />
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- incomeTaxYear-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div class="top">
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<div id="tableWrapper">
				<jsp:include page="/contents/payment/incomeTaxYearView/table/listIncomeTaxYearViewTable.jsp" flush="true"/> 
			</div>
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_IncomeTaxY').addClass('selected');
		
		$('#searchDiv').hide();

	})(jQuery);
	
	// 搜索按钮
	function searchForm(){
		var flag = 0;
		flag = flag + validate("incomeTaxYearViewForm_taxAmountPersonalMin", false, "currency", 0, 0);
		flag = flag + validate("incomeTaxYearViewForm_taxAmountPersonalMax", false, "currency", 0, 0);
		if( flag == 0 ){
			submitForm('list_form', 'searchObject', null, null, null, null);
		}
	};

	// 重置按钮
	function resetForm() {
		$('.incomeTaxYearViewForm_employeeId').val('');
		$('.incomeTaxYearViewForm_employeeNameZH').val('');
		$('.incomeTaxYearViewForm_employeeNameEN').val('');
		$('.incomeTaxYearViewForm_taxAmountPersonalMin').val('');
		$('.incomeTaxYearViewForm_taxAmountPersonalMax').val('');
	};
</script>
