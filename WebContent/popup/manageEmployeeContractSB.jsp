<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@	page import="com.kan.base.util.CachedUtil"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="employeeContractSBModalId">
    <div class="modal-header" id="employeeContractSBHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#employeeContractSBModalId').addClass('hide');$('#shield').hide();">��</a>
        <label><bean:message bundle="sb" key="sb.solution" /></label>
    </div>
    
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save hide" name="btnPopupSave" id="btnPopupSave" value="<bean:message bundle="public" key="button.save" />"/>
	   		<kan:auth right="submit" action="HRO_SB_PURCHASE">
	   			<input title="<bean:message bundle="public" key="button.submit" />" type="button" class="function hide" name="btnPopupSubmit" id="btnPopupSubmit" onclick="submitPopup();" value="<bean:message bundle="public" key="button.submit" />"/>
	   		</kan:auth>
	   		<input title="<bean:message bundle="public" key="button.delete" />" type="button" class="delete hide" name="btnPopupDelete" id="btnPopupDelete" onclick="deletePopup();" value="<bean:message bundle="public" key="button.delete" />"/>
	    	<input type="button" class="reset hide" name="btnPopupReset" id="btnPopupReset" onclick="resetPopup();" value="<bean:message bundle="public" key="button.reset" />"/>
	    </div>
        <html:form action="employeeContractSBAction.do?proc=modify_objects_popup" styleClass="employeeContractSB_form">
        	<%= BaseAction.addToken( request ) %>
			<input type="hidden" name="selectedIds" id="selectedIds" value="" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<fieldset>
				<ol class="auto hide" id="applyToAllHeaderOL">
					<li>
						<label id="sbSolutionIdLabel">�Ƿ��޸�<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>���籣��������Ϣ��</label>
						<input type="checkbox" id="applyToAllHeader" name="applyToAllHeader" value="1" checked="checked"/>
					</li>
				</ol>
				<ol class="hide" id="noticeHeaderOL">
					<li>
					    <span class="highlight">ע�⣺ <br/>  1�������ѡ���޸�ѡ��<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>�Ķ�Ӧ�籣�����𷽰����͵��������ݡ�<br/>  2����<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>�����ڶ�Ӧ�籣�����𷽰����������򴴽��������籣�����𷽰�����ѡ���Ƿ�ѡ��Ӱ�촴������<br/> 3����<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>���ڶ�Ӧ�籣�����𷽰��������ݣ��ٰ������桱ֻ�޸���ֵ����;�ڰ����ύ���������ѡ���籣����������״̬���С��ӱ������˱���������</span>
					</li>
				</ol>
				<ol class="auto hide">
					<li>
						<label>������ϢID</label><span><html:text property="contractId" styleClass="manageEmployeeContractSB_contractId"></html:text></span>
						<input type="hidden" class="manageEmployeeContractSB_encodedContractId" name="encodedContractId" id="manageEmployeeContractSB_encodedContractId" value="" />
					</li>
					<li>
						<label>����ID</label><span><html:text property="employeeSBId" styleClass="manageEmployeeContractSB_employeeSBId"></html:text></span>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.solution" /><em> *</em></label>
						<html:select property="sbSolutionId" styleClass="manageEmployeeContractSB_sbSolutionId">
							<html:optionsCollection property="solutions" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.vendor" /></label> 
						<html:select property="vendorId" styleClass="manageEmployeeContractSB_vendorId">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
					<li>
						<label><bean:message bundle="business" key="business.vendor.service" /></label> 
						<html:select property="vendorServiceId" styleClass="manageEmployeeContractSB_vendorServiceId">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.start.date" /></label> 
						<input type="hidden" id="tempStartDate" value=""/>
						<input name="startDate" class="manageEmployeeContractSB_startDate  Wdate " id="manageEmployeeContractSB_startDate" onfocus="WdatePicker({minDate:'#F{ $dp.$D(\'tempStartDate\')}', maxDate:'#F{ $dp.$D(\'manageEmployeeContractSB_endDate\')}'})" type="text" maxlength="10">
					</li>
					<li>
						<label><bean:message bundle="sb" key="sb.end.date" /></label> 
						<input type="hidden" id="tempEndDate" value=""/>
						<input name="endDate" class="manageEmployeeContractSB_endDate  Wdate " id="manageEmployeeContractSB_endDate" onfocus="WdatePicker({minDate:'#F{ $dp.$D(\'manageEmployeeContractSB_startDate\')}', maxDate:'#F{ $dp.$D(\'tempEndDate\')}'})" type="text" maxlength="10">
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.employee.need.medical.card" /></label>
						<html:select property="needMedicalCard" styleClass="manageEmployeeContractSB_needMedicalCard">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
					<li>
						<label><bean:message bundle="sb" key="sb.employee.medical.number" /></label>
						<html:text property="medicalNumber" maxlength="15" styleClass="manageEmployeeContractSB_medicalNumber"></html:text>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.employee.need.sb.card" /></label> 
						<html:select property="needSBCard" styleClass="manageEmployeeContractSB_needSBCard">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
					<li>
						<label><bean:message bundle="sb" key="sb.employee.sb.number" /></label>
						<html:text property="sbNumber" maxlength="15" styleClass="manageEmployeeContractSB_sbNumber"></html:text>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.employee.fund.number" /></label>
						<html:text property="fundNumber" maxlength="15" styleClass="manageEmployeeContractSB_fundNumber	"></html:text>
					</li>
					<li>
						<label><bean:message bundle="public" key="public.status" /></label> 
						<html:select property="status" styleClass="manageEmployeeContractSB_status">
							<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto hide">
					<li>
						<label><bean:message bundle="public" key="public.description" /></label> 
						<html:textarea property="description" styleClass="manageEmployeeContractSB_description"></html:textarea>
					</li>
				</ol>
				<ol class="auto hide" id="applyToAllDetailOL">
					<li>
						<label id="solutionDetailIdLabel">�Ƿ��޸�<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>���籣��������ϸ��Ϣ��</label>
						<input type="checkbox" id="applyToAllDetail" name="applyToAllDetail" value="1" checked="checked"/>
					</li>
				</ol>
				<ol class="hide" id="noticeDetailOL">
					<li>
					    <span class="highlight">ע�⣺ <br/>  1�������ѡ���޸�ѡ��<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>�Ķ�Ӧ�籣�����𷽰����͵���ϸ��Ϣ���ݣ�����ֻ�޸ķ�����Ϣ�����޸���ϸ��Ϣ��<br/>  2����<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>�����ڶ�Ӧ�籣�����𷽰����������򴴽��������籣�����𷽰�ͬʱ�����ɷ�������ϸ��Ϣ����ѡ���Ƿ�ѡ��Ӱ�����ɣ���<br/></span>
					</li>
				</ol>
			</fieldset>
			<div id="special_info">
				<!-- �籣������Detail��Ϣ -->
				<jsp:include page="/popup/extend/listEmployeeContractSBDetail.jsp"></jsp:include>
			</div>			
		</html:form >
    </div>
     
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	(function($) {
		// ���ع�Ӧ��������
		loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>');
		// ���ع�Ӧ�̷���������
		loadHtml('.manageEmployeeContractSB_vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>&serviceId=<bean:write name="employeeContractSBForm" property="vendorServiceId"/>');
		// �����籣��������ϸTabҳ
		loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&employeeSBId=<bean:write name="employeeContractSBForm" property="employeeSBId"/>&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>', true, null);
		
		// �籣�����𷽰�Change�¼�
		$(".manageEmployeeContractSB_sbSolutionId").change(function(){
			$('#special_info').removeClass('hide');
			if(getPopSubAction() != 'viewObject') {
				loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&contractId=' + $('.manageEmployeeContractSB_contractId').val() + '&employeeSBId=<bean:write name="employeeContractSBForm" property="employeeSBId"/>&sbSolutionId=' + $(".manageEmployeeContractSB_sbSolutionId").val(), false, null);
			}
			// ���ع�Ӧ��������
			 loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + $(".manageEmployeeContractSB_sbSolutionId").val() + '&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>', null, '$(".manageEmployeeContractSB_sbSolutionIdvendorId").change();');
		});
		
		// ��Ӧ��change�¼����ع�Ӧ�̷���
		$(".manageEmployeeContractSB_vendorId").change(function(){
			// ���ع�Ӧ�̷���������
			loadHtml('.manageEmployeeContractSB_vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=' + $('.manageEmployeeContractSB_sbSolutionId').val() + '&vendorId=' + $('.manageEmployeeContractSB_vendorId').val() + '&serviceId=<bean:write name="employeeContractSBForm" property="vendorServiceId"/>',false,function(){
				if($('.manageEmployeeContractSB_vendorServiceId option').size()==2){
					$('.manageEmployeeContractSB_vendorServiceId option').eq(1).attr('selected','seleccted');
				}
			});
		});
		
		// ����籣�����𷽰���� - ��Ҫ��ʾ�ύ��ť
		if( getPopSubAction() == 'createObject' ){
			$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
		}
		
		// ��ʼ������Button��Disable Form
		hideButton();

		// �ӱ��ύ���˱��ύ�����˱�״̬���ܱ༭||���ڹ��������ܱ༭
		if( '<bean:write name="employeeContractSBForm" property="workflowId" />' == '' && '<bean:write name="employeeContractSBForm" property="status" />' != '1' && '<bean:write name="employeeContractSBForm" property="status" />' != '4' && '<bean:write name="employeeContractSBForm" property="status" />' != '6'){
			$('#btnPopupEdit').show();
		}
		
		disableForm('employeeContractSB_form');

		// ���水ť����¼�
		$('#btnPopupSave').click( function () {		
	    	// ��մ���
	    	cleanAllError();
	    	
			var status = $('.manageEmployeeContractSB_status').val();
			var selectedIds =  $('.list_form input[id="selectedIds"]').val();
			var contractId = $('.manageEmployeeContractSB_contractId').val();
			var encodedContractId = $('.manageEmployeeContractSB_encodedContractId').val();
			
			// ����ǲ鿴
			if(getPopSubAction() == 'viewObject'){
				enableForm('employeeContractSB_form');
				// ���籣�����𷽰��������޸�
				$('.manageEmployeeContractSB_sbSolutionId').attr('disabled', true);
				// ״̬�������޸�
				$('.manageEmployeeContractSB_status').attr('disabled', true);
				// �޸�subActionΪ�޸�
				$('.employeeContractSB_form input[id="subAction"]').val('modifyObject');
				// ��ʾ���Btn
				$('#btnPopupSave').removeClass('hide');
				$('#btnPopupReset').removeClass('hide');
				$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
				
				// ����ǡ����籣������״̬��ʾ���ӱ��ύ��
				if(status == 0){
					$('#btnPopupSubmit').removeClass('hide');
					$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
					$('.manageEmployeeContractSB_sbSolutionId').removeAttr('disabled');
					$('#manageEmployeeContractSB_endDate').attr('disabled', true);
				}
				// �����ӱ��������������� ��ʾ���˱��ύ��
				else if(status == 2 || status == 3){
					$('#btnPopupSubmit').removeClass('hide');
					$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit.to.stop.claim" />');
					if(status == 2){
						$('#manageEmployeeContractSB_startDate').attr('disabled', false);
					}else{
						$('#manageEmployeeContractSB_startDate').attr('disabled', true);
					}
				}
				// �������걨�˱���
				else if(status == 5){
					$('#manageEmployeeContractSB_startDate,#manageEmployeeContractSB_sbSolutionId').attr('disabled', true);
				}
				// ���˱�����༭��ť����¼�ʱ��1�����ر��水ť��2����ʾ�ӱ��ύ��ť��3��������ڣ��˱�����disabled����4�����°����ڿؼ����ӱ����ڲ���С��֮ǰ���˱����ڣ�
				else if(status == 6){
// 					$('#btnPopupSubmit').addClass('hide');
// 					$('#manageEmployeeContractSB_startDate').attr('disabled', true);
// 					$('#manageEmployeeContractSB_endDate').attr('disabled', true);
					
					$('#btnPopupSave').hide();
					$('#btnPopupSubmit').show();
					$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
					
					$('#manageEmployeeContractSB_endDate, #sbSolutionId').attr('disabled', 'disabled');
					
					var endDate = getAppointDate($('#manageEmployeeContractSB_endDate').val(), 1);
					
					// ȡ���ӱ�����onfocus�¼�
					$('#manageEmployeeContractSB_startDate').val(endDate);
					$('#manageEmployeeContractSB_startDate').attr('onfocus',"WdatePicker({minDate:'"+ endDate +"'})");
					$('#manageEmployeeContractSB_endDate').val("");
				}

			}
			// ����ǵ�������
			else if(getPopSubAction() == 'createObject'){
					
				if(!checkPopupValue()){
			    	enableForm('employeeContractSB_form');
					// �ύģ̬��
					submitForm('employeeContractSB_form', 'modifyObject', null, null, null, 'search-results', null, hidePopup());
				}
					
			}
			// ����ǵ����������޸�
			else if(getPopSubAction() == 'modifyObject'){
					
				if(!checkPopupValue()){
			    	enableForm('employeeContractSB_form');
					// �ύģ̬��
					submitForm('employeeContractSB_form', 'modifyObject', null, null, null, 'search-results', null, hidePopup());
				}
				
			}
		});
	})(jQuery);

	// ����ģ̬����_���(����)
	function addEmployeeContractSB(contractId, encodedContractId){
		$('#special_info').addClass('hide');
		$.ajax({
			url:'employeeContractAction.do?proc=get_object_json&contractId=' + contractId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('#manageEmployeeContractSB_startDate').val(data.startDate);
				$('#tempStartDate').val(data.startDate);

				// ����Button
				hideButton();
				enableForm('employeeContractSB_form');
				// ״̬�������޸�
				$('.manageEmployeeContractSB_status').attr('disabled', true);
				
				// ����ContractId�����EmployeeSB ID
				$('.manageEmployeeContractSB_contractId').val(contractId);
				$('.manageEmployeeContractSB_employeeSBId').val('');
				
				// �޸�Action		
				$('.employeeContractSB_form').attr('action', 'employeeContractSBAction.do?proc=modify_object_popup');
				// ��ʾ�����桱�͡����á�button
				$('#btnPopupSave').removeClass('hide');
				$('#btnPopupReset').removeClass('hide');
				// ��ʾ���ӱ��ύ��button
				$("#btnPopupSubmit").show();
				$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
				// manageEmployeeContractSB_endDate���ɱ༭
				$('.modal-body #manageEmployeeContractSB_endDate').attr('disabled', true);
				// subActionΪ����
				$('.employeeContractSB_form input[id="subAction"]').val('createObject');
				// �����籣������������
				loadSolutionHeaderOptions(encodedContractId, null, false);
			}
		});
	};
	
	// ����ģ̬����_����(����)
	function addEmployeeContractSBs(){
		$('#special_info').removeClass('hide');
		var selectedIds =  $('.list_form input[id="selectedIds"]').val();
		
		// �Ƴ�CheckBox����
		$('#applyToAllHeader').removeAttr('checked');
		$('#applyToAllDetail').removeAttr('checked');
		
		// ���δѡ���¼
		if(selectedIds == null || selectedIds.trim() == ''){
			alert("��ѡ��<logic:equal name='role' value='1'>��Ա</logic:equal><logic:equal name='role' value='2'>Ա��</logic:equal>��¼��");
		}else{
			// ����Popup SelectedIdsֵ
			$('.employeeContractSB_form input[id="selectedIds"]').val(selectedIds);
			// ����Button
			hideButton();
			enableForm('employeeContractSB_form');
			// ״̬�������޸�
			$('.manageEmployeeContractSB_status').attr('disabled', true);
			
			// �޸�Button
			$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
			$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit" />');
			// ���Contract ID ��EmployeeSB ID
			$('.manageEmployeeContractSB_contractId').val('');
			$('.manageEmployeeContractSB_employeeSBId').val('');
			
			// �޸�Action		
			$('.employeeContractSB_form').attr('action', 'employeeContractSBAction.do?proc=modify_objects_popup');
			// ��ʾ�����桱�����ύ���͡����á�button
			$('#btnPopupSave').removeClass('hide');
			$('#btnPopupSubmit').removeClass('hide');
			$('#btnPopupReset').removeClass('hide');
			// ��ʾ��ʾ���͸�ѡ��
			$('#applyToAllHeaderOL').removeClass('hide');
			$('#applyToAllDetailOL').removeClass('hide');
			$('#noticeHeaderOL').removeClass('hide');
			$('#noticeDetailOL').removeClass('hide');
			// subActionΪ����
			$('.employeeContractSB_form input[id="subAction"]').val("modifyObject");

			// �����籣������������
			loadSolutionHeaderOptions('', '', true);
		}
	}
	
	// ����ģ̬����_�˱�
	function rollbackEmployeeContractSB(employeeSBId){
		$('#special_info').removeClass('hide');
		// ����Button
		hideButton();
		enableForm('employeeContractSB_form');
		// ״̬�������޸�
		$('.manageEmployeeContractSB_status').attr('disabled', true);
		// ���������޸�
		$('.manageEmployeeContractSB_sbSolutionId').attr('disabled', true);
		
		// �޸�Action
		$('.employeeContractSB_form').attr('action', 'employeeContractSBAction.do?proc=modify_object_popup');
		
		$.ajax({
			url:'employeeContractSBAction.do?proc=get_object_json&employeeSBId=' + employeeSBId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractSB_contractId').val(data.contractId);
				$('.manageEmployeeContractSB_encodedContractId').val(data.encodedContractId);
				$('.manageEmployeeContractSB_employeeSBId').val(data.employeeSBId);
				$('.manageEmployeeContractSB_sbSolutionId').val(data.sbSolutionId);
				$('.manageEmployeeContractSB_vendorId').val(data.vendorId);
				$('.manageEmployeeContractSB_vendorServiceId').val(data.vendorServiceId);
				$('.manageEmployeeContractSB_needMedicalCard').val(data.needMedicalCard);
				$('.manageEmployeeContractSB_medicalNumber').val(data.medicalNumber);
				$('.manageEmployeeContractSB_needSBCard').val(data.needSBCard);
				$('.manageEmployeeContractSB_sbNumber').val(data.sbNumber);
				$('.manageEmployeeContractSB_fundNumber').val(data.fundNumber);
				$('.manageEmployeeContractSB_description').val(data.description);
				$('.manageEmployeeContractSB_status').val(data.status);
				$('#manageEmployeeContractSB_startDate').val(data.startDate);
				$('.modal-body #manageEmployeeContractSB_endDate').val(data.endDate);

				// �����籣������������
				loadSolutionHeaderOptions(data.encodedContractId, data.sbSolutionId, true);
				// ���ع�Ӧ��������
				loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + data.sbSolutionId + '&vendorId=' + data.vendorId);
				// ���ع�Ӧ�̷���������
				loadHtml('.manageEmployeeContractSB_vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=' + data.sbSolutionId + '&vendorId=' + data.vendorId + '&serviceId=' + data.vendorServiceId);
				// �����籣��������ϸTabҳ
				loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&employeeSBId=' + data.employeeSBId + '&sbSolutionId=' + data.sbSolutionId, true, null);
			}
		});
		
		// �޸�Button
		$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
		// �޸�subActionΪ�޸�
		$('.employeeContractSB_form input[id="subAction"]').val('modifyObject');
		// ��ʾ�����桱�͡����á�button
		$('#btnPopupSave').removeClass('hide');
		$('#btnPopupReset').removeClass('hide');
		// ��ʾ���˱��ύ��
		$('#btnPopupSubmit').removeClass('hide');
		$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit.to.stop.claim" />');
		// manageEmployeeContractSB_startDate���ɱ༭
		$('#manageEmployeeContractSB_startDate').attr('disabled', true);
		// ��ʾģ̬��
		showPopup();
	}
	
	// ����ģ̬����_�޸�
	function modifyEmployeeContractSB(employeeSBId){
		$('#special_info').removeClass('hide');
		disableForm('employeeContractSB_form');
		// ����Button
		hideButton();
		
		// �޸�SubAction
		$('.employeeContractSB_form input[id="subAction"]').val('viewObject');
		// �޸�Action
		$('.employeeContractSB_form').attr('action', 'employeeContractSBAction.do?proc=modify_object_popup');

		$.ajax({
			url:'employeeContractSBAction.do?proc=get_object_json&employeeSBId=' + employeeSBId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractSB_contractId').val(data.contractId);
				$('.manageEmployeeContractSB_encodedContractId').val(data.encodedContractId);
				$('.manageEmployeeContractSB_employeeSBId').val(data.employeeSBId);
				$('.manageEmployeeContractSB_sbSolutionId').val(data.sbSolutionId);
				$('.manageEmployeeContractSB_vendorId').val(data.vendorId);
				$('.manageEmployeeContractSB_vendorServiceId').val(data.vendorServiceId);
				$('.manageEmployeeContractSB_needMedicalCard').val(data.needMedicalCard);
				$('.manageEmployeeContractSB_medicalNumber').val(data.medicalNumber);
				$('.manageEmployeeContractSB_needSBCard').val(data.needSBCard);
				$('.manageEmployeeContractSB_sbNumber').val(data.sbNumber);
				$('.manageEmployeeContractSB_fundNumber').val(data.fundNumber);
				$('.manageEmployeeContractSB_description').val(data.description);
				$('.manageEmployeeContractSB_status').val(data.status);
				$('#manageEmployeeContractSB_startDate').val(data.startDate);
				$('.modal-body #manageEmployeeContractSB_endDate').val(data.endDate);
				
				// ���ӱ��ύ�������˱��ύ�� ֻ�ܿ��������޸�
				if(data.status == 1 || data.status == 4){
					hideButton();
				}
				else{
					$('#btnPopupSave').val('<bean:message bundle="public" key="button.edit" />');
					$('#btnPopupSave').show();
					
					$('#btnPopupSubmit').hide();
				}

				// �����籣������������
				loadSolutionHeaderOptions(data.encodedContractId, data.sbSolutionId, false);
				// ���ع�Ӧ��������
				loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + data.sbSolutionId + '&vendorId=' + data.vendorId);
				// ���ع�Ӧ�̷���������
				loadHtml('.manageEmployeeContractSB_vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=' + data.sbSolutionId + '&vendorId=' + data.vendorId + '&serviceId=' + data.vendorServiceId);
				// �����籣��������ϸTabҳ
				loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&employeeSBId=' + data.employeeSBId + '&sbSolutionId=' + data.sbSolutionId, true, null);
			}
		});
		
	}
	
	// ɾ���籣�����𷽰�
	function deleteEmployeeContractSB(employeeSBId){
		if(confirm("ȷ��ɾ��������¼��")){
			// ע��SelectedIdsֵ
			$('.employeeContractSB_form input[id="selectedIds"]').val(employeeSBId);
			// �ύ
			submitForm('employeeContractSB_form', 'deleteObjects', null, null, null, 'search-results', "employeeContractSBAction.do?proc=list_object", hidePopup());
		}
	}
	
	// ���ύ����ť
	function submitPopup(){
		// ��ͬʱ��֤���������
		if(!checkPopupValue() && !checkDateValue()){
			// �ύģ̬��
			submitForm('employeeContractSB_form', 'submitObject', null, null, null, 'search-results', null, hidePopup());
		}
	}

	// ��������Ƿ���Ч
	function checkDateValue(){
		
		var status = $('.manageEmployeeContractSB_status').val();
		var contractId = $('.manageEmployeeContractSB_contractId').val();
		var flag = 0;
		
		// �����������������֤���ӱ����ڡ������˱����ڡ�
		if(!contractId){
			flag = flag + validate("manageEmployeeContractSB_startDate", true, null, 0, 0);
			flag = flag + validate("manageEmployeeContractSB_endDate", true, null, 0, 0);
		}
		// ����ǡ����籣������(�ӱ��ύ) - �ӱ����ڲ���Ϊ��
		else if(status == 0){
			flag = flag + validate("manageEmployeeContractSB_startDate", true, null, 0, 0);
		}
		// ����ǡ����ӱ���������������(�˱��ύ) - �˱����ڲ���Ϊ��
		else if(status == 2 || status == 3 ){
			flag = flag + validate("manageEmployeeContractSB_endDate", true, null, 0, 0);
		}
		
		return flag;
	}

	// ���ģ̬������ֵ(����Detail��Ϣ)�Ƿ���Ч
	function checkPopupValue(){
		var flag = 0;
		flag = flag + validate("manageEmployeeContractSB_sbSolutionId", true, "select", 0, 0);
		flag = flag + validate("manageEmployeeContractSB_startDate", true, null, 0, 0);
		// ���Header��Ϣ
		flag = flag + validateVendorService();
		// ���Detail��Ϣ
		flag = flag + validateBase();
		return flag;
	}

	// ��֤��Ӧ�̷���������
	function validateVendorService(){
		cleanError('manageEmployeeContractSB_vendorServiceId');
		if($('.manageEmployeeContractSB_vendorId').val()!=0 && $('.manageEmployeeContractSB_vendorServiceId').val()==0 ){
			addError('manageEmployeeContractSB_vendorServiceId', '��ѡ��');
			return 1;
		}
		return 0;
	}
	
	// ��֤����
	function validateBase(){
		var error = 0;
		$(':input[maxvalue][minvalue]').each(function(){
			if( !(/^[0-9]+(.[0-9]+)?$/.test($.trim($(this).val()))) ){
				//��ʽ��֤����
				$(this).addClass("error");
				error++;
			}else {
				if( parseFloat($(this).attr('minvalue')) <= parseFloat($(this).val()) && parseFloat($(this).val()) <= parseFloat($(this).attr('maxvalue'))){
					//��֤ͨ��
					$(this).removeClass("error");
				}else{
					//��֤ʧ��
					$(this).addClass("error");
					error++;
				}
			}
		});
		
		return error;
	};
	
	// ������������
	function resetPopup(){
		$('.manageEmployeeContractSB_vendorId').val('0');
		$('.manageEmployeeContractSB_vendorServiceId').val('0');
		$('.manageEmployeeContractSB_needMedicalCard').val('0');
		$('.manageEmployeeContractSB_medicalNumber').val('');
		$('.manageEmployeeContractSB_needSBCard').val('0');
		$('.manageEmployeeContractSB_sbNumber').val('');
		$('.manageEmployeeContractSB_fundNumber').val('');
		if(!$('#manageEmployeeContractSB_startDate').attr('disabled')){
			$('#manageEmployeeContractSB_startDate').val('');
		}
		if(!$('#manageEmployeeContractSB_endDate').attr('disabled')){
			$('#manageEmployeeContractSB_endDate').val('');
		}
	};
	
	// ��ʾ������
	function showPopup( ){
		$('#employeeContractSBModalId').removeClass('hide');
    	$('#shield').show();
    	
    	// ��մ���
    	cleanAllError();
	};

	// ���ص�����
	function hidePopup(){
		$('#employeeContractSBModalId').addClass('hide');
    	$('#shield').hide();
    	link('employeeContractSBAction.do?proc=list_object');
	};
	
	// ��մ���
	function cleanAllError(){
		cleanError('manageEmployeeContractSB_sbSolutionId');
		cleanError('manageEmployeeContractSB_startDate');
		cleanError('manageEmployeeContractSB_endDate');
		cleanError('manageEmployeeContractSB_vendorServiceId');
	};
	
	// ����Button����ʾ��Ϣ
	function hideButton(){
		// ���ذ�ť
		$('#btnPopupSave,#btnPopupSubmit,#btnPopupDelete,#btnPopupReset').addClass('hide');
		// ����ѡ��
		$('#applyToAllHeaderOL,#applyToAllDetailOL,#noticeHeaderOL,#noticeDetailOL').addClass('hide');
	};
	
	// ��ȡ��ǰSubAction
	function getPopSubAction(){
		return $('.employeeContractSB_form input[id="subAction"]').val();
	};
	
	// �����籣������������
	function loadSolutionHeaderOptions(contractId, sbSolutionId, integrityFlag){
		// ���������������
		if(integrityFlag){
			loadHtml('.manageEmployeeContractSB_sbSolutionId', 'clientOrderSBAction.do?proc=list_object_options_ajax&contractId=' + contractId + '&sbSolutionId=' + sbSolutionId, null, showPopup());
		}
		// ֻ����δ��ӵ�
		else{
			loadHtml('.manageEmployeeContractSB_sbSolutionId', 'employeeContractSBAction.do?proc=list_object_options_manage_ajax&contractId=' + contractId + '&sbSolutionId=' + sbSolutionId, null, showPopup());
		}
	};

	function getAppointDate( date, appoint ){
		var newDate = new Date(date);
		var hs = newDate.getTime() + parseInt(appoint) * 1000*60*60*24;
		var returnDate = new Date();
		returnDate.setTime(hs);
		
		var year = returnDate.getFullYear();
		var month = returnDate.getMonth() + 1;
		var day = returnDate.getDate();
		
		if( month < 10){
			month = "0" + month;
		}
		
		if( day < 10){
			day = "0" + day;
		}
		
		return year + "-" + month + "-" + day;
	};
	
	// Esc�����¼� - ���ص�����
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	hidePopup();
	    } 
	});
</script>