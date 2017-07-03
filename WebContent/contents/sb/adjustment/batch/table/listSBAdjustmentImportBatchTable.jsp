<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.sb.SBAdjustmentImportBatchAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder sbAdjustmentImportBatchHolder = ( PagedListHolder ) request.getAttribute( "sbAdjustmentImportBatchHolder" ); 
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
			<th style="width: 10%" class="header <%=sbAdjustmentImportBatchHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('searchSBAdjustmentImportBatch_form', null, null, 'batchId', '<%=sbAdjustmentImportBatchHolder.getNextSortOrder("batchId")%>', 'tableWrapper');">批次ID</a>
			</th>
			<th style="width: 40%" class="header-nosort">
				<span>备注</span>
			</th>
			<th style="width: 15%" class="header  <%=sbAdjustmentImportBatchHolder.getCurrentSortClass("importExcelName")%>">
				<a onclick="submitForm('searchSBAdjustmentImportBatch_form', null, null, 'importExcelName', '<%=sbAdjustmentImportBatchHolder.getNextSortOrder("importExcelName")%>', 'tableWrapper');">文件名称</a>
			</th>
			<th style="width: 10%" class="header <%=sbAdjustmentImportBatchHolder.getCurrentSortClass("createBy")%>">
				<a onclick="submitForm('searchSBAdjustmentImportBatch_form', null, null, 'createBy', '<%=sbAdjustmentImportBatchHolder.getNextSortOrder("createBy")%>', 'tableWrapper');">上传人</a>
			</th>
			<th style="width: 15%" class="header  <%=sbAdjustmentImportBatchHolder.getCurrentSortClass("createDate")%>">
				<a onclick="submitForm('searchSBAdjustmentImportBatch_form', null, null, 'createDate', '<%=sbAdjustmentImportBatchHolder.getNextSortOrder("createDate")%>', 'createDate');">上传时间</a>
			</th>
			<th style="width: 10%" class="header  <%=sbAdjustmentImportBatchHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchSBAdjustmentImportBatch_form', null, null, 'status', '<%=sbAdjustmentImportBatchHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>	
		</tr>
	</thead>
	<logic:notEqual name="sbAdjustmentImportBatchHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="sbAdjustmentImportBatchVO" name="sbAdjustmentImportBatchHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="sbAdjustmentImportBatchVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="sbAdjustmentImportBatchVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="sbAdjustmentImportBatchVO" property="encodedBatchId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('sbAdjustmentImportHeaderAction.do?proc=list_object&batchId=<bean:write name="sbAdjustmentImportBatchVO" property="encodedBatchId" />&batchStatus=<bean:write name="sbAdjustmentImportBatchVO" property="status" />');"><bean:write name="sbAdjustmentImportBatchVO" property="batchId" /></a>
					</td>
					<td class="left">
						合同数量：<bean:write name="sbAdjustmentImportBatchVO" property="countContractId" />；
						社保公积金调整数量：<bean:write name="sbAdjustmentImportBatchVO" property="countHeaderId" />；
						调整金额（公司）：<bean:write name="sbAdjustmentImportBatchVO" property="amountCompany" />；
						调整金额（个人）：<bean:write name="sbAdjustmentImportBatchVO" property="amountPersonal" />；
					</td>
					<td class="left"><bean:write name="sbAdjustmentImportBatchVO" property="importExcelName" /></td>
						<td class="left"><bean:write name="sbAdjustmentImportBatchVO" property="decodeCreateBy" /></td>
					<td class="left"><bean:write name="sbAdjustmentImportBatchVO" property="decodeCreateDate" /></td>
					<td class="left">
						<logic:equal name="sbAdjustmentImportBatchVO" property="status" value="1">
							新建
						</logic:equal>
						<logic:equal name="sbAdjustmentImportBatchVO" property="status" value="2">
							已提交
						</logic:equal>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="sbAdjustmentImportBatchHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="sbAdjustmentImportBatchHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="sbAdjustmentImportBatchHolder" property="indexStart" /> - <bean:write name="sbAdjustmentImportBatchHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchSBAdjustmentImportBatch_form', null, '<bean:write name="sbAdjustmentImportBatchHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchSBAdjustmentImportBatch_form', null, '<bean:write name="sbAdjustmentImportBatchHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchSBAdjustmentImportBatch_form', null, '<bean:write name="sbAdjustmentImportBatchHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchSBAdjustmentImportBatch_form', null, '<bean:write name="sbAdjustmentImportBatchHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="sbAdjustmentImportBatchHolder" property="realPage" />/<bean:write name="sbAdjustmentImportBatchHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>