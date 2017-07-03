<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.service.inf.biz.sb.SBHeaderService"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder sbHeaderHolder = (PagedListHolder) request.getAttribute("sbHeaderHolder");
%>
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 3%" class="header <%=sbHeaderHolder.getCurrentSortClass("headerId")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'headerId', '<%=sbHeaderHolder.getNextSortOrder("headerId")%>', 'sbHeaderTableWrapper');">
					<bean:message bundle="sb" key="sb.number.id" />
				</a>
			</th>
			<th style="width: 5%" class="header <%=sbHeaderHolder.getCurrentSortClass("employeeSBId")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'employeeSBId', '<%=sbHeaderHolder.getNextSortOrder("employeeSBId")%>', 'sbHeaderTableWrapper');">
					<bean:message bundle="sb" key="sb.solution.id" />
				</a>
			</th>
			<th style="width: 6%" class="header <%=sbHeaderHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'monthly', '<%=sbHeaderHolder.getNextSortOrder("monthly")%>', 'sbHeaderTableWrapper');">
					<bean:message bundle="sb" key="sb.monthly" />
				</a>
			</th>
			<th style="width: 12%" class="header <%=sbHeaderHolder.getCurrentSortClass("employeeSBId")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'employeeSBId', '<%=sbHeaderHolder.getNextSortOrder("employeeSBId")%>', 'tableWrapper');">
					<bean:message bundle="sb" key="sb.solution.name" />
				</a>
			</th>
			<logic:notEqual name="role" value="3">
				<th style="width: 7%" class="header <%=sbHeaderHolder.getCurrentSortClass("orderId")%>">
					<a onclick="submitForm('listHeader_form', null, null, 'orderId', '<%=sbHeaderHolder.getNextSortOrder("orderId")%>', 'sbHeaderTableWrapper');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
					</a>
				</th>
				<th style="width: 7%" class="header <%=sbHeaderHolder.getCurrentSortClass("contractId")%>">
					<a onclick="submitForm('listHeader_form', null, null, 'contractId', '<%=sbHeaderHolder.getNextSortOrder("contractId")%>', 'sbHeaderTableWrapper');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
					</a>
				</th>
			</logic:notEqual>	
			<th style="width: 10%" class="header <%=sbHeaderHolder.getCurrentSortClass("entityId")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'entityId', '<%=sbHeaderHolder.getNextSortOrder("entityId")%>', 'sbHeaderTableWrapper');">
					<bean:message bundle="security" key="security.entity" />
				</a>
			</th>
			<th style="width: 7%" class="header <%=sbHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'employeeId', '<%=sbHeaderHolder.getNextSortOrder("employeeId")%>', 'sbHeaderTableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
					<logic:equal name="role" value="3"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: 9%" class="header <%=sbHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'employeeNameZH', '<%=sbHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'sbHeaderTableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
					<logic:equal name="role" value="3"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th style="width: 9%" class="header <%=sbHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'employeeNameEN', '<%=sbHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'sbHeaderTableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
					<logic:equal name="role" value="3"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</a>
			</th>
			<th style="width: 6%" class="header <%=sbHeaderHolder.getCurrentSortClass("employStatus")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'employStatus', '<%=sbHeaderHolder.getNextSortOrder("employStatus")%>', 'sbHeaderTableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.status" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.status" /></logic:equal>
					<logic:equal name="role" value="3"><bean:message bundle="public" key="public.employee2.status" /></logic:equal>
				</a>
			</th>
			<th style="width: 7%" class="header <%=sbHeaderHolder.getCurrentSortClass("sbStatus")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'sbStatus', '<%=sbHeaderHolder.getNextSortOrder("sbStatus")%>', 'sbHeaderTableWrapper');">
					<bean:message bundle="sb" key="sb.status" />
				</a>
			</th>
			<th style="width: 6%" class="header <%=sbHeaderHolder.getCurrentSortClass("amountCompany")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'amountCompany', '<%=sbHeaderHolder.getNextSortOrder("amountCompany")%>', 'sbHeaderTableWrapper');">
					<bean:message bundle="sb" key="sb.company" />
				</a>
			</th>
			<th style="width: 6%" class="header <%=sbHeaderHolder.getCurrentSortClass("amountPersonal")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'amountPersonal', '<%=sbHeaderHolder.getNextSortOrder("amountPersonal")%>', 'sbHeaderTableWrapper');">
					<bean:message bundle="sb" key="sb.personal" />
				</a>
			</th>
			<th style="width: 5%" class="header <%=sbHeaderHolder.getCurrentSortClass("countItemId")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'countItemId', '<%=sbHeaderHolder.getNextSortOrder("countItemId")%>', 'sbHeaderTableWrapper');">
					<bean:message bundle="sb" key="sb.item.number" />
				</a>
			</th>
			<th style="width: 5%" class="header <%=sbHeaderHolder.getCurrentSortClass("additionalStatus")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'additionalStatus', '<%=sbHeaderHolder.getNextSortOrder("additionalStatus")%>', 'sbHeaderTableWrapper');">
					<bean:message bundle="public" key="public.status" />
				</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="sbHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="sbHeaderVO" name="sbHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<logic:notEqual name="role" value="3">
						<td class="left"><a onclick="link('vendorPaymentAction.do?proc=to_sbDetail&vendorId=<bean:write name="sbHeaderVO" property="encodedVendorId"/>&monthly=<bean:write name="sbHeaderVO" property="monthly"/>&headerId=<bean:write name="sbHeaderVO" property="encodedId"/>&additionalStatus=<bean:write name="sbHeaderVO" property="additionalStatus"/>');"><bean:write name="sbHeaderVO" property="headerId" /></a></td>
					</logic:notEqual>
					<logic:equal name="role" value="3">
						<td class="left"><a onclick="link('sbAction.do?proc=to_sbDetail_inVendor&vendorId=<bean:write name="sbHeaderVO" property="encodedVendorId"/>&monthly=<bean:write name="sbHeaderVO" property="monthly"/>&headerId=<bean:write name="sbHeaderVO" property="encodedId"/>&additionalStatus=<bean:write name="sbHeaderVO" property="additionalStatus"/>');"><bean:write name="sbHeaderVO" property="headerId" /></a></td>
					</logic:equal>
					<td class="left"><bean:write name="sbHeaderVO" property="employeeSBId" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="monthly" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="employeeSBName" /></td>
					<logic:notEqual name="role" value="3">
						<td class="left"><a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="sbHeaderVO" property="encodedOrderId"/>');"><bean:write name="sbHeaderVO" property="orderId" /></a></td>
						<td class="left"><a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="sbHeaderVO" property="encodedContractId"/>');"><bean:write name="sbHeaderVO" property="contractId" /></a></td>
					</logic:notEqual>	
					<td class="left"><bean:write name="sbHeaderVO" property="decodeEntityId" /></td>
					<td class="left"><a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="sbHeaderVO" property="encodedEmployeeId"/>');"><bean:write name="sbHeaderVO" property="employeeId" /></a></td>
					<td class="left"><bean:write name="sbHeaderVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="employeeNameEN" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="decodeEmployStatus" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="decodeSbStatus" /></td>
					<td class="right"><bean:write name="sbHeaderVO" property="decodeAmountCompany" /></td>
					<td class="right"><bean:write name="sbHeaderVO" property="decodeAmountPersonal" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="countItemId"/></td>
					<logic:notEqual name="role" value="3">
						<td class="left"><bean:write name="sbHeaderVO" property="decodeAdditionalStatus" /></td>
					</logic:notEqual>	
					<logic:equal name="role" value="3">
						<logic:equal name="sbHeaderVO" property="additionalStatus" value="2"><td class="left">可操作</td></logic:equal>
						<logic:notEqual name="sbHeaderVO" property="additionalStatus" value="2"><td class="left">不可操作</td></logic:notEqual>
					</logic:equal>	
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="sbHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="16" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="sbHeaderHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="sbHeaderHolder" property="indexStart" /> - <bean:write name="sbHeaderHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbHeaderHolder" property="firstPage" />', null, null, 'sbHeaderTableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbHeaderHolder" property="previousPage" />', null, null, 'sbHeaderTableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbHeaderHolder" property="nextPage" />', null, null, 'sbHeaderTableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbHeaderHolder" property="lastPage" />', null, null, 'sbHeaderTableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="sbHeaderHolder" property="realPage" />/<bean:write name="sbHeaderHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
		
		// 确认事件
		$('#btnSBConfirm').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定确认社保公积金？")){
					submitForm('listHeader_form', 'confirmObjects', null, null, null, 'sbHeaderTableWrapper');
				}
			}else{
				alert("请选择要确认的记录！");
			}
		});
	})(jQuery);	
</script>
