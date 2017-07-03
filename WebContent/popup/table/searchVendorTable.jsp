<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder  vendorHolder = (PagedListHolder) request.getAttribute("vendorHolder");
%>

<div class="top">
	<input type="button" id="btn_select" class="btn" value="<bean:message bundle="public" key="button.selected" />" onclick="selectVendor()"/>
	<input type="button" id="btn_cancel" name="btn_cancel" class="btn reset" onclick="$('#vendorModalId').addClass('hide');$('#shield').hide();" value="<bean:message bundle="public" key="button.exit" />" />
</div>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" disabled="disabled" style="border-color:#CCCCCC" />
			</th>
			<th style="width: 20%" class="header <%=vendorHolder.getCurrentSortClass("vendorId")%>">
				<a onclick="if( checkSearchVendor() ) submitForm('listVendor_form', null, null, 'vendorId', '<%=vendorHolder.getNextSortOrder("vendorId")%>', 'vendorTableWrapper');"><bean:message bundle="business" key="business.vendor.id" /></a>
			</th>
			<th style="width: 40%" class="header <%=vendorHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="if( checkSearchVendor() ) submitForm('listVendor_form', null, null, 'nameZH', '<%=vendorHolder.getNextSortOrder("nameZH")%>', 'vendorTableWrapper');"><bean:message bundle="business" key="business.vendor.name.cn" /></a>
			</th>
			<th style="width: 40%" class="header <%=vendorHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="if( checkSearchVendor() ) submitForm('listVendor_form', null, null, 'nameEN', '<%=vendorHolder.getNextSortOrder("nameEN")%>', 'vendorTableWrapper');"><bean:message bundle="business" key="business.vendor.name.en" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="vendorHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="vendorVO" name="vendorHolder" property="source" indexId="certificateNumber">
				<tr class='<%=certificateNumber % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="vendorVO" property="vendorId"/>" name="chkSelectRow[]" value="<bean:write name="vendorVO" property="vendorId"/>" />
					</td>
					<td class="left"><bean:write name="vendorVO" property="vendorId" /></td>
					<td class="left"><bean:write name="vendorVO" property="nameZH" /></td>
					<td class="left"><bean:write name="vendorVO" property="nameEN" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="vendorHolder">
		<tfoot>
			<tr class="total">
				<td colspan="5" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="vendorHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="vendorHolder" property="indexStart" /> - <bean:write name="vendorHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="if( checkSearchVendor() ) submitForm('listVendor_form', null, '<bean:write name="vendorHolder" property="firstPage" />', null, null, 'vendorTableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchVendor() ) submitForm('listVendor_form', null, '<bean:write name="vendorHolder" property="previousPage" />', null, null, 'vendorTableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchVendor() ) submitForm('listVendor_form', null, '<bean:write name="vendorHolder" property="nextPage" />', null, null, 'vendorTableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="if( checkSearchVendor() ) submitForm('listVendor_form', null, '<bean:write name="vendorHolder" property="lastPage" />', null, null, 'vendorTableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="vendorHolder" property="realPage" />/<bean:write name="vendorHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		// Checkbox 选择事件
		$('#vendorTableWrapper input[id^="kanList_chkSelectRecord_"]').click(function(){
			if($(this).attr("checked")=="checked"){
				$('#vendorTableWrapper input[id^="kanList_chkSelectRecord_"]').not($(this)).attr('checked', false);
			}
		});
		
		//Checkbox双击事件
		  jQuery("tbody tr").dblclick(function(){ 
			 kanlist_dbclickDailogBox($(this),"listVendor_form");
		 }); 
		
		
		  $(function(){
				var selectValue= $('.listVendor_form input[id="selectedIds"]').val();
				if(selectValue!=null||selectValue!=""){
					$("tbody tr").children("td").children("input[value="+selectValue+"]").attr("checked",'true');
				}
			});
	})(jQuery);
</script>