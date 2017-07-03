<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder timesheetHeaderHolder = ( PagedListHolder ) request.getAttribute( "timesheetHeaderHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "5" : "8" %>%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("allowanceId")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'allowanceId', '<%=timesheetHeaderHolder.getNextSortOrder("allowanceId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.allowance.id" /></a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "5" : "8" %>%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'monthly', '<%=timesheetHeaderHolder.getNextSortOrder("monthly")%>', 'tableWrapper');"><bean:message bundle="business" key="business.allowance.import.monthly" /></a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "5" : "8" %>%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'employeeId', '<%=timesheetHeaderHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: 15%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'employeeNameZH', '<%=timesheetHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th style="width: 15%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'employeeNameEN', '<%=timesheetHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</a>
			</th>
			
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "5" : "8" %>%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("itemNo")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'itemNo', '<%=timesheetHeaderHolder.getNextSortOrder("itemNo")%>', 'tableWrapper');">
					<bean:message bundle="business" key="business.ts.allowance.item.no" />
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "5" : "8" %>%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("itemNameZH")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'itemNameZH', '<%=timesheetHeaderHolder.getNextSortOrder("itemNameZH")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ts.allowance.item.name.cn" /></a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "5" : "8" %>%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("itemNameEN")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'itemNameEN', '<%=timesheetHeaderHolder.getNextSortOrder("itemNameEN")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ts.allowance.item.name.en" /></a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "5" : "8" %>%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("base")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'base', '<%=timesheetHeaderHolder.getNextSortOrder("base")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ts.allowance.money" /></a>
			</th>
			
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "5" : "8" %>%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("orderId")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'orderId', '<%=timesheetHeaderHolder.getNextSortOrder("orderId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "5" : "8" %>%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'contractId', '<%=timesheetHeaderHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
				</a>
			</th>
			
			<%-- <logic:equal name="role" value="1">
				<th style="width: 5%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("clientId")%>">
					<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'clientId', '<%=timesheetHeaderHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">客户ID</a>
				</th>
				<th style="width: 10%" class="header-nosort">
					客户名称
				</th>
			</logic:equal> --%>
			
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="public" key="public.modify.by" />
			</th>
			<th style="width: 20%" class="header-nosort">
				<bean:message bundle="public" key="public.modify.date" />
			</th>
			<%-- <th style="width: 8%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'status', '<%=timesheetHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th> --%>
		</tr>
	</thead>
	<logic:notEqual name="timesheetHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="timesheetHeaderVO" name="timesheetHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td >
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="timesheetHeaderVO" property="allowanceId"/>" name="chkSelectRow[]" value="<bean:write name="timesheetHeaderVO" property="allowanceIdEncodedId"/>" />
					</td>
					<td class="left">
						<logic:equal name="batchStatus" value="2">
							<bean:write name="timesheetHeaderVO" property="allowanceId" />
						</logic:equal>
						<logic:notEqual  name="batchStatus" value="2">
							<a href="#" onclick="resetAllowanceBase('<bean:write name="timesheetHeaderVO" property="allowanceId" />','<bean:write name="timesheetHeaderVO" property="itemNo" />','<bean:write name="timesheetHeaderVO" property="base" />')"><bean:write name="timesheetHeaderVO" property="allowanceId" /></a>
						</logic:notEqual>
					</td>	
					<td class="left"><bean:write name="timesheetHeaderVO" property="monthly" /></td>
					<td class="left">
						<a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="timesheetHeaderVO" property="encodedEmployeeId" />');"><bean:write name="timesheetHeaderVO" property="employeeId" /></a>
					</td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="employeeNameEN" /></td>
					
					<td class="left"><bean:write name="timesheetHeaderVO" property="itemNo" /></td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="itemNameZH" /></td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="itemNameEN" /></td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="base" /></td>
					
					<td class="left">
						<a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="timesheetHeaderVO" property="encodedOrderId" />');"><bean:write name="timesheetHeaderVO" property="orderId" /></a>
					</td>
					<td class="left">
						<a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="timesheetHeaderVO" property="encodedContractId" />');"><bean:write name="timesheetHeaderVO" property="contractId" /></a>
					</td>
					<%-- <logic:equal name="role" value="1">
						<td class="left">
							<a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="timesheetHeaderVO" property="encodedClientId" />');"><bean:write name="timesheetHeaderVO" property="clientId" /></a>
						</td>
						<td class="left"><bean:write name="timesheetHeaderVO" property="clientName" /></td>
					</logic:equal> --%>
					<td class="left"><bean:write name="timesheetHeaderVO" property="decodeModifyBy" /></td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="decodeModifyDate" /></td>
					<%-- <td class="left">
						<bean:write name="timesheetHeaderVO" property="decodeStatus" />
						<logic:equal name="timesheetHeaderVO" property="status" value="2" >
							<logic:equal name="isHRFunction" value="1" >
								&nbsp;&nbsp;<img src='images/magnifer.png' onclick=popupWorkflow('<bean:write name="timesheetHeaderVO" property="workflowId"/>'); />
							</logic:equal>
						</logic:equal>
					</td> --%>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="timesheetHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="16" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="timesheetHeaderHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="timesheetHeaderHolder" property="indexStart" /> - <bean:write name="timesheetHeaderHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="timesheetHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="timesheetHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="timesheetHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="timesheetHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="timesheetHeaderHolder" property="realPage" />/<bean:write name="timesheetHeaderHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>

<div class="modal midsize content hide" id="resetAllowanceBaseDiv">
    <div class="modal-header" id="clientHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#resetAllowanceBaseDiv').addClass('hide');$('#shield').hide();">×</a>
        <label id="importExcelTitleLableId">考勤津贴金额修改</label>
    </div>
    <div class="modal-body">
    	<div class="top">
			<table style="width: 100%">
				<tr>
					<td>
						<label>科目编号:</label> 
					</td>
					<td>
						<label><span id="itemNoPop"></span></label> 
					</td>
					<td>
						<label>金额:</label> 
					</td>
					<td>
						<input type="text" id="allowanceBasePop" maxlength="10" class="allowanceBasePop"/>
						<input type="hidden" id="allowanceId"/>
					</td>
				</tr>
			</table>
	    </div>
    </div>
    <div class="modal-footer" style="text-align: right">
    	<input type="button" id="btnAdd" name="btnAdd" value="保存" onclick="updateAllowanceBase()">
    </div>
</div>

<script type="text/javascript">
	function resetAllowanceBase(id,no,base){
		
		$("#itemNoPop").html(no);
		$("#allowanceBasePop").val(base);
		$("#allowanceId").val(id);
		$('#resetAllowanceBaseDiv').removeClass('hide');
    	$('#shield').show();
	}
	
	function updateAllowanceBase(){
		
		if (validate('allowanceBasePop', true, 'numeric', 0, 0, 0, 0) == 0) {
			$.ajax({
				url: "timesheetAllowanceHeaderAction.do?proc=updateAllowanceBase",
				type: 'POST', 
				data: 'allowanceId='+$("#allowanceId").val()+'&base='+$("#allowanceBasePop").val(),
				dataType: 'json',
				success: function(data, status){
					
					if(data.status == 'success'){
						$('#messageWrapper').html('<div class="message success fadable">保存成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
						messageWrapperFada();
						$('#resetAllowanceBaseDiv').addClass('hide');
				    	$('#shield').hide();
				    	window.location.href = "timesheetAllowanceHeaderAction.do?proc=list_object&batchId="+$("#batchId").val();;
					}
				} 
			});
		}
	}
	
	// 弹出模态窗口
	function popupExcelImport(){
		$('#resetAllowanceBaseDiv').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#resetAllowanceBaseDiv').addClass('hide');
	    	$('#shield').hide();
	    } 
	});
</script>