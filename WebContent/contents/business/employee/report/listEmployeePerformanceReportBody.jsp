<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeReportAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label>Employee Performance Evaluation Report</label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchPerformanceForm', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="performanceAction.do?proc=list_object" method="post" styleClass="searchPerformanceForm">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="pagedListHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="pagedListHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="pagedListHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="pagedListHolder" property="selectedIds" />" />
				<input type="hidden" name="rt" id="reportType" value="performance" />
				<input type="hidden" name="subAction" id="subAction" class="subAction" value="" />
				<html:hidden property="selectBUFunctionName" styleClass="selectBUFunctionName" styleId="selectBUFunctionName" />
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
							<html:text property="chineseName" maxlength="100" styleClass="search_chineseName" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
							</label>
							<html:text property="fullName" maxlength="100" styleClass="search_fullName" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.performance.yearly" /></label>
							<html:select property="yearly" styleClass="search_yearly">
								<html:optionsCollection property="years" value="mappingId" label="mappingValue" />
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
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="generate" action="<%=EmployeeReportAction.ACCESS_ACTION_PERFORMANCE%>">
					<input type="button" class="function" id="syncBtn" name="syncBtn" value="<bean:message bundle="public" key="button.generate" />" onclick="syncBtnClick();" />
				</kan:auth>
				<!-- salarysetting 薪资调整权限 -->
				<kan:auth right="salarysetting" action="<%=EmployeeReportAction.ACCESS_ACTION_PERFORMANCE%>">
					<input type="button" class="function" name="genSalaryAdjust" id="genSalaryAdjust" value="<bean:message bundle="public" key="button.final.confirm" />" />
					<input type="button" class="function" name="confirmFinalRating" id="confirmFinalRating" value="<bean:message bundle="public" key="button.confirm.final.rating" />" />
				</kan:auth>
				
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
					
				<kan:auth right="export" action="<%=EmployeeReportAction.ACCESS_ACTION_PERFORMANCE%>"> 
					<a id="exportZip" name="exportZip" class="commonTools" onclick="export_zip();" ><img src="images/appicons/zip_16.png" /></a>
			    </kan:auth>
			    	
			    <kan:auth right="export" action="<%=EmployeeReportAction.ACCESS_ACTION_PERFORMANCE%>"> 
					<a id="exportExcel" name="exportExcel" class="commonTools" style="margin-right: 8px;" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" 
					onclick="popupSelectBUFunction('${selectBUFunctionOption}');"><img src="images/appicons/excel_16.png" /></a> 
			    </kan:auth>  
			    
				<kan:auth right="import" action="<%=EmployeeReportAction.ACCESS_ACTION_PERFORMANCE%>"> 
					<logic:empty name="canImport">
						<img style="float:right" src="images/import.png" onclick="popupExcelImport();" title="<bean:message bundle="public" key="img.title.tips.import.excel" />"> 
			   		</logic:empty>
			    </kan:auth> 
			</div>
			<!-- top -->
			<div id="tableWrapper" style="overflow: hidden;">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/employee/report/table/listEmployeePerformanceReportBodyTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/importPerformanceExcel.jsp" flush = "true" />
	<jsp:include page="/popup/selectBUFunction.jsp" flush = "true" />
</div>	

<script type="text/javascript">
	(function($) {
		// JS of the List 系统自动生成js代码 
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Reports').addClass('selected');
		$('#menu_biz_Employee_Performance_Report').addClass('selected');
		
		kanList_init();
		kanCheckbox_init();
		
		useFixColumn();
		var colspanSize = 64;
		if( $('tbody#mytab tr').find('td').length != 0 ){
			colspanSize = $('tbody#mytab tr').find('td').length;
		}
		$('tr.total td').attr( 'colspan', colspanSize );
		
		// 发送调薪通知信
		$("#sendAdjustmentSalaryNoticeLetter").click( function(){
			var yearly = $(".search_yearly").val();
			if(yearly==''||yearly=='0'){
				alert("<bean:message bundle="public" key="popup.performance.choose.yearly" />");
				$("#filterRecords").click();
				$('.search_yearly').addClass("error");
				return;
			}else{
				if(confirm("发送调薪通知信？")){
					$('#shield').show();
					$.ajax({
						url: "performanceAction.do?proc=sendAdjustmentSalaryNoticeLetter",
						type: "GET",
						dataType: 'html',
						async: true,
						success: function(html){
							alert(html);
							$('#shield').hide();
						}
					})
				}
			}
		});
		
		// 确认最终评分
		$("#confirmFinalRating").click( function(){
			var yearly = $(".search_yearly").val();
			if(yearly==''||yearly=='0'){
				alert("<bean:message bundle="public" key="popup.performance.choose.yearly" />");
				$("#filterRecords").click();
				$('.search_yearly').addClass("error");
				return;
			}else{
				if(confirm("<bean:message bundle="public" key="popup.performance.rating.confirm" /> ")){
					$('#shield').show();
					$.ajax({
						url: "performanceAction.do?proc=confirmFinalRating&yearly="+yearly,
						type: "GET",
						dataType: 'html',
						async: true,
						success: function(html){
							alert(html);
							$('#shield').hide();
						}
					})
				}
			}
		});
		
		$("#genSalaryAdjust").click(function(){
			var yearly = $(".search_yearly").val();
			if(yearly==''||yearly=='0'){
				alert("<bean:message bundle="public" key="popup.performance.choose.yearly" />");
				$("#filterRecords").click();
				$('.search_yearly').addClass("error");
				return;
			}else{
				$.post("performanceAction.do?proc=getAdjustmentCount",{"yearly":yearly},function(data){
					if(confirm(data)){
						$('#shield').show();
						$.post("performanceAction.do?proc=confirmAdjust",{"yearly":yearly},function(count){
							alert("success!");
							$('#shield').hide();
						},"text");
					}else{
						return;
					}
				},"text");
			}
		});
	})(jQuery); 
	
	function export_zip(){
		$('.searchPerformanceForm').attr('action','performanceAction.do?proc=exportZip');
		$('.searchPerformanceForm').submit();
		$('.searchPerformanceForm').attr('action','performanceAction.do?proc=list_object');
	};
	
	function resetForm() {
	   var oForm = $('.searchPerformanceForm');
	   oForm.find('input:visible').val('');
	   oForm.find('select:visible').filter(':hidden').val('0');
	};
	
	function syncBtnClick(){
		if(!confirm('<bean:message bundle="public" key="popup.confirm.generate.performance.template" />')){
			return;
		}
		$('.searchPerformanceForm').attr('action', 'performanceAction.do?proc=sync_object');
		submitForm('searchPerformanceForm', "submitObjects", null, null, null, 'tableWrapper');
		$('#selectedIds').val('');
		$('.searchPerformanceForm').attr('action', 'performanceAction.do?proc=list_object');
	};
</script>
