<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.sb.SBAdjustmentHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder sbAdjustmentImportHeaderHolder = ( PagedListHolder ) request.getAttribute( "sbAdjustmentImportHeaderHolder" ); 
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 3%" class="header <%=sbAdjustmentImportHeaderHolder.getCurrentSortClass("adjustmentHeaderId")%>">
				<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, null, 'adjustmentHeaderId', '<%=sbAdjustmentImportHeaderHolder.getNextSortOrder("adjustmentHeaderId")%>', 'tableWrapper');">ID</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "12" : "14" %>%" class="header <%=sbAdjustmentImportHeaderHolder.getCurrentSortClass("sbSolutionId")%>">
				<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, null, 'sbSolutionId', '<%=sbAdjustmentImportHeaderHolder.getNextSortOrder("sbSolutionId")%>', 'tableWrapper');">�籣�����𷽰�</a>
			</th>
			<th style="width: 6%" class="header <%=sbAdjustmentImportHeaderHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, null, 'monthly', '<%=sbAdjustmentImportHeaderHolder.getNextSortOrder("monthly")%>', 'tableWrapper');">�����·�</a>
			</th>
			<th style="width: 7%" class="header <%=sbAdjustmentImportHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, null, 'employeeId', '<%=sbAdjustmentImportHeaderHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');"><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>ID</a>
			</th>
			<th style="width: 8%" class="header <%=sbAdjustmentImportHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, null, 'employeeNameZH', '<%=sbAdjustmentImportHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');"><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>���������ģ�</a>
			</th>
			<th style="width: 8%" class="header <%=sbAdjustmentImportHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, null, 'employeeNameEN', '<%=sbAdjustmentImportHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');"><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>������Ӣ�ģ�</a>
			</th>
			<logic:equal name="role" value="1">
				<th style="width: 7%" class="header <%=sbAdjustmentImportHeaderHolder.getCurrentSortClass("clientId")%>">
					<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, null, 'clientId', '<%=sbAdjustmentImportHeaderHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">�ͻ�ID</a>
				</th>
				<th style="width: 10%" class="header-nosort">
					�ͻ�����
				</th>
				<th style="width: 7%" class="header <%=sbAdjustmentImportHeaderHolder.getCurrentSortClass("orderId")%>">
					<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, null, 'orderId', '<%=sbAdjustmentImportHeaderHolder.getNextSortOrder("orderId")%>', 'tableWrapper');">����ID</a>
				</th>
			</logic:equal>
			<logic:equal name="role" value="2">
				<th style="width: 16%" class="header <%=sbAdjustmentImportHeaderHolder.getCurrentSortClass("orderDescription")%>">
					<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, null, 'orderDescription', '<%=sbAdjustmentImportHeaderHolder.getNextSortOrder("orderDescription")%>', 'tableWrapper');">�����������</a>
				</th>
			</logic:equal>
			<th style="width: 7%" class="header <%=sbAdjustmentImportHeaderHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, null, 'contractId', '<%=sbAdjustmentImportHeaderHolder.getNextSortOrder("contractId")%>', 'tableWrapper');"><logic:equal name="role" value="1">����Э��</logic:equal><logic:equal name="role" value="2">�Ͷ���ͬ</logic:equal>ID</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "10" : "16" %>%" class="header-nosort">
				<logic:equal name="role" value="1">����Э������</logic:equal><logic:equal name="role" value="2">�Ͷ���ͬ����</logic:equal>
			</th>
			<th style="width: 6%" class="header <%=sbAdjustmentImportHeaderHolder.getCurrentSortClass("amountCompany")%>">
				<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, null, 'amountCompany', '<%=sbAdjustmentImportHeaderHolder.getNextSortOrder("amountCompany")%>', 'tableWrapper');">��������</a>
			</th>
			<th style="width: 6%" class="header <%=sbAdjustmentImportHeaderHolder.getCurrentSortClass("amountPersonal")%>">
				<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, null, 'amountPersonal', '<%=sbAdjustmentImportHeaderHolder.getNextSortOrder("amountPersonal")%>', 'tableWrapper');">����������</a>
			</th>
			<th style="width: 6%" class="header <%=sbAdjustmentImportHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, null, 'status', '<%=sbAdjustmentImportHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');">״̬</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="sbAdjustmentImportHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="sbAdjustmentHeaderVO" name="sbAdjustmentImportHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="sbAdjustmentHeaderVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="sbAdjustmentHeaderVO" property="adjustmentHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="sbAdjustmentHeaderVO" property="adjustmentHeaderId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('sbAdjustmentImportDetailAction.do?proc=list_object&headerId=<bean:write name="sbAdjustmentHeaderVO" property="encodedId" />');"><bean:write name="sbAdjustmentHeaderVO" property="adjustmentHeaderId" /></a>
					</td>
					<td class="left">
						<bean:write name="sbAdjustmentHeaderVO" property="decodeSbSolutionId" />
					</td>
					<td class="left">
						<bean:write name="sbAdjustmentHeaderVO" property="monthly" />
					</td>
					<td class="left">
						<a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="sbAdjustmentHeaderVO" property="encodedEmployeeId"/>');">
							<bean:write name="sbAdjustmentHeaderVO" property="employeeId" />
						</a>
					</td>
					<td class="left"><bean:write name="sbAdjustmentHeaderVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="sbAdjustmentHeaderVO" property="employeeNameEN" /></td>
					<logic:equal name="role" value="1">
						<td class="left">
							<a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="sbAdjustmentHeaderVO" property="encodedClientId"/>');">
								<bean:write name="sbAdjustmentHeaderVO" property="clientId" />
							</a>
						</td>
						<td class="left"><bean:write name="sbAdjustmentHeaderVO" property="clientName" /></td>
						<td class="left">
							<a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="sbAdjustmentHeaderVO" property="encodedOrderId"/>');">
								<bean:write name="sbAdjustmentHeaderVO" property="orderId" />
							</a>
						</td>
					</logic:equal>
					<logic:equal name="role" value="2">
						<td class="left"><bean:write name="sbAdjustmentHeaderVO" property="orderDescription" /></td>
					</logic:equal>
					<td class="left">
						<a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="sbAdjustmentHeaderVO" property="encodedContractId"/>');">
							<bean:write name="sbAdjustmentHeaderVO" property="contractId" />
						</a>
					</td>
					<td class="left"><bean:write name="sbAdjustmentHeaderVO" property="contractName" /></td>
					<td class="right"><bean:write name="sbAdjustmentHeaderVO" property="amountCompany" /></td>
					<td class="right"><bean:write name="sbAdjustmentHeaderVO" property="amountPersonal" /></td>
					<td class="left">
						<bean:write name="sbAdjustmentHeaderVO" property="decodeStatus" />
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="sbAdjustmentImportHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="16" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />�� <bean:write name="sbAdjustmentImportHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />��<bean:write name="sbAdjustmentImportHeaderHolder" property="indexStart" /> - <bean:write name="sbAdjustmentImportHeaderHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, '<bean:write name="sbAdjustmentImportHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, '<bean:write name="sbAdjustmentImportHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, '<bean:write name="sbAdjustmentImportHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchSBAdjustmentImportHeader_form', null, '<bean:write name="sbAdjustmentImportHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />��<bean:write name="sbAdjustmentImportHeaderHolder" property="realPage" />/<bean:write name="sbAdjustmentImportHeaderHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;��ת����<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="sbAdjustmentImportHeaderHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />ҳ</label>&nbsp;
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
		// ���ҳ����Ч�Զ���ת����һҳ
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage = Number($('.forwardPage').val()) - 1;
		submitForm('searchSBAdjustmentImportHeader_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>