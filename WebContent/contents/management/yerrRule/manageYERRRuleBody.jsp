<%@page import="com.kan.base.web.actions.management.YERRRuleAction"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="systemUser" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">��Ч�Ƿ�����</label>
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
				<logic:empty name="yerrRuleForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="yerrRuleForm" property="encodedId">
					<%-- <kan:auth right="modify" action="<%=YERRRuleAction.accessAction%>"> --%>
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					<%-- </kan:auth> --%>
				</logic:notEmpty>
				<%-- <kan:auth right="list" action="<%=YERRRuleAction.accessAction%>"> --%>
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				<%-- </kan:auth> --%>
			</div>
			<html:form action="yerrRuleAction.do?proc=add_object" styleClass="manageYERRRule_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="encodedId" value='<bean:write name="yerrRuleForm" property="encodedId" />'>
				<html:hidden property="subAction" styleClass="subAction" />
				<fieldset>
					<ol class="auto">	
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  					
					</ol>
					<ol class="auto">
						<li>
							<label>��Ч����<em> *</em></label> 
							<html:select property="rating" styleClass="manageYERRRule_rating">								
								<html:optionsCollection property="ratings" value="mappingId" label="mappingValue" />				
							</html:select>
						</li>
						<li>
							<label>������� % <em> *</em></label> 
							<html:text property="distribution" maxlength="4" styleClass="manageYERRRule_distribution"  />
						</li>	
					</ol>
					<ol class="auto">
						<li>
							<label>ҵ�� / RBM<em> *</em></label> 
							<html:text property="meritRateRMB" maxlength="5" styleClass="manageYERRRule_meritRateRMB" />
						</li>
						<li>
							<label>ҵ��+���� / RBM<em> *</em></label> 
							<html:text property="meritAndPromotionRateRMB" maxlength="5" styleClass="manageYERRRule_meritAndPromotionRateRMB" />
						</li>
						<li>
							<label>ҵ�� / HKD<em> *</em></label> 
							<html:text property="meritRateHKD" maxlength="5" styleClass="manageYERRRule_meritRateHKD" />
						</li>
						<li>
							<label>ҵ��+���� / HKD<em> *</em></label> 
							<html:text property="meritAndPromotionRateHKD" maxlength="5" styleClass="manageYERRRule_meritAndPromotionRateHKD" />
						</li>
						<li>
							<label>ҵ�� / SGD<em> *</em></label> 
							<html:text property="meritRateSGD" maxlength="5" styleClass="manageYERRRule_meritRateSGD" />
						</li>
						<li>
							<label>ҵ��+���� / SGD<em> *</em></label> 
							<html:text property="meritAndPromotionRateSGD" maxlength="5" styleClass="manageYERRRule_meritAndPromotionRateSGD" />
						</li>						
					</ol>
					<ol class="auto">
						<li>
							<label>�����Ƿ�<em> *</em></label> 
							<html:text property="bounsRate" maxlength="5" styleClass="manageYERRRule_bounsRate" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchlanguage_status">
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
		$('#menu_employee_Modules').addClass('current');	
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_Languages').addClass('selected');
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.manageYERRRule_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageYERRRule_form');
				// �޸��ˡ��޸�ʱ�䲻�ɱ༭
				$('.decodeModifyBy').attr('disabled', 'disabled');
				$('.decodeModifyDate').attr('disabled', 'disabled');
				// ����Subaction
        		$('.manageYERRRule_form input.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.manageYERRRule_form').attr('action', 'yerrRuleAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
        		
        		flag = flag + validate("manageYERRRule_rating", true, "select", 0, 0);
        		flag = flag + validate("manageYERRRule_distribution", true, "currency", 4, 0);
        		flag = flag + validate("manageYERRRule_meritRateRMB", true, "currency", 5, 0)
        		flag = flag + validate("manageYERRRule_meritAndPromotionRateRMB", true, "currency", 5, 0)
        		flag = flag + validate("manageYERRRule_meritRateHKD", true, "currency", 5, 0)
        		flag = flag + validate("manageYERRRule_meritAndPromotionRateHKD", true, "currency", 5, 0)
        		flag = flag + validate("manageYERRRule_meritRateSGD", true, "currency", 5, 0)
        		flag = flag + validate("manageYERRRule_meritAndPromotionRateSGD", true, "currency", 5, 0)
        		flag = flag + validate("manageYERRRule_bounsRate", true, "currency", 5, 0)
        		flag = flag + validate("manageYERRRule_status", true, "select", 0, 0);
    			
    			if(flag == 0){
    				submit('manageYERRRule_form');
    			}
        	}
		});
		
		// �鿴ģʽ
		if($('.manageYERRRule_form input.subAction').val() != 'createObject'){
			// ��Form��ΪDisable
			disableForm('manageYERRRule_form');
			// ����Subaction
    		$('.manageYERRRule_form input.subAction').val('viewObject');
			// ������ťValue
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}

		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');

		// ����ģʽ
		if($('.manageYERRRule_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		$('#btnList').click( function () {
			if (agreest())
			link('yerrRuleAction.do?proc=list_object');
		});
	})(jQuery);
</script>
