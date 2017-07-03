<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder batchTempHolder = (PagedListHolder) request.getAttribute("batchTempHolder");
%>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 7%" class="header  <%=batchTempHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('list_form', null, null, 'batchId', '<%=batchTempHolder.getNextSortOrder("batchId")%>', 'tableWrapper');">����ID</a>
			</th>
			<th style="width: 7%" class="header  <%=batchTempHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('list_form', null, null, 'monthly', '<%=batchTempHolder.getNextSortOrder("monthly")%>', 'tableWrapper');">�����·�</a>
			</th> 
			<th style="width: 8%" class="header  <%=batchTempHolder.getCurrentSortClass("billAmountCompany")%>">
				<a onclick="submitForm('list_form', null, null, 'billAmountCompany', '<%=batchTempHolder.getNextSortOrder("billAmountCompany")%>', 'tableWrapper');">��˾Ӫ��</a>
			</th>
			<th style="width: 8%" class="header  <%=batchTempHolder.getCurrentSortClass("costAmountCompany")%>">
				<a onclick="submitForm('list_form', null, null, 'costAmountCompany', '<%=batchTempHolder.getNextSortOrder("costAmountCompany")%>', 'tableWrapper');">��˾�˹��ɱ�</a>
			</th>	 	 
			<th style="width: 8%" class="header  <%=batchTempHolder.getCurrentSortClass("billAmountPersonal")%>">
				<a onclick="submitForm('list_form', null, null, 'billAmountPersonal', '<%=batchTempHolder.getNextSortOrder("billAmountPersonal")%>', 'tableWrapper');">Ӧ���ϼ�</a>
			</th>	 
			<th style="width: 8%" class="header  <%=batchTempHolder.getCurrentSortClass("costAmountPersonal")%>">
				<a onclick="submitForm('list_form', null, null, 'costAmountPersonal', '<%=batchTempHolder.getNextSortOrder("costAmountPersonal")%>', 'tableWrapper');">���˿ۿ� </a>
			</th>	 
			<th style="width: 30%" class="header-nosort">
				<span>��ע</span>
			</th>
			<th style="width: 12%" class="header  <%=batchTempHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('list_form', null, null, 'startDate', '<%=batchTempHolder.getNextSortOrder("startDate")%>', 'tableWrapper');">���㿪ʼʱ��</a>
			</th>
			<th style="width: 12%" class="header  <%=batchTempHolder.getCurrentSortClass("endDate")%>">
				<a onclick="submitForm('list_form', null, null, 'endDate', '<%=batchTempHolder.getNextSortOrder("endDate")%>', 'tableWrapper');">�������ʱ��</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="batchTempHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="batchTempVO" name="batchTempHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="batchTempVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="batchTempVO" property="encodedId"/>" /></td>
					<td class="left"><a href="#" onclick="link('settlementHeaderTempAction.do?proc=list_object&batchId=<bean:write name="batchTempVO" property="encodedId"/>');"><bean:write name="batchTempVO" property="batchId" /></a></td>								
					<td class="left"><bean:write name="batchTempVO" property="monthly" /></td>
					<td class="right"><bean:write name="batchTempVO" property="billAmountCompany" /></td>		
					<td class="right"><bean:write name="batchTempVO" property="costAmountCompany" /></td>							
					<td class="right"><bean:write name="batchTempVO" property="billAmountPersonal" /></td>								
					<td class="right"><bean:write name="batchTempVO" property="costAmountPersonal" /></td>								
					<td class="left">
						�ͻ���<bean:write name="batchTempVO" property="countClientId" />��������<bean:write name="batchTempVO" property="countOrderId" />����Ա�˴Σ�<bean:write name="batchTempVO" property="countContractId" />��
						<logic:greaterThan name="batchTempVO" property="employeeListSize" value="5">
							<logic:equal name="role" value="2">Ա��</logic:equal><logic:equal name="role" value="1">��Ա</logic:equal>
							��<bean:write name="batchTempVO" property="countEmployeeId" /> �ˣ���<bean:write name="batchTempVO" property="employeeNameTop5List" />
							<img src="images/tips.png" title="<bean:write name="batchTempVO" property="employeeNameList" />" />
						</logic:greaterThan>
						<logic:lessThan name="batchTempVO" property="employeeListSize" value="6">
							<logic:equal name="role" value="2">Ա��</logic:equal><logic:equal name="role" value="1">��Ա</logic:equal>
							��<bean:write name="batchTempVO" property="countEmployeeId" /> �ˣ���<bean:write name="batchTempVO" property="employeeNameList" />
						</logic:lessThan>
					</td>							
					<td class="left"><bean:write name="batchTempVO" property="startDate" /></td>	
					<td class="left"><bean:write name="batchTempVO" property="endDate" /></td>	
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="batchTempHolder">
		<tfoot>
			<tr class="total">
				<td colspan="10" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />�� <bean:write name="batchTempHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />��<bean:write name="batchTempHolder" property="indexStart" /> - <bean:write name="batchTempHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="batchTempHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="batchTempHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="batchTempHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="batchTempHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />��<bean:write name="batchTempHolder" property="realPage" />/<bean:write name="batchTempHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;��ת����<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="batchTempHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />ҳ</label>&nbsp;
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
		submitForm('list_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>