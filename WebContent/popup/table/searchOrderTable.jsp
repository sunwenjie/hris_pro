<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder orderHolder = (PagedListHolder) request.getAttribute("orderHolder");
%>

<div class="top">
	<input type="button" name="btn_select" id="btn_select" class="" value="选定 " onclick="selectOrder()"/>
	<input type="button" name="btn_cancel" id="btn_cancel" class="reset" value="退出" onclick="$('#orderModalId').addClass('hide');$('#shield').hide();" />
</div>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" disabled="disabled" style="border-color:#CCCCCC" />
			</th>
			<th style="width: 10%" class="header <%=orderHolder.getCurrentSortClass("orderHeaderId")%>">
				<a onclick="if( checkSearchOrder() ) submitForm('listOrder_form', null, null, 'orderHeaderId', '<%=orderHolder.getNextSortOrder("orderHeaderId")%>', 'orderTableWrapper');">订单ID</a>
			</th>
			<th style="width: 10%" class="header <%=orderHolder.getCurrentSortClass("clientId")%>">
				<a onclick="if( checkSearchOrder() ) submitForm('listOrder_form', null, null, 'clientId', '<%=orderHolder.getNextSortOrder("clientId")%>', 'orderTableWrapper');">客户ID</a>
			</th>
			<th style="width: 40%" class="header <%=orderHolder.getCurrentSortClass("clientNameZH")%>">
				<a onclick="if( checkSearchOrder() ) submitForm('listOrder_form', null, null, 'clientNameZH', '<%=orderHolder.getNextSortOrder("clientNameZH")%>', 'orderTableWrapper');">客户名称（中文）</a>
			</th>
			<th style="width: 40%" class="header <%=orderHolder.getCurrentSortClass("clientNameEN")%>">
				<a onclick="if( checkSearchOrder() ) submitForm('listOrder_form', null, null, 'clientNameEN', '<%=orderHolder.getNextSortOrder("clientNameEN")%>', 'orderTableWrapper');">客户名称（英文）</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="orderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="clientOrderHeaderVO" name="orderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="clientOrderHeaderVO" property="orderHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="clientOrderHeaderVO" property="orderHeaderId"/>" />
					</td>
					<td class="left"><bean:write name="clientOrderHeaderVO" property="orderHeaderId" /></td>
					<td class="left"><bean:write name="clientOrderHeaderVO" property="clientId" /></td>
					<td class="left"><bean:write name="clientOrderHeaderVO" property="clientNameZH" /></td>
					<td class="left"><bean:write name="clientOrderHeaderVO" property="clientNameEN" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="orderHolder">
		<tfoot>
			<tr class="total">
				<td></td>
				<td colspan="5" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="orderHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="orderHolder" property="indexStart" /> - <bean:write name="orderHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="if( checkSearchOrder() ) submitForm('listOrder_form', null, '<bean:write name="orderHolder" property="firstPage" />', null, null, 'orderTableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchOrder() ) submitForm('listOrder_form', null, '<bean:write name="orderHolder" property="previousPage" />', null, null, 'orderTableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchOrder() ) submitForm('listOrder_form', null, '<bean:write name="orderHolder" property="nextPage" />', null, null, 'orderTableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchOrder() ) submitForm('listOrder_form', null, '<bean:write name="orderHolder" property="lastPage" />', null, null, 'orderTableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="orderHolder" property="realPage" />/<bean:write name="orderHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		// Checkbox 选择事件
		$('#orderTableWrapper input[id^="kanList_chkSelectRecord_"]').click(function(){
			if($(this).attr("checked")=="checked"){
				$('#orderTableWrapper input[id^="kanList_chkSelectRecord_"]').not($(this)).attr('checked', false);
			}
		});
		
		//Checkbox双击事件
		  jQuery("tbody tr").dblclick(function(){ 
			 kanlist_dbclickDailogBox($(this),"listOrder_form");
		 }); 
		
		  $(function(){
				var selectValue= $('.listOrder_form input[id="selectedIds"]').val();
				if(selectValue!=null||selectValue!=""){
					$("tbody tr").children("td").children("input[value="+selectValue+"]").attr("checked",'true');
				}
			});
	})(jQuery);
</script>