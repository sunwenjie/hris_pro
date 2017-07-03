<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder  contractHolder = (PagedListHolder) request.getAttribute("contractHolder");
%>

<div class="top">
	<input type="button" id="btn_select" class="btn" value="<bean:message bundle="public" key="button.selected" />" onclick="selectContract()"/>
	<input type="button" id="btn_cancel" name="btn_cancel" class="btn reset" onclick="$('#contractModalId').addClass('hide');$('#shield').hide();" value="<bean:message bundle="public" key="button.exit" />" />
</div>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" disabled="disabled" style="border-color:#CCCCCC" />
			</th>
			<th style="width: 10%" class="header <%=contractHolder.getCurrentSortClass("contractId")%>">
				<a onclick="if( checkSearchContract() ) submitForm('listContract_form', null, null, 'contractId', '<%=contractHolder.getNextSortOrder("contractId")%>', 'contractTableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: 15%" class="header <%=contractHolder.getCurrentSortClass("number")%>">
				<a onclick="if( checkSearchContract() ) submitForm('listContract_form', null, null, 'number', '<%=contractHolder.getNextSortOrder("number")%>', 'contractTableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.no" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.no" /></logic:equal>
				</a>
			</th>
			<th style="width: 40%" class="header <%=contractHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="if( checkSearchContract() ) submitForm('listContract_form', null, null, 'nameZH', '<%=contractHolder.getNextSortOrder("nameZH")%>', 'contractTableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.name.cn" /></logic:equal>
				</a>
			</th>
			<logic:equal name="role" value="2">
				<th style="width: 35%" class="header <%=contractHolder.getCurrentSortClass("nameEN")%>">
					<a onclick="if( checkSearchContract() ) submitForm('listContract_form', null, null, 'nameEN', '<%=contractHolder.getNextSortOrder("nameEN")%>', 'contractTableWrapper');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.name.en" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.name.en" /></logic:equal>
					</a>
				</th>
			</logic:equal>
			<logic:equal name="role" value="1">
				<th style="width: 35%" class="header <%=contractHolder.getCurrentSortClass("clientNameZH")%>">
					<a onclick="if( checkSearchContract() ) submitForm('listContract_form', null, null, 'clientNameZH', '<%=contractHolder.getNextSortOrder("clientNameZH")%>', 'contractTableWrapper');">客户名称</a>
				</th>
			</logic:equal>
		</tr>
	</thead>
	<logic:notEqual name="contractHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="contractVO" name="contractHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="contractVO" property="clientId"/>" name="chkSelectRow[]" value="<bean:write name="contractVO" property="contractId"/>" />
					</td>
					<td class="left"><bean:write name="contractVO" property="contractId" /></td>
					<td class="left"><bean:write name="contractVO" property="contractNo" /></td>
					<td class="left"><bean:write name="contractVO" property="nameZH" /></td>
					<logic:equal name="role" value="2">
						<td class="left"><bean:write name="contractVO" property="nameEN" /></td>
					</logic:equal>
					<logic:equal name="role" value="1">
						<td class="left"><bean:write name="contractVO" property="clientNameZH" /></td>
					</logic:equal>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="contractHolder">
		<tfoot>
			<tr class="total">
				<td colspan="5" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="contractHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="contractHolder" property="indexStart" /> - <bean:write name="contractHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="if( checkSearchContract() ) submitForm('listContract_form', null, '<bean:write name="contractHolder" property="firstPage" />', null, null, 'contractTableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchContract() ) submitForm('listContract_form', null, '<bean:write name="contractHolder" property="previousPage" />', null, null, 'contractTableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchContract() ) submitForm('listContract_form', null, '<bean:write name="contractHolder" property="nextPage" />', null, null, 'contractTableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchContract() ) submitForm('listContract_form', null, '<bean:write name="contractHolder" property="lastPage" />', null, null, 'contractTableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="contractHolder" property="realPage" />/<bean:write name="contractHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>

<script type="text/javascript">
	(function($) {
		// checkbox 选择事件
		$('#contractTableWrapper input[id^="kanList_chkSelectRecord_"]').click(function(){
			if($(this).attr("checked")=="checked"){
				$('#contractTableWrapper input[id^="kanList_chkSelectRecord_"]:checked').not($(this)).attr('checked', false);
			}
		}
	);
		
		$("tbody tr").dblclick(function(){ 
			kanlist_dbclickDailogBox($(this),"listContract_form");
		}); 
		
		 
		$(function(){
			var selectValue= $('.listContract_form input[id="selectedIds"]').val();
			if(selectValue!=null||selectValue!=""){
				$("tbody tr").children("td").children("input[value="+selectValue+"]").attr("checked",'true');
			}
		});
	})(jQuery);
</script>