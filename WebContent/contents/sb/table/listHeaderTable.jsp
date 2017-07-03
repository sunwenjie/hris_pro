<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder sbHeaderHolder = (PagedListHolder) request.getAttribute("sbHeaderHolder");
%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>	
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 5%" class="header <%=sbHeaderHolder.getCurrentSortClass("a.headerId")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'a.headerId', '<%=sbHeaderHolder.getNextSortOrder("a.headerId")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.number.id" /></a>
			</th>
			<th style="width: 10%" class="header <%=sbHeaderHolder.getCurrentSortClass("a.flag")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'a.flag', '<%=sbHeaderHolder.getNextSortOrder("a.flag")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.payment.status" /></a>
			</th>
			<th style="width: 6%" class="header <%=sbHeaderHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'monthly', '<%=sbHeaderHolder.getNextSortOrder("monthly")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.monthly" /></a>
			</th>
			<th style="width: 6%" class="header-nosort">
				<bean:message bundle="sb" key="sb.account.monthly" />
			</th>
			<th style="width: 8%" class="header <%=sbHeaderHolder.getCurrentSortClass("employeeSBId")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'employeeSBId', '<%=sbHeaderHolder.getNextSortOrder("employeeSBId")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.solution.name" /></a>
			</th>
			<th style="width: 5%" class="header <%=sbHeaderHolder.getCurrentSortClass("vendorId")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'vendorId', '<%=sbHeaderHolder.getNextSortOrder("vendorId")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.vendor.id" /></a>
			</th>
			<th style="width: 10%" class="header <%=sbHeaderHolder.getCurrentSortClass("vendorNameZH")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'vendorNameZH', '<%=sbHeaderHolder.getNextSortOrder("vendorNameZH")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.vendor.name" /></a>
			</th>
			<th style="width: 7%" class="header <%=sbHeaderHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'contractId', '<%=sbHeaderHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>	
				</a>
			</th>
			<th style="width: 7%" class="header <%=sbHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'employeeId', '<%=sbHeaderHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>	
				</a>
			</th>
			<th style="width: 6%" class="header <%=sbHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'employeeNameZH', '<%=sbHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>	
				</a>
			</th>
			<th style="width: 8%" class="header <%=sbHeaderHolder.getCurrentSortClass("certificateNumber")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'certificateNumber', '<%=sbHeaderHolder.getNextSortOrder("certificateNumber")%>', 'tableWrapper');"><bean:message bundle="public" key="public.certificate.number" /></a>
			</th>
			<th style="width: 8%" class="header <%=sbHeaderHolder.getCurrentSortClass("residencyType")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'residencyType', '<%=sbHeaderHolder.getNextSortOrder("residencyType")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.employee.residency.type" /></a>
			</th>
			<th style="width: 6%" class="header-nosort">
				<span><bean:message bundle="sb" key="sb.employee.entity" /></span>
			</th>
			<th style="width: 6%" class="header <%=sbHeaderHolder.getCurrentSortClass("contractBranch")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'contractBranch', '<%=sbHeaderHolder.getNextSortOrder("contractBranch")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.employee.service.branch" /></a>
			</th>
			<th style="width: 6%" class="header <%=sbHeaderHolder.getCurrentSortClass("contractStartDate")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'contractStartDate', '<%=sbHeaderHolder.getNextSortOrder("contractStartDate")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="sb" key="sb.employee.contract1.start.date" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="sb" key="sb.employee.contract2.start.date" /></logic:equal>
				</a>
			</th>
			
			<th style="width: 5%" class="header <%=sbHeaderHolder.getCurrentSortClass("contractStatus")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'contractStatus', '<%=sbHeaderHolder.getNextSortOrder("contractStatus")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="sb" key="sb.employee.contract1.status" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="sb" key="sb.employee.contract2.status" /></logic:equal>
				</a>
			</th>
			<th style="width: 7%" class="header <%=sbHeaderHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'startDate', '<%=sbHeaderHolder.getNextSortOrder("startDate")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.start.date" /></a>
			</th>
			<th style="width: 7%" class="header <%=sbHeaderHolder.getCurrentSortClass("sbStatus")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'sbStatus', '<%=sbHeaderHolder.getNextSortOrder("sbStatus")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.status" /></a>
			</th>
			<th style="width: 8%" class="header <%=sbHeaderHolder.getCurrentSortClass("amountCompany")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'amountCompany', '<%=sbHeaderHolder.getNextSortOrder("amountCompany")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.company" /></a>
			</th>
			<th style="width: 8%" class="header <%=sbHeaderHolder.getCurrentSortClass("amountPersonal")%>">
				<a onclick="submitForm('searchSBHeader_form', null, null, 'amountPersonal', '<%=sbHeaderHolder.getNextSortOrder("amountPersonal")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.personal" /></a>
			</th>
			<th style="width: 8%" class="header-nosort">
				<span><bean:message bundle="public" key="public.status" /></span>
			</th>	 
		</tr>
	</thead>
	<logic:notEqual name="sbHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="sbHeaderVO" name="sbHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
					<logic:empty name="sbHeaderVO" property="workflowId" >
						<input type="checkbox" <logic:greaterEqual name="sbHeaderVO" property="additionalStatus" value="4">class="hide"</logic:greaterEqual> id="kanList_chkSelectRecord_<bean:write name="sbHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="sbHeaderVO" property="encodedId"/>" />
					</logic:empty>
					</td>
					<td class="left"><a onclick="link('sbAction.do?proc=to_sbDetail&batchId=<bean:write name="sbHeaderVO" property="encodedBatchId"/>&contractId=<bean:write name="sbHeaderVO" property="encodedContractId"/>&headerId=<bean:write name="sbHeaderVO" property="encodedId"/>&statusFlag=<bean:write name="sbBatchForm" property="statusFlag"/>');"><bean:write name="sbHeaderVO" property="headerId" /></a></td>
					<td class="left">
						<logic:equal value="preview" name="sbBatchForm" property="statusFlag">
							<bean:write name="sbHeaderVO" property="decodeFlag"/>
						</logic:equal>
						<logic:notEqual value="preview" name="sbBatchForm" property="statusFlag">
						<logic:notEmpty name="sbHeaderVO" property="workflowId" >
							<bean:write name="sbHeaderVO" property="decodeFlag"/>
						</logic:notEmpty>
						<logic:empty name="sbHeaderVO" property="workflowId" >
							<logic:equal value="1" name="sbHeaderVO" property="flag">
								<select onchange="changeSBHeaderStatus(<bean:write name='sbHeaderVO' property='headerId'/>,this.value);" disabled="disabled">
									<option value='1' selected="selected"><%=KANUtil.getMappings( request.getLocale(), "sb.status.flag" ).get( 1 ).getMappingValue() %></option>
									<option value='2' ><%=KANUtil.getMappings( request.getLocale(), "sb.status.flag" ).get( 2 ).getMappingValue() %></option>
									<bean:write name="sbBatchForm" property="flags" />
								</select>
							</logic:equal>
							<logic:notEqual value="1" name="sbHeaderVO" property="flag">
								<select onchange="changeSBHeaderStatus(<bean:write name='sbHeaderVO' property='headerId'/>,this.value);">
									<option value='1' ><%=KANUtil.getMappings( request.getLocale(), "sb.status.flag" ).get( 1 ).getMappingValue() %></option>
									<option value='2' selected="selected"><%=KANUtil.getMappings( request.getLocale(), "sb.status.flag" ).get( 2 ).getMappingValue() %></option>
								</select>
							</logic:notEqual>
						</logic:empty>
						</logic:notEqual>
					</td>
					<td class="left"><bean:write name="sbHeaderVO" property="monthly" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="accountMonthlyList" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="employeeSBName" /></td>
					<td class="left"><a onclick="link('vendorAction.do?proc=to_objectModify&id=<bean:write name="sbHeaderVO" property="encodedVendorId"/>');"><bean:write name="sbHeaderVO" property="vendorId" /></a></td>
					<td class="left"><bean:write name="sbHeaderVO" property="vendorName" /></td>
					<td class="left"><a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="sbHeaderVO" property="encodedContractId"/>');"><bean:write name="sbHeaderVO" property="contractId" /></a></td>
					<td class="left"><a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="sbHeaderVO" property="encodedEmployeeId"/>');"><bean:write name="sbHeaderVO" property="employeeId" /></a></td>
					<td class="left"><bean:write name="sbHeaderVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="certificateNumber" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="decodeResidencyType" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="orderDescription" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="decodeBranch" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="contractStartDate" /></td>
<%-- 					<td class="left"><bean:write name="sbHeaderVO" property="contractStatus" /></td> --%>
					<td class="left"><bean:write name="sbHeaderVO" property="decodeContractStatus" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="startDate" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="decodeSbStatus" /></td>
					<td class="right"><bean:write name="sbHeaderVO" property="decodeAmountCompany" /></td>
					<td class="right"><bean:write name="sbHeaderVO" property="decodeAmountPersonal" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="decodeAdditionalStatus" />
					<logic:notEmpty name="sbHeaderVO" property="workflowId" >
						待审核&nbsp;&nbsp;<img src='images/magnifer.png' onclick=popupWorkflow('<bean:write name="sbHeaderVO" property="workflowId"/>'); />
					</logic:notEmpty>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="sbHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="22" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="sbHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="sbHeaderHolder" property="indexStart" /> - <bean:write name="sbHeaderHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('searchSBHeader_form', null, '<bean:write name="sbHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchSBHeader_form', null, '<bean:write name="sbHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchSBHeader_form', null, '<bean:write name="sbHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchSBHeader_form', null, '<bean:write name="sbHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="sbHeaderHolder" property="realPage" />/<bean:write name="sbHeaderHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="sbHeaderHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
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
		submitForm('searchSBHeader_form', null, forwardPage, null, null, 'tableWrapper');
	};
	
	function changeSBHeaderStatus(headerId,flag){
		var url = "sbHeaderAction.do?proc=modify_flag_ajax&headerId="+headerId+"&flag="+flag;
		$.post(url,{},function(data){
			if(data=='success'){
				/* var html="<div class='message success fadable'>"+data;
    			html = html + "<a onclick='$('div.fadable').remove();' class='messageCloseButton'>&nbsp;</a></div>";
    			$("#messageWrapper").append(html); */
    			alert("修改成功");
    			//submitForm('searchSBHeader_form', null, <bean:write name="sbHeaderHolder" property="realPage" />, null, null, 'tableWrapper');
    			submitForm('searchSBHeader_form', 'searchObject', null, null, null, null, null, null, true);
			}
		},"text");
	};
</script>
						