<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.List"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.domain.system.ModuleVO"%>
<%@ page import="com.kan.base.domain.system.AccountVO"%>
<%@ page import="com.kan.base.web.renders.system.ModuleRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final AccountVO accountVO = (AccountVO) request.getAttribute("accountForm");
	String accountId = null;
	String initialized = null;
	
	if(accountVO != null && accountVO.getAccountId() != null){
	   accountId = accountVO.getAccountId();
	   initialized = accountVO.getInitialized();
	}
%>

<div id="content">
	<div id="systemAccount" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">�˻����</label>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" /> 
			</div>
			<html:form action="accountAction.do?proc=add_object" styleClass="account_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="accountId" name="accountId" value="<bean:write name="accountForm" property="encodedId" />" />
				<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="accountForm" property="subAction" />" />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em>Required field</label></li>
					</ol>
					<ol class="auto">
						<li>
							<label>�ʺ�����<em> *</em></label> 
							<html:select property="accountType" styleClass="account_accountType">
								<html:optionsCollection property="accountTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>�˻��� �����ģ�<em> *</em></label> 
							<html:text property="nameCN" maxlength="25" styleClass="account_namecn" /> 
						</li>
						<li>
							<label>�˻��� ��Ӣ�ģ�</label> 
							<html:text property="nameEN" maxlength="25" styleClass="account_nameen" /> 
						</li>
						<li>
							<label>��˾����<em> *</em></label> 
							<html:text property="entityName" maxlength="100" styleClass="account_entityName" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>��ϵ��</label> 
							<html:text property="linkman" maxlength="50" styleClass="account_linkman" />
						</li>
						<li>
							<label>�Ա�</label> 
							<html:select property="salutation" styleClass="account_salutation">
								<html:optionsCollection property="salutations" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>ְ��</label>
							<html:text property="title" maxlength="100" styleClass="account_title"/>
						</li>
						<li>
							<label>����</label> 
							<html:text property="department" maxlength="50" styleClass="account_department" />
						</li>
						<li>
							<label>�����绰</label> 
							<html:text property="bizPhone" maxlength="25" styleClass="account_bizPhone" />
						</li>
						<li>
							<label>˽�˵绰</label> 
							<html:text property="personalPhone" maxlength="25" styleClass="account_personalPhone" />
						</li>
						<li>
							<label>�����ֻ�</label> 
							<html:text property="bizMobile" maxlength="25" styleClass="account_bizMobile" />
						</li>
						<li>
							<label>˽���ֻ�</label> 
							<html:text property="personalMobile" maxlength="25" styleClass="account_personalMobile" />
						</li>
						<li>
							<label>�����绰</label> 
							<html:text property="otherPhone" maxlength="25" styleClass="account_otherPhone" />
						</li>
						<li>
							<label>����</label> 
							<html:text property="fax" maxlength="25" styleClass="account_fax" />
						</li>
						<li>
							<label>��������</label> 
							<html:text property="bizEmail" maxlength="100" styleClass="account_bizEmail" />
						</li>
						<li>
							<label>˽������</label> 
							<html:text property="personalEmail" maxlength="100" styleClass="account_personalEmail" />
						</li>
						<li>
							<label>��ַ</label> 
							<html:text property="website" maxlength="100" styleClass="account_website" />
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label>������������  <a title="��ѡ��ʾ�����������䷢�����ѻ�Ӫ����Ϣ"><img src="images/tips.png" /></a></label> 
							<html:checkbox property="canAdvBizEmail" styleClass="account_canAdvBizEmail" />
						</li>
						<li>
							<label>˽����������  <a title="��ѡ��ʾ������˽�����䷢�����ѻ�Ӫ����Ϣ"><img src="images/tips.png" /></a></label> 
							<html:checkbox property="canAdvPersonalEmail" styleClass="account_canAdvPersonalEmail" />
						</li>
						<li>
							<label>������������  <a title="��ѡ��ʾ������˾�ֻ��������ѻ�Ӫ������"><img src="images/tips.png" /></a></label> 
							<html:checkbox property="canAdvBizSMS" styleClass="account_canAdvBizSMS" />
						</li>
						<li>
							<label>˽�˶�������  <a title="��ѡ��ʾ������˽���ֻ��������ѻ�Ӫ������"><img src="images/tips.png" /></a></label> 
							<html:checkbox property="canAdvPersonalSMS" styleClass="account_canAdvPersonalSMS" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>���ڳ���</label> 
							<html:hidden property="cityIdTemp" styleClass="account_cityIdTemp"/>
							<html:select property="provinceId" styleClass="account_provinceId">
								<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>��ַ</label> 
							<html:text property="address" maxlength="100" styleClass="account_address" />
						</li>
						<li>
							<label>�ʱ�</label> 
							<html:text property="postcode" maxlength="25" styleClass="account_postcode" />
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label>��IP��ַ  <a title="ʹ�á�,���ָ�����á�x������"><img src="images/tips.png" /></a></label> 
							<html:text property="bindIP" maxlength="500" styleClass="account_bindIP" />
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="account_description" />
						</li>
						<li>
							<label>��ע</label> 
							<html:textarea property="comment" styleClass="account_comment" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="account_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<div id="tab"> 
						<div class="tabMenu"> 
							<ul> 
								<li id="tabMenu1" onClick="changeTab(1,3)" class="hover first">ģ��</li> 
							</ul> 
						</div> 
						<div class="tabContent"> 
							<div id="tabContent1" class="kantab kantree">
								<%
									if( initialized != null && initialized.equals("1") ){
									   out.print( ModuleRender.getModuleTreeByAccountId( request, true, accountId ) );
									} else {
									   out.print(ModuleRender.getModuleTreeBySelectedModules( request, true, ( List< ModuleVO >) request.getAttribute("selectedModules") ) );
									}
								%>
							</div>
						</div> 
					</div>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_Account').addClass('selected');
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('account_form');
        		<logic:equal name="accountForm" property="accountId" value="1">
        			$('.account_status').attr('disabled', 'disabled');
        		</logic:equal>
				// ����Subaction
        		$('.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// ����Page Title
    			$('#pageTitle').html('�˻��༭');
				// ����Form Action
        		$('.account_form').attr('action', 'accountAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
        		flag = flag + validate("account_accountType",  true, "select", 0, 0);
    			flag = flag + validate("account_namecn", true, "common", 25, 0);    			
    			flag = flag + validate("account_entityName", true, "common", 100, 0);
    			flag = flag + validate("account_bizPhone", false, "phone", 0, 0);
    			flag = flag + validate("account_personalPhone", false, "phone", 0, 0);
    			flag = flag + validate("account_otherPhone", false, "phone", 0, 0);
    			flag = flag + validate("account_fax", false, "phone", 0, 0);
    			flag = flag + validate("account_bizMobile", false, "mobile", 0, 0);
    			flag = flag + validate("account_personalMobile", false, "mobile", 0, 0);
    			flag = flag + validate("account_bizEmail", false, "email", 0, 0);
    			flag = flag + validate("account_personalEmail", false, "email", 0, 0);
    			flag = flag + validate("account_status", true, "common", 0, 0);
    			flag = flag + validate("account_description", false, "common", 2500, 0);
    			flag = flag + validate("account_comment", false, "common", 500, 0);
    			if(flag == 0){
    				submit('account_form');
    			}
        	}
		});
		
		// ��ʡChange�¼�
		$('.account_provinceId').change( function () { 
			provinceChange('account_provinceId', 'modifyObject', 0, '');
		});
		
		// �鿴ģʽ
		if($('.subAction').val() == 'viewObject'){
			// ����ʡ��Change�¼�,���󶨵�ǰCity
			if($('.account_provinceId').val() != '' && $('.account_provinceId').val() != "0"){
				provinceChange('account_provinceId', 'viewObject', $('.account_cityIdTemp').val(), '');
			}
			// ��Form��ΪDisable
			disableForm('account_form');
			// ���İ�ť��ʾ��
    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			// ����Page Title
			$('#pageTitle').html('�˻���ѯ');
		}
		
		$('#btnCancel').click( function () {
			link('accountAction.do?proc=list_object');
		});
	})(jQuery);
</script>
