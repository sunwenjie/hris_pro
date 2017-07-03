<%@page import="com.kan.base.domain.management.ItemGroupVO"%>
<%@page import="com.kan.base.web.renders.management.ItemRender"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final ItemGroupVO itemGroupVO = ( ItemGroupVO ) request.getAttribute("itemGroupForm");
	String[] itemIdArray = null;
	if( itemGroupVO != null && itemGroupVO.getItemIdArray() != null){
	   itemIdArray = itemGroupVO.getItemIdArray();
	} 
%>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,1)" class="hover first"><bean:message bundle="management" key="management.item.group.bind.item" /></li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab kanThinkingCombo">
			<span><a onclick="link('itemAction.do?proc=to_objectNew');" id="addVendorContact"><bean:message bundle="management" key="management.item.group.add.item" /></a></span>
			<%= ItemRender.getItemThinkingManagement( request, itemIdArray ) %>
		</div>
	</div>
	<br/>
</div>

<script type="text/javascript">
		(function($) {	
			//绑定联想框到参数名
			bindThinkingToItemName();
			
		})(jQuery);
		
		function bindThinkingToItemName(){
			// Use the common thinking
			kanThinking_column('thinking_itemName', 'thinking_itemId', 'itemAction.do?proc=list_object_json&type=0');
		};
		
		// 移除参数事件
		function removeItem(id){
			if(confirm("确定移除选中参数？")){
				$('#' + id).remove();
			}
		}; 
		
		// 绑定参数事件
		function addItem(){
			var flag =  false;
			var itemId = $('#itemId').val();
			$("input[id^='itemIdArray']").each(function(){
				if($(this).val() == itemId){
					flag = true;
				}
			});	
			if(itemId == '' || itemId == null){
				alert('请先填写必要的信息哦！');
				//return;
			}else if(flag){
				alert('选择的参数已存在，请重新选择！');
				//return;
			}else{
				$('#itemList').append("<li id=\"mannageItem_" + itemId + "\" style=\"margin: 2px 0px;\">" + 
					"<input type=\"hidden\" id=\"itemIdArray\" name=\"itemIdArray\" value=\"" + itemId + "\">" +
					"<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" style=\"display: none;\" name=\"disable_img\">" +
					"<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onClick=\"removeItem('mannageItem_" + itemId + "');\"/> &nbsp;&nbsp; " + $('#itemName').val() + "</li>");
			}
			//	清空输入框
			$('#itemId').val('');
			$('#itemName').val('');
		};
</script>