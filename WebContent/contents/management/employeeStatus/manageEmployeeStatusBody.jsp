<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<div id="content">
	<div id="systemUser" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<logic:equal value="1" name="role">��Ӷ״̬���</logic:equal>
				<logic:equal value="2" name="role">Ա��״̬���</logic:equal>
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
								<logic:equal value="1" name="role">��Ӷ״̬���ƣ����ģ�</logic:equal>
								<logic:equal value="2" name="role">Ա��״̬���ƣ����ģ�</logic:equal>
								<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageEmployeeStatus_nameZH" />
						</li>
						<li>
							<label>
								<logic:equal value="1" name="role">��Ӷ״̬���ƣ�Ӣ�ģ�</logic:equal>
								<logic:equal value="2" name="role">Ա��״̬���ƣ�Ӣ�ģ�</logic:equal>
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
							<label>��ΪĬ��</label>
							<select name="setDefault" id="setDefault" class="setDefault">
								<option value="1">��</option>
								<option value="2" selected>��</option>
							</select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>�޸���</label> 
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
		
		// ��ʼ�� ����Ĭ��������ֵ
		if($('.manageEmployeeStatus_form input.subAction').val() == 'viewObject'){
			if('<bean:write name="employeeStatusForm" property="setDefault"/>'==1){
				$("#setDefault").val('1');
			}else{
				$("#setDefault").val('2');
			}
			
		}

		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.manageEmployeeStatus_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageEmployeeStatus_form');
				// ����Subaction
        		$('.manageEmployeeStatus_form input.subAction').val('modifyObject');
        		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
	    		$('.decodeModifyBy').attr('disabled', 'disabled');
	    		$('.decodeModifyDate').attr('disabled', 'disabled');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// ����Page Title
        		if('<bean:write name="role"/>' == 1){
	        		$('#pageTitle').html('��Ӷ״̬�༭');
        		}else{
        			$('#pageTitle').html('Ա��״̬�༭');
        		}
				// ����Form Action
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
		
		// �鿴ģʽ
		if($('.manageEmployeeStatus_form input.subAction').val() != 'createObject'){
			// ��Form��ΪDisable
			disableForm('manageEmployeeStatus_form');
			// ����SubAction
			$('.manageEmployeeStatus_form input.subAction').val('viewObject');
			// ����Page Title
			if('<bean:write name="role"/>' == 1){
				$('#pageTitle').html('��Ӷ״̬��ѯ');
			}else{
				$('#pageTitle').html('Ա��״̬��ѯ');
			}
			// ������ťValue
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}

		// ����ģʽ
		if($('.manageEmployeeStatus_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');


		$("#btnList").click(function() {
			if (agreest())
			link('employeeStatusAction.do?proc=list_object');
		});
	})(jQuery);
</script>
