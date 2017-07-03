<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.payment.PaymentAdjustmentImportBatchAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder paymentAdjustmentImportBatchHolder = ( PagedListHolder ) request.getAttribute( "paymentAdjustmentImportBatchHolder" ); 
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
			<th style="width: 10%" class="header <%=paymentAdjustmentImportBatchHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('searchPaymentAdjustmentImportBatch_form', null, null, 'batchId', '<%=paymentAdjustmentImportBatchHolder.getNextSortOrder("batchId")%>', 'tableWrapper');">批次ID</a>
			</th>
			<th style="width: 40%" class="header-nosort">
				<span>备注</span>
			</th>
			<th style="width: 15%" class="header  <%=paymentAdjustmentImportBatchHolder.getCurrentSortClass("importExcelName")%>">
				<a onclick="submitForm('searchPaymentAdjustmentImportBatch_form', null, null, 'importExcelName', '<%=paymentAdjustmentImportBatchHolder.getNextSortOrder("importExcelName")%>', 'tableWrapper');">文件名称</a>
			</th>
			<th style="width: 10%" class="header <%=paymentAdjustmentImportBatchHolder.getCurrentSortClass("createBy")%>">
				<a onclick="submitForm('searchPaymentAdjustmentImportBatch_form', null, null, 'createBy', '<%=paymentAdjustmentImportBatchHolder.getNextSortOrder("createBy")%>', 'tableWrapper');">上传人</a>
			</th>
			<th style="width: 15%" class="header  <%=paymentAdjustmentImportBatchHolder.getCurrentSortClass("createDate")%>">
				<a onclick="submitForm('searchPaymentAdjustmentImportBatch_form', null, null, 'createDate', '<%=paymentAdjustmentImportBatchHolder.getNextSortOrder("createDate")%>', 'createDate');">上传时间</a>
			</th>
			<th style="width: 10%" class="header  <%=paymentAdjustmentImportBatchHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchPaymentAdjustmentImportBatch_form', null, null, 'status', '<%=paymentAdjustmentImportBatchHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>	
		</tr>
	</thead>
	<logic:notEqual name="paymentAdjustmentImportBatchHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="paymentAdjustmentImportBatchVO" name="paymentAdjustmentImportBatchHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="paymentAdjustmentImportBatchVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="paymentAdjustmentImportBatchVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="paymentAdjustmentImportBatchVO" property="encodedBatchId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('paymentAdjustmentImportHeaderAction.do?proc=list_object&batchId=<bean:write name="paymentAdjustmentImportBatchVO" property="encodedBatchId" />&batchStatus=<bean:write name="paymentAdjustmentImportBatchVO" property="status" />');"><bean:write name="paymentAdjustmentImportBatchVO" property="batchId" /></a>
					</td>
					<td class="left">
						合同数量：<bean:write name="paymentAdjustmentImportBatchVO" property="countContractId" />；
						调整数量：<bean:write name="paymentAdjustmentImportBatchVO" property="countHeaderId" />；
						收入合计（个人）金额：<bean:write name="paymentAdjustmentImportBatchVO" property="billAmountPersonal" />；
						支出合计（个人）金额：<bean:write name="paymentAdjustmentImportBatchVO" property="costAmountPersonal" />；
					</td>
					<td class="left"><bean:write name="paymentAdjustmentImportBatchVO" property="importExcelName" /></td>
						<td class="left"><bean:write name="paymentAdjustmentImportBatchVO" property="decodeCreateBy" /></td>
					<td class="left"><bean:write name="paymentAdjustmentImportBatchVO" property="decodeCreateDate" /></td>
					<td class="left">
						<logic:equal name="paymentAdjustmentImportBatchVO" property="status" value="1">
							新建
						</logic:equal>
						<logic:equal name="paymentAdjustmentImportBatchVO" property="status" value="2">
							已提交
						</logic:equal>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="paymentAdjustmentImportBatchHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="paymentAdjustmentImportBatchHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="paymentAdjustmentImportBatchHolder" property="indexStart" /> - <bean:write name="paymentAdjustmentImportBatchHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchPaymentAdjustmentImportBatch_form', null, '<bean:write name="paymentAdjustmentImportBatchHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchPaymentAdjustmentImportBatch_form', null, '<bean:write name="paymentAdjustmentImportBatchHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchPaymentAdjustmentImportBatch_form', null, '<bean:write name="paymentAdjustmentImportBatchHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchPaymentAdjustmentImportBatch_form', null, '<bean:write name="paymentAdjustmentImportBatchHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="paymentAdjustmentImportBatchHolder" property="realPage" />/<bean:write name="paymentAdjustmentImportBatchHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>