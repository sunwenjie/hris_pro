<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder  clientHolder = (PagedListHolder) request.getAttribute("clientHolder");
%>

<div class="top">
	<input type="button" id="btn_select" class="btn" value="选定 " onclick="selectClient()"/>
	<input type="button" id="btn_cancel" name="btn_cancel" class="btn reset" onclick="$('#clientModalId').addClass('hide');$('#shield').hide();" value="退出" />
</div>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" disabled="disabled" style="border-color:#CCCCCC" />
			</th>
			<th style="width: 10%" class="header <%=clientHolder.getCurrentSortClass("clientId")%>">
				<a onclick="if( checkSearchClient() ) submitForm('listClient_form', null, null, 'clientId', '<%=clientHolder.getNextSortOrder("clientId")%>', 'clientTableWrapper');">客户ID</a>
			</th>
			<th style="width: 15%" class="header <%=clientHolder.getCurrentSortClass("number")%>">
				<a onclick="if( checkSearchClient() ) submitForm('listClient_form', null, null, 'number', '<%=clientHolder.getNextSortOrder("number")%>', 'clientTableWrapper');">客户编号</a>
			</th>
			<th style="width: 40%" class="header <%=clientHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="if( checkSearchClient() ) submitForm('listClient_form', null, null, 'nameZH', '<%=clientHolder.getNextSortOrder("nameZH")%>', 'clientTableWrapper');">客户名称（中文）</a>
			</th>
			<th style="width: 35%" class="header <%=clientHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="if( checkSearchClient() ) submitForm('listClient_form', null, null, 'nameEN', '<%=clientHolder.getNextSortOrder("nameEN")%>', 'clientTableWrapper');">客户名称（英文）</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="clientHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="clientVO" name="clientHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="clientVO" property="clientId"/>" name="chkSelectRow[]" value="<bean:write name="clientVO" property="clientId"/>" />
					</td>
					<td class="left"><bean:write name="clientVO" property="clientId" /></td>
					<td class="left"><bean:write name="clientVO" property="number" /></td>
					<td class="left"><bean:write name="clientVO" property="nameZH" /></td>
					<td class="left"><bean:write name="clientVO" property="nameEN" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="clientHolder">
		<tfoot>
			<tr class="total">
				<td colspan="5" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="clientHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="clientHolder" property="indexStart" /> - <bean:write name="clientHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="if( checkSearchClient() ) submitForm('listClient_form', null, '<bean:write name="clientHolder" property="firstPage" />', null, null, 'clientTableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchClient() ) submitForm('listClient_form', null, '<bean:write name="clientHolder" property="previousPage" />', null, null, 'clientTableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchClient() ) submitForm('listClient_form', null, '<bean:write name="clientHolder" property="nextPage" />', null, null, 'clientTableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchClient() ) submitForm('listClient_form', null, '<bean:write name="clientHolder" property="lastPage" />', null, null, 'clientTableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="clientHolder" property="realPage" />/<bean:write name="clientHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		// Checkbox 选择事件
		$('#clientTableWrapper input[id^="kanList_chkSelectRecord_"]').click(function(){
			if($(this).attr("checked")=="checked"){
				$('#clientTableWrapper input[id^="kanList_chkSelectRecord_"]').not($(this)).attr('checked', false);
			}
		});
		
		//列表双击
		$("tbody tr").dblclick(function(){ 
			kanlist_dbclickDailogBox($(this),"listClient_form");
		}); 
		
		$(function(){
			var selectValue= $('.listClient_form input[id="selectedIds"]').val();
			if(selectValue!=null||selectValue!=""){
				$("tbody tr").children("td").children("input[value="+selectValue+"]").attr("checked",'true');
			}
		});
	})(jQuery);
</script>