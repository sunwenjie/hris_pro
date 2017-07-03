<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.SettlementAdjustmentImportBatchAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder settlementAdjustmentImportBatchHolder = ( PagedListHolder ) request.getAttribute( "settlementAdjustmentImportBatchHolder" ); 
%>
<logic:notEmpty name="MESSAGE">
<div id="_USER_DEFINE_MSG" class="message <bean:write name="MESSAGE_CLASS" /> fadable" style="display:none;"><bean:write name="MESSAGE" /></div>
</logic:notEmpty>
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=settlementAdjustmentImportBatchHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportBatch_form', null, null, 'batchId', '<%=settlementAdjustmentImportBatchHolder.getNextSortOrder("batchId")%>', 'tableWrapper');">����ID</a>
			</th>
			<th style="width: 40%" class="header-nosort">
				<span>��ע</span>
			</th>
			<th style="width: 15%" class="header  <%=settlementAdjustmentImportBatchHolder.getCurrentSortClass("importExcelName")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportBatch_form', null, null, 'importExcelName', '<%=settlementAdjustmentImportBatchHolder.getNextSortOrder("importExcelName")%>', 'tableWrapper');">�ļ�����</a>
			</th>
			<th style="width: 10%" class="header <%=settlementAdjustmentImportBatchHolder.getCurrentSortClass("createBy")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportBatch_form', null, null, 'createBy', '<%=settlementAdjustmentImportBatchHolder.getNextSortOrder("createBy")%>', 'tableWrapper');">�ϴ���</a>
			</th>
			<th style="width: 15%" class="header  <%=settlementAdjustmentImportBatchHolder.getCurrentSortClass("createDate")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportBatch_form', null, null, 'createDate', '<%=settlementAdjustmentImportBatchHolder.getNextSortOrder("createDate")%>', 'createDate');">�ϴ�ʱ��</a>
			</th>
			<th style="width: 10%" class="header  <%=settlementAdjustmentImportBatchHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportBatch_form', null, null, 'status', '<%=settlementAdjustmentImportBatchHolder.getNextSortOrder("status")%>', 'tableWrapper');">״̬</a>
			</th>	
		</tr>
	</thead>
	<logic:notEqual name="settlementAdjustmentImportBatchHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="settlementAdjustmentImportBatchVO" name="settlementAdjustmentImportBatchHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="settlementAdjustmentImportBatchVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="settlementAdjustmentImportBatchVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="settlementAdjustmentImportBatchVO" property="encodedBatchId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('settlementAdjustmentImportHeaderAction.do?proc=list_object&batchId=<bean:write name="settlementAdjustmentImportBatchVO" property="encodedBatchId" />&batchStatus=<bean:write name="settlementAdjustmentImportBatchVO" property="status" />');"><bean:write name="settlementAdjustmentImportBatchVO" property="batchId" /></a>
					</td>
					<td class="left">
						��ͬ������<bean:write name="settlementAdjustmentImportBatchVO" property="countContractId" />��
						����������<bean:write name="settlementAdjustmentImportBatchVO" property="countHeaderId" />��
						��˾Ӫ�գ�<bean:write name="settlementAdjustmentImportBatchVO" property="billAmountCompany" />��
						��˾�ɱ���<bean:write name="settlementAdjustmentImportBatchVO" property="costAmountCompany" />��
						�������룺<bean:write name="settlementAdjustmentImportBatchVO" property="billAmountPersonal" />��
						����֧����<bean:write name="settlementAdjustmentImportBatchVO" property="costAmountPersonal" />��
					</td>
					<td class="left"><bean:write name="settlementAdjustmentImportBatchVO" property="importExcelName" /></td>
						<td class="left"><bean:write name="settlementAdjustmentImportBatchVO" property="decodeCreateBy" /></td>
					<td class="left"><bean:write name="settlementAdjustmentImportBatchVO" property="decodeCreateDate" /></td>
					<td class="left">
						<logic:equal name="settlementAdjustmentImportBatchVO" property="status" value="1">
							�½�
						</logic:equal>
						<logic:equal name="settlementAdjustmentImportBatchVO" property="status" value="2">
							���ύ
						</logic:equal>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="settlementAdjustmentImportBatchHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />�� <bean:write name="settlementAdjustmentImportBatchHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />��<bean:write name="settlementAdjustmentImportBatchHolder" property="indexStart" /> - <bean:write name="settlementAdjustmentImportBatchHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchSettlementAdjustmentImportBatch_form', null, '<bean:write name="settlementAdjustmentImportBatchHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchSettlementAdjustmentImportBatch_form', null, '<bean:write name="settlementAdjustmentImportBatchHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchSettlementAdjustmentImportBatch_form', null, '<bean:write name="settlementAdjustmentImportBatchHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchSettlementAdjustmentImportBatch_form', null, '<bean:write name="settlementAdjustmentImportBatchHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />��<bean:write name="settlementAdjustmentImportBatchHolder" property="realPage" />/<bean:write name="settlementAdjustmentImportBatchHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>