<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder cbBillListHolder = (PagedListHolder) request.getAttribute("cbBillListHolder");
	final boolean IN_HOUSE = request.getAttribute( "role" ).equals( "2" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<logic:equal name="role" value="1">
				<th rowspan="2" nowrap style="width: 4%" class="header <%=cbBillListHolder.getCurrentSortClass("a.clientId")%>">
					<a onclick="submitForm('searchCBBill_form', null, null, 'a.clientId', '<%=cbBillListHolder.getNextSortOrder("a.clientId")%>', 'tableWrapper');">�ͻ�ID</a>
				</th>
				<th rowspan="2" nowrap style="width: 3%" class="header <%=cbBillListHolder.getCurrentSortClass("clientNumber")%>">
					<a onclick="submitForm('searchCBBill_form', null, null, 'clientNumber', '<%=cbBillListHolder.getNextSortOrder("clientNumber")%>', 'tableWrapper');">�������</a>
				</th>
				<th rowspan="2" nowrap style="width: 5%" class="header <%=cbBillListHolder.getCurrentSortClass("clientNameZH")%>">
					<a onclick="submitForm('searchCBBill_form', null, null, 'clientNameZH', '<%=cbBillListHolder.getNextSortOrder("clientNameZH")%>', 'tableWrapper');">�ͻ�����</a>
				</th>
			</logic:equal>
			<logic:equal name="role" value="2">
				<th rowspan="2" nowrap style="width: 4%" class="header <%=cbBillListHolder.getCurrentSortClass("a.clientId")%>">
					<a onclick="submitForm('searchCBBill_form', null, null, 'a.clientId', '<%=cbBillListHolder.getNextSortOrder("a.clientId")%>', 'tableWrapper');">�ͻ�ID</a>
				</th>
				<th rowspan="2" nowrap style="width: 3%" class="header <%=cbBillListHolder.getCurrentSortClass("clientNumber")%>">
					<a onclick="submitForm('searchCBBill_form', null, null, 'clientNumber', '<%=cbBillListHolder.getNextSortOrder("clientNumber")%>', 'tableWrapper');">�������</a>
				</th>
				<th rowspan="2" nowrap style="width: 5%" class="header <%=cbBillListHolder.getCurrentSortClass("clientNameZH")%>">
					<a onclick="submitForm('searchCBBill_form', null, null, 'clientNameZH', '<%=cbBillListHolder.getNextSortOrder("clientNameZH")%>', 'tableWrapper');">�ͻ�����</a>
				</th>
			</logic:equal>
			<th rowspan="2" nowrap style="width: 3%" class="header <%=cbBillListHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchCBBill_form', null, null, 'employeeId', '<%=cbBillListHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');"><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="4">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>ID</a>
			</th>
			<th rowspan="2" nowrap style="width: 5%" class="header <%=cbBillListHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchCBBill_form', null, null, 'employeeNameZH', '<%=cbBillListHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');"><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="4">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>����</a>
			</th>
			<th rowspan="2" nowrap style="width: 4%" class="header <%=cbBillListHolder.getCurrentSortClass("certificateNumber")%>">
				<a onclick="submitForm('searchCBBill_form', null, null, 'certificateNumber', '<%=cbBillListHolder.getNextSortOrder("certificateNumber")%>', 'tableWrapper');">֤������</a>
			</th>
			<th rowspan="2" nowrap style="width: 4%" class="header <%=cbBillListHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchCBBill_form', null, null, 'contractId', '<%=cbBillListHolder.getNextSortOrder("contractId")%>', 'tableWrapper');"><logic:equal name="role" value="1">����Э��</logic:equal><logic:equal name="role" value="4">����Э��</logic:equal><logic:equal name="role" value="2">�Ͷ���ͬ</logic:equal>ID</a>
			</th>
			<th rowspan="2" nowrap style="width: 4%" class="header <%=cbBillListHolder.getCurrentSortClass("contractStatus")%>">
				<a onclick="submitForm('searchCBBill_form', null, null, 'contractStatus', '<%=cbBillListHolder.getNextSortOrder("contractStatus")%>', 'tableWrapper');"><logic:equal name="role" value="1">����</logic:equal><logic:equal name="role" value="4">����</logic:equal><logic:equal name="role" value="2">�Ͷ���ͬ</logic:equal>״̬</a>
			</th>
			<th rowspan="2" nowrap style="width: 3%" class="header <%=cbBillListHolder.getCurrentSortClass("c.monthly")%>">
				<a onclick="submitForm('searchCBBill_form', null, null, 'c.monthly', '<%=cbBillListHolder.getNextSortOrder("c.monthly")%>', 'tableWrapper');">�·�</a>
			</th>
			<th rowspan="2" nowrap style="width: 6%" class="header <%=cbBillListHolder.getCurrentSortClass("employeeCBId")%>">
				<a onclick="submitForm('searchCBBill_form', null, null, 'employeeCBId', '<%=cbBillListHolder.getNextSortOrder("employeeCBId")%>', 'tableWrapper');">�̱�����</a>
			</th>
			<th rowspan="2" nowrap style="width: 3%" class="header <%=cbBillListHolder.getCurrentSortClass("cbStatus")%>">
				<a onclick="submitForm('searchCBBill_form', null, null, 'cbStatus', '<%=cbBillListHolder.getNextSortOrder("cbStatus")%>', 'tableWrapper');">�̱�״̬</a>
			</th>
			<th rowspan="2" nowrap style="width: 3%" class="header <%=cbBillListHolder.getCurrentSortClass("cbNumber")%>">
				<a onclick="submitForm('searchCBBill_form', null, null, 'cbNumber', '<%=cbBillListHolder.getNextSortOrder("cbNumber")%>', 'tableWrapper');">������</a>
			</th>
			<th nowrap style="width: 13%" colspan="<%=request.getAttribute("itemNameMappings") == null ? 0 : ((List<Object>)request.getAttribute("itemNameMappings")).size() %>" class="header-nosort center">
				��Ŀ����
			</th>
			<logic:equal name="role" value="1">
				<th rowspan="2" nowrap style="width: 4%" class="header <%=cbBillListHolder.getCurrentSortClass("amountSalesPrice")%>">
					<a onclick="submitForm('searchCBBill_form', null, null, 'amountSalesPrice', '<%=cbBillListHolder.getNextSortOrder("amountSalesPrice")%>', 'tableWrapper');">��˾Ӫ�գ��ϼƣ�</a>
				</th>
			</logic:equal>
			<th rowspan="2" nowrap style="width: 4%" class="header <%=cbBillListHolder.getCurrentSortClass("amountSalesCost")%>">
				<a onclick="submitForm('searchCBBill_form', null, null, 'amountPurchaseCost', '<%=cbBillListHolder.getNextSortOrder("amountPurchaseCost")%>', 'tableWrapper');">���ѳɱ����ϼƣ�</a>
			</th>
			<th rowspan="2" nowrap style="width: 3%" class="header <%=cbBillListHolder.getCurrentSortClass("c.status")%>">
				<a onclick="submitForm('searchCBBill_form', null, null, 'c.status', '<%=cbBillListHolder.getNextSortOrder("c.status")%>', 'tableWrapper');">״̬</a>
			</th>
		</tr>
		<tr>
			<logic:iterate id="mappingVO" name="itemNameMappings" >
				<th width="150px;" class="header-nosort" style="text-align: center;">
					<bean:write name="mappingVO" property="mappingValue"/>
				</th>
			</logic:iterate>
		</tr>
	</thead>
	<logic:notEqual name="cbBillListHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="cbHeaderVO" name="cbBillListHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<logic:equal name="role" value="1">
						<td class="left"><span><bean:write name="cbHeaderVO" property="clientId" /></span></td>
						<td class="left"><span><bean:write name="cbHeaderVO" property="clientNumber" /></span></td>
						<td class="left"><bean:write name="cbHeaderVO" property="clientName" /></td>
					</logic:equal>
					<logic:equal name="role" value="2">
						<td class="left"><span><bean:write name="cbHeaderVO" property="clientId" /></span></td>
						<td class="left"><span><bean:write name="cbHeaderVO" property="clientNumber" /></span></td>
						<td class="left"><bean:write name="cbHeaderVO" property="clientName" /></td>
					</logic:equal>
					<td class="left"><bean:write name="cbHeaderVO" property="employeeId" /></td>
					<td class="left"><span><bean:write name="cbHeaderVO" property="employeeName" /></span></td>
					<td class="left"><bean:write name="cbHeaderVO" property="certificateNumber" /></td>
					<td class="left"><bean:write name="cbHeaderVO" property="contractId" /></td>
					<td class="left"><bean:write name="cbHeaderVO" property="decodeContractStatus" /></td>
					<td class="left"><bean:write name="cbHeaderVO" property="monthly" /></td>
					<td class="left"><bean:write name="cbHeaderVO" property="employeeCBName" /></td>
					<td class="left"><bean:write name="cbHeaderVO" property="decodeCbStatus" /></td>
					<td class="left"><bean:write name="cbHeaderVO" property="cbNumber" /></td>
					<logic:iterate id="cbBill" name="cbHeaderVO" property="detailList">
						<td class="right">
							<bean:write name="cbBill" property="decodeAmountPurchaseCost" />
						</td>
					</logic:iterate>
					
					<logic:equal name="role" value="1">
					<td class="right"><span><bean:write name="cbHeaderVO" property="decodeAmountSalesPrice" /></span></td>
					</logic:equal>
					<td class="right"><span><bean:write name="cbHeaderVO" property="decodeDetailAmountPurchaseCosts" /></span></td>
					<td class="left"><bean:write name="cbHeaderVO" property="decodeAdditionalStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="cbBillListHolder">
		<tfoot>
			<tr class="total">
				<td colspan="<%=18+(request.getAttribute("itemNameMappings") == null ? 0 : ((List<Object>)request.getAttribute("itemNameMappings")).size()) %>" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />�� <bean:write name="cbBillListHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />��<bean:write name="cbBillListHolder" property="indexStart" /> - <bean:write name="cbBillListHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchCBBill_form', null, '<bean:write name="cbBillListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchCBBill_form', null, '<bean:write name="cbBillListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchCBBill_form', null, '<bean:write name="cbBillListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchCBBill_form', null, '<bean:write name="cbBillListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />��<bean:write name="cbBillListHolder" property="realPage" />/<bean:write name="cbBillListHolder" property="pageCount" /> </label>&nbsp;
				<label>&nbsp;&nbsp;��ת����<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="cbBillListHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />ҳ</label>&nbsp;
			</tr>
		</tfoot>
	</logic:present>
</table>
<div class="bottom">
	<p></p>
</div>
