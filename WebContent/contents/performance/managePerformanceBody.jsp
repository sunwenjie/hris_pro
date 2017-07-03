<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<style type="text/css">
	.def-div1 {
		font-size: 13px; font-family: 榛戜綋, Calibri; color: #5d5d5d; padding: 15px 15px 0px 15px; border: 1px solid #dedede;
		border-radius: 15px; -moz-border-radius: 15px; -webkit-border-radius: 15px;
		overflow: hidden; margin: 0px 0px 15px 0px; }
	form ol li label { width: 50%; }
</style>

<div id="content">
	<div id="managePerformance" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">YERR</label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<input type="button" class="save" id="btnEdit" value='<bean:message bundle="public" key="button.edit" />' />
				<input type="button" class="reset" id="btnList" value='<bean:message bundle="public" key="button.list" />' />
			</div>
			
			<html:form action="performanceAction.do?proc=add_object" styleClass="managePerformance_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="id" name="id" value="<bean:write name="performanceForm" property="encodedId" />"/>
				<html:hidden property="subAction" styleClass="subAction" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<div class="def-div" id="EMP-BASE-INFO-DIV">
						<ol class="auto">
							<li>
								<label><bean:message bundle="public" key="public.employee2.id" /></label>
								<html:text property="employeeId" styleClass="employeeId" styleId="employeeId" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.year" /></label>
								<html:text property="yearly" styleClass="yearly" styleId="yearly" />
							</li>
							<li>
								<label><bean:message bundle="public" key="public.employee2.name.cn" /></label>
								<html:text property="chineseName" styleClass="chineseName" styleId="chineseName" />
							</li>
							<li>
								<label><bean:message bundle="public" key="public.employee2.name.en" /></label>
								<html:text property="fullName" styleClass="fullName" styleId="fullName" />
							</li>
							<li>
								<label><bean:message bundle="public" key="public.employee2.short.name" /></label>
								<html:text property="shortName" styleClass="shortName" styleId="shortName" />
							</li>
						</ol>
					</div>
					<ol class="auto">
						<li><a id="EMP-SUMMARY-VIEW-DIV-LINK"><bean:message bundle="performance" key="YERR.link.emp.summary.view" /></a></li>
					</ol>	
					<div class="def-div hide" id="EMP-SUMMARY-VIEW-DIV">
						<ol class="auto">
							<li>
								<label><bean:message bundle="performance" key="YERR.employmentEntityEN" /></label>
								<html:text property="employmentEntityEN" styleClass="employmentEntityEN" styleId="employmentEntityEN" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.employmentEntityZH" /></label>
								<html:text property="employmentEntityZH" styleClass="employmentEntityZH" styleId="employmentEntityZH" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.companyInitial" /></label>
								<html:text property="companyInitial" styleClass="companyInitial" styleId="companyInitial" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.buFunctionEN" /></label>
								<html:text property="buFunctionEN" styleClass="buFunctionEN" styleId="buFunctionEN" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.buFunctionZH" /></label>
								<html:text property="buFunctionZH" styleClass="buFunctionZH" styleId="buFunctionZH" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.departmentEN" /></label>
								<html:text property="departmentEN" styleClass="departmentEN" styleId="departmentEN" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.departmentZH" /></label>
								<html:text property="departmentZH" styleClass="departmentZH" styleId="departmentZH" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.costCenter" /></label>
								<html:text property="costCenter" styleClass="costCenter" styleId="costCenter" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.functionCode" /></label>
								<html:text property="functionCode" styleClass="functionCode" styleId="functionCode" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.location" /></label>
								<html:text property="location" styleClass="location" styleId="location" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.jobRole" /></label>
								<html:text property="jobRole" styleClass="jobRole" styleId="jobRole" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.positionEN" /></label>
								<html:text property="positionEN" styleClass="positionEN" styleId="positionEN" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.positionZH" /></label>
								<html:text property="positionZH" styleClass="positionZH" styleId="positionZH" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.jobGrade" /></label>
								<html:text property="jobGrade" styleClass="jobGrade" styleId="jobGrade" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.internalTitle" /></label>
								<html:text property="internalTitle" styleClass="internalTitle" styleId="internalTitle" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.lineBizManager" /></label>
								<html:text property="lineBizManager" styleClass="lineBizManager" styleId="lineBizManager" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.lineHRManager" /></label>
								<html:text property="lineHRManager" styleClass="lineHRManager" styleId="lineHRManager" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.seniorityDate" /></label>
								<html:text property="seniorityDate" styleClass="seniorityDate" styleId="seniorityDate" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.employmentDate" /></label>
								<html:text property="employmentDate" styleClass="employmentDate" styleId="employmentDate" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.shareOptions" /></label>
								<html:text property="shareOptions" styleClass="shareOptions" styleId="shareOptions" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.lastYearPerformanceRating" /></label>
								<html:text property="lastYearPerformanceRating" styleClass="lastYearPerformanceRating" styleId="lastYearPerformanceRating" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.lastYearPerformancePromotion" /></label>
								<html:text property="lastYearPerformancePromotion" styleClass="lastYearPerformancePromotion" styleId="lastYearPerformancePromotion" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.midYearPromotion" /></label>
								<html:text property="midYearPromotion" styleClass="midYearPromotion" styleId="midYearPromotion" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.midYearSalaryIncrease" /></label>
								<html:text property="midYearSalaryIncrease" styleClass="midYearSalaryIncrease" styleId="midYearSalaryIncrease" />
							</li>
						</ol>	
					</div>
					<ol class="auto">
						<li><a id="SALARY-PACKAGE-STRUCTURE-DIV-LINK"><bean:message bundle="performance" key="YERR.link.salary.package.structre" /></a></li>
					</ol>
					<div class="def-div hide" id="SALARY-PACKAGE-STRUCTURE-DIV">
						<ol class="auto">
							<li>
								<label><bean:message bundle="performance" key="YERR.currencyCode" /></label>
								<html:text property="currencyCode" styleClass="currencyCode" styleId="currencyCode" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.baseSalaryLocal" /></label>
								<html:text property="baseSalaryLocal" styleClass="baseSalaryLocal" styleId="baseSalaryLocal" />
								<html:hidden property="baseSalaryUSD" styleClass="baseSalaryUSD" styleId="baseSalaryUSD" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.annualBaseSalaryLocal" /></label>
								<html:text property="annualBaseSalaryLocal" styleClass="annualBaseSalaryLocal" styleId="annualBaseSalaryLocal" />
								<html:hidden property="annualBaseSalaryUSD" styleClass="annualBaseSalaryUSD" styleId="annualBaseSalaryUSD" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.housingAllowanceLocal" /></label>
								<html:text property="housingAllowanceLocal" styleClass="housingAllowanceLocal" styleId="housingAllowanceLocal" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.childrenEduAllowanceLocal" /></label>
								<html:text property="childrenEduAllowanceLocal" styleClass="childrenEduAllowanceLocal" styleId="childrenEduAllowanceLocal" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.guaranteedCashLocal" /></label>
								<html:text property="guaranteedCashLocal" styleClass="guaranteedCashLocal" styleId="guaranteedCashLocal" />
								<html:hidden property="guaranteedCashUSD" styleClass="guaranteedCashUSD" styleId="guaranteedCashUSD" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.monthlyTarget" /></label>
								<html:text property="monthlyTarget" styleClass="monthlyTarget" styleId="monthlyTarget" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.quarterlyTarget" /></label>
								<html:text property="quarterlyTarget" styleClass="quarterlyTarget" styleId="quarterlyTarget" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.gpTarget" /></label>
								<html:text property="gpTarget" styleClass="gpTarget" styleId="gpTarget" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.targetValueLocal" /></label>
								<html:text property="targetValueLocal" styleClass="targetValueLocal" styleId="targetValueLocal" />
								<html:hidden property="targetValueUSD" styleClass="targetValueUSD" styleId="targetValueUSD" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.ttcLocal" /></label>
								<html:text property="ttcLocal" styleClass="ttcLocal" styleId="ttcLocal" />
								<html:hidden property="ttcUSD" styleClass="ttcUSD" styleId="ttcUSD" />
							</li>
						</ol>
					</div>
					<ol class="auto">
						<li><a id="YERR-DIV-LINK"><bean:message bundle="performance" key="YERR.link.YERR" /></a></li>
					</ol>
					<div class="def-div" id="YERR-DIV">
						<ol class="auto">
							<li>
								<label><bean:message bundle="performance" key="YERR.yearPerformanceRating" /><em> *</em></label>
								<html:select property="yearPerformanceRating" styleClass="yearPerformanceRating" styleId="yearPerformanceRating">
									<html:optionsCollection property="ratings" value="mappingId" label="mappingValue" />
								</html:select>
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.yearPerformancePromotion" /></label>
								<html:select property="yearPerformancePromotion" styleClass="yearPerformancePromotion" styleId="yearPerformancePromotion">
									<html:optionsCollection property="isPromotions" value="mappingId" label="mappingValue" />
								</html:select>
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.recommendTTCIncrease" /><em> *</em></label>
								<html:text property="recommendTTCIncrease" styleClass="recommendTTCIncrease" styleId="recommendTTCIncrease" readonly="true"/>
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.ttcIncrease" /><em> *</em></label>
								<html:text property="ttcIncrease" styleClass="ttcIncrease" styleId="ttcIncrease" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.newTTCLocal" /><em> *</em></label>
								<html:text property="newTTCLocal" styleClass="newTTCLocal" styleId="newTTCLocal" />
								<html:hidden property="newTTCUSD" styleClass="newTTCUSD" styleId="newTTCUSD" />
							</li>
						</ol>	
						<h2>工资相关</h2>
						<ol class="auto">	
							<li>
								<label><bean:message bundle="performance" key="YERR.newBaseSalaryLocal" /><em> *</em></label>
								<html:text property="newBaseSalaryLocal" styleClass="newBaseSalaryLocal" styleId="newBaseSalaryLocal" />
								<html:hidden property="newBaseSalaryUSD" styleClass="newBaseSalaryUSD" styleId="newBaseSalaryUSD" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.newAnnualSalaryLocal" /><em> *</em></label>
								<html:text property="newAnnualSalaryLocal" styleClass="newAnnualSalaryLocal" styleId="newAnnualSalaryLocal" />
								<html:hidden property="newAnnualSalaryUSD" styleClass="newAnnualSalaryUSD" styleId="newAnnualSalaryUSD" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.newAnnualHousingAllowance" /></label>
								<html:text property="newAnnualHousingAllowance" styleClass="newAnnualHousingAllowance" styleId="newAnnualHousingAllowance" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.newAnnualChildrenEduAllowance" /></label>
								<html:text property="newAnnualChildrenEduAllowance" styleClass="newAnnualChildrenEduAllowance" styleId="newAnnualChildrenEduAllowance" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.newAnnualGuaranteedAllowanceLocal" /><em> *</em></label>
								<html:text property="newAnnualGuaranteedAllowanceLocal" styleClass="newAnnualGuaranteedAllowanceLocal" styleId="newAnnualGuaranteedAllowanceLocal" />
								<html:hidden property="newAnnualGuatanteedAllowanceUSD" styleClass="newAnnualGuatanteedAllowanceUSD" styleId="newAnnualGuatanteedAllowanceUSD" />
							</li>
							<li>
								<label>年薪<em> *</em></label>
								<html:text property="remark2" styleClass="remark2" styleId="remark2" />
							</li>
						</ol>	
						<h2>销售</h2>
						<ol class="auto">
							<li>
								<label><bean:message bundle="performance" key="YERR.newMonthlyTarget" /></label>
								<html:text property="newMonthlyTarget" styleClass="newMonthlyTarget" styleId="newMonthlyTarget" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.newQuarterlyTarget" /></label>
								<html:text property="newQuarterlyTarget" styleClass="newQuarterlyTarget" styleId="newQuarterlyTarget" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.newGPTarget" /></label>
								<html:text property="newGPTarget" styleClass="newGPTarget" styleId="newGPTarget" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.newTargetValueLocal" /></label>
								<html:text property="newTargetValueLocal" styleClass="newTargetValueLocal" styleId="newTargetValueLocal" />
								<html:hidden property="newTargetValueUSD" styleClass="newTargetValueUSD" styleId="newTargetValueUSD" />
							</li>
						</ol>	
						<h2>职级晋升</h2>
						<ol class="auto">
							<li>
								<label><bean:message bundle="performance" key="YERR.newJobGrade" /></label>
								<html:select property="newJobGrade" styleClass="newJobGrade" styleId="newJobGrade">
									<html:optionsCollection property="jobGrades" value="mappingId" label="mappingValue" />
								</html:select>
							</li>
						</ol>	
						<ol class="auto">	
							<li>
								<label><bean:message bundle="performance" key="YERR.newPositionEN" /></label>
								<html:text property="newPositionEN" styleClass="newPositionEN" styleId="newPositionEN" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.newPositionZH" /></label>
								<html:text property="newPositionZH" styleClass="newPositionZH" styleId="newPositionZH" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.newShareOptions" /></label>
								<html:select property="newShareOptions" styleClass="newShareOptions">
									<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
								</html:select>
							</li>
						</ol>
					</div>
					<ol class="auto">
						<li><a id="BONUS-PAYOUT-SUMMARY-DIV-LINK"><bean:message bundle="performance" key="YERR.link.bonus.payout.summary" /></a></li>
					</ol>
					<div class="def-div" id="BONUS-PAYOUT-SUMMARY-DIV">
						<ol class="auto">	
							<li>
								<label><bean:message bundle="performance" key="YERR.targetBonus" /></label>
								<html:text property="targetBonus" styleClass="targetBonus" styleId="targetBonus" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.proposedBonus" /></label>
								<html:text property="proposedBonus" styleClass="proposedBonus" styleId="proposedBonus" />
							</li>
							<li>
								<label><bean:message bundle="performance" key="YERR.proposedPayoutLocal" /></label>
								<html:text property="proposedPayoutLocal" styleClass="proposedPayoutLocal" styleId="proposedPayoutLocal" />
								<html:hidden property="proposedPayoutUSD" styleClass="proposedPayoutUSD" styleId="proposedPayoutUSD" />
							</li>
							<li>
								<label>年终奖</label>
								<html:text property="yearEndBonus" styleClass="yearEndBonus" styleId="yearEndBonus" />
							</li>
							<li>
								<label>实发年终奖</label>
								<html:text property="remark3" styleClass="remark3" styleId="remark3" />
							</li>
						</ol>
					</div>
				</fieldset>	
			</html:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($){
		// 初始化菜单选中样式
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Reports').addClass('selected');
		$('#menu_biz_Employee_Performance_Report').addClass('selected');
		
		div_display('EMP-SUMMARY-VIEW-DIV');
		div_display('SALARY-PACKAGE-STRUCTURE-DIV');
		div_display('YERR-DIV');
		div_display('BONUS-PAYOUT-SUMMARY-DIV');
		
		disableForm('managePerformance_form');
		
		$("#yearPerformanceRating").change( function(){
			getTTCIncrease_ajax($("#id").val(),$(this).val(),$("#yearPerformancePromotion").val(),$("#id").val());
		});
		
		$("#yearPerformancePromotion").change( function(){
			getTTCIncrease_ajax($("#id").val(),$("#yearPerformanceRating").val(), $(this).val())
		});
		
		$('#btnList').click(function(){
			if (agreest())
			link('performanceAction.do?proc=list_object'); 
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.managePerformance_form input.subAction').val() == 'viewObject'){
				// Enable form
				$('#YERR-DIV input, #YERR-DIV select, #BONUS-PAYOUT-SUMMARY-DIV input, #BONUS-PAYOUT-SUMMARY-DIV select').attr('disabled',false);
				// 更改Subaction
        		$('.managePerformance_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.managePerformance_form').attr('action', 'performanceAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
        		
        		flag = flag + validate("yearPerformanceRating", true, "select", 0, 0);
        		flag = flag + validate("recommendTTCIncrease", true, "currency", 6, 0);
        		flag = flag + validate("ttcIncrease", true, "currency", 6, 0);
        		flag = flag + validate("newTTCLocal", true, "currency", 20, 0);
        		flag = flag + validate("newBaseSalaryLocal", true, "currency", 20, 0);
        		flag = flag + validate("newAnnualSalaryLocal", true, "currency", 20, 0);
        		flag = flag + validate("newAnnualHousingAllowance", false, "currency", 20, 0);
        		flag = flag + validate("newAnnualChildrenEduAllowance", false, "currency", 20, 0);
        		flag = flag + validate("newAnnualGuaranteedAllowanceLocal", true, "currency", 20, 0);
        		flag = flag + validate("remark2", true, "currency", 20, 0);
        		flag = flag + validate("yearEndBonus", false, "currency", 20, 0);
        		flag = flag + validate("remark3", false, "currency", 20, 0);
        		
    			if(flag == 0){
    				enableForm('managePerformance_form');
    				submit('managePerformance_form');
    			}
        	}
		});
		
	})(jQuery);

	//Ajax获取TTC增长
	function getTTCIncrease_ajax( id, rating, promotion ){
		$.ajax({
			  url: "performanceAction.do?proc=getTTCIncrease_ajax",
			  type: "GET",
			  data: { "id" : id, "rating" : rating, "promotion" : promotion },
			  dataType: "JSON",
			  success: function(data){
				  $.each(data,function(k,v){  
					  $("#" + k).val(v);
				  });
			  }
		})
	};
</script>