<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@	page import="com.kan.base.util.CachedUtil"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="employeeContractSBModalId">
    <div class="modal-header" id="employeeContractSBHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#employeeContractSBModalId').addClass('hide');$('#shield').hide();">��</a>
        <label><bean:message bundle="sb" key="sb.vendor.setting" /></label>
    </div>
    
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnPopupSave" id="btnPopupSave" value="<bean:message bundle="public" key="button.edit" />"/>
	    </div>
        <html:form action="employeeContractSBAction.do?proc=modify_object_popup" styleClass="employeeContractSB_form">
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
						<label><bean:message bundle="sb" key="sb.vendor.service" /></label> 
						<html:select property="vendorServiceId" styleClass="manageEmployeeContractSB_vendorServiceId">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.start.date" /></label> 
						<input name="startDate" class="startDate  Wdate " id="startDate" onfocus="WdatePicker({minDate:'2013-11-27',maxDate:'#F{ $dp.$D(\'endDate\') || $dp.$DV(\'2014-11-28\') }'})" type="text" maxlength="10">
					</li>
					<li>
						<label><bean:message bundle="sb" key="sb.end.date" /></label> 
						<input name="endDate" class="endDate  Wdate " id="endDate" onfocus="WdatePicker({maxDate:'2014-11-28',minDate:'#F{ $dp.$D(\'startDate\') || $dp.$DV(\'2013-11-27\') }' })" type="text" maxlength="10">
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
					<li style="display:none">
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
			<div id="special_info" class="hide">
				<!-- �籣������Detail��Ϣ -->
				<jsp:include page="/popup/extend/listVendorSBDetail.jsp"></jsp:include>
			</div>			
		</html:form >
    </div>
     
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	(function($) {

		// �����籣������������
		loadHtml('.manageEmployeeContractSB_sbSolutionId', 'clientOrderSBAction.do?proc=list_object_options_ajax&contractId=<bean:write name="employeeContractSBForm" property="encodedContractId"/>&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>');
		// ���ع�Ӧ��������
		loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>');
		// ���ع�Ӧ�̷���������
		loadHtml('.manageEmployeeContractSB_vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>&serviceId=<bean:write name="employeeContractSBForm" property="vendorServiceId"/>');
		// �����籣��������ϸTabҳ
		loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&employeeSBId=<bean:write name="employeeContractSBForm" property="employeeSBId"/>&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>', true, null);
		
		// �籣�����𷽰�Change�¼�
		$(".manageEmployeeContractSB_sbSolutionId").change(function(){
			if($('.employeeContractSB_form input#subAction').val() != 'viewObject') {
				loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&contractId=<bean:write name="employeeContractSBForm" property="encodedId"/>&employeeSBId=<bean:write name="employeeContractSBForm" property="employeeSBId"/>&sbSolutionId=' + $(".manageEmployeeContractSB_sbSolutionId").val(), false, null);
			}
			
			// ���ع�Ӧ��������
			 loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + $(".manageEmployeeContractSB_sbSolutionId").val() + '&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>', null, null);
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
		
		// ��ʼ��Disable Form
		disableForm('employeeContractSB_form');

		// ���水ť����¼�
		$('#btnPopupSave').click( function () {
	    	// ��մ���
	    	cleanAllError();
	    	
			var popupSubAction = $('.employeeContractSB_form input[id="subAction"]').val();
			
			// ����ǲ鿴
			if(popupSubAction == 'viewObject'){
				disableForm('employeeContractSB_form');
				// ��Ӧ�̡���Ӧ�̷��� �ɱ༭
				$('.manageEmployeeContractSB_vendorId').removeAttr('disabled');
				$('.manageEmployeeContractSB_vendorServiceId').removeAttr('disabled');
				// �޸�subActionΪ�޸�
				$('.employeeContractSB_form input[id="subAction"]').val('submitObject');
				// ��ʾ���Btn
				$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
				
			}
			// ����ǵ����������޸�
			else if(popupSubAction == 'submitObject'){
					
				if(!checkPopupValue()){
			    	enableForm('employeeContractSB_form');
					// �ύģ̬��
					submitForm('employeeContractSB_form', 'submitObject', null, null, null, 'search-results', 'employeeContractSBAction.do?proc=modify_object_popup&pageFlag=vendor', hidePopup());
				}
				
			}
		});
	})(jQuery);
	
	// ����ģ̬����_�޸�
	function modifyEmployeeContractSB(employeeSBId){
		disableForm('employeeContractSB_form');
		// �޸�SubAction
		$('.employeeContractSB_form input[id="subAction"]').val('viewObject');
		// �޸�Action
		$('.employeeContractSB_form').attr('action', 'employeeContractSBAction.do?proc=modify_object_popup');
		
		$.ajax({
			url:'employeeContractSBAction.do?proc=get_object_json&employeeSBId=' + employeeSBId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractSB_contractId').val(data.contractId);
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
				$('#startDate').val(data.startDate);
				$('#endDate').val(data.endDate);
				
				$('#btnPopupSave').val('<bean:message bundle="public" key="button.edit" />');
				// �����籣������������
				loadHtml('.manageEmployeeContractSB_sbSolutionId', 'clientOrderSBAction.do?proc=list_object_options_ajax&contractId=' + data.encodedContractId + '&sbSolutionId=' + data.sbSolutionId);
				// ���ع�Ӧ��������
				loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + data.sbSolutionId + '&vendorId=' + data.vendorId);
				// ���ع�Ӧ�̷���������
				loadHtml('.manageEmployeeContractSB_vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=' + data.sbSolutionId + '&vendorId=' + data.vendorId + '&serviceId=' + data.vendorServiceId);
				// �����籣��������ϸTabҳ
				loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&employeeSBId=' + data.employeeSBId + '&sbSolutionId=' + data.sbSolutionId, true, null);
			}
		});

		// ��ʾģ̬��
		showPopup();
	}

	// ��������Ƿ���Ч
	function checkDateValue(){
		
		var status = $('.manageEmployeeContractSB_status').val();
		var contractId = $('.manageEmployeeContractSB_contractId').val();
		var flag = 0;
		
		// �����������������֤���ӱ����ڡ������˱����ڡ�
		if(!contractId){
			flag = flag + validate("startDate", true, null, 0, 0);
			flag = flag + validate("endDate", true, null, 0, 0);
		}
		// ����ǡ����籣������(�ӱ��ύ) - �ӱ����ڲ���Ϊ��
		else if(status == 0){
			flag = flag + validate("startDate", true, null, 0, 0);
		}
		// ����ǡ����ӱ���������������(�˱��ύ) - �˱����ڲ���Ϊ��
		else if(status == 2 || status == 3 ){
			flag = flag + validate("endDate", true, null, 0, 0);
		}
		
		return flag;
	}

	// ���ģ̬������ֵ(����Detail��Ϣ)�Ƿ���Ч
	function checkPopupValue(){
		var flag = 0;
		flag = flag + validate("manageEmployeeContractSB_sbSolutionId", true, "select", 0, 0);
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

	// ��ʾ������
	function showPopup(){
		$('#employeeContractSBModalId').removeClass('hide');
    	$('#shield').show();
    	
    	// ��մ���
    	cleanAllError();
	}

	// ���ص�����
	function hidePopup(){
		$('#employeeContractSBModalId').addClass('hide');
    	$('#shield').hide();
	}
	
	// ��մ���
	function cleanAllError(){
		cleanError('manageEmployeeContractSB_sbSolutionId');
		cleanError('startDate');
		cleanError('endDate');
		cleanError('manageEmployeeContractSB_vendorServiceId');
	}

	// Esc�����¼� - ���ص�����
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	hidePopup();
	    } 
	});
</script>