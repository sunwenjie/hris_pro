<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.management.IndustryTypeAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
	<div id="industryType" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">��ҵ�������</label><logic:notEmpty name="industryTypeForm" property="typeId" ><label class="recordId"> &nbsp; (ID: <bean:write name="industryTypeForm" property="typeId" />)</label></logic:notEmpty>
		</div>
		<div class="inner">
			<div class="top">
				<logic:empty name="industryTypeForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="industryTypeForm" property="encodedId">
					<kan:auth right="modify" action="<%=IndustryTypeAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=IndustryTypeAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="industryTypeAction.do?proc=add_object" styleClass="manageIndustryType_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="typeId" id="typeId" value='<bean:write name="industryTypeForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="industryTypeForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">					
						<li>
							<label>��ҵ�������ƣ����ģ�<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageIndustryType_nameZH" /> 
						</li>
						<li>
							<label>��ҵ�������ƣ�Ӣ�ģ�</label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageIndustryType_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageIndustryType_description"></html:textarea>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageIndustryType_status">
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
		$('#menu_client_Modules').addClass('current');
		$('#menu_client_Configuration').addClass('selected');
		$('#menu_client_IndustryType').addClass('selected');
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.manageIndustryType_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageIndustryType_form');
        		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
        		$('.decodeModifyBy').attr('disabled', 'disabled');
        		$('.decodeModifyDate').attr('disabled', 'disabled');
				// ����Subaction
        		$('.manageIndustryType_form input.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// ����Page Title
        		$('#pageTitle').html('��ҵ���ͱ༭');
				// ����Form Action
        		$('.manageIndustryType_form').attr('action', 'industryTypeAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageIndustryType_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageIndustryType_description", false, "common", 500, 0);
    			flag = flag + validate("manageIndustryType_status", true, "select", 0, 0);
    			
    			if(flag == 0){
    				submit('manageIndustryType_form');
    			}
        	}
		});

		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');
		
		// �鿴ģʽ
		if($('.manageIndustryType_form input.subAction').val() != 'createObject'){
			// ��Form��ΪDisable
			disableForm('manageIndustryType_form');
			// ����ҳ���ʼ��SubAction
			$('.manageIndustryType_form input.subAction').val('viewObject');
			// ����Page Title
			$('#pageTitle').html('��ҵ���Ͳ�ѯ');
			// ������ťValue
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}

		// ����ģʽ
		if($('.manageIndustryType_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}
		
		$('#btnList').click( function () {
			if (agreest())
			link('industryTypeAction.do?proc=list_object');
		});
	})(jQuery);
</script>
