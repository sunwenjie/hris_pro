<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.TaxTemplateDetailAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<!-- TaxTemplateHeader Information Start-->
	<div class="box" id="TaxTemplateHeader-Information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.tax.template" /></label>
			<logic:notEmpty name="taxTemplateHeaderForm" property="templateHeaderId" >
	        	<label class="recordId"> &nbsp; (ID: <bean:write name="taxTemplateHeaderForm" property="templateHeaderId" />)</label>
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
				<logic:empty name="taxTemplateHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditTaxTemplateHeader" id="btnEditTaxTemplateHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				<logic:notEmpty name="taxTemplateHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=TaxTemplateDetailAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEditTaxTemplateHeader" id="btnEditTaxTemplateHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=TaxTemplateDetailAction.accessAction%>">
					<input type="button" class="reset" name="btnListTaxTemplateHeader" id="btnListTaxTemplateHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/define/taxTemplate/header/form/manageTaxTemplateHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	<!-- TaxTemplateHeader Information End-->
	
	<!-- List TaxTemplateDetail Information Start-->
	<div class="box" id="TaxTemplateDetail-Information" style="display: none;">	
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="management" key="management.tax.template.detail.search.title" /></label>
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
				<kan:auth right="modify" action="<%=TaxTemplateDetailAction.accessAction%>">
					<input type="button" class="editbutton" id="btnEditTaxTemplateDetail" name="btnEditTaxTemplateDetail" value="<bean:message bundle="public" key="button.add" />"  />
					<input type="button" class="reset" name="btnCancelTaxTemplateDetail" id="btnCancelTaxTemplateDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listTaxTemplateDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>
			<!-- TaxTemplateDetailForm -->
			<div id="detailFormWrapper" style="display: none;">
				<jsp:include page="/contents/define/taxTemplate/detail/form/manageTaxTemplateDetailForm.jsp" flush="true"/> 
			</div>
			<!-- if exist bean taxTemplateDetailHolder -->
			<logic:notEmpty name="taxTemplateDetailHolder">
				<html:form action="taxTemplateDetailAction.do?proc=list_object" styleClass="listTaxTemplateDetail_form">
					<fieldset>		
						<input type="hidden" name="id" value="<bean:write name="taxTemplateHeaderForm" property="encodedId"/>"/>			
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="taxTemplateDetailHolder" property="sortColumn" />" /> 
						<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="taxTemplateDetailHolder" property="sortOrder" />" />
						<input type="hidden" name="page" id="page" value="<bean:write name="taxTemplateDetailHolder" property="page" />" />
						<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="taxTemplateDetailHolder" property="selectedIds" />" />
						<input type="hidden" name="subAction" id="subAction" value="" />					
					</fieldset>
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp ����table��Ӧ��jsp�ļ� -->  
					<jsp:include page="/contents/define/taxTemplate/detail/table/listTaxTemplateDetailTable.jsp" flush="true"/> 
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
		$('#menu_salary_IncomeTaxTemplate').addClass('selected');
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();
		
		 // �༭��ť����¼� - List Header
		$('#btnEditTaxTemplateHeader').click(function(){
			if( getSubAction() == 'viewObject' ){  
				// Enable����Form
        		enableForm('manageTaxTemplateHeader_form');
        		// �����ֶ�����Disable
				$('.manageTaxTemplateHeader_form input.manageTaxTemplateHeader_taxId').attr('disabled','disabled');
        		// ���õ�ǰForm��SubActionΪ�޸�״̬
        		$('.manageTaxTemplateHeader_form input#subAction').val('modifyObject'); 
        		// ����Form Action
        		$('.manageTaxTemplateHeader_form').attr('action', 'taxTemplateHeaderAction.do?proc=modify_object');
        		// �޸İ�ť��ʾ����
        		$(this).val('<bean:message bundle="public" key="button.save" />');
        		// �޸�Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.tax.template" /> <bean:message bundle="public" key="oper.edit" />');
			}else{
    			if( validate_manage_primary_form() == 0 ){
    				enableForm('manageTaxTemplateHeader_form');
    			    submit('manageTaxTemplateHeader_form');
    			}
			}
		});
		 
		$('#btnEditTaxTemplateDetail').click(function(){
			// ��ȡTaxTemplateDetailForm��subAction
			var detailSubAction = $('.manageTaxTemplateDetail_form input#subAction').val();
			
			// ����������� 
			if( detailSubAction == '' ){
				// ��ʾCancel��ť
				$('#btnCancelTaxTemplateDetail').show();
				// ��ʾList Detail Form
				$('#detailFormWrapper').show();	
				// ����SubActionΪ�½�
				$('.manageTaxTemplateDetail_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$(this).val('<bean:message bundle="public" key="button.save" />');
			}
			// �鿴
			else if( detailSubAction == 'viewObject'){
				// �༭������Enable����Form
				enableForm('manageTaxTemplateDetail_form');
				// �޸İ�ť��ʾ����
				$(this).val('<bean:message bundle="public" key="button.save" />');
				// ����SubActionΪ�༭
				$('.manageTaxTemplateDetail_form input#subAction').val('modifyObject');
				// ����Form Action
				$('.manageTaxTemplateDetail_form').attr('action', 'taxTemplateDetailAction.do?proc=modify_object');
			}
			// �༭
			else{
				// ͨ��JS��֤���ύFORM
				if( validate_manage_secondary_form() == 0){
					enableForm('manageTaxTemplateDetail_form');
					submit('manageTaxTemplateDetail_form');
				}
			}
		});
		
		// �б�ť����¼� - TaxTemplate Header 
		$('#btnListTaxTemplateHeader').click( function () {
			if (agreest())
			link('taxTemplateHeaderAction.do?proc=list_object');
		});
		
		// ȡ����ť����¼� - TaxTemplate Detail
		$('#btnCancelTaxTemplateDetail').click(function(){
			if(agreest())
			link('taxTemplateDetailAction.do?proc=list_object&id=<bean:write name="taxTemplateHeaderForm" property="encodedId"/>');
		});	
		 
		if( getSubAction() != 'createObject' ) {
			disableForm('manageTaxTemplateHeader_form');
			$('#TaxTemplateDetail-Information').show();
			$('.manageTaxTemplateHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('<bean:message bundle="management" key="management.tax.template" /> <bean:message bundle="public" key="oper.view" />');
		} else if ( getSubAction() == 'createObject' ) {
		    $('#btnEditTaxTemplateHeader').val('<bean:message bundle="public" key="button.save" />');
		}
	})(jQuery);
	
	// ��������ӣ�ajax����ȥ�޸�ҳ��
	function taxTemplateDetailModify( detailId ){
		// ��ʾCancel��ť
		$('#btnCancelTaxTemplateDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// �޸İ�ť��ʾ����
		$('#btnEditTaxTemplateDetail').val('<bean:message bundle="public" key="button.edit" />');
		var callback = "$(\".manageTaxTemplateDetail_valueType\").trigger(\"change\");";
		// Ajax����TaxTemplateDetail�޸�ҳ��
		loadHtmlWithRecall('#detailFormWrapper', 'taxTemplateDetailAction.do?proc=to_objectModify_ajax&id=' + detailId, true, callback);
	};
	
	// cityId change�¼�
	function cityIdChange(){
		cleanError('cityId');
		$.ajax({
			url : "taxTemplateHeaderAction.do?proc=cityId_change_ajax&cityId=" + $('#cityId').val(), 
			dataType : "json",
			success : function(data){
				if(data.success == 'false'){
					var error = $('#cityId option:selected').html() + "�Ѿ��������˰ģ�壻";
					addError('cityId', error);
					$('#cityId').val('0');
				}
			}
		});
	};
	
	// ��ǰ�Ƿ���ҪDisable
	function getDisable(){
		return getSubAction() == 'viewObject' ? true : false;
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageTaxTemplateHeader_form input#subAction').val();
	};
</script>