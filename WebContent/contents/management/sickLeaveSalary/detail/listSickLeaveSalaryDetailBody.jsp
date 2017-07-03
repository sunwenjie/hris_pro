<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.actions.management.SickLeaveSalaryDetailAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
 	<div class="box" id="shiftHeader - information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.sick.leave" /></label>
		</div>
		<div class="inner">
			<div class="top">
				<logic:empty name="sickLeaveSalaryHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditsickLeaveSalaryHeader" id="btnEditsickLeaveSalaryHeader" value="<bean:message bundle="public" key="button.edit" />" />
				</logic:empty>
				<logic:notEmpty name="sickLeaveSalaryHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=SickLeaveSalaryDetailAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEditsickLeaveSalaryHeader" id="btnEditsickLeaveSalaryHeader" value="<bean:message bundle="public" key="button.edit" />" />
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=SickLeaveSalaryDetailAction.accessAction%>">
					<input type="button" class="reset" name="btnCancelsickLeaveSalaryHeader" id="btnCancelsickLeaveSalaryHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
			   <div id="formWrapper">
				<jsp:include page="/contents/management/sickLeaveSalary/header/form/manageSickLeaveSalaryHeaderForm.jsp" flush="true"/>
			</div>   
		</div>	
	</div>
	
	<!-- sickLeaveSalaryDetail - information -->
	<div class="box" id="sickLeaveSalaryDetail-Information" style="display: none;">		
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="management" key="management.sick.leave.detail.search.title" /></label>
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
				<kan:auth right="modify" action="<%=SickLeaveSalaryDetailAction.accessAction%>">
					<input type="button" class="editbutton" name="btnEditsickLeaveSalaryDetail" id="btnEditsickLeaveSalaryDetail" value="<bean:message bundle="public" key="button.add" />" /> 
					<input type="button" class="reset" name="btnCancelsickLeaveSalaryDetail" id="btnCancelsickLeaveSalaryDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listsickLeaveSalaryDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>	
			 <div id="detailFormWrapper" style="display:none" >
				<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
				<jsp:include page="/contents/management/sickLeaveSalary/detail/form/listSickLeaveSalaryDetailForm.jsp" flush="true"/>
			</div>	
			<!-- if exist bean sickLeaveSalaryDetailHolder -->	
			<logic:notEmpty name="sickLeaveSalaryDetailHolder">														
				<html:form action="sickLeaveSalaryDetailAction.do?proc=list_object" styleClass="listsickLeaveSalaryDetail_form">
					<input type="hidden" name="id" value="<bean:write name="sickLeaveSalaryHeaderForm" property="encodedId"/>"/>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sickLeaveSalaryDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sickLeaveSalaryDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="sickLeaveSalaryDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sickLeaveSalaryDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</html:form>
				 <div id="tableWrapper">
					<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
					<jsp:include page="/contents/management/sickLeaveSalary/detail/table/listSickLeaveSalaryDetailTable.jsp" flush="true"/>
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
		$('#menu_salary_Modules').addClass('current');			
		$('#menu_salary_Configuration').addClass('selected');
		$('#menu_salary_Sickleave').addClass('selected');
		
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();
		
		// �����ǰ��ϵͳ�̱��������ҵ�ǰ�û���Super�����ذ�ť�� 
		if('<bean:write name="sickLeaveSalaryHeaderForm" property="accountId" />' == '1' && '<bean:write name="accountId" />' != '1'){
			$('#btnEditsickLeaveSalaryHeader').hide();
			$('#btnEditsickLeaveSalaryDetail').hide();
			$('#btnDelete').hide();
		}
		
		// �༭��ť����¼� - sickLeaveSalary Header
		$('#btnEditsickLeaveSalaryHeader').click(function(){
			if( getSubAction() == 'viewObject'){  
				// Enable����Form
        		enableForm('manageSickLeaveSalaryHeader_form');
        		// ���õ�ǰForm��SubActionΪ�޸�״̬
        		$('.manageSickLeaveSalaryHeader_form input#subAction').val("modifyObject");
        		// ����Form Action
        		$('.manageSickLeaveSalaryHeader_form').attr('action', 'sickLeaveSalaryHeaderAction.do?proc=modify_object');
        		// �޸İ�ť��ʾ����
        		$('#btnEditsickLeaveSalaryHeader').val('<bean:message bundle="public" key="button.save" />');
        		
        		$('.manageSickLeaveSalaryHeader_form').find(":input").removeAttr('disabled');
        		// �޸�Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.sick.leave" /> <bean:message bundle="public" key="oper.edit" />');
			}else{
				if( validate_manage_primary_form() == 0){
					submit('manageSickLeaveSalaryHeader_form');
				}
			}
		}); 
		 // �༭��ť����¼� - sickLeaveSalary Detail
		$('#btnEditsickLeaveSalaryDetail').click(function(){	
			// �ж�����ӡ��鿴�����޸� 
			if($('.managesickLeaveSalaryDetail_form input#subAction').val() == ''){
				// ��ʾCancel��ť
				$('#btnCancelsickLeaveSalaryDetail').show();
				// ��ʾList Detail Form
				$('#detailFormWrapper').show();	
				
				// ����SubActionΪ�½�
				$('.managesickLeaveSalaryDetail_form input#subAction').val('createObject');
				$('.managesickLeaveSalaryDetail_form input.managesickLeaveSalaryDetail_percentage').val('0');
				$('.managesickLeaveSalaryDetail_form input.managesickLeaveSalaryDetail_fix').val('0');
				$('.managesickLeaveSalaryDetail_form input.managesickLeaveSalaryDetail_deduct').val('0');
				// �޸İ�ť��ʾ����
				$('#btnEditsickLeaveSalaryDetail').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.managesickLeaveSalaryDetail_form input#subAction').val() == 'viewObject'){	
				// �༭������Enable����Form
				enableForm('managesickLeaveSalaryDetail_form');
				// ����SubActionΪ�༭
				$('.managesickLeaveSalaryDetail_form input#subAction').val('modifyObject');
				// �޸İ�ť��ʾ����
				$('#btnEditsickLeaveSalaryDetail').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.managesickLeaveSalaryDetail_form').attr('action', 'sickLeaveSalaryDetailAction.do?proc=modify_object');
        		// ��Ŀ����������Disable 
			}else{
				if( validate_manage_secondary_form() == 0 ){
					submit('managesickLeaveSalaryDetail_form');
				}
			}
		}); 
		
		// Header �б�
	 	$('#btnCancelsickLeaveSalaryHeader').click( function () {
	 		if (agreest())
			link('sickLeaveSalaryHeaderAction.do?proc=list_object');
		});	 
		
		// Detail ȡ��
		 $('#btnCancelsickLeaveSalaryDetail').click( function () {
			 if(agreest())
			link('sickLeaveSalaryDetailAction.do?proc=list_object&id='+$("#headerId").val());
		}); 
		
		 if( getSubAction() != 'createObject' ) {
			disableForm('managesickLeaveSalaryHeader_form');
			$('#sickLeaveSalaryDetail-Information').show();
			$('.manageSickLeaveSalaryHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('<bean:message bundle="management" key="management.sick.leave" /> <bean:message bundle="public" key="oper.view" />');
			$('.manageSickLeaveSalaryHeader_form li').find(":input").attr('disabled',true);	
		} else if ( getSubAction() == 'createObject' ) {
		    $('#btnEditsickLeaveSalaryHeader').val('<bean:message bundle="public" key="button.save" />');
		} 
		
	})(jQuery);
	
	// ��������ӣ�ajax����ȥ�޸�ҳ��
	function sickLeaveSalaryDetailModify( detailId ){
		// ��ʾCancel��ť
		$('#btnCancelsickLeaveSalaryDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// Ajax����sickLeaveSalary Detail�޸�ҳ��
		loadHtml('#detailFormWrapper', "sickLeaveSalaryDetailAction.do?proc=to_objectModify_ajax&detailId=" + detailId, true );
		// �޸İ�ť��ʾ����
		$('#btnEditsickLeaveSalaryDetail').val('<bean:message bundle="public" key="button.edit" />');		
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageSickLeaveSalaryHeader_form input#subAction').val();
	}; 
</script>