<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<div id="content">
	<div id="systemUser" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<logic:equal value="1" name="role">雇佣状态添加</logic:equal>
				<logic:equal value="2" name="role">员工状态添加</logic:equal>
			</label>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
			</div>
			<html:form action="employeeStatusAction.do?proc=add_object" styleClass="manageEmployeeStatus_form">
				<%= BaseAction.addToken( request ) %>
				<html:hidden property="encodedId" /> 
				<html:hidden property="subAction" styleClass="subAction" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  		
					</ol>
					<ol class="auto">
						<li>
							<label>
								<logic:equal value="1" name="role">雇佣状态名称（中文）</logic:equal>
								<logic:equal value="2" name="role">员工状态名称（中文）</logic:equal>
								<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageEmployeeStatus_nameZH" />
						</li>
						<li>
							<label>
								<logic:equal value="1" name="role">雇佣状态名称（英文）</logic:equal>
								<logic:equal value="2" name="role">员工状态名称（英文）</logic:equal>
							</label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageEmployeeStatus_nameEN" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageEmployeeStatus_description"></html:textarea>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageEmployeeStatus_status">								
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />				
							</html:select>
							
						</li>	
						<li>
							<label>设为默认</label>
							<select name="setDefault" id="setDefault" class="setDefault">
								<option value="1">是</option>
								<option value="2" selected>否</option>
							</select>
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
		$('#menu_employee_Modules').addClass('current');	
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_EmploymentStatus').addClass('selected');
		
		// 初始化 设置默认下拉框值
		if($('.manageEmployeeStatus_form input.subAction').val() == 'viewObject'){
			if('<bean:write name="employeeStatusForm" property="setDefault"/>'==1){
				$("#setDefault").val('1');
			}else{
				$("#setDefault").val('2');
			}
			
		}

		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageEmployeeStatus_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageEmployeeStatus_form');
				// 更改Subaction
        		$('.manageEmployeeStatus_form input.subAction').val('modifyObject');
        		// 修改人、修改时间不可编辑
	    		$('.decodeModifyBy').attr('disabled', 'disabled');
	    		$('.decodeModifyDate').attr('disabled', 'disabled');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		if('<bean:write name="role"/>' == 1){
	        		$('#pageTitle').html('雇佣状态编辑');
        		}else{
        			$('#pageTitle').html('员工状态编辑');
        		}
				// 更改Form Action
        		$('.manageEmployeeStatus_form').attr('action', 'employeeStatusAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageEmployeeStatus_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageEmployeeStatus_description", false, "common", 500, 0);
    			flag = flag + validate("manageEmployeeStatus_status", true, "select", 0, 0);
    			
    			if (flag == 0) {
    				submit("manageEmployeeStatus_form");
    			}
        	}
		});
		
		// 查看模式
		if($('.manageEmployeeStatus_form input.subAction').val() != 'createObject'){
			// 将Form设为Disable
			disableForm('manageEmployeeStatus_form');
			// 更改SubAction
			$('.manageEmployeeStatus_form input.subAction').val('viewObject');
			// 更换Page Title
			if('<bean:write name="role"/>' == 1){
				$('#pageTitle').html('雇佣状态查询');
			}else{
				$('#pageTitle').html('员工状态查询');
			}
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}

		// 创建模式
		if($('.manageEmployeeStatus_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		// 修改人、修改时间不可编辑
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');


		$("#btnList").click(function() {
			if (agreest())
			link('employeeStatusAction.do?proc=list_object');
		});
	})(jQuery);
</script>
