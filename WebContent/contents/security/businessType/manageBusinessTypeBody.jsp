<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.security.BusinessTypeAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="businessType" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="security" key="security.business.type" />
			</label>
			<logic:notEmpty name="businessTypeForm" property="businessTypeId" >
				<label class="recordId"> &nbsp; (ID: <bean:write name="businessTypeForm" property="businessTypeId" />)</label>
			</logic:notEmpty>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message success fadable">
						<bean:write name="MESSAGE" />
			    		<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<logic:empty name="businessTypeForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="businessTypeForm" property="encodedId">
					<kan:auth right="modify" action="<%=BusinessTypeAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=BusinessTypeAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="businessTypeAction.do?proc=add_object" styleClass="manageBusinessType_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="id" id="businessTypeId" value='<bean:write name="businessTypeForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="businessTypeForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">					
						<li>
							<label><bean:message bundle="security" key="security.business.type.name.cn" /><em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageBusinessType_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="security" key="security.business.type.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageBusinessType_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageBusinessType_description"></html:textarea>
						</li>					
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageBusinessType_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.modify.by" /></label> 
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
		$('#menu_security_Modules').addClass('current');	
		$('#menu_seciruty_BusinessType').addClass('selected');
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.manageBusinessType_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageBusinessType_form');
				// �޸��ˡ��޸�ʱ�䲻�ɱ༭
				$('.decodeModifyBy').attr('disabled', 'disabled');
				$('.decodeModifyDate').attr('disabled', 'disabled');
				// ����Subaction
        		$('.manageBusinessType_form input.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// ����Page Title
        		$('#pageTitle').html('<bean:message bundle="security" key="security.business.type" />' + ' ' + '<bean:message bundle="public" key="oper.edit" />');
				// ����Form Action
        		$('.manageBusinessType_form').attr('action', 'businessTypeAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			flag = flag + validate("manageBusinessType_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageBusinessType_description", false, "common", 500, 0);
    			flag = flag + validate("manageBusinessType_status", true, "select", 0, 0);
    			
    			if(flag == 0){
    				submit('manageBusinessType_form');
    			}
        	}
		});
		
		// �鿴ģʽ
		if($('.manageBusinessType_form input.subAction').val() != 'createObject'){
			// ��Form��ΪDisable
			disableForm('manageBusinessType_form');
			// ����Page Title
			$('#pageTitle').html('<bean:message bundle="security" key="security.business.type" />' + ' ' + '<bean:message bundle="public" key="oper.view" />');
			// ����Subaction
    		$('.manageBusinessType_form input.subAction').val('viewObject');
			// ������ťValue
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}

		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');

		// ����ģʽ
		if($('.manageBusinessType_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		$('#btnList').click( function () {
			if (agreest())
			link('businessTypeAction.do?proc=list_object');
		});
	})(jQuery);
</script>
