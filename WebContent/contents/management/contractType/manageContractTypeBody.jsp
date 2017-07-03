<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="systemAccount" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">合同类型添加</label><logic:notEmpty name="contractTypeForm" property="typeId" ><label class="recordId"> &nbsp; (ID: <bean:write name="contractTypeForm" property="typeId" />)</label></logic:notEmpty>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
			</div>
			<html:form action="contractTypeAction.do?proc=add_object" styleClass="manageContractType_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="typeId" id="typeId" value='<bean:write name="contractTypeForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="contractTypeForm" property="subAction" />' /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">					
						<li>
							<label>类型名称（中文）<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageContractType_nameZH" /> 
						</li>
						<li>
							<label>类型名称（英文）</label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageContractType_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageContractType_description"></html:textarea>
						</li>					
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageContractType_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>修改人</label> 
							<html:text property="decodeModifyBy" maxlength="100" disabled="disabled" styleClass="decodeModifyBy" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.modify.date" /></label> 
							<html:text property="decodeModifyDate" maxlength="100" disabled="disabled" styleClass="decodeModifyDate" /> 
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
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_ContractType').addClass('selected');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageContractType_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageContractType_form');
				//状态不可更改
				$(".manageContractType_status").attr('disabled',true);
				// 修改人、修改时间不可编辑
	    		$('.decodeModifyBy').attr('disabled', 'disabled');
	    		$('.decodeModifyDate').attr('disabled', 'disabled');
				// 更改Subaction
        		$('.manageContractType_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('合同类型编辑');
				// 更改Form Action
        		$('.manageContractType_form').attr('action', 'contractTypeAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageContractType_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageContractType_description", false, "common", 500, 0);
    			flag = flag + validate("manageContractType_status", true, "select", 0, 0);
    			
    			if(flag == 0){
    				$(".manageContractType_status").attr('disabled',false);
    				submit('manageContractType_form');
    			}
        	}
		});
		
		// 查看模式
		if($('.manageContractType_form input.subAction').val() != 'createObject'){
			// 将Form设为Disable
			disableForm('manageContractType_form');
			// 更改SubAction
			$('.manageContractType_form input.subAction').val('viewObject');
			// 更换Page Title
			$('#pageTitle').html('合同类型查询');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}
		
		// 修改人、修改时间不可编辑
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');

		// 创建模式
		if($('.manageContractType_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		$('#btnList').click( function () {
			if (agreest())
			link('contractTypeAction.do?proc=list_object');
		});
	})(jQuery);
</script>
