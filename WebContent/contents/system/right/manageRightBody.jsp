<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="systemRight" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">Ȩ������</label>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" /> 
			</div>
			<html:form action="rightAction.do?proc=add_object" styleClass="right_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="rightId" name="rightId" value="<bean:write name="rightForm" property="encodedId" />" /> 
				<html:hidden property="subAction" styleClass="subAction" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label>Ȩ������ �����ģ�<em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="right_namezh" /> 
						</li>
						<li>
							<label>Ȩ������ ��Ӣ�ģ�<em> *</em></label> 
							<html:text property="nameEN" maxlength="100" styleClass="right_nameen" /> 
						</li>
						<li>
							<label>Ȩ������<em> *</em></label> 
							<html:select property="rightType" styleClass="right_rightType">
								<html:optionsCollection property="rightTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="right_status">
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
		// ���ò˵�ѡ����ʽ
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_Right').addClass('selected');
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('right_form');
				// ����Subaction
        		$('.subAction').val('modifyObject');
        		// ����Page Title
    			$('#pageTitle').html('Ȩ�ޱ༭');
				// ���İ�ť��ʾ����
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.right_form').attr('action', 'rightAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			flag = flag + validate("right_namezh", true, "common", 100, 0);
    			flag = flag + validate("right_nameen", true, "common", 100, 0);
    			flag = flag + validate("right_rightType", true, "select", 0, 0);
    			flag = flag + validate("right_status", true, "common", 0, 0);
    			
    			if(flag == 0){
    				$('.right_form').submit();
    			}
        	}
		});
		
		// �鿴ģʽ
		if($('.subAction').val() == 'viewObject'){
			// ��Form��ΪDisable
			disableForm('right_form');
			// ����Page Title
			$('#pageTitle').html('Ȩ�޲�ѯ');
			// ������ťValue
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}
		
		$('#btnCancel').click( function () {
			link('rightAction.do?proc=list_object');
		});
	})(jQuery);
</script>
