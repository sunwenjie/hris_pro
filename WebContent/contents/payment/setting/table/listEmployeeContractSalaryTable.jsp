<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<%@ page import=" com.kan.hro.web.actions.biz.employee.EmployeeContractSalaryAction"%>
<%@ page import=" com.kan.hro.web.actions.biz.employee.EmployeeAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder employeeContractSalaryHolder = (PagedListHolder) request.getAttribute("employeeContractSalaryHolder");
%>

<div class="inner">
	<div id="messageWrapper">
		<logic:present name="MESSAGE_HEADER">
			<logic:present name="MESSAGE">
				<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
					<bean:write name="MESSAGE" />
	    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
				</div>
			</logic:present>
		</logic:present>
	</div>
	<!-- top -->
	<div class="top">
		<kan:auth right="salarysetting" action="<%=EmployeeContractSalaryAction.accessActionForEmployeeSalary%>">
			<input type="button" class="function" id="btnAdd" name="btnAdd" value="<bean:message bundle="payment" key="payment.employee.salary.setting" />" onclick="addEmployeeContractSalarys();" />
		</kan:auth>	
		<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
		<kan:auth right="export" action="<%=EmployeeContractSalaryAction.accessActionForEmployeeSalary%>">
			<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm( 'list_form', 'downloadObjects', null, null );"><img src="images/appicons/excel_16.png" /></a> 
		</kan:auth>
	</div>
	<!-- top -->
	<div id="tableWrapper">
	<!-- Include table jsp 包含tabel对应的jsp文件 -->
	<table class="table hover" id="resultTable">
		<thead>
			<tr>
				<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
				</th>
				<th style="width: 4%" class="header  <%=employeeContractSalaryHolder.getCurrentSortClass("employeeSalaryId")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeSalaryId', '<%=employeeContractSalaryHolder.getNextSortOrder("employeeSalaryId")%>', 'search-results');"><bean:message bundle="payment" key="payment.employee.salary.header.id" /></a>
				</th>
				<th style="width: 7%" class="header  <%=employeeContractSalaryHolder.getCurrentSortClass("employeeId")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeId', '<%=employeeContractSalaryHolder.getNextSortOrder("employeeId")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
					</a>
				</th> 
				<th style="width: 10%" class="header  <%=employeeContractSalaryHolder.getCurrentSortClass("employeeNameZH")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeNameZH', '<%=employeeContractSalaryHolder.getNextSortOrder("employeeNameZH")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
					</a>
				</th> 
				<th style="width: 10%" class="header  <%=employeeContractSalaryHolder.getCurrentSortClass("employeeNameEN")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeNameEN', '<%=employeeContractSalaryHolder.getNextSortOrder("employeeNameEN")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
					</a>
				</th>
				<th style="width: <logic:equal name="role" value="1">26</logic:equal><logic:equal name="role" value="2">33</logic:equal>%" class="header-nosort"><bean:message bundle="public" key="public.note" /></th>
				<th style="width: 10%" class="header-nosort"><bean:message bundle="payment" key="payment.employee.salary" /></th>
				<logic:equal name="role" value="1">
					<th style="width: 7%" class="header  <%=employeeContractSalaryHolder.getCurrentSortClass("clientId")%>">
						<a onclick="submitForm('list_form', null, null, 'clientId', '<%=employeeContractSalaryHolder.getNextSortOrder("clientId")%>', 'search-results');">客户ID</a>
					</th>
				</logic:equal>
				<th style="width: 7%" class="header  <%=employeeContractSalaryHolder.getCurrentSortClass("orderId")%>">
					<a onclick="submitForm('list_form', null, null, 'orderId', '<%=employeeContractSalaryHolder.getNextSortOrder("orderId")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.name" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.name" /></logic:equal>
					</a>
				</th>
<%-- 				<th style="width: 7%" class="header  <%=employeeContractSalaryHolder.getCurrentSortClass("contractId")%>"> --%>
<%-- 					<a onclick="submitForm('list_form', null, null, 'contractId', '<%=employeeContractSalaryHolder.getNextSortOrder("contractId")%>', 'search-results');"><logic:equal name="role" value="1">协议</logic:equal><logic:equal name="role" value="2">合同</logic:equal>ID</a> --%>
<!-- 				</th> -->
				<th style="width: 7%" class="header  <%=employeeContractSalaryHolder.getCurrentSortClass("startDate")%>">
					<a onclick="submitForm('list_form', null, null, 'startDate', '<%=employeeContractSalaryHolder.getNextSortOrder("startDate")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.start.date" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.start.date" /><</logic:equal>
					</a>
				</th>
				<th style="width: 7%" class="header  <%=employeeContractSalaryHolder.getCurrentSortClass("endDate")%>">
					<a onclick="submitForm('list_form', null, null, 'endDate', '<%=employeeContractSalaryHolder.getNextSortOrder("endDate")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.end.date" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.end.date" /><</logic:equal>
					</a>
				</th>
				<th style="width: 6%" class="header  <%=employeeContractSalaryHolder.getCurrentSortClass("status")%>">
					<a onclick="submitForm('list_form', null, null, 'status', '<%=employeeContractSalaryHolder.getNextSortOrder("status")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.status" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.status" /><</logic:equal>
					</a>
				</th>
				<th style="width: 6%" class="header-nosort"><bean:message bundle="public" key="public.oper" /></th>
			</tr>
		</thead>
		<logic:notEqual name="employeeContractSalaryHolder" property="holderSize" value="0">
			<tbody>
				<logic:iterate id="employeeContractSalaryVO" name="employeeContractSalaryHolder" property="source" indexId="number">
					<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
						<td><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="employeeContractSalaryVO" property="employeeSalaryId"/>" name="chkSelectRow[]" value="<bean:write name='employeeContractSalaryVO' property='contractId'/>-<bean:write name='employeeContractSalaryVO' property='employeeSalaryId'/>" /></td>
						<td class="left">
						<a onclick="link('employeeContractSalaryAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSalaryVO" property="encodedId"/>&comefrom=setting');">
							<bean:write name="employeeContractSalaryVO" property="employeeSalaryId" />
						</a>
						</td>								
						<td class="left">
						<a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSalaryVO" property="encodedEmployeeId"/>');">
							<bean:write name="employeeContractSalaryVO" property="employeeId" />
						</a>
						
						</td>								
						<td class="left"><bean:write name="employeeContractSalaryVO" property="employeeNameZH" /></td>
						<td class="left"><bean:write name="employeeContractSalaryVO" property="employeeNameEN" /></td>
						<td class="left">
							<logic:notEmpty name="employeeContractSalaryVO" property="itemId">
								<bean:write name="employeeContractSalaryVO" property="decodeItemId" /> 
								（
									<logic:greaterThan name="employeeContractSalaryVO" property="baseFrom" value="0">
										<bean:write name="employeeContractSalaryVO" property="decodeBaseFrom" />
										<logic:greaterEqual name="employeeContractSalaryVO" property="percentage" value="0">
											&nbsp;*&nbsp;<bean:write name="employeeContractSalaryVO" property="percentage" />%
										</logic:greaterEqual>
										<logic:greaterEqual name="employeeContractSalaryVO" property="fix" value="0">
											&nbsp;+&nbsp;<bean:write name="employeeContractSalaryVO" property="fix" />
										</logic:greaterEqual>
									</logic:greaterThan>
									<logic:lessThan name="employeeContractSalaryVO" property="baseFrom" value="1">
										<logic:greaterEqual name="employeeContractSalaryVO" property="base" value="0">
											<bean:write name="employeeContractSalaryVO" property="base" />
										</logic:greaterEqual> 
									</logic:lessThan>
								 / 
									<logic:notEqual name="employeeContractSalaryVO" property="cycle" value="0">
										<logic:notEqual name="employeeContractSalaryVO" property="cycle" value="13">每</logic:notEqual>
										<bean:write name="employeeContractSalaryVO" property="decodeCycle" />
									</logic:notEqual>
								）
								<bean:write name="employeeContractSalaryVO" property="startDate" />
								<logic:notEmpty name="employeeContractSalaryVO" property="endDate"> ~ <bean:write name="employeeContractSalaryVO" property="endDate" /></logic:notEmpty>
								<logic:equal name="employeeContractSalaryVO" property="status" value="2"><span class="highlight">（停用）</span></logic:equal>
								<logic:empty name="employeeContractSalaryVO" property="status"><span class="highlight">（停用）</span></logic:empty>
							</logic:notEmpty>
						</td>
						<td class="left"><bean:write name="employeeContractSalaryVO" property="decodeItemId" /></td>
						<logic:equal name="role" value="1">
							<td class="left"><a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSalaryVO" property="encodedClientId"/>');" title="<bean:write name="employeeContractSalaryVO" property="clientName" />"><bean:write name="employeeContractSalaryVO" property="clientId" /></a></td>
						</logic:equal>
						<td class="left"><a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSalaryVO" property="encodedOrderId"/>');"><bean:write name="employeeContractSalaryVO" property="orderName" /></a></td>								
<%-- 						<td class="left"><a title='<bean:write name="employeeContractSalaryVO" property="contractName" />' onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSalaryVO" property="encodedContractId"/>');"><bean:write name="employeeContractSalaryVO" property="contractId" /></a></td>		 --%>
						<td class="left"><bean:write name="employeeContractSalaryVO" property="contractStartDate" /></td>			
						<td class="left"><bean:write name="employeeContractSalaryVO" property="contractEndDate" /></td>							
						<td class="left">
							<bean:write name="employeeContractSalaryVO" property="decodeContractStatus" />
						</td>
						<td class="left">
						<kan:auth right="new" action="<%=EmployeeContractSalaryAction.accessActionForEmployeeSalary%>">
							<a style="text-decoration:none;" onclick="addEmployeeContractSalary('<bean:write name="employeeContractSalaryVO" property="contractId"/>');" class="kanhandle">
								<img src="images/add.png" title="<bean:message bundle="payment" key="payment.employee.salary.new" />" />
							</a>
						</kan:auth>	
							<logic:notEmpty name="employeeContractSalaryVO" property="employeeSalaryId">
								<logic:equal name="employeeContractSalaryVO" property="status" value="1" >
								<kan:auth right="stop" action="<%=EmployeeContractSalaryAction.accessActionForEmployeeSalary%>">
									<a style="text-decoration:none;" onclick="if(confirm('<bean:message bundle="payment" key="payment.employee.salary.stop.tips" />')){link('employeeContractSalaryAction.do?proc=disable_object&employeeSalaryId=<bean:write name="employeeContractSalaryVO" property="encodedId"/>');}" class="kanhandle">
										<img src="images/deduct.png" title="<bean:message bundle="payment" key="payment.employee.salary.stop" />" />
									</a> &nbsp;
								</kan:auth>
								</logic:equal>
								<kan:auth right="modify" action="<%=EmployeeContractSalaryAction.accessActionForEmployeeSalary%>">
									<a style="text-decoration:none;" onclick="modifyEmployeeContractSalary('<bean:write name="employeeContractSalaryVO" property="employeeSalaryId"/>');" class="kanhandle">
										<img src="images/modify.png" title="<bean:message bundle="payment" key="payment.employee.salary.edit" />" />
									</a>
								</kan:auth>
							</logic:notEmpty>
						</td>
					</tr>
				</logic:iterate>
			</tbody>
		</logic:notEqual>
		<logic:present name="employeeContractSalaryHolder">
			<tfoot>
				<tr class="total">
					<td colspan="14" class="left">
						<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="employeeContractSalaryHolder" property="holderSize" /> </label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="employeeContractSalaryHolder" property="indexStart" /> - <bean:write name="employeeContractSalaryHolder" property="indexEnd" /> </label> 
						<label>&nbsp;&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="employeeContractSalaryHolder" property="firstPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.first" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="employeeContractSalaryHolder" property="previousPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.previous" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="employeeContractSalaryHolder" property="nextPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.next" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="employeeContractSalaryHolder" property="lastPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.last" /></a></label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="employeeContractSalaryHolder" property="realPage" />/<bean:write name="employeeContractSalaryHolder" property="pageCount" /> </label>&nbsp;
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="employeeContractSalaryHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
					</td>
				</tr>
			</tfoot>
		</logic:present>
	</table>
	</div>
	<div class="bottom">
		<p>
	</div>
</div>
						
<script type="text/javascript">
	(function($) {
		<logic:present name="MESSAGE">
			messageWrapperFada();
		</logic:present>
		
		kanList_init();
		kanCheckbox_init();
		//列表双击
		$("tbody tr").dblclick(function(){ 
			 kanlist_dbclick($(this),null);
		}); 
	})(jQuery);	
	
	// 弹出模态窗口_添加(单条)
	function addEmployeeContractSalary(contractId){
		// 重置Popup区域
		resetPopup();
		// 设置派送信息ID
		$('.manageEmployeeContractSalary_contractId').val(contractId);
		loadHtmlWithRecall('#managePopupWrapper', 'employeeContractSalaryAction.do?proc=get_object_ajax_popup&contractId=' + contractId, null, 'showPopup()');
	};
	
	// 弹出模态窗口_添加（多条）
	function addEmployeeContractSalarys(){
		var selectedIds =  $('.list_form input[id="selectedIds"]').val();
		
		if(selectedIds == null || selectedIds.trim() == '')
		{
			alert("请选择<logic:equal name='role' value='1'>雇员</logic:equal><logic:equal name='role' value='2'>员工</logic:equal>记录！");
		}
		else if(selectedIds.split(',').length == 1){
			var contractId = selectedIds.split(',')[0];
			var contractId = contractId.substring(0, contractId.length - 1);
			addEmployeeContractSalary(contractId);
		}
		else
		{
			// 清除派送信息ID、生效日期、结束日期
			$('.manageEmployeeContractSalary_contractId').val('');
			$('#startDate').val('');
			$('#endDate').val('');
			// 计薪方式默认按月，折算方式默认按月
			$('.manageEmployeeContractSalary_salaryType').val('1');
			$('.manageEmployeeContractSalary_salaryType').change();
			$('.manageEmployeeContractSalary_divideType').val('2');
			showPopup();
		}
	};
	
	// 弹出模态窗口_修改
	function modifyEmployeeContractSalary(employeeSalaryId){
		// 清楚派送信息ID
		$('.manageEmployeeContractSalary_contractId').val('');
		
		$.ajax({
			url:'employeeContractSalaryAction.do?proc=get_object_json&employeeSalaryId=' + employeeSalaryId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractSalary_contractId').val(data.contractId);
				$('.manageEmployeeContractSalary_employeeSalaryId').val(data.employeeSalaryId);
				$('.manageEmployeeContractSalary_itemId').val(data.itemId);
				$('.manageEmployeeContractSalary_salaryType').val(data.salaryType);
				$('.manageEmployeeContractSalary_divideType').val(data.divideType);
				$('.manageEmployeeContractSalary_base').val(data.base);
				$('.manageEmployeeContractSalary_baseFrom').val(data.baseFrom);
				$('.manageEmployeeContractSalary_percentage').val(data.percentage);
				$('.manageEmployeeContractSalary_fix').val(data.fix);
				$('.manageEmployeeContractSalary_resultCap').val(data.resultCap);
				$('.manageEmployeeContractSalary_resultFloor').val(data.resultFloor);
				$('.manageEmployeeContractSalary_cycle').val(data.cycle);
				$('.manageEmployeeContractSalary_formular').val(data.formular);
				$('.manageEmployeeContractSalary_showToTS').val(data.showToTS);
				$('.manageEmployeeContractSalary_description').val(data.description);
				$('#tempStartDate').val(data.startDate);
				$('#tempEndDate').val(data.endDate);
				$('#startDate').val(data.startDate);
				$('#endDate').val(data.endDate);
				// 修改subAction为修改
				$('.employeeContractSalary_form input[id="subAction"]').val('modifyObject');

				showPopup();
			}
		});
		
	};
	
	// 隐藏弹出框
	function hidePopup(){
		$('#employeeContractSalaryModalId').addClass('hide');
    	$('#shield').hide();
	}
	
	// 显示弹出框
	function showPopup(){
		$('#employeeContractSalaryModalId').removeClass('hide');
    	$('#shield').show();
	}
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	hidePopup();
	    } 
	});

	function forward() {
		var value = Number($('#forwardPage').val());
		// 如果页数无效自动跳转到第一页
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage = Number($('.forwardPage').val()) - 1;
		submitForm('list_form', null, forwardPage, null, null, 'search-results');
	}
</script>