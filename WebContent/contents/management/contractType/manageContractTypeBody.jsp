<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="systemAccount" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">��ͬ�������</label><logic:notEmpty name="contractTypeForm" property="typeId" ><label class="recordId"> &nbsp; (ID: <bean:write name="contractTypeForm" property="typeId" />)</label></logic:notEmpty>
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
							<label>�������ƣ����ģ�<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageContractType_nameZH" /> 
						</li>
						<li>
							<label>�������ƣ�Ӣ�ģ�</label> 
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
		// ���ò˵�ѡ����ʽ
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_ContractType').addClass('selected');
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.manageContractType_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageContractType_form');
				//״̬���ɸ���
				$(".manageContractType_status").attr('disabled',true);
				// �޸��ˡ��޸�ʱ�䲻�ɱ༭
	    		$('.decodeModifyBy').attr('disabled', 'disabled');
	    		$('.decodeModifyDate').attr('disabled', 'disabled');
				// ����Subaction
        		$('.manageContractType_form input.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// ����Page Title
        		$('#pageTitle').html('��ͬ���ͱ༭');
				// ����Form Action
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
		
		// �鿴ģʽ
		if($('.manageContractType_form input.subAction').val() != 'createObject'){
			// ��Form��ΪDisable
			disableForm('manageContractType_form');
			// ����SubAction
			$('.manageContractType_form input.subAction').val('viewObject');
			// ����Page Title
			$('#pageTitle').html('��ͬ���Ͳ�ѯ');
			// ������ťValue
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}
		
		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');

		// ����ģʽ
		if($('.manageContractType_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		$('#btnList').click( function () {
			if (agreest())
			link('contractTypeAction.do?proc=list_object');
		});
	})(jQuery);
</script>
