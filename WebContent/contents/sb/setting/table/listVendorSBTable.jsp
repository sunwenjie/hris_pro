<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final PagedListHolder employeeContractSBHolder = (PagedListHolder) request.getAttribute("employeeContractSBHolder");
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
		<!-- <input type="button" class="function" id="btnAdd" name="btnAdd" value="社保公积金设置" onclick="addEmployeeContractSBs();" /> -->
		<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
	</div>
	<!-- top -->
	<div id="tableWrapper">
	<!-- Include table jsp 包含tabel对应的jsp文件 -->
	<table class="table hover" id="resultTable">
		<thead>
			<tr>
				<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" disabled="disabled" /></th>
				<th style="width: 4%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("employeeSBId")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeSBId', '<%=employeeContractSBHolder.getNextSortOrder("employeeSBId")%>', 'search-results');"><bean:message bundle="sb" key="sb.number.id" /></a>
				</th>
				<th style="width: 6%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("employeeId")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeId', '<%=employeeContractSBHolder.getNextSortOrder("employeeId")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
					</a>
				</th> 
				<th style="width: 8%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("employeeNameZH")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeNameZH', '<%=employeeContractSBHolder.getNextSortOrder("employeeNameZH")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
					</a>
				</th> 
				<th style="width: 8%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("employeeNameEN")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeNameEN', '<%=employeeContractSBHolder.getNextSortOrder("employeeNameEN")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
					</a>
				</th>
				<th style="width: <logic:equal name="role" value="1">14</logic:equal><logic:equal name="role" value="2">18</logic:equal>%" class="header-nosort"><bean:message bundle="sb" key="sb.solution" /></th>
				<th style="width: <logic:equal name="role" value="1">11</logic:equal><logic:equal name="role" value="2">13</logic:equal>%" class="header-nosort"><bean:message bundle="sb" key="sb.start.date" /></th>
				<th style="width: 8%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("startDate")%>">
					<a onclick="submitForm('list_form', null, null, 'startDate', '<%=employeeContractSBHolder.getNextSortOrder("startDate")%>', 'search-results');"><bean:message bundle="sb" key="sb.end.date" /></a>
				</th>
				<th style="width: 8%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("endDate")%>">
					<a onclick="submitForm('list_form', null, null, 'endDate', '<%=employeeContractSBHolder.getNextSortOrder("endDate")%>', 'search-results');"><bean:message bundle="sb" key="sb.vendor" /></a>
				</th>
				<logic:equal name="role" value="1">
					<th style="width: 6%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("corpId")%>">
						<a onclick="submitForm('list_form', null, null, 'clientId', '<%=employeeContractSBHolder.getNextSortOrder("clientId")%>', 'search-results');">客户ID</a>
					</th>
				</logic:equal>
				<th style="width: 6%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("orderId")%>">
					<a onclick="submitForm('list_form', null, null, 'orderId', '<%=employeeContractSBHolder.getNextSortOrder("orderId")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
					</a>
				</th>
				<th style="width: 6%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("contractId")%>">
					<a onclick="submitForm('list_form', null, null, 'contractId', '<%=employeeContractSBHolder.getNextSortOrder("contractId")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
					</a>
				</th>
				<th style="width: 6%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("status")%>">
					<a onclick="submitForm('list_form', null, null, 'status', '<%=employeeContractSBHolder.getNextSortOrder("status")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.status" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.status" /></logic:equal>
					</a>
				</th>
				<th style="width: 3%" class="header-nosort"><bean:message bundle="sb" key="sb.status" /></th>
				<th style="width: 3%" class="header-nosort"><bean:message bundle="public" key="public.oper" /></th>
			</tr>
		</thead>
		<logic:notEqual name="employeeContractSBHolder" property="holderSize" value="0">
			<tbody>
				<logic:iterate id="employeeContractSBVO" name="employeeContractSBHolder" property="source" indexId="number">
					<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
						<td><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="employeeContractSBVO" property="employeeSBId"/>" name="chkSelectRow[]" value="<bean:write name='employeeContractSBVO' property='contractId'/>" disabled="disabled" /></td>
						<td class="left"><a onclick="link('employeeContractSBAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSBVO" property="encodedId"/>');"><bean:write name="employeeContractSBVO" property="employeeSBId" /></a></td>								
						<td class="left"><a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSBVO" property="encodedEmployeeId"/>');"><bean:write name="employeeContractSBVO" property="employeeId" /></a></td>								
						<td class="left"><bean:write name="employeeContractSBVO" property="employeeNameZH" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="employeeNameEN" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="sbName" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="startDate" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="endDate" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="vendorName" /></td>
						<logic:equal name="role" value="1">
							<td class="left"><a title='<bean:write name="employeeContractSBVO" property="clientName" />' onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSBVO" property="encodedClientId"/>');"><bean:write name="employeeContractSBVO" property="clientId" /></a></td>
						</logic:equal>
						<td class="left"><a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSBVO" property="encodedOrderId"/>');"><bean:write name="employeeContractSBVO" property="orderId" /></a></td>								
						<td class="left"><a title='<bean:write name="employeeContractSBVO" property="contractName" />' onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSBVO" property="encodedContractId"/>');"><bean:write name="employeeContractSBVO" property="contractId" /></a></td>								
						<td class="left"><bean:write name="employeeContractSBVO" property="decodeContractStatus" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="decodeStatus" /></td>
						<td class="left">
							<kan:auth right="modify" action="HRO_SB_VENDORSETTING">
								<a onclick="modifyEmployeeContractSB('<bean:write name="employeeContractSBVO" property="employeeSBId"/>');" class="kanhandle">
									<img src="images/modify.png" title="<bean:message bundle="public" key="oper.edit" />" />
								</a>
							</kan:auth>
						</td>
					</tr>
				</logic:iterate>
			</tbody>
		</logic:notEqual>
		<logic:present name="employeeContractSBHolder">
			<tfoot>
				<tr class="total">
					<td colspan="20" class="left">
						<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="employeeContractSBHolder" property="holderSize" /> </label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="employeeContractSBHolder" property="indexStart" /> - <bean:write name="employeeContractSBHolder" property="indexEnd" /> </label> 
						<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeContractSBHolder" property="firstPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.first" /></a></label> 
						<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeContractSBHolder" property="previousPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.previous" /></a></label> 
						<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeContractSBHolder" property="nextPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.next" /></a></label> 
						<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeContractSBHolder" property="lastPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.last" /></a></label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="employeeContractSBHolder" property="realPage" />/<bean:write name="employeeContractSBHolder" property="pageCount" /> </label>&nbsp;
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="employeeContractSBHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
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
		// 检查是否通过提交后的Ajax跳转，是的话清空SelectIds
		<logic:empty name="employeeContractSBForm" property="selectedIds">
			$('.list_form input[id="selectedIds"]').val("");
		</logic:empty>
		
		kanList_init();
 		kanCheckbox_init();
	})(jQuery);	

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