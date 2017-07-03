<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<!-- SBAdjustmentHeader - information -->
<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle"><bean:message bundle="sb" key="sb.adjustment.confirm.header.title" /></label>
	        <label class="recordId"> &nbsp; (ID: <bean:write name="sbAdjustmentHeaderForm" property="adjustmentHeaderId" />)</label>
	    </div>
	    <div class="inner">
	        <div id="messageWrapper">
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
			</div>
			<div class="top">
				<kan:auth right="approve" action="HRO_SB_ADJUSTMENT_HEADER_CONFIRM">
	            	<input type="button" class="function" name="btnApprove" id="btnApprove" value="<bean:message bundle="public" key="button.approve" />" />
	            </kan:auth>
				<kan:auth right="back" action="HRO_SB_ADJUSTMENT_HEADER_CONFIRM">
	            	<input type="button" class="delete" name="btnRollback" id="btnRollback" value="<bean:message bundle="public" key="button.return" />" />
	            </kan:auth>
	            <kan:auth right="back" action="HRO_SB_ADJUSTMENT_HEADER_CONFIRM">
	            	<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
	            </kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
			<jsp:include page="/contents/sb/adjustment/header/form/manageSBAdjustmentHeaderForm.jsp" flush="true"></jsp:include>		
	    </div>
	</div>
</div>

<!-- SBAdjustmentDetail - information -->
<div class="box" id="SBAdjustmentDetail-information">		
	<!-- Inner -->	
	<div class="head">
		<label><bean:message bundle="sb" key="sb.adjustment.confirm.detail.title" /></label>
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
			<input type="button" class="reset" name="btnCancelSBAdjustmentDetail" id="btnCancelSBAdjustmentDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
		</div>	
		<div id="detailFormWrapper"  style="display: none;">
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
			<jsp:include page="/contents/sb/adjustment/detail/form/manageSBAdjustmentDetailForm.jsp" flush="true"></jsp:include>
		</div>	
		<div id="helpText" class="helpText"></div>	
		<div id="scrollWrapper">
			<div id="scrollContainer"></div>
		</div>																	
		<logic:notEmpty name="sbAdjustmentDetailHolder">	
			<html:form action="sbAdjustmentDetailAction.do?proc=list_object" styleClass="listSBAdjustmentDetail_form">
				<input type="hidden" name="id" value="<bean:write name="sbAdjustmentHeaderForm" property="encodedId"/>"/>
				<input type="hidden" name="employeeSBId" value="<bean:write name="sbAdjustmentHeaderForm" property="encodedEmployeeSBId"/>"/>
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbAdjustmentDetailHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbAdjustmentDetailHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="sbAdjustmentDetailHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbAdjustmentDetailHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />					
			</html:form>	
		</logic:notEmpty>	
		<div id="tableWrapper">
			<!-- Include table jsp ����table��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/sb/adjustment/detail/table/listSBAdjustmentDetailTable.jsp" flush="true"></jsp:include>
		</div>	
		<!-- tableWrapper -->	
		<div class="bottom">
			<p>
		</div>	
	</div>		
	<!-- inner -->		
</div>		
<!-- search-results -->		
	
<script type="text/javascript">
	(function($) {
		// ��ʼ���˵���ʽ
		$('#menu_sb_Modules').addClass('current');
		$('#menu_sb_Process').addClass('selected');
		$('#menu_sb_AdjustmentConfirm').addClass('selected');
		kanList_init();
		kanCheckbox_init();
		
		disableForm('manageSBAdjustmentHeader_form');
		
		// ������ģʽ��ӿ��ٲ�ͼ��
		if( getSubAction() != 'createObject'){
			$('#employeeIdLI label').append('&nbsp;<a onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name="sbAdjustmentHeaderForm" property="encodedEmployeeId" />\');" ><img src="images/find.png" title="�鿴<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>��¼" /></a>');
			$('#contractIdLI label').append('&nbsp;<a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="sbAdjustmentHeaderForm" property="encodedContractId" />\');" ><img src="images/find.png" title="�鿴<logic:equal name="role" value="1">������Ϣ</logic:equal><logic:equal name="role" value="2">�Ͷ���ͬ</logic:equal>��¼" /></a>');
		}
		
		// �󶨷���Э��Change�¼���ѡ�����Э�飬����籣������������ 
		$('#contractId').bind('keyup', function(){
			if($(this).val().length >= 9){
				$.ajax({
					 url : 'employeeContractAction.do?proc=get_object_ajax&contractId=' + $(this).val() + '&date=' + new Date(),
					 dataType : "json",
					 success : function(data){
						 cleanError('manageSBAdjustmentHeader_contractId');
						 
						 if(data.success=='true'){
							 $('.manageSBAdjustmentHeader_entityId').val(data.entityId);
							 $('.manageSBAdjustmentHeader_businessTypeId').val(data.businessTypeId);
							 $('.manageSBAdjustmentHeader_employeeId').val(data.employeeId);
							 $('.manageSBAdjustmentHeader_employeeNameZH').val(data.employeeNameZH);
							 $('.manageSBAdjustmentHeader_employeeNameEN').val(data.employeeNameEN);
							 $('.manageSBAdjustmentHeader_clientId').val(data.clientId);
							 $('.manageSBAdjustmentHeader_orderId').val(data.orderId);
							 $('.manageSBAdjustmentHeader_clientNameZH').val(data.clientNameZH);
							 $('.manageSBAdjustmentHeader_clientNameEN').val(data.clientNameEN);
						 }else{
							 $('.manageSBAdjustmentHeader_entityId').val('0');
							 $('.manageSBAdjustmentHeader_businessTypeId').val('0');
							 $('.manageSBAdjustmentHeader_employeeId').val('');
							 $('.manageSBAdjustmentHeader_employeeNameZH').val('');
							 $('.manageSBAdjustmentHeader_employeeNameEN').val('');
							 $('.manageSBAdjustmentHeader_clientId').val('');
							 $('.manageSBAdjustmentHeader_orderId').val('');
							 $('.manageSBAdjustmentHeader_clientNameZH').val('');
							 $('.manageSBAdjustmentHeader_clientNameEN').val('');
							 addError('manageSBAdjustmentHeader_contractId', '<logic:equal name="role" value="1">������Ϣ</logic:equal><logic:equal name="role" value="2">�Ͷ���ͬ</logic:equal>ID��Ч��');
						 }
						 
						 var callback = "if( getSubAction() == 'viewObject' ){$('#employeeSBId').val(\"<bean:write name='sbAdjustmentHeaderForm' property='employeeSBId' />\");}";
						 // �����籣�����𷽰�
						 loadHtmlWithRecall('#employeeSBId','employeeContractSBAction.do?proc=list_object_options_ajax&contractId=' + $('#contractId').val(), false , callback );
					 }
				});
			}
		});
		
		// SBAdjustment Header �б�Click
		$('#btnList').click(function(){
			if (agreest())
			link('sbAdjustmentHeaderAction.do?proc=list_object_confirm');
		});
		
		// SBAdjustment Detail ȡ��click
		$('#btnCancelSBAdjustmentDetail').click(function(){
			if (agreest())
			link('sbAdjustmentDetailAction.do?proc=list_object_confirm&id=<bean:write name="sbAdjustmentHeaderForm" property="encodedId" />&employeeSBId=<bean:write name="sbAdjustmentHeaderForm" property="encodedEmployeeSBId" />');
		});
		
		// ��׼�¼�
		$('#btnApprove').click(function(){
		    $('.manageSBAdjustmentHeader_form').attr('action', 'sbAdjustmentHeaderAction.do?proc=approve_object&selectedIds=' + $('.manageSBAdjustmentHeader_form #id').val()); 
		    submitForm('manageSBAdjustmentHeader_form');
		});
		
		// �˻��¼�
		$('#btnRollback').click(function(){
		    $('.manageSBAdjustmentHeader_form').attr('action', 'sbAdjustmentHeaderAction.do?proc=rollback_object&selectedIds=' + $('.manageSBAdjustmentHeader_form #id').val()); 
		    submitForm('manageSBAdjustmentHeader_form');
		});
		
		if( getSubAction() == 'viewObject' ){
			$('#contractId').keyup();
		}
	})(jQuery);
	
	// ��������ӣ�ajax����ȥ�޸�ҳ��
	function sbAdjustmentDetailModify( detailId ){
		// ��ʾCancel��ť
		$('#btnCancelSBAdjustmentDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// Ajax����SBAdjustment Detail�޸�ҳ��
		loadHtml('#detailFormWrapper', 'sbAdjustmentDetailAction.do?proc=to_objectModify_ajax&id=' + detailId, true );
	};
	
	// ��ȡsubAction
	function getSubAction(){
		return $('.manageSBAdjustmentHeader_form input#subAction').val();
	};
</script>

