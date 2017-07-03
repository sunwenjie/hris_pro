<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.web.actions.biz.sb.SBAdjustmentHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<!-- SBAdjustmentHeader - information -->
<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="sb" key="sb.adjustment.header.title" /></label>
	        <logic:notEmpty name="sbAdjustmentHeaderForm" property="adjustmentHeaderId" >
	        	<label class="recordId"> &nbsp; (ID: <bean:write name="sbAdjustmentHeaderForm" property="adjustmentHeaderId" />)</label>
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
				<logic:empty name="sbAdjustmentHeaderForm" property="encodedId">
					<kan:auth right="new" action="<%=SBAdjustmentHeaderAction.accessAction%>">
						<input type="button" class="save" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" />
					</kan:auth>
				</logic:empty>
				<logic:notEmpty name="sbAdjustmentHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=SBAdjustmentHeaderAction.accessAction%>">
						<input type="button" class="save" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" />
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="submit" action="<%=SBAdjustmentHeaderAction.accessAction%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" style="display: none;" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>
				<kan:auth right="list" action="<%=SBAdjustmentHeaderAction.accessAction%>">
					 <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />"/>
				</kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
			<jsp:include page="/contents/sb/adjustment/header/form/manageSBAdjustmentHeaderForm.jsp" flush="true"></jsp:include>		
	    </div>
	</div>
</div>

<!-- SBAdjustmentDetail - information -->
<div class="box" id="SBAdjustmentDetail-information" style="display: none;">		
	<!-- Inner -->	
	<div class="head">
		<label><bean:message bundle="sb" key="sb.adjustment.detail.title" /></label>
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
			<input type="button" class="editbutton" name="btnEditSBAdjustmentDetail" id="btnEditSBAdjustmentDetail" value="<bean:message bundle="public" key="button.add" />" /> 
			<input type="button" class="reset" name="btnCancelSBAdjustmentDetail" id="btnCancelSBAdjustmentDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
			<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listSBAdjustmentDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
		</div>	
		<div id="detailFormWrapper" style="display: none;">
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

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/searchContract.jsp"></jsp:include>
</div>		
	
<script type="text/javascript">
	(function($) {
		// ��ʼ���˵���ʽ
		$('#menu_sb_Modules').addClass('current');
		$('#menu_sb_Process').addClass('selected');
		$('#menu_sb_DeclarationAdjustment').addClass('selected');
		
		// ������ģʽ��ӿ��ٲ�ͼ��
		if( getSubAction() != 'createObject'){
			$('#employeeIdLI label').append('&nbsp;<a onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name="sbAdjustmentHeaderForm" property="encodedEmployeeId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
			$('#contractIdLI label').append('&nbsp;<a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="sbAdjustmentHeaderForm" property="encodedContractId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
		}
		
		// ��ӡ���������Э����Ϣ��
		$('#contractId').after('<a onclick="popupContractSearch();" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>');
		
		if( getStatus() == '1' && getSubAction() != 'createObject' ){
			$('#btnSubmit').show();
		}else if(getStatus() == '4'){
			$('#btnSubmit').show();
		}else if( getStatus() == '2' || getStatus() == '3' || getStatus() == '5'){
			$('#btnEdit').hide();
			$('#btnEditSBAdjustmentDetail').hide();
			$('#btnDelete').hide();
		}
	    
	    // �ύ��ť�¼�
		$('#btnSubmit').click( function () { 
			if(validate_manage_primary_form() == 0){
				// ���ĵ�ǰForm��SubAction
				if($('.manageSBAdjustmentHeader_form input#subAction').val() != 'createObject'){
					$('.manageSBAdjustmentHeader_form').attr('action', 'sbAdjustmentHeaderAction.do?proc=submit_object');
				}
				$('.manageSBAdjustmentHeader_form input#subAction').val('submitObject');
		    	enableForm('manageSBAdjustmentHeader_form');
		    	submitForm('manageSBAdjustmentHeader_form');
			}
		});
		
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
							 
							 addError('manageSBAdjustmentHeader_contractId', '<bean:message bundle="public" key="error.input.id.invalid" />');
						 }
						 
						 var callback = "if($('#employeeSBId').children().length=='1' && !$('.manageSBAdjustmentHeader_contractId').hasClass('error')){addError('manageSBAdjustmentHeader_contractId', '<bean:message bundle="public" key="error.no.sb" />');}if( getSubAction() == 'viewObject' ){$('#employeeSBId').val(\"<bean:write name='sbAdjustmentHeaderForm' property='employeeSBId' />\");}";
						 // �����籣�����𷽰�
						 loadHtmlWithRecall('#employeeSBId','employeeContractSBAction.do?proc=list_object_options_ajax&contractId=' + $('#contractId').val(), false , callback );
					 }
				});
			}
		});
		
		// SBAdjustment Header ���桢�༭Click
		$('#btnEdit').click(function() {
		    if( getSubAction() == 'viewObject') {
		        enableForm('manageSBAdjustmentHeader_form');
		        $('.manageSBAdjustmentHeader_form input#subAction').val('modifyObject');
		        $('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		        $('.manageSBAdjustmentHeader_form').attr('action', 'sbAdjustmentHeaderAction.do?proc=modify_object');
		        $('#pageTitle').html('<bean:message bundle="sb" key="sb.adjustment.header.title" /> <bean:message bundle="public" key="oper.edit" />');
		        $('#contractId').addClass('important');
		        $('.manageSBAdjustmentHeader_status').attr('disabled', 'disabled');
		        $('.manageSBAdjustmentHeader_entityId').attr('disabled', 'disabled');
				$('.manageSBAdjustmentHeader_businessTypeId').attr('disabled', 'disabled');
				$('.manageSBAdjustmentHeader_employeeId').attr('disabled', 'disabled');
				$('.manageSBAdjustmentHeader_employeeNameZH').attr('disabled', 'disabled');
				$('.manageSBAdjustmentHeader_employeeNameEN').attr('disabled', 'disabled');
				$('.manageSBAdjustmentHeader_clientId').attr('disabled', 'disabled');
				$('.manageSBAdjustmentHeader_orderId').attr('disabled', 'disabled');
				$('.manageSBAdjustmentHeader_clientNameZH').attr('disabled', 'disabled');
				$('.manageSBAdjustmentHeader_clientNameEN').attr('disabled', 'disabled');
				$('.manageSBAdjustmentHeader_amountCompany').attr('disabled', 'disabled');
				$('.manageSBAdjustmentHeader_amountPersonal').attr('disabled', 'disabled');
		    } else {
		        var flag = validate_manage_primary_form();
		        
		        if (flag == 0) {
		        	enableForm('manageSBAdjustmentHeader_form');
		            submit('manageSBAdjustmentHeader_form');
		        }
		    }
		});
		
		// SBAdjustment Header �б�Click
		$('#btnList').click(function(){
			if (agreest())
			link('sbAdjustmentHeaderAction.do?proc=list_object');
		});
		
		// SBAdjustment Detail ���桢�༭click
		$('#btnEditSBAdjustmentDetail').click(function(){
			if($('.manageSBAdjustmentHeader_form #headerId').val() != ''){
				var detailSubAction = $('.manageSBAdjustmentDetail_form input#subAction').val();
				
				// ��� 
				if( detailSubAction == '' ){
					// ����Ĭ��ֵ
					$('.manageSBAdjustmentDetail_form .manageSBAdjustmentDetail_status').val('1');
					// ��ʾCancel��ť
					$('#btnCancelSBAdjustmentDetail').show();
					// ��ʾList Detail Form
					$('#detailFormWrapper').show();	
					// ����SubActionΪ�½�
					$('.manageSBAdjustmentDetail_form input#subAction').val('createObject');
					// �޸İ�ť��ʾ����
					$('#btnEditSBAdjustmentDetail').val('<bean:message bundle="public" key="button.save" />');
				}
				// �鿴
				else if( detailSubAction == 'viewObject'){
					// �༭������Enable����Form
					enableForm('manageSBAdjustmentDetail_form');
					// ����SubActionΪ�༭
					$('.manageSBAdjustmentDetail_form input#subAction').val('modifyObject');
					// �޸İ�ť��ʾ����
					$('#btnEditSBAdjustmentDetail').val('<bean:message bundle="public" key="button.save" />');
					// ����Form Action
	        		$('.manageSBAdjustmentDetail_form').attr('action', 'sbAdjustmentDetailAction.do?proc=modify_object');
				}
				// �༭
				else{
					// �ڴ�����ӻ��޸ĵ���֤
					var flag = 0;
					
					flag = flag + validate("manageSBAdjustmentDetail_itemId", true, "select", 0, 0);
					flag = flag + validate("manageSBAdjustmentDetail_amountPersonal", true, "currency", 8, 0, 10000, -10000);
					flag = flag + validate("manageSBAdjustmentDetail_amountCompany", true, "currency", 8, 0, 10000, -10000);
					flag = flag + validate("manageSBAdjustmentDetail_description", false, "common", 500, 0);
					flag = flag + validate("manageSBAdjustmentDetail_status", true, "select", 0, 0);
					
					if(flag == 0){
						$('.manageSBAdjustmentDetail_form').submit();
					}
				}
			}else {
				var flag = validate_manage_primary_form();
				
				if( flag == 0 ){
					enableForm('manageSBAdjustmentHeader_form');
					submit('manage_primary_form');
				}
			}
		});
		
		// SBAdjustment Detail ȡ��click
		$('#btnCancelSBAdjustmentDetail').click(function(){
			if(agreest())
			link('sbAdjustmentDetailAction.do?proc=list_object&id=<bean:write name="sbAdjustmentHeaderForm" property="encodedId" />');
		});
		
		if ( getSubAction() != 'createObject' ) {
		    disableForm('manageSBAdjustmentHeader_form');
		    $('.manageSBAdjustmentHeader_form input.subAction').val('viewObject');
		    $('#pageTitle').html('<bean:message bundle="sb" key="sb.adjustment.header.title" /> <bean:message bundle="public" key="oper.view" />');
		    $('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		    $('#contractId').trigger('keyup');
		    $('#SBAdjustmentDetail-information').show();
		} else if ( getSubAction() == 'createObject' ) {
		    $('#contractId').addClass('important');
			$('.manageSBAdjustmentHeader_status').attr('disabled', 'disabled');
			$('.manageSBAdjustmentHeader_entityId').attr('disabled', 'disabled');
			$('.manageSBAdjustmentHeader_businessTypeId').attr('disabled', 'disabled');
			$('.manageSBAdjustmentHeader_employeeId').attr('disabled', 'disabled');
			$('.manageSBAdjustmentHeader_employeeNameZH').attr('disabled', 'disabled');
			$('.manageSBAdjustmentHeader_employeeNameEN').attr('disabled', 'disabled');
			$('.manageSBAdjustmentHeader_clientId').attr('disabled', 'disabled');
			$('.manageSBAdjustmentHeader_orderId').attr('disabled', 'disabled');
			$('.manageSBAdjustmentHeader_clientNameZH').attr('disabled', 'disabled');
			$('.manageSBAdjustmentHeader_clientNameEN').attr('disabled', 'disabled');
			$('.manageSBAdjustmentHeader_amountCompany').attr('disabled', 'disabled');
			$('.manageSBAdjustmentHeader_amountPersonal').attr('disabled', 'disabled');
		}
	})(jQuery);
	
	// ��������ӣ�ajax����ȥ�޸�ҳ��
	function sbAdjustmentDetailModify( detailId, itemId ){
		// ��ʾCancel��ť
		$('#btnCancelSBAdjustmentDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// Ajax����SBAdjustment Detail�޸�ҳ��
		loadHtmlWithRecall('#detailFormWrapper', 'sbAdjustmentDetailAction.do?proc=to_objectModify_ajax&id=' + detailId, true, "$('.manageSBAdjustmentDetail_itemId').val(\"" + itemId + "\")");
		// �޸İ�ť��ʾ����
		$('#btnEditSBAdjustmentDetail').val('<bean:message bundle="public" key="button.edit" />');		
	};
	
	// ��ȡ״̬
	function getStatus(){
		return $('.manageSBAdjustmentHeader_form .manageSBAdjustmentHeader_status').val();
	};
	
	// ��ȡsubAction
	function getSubAction(){
		return $('.manageSBAdjustmentHeader_form input#subAction').val();
	};
</script>

