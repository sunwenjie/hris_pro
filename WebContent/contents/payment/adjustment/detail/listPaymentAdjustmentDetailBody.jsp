<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ page import=" com.kan.hro.web.actions.biz.payment.PaymentAdjustmentHeaderAction"%>
<%@ page import=" com.kan.hro.web.actions.biz.payment.PaymentAdjustmentDetailAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>


<!-- PaymentAdjustmentHeader - information -->
<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="payment" key="payment.salary.adjustment" /></label>
	        <logic:notEmpty name="paymentAdjustmentHeaderForm" property="adjustmentHeaderId" >
	        	<label class="recordId"> &nbsp; (ID: <bean:write name="paymentAdjustmentHeaderForm" property="adjustmentHeaderId" />)</label>
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
				<logic:empty name="paymentAdjustmentHeaderForm" property="encodedId">
					<input type="button" class="save" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" />
				</logic:empty>
				<logic:notEmpty name="paymentAdjustmentHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=PaymentAdjustmentHeaderAction.accessAction%>">
						<input type="button" class="save" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" />
					</kan:auth>
				</logic:notEmpty>

				<kan:auth right="submit" action="<%=PaymentAdjustmentHeaderAction.accessAction%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" style="display: none;" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>

				<kan:auth right="grant" action="<%=PaymentAdjustmentHeaderAction.accessAction%>">
					<input type="button" class="function" name="btnIssue" id="btnIssue" style="display: none;" value="<bean:message bundle="public" key="button.issue" />" />
				</kan:auth>
				<kan:auth right="list" action="<%=PaymentAdjustmentHeaderAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
			<jsp:include page="/contents/payment/adjustment/header/form/managePaymentAdjustmentHeaderForm.jsp" flush="true"></jsp:include>		
	    </div>
	</div>
</div>

<!-- PaymentAdjustmentDetail - information -->
<div class="box" id="paymentAdjustmentDetail-information" style="display: none;">		
	<!-- Inner -->	
	<div class="head">
		<label><bean:message bundle="payment" key="payment.salary.adjustment.detail.search.title" /></label>
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
			<kan:auth right="modify" action="<%=PaymentAdjustmentHeaderAction.accessAction%>">
				<input type="button" class="editbutton" name="btnEditPaymentAdjustmentDetail" id="btnEditPaymentAdjustmentDetail" value="<bean:message bundle="public" key="button.add" />" /> 
				<input type="button" class="reset" name="btnCancelPaymentAdjustmentDetail" id="btnCancelPaymentAdjustmentDetail" value="<bean:message bundle="public" key="button.reset" />" style="display:none" />
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listPaymentAdjustmentDetail_form', 'deleteObjects', null, null, null, null);" />
			</kan:auth>
		</div>	
		<div id="detailFormWrapper" style="display: none;">
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
			<jsp:include page="/contents/payment/adjustment/detail/form/managePaymentAdjustmentDetailForm.jsp" flush="true"></jsp:include>
		</div>																		
		<html:form action="paymentAdjustmentDetailAction.do?proc=list_object" styleClass="listPaymentAdjustmentDetail_form">
			<input type="hidden" name="adjustmentHeaderId" id="adjustmentHeaderId" value="<bean:write name="paymentAdjustmentHeaderForm" property="encodedId"/>"/>
			<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="paymentAdjustmentHeaderForm" property="selectedIds" />" />
			<input type="hidden" name="subAction" id="subAction" value="" />					
		</html:form>	
		<div id="tableWrapper">
			<!-- Include table jsp ����table��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/payment/adjustment/detail/table/listPaymentAdjustmentDetailTable.jsp" flush="true"></jsp:include>
		</div>	
		<!-- tableWrapper -->	
		<div class="bottom">
			<p>
		</div>	
	</div>		
	<!-- inner -->		
</div>		

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/searchContract.jsp"></jsp:include>
</div>		
	
<script type="text/javascript">
	(function($) {
		// ��ʼ���˵���ʽ
		$('#menu_salary_Modules').addClass('current');	
		$('#menu_salary_Process').addClass('selected');
		$('#menu_salary_Adjustment').addClass('selected');
		
		if( ( getStatus() == '1' && getSubAction() != 'createObject' ) || getStatus() == '4' ){
			$('#btnSubmit').show();
		}else if(getStatus() == '3'){
			$('#btnEdit').hide();
			$('#btnIssue').show();
			$('#btnEditPaymentAdjustmentDetail').hide();
			$('#btnDelete').hide();
		}else if( getStatus() == '2' || getStatus() == '5'){
			$('#btnEdit').hide();
			$('#btnEditPaymentAdjustmentDetail').hide();
			$('#btnDelete').hide();
		}
	    
	    // �ύ��ť�¼�
		$('#btnSubmit').click( function () { 
			if(validate_manage_primary_form() == 0){
				// ���ĵ�ǰForm��SubAction
				if($('.managePaymentAdjustmentHeader_form input#subAction').val() != 'createObject'){
					$('.managePaymentAdjustmentHeader_form').attr('action', 'paymentAdjustmentHeaderAction.do?proc=submit_object');
				}
				$('.managePaymentAdjustmentHeader_form input#subAction').val('submitObject');
		    	enableForm('managePaymentAdjustmentHeader_form');
		    	submit('managePaymentAdjustmentHeader_form');
			}
		});
	    
	    // �ύ��ť�¼�
		$('#btnIssue').click( function () { 
			if(validate_manage_primary_form() == 0){
				// ���ĵ�ǰForm��SubAction
				if($('.managePaymentAdjustmentHeader_form input#subAction').val() != 'createObject'){
					$('.managePaymentAdjustmentHeader_form').attr('action', 'paymentAdjustmentHeaderAction.do?proc=issue_object');
				}
				$('.managePaymentAdjustmentHeader_form input#subAction').val('issueObject');
		    	enableForm('managePaymentAdjustmentHeader_form');
		    	submit('managePaymentAdjustmentHeader_form');
			}
		});
		
		// �󶨷���Э��keyup�¼�
		$('#contractId').bind('keyup', function(){
			if($("#contractId").val().length >= 9){
				var contractId = $(this).val();
				if( contractId != '' && contractId != '0' )
				{
					$.ajax({
						 url:"employeeContractAction.do?proc=get_object_json&contractId=" + contractId + '&date' + new Date(),
						 dataType:"json",
						 success:function(data) 
						 {
						 	cleanError('managePaymentAdjustmentHeader_contractId');
							$('#contractIdLI label em').next('a').remove();
							if(data.success == 'true'){
								$('.managePaymentAdjustmentHeader_clientId').val(data.clientId);
								$('.managePaymentAdjustmentHeader_clientNameZH').val(data.clientNameZH);
								$('.managePaymentAdjustmentHeader_clientNameEN').val(data.clientNameEN);
								$('.managePaymentAdjustmentHeader_orderId').val(data.orderId);
								$('.managePaymentAdjustmentHeader_employeeId').val(data.employeeId);
								$('.managePaymentAdjustmentHeader_employeeNameZH').val(data.employeeNameZH);
								$('.managePaymentAdjustmentHeader_employeeNameEN').val(data.employeeNameEN);
								$('.managePaymentAdjustmentHeader_contractIdLI label').append('<a onclick="link(\'clientContractAction.do?proc=to_objectModify&id=' + data.encodedId + '\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
							}else if(data.success == 'false'){
								$('.managePaymentAdjustmentHeader_clientId').val('');
								$('.managePaymentAdjustmentHeader_orderId').val('');
								$('.managePaymentAdjustmentHeader_clientNameZH').val('');
								$('.managePaymentAdjustmentHeader_clientNameEN').val('');
								$('.managePaymentAdjustmentHeader_employeeId').val('');
								$('.managePaymentAdjustmentHeader_employeeNameZH').val('');
								$('.managePaymentAdjustmentHeader_employeeNameEN').val('');
								addError('managePaymentAdjustmentHeader_contractId', '<bean:message bundle="public" key="error.input.id.invalid" />');
							}
						 }
					});
				}
			}
		});
		
		// ���ʵ��������桢�༭����¼�
		$('#btnEdit').click(function() {
		    if( getSubAction() == 'viewObject') {
		    	// Enable Form
		        enableForm('managePaymentAdjustmentHeader_form');
		    	// ����SubAction
		        $('.managePaymentAdjustmentHeader_form input#subAction').val('modifyObject');
		    	// ����Action
		        $('.managePaymentAdjustmentHeader_form').attr('action', 'paymentAdjustmentHeaderAction.do?proc=modify_object');
		    	// ���İ�ť��ʾ����
		        $('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		    	// ����Title
		        $('#pageTitle').html('<bean:message bundle="payment" key="payment.salary.adjustment" /> <bean:message bundle="public" key="oper.edit" />');
		    	// Disable���水ť
		    	disableLinkById("#popupContractSearchLink");
		    	$('.managePaymentAdjustmentHeader_monthly').attr('disabled', 'disabled');
		        $('.managePaymentAdjustmentHeader_contractId').attr('disabled', 'disabled');
				$('.managePaymentAdjustmentHeader_clientId').attr('disabled', 'disabled');
				$('.managePaymentAdjustmentHeader_clientNameZH').attr('disabled', 'disabled');
				$('.managePaymentAdjustmentHeader_clientNameEN').attr('disabled', 'disabled');
				$('.managePaymentAdjustmentHeader_employeeId').attr('disabled', 'disabled');
				$('.managePaymentAdjustmentHeader_orderId').attr('disabled', 'disabled');
				$('.managePaymentAdjustmentHeader_employeeNameZH').attr('disabled', 'disabled');
				$('.managePaymentAdjustmentHeader_employeeNameEN').attr('disabled', 'disabled');
				$('.managePaymentAdjustmentHeader_billAmountPersonal').attr('disabled', 'disabled');
				$('.managePaymentAdjustmentHeader_costAmountPersonal').attr('disabled', 'disabled');
		        $('.managePaymentAdjustmentHeader_status').attr('disabled', 'disabled');
		    } else {
		        var flag = validate_manage_primary_form();
		        
		        if (flag == 0) {
		        	enableForm('managePaymentAdjustmentHeader_form');
		            submit('managePaymentAdjustmentHeader_form');
		        }
		    }
		});
		
		// ���ʵ��������б����¼�
		$('#btnList').click(function(){
			if (agreest())
			link('paymentAdjustmentHeaderAction.do?proc=list_object');
		});
		
		// ���ʵ����ӱ��桢�༭����¼�
		$('#btnEditPaymentAdjustmentDetail').click(function(){
			if($('.managePaymentAdjustmentHeader_form #adjustmentHeaderId').val() != ''){
				var detailSubAction = $('.managePaymentAdjustmentDetail_form input#subAction').val();
				
				// ��� 
				if( detailSubAction == '' ){
					// ����Ĭ��ֵ
					$('.managePaymentAdjustmentDetail_form .managePaymentAdjustmentDetail_status').val('1');
					// ��ʾCancel��ť
					$('#btnCancelPaymentAdjustmentDetail').show();
					// ��ʾList Detail Form
					$('#detailFormWrapper').show();	
					// װ�شӱ��½�����
					loadHtml('#detailFormWrapper', 'paymentAdjustmentDetailAction.do?proc=to_objectNew_ajax&adjustmentHeaderId=<bean:write name="paymentAdjustmentHeaderForm" property="encodedId"/>', false );
					// ����SubActionΪ�½�
					$('.managePaymentAdjustmentDetail_form input#subAction').val('createObject');
					// �޸İ�ť��ʾ����
					$('#btnEditPaymentAdjustmentDetail').val('<bean:message bundle="public" key="button.save" />');
				}
				// �鿴
				else if( detailSubAction == 'viewObject'){
					// Enable Form
					enableForm('managePaymentAdjustmentDetail_form');
					// ����SubActionΪ�༭
					$('.managePaymentAdjustmentDetail_form input#subAction').val('modifyObject');
					// ����Form Action
	        		$('.managePaymentAdjustmentDetail_form').attr('action', 'paymentAdjustmentDetailAction.do?proc=modify_object');
	        		// �޸İ�ť��ʾ����
					$('#btnEditPaymentAdjustmentDetail').val('<bean:message bundle="public" key="button.save" />');
				}
				// �༭
				else{
					// �ڴ�����ӻ��޸ĵ���֤
					var flag = 0;
					
					flag = flag + validate("managePaymentAdjustmentDetail_itemId", true, "select", 0, 0);
					flag = flag + validate("managePaymentAdjustmentDetail_billAmountPersonal", true, "currency", 9, 0, 100000, 0);
					flag = flag + validate("managePaymentAdjustmentDetail_costAmountPersonal", true, "currency", 9, 0, 100000, 0);
					flag = flag + validate("managePaymentAdjustmentDetail_description", false, "common", 500, 0);
					flag = flag + validate("managePaymentAdjustmentDetail_status", true, "select", 0, 0);
					
					if(flag == 0){
						$('.managePaymentAdjustmentDetail_form').submit();
					}
				}
			}else {
				var flag = validate_manage_primary_form();
				
				if( flag == 0 ){
					enableForm('managePaymentAdjustmentHeader_form');
					submit('managePaymentAdjustmentHeader_form');
				}
			}
		});
		
		// ���ʵ����ӱ�ȡ������¼�
		$('#btnCancelPaymentAdjustmentDetail').click(function(){
			if(agreest())
			link('paymentAdjustmentDetailAction.do?proc=list_object&adjustmentHeaderId=<bean:write name="paymentAdjustmentHeaderForm" property="encodedId" />');
		});
		
		// ��ʼ��JS
		if ( getSubAction() != 'createObject' ) {
			// Disable Form
		    disableForm('managePaymentAdjustmentHeader_form');
			// ��ʼ��SubAction
		    $('.managePaymentAdjustmentHeader_form input.subAction').val('viewObject');
		 	// ���İ�ť��ʾ����
		    $('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		 	// ����Title
		   $('#pageTitle').html('<bean:message bundle="payment" key="payment.salary.adjustment" /> <bean:message bundle="public" key="oper.view" />');
			 // ����ContractId Keyup�¼�
		    $('#contractId').trigger('keyup');
		    // ��ʾ������ϸ
		    $('#paymentAdjustmentDetail-information').show();
		} else if ( getSubAction() == 'createObject' ) {
		 	// Disable���水ť
	        $('#contractId').addClass('important');
			$('.managePaymentAdjustmentHeader_clientId').attr('disabled', 'disabled');
			$('.managePaymentAdjustmentHeader_clientNameZH').attr('disabled', 'disabled');
			$('.managePaymentAdjustmentHeader_clientNameEN').attr('disabled', 'disabled');
			$('.managePaymentAdjustmentHeader_employeeId').attr('disabled', 'disabled');
			$('.managePaymentAdjustmentHeader_employeeNameZH').attr('disabled', 'disabled');
			$('.managePaymentAdjustmentHeader_employeeNameEN').attr('disabled', 'disabled');
			$('.managePaymentAdjustmentHeader_billAmountPersonal').attr('disabled', 'disabled');
			$('.managePaymentAdjustmentHeader_costAmountPersonal').attr('disabled', 'disabled');
	        $('.managePaymentAdjustmentHeader_status').attr('disabled', 'disabled');
		}
	})(jQuery);
	
	// ��������ӣ�Ajax����ȥ�޸�ҳ��
	function paymentAdjustmentDetailModify( adjustmentDetailId ){
		// ��ʾCancel��ť
		$('#btnCancelPaymentAdjustmentDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// װ�شӱ�༭����
		loadHtml('#detailFormWrapper', 'paymentAdjustmentDetailAction.do?proc=to_objectModify_ajax&id=' + adjustmentDetailId, true );
		// �޸İ�ť��ʾ����
		$('#btnEditPaymentAdjustmentDetail').val('<bean:message bundle="public" key="button.edit" />');		
	};
	
	// ��ȡStatus
	function getStatus(){
		return $('.managePaymentAdjustmentHeader_form .managePaymentAdjustmentHeader_status').val();
	};
	
	// ��ȡSubAction
	function getSubAction(){
		return $('.managePaymentAdjustmentHeader_form input#subAction').val();
	};
</script>

