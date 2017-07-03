<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.BankTemplateDetailAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<!-- BankTemplateHeader Information Start-->
	<div class="box" id="BankTemplateHeader-Information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.salary.template" /></label>
			<logic:notEmpty name="bankTemplateHeaderForm" property="templateHeaderId" >
	        	<label class="recordId"> &nbsp; (ID: <bean:write name="bankTemplateHeaderForm" property="templateHeaderId" />)</label>
	        </logic:notEmpty>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE_HEADER">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<logic:empty name="bankTemplateHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditBankTemplateHeader" id="btnEditBankTemplateHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				<logic:notEmpty name="bankTemplateHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=BankTemplateDetailAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEditBankTemplateHeader" id="btnEditBankTemplateHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=BankTemplateDetailAction.accessAction%>">
					<input type="button" class="reset" name="btnListBankTemplateHeader" id="btnListBankTemplateHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/define/bankTemplate/header/form/manageBankTemplateHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	<!-- BankTemplateHeader Information End-->
	
	<!-- List BankTemplateDetail Information Start-->
	<div class="box" id="BankTemplateDetail-Information" style="display: none;">	
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="management" key="management.salary.template.header.search.title" /></label>
		</div>
		<div class="inner">	
			<div id="messageWrapper">
				<logic:present name="MESSAGE_DETAIL">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="modify" action="<%=BankTemplateDetailAction.accessAction%>">
					<input type="button" class="editbutton" id="btnEditBankTemplateDetail" name="btnEditBankTemplateDetail" value="<bean:message bundle="public" key="button.add" />"  />
					<input type="button" class="reset" name="btnCancelBankTemplateDetail" id="btnCancelBankTemplateDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listBankTemplateDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>
			<!-- BankTemplateDetailForm -->
			<div id="detailFormWrapper" style="display: none;">
				<jsp:include page="/contents/define/bankTemplate/detail/form/manageBankTemplateDetailForm.jsp" flush="true"/> 
			</div>
			<!-- if exist bean bankTemplateDetailHolder -->
			<logic:notEmpty name="bankTemplateDetailHolder">
				<html:form action="bankTemplateDetailAction.do?proc=list_object" styleClass="listBankTemplateDetail_form">
					<fieldset>		
						<input type="hidden" name="id" value="<bean:write name="bankTemplateHeaderForm" property="encodedId"/>"/>			
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="bankTemplateDetailHolder" property="sortColumn" />" /> 
						<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="bankTemplateDetailHolder" property="sortOrder" />" />
						<input type="hidden" name="page" id="page" value="<bean:write name="bankTemplateDetailHolder" property="page" />" />
						<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="bankTemplateDetailHolder" property="selectedIds" />" />
						<input type="hidden" name="subAction" id="subAction" value="" />					
					</fieldset>
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp ����table��Ӧ��jsp�ļ� -->  
					<jsp:include page="/contents/define/bankTemplate/detail/table/listBankTemplateDetailTable.jsp" flush="true"/> 
				</div>
			</logic:notEmpty>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
		<!-- Inner End-->
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// ��ʼ���˵�
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Configuration').addClass('selected');
		$('#menu_salary_Template').addClass('selected');
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();
		
		 // �༭��ť����¼� - List Header
		$('#btnEditBankTemplateHeader').click(function(){
			if( getSubAction() == 'viewObject' ){  
				// Enable����Form
        		enableForm('manageBankTemplateHeader_form');
        		// �����ֶ�����Disable
				$('.manageBankTemplateHeader_form input.manageBankTemplateHeader_bankId').attr('disabled','disabled');
        		// ���õ�ǰForm��SubActionΪ�޸�״̬
        		$('.manageBankTemplateHeader_form input#subAction').val('modifyObject'); 
        		// ����Form Action
        		$('.manageBankTemplateHeader_form').attr('action', 'bankTemplateHeaderAction.do?proc=modify_object');
        		// �޸İ�ť��ʾ����
        		$(this).val('<bean:message bundle="public" key="button.save" />');
        		// �޸�Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.salary.template" /> <bean:message bundle="public" key="oper.edit" />');
			}else{
    			if( validate_manage_primary_form() == 0 ){
    				enableForm('manageBankTemplateHeader_form');
    				submit('manageBankTemplateHeader_form');
    			}
			}
		});
		 
		$('#btnEditBankTemplateDetail').click(function(){
			// ��ȡBankTemplateDetailForm��subAction
			var detailSubAction = $('.manageBankTemplateDetail_form input#subAction').val();
			
			// ����������� 
			if( detailSubAction == '' ){
				// ��ʾCancel��ť
				$('#btnCancelBankTemplateDetail').show();
				// ��ʾList Detail Form
				$('#detailFormWrapper').show();	
				// ����SubActionΪ�½�
				$('.manageBankTemplateDetail_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$(this).val('<bean:message bundle="public" key="button.save" />');
			}
			// �鿴
			else if( detailSubAction == 'viewObject'){
				// �༭������Enable����Form
				enableForm('manageBankTemplateDetail_form');
				// �޸İ�ť��ʾ����
				$(this).val('<bean:message bundle="public" key="button.save" />');
				// ����SubActionΪ�༭
				$('.manageBankTemplateDetail_form input#subAction').val('modifyObject');
				// ����Form Action
				$('.manageBankTemplateDetail_form').attr('action', 'bankTemplateDetailAction.do?proc=modify_object');
			}
			// �༭
			else{
				// ͨ��JS��֤���ύFORM
				if( validate_manage_secondary_form() == 0){
					enableForm('manageBankTemplateDetail_form');
					submit('manageBankTemplateDetail_form');
				}
			}
		});
		
		// �б�ť����¼� - BankTemplate Header 
		$('#btnListBankTemplateHeader').click( function () {
			if (agreest())
			link('bankTemplateHeaderAction.do?proc=list_object');
		});
		
		// ȡ����ť����¼� - BankTemplate Detail
		$('#btnCancelBankTemplateDetail').click(function(){
			if(agreest())
			link('bankTemplateDetailAction.do?proc=list_object&id=<bean:write name="bankTemplateHeaderForm" property="encodedId"/>');
		});	
		 
		if( getSubAction() != 'createObject' ) {
			disableForm('manageBankTemplateHeader_form');
			$('#BankTemplateDetail-Information').show();
			$('.manageBankTemplateHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('<bean:message bundle="management" key="management.salary.template" /> <bean:message bundle="public" key="oper.view" />');
		} else if ( getSubAction() == 'createObject' ) {
		    $('#btnEditBankTemplateHeader').val('<bean:message bundle="public" key="button.save" />');
		}
	})(jQuery);
	
	// ��������ӣ�ajax����ȥ�޸�ҳ��
	function bankTemplateDetailModify( detailId ){
		// ��ʾCancel��ť
		$('#btnCancelBankTemplateDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// �޸İ�ť��ʾ����
		$('#btnEditBankTemplateDetail').val('<bean:message bundle="public" key="button.edit" />');
		var callback = "$(\".manageBankTemplateDetail_valueType\").trigger(\"change\");";
		// Ajax����BankTemplateDetail�޸�ҳ��
		loadHtmlWithRecall('#detailFormWrapper', 'bankTemplateDetailAction.do?proc=to_objectModify_ajax&id=' + detailId, true, callback);
	};
	
	// ��ǰ�Ƿ���ҪDisable
	function getDisable(){
		return getSubAction() == 'viewObject' ? true : false;
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageBankTemplateHeader_form input#subAction').val();
	};
</script>