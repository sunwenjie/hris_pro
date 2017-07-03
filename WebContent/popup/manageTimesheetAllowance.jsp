<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="timesheetAllowanceModualId">
    <div class="modal-header" id="employeeContractSBHeader">
        <a class="close" data-dismiss="modal" onclick="hidePopup();">��</a>
        <label>�����Բ���</label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input title="�����¼" type="button" class="save" name="btnPopupSave" id="btnPopupSave" value="����"/>
	    	<input title="�����������Ϣ" type="button" class="reset" name="btnPopupReset" id="btnPopupReset" onclick="resetPopup();" value="<bean:message bundle="public" key="button.reset" />"/>
	    </div>
       <html:form action="timesheetAllowanceAction.do?proc=add_object" styleClass="manageTimesheetAllowance_form">
			<%= BaseAction.addToken( request ) %>
			<input type="hidden" id="allowanceId" name="allowanceId" value="<bean:write name="timesheetAllowanceForm" property="encodedId"/>" />
			<input type="hidden" id="headerId" name="headerId" value="<bean:write name="timesheetHeaderForm" property="headerId"/>" />
			<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="timesheetAllowanceForm" property="subAction"/>" /> 
			<fieldset>
				<ol class="auto">
					<li class="required">
						<label><em>* </em>�����ֶ�</label>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label>��Ŀ  <em>* </em></label> 
						<html:select property="itemId" styleClass="manageTimesheetAllowance_itemId" >
							<option value="0">��ѡ��</option>
							<html:optionsCollection property="salaryItems" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
					<li>
						<label>����  <em> *</em></label>
						<html:text property="base" styleClass="manageTimesheetAllowance_base"  />
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label>��ע</label> 
						<html:textarea property="description" styleClass="manageTimesheetAllowance_description"></html:textarea>
					</li>
					<li>
						<label>״̬  <em> *</em></label> 
						<html:select property="status" styleClass="manageTimesheetAllowance_status" styleId="status">
							<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
			</fieldset>
		</html:form>
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	(function($) {
		// ���水ť����¼�
		$('#btnPopupSave').click( function () {	
			 if($('.manageTimesheetAllowance_form #subAction').val() == 'viewObject' ){
				// Enable����Form
				enableForm('manageTimesheetAllowance_form');
				$('#btnPopupSave').val('����');
				// ����SubActionΪ�༭
				$('.manageTimesheetAllowance_form input#subAction').val('modifyObject');
				// ����Form Action
        		$('.manageTimesheetAllowance_form').attr('action', 'timesheetAllowanceAction.do?proc=modify_object');
			}else{
				var flag = 0;
				
				flag = flag + validate( 'manageTimesheetAllowance_itemId', true, 'select', 0, 0 );
				flag = flag + validate( 'manageTimesheetAllowance_base', true, 'currency', 0, 0 );
				flag = flag + validate( 'manageTimesheetAllowance_description', false, 'select', 500, 0 );
				flag = flag + validate( 'manageTimesheetAllowance_status', true, 'select', 0, 0 );
				
				if( flag == 0){
					submit('manageTimesheetAllowance_form'); 
				}
			}
		});
	})(jQuery);

	// ������������
	function resetPopup(){
		$('.manageTimesheetAllowance_itemId').val('0');
		$('.manageTimesheetAllowance_base').val('');
		$('.manageTimesheetAllowance_description').val('');
		$('.manageTimesheetAllowance_status').val('0');
	};
	
	// ��ʾ������
	function showPopup(){
		$('#timesheetAllowanceModualId').removeClass('hide');
    	$('#shield').show();
    	if( getPopSubAction() == 'viewObject'){
    		$('#btnPopupSave').val('�༭');
    		$('#btnPopupSave').removeAttr('disabled');
    	}else{
    		resetPopup();
    		enableForm('manageTimesheetAllowance_form');
    		$('.manageTimesheetAllowance_form').attr('action', 'timesheetAllowanceAction.do?proc=add_object');
    		$('.manageTimesheetAllowance_status').val('1');
    		$('#btnPopupSave').val('����');
    	}
    	
    	// ��մ���
    	cleanAllError();
	};

	// ���ص�����
	function hidePopup(){
		$('#timesheetAllowanceModualId').addClass('hide');
    	$('#shield').hide();
    	resetPopup();
    	$('.manageTimesheetAllowance_form input#subAction').val('');
	};
	
	// ��մ���
	function cleanAllError(){
		cleanError('manageTimesheetAllowance_itemId');
		cleanError('manageTimesheetAllowance_base');
		cleanError('manageTimesheetAllowance_description');
		cleanError('manageTimesheetAllowance_status');
	};
	
	// ��ȡ��ǰSubAction
	function getPopSubAction(){
		return $('.manageTimesheetAllowance_form input[id="subAction"]').val();
	};

	// Esc�����¼� - ���ص�����
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	hidePopup();
	    } 
	});
</script>