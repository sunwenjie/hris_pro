<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.management.ItemGroupAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan" %>

<div id="content">
	<div id="manageItemGroupGroup" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.item.group" /></label>
		</div>
		<div class="inner">
			<div class="top">
				<logic:empty name="itemGroupForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" />
				</logic:empty>
				<logic:notEmpty name="itemGroupForm" property="encodedId">
					<kan:auth right="modify" action="<%=ItemGroupAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" />
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=ItemGroupAction.accessAction%>">
					<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
			</div>
			<html:form action="itemGroupAction.do?proc=add_object" styleClass="manageItemGroup_form">
				<%= BaseAction.addToken( request ) %>
				<html:hidden property="encodedId" /> 
				<html:hidden property="subAction" styleClass="subAction" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.item.group.name.cn" /><em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageItemGroup_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.group.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageItemGroup_nameEN" /> 
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.item.group.type" /><em> *</em></label> 
							<html:select property="itemGroupType" styleClass="manageItemMapping_itemGroupType" >
								<html:optionsCollection property="itemGroupTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.group.bind.item" /></label> 
							<html:select property="bindItemId" styleClass="manageItemMapping_bindItemId" >
								<html:optionsCollection property="bindItems" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.item.group.listMerge" /><em> *</em></label> 
							<html:select property="listMerge" styleClass="manageItemGroup_listMerge">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.group.reportMerge" /><em> *</em></label> 
							<html:select property="reportMerge" styleClass="manageItemGroup_reportMerge">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.group.invoiceMerge" /><em> *</em></label> 
							<html:select property="invoiceMerge" styleClass="manageItemGroup_invoiceMerge">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>	
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageItemGroup_description"></html:textarea>
						</li>	
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageItemGroup_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<div id="special_info">
						<jsp:include page="/contents/management/itemGroup/table/manageItemGroupExtend.jsp" flush="true"/> 
					</div>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_finance_Modules').addClass('current');		
		$('#menu_finance_Configuration').addClass('selected');
		$('#menu_finance_ItemGroup').addClass('selected');
		
		//	如果是编辑模式下,隐藏添加按钮 
		if($('.subAction').val() == 'viewObject'){
			$('#btnAddItem').hide();
		}

		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageItemGroup_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageItemGroup_form');
				// 更改Subaction
        		$('.manageItemGroup_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.item.group" /> <bean:message bundle="public" key="oper.edit" />');
				// 更改Form Action
        		$('.manageItemGroup_form').attr('action', 'itemGroupAction.do?proc=modify_object');
				
				// 显示添加元素按钮图标
       			$('img[id^=warning_img]').each(function(i){
       				$(this).show();
       			});
       			$('img[id^=disable_img]').each(function(i){
       				$(this).hide();
       			});
       			//	显示添加元素按钮
     			$('#btnAddItem').show();
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageItemGroup_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageItemGroup_listMerge", true, "select", 0, 0);
    			flag = flag + validate("manageItemGroup_reportMerge", true, "select", 0, 0);
    			flag = flag + validate("manageItemGroup_invoiceMerge", true, "select", 0, 0);
    			flag = flag + validate("manageItemGroup_description", false, "common", 500, 0);
    			flag = flag + validate("manageItemGroup_status", true, "select", 0, 0);
    			flag = flag + validate("manageItemMapping_itemGroupType", true, "select", 0, 0);
    			if(flag == 0){
    				submit('manageItemGroup_form');
    			}
        	}
		});
		
		// 查看模式
		if($('.manageItemGroup_form input.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('manageItemGroup_form');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="management" key="management.item.group" /> <bean:message bundle="public" key="oper.view" />');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}
		
		$('#btnCancel').click( function () {
			link('itemGroupAction.do?proc=list_object');
		});
	})(jQuery);
</script>
