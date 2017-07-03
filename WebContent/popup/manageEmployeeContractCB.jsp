<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="employeeContractCBModalId">
    <div class="modal-header" id="employeeContractCBHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#employeeContractCBModalId').addClass('hide');$('#shield').hide();">��</a>
        <label><bean:message bundle="cb" key="cb.batch.solution" /></label>
    </div>
    <div class="modal-body">
    	<div class="top">
    		<!-- ���˹����Ա༭6 -->
	   		<input  type="button" class="save hide" name="btnPopupSave" id="btnPopupSave" value="<bean:message bundle="public" key="button.save" />"/>
	   		<kan:auth right="submit" action="HRO_CB_PURCHASE">
	   			<input title="<bean:message bundle="public" key="button.submit" />" type="button" class="function hide" name="btnPopupSubmit" id="btnPopupSubmit" onclick="submitPopup();" value="<bean:message bundle="public" key="button.submit" />"/>
	   		</kan:auth>
	   		<input title="<bean:message bundle="public" key="button.delete" />" type="button" class="delete hide" name="btnPopupDelete" id="btnPopupDelete" onclick="deletePopup();" value="<bean:message bundle="public" key="button.delete" />"/>
	    	<input title="�����������Ϣ" type="button" class="reset hide" name="btnPopupReset" id="btnPopupReset" onclick="resetPopup();" value="<bean:message bundle="public" key="button.reset" />"/>
	    </div>
<!-- 		    <span class="highlight">*** ���棺�����¼���깺�ύ�����沢�ύ�깺���˹��ύ�����沢�ύ�˹� ***</span> -->
        <html:form action="employeeContractCBAction.do?proc=modify_objects_popup" styleClass="employeeContractCB_form">
        	<%= BaseAction.addToken( request ) %>
<!-- 			<input type="hidden" name="selectedIds" id="selectedIds" value="" /> -->
			<input type="hidden" name="selectedIds" id="selectedIds" value="" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<input type="hidden" name="encodedContractId" id="encodedContractId" class="manageEmployeeContractCB_encodedContractId" value="" />
			<fieldset>
				<ol class="auto hide" id="applyToAllHeaderOL">
					<li>
						<label id="solutionIdLabel">�Ƿ��޸�<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>���̱���Ϣ��</label>
						<input type="checkbox" id="applyToAllHeader" name="applyToAllHeader" value="1" checked="checked"/>
					</li>
				</ol>
				<ol class="hide" id="noticeOL">
					<li>
					    <span class="highlight">ע�⣺ <br/>  1�������ѡ���޸�ѡ��<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>�Ķ�Ӧ�̱��������͵��������ݡ�<br/>  2����<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>�����ڶ�Ӧ�̱��������������򴴽��������̱���������ѡ���Ƿ�ѡ��Ӱ�촴������<br/> 3����<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>���ڶ�Ӧ�̱������������ݣ��ٰ������桱ֻ�޸���ֵ����;�ڰ����ύ���������ѡ���̱�����״̬���С��깺�����˹���������</span>
					</li>
				</ol>
				<ol class="auto hide">
					<li>
						<label>������ϢID</label><span><html:text property="contractId" styleClass="manageEmployeeContractCB_contractId"></html:text></span>
					</li>
					<li>
						<label>����ID</label><span><html:text property="employeeCBId" styleClass="manageEmployeeContractCB_employeeCBId"></html:text></span>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label id="solutionIdLabel"><bean:message bundle="cb" key="cb.batch.solution" /><em> *</em>&nbsp;&nbsp;&nbsp;</label>
						<html:select property="solutionId" styleId="solutionId" styleClass="manageEmployeeContractCB_solutionId">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="cb" key="cb.header.start.date" /></label> 
						<input type="hidden" id="tempStartDate" value=""/>
						<input name="startDate" class="startDate  Wdate " id="startDate" <logic:equal name="employeeContractCBForm" property="status" value="2">disabled="disabled"</logic:equal><logic:equal name="employeeContractCBForm" property="status" value="3">disabled="disabled"</logic:equal> onfocus="WdatePicker({minDate:'tempStartDate',maxDate:'#F{ $dp.$D(\'endDate\')}'})" type="text" maxlength="10">
					</li>
					<li>
						<label><bean:message bundle="cb" key="cb.header.end.date" /></label>
						<input type="hidden" id="tempEndDate" value=""/>
						<input name="endDate" class="endDate  Wdate " id="endDate" onfocus="WdatePicker({minDate:'#F{ $dp.$D(\'startDate\')}'})" type="text" maxlength="10">
					</li>
					<li>
						<label><bean:message bundle="public" key="public.status" /></label> 
						<html:select property="status" disabled="disabled" styleClass="manageEmployeeContractCB_status">
							<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto"><li><a id="moreInfo-popup"><bean:message bundle="public" key="link.more.info" /></a></li></ol>
				<div id="moreInfo-div-popup" style="border: 1px solid #aaa; -webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px; padding: 10px 10px 0px 10px; display: none;" >
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.cb.solution.header.free.short.of.month" /></label> 
							<html:select property="freeShortOfMonth" styleClass="manageEmployeeContractCB_freeShortOfMonth">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.cb.solution.header.charge.full.month" /></label> 
							<html:select property="chargeFullMonth" styleClass="manageEmployeeContractCB_chargeFullMonth">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</div>
				<ol class="auto" style="display:none">
					<li>
						<label><bean:message bundle="public" key="public.description" /></label> 
						<html:textarea property="description" styleClass="manageEmployeeContractCB_description"></html:textarea>
					</li>
				</ol>
			</fieldset>
		</html:form >
    </div>
     
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
(function($) {
	// ״̬��0:���̱���1:�깺�ύ��2:���깺��3:��������4:�˹��ύ��5:���˹���6:���˹���
	
	// ����̱�������� - ��Ҫ��ʾ�ύ��ť
	if( getSubAction() == 'createObject' ){
		$('#btnPopupSubmit').val("<bean:message bundle="public" key="button.purchase.submit" />");
	}
	// ��ʼ������Button��Disable Form
	hideButton();
	disableForm('employeeContractCB_form');

	// ���水ť����¼�
	$('#btnPopupSave').click( function () {
		// ����Btn
// 		hideButton();
		
    	// ��մ���
    	cleanAllError();
    	
		var popupSubAction = $('.employeeContractCB_form input[id="subAction"]').val();
		var status = $('.manageEmployeeContractCB_status').val();
		
		var selectedIds =  $('.list_form input[id="selectedIds"]').val();
		var contractId = $('.manageEmployeeContractCB_contractId').val();
		
		// ����ǲ鿴
		if(popupSubAction == 'viewObject'){
			enableForm('employeeContractCB_form');
			// ״̬�������޸�
			$('.manageEmployeeContractCB_status').attr('disabled', true);
			// ���̱������������޸�
			$('.manageEmployeeContractCB_solutionId').attr('disabled', true);
			// �޸�subActionΪ�޸�
			$('.employeeContractCB_form input[id="subAction"]').val('modifyObject');
			// ��ʾ���Btn
			$('#btnPopupSave').removeClass('hide');
			$('#btnPopupReset').removeClass('hide');
			$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
			
			// ����ǡ����̱���״̬��ʾ���깺�ύ��
			if(status == 0){
				$('#btnPopupSubmit').removeClass('hide');
				$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.purchase.submit" />');
				$('#endDate').attr('disabled', true);
			}
			// �����깺�������������� ��ʾ���˹��ύ��
			else if(status == 2 || status == 3){
				$('#btnPopupSubmit').removeClass('hide');
				$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.return.submit" />');
				$('#startDate').attr('disabled', true);
			}
			// �����˹��������޸����ڣ����ء��ύ��Button,�����޸�����
			else if(status == 6){
				$('#btnPopupSubmit').addClass('hide');
				$('#startDate').attr('disabled', true);
				$('#endDate').attr('disabled', true);
			}

		}
		// ����ǵ�������
		else if(popupSubAction == 'createObject'){
				
			if(!checkPopupValue()){
		    	enableForm('employeeContractCB_form');
				// �ύģ̬��
				submitForm('employeeContractCB_form', 'modifyObject', null, null, null, 'search-results', null, hidePopup());
			}
				
		}
		// ����ǵ����������޸�
		else if(popupSubAction == 'modifyObject'){
				
			if(!checkPopupValue()){
		    	enableForm('employeeContractCB_form');
				// �ύģ̬��
				submitForm('employeeContractCB_form', 'modifyObject', null, null, null, 'search-results', null, hidePopup());
			}
			
		}
	});
	
	 // �����̱�������
	 loadHtml('#solutionId','clientOrderCBAction.do?proc=list_object_options_ajax&contractId=<bean:write name="employeeContractCBForm" property="encodedContractId"/>&solutionId=<bean:write name="employeeContractCBForm" property="solutionId"/>');

	 // �̱�����Change�¼�
	 $('.manageEmployeeContractCB_solutionId').change(function(){
		 $.ajax({
			url:'employeeContractCBAction.do?proc=get_object_json_manage&contractId=' + $('.manageEmployeeContractCB_contractId').val() + '&solutionId=' + $('.manageEmployeeContractCB_solutionId').val() + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractCB_freeShortOfMonth').val(data.freeShortOfMonth);
				$('.manageEmployeeContractCB_chargeFullMonth').val(data.chargeFullMonth);
				$('.manageEmployeeContractCB_solutionId').val(data.solutionId);
				$('#startDate').val(data.startDate);
		
				// �����̱�������
				loadSolutionHeaderOptions( $('.manageEmployeeContractCB_encodedContractId').val(), data.solutionId, false);
			}
		});
	 });
	 
	 // �� ��ȫ����� �� ���¼Ʒ� �¼�
	 $('.manageEmployeeContractCB_freeShortOfMonth').change(function(){
		 var chargeFullMonthVal = $('.manageEmployeeContractCB_chargeFullMonth').val();
		 if(this.value==chargeFullMonthVal && this.value!=0 && this.value!=2){
			 this.value=0;
			 alert('\'��ȫ�����\'  ��  \'���¼Ʒ�\'  ����ͬʱѡ��  \'��\'');
		 }
	 });
	 
	 $('.manageEmployeeContractCB_chargeFullMonth').change(function(){
		 var freeShortOfMonthVal = $('.manageEmployeeContractCB_freeShortOfMonth').val();
		 if(this.value==freeShortOfMonthVal && this.value!=0 && this.value!=2){
			 this.value=0;
			 alert('\'��ȫ�����\'  ��  \'���¼Ʒ�\'  ����ͬʱѡ��  \'��\'');
		 }
	 });
})(jQuery);

	// ����ģ̬����_���(����)
	function addEmployeeContractCB(contractId, encodedContractId){
		// ����Button
		hideButton();
		enableForm('employeeContractCB_form');
		
		// ���EmployeeCB ID
		$('.manageEmployeeContractCB_encodedContractId').val(encodedContractId);
		$('.manageEmployeeContractCB_employeeCBId').val('');
		
		// �޸�Action		
		$('.employeeContractCB_form').attr('action', 'employeeContractCBAction.do?proc=modify_object_popup');
		// ״̬�������޸�
		$('.manageEmployeeContractCB_status').attr('disabled', true);
		// ��ʾ�����桱�͡����á�button
		$('#btnPopupSave').removeClass('hide');
		$('#btnPopupReset').removeClass('hide');
		// ��ʾ���깺�ύ��button
		$('#btnPopupSubmit').removeClass('hide');
		$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.purchase.submit" />');
		// endDate���ɱ༭
		$('#endDate').attr('disabled', true);
		// subActionΪ����
		$('.employeeContractCB_form input[id="subAction"]').val('createObject');
		
		// ���ӱ�ʱ�䡱Ĭ��ֵ
		$.ajax({
			url:'employeeContractCBAction.do?proc=get_object_json_manage&contractId=' + contractId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractCB_freeShortOfMonth').val(data.freeShortOfMonth);
				$('.manageEmployeeContractCB_chargeFullMonth').val(data.chargeFullMonth);
				// ����ContractId
				$('.manageEmployeeContractCB_contractId').val(data.contractId);
				$('#startDate').val(data.startDate);
		
				// �����̱�������
				loadSolutionHeaderOptions(encodedContractId, null, false);
			}
		});
		
	};
	
	// ����ģ̬����_����(����)
	function addEmployeeContractCBs(){
		var selectedIds =  $('.list_form input[id="selectedIds"]').val();
		// �Ƴ�CheckBox����
		$('#applyToAllHeader').removeAttr('checked');
		
		// ���δѡ���¼
		if(selectedIds == null || selectedIds.trim() == ''){
			alert("��ѡ��<logic:equal name='role' value='1'>��Ա</logic:equal><logic:equal name='role' value='2'>Ա��</logic:equal>��¼��");
		}else{
			// ����Popup SelectedIdsֵ
			$('.employeeContractCB_form input[id="selectedIds"]').val(selectedIds);
			// ����Button
			hideButton();
			enableForm('employeeContractCB_form');
			
			// �޸�Button
			$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
			$('#btnPopupSubmit').val('�ύ');
			// ���Contract ID ��EmployeeCB ID
			$('.manageEmployeeContractCB_contractId').val('');
			$('.manageEmployeeContractCB_employeeCBId').val('');
			
			// �޸�Action		
			$('.employeeContractCB_form').attr('action', 'employeeContractCBAction.do?proc=modify_objects_popup');
			// ״̬�������޸�
			$('.manageEmployeeContractCB_status').attr('disabled', true);
			// ��ʾ�����桱�����ύ���͡����á�button
			$('#btnPopupSave').removeClass('hide');
			$('#btnPopupSubmit').removeClass('hide');
			$('#btnPopupReset').removeClass('hide');
			// ��ʾ��ʾ���͸�ѡ��
			$('#applyToAllHeaderOL').removeClass('hide');
			$('#noticeOL').removeClass('hide');
			// subActionΪ����
			$('.employeeContractCB_form input[id="subAction"]').val("modifyObject");
			// �����̱�������
			loadSolutionHeaderOptions('', '', true);
		}
	}
	
	// ����ģ̬����_�˹�
	function rollbackEmployeeContractCB(employeeCBId){
		// ����Button
		hideButton();
		enableForm('employeeContractCB_form');
		// ״̬�������޸�
		$('.manageEmployeeContractCB_status').attr('disabled', true);
		// ���������޸�
		$('.manageEmployeeContractCB_solutionId').attr('disabled', true);
		
		// �޸�Action
		$('.employeeContractCB_form').attr('action', 'employeeContractCBAction.do?proc=modify_object_popup');
		
		$.ajax({
			url:'employeeContractCBAction.do?proc=get_object_json&employeeCBId=' + employeeCBId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractCB_contractId').val(data.contractId);
				$('.manageEmployeeContractCB_employeeCBId').val(data.employeeCBId);
				$('.manageEmployeeContractCB_solutionId').val(data.solutionId);
				$('.manageEmployeeContractCB_freeShortOfMonth').val(data.freeShortOfMonth);
				$('.manageEmployeeContractCB_chargeFullMonth').val(data.chargeFullMonth);
				$('.manageEmployeeContractCB_description').val(data.description);
				$('.manageEmployeeContractCB_status').val(data.status);
				$('#startDate').val(data.startDate);
				$('#endDate').val(data.endDate);

				// �����̱�������
				loadSolutionHeaderOptions(data.encodedContractId, data.solutionId, true);
			}
		});
		
		// �޸�Button
		$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
		// �޸�subActionΪ�޸�
		$('.employeeContractCB_form input[id="subAction"]').val('modifyObject');
		// ��ʾ�����桱�͡����á�button
		$('#btnPopupSave').removeClass('hide');
		$('#btnPopupReset').removeClass('hide');
		// ��ʾ���˹��ύ��
		$('#btnPopupSubmit').removeClass('hide');
		$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.return.submit" />');
		// startDate���ɱ༭
		$('#startDate').attr('disabled', true);
		// ��ʾģ̬��
		showPopup();
	}
	
	// ����ģ̬����_�޸�
	function modifyEmployeeContractCB(employeeCBId){
		disableForm('employeeContractCB_form');
		// ����Button
		hideButton();
		// ״̬�������޸�
		$('.manageEmployeeContractCB_status').attr('disabled', true);
		// �޸�SubAction
		$('.employeeContractCB_form input[id="subAction"]').val('viewObject');
		// �޸�Action
		$('.employeeContractCB_form').attr('action', 'employeeContractCBAction.do?proc=modify_object_popup');
		
		$.ajax({
			url:'employeeContractCBAction.do?proc=get_object_json&employeeCBId=' + employeeCBId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractCB_contractId').val(data.contractId);
				$('.manageEmployeeContractCB_employeeCBId').val(data.employeeCBId);
				$('.manageEmployeeContractCB_solutionId').val(data.solutionId);
				$('.manageEmployeeContractCB_freeShortOfMonth').val(data.freeShortOfMonth);
				$('.manageEmployeeContractCB_chargeFullMonth').val(data.chargeFullMonth);
				$('.manageEmployeeContractCB_description').val(data.description);
				$('.manageEmployeeContractCB_status').val(data.status);
				$('#startDate').val(data.startDate);
				$('#endDate').val(data.endDate);
				
				// ���깺�ύ�������˹��ύ���������˹��� ֻ�ܿ��������޸�
				if(data.status == 1 || data.status == 4 || data.status == 5){
					hideButton();
				}else{
					$('#btnPopupSave').val('<bean:message bundle="public" key="button.edit" />');
					$('#btnPopupSave').removeClass('hide');
				}

				// �����̱�������
				loadSolutionHeaderOptions(data.encodedContractId, data.solutionId, true);
			}
		});

		// ��ʾģ̬��
		showPopup();
	}
	
	// ɾ���̱�����
	function deleteEmployeeContractCB(employeeCBId){
		if(confirm("ȷ��ɾ��������¼��")){
			// ע��SelectedIdsֵ
			$('.employeeContractCB_form input[id="selectedIds"]').val(employeeCBId);
			// �ύ
			submitForm('employeeContractCB_form', 'deleteObjects', null, null, null, 'search-results', "employeeContractCBAction.do?proc=list_object", hidePopup());
		}
	}
	
	// ���ύ����ť
	function submitPopup(){
		
		// ��ͬʱ��֤���������
		if(!checkPopupValue() && !checkDateValue()){
			// �ύģ̬��
			submitForm('employeeContractCB_form', 'submitObject', null, null, null, 'search-results', null, hidePopup());
		}
		
	}

	// ���ģ̬������ֵ�Ƿ���Ч
	function checkPopupValue(){
		
		var flag = 0;
		flag = flag + validate("manageEmployeeContractCB_solutionId", true, "select", 0, 0);
		return flag;
	}
	
	// ��������Ƿ���Ч
	function checkDateValue(){
		
		var status = $('.manageEmployeeContractCB_status').val();
		var contractId = $('.manageEmployeeContractCB_contractId').val();
		var flag = 0;
		
		// �����������������֤���ӱ����ڡ������˱����ڡ�
		if(!contractId){
			flag = flag + validate("startDate", true, null, 0, 0);
			flag = flag + validate("endDate", true, null, 0, 0);
		}
		// ����ǡ����̱���(�깺�ύ) - �ӱ����ڲ���Ϊ��
		else if(status == 0){
			flag = flag + validate("startDate", true, null, 0, 0);
		}
		// ����ǡ����깺��������������(�˹��ύ) - �˱����ڲ���Ϊ��
		else if(status == 2 || status == 3 ){
			flag = flag + validate("endDate", true, null, 0, 0);
		}
		
		return flag;
	}

	// ������������
	function resetPopup(){
		$('.manageEmployeeContractCB_solutionId').val('0');
		$('.manageEmployeeContractCB_freeShortOfMonth').val('0');
		$('.manageEmployeeContractCB_chargeFullMonth').val('0');
		if(!$('#startDate').attr('disabled')){
			$('#startDate').val('');
		}
		if(!$('#endDate').attr('disabled')){
			$('#endDate').val('');
		}
	}
	
	// ���ص�����
	function hidePopup(){
		$('#employeeContractCBModalId').addClass('hide');
    	$('#shield').hide();
	}
	
	// ��ʾ������
	function showPopup(){
		$('#employeeContractCBModalId').removeClass('hide');
    	$('#shield').show();
    	
    	// ��մ���
    	cleanAllError();
	}
	
	// ��մ���
	function cleanAllError(){
		cleanError('manageEmployeeContractCB_solutionId');
		cleanError('startDate');
		cleanError('endDate');
	}
	
	// ����Button����ʾ��Ϣ
	function hideButton(){
		$('#btnPopupSave').addClass('hide');
		$('#btnPopupSubmit').addClass('hide');
		$('#btnPopupDelete').addClass('hide');
		$('#btnPopupReset').addClass('hide');
		
		$('#applyToAllHeaderOL').addClass('hide');
		$('#noticeOL').addClass('hide');
	}
	
	// ��ȡ��ǰSubAction
	function getSubAction(){
		return $('.employeeContractCB_form input#subAction').val();
	}

	// �����̱�������
	function loadSolutionHeaderOptions(contractId, solutionId, integrityFlag){
		// ���������������
		if(integrityFlag){
			loadHtml('.manageEmployeeContractCB_solutionId', 'clientOrderCBAction.do?proc=list_object_options_ajax&contractId=' + contractId + '&solutionId=' + solutionId, null, showPopup());
		}
		// ֻ����δ��ӵ�
		else{
			loadHtml('.manageEmployeeContractCB_solutionId', 'employeeContractCBAction.do?proc=list_object_options_manage_ajax&contractId=' + contractId + '&solutionId=' + solutionId, null, showPopup());
		}
	}
	
	// ��������Ϣ������¼�
	$('#moreInfo-popup').click( function () { 
			if($('#moreInfo-div-popup').is(':visible')) { 
				$('#moreInfo-div-popup').hide();
			} else { 
				$('#moreInfo-div-popup').show();
			}
	});
	
	// Esc�����¼� - ���ص�����
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	hidePopup();
	    } 
	});
</script>