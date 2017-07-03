<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.payment.PaymentAdjustmentHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder paymentAdjustmentImportHeaderHolder = ( PagedListHolder ) request.getAttribute( "paymentAdjustmentImportHeaderHolder" ); 
%>

<table class="table hover" id="resultTable">
	<thead>
			<tr>
				<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
				</th>
				<th style="width: 6%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("adjustmentHeaderId")%>">
					<a onclick="submitForm('list_form', null, null, 'adjustmentHeaderId', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("adjustmentHeaderId")%>', 'search-results');">����ID</a>
				</th>
				<th style="width: 6%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("monthly")%>">
					<a onclick="submitForm('list_form', null, null, 'monthly', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("monthly")%>', 'search-results');">�����·�</a>
				</th> 
				<logic:equal name="role" value="1">
					<th style="width: 7%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("clientId")%>">
						<a onclick="submitForm('list_form', null, null, 'clientId', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("clientId")%>', 'search-results');">�ͻ�ID</a>
					</th>
				</logic:equal>
				<th style="width: 7%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("orderId")%>">
					<a onclick="submitForm('list_form', null, null, 'orderId', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("orderId")%>', 'search-results');"><logic:equal name="role" value="1">����ID</logic:equal><logic:equal name="role" value="2">�������ID</logic:equal></a>
				</th>	 
				<th style="width: 7%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("employeeId")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeId', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("employeeId")%>', 'search-results');"><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>ID</a>
				</th>	 
				<th style="width: <logic:equal name="role" value="1">5</logic:equal><logic:equal name="role" value="2">8</logic:equal>%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeNameZH', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'search-results');"><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>���������ģ�</a>
				</th>	 
				<th style="width: <logic:equal name="role" value="1">5</logic:equal><logic:equal name="role" value="2">8</logic:equal>%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeNameEN', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'search-results');"><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>������Ӣ�ģ�</a>
				</th>	 
				<th style="width: <logic:equal name="role" value="1">7</logic:equal><logic:equal name="role" value="2">8</logic:equal>%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("contractId")%>">
					<a onclick="submitForm('list_form', null, null, 'contractId', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("contractId")%>', 'search-results');"><logic:equal name="role" value="1">������Ϣ</logic:equal><logic:equal name="role" value="2">�Ͷ���ͬ</logic:equal>ID</a>
				</th>
				<th style="width: 6%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("billAmountPersonal")%>">
					<a onclick="submitForm('list_form', null, null, 'billAmountPersonal', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("billAmountPersonal")%>', 'search-results');">��������</a>
				</th>
				<th style="width: 6%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("costAmountPersonal")%>">
					<a onclick="submitForm('list_form', null, null, 'costAmountPersonal', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("costAmountPersonal")%>', 'search-results');">����֧��</a>
				</th>	 
				<th style="width: 6%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("taxAmountPersonal")%>">
					<a onclick="submitForm('list_form', null, null, 'taxAmountPersonal', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("taxAmountPersonal")%>', 'search-results');">��˰</a>
				</th>	 
				<th style="width: 6%" class="header-nosort">
					<span>Ӧ������</span>
				</th>
				<th style="width: 8%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("modifyBy")%>">
					<a onclick="submitForm('list_form', null, null, 'modifyBy', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("modifyBy")%>', 'search-results');">�޸���</a>
				</th>
				<th style="width: 11%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("modifyDate")%>">
					<a onclick="submitForm('list_form', null, null, 'modifyDate', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("modifyDate")%>', 'search-results');">�޸�ʱ��</a>
				</th>
				<th style="width: 7%" class="header  <%=paymentAdjustmentImportHeaderHolder.getCurrentSortClass("status")%>">
					<a onclick="submitForm('list_form', null, null, 'status', '<%=paymentAdjustmentImportHeaderHolder.getNextSortOrder("status")%>', 'search-results');">״̬</a>
				</th>
			</tr>
		</thead>
		<logic:notEqual name="paymentAdjustmentImportHeaderHolder" property="holderSize" value="0">
			<tbody>
				<logic:iterate id="paymentAdjustmentHeaderVO" name="paymentAdjustmentImportHeaderHolder" property="source" indexId="number">
					<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
						<td>
						<logic:equal name="paymentAdjustmentHeaderVO" property="status" value="1" >
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="paymentAdjustmentHeaderVO" property="adjustmentHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="paymentAdjustmentHeaderVO" property="adjustmentHeaderId"/>" />
						</logic:equal> 
						</td>
						<td class="left">
						<kan:auth right="modify" action="<%=PaymentAdjustmentHeaderAction.accessAction%>">
							<a href="#" onclick="link('paymentAdjustmentImportDetailAction.do?proc=list_object&adjustmentHeaderId=<bean:write name="paymentAdjustmentHeaderVO" property="encodedId"/>');">
						</kan:auth>
							<bean:write name="paymentAdjustmentHeaderVO" property="adjustmentHeaderId" />
						<kan:auth right="modify" action="<%=PaymentAdjustmentHeaderAction.accessAction%>">
							</a>
						</kan:auth>
						</td>								
						<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="monthly" /></td>
						<logic:equal name="role" value="1">
							<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="clientId" /></td>
						</logic:equal>								
						<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="orderId" /></td>
						<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="employeeId" /></td>
						<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="employeeNameZH" /></td>
						<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="employeeNameEN" /></td>
						<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="contractId" /></td>
						<td class="right"><bean:write name="paymentAdjustmentHeaderVO" property="billAmountPersonal" /></td>
						<td class="right"><bean:write name="paymentAdjustmentHeaderVO" property="costAmountPersonal" /></td>
						<td class="right"><bean:write name="paymentAdjustmentHeaderVO" property="taxAmountPersonal" /></td>
						<td class="right"><bean:write name="paymentAdjustmentHeaderVO" property="amountAdjustment" /></td>
						<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="decodeModifyBy" /></td>	
						<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="decodeModifyDate" /></td>	
						<td class="left">
							<bean:write name="paymentAdjustmentHeaderVO" property="decodeStatus" />
						</td>
					</tr>
				</logic:iterate>
			</tbody>
		</logic:notEqual>
	<logic:present name="paymentAdjustmentImportHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="16" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />�� <bean:write name="paymentAdjustmentImportHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />��<bean:write name="paymentAdjustmentImportHeaderHolder" property="indexStart" /> - <bean:write name="paymentAdjustmentImportHeaderHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('searchPaymentAdjustmentImportHeader_form', null, '<bean:write name="paymentAdjustmentImportHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchPaymentAdjustmentImportHeader_form', null, '<bean:write name="paymentAdjustmentImportHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchPaymentAdjustmentImportHeader_form', null, '<bean:write name="paymentAdjustmentImportHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchPaymentAdjustmentImportHeader_form', null, '<bean:write name="paymentAdjustmentImportHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />��<bean:write name="paymentAdjustmentImportHeaderHolder" property="realPage" />/<bean:write name="paymentAdjustmentImportHeaderHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;��ת����<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="paymentAdjustmentImportHeaderHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />ҳ</label>&nbsp;
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
		submitForm('searchPaymentAdjustmentImportHeader_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>