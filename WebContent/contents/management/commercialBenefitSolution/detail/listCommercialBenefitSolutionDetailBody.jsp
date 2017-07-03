<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.actions.management.CommercialBenefitSolutionDetailAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box" id="commercialBenefitSolutionHeader - information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.cb.solution" /></label>
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
				<logic:empty name="commercialBenefitSolutionHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditCommercialBenefitSolutionHeader" id="btnEditCommercialBenefitSolutionHeader" value="<bean:message bundle="public" key="button.edit" />" />
				</logic:empty>
				<logic:notEmpty name="commercialBenefitSolutionHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=CommercialBenefitSolutionDetailAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEditCommercialBenefitSolutionHeader" id="btnEditCommercialBenefitSolutionHeader" value="<bean:message bundle="public" key="button.edit" />" />
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=CommercialBenefitSolutionDetailAction.accessAction%>">
					<input type="button" class="reset" name="btnCancelCommercialBenefitSolutionHeader" id="btnCancelCommercialBenefitSolutionHeader" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/management/commercialBenefitSolution/header/form/manageCommercialBenefitSolutionHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	
	<!-- CommercialBenefitSolutionDetail - information -->
	<div class="box" id="CommercialBenefitSolutionDetail-Information" style="display: none;">		
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="management" key="management.cb.solution.detail.search.title" /></label>
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
				<kan:auth right="modify" action="<%=CommercialBenefitSolutionDetailAction.accessAction%>">					
					<input type="button" class="editbutton" name="btnEditCommercialBenefitSolutionDetail" id="btnEditCommercialBenefitSolutionDetail" value="<bean:message bundle="public" key="button.add" />" /> 
					<input type="button" class="reset" name="btnCancelCommercialBenefitSolutionDetail" id="btnCancelCommercialBenefitSolutionDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listCommercialBenefitSolutionDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>	
			<div id="detailFormWrapper" style="display:none" >
				<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
				<jsp:include page="/contents/management/commercialBenefitSolution/detail/form/manageCommercialBenenfitSolutionDetailForm.jsp" flush="true"/>
			</div>		
			<!-- if exist bean commercialBenefitSolutionDetailHolder -->	
			<logic:notEmpty name="commercialBenefitSolutionDetailHolder">													
				<html:form action="commercialBenefitSolutionDetailAction.do?proc=list_object" styleClass="listCommercialBenefitSolutionDetail_form">
					<input type="hidden" name="id" value="<bean:write name="commercialBenefitSolutionHeaderForm" property="encodedId"/>"/>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="commercialBenefitSolutionDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="commercialBenefitSolutionDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="commercialBenefitSolutionDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="commercialBenefitSolutionDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
					<jsp:include page="/contents/management/commercialBenefitSolution/detail/table/listCommercialBenefitSolutionDetailTable.jsp" flush="true"/>
				</div>
			</logic:notEmpty>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->		
</div>
<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ�������ǰ�û���Super
		if('<bean:write name="accountId"/>' == '1'){
			$('#menu_system_Modules').addClass('current');
			$('#menu_system_CB').addClass('selected');
		}else{
			$('#menu_cb_Modules').addClass('current');			
			$('#menu_cb_Configuration').addClass('selected');
			$('#menu_cb_Solution').addClass('selected');
		}
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();
		
		// �����ǰ��ϵͳ�̱��������ҵ�ǰ�û���Super�����ذ�ť�� 
		if('<bean:write name="commercialBenefitSolutionHeaderForm" property="accountId" />' == '1' && '<bean:write name="accountId" />' != '1'){
			$('#btnEditCommercialBenefitSolutionHeader').hide();
			$('#btnEditCommercialBenefitSolutionDetail').hide();
			$('#btnDelete').hide();
		}
		
		// �༭��ť����¼� - CommercialBenefitSolution Header
		$('#btnEditCommercialBenefitSolutionHeader').click(function(){
			if( getSubAction() == 'viewObject'){  
				// Enable����Form
        		enableForm('manageCommercialBenefitSolutionHeader_form');
        		// ���õ�ǰForm��SubActionΪ�޸�״̬
        		$('.manageCommercialBenefitSolutionHeader_form input#subAction').val('modifyObject'); 
        		// ����Form Action
        		$('.manageCommercialBenefitSolutionHeader_form').attr('action', 'commercialBenefitSolutionHeaderAction.do?proc=modify_object');
        		// �޸İ�ť��ʾ����
        		$('#btnEditCommercialBenefitSolutionHeader').val('<bean:message bundle="public" key="button.save" />');
        		// �޸�Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.cb.solution" /> <bean:message bundle="public" key="oper.edit" />');
        		// �ϴ�������Ч
				$('#uploadAttachment').show();
				// Enableɾ��Сͼ��
	   			$('img[id^=warning_img]').each(function(i){
	   				$(this).show();
	   			});
	   			$('img[id^=disable_img]').each(function(i){
	   				$(this).hide();
	   			});
			}else{
				if( validate_manage_primary_form() == 0){
					submit('manageCommercialBenefitSolutionHeader_form');
				}
			}
		});
		
		// �༭��ť����¼� - CommercialBenefitSolution Detail
		$('#btnEditCommercialBenefitSolutionDetail').click(function(){	
			// �ж�����ӡ��鿴�����޸� 
			if($('.manageCommercialBenefitSolutionDetail_form input#subAction').val() == ''){
				// ��ʾCancel��ť
				$('#btnCancelCommercialBenefitSolutionDetail').show();
				// ��ʾList Detail Form
				$('#detailFormWrapper').show();	
				// ����SubActionΪ�½�
				$('.manageCommercialBenefitSolutionDetail_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$('#btnEditCommercialBenefitSolutionDetail').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.manageCommercialBenefitSolutionDetail_form input#subAction').val() == 'viewObject'){	
				// �༭������Enable����Form
				enableForm('manageCommercialBenefitSolutionDetail_form');
				// ����SubActionΪ�༭
				$('.manageCommercialBenefitSolutionDetail_form input#subAction').val('modifyObject');
				// �޸İ�ť��ʾ����
				$('#btnEditCommercialBenefitSolutionDetail').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.manageCommercialBenefitSolutionDetail_form').attr('action', 'commercialBenefitSolutionDetailAction.do?proc=modify_object');
        		// ��Ŀ����������Disable 
        		//$('.manageCommercialBenefitSolutionDetail_itemId').attr('disabled','disabled');	
			}else{
				if( validate_manage_secondary_form() == 0 ){
					submit('manageCommercialBenefitSolutionDetail_form');
				}
			}
		});
		
		// Header �б�
		$('#btnCancelCommercialBenefitSolutionHeader').click( function () {
			if (agreest()) {
				link('commercialBenefitSolutionHeaderAction.do?proc=list_object');
			}
		});	
		
		// Detail ȡ��
		$('#btnCancelCommercialBenefitSolutionDetail').click( function () {
			if(agreest())
			link('commercialBenefitSolutionDetailAction.do?proc=list_object&id=<bean:write name="commercialBenefitSolutionHeaderForm" property="encodedId"/>');
		});
		
		if( getSubAction() != 'createObject' ) {
			disableForm('manageCommercialBenefitSolutionHeader_form');
			$('#CommercialBenefitSolutionDetail-Information').show();
			$('.manageCommercialBenefitSolutionHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('<bean:message bundle="management" key="management.cb.solution" /> <bean:message bundle="public" key="oper.view" />');
		} else if ( getSubAction() == 'createObject' ) {
		    $('#btnEditCommercialBenefitSolutionHeader').val('<bean:message bundle="public" key="button.save" />');
			$('#uploadAttachment').show();
		}
		
		var uploadObject = createUploadObject('uploadAttachment', 'common', '<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_CB %>/<bean:write name="accountId" />/<bean:write name="username" />/');
	})(jQuery);
	
	// ��������ӣ�ajax����ȥ�޸�ҳ��
	function commercialBenefitSolutionDetailModify( detailId ){
		// ��ʾCancel��ť
		$('#btnCancelCommercialBenefitSolutionDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// Ajax����CommercialBenefitSolution Detail�޸�ҳ��
		loadHtml('#detailFormWrapper', 'commercialBenefitSolutionDetailAction.do?proc=to_objectModify_ajax&detailId=' + detailId, true );
		// �޸İ�ť��ʾ����
		$('#btnEditCommercialBenefitSolutionDetail').val('<bean:message bundle="public" key="button.edit" />');		
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageCommercialBenefitSolutionHeader_form input#subAction').val();
	};
</script>