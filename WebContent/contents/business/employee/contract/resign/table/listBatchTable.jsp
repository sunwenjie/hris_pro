<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder employeeContractResignHolder = (PagedListHolder) request.getAttribute("employeeContractResignHolder");
%>


<div id="messageWrapper">
	<logic:present name="MESSAGE">
		<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
			<bean:write name="MESSAGE" />
	  			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
		</div>
	</logic:present>
</div>	
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header  <%=employeeContractResignHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('list_form', null, null, 'batchId', '<%=employeeContractResignHolder.getNextSortOrder("batchId")%>', 'tableWrapper');">批次ID</a>
			</th>
			<th style="width: 30%" class="header-nosort ">
				导入EXCEL名称
			</th>
			<th style="width: 20%" class="header-nosort">
				描述
			</th>
			<th style="width: 15%" class="header-nosort">
				上传人
			</th>
			<th style="width: 15%" class="header-nosort">
				上传时间
			</th>
			<th style="width: 10%" class="header-nosort">
				状态
			</th>
		</tr>
	</thead>
	<logic:notEqual name="employeeContractResignHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="commonBatchVO" name="employeeContractResignHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="commonBatchVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="commonBatchVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="commonBatchVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left"><a href="#" onclick="link('employeeContractResignAction.do?proc=list_object&batchId=<bean:write name="commonBatchVO" property="encodedId"/>');"><bean:write name="commonBatchVO" property="batchId" /></a></td>								
					<td class="left"> <bean:write name="commonBatchVO" property="importExcelName"/></td>
					<td class="left"> <bean:write name="commonBatchVO" property="subStrDescription"/></td>
					<td class="left"> <bean:write name="commonBatchVO" property="decodeCreateBy"/></td>
					<td class="left"> <bean:write name="commonBatchVO" property="decodeCreateDate"/></td>
					<td class="left"> <bean:write name="commonBatchVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeeContractResignHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="employeeContractResignHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="employeeContractResignHolder" property="indexStart" /> - <bean:write name="employeeContractResignHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeContractResignHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeContractResignHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeContractResignHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeContractResignHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="employeeContractResignHolder" property="realPage" />/<bean:write name="employeeContractResignHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
		messageWrapperFada();
	})(jQuery);	
</script>