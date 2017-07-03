<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.management.ItemMappingAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan" %>

<div id="content">
	<div id="itemMapping" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.item.mapping" /></label>
		</div>
		<div class="inner">
			<div class="top">
				<logic:empty name="itemMappingForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" />
				</logic:empty>
				<logic:notEmpty name="itemMappingForm" property="encodedId">
					<kan:auth right="modify" action="<%=ItemMappingAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" />
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=ItemMappingAction.accessAction%>">
					<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
			</div>
			<html:form action="itemMappingAction.do?proc=add_object" styleClass="manageItemMapping_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="itemMappingId" id="itemMappingId" value='<bean:write name="itemMappingForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="itemMappingForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.item" /><em> *</em></label> 
							<html:select property="itemId" styleClass="manageItemMapping_itemId" >
								<html:optionsCollection property="items" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="security" key="security.entity" /><em> *</em></label> 
							<html:select property="entityId" styleClass="manageItemMapping_entityId" >
								<html:optionsCollection property="entitys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.business.type" /><em> *</em></label> 
							<html:select property="businessTypeId" styleClass="manageItemMapping_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li id="selectIsFeasible">
						
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="management" key="management.item.mapping.saleDebit" /></label> 
							<html:text property="saleDebit" maxlength="100" styleClass="manageCertification_saleDebit" /> 
						</li>	
						<li>
							<label><bean:message bundle="management" key="management.item.mapping.saleDebitBranch" /></label> 
							<html:select property="saleDebitBranch" styleClass="manageItemMapping_saleDebitBranch">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						
						<li>
							<label><bean:message bundle="management" key="management.item.mapping.saleCredit" /></label> 
							<html:text property="saleCredit" maxlength="100" styleClass="manageCertification_saleDebit" /> 
						</li>	
						<li>
							<label><bean:message bundle="management" key="management.item.mapping.saleCreditBranch" /></label> 
							<html:select property="saleCreditBranch" styleClass="manageItemMapping_saleCreditBranch">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.mapping.costDebit" /></label> 
							<html:text property="costDebit" maxlength="100" styleClass="manageCertification_costDebit" /> 
						</li>	
						<li>
							<label><bean:message bundle="management" key="management.item.mapping.costDebitBranch" /></label> 
							<html:select property="costDebitBranch" styleClass="manageItemMapping_costDebitBranch">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.item.mapping.costCredit" /></label> 
							<html:text property="costCredit" maxlength="100" styleClass="manageCertification_costCredit" /> 
						</li>	
						<li>
							<label><bean:message bundle="management" key="management.item.mapping.costCreditBranch" /></label> 
							<html:select property="costCreditBranch" styleClass="manageItemMapping_costCreditBranch">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageItemMapping_description"></html:textarea>
						</li>					
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageItemMapping_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>	
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
		$('#menu_finance_ItemMapping').addClass('selected');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageItemMapping_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageItemMapping_form');
				// 更改Subaction
        		$('.manageItemMapping_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.item.mapping" /> <bean:message bundle="public" key="oper.edit" />');
				// 更改Form Action
        		$('.manageItemMapping_form').attr('action', 'itemMappingAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
        		// 验证
        		flag = flag + validate('manageItemMapping_itemId', true, 'select', 0, 0);
        		flag = flag + validate('manageItemMapping_entityId', true, 'select', 0, 0);
        		flag = flag + validate('manageItemMapping_businessTypeId', true, 'select', 0, 0);
        		
        		flag = flag + validate('manageItemMapping_saleDebitBranch', true, 'select', 0, 0);
        		flag = flag + validate('manageItemMapping_saleCreditBranch', true, 'select', 0, 0);
        		flag = flag + validate('manageItemMapping_costDebitBranch', true, 'select', 0, 0);
        		flag = flag + validate('manageItemMapping_costCreditBranch', true, 'select', 0, 0);
        		
        		flag = flag + validate('manageItemMapping_description', false, 'common', 0, 0);
        		flag = flag + validate('manageItemMapping_status', true, 'select', 0, 0);
    			
    			
    			if(flag == 0){
    				
    				if($('#isFeasible').val() == '2'){
    					alert('选择数据重复，请重新选择！');
    					$('.manageItemMapping_itemId').val('0');
    					$('.manageItemMapping_entityId').val('0');
    					$('.manageItemMapping_businessTypeId').val('0');
    				}else{
    					submit('manageItemMapping_form');
    				}
    			}
        	}
		});
		$('.manageItemMapping_itemId').change(function(){
			selectIsFeasible();
		});
		$('.manageItemMapping_entityId').change(function(){
			selectIsFeasible();
		});
		$('.manageItemMapping_businessTypeId').change(function(){
			selectIsFeasible();
		});
		
		
		// 查看模式
		if($('.manageItemMapping_form input.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('manageItemMapping_form');
			// 更换Page Title
    		$('#pageTitle').html('<bean:message bundle="management" key="management.item.mapping" /> <bean:message bundle="public" key="oper.view" />');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}
		
		$('#btnCancel').click( function () {
			link('itemMappingAction.do?proc=list_object');
		});
		
	})(jQuery);
	
	// 根据选择的：科目、法务实体、业务类型，判断是否可行。
	function selectIsFeasible(){
		// 当前选中科目
		var itemId = $('.manageItemMapping_itemId').val();
		// 当前选中法务实体
		var entityId = $('.manageItemMapping_entityId').val();
		// 当前选择业务类型
		var businessTypeId = $('.manageItemMapping_businessTypeId').val();
		// 初始化返回值
		var selectIsFeasible = '';
		
		if(itemId != 0 && entityId != 0 && businessTypeId != 0){
			loadHtml("#selectIsFeasible", "itemMappingAction.do?proc=add_object_ajax&itemId=" + itemId + "&entityId=" + entityId + "&businessTypeId=" + businessTypeId, false);
		}
	}
</script>
