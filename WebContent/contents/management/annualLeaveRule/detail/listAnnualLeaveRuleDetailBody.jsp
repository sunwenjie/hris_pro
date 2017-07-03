<%@page import="com.kan.base.web.actions.management.AnnualLeaveRuleHeaderAction"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
 	<div class="box" id="AnnualLeaveHeaderRule-Information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.annual.leave.rule" /></label>
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
				<logic:empty name="annualLeaveRuleHeaderForm" property="encodedId">
					<input type="button" class="save" name="btnEditAnnualLeaveRuleHeader" id="btnEditAnnualLeaveRuleHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				
				<logic:notEmpty name="annualLeaveRuleHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=AnnualLeaveRuleHeaderAction.ACCESS_ACTION%>">
						<input type="button" class="save" name="btnEditAnnualLeaveRuleHeader" id="btnEditAnnualLeaveRuleHeader" value="<bean:message bundle="public" key="button.edit" />" />
					</kan:auth>
				</logic:notEmpty>
				
				<kan:auth right="list" action="<%=AnnualLeaveRuleHeaderAction.ACCESS_ACTION%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/management/annualLeaveRule/header/form/manageAnnualLeaveRuleHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	
	<!-- AnnualLeaveDetailRule-Information -->
	<div class="box" id="AnnualLeaveDetailRule-Information" style="display: none;">		
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="management" key="management.annual.leave.rule.detail.search.title" /></label>
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
				<kan:auth right="modify" action="<%=AnnualLeaveRuleHeaderAction.ACCESS_ACTION%>">
					<input type="button" class="save" name="btnEditAnnualLeaveRuleDetail" id="btnEditAnnualLeaveRuleDetail" value="<bean:message bundle="public" key="button.add" />" /> 
					<input type="button" class="reset" name="btnCancelAnnualLeaveRuleDetail" id="btnCancelAnnualLeaveRuleDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listAnnualLeaveRuleDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>	
			<div id="detailFormWrapper" style="display:none" >
				<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
				<jsp:include page="/contents/management/annualLeaveRule/detail/form/listAnnualLeaveRuleDetailForm.jsp" flush="true"/>
			</div>	
			<!-- if exist bean annualLeaveRuleDetailHolder -->	
			<logic:notEmpty name="annualLeaveRuleDetailHolder">														
				<html:form action="annualLeaveRuleDetailAction.do?proc=list_object" styleClass="listAnnualLeaveRuleDetail_form">
					<input type="hidden" name="id" value="<bean:write name="annualLeaveRuleHeaderForm" property="encodedId"/>"/>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="annualLeaveRuleDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="annualLeaveRuleDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="annualLeaveRuleDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="annualLeaveRuleDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
					<jsp:include page="/contents/management/annualLeaveRule/detail/table/listAnnualLeaveRuleDetailTable.jsp" flush="true"/>
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
		$('#menu_salary_AnnualLeave_Rule').addClass('selected');
		
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();
		
		// �༭��ť����¼� - Annual Leave Header
		$('#btnEditAnnualLeaveRuleHeader').click(function(){
			if( getSubAction() == 'viewObject'){  
				// Enable����Form
        		enableForm('manageAnnualLeaveRuleHeader_form');
        		// ���õ�ǰForm��SubActionΪ�޸�״̬
        		$('.manageAnnualLeaveRuleHeader_form input#subAction').val("modifyObject");
        		// ����Form Action
        		$('.manageAnnualLeaveRuleHeader_form').attr('action', 'annualLeaveRuleHeaderAction.do?proc=modify_object');
        		// �޸İ�ť��ʾ����
        		$('#btnEditAnnualLeaveRuleHeader').val('<bean:message bundle="public" key="button.save" />');
        		// �޸�Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.annual.leave.rule" /> <bean:message bundle="public" key="oper.edit" />');
			}else{
				if( validate_manage_primary_form() == 0){
					submit('manageAnnualLeaveRuleHeader_form');
				}
			}
		}); 
		
		// �༭��ť����¼� - sickLeaveSalary Detail
		$('#btnEditAnnualLeaveRuleDetail').click(function(){	
			// �ж�����ӡ��鿴�����޸� 
			if($('.manageAnnualLeaveRuleDetail_form input#subAction').val() == ''){
				// ��ʾCancel��ť
				$('#btnCancelAnnualLeaveRuleDetail').show();
				// ��ʾList Detail Form
				$('#detailFormWrapper').show();	
				// ����SubActionΪ�½�
				$('.manageAnnualLeaveRuleDetail_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$('#btnEditAnnualLeaveRuleDetail').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.manageAnnualLeaveRuleDetail_form input#subAction').val() == 'viewObject'){	
				// �༭������Enable����Form
				enableForm('manageAnnualLeaveRuleDetail_form');
				// ����SubActionΪ�༭
				$('.manageAnnualLeaveRuleDetail_form input#subAction').val('modifyObject');
				// �޸İ�ť��ʾ����
				$('#btnEditAnnualLeaveRuleDetail').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.manageAnnualLeaveRuleDetail_form').attr('action', 'annualLeaveRuleDetailAction.do?proc=modify_object');
        		// ��Ŀ����������Disable 
			}else{
				if( validate_manage_secondary_form() == 0 ){
					submit('manageAnnualLeaveRuleDetail_form');
				}
			}
		}); 
		
		// Header �б�
	 	$('#btnList').click( function () {
	 		if (agreest())
				link('annualLeaveRuleHeaderAction.do?proc=list_object');
		});	 
		
		// Detail ȡ��
		$('#btnCancelAnnualLeaveRuleDetail').click( function () {
			 if(agreest())
			link('sickLeaveSalaryDetailAction.do?proc=list_object&id='+$("#headerId").val());
		}); 
		
		if( getSubAction() != 'createObject' ) {
			disableForm('manageAnnualLeaveRuleHeader_form');
			$('#AnnualLeaveDetailRule-Information').show();
			$('.manageAnnualLeaveRuleHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('<bean:message bundle="management" key="management.annual.leave.rule" /> <bean:message bundle="public" key="oper.view" />');
		} else if ( getSubAction() == 'createObject' ) {
			$('#pageTitle').html('<bean:message bundle="public" key="oper.new" /> <bean:message bundle="management" key="management.annual.leave.rule" />');
		    $('#btnEditAnnualLeaveRuleHeader').val('<bean:message bundle="public" key="button.save" />');
		} 
	})(jQuery);
	
	// ��������ӣ�ajax����ȥ�޸�ҳ��
	function annualLeaveRuleDetailModify( detailId ){
		// ��ʾCancel��ť
		$('#btnCancelsickLeaveSalaryDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// Ajax����sickLeaveSalary Detail�޸�ҳ��
		loadHtml('#detailFormWrapper', "annualLeaveRuleDetailAction.do?proc=to_objectModify_ajax&detailId=" + detailId, true );
		// �޸İ�ť��ʾ����
		$('#btnEditAnnualLeaveRuleDetail').val('<bean:message bundle="public" key="button.edit" />');		
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageAnnualLeaveRuleHeader_form input#subAction').val();
	}; 
</script>