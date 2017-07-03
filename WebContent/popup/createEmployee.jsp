<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<script type="text/javascript" src="js/idCard.js"></script>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="createEmployeeModalId">
    <div class="modal-header" id="createEmployeeHeader" style="cursor:move;"> 
        <a class="close" data-dismiss="modal" onclick="$('#createEmployeeModalId').addClass('hide');$('#shield').hide();">��</a>
        <label>
        	<logic:equal value="2" name="role"><%=request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "Ա��" : "Employee" %></logic:equal>
        	<logic:notEqual value="2" name="role"><%=request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "��Ա" : "Employee" %></logic:notEqual>
        	<bean:message bundle="public" key="link.quick.create" />
        </label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnAdd" id="btnAdd" value="<bean:message bundle="public" key="button.save" />" onclick="addEmployee();" />
	    	<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetEmployeeCreate()" value="<bean:message bundle="public" key="button.reset" />" />
	    </div>
        <html:form action="employeeAction.do?proc=add_object_ajax" styleClass="addEmployee_form">
			<input type="hidden" name="sortColumn" id="sortColumn" value="" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="" />
			<input type="hidden" name="page" id="page" value="" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<input type="hidden" name="orderId" id="orderId" value="" />
			<!-- ���ٴ�����Ա��״̬Ϊ��ְ -->
			<input type="hidden" name="status" id="status" value="1" />
			<fieldset>
				<ol class="auto">
					<li>
						<label>
							<logic:equal value="2" name="role"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
        					<logic:notEqual value="2" name="role"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:notEqual>
							<em> *</em>
						</label> 
						<input type="text" name="nameZH" maxlength="25" class="addEmployee_nameZH" />
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.vendor.payment.certificate.number" /></label>
						<input type="hidden" name="certificateType" value="1"><!-- ֤������ ���֤ -->
						<input type="text" name="certificateNumber" maxlength="18" class="addEmployee_certificateNumber" />
					</li>
					<li>
						<label><bean:message bundle="business" key="business.vendor.payment.residency.type" /></label>
						<%=KANUtil.getSelectHTML(KANUtil.getMappings( request.getLocale(), "sys.sb.residency" ),"residencyType","addEmployee_residencyType",null,null,null) %>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.base.salary" /></label> 
						<input type="text" name="salaryBase" maxlength="10" class="addEmployee_salaryBase" />
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.vendor.payment.sb.solution" /></label>
						<select id="sbItem" name="sbItem" class="addEmployee_sbItem"></select>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.vendor.payment.base.company" /></label> 
						<input type="text" name="baseCompany" maxlength="10" class="addEmployee_baseCompany" />
					</li>
					<li>
						<label><bean:message bundle="business" key="business.vendor.payment.base.personal" /></label> 
						<input type="text" name="basePersonal" maxlength="10" class="addEmployee_basePersonal" />
					</li>
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.cb.solution" /></label>
						<select id="cbItem" name="cbItem" class="addEmployee_cbItem"></select>
					</li>
				</ol>
			</fieldset>
		</html:form >
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// ����
	function resetEmployeeCreate(){
		$('.addEmployee_nameZH').val('');
		$('.addEmployee_certificateNumber').val('');
		$('.addEmployee_residencyType').val('0');
		$('.addEmployee_salaryBase').val('');
		$('.addEmployee_sbItem').val('0');
		$('.addEmployee_baseCompany').val('');
		$('.addEmployee_basePersonal').val('');
		$('.addEmployee_cbItem').val('0');
	};
	
	// "����"�¼�
	function addEmployee(){
		if(validate_popup_add_employee_form()==0){
			$(".addEmployee_form #orderId").val($(".manage_primary_form #orderId").val());
			$(".addEmployee_form").submit();
		}
	};
	
	function validate_popup_add_employee_form(){
		var flag = 0;
		
		flag = flag + validate('addEmployee_nameZH', true, 'common', 100, 0, 0, 0);
		flag = flag + validate('addEmployee_salaryBase', false, 'numeric', 50, 0, 0, 0);
		flag = flag + validate('addEmployee_baseCompany', false, 'numeric', 50, 0, 0, 0);
		flag = flag + validate('addEmployee_basePersonal', false, 'numeric', 50, 0, 0, 0);
		flag = flag + validateIDCard();
		return flag;
	};
	
	// ���֤��֤
	function validateIDCard(){
		// ɾ��������������
		$(".addEmployee_certificateNumber_error").remove();
		// ֤������Ϊ���֤ ���� �������ݲ�Ϊ�ղ���֤
		if( $.trim($('.addEmployee_certificateNumber').val()) != ''){
			var card = new clsIDCard($('.addEmployee_certificateNumber').val());
// 			if(!card.IsValid()){
			if(card.GetError()){
				addError('addEmployee_certificateNumber', card.GetError());
				return 1;
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	};
	
	// ����ģ̬����
	function popupEmployeeAdd(){
		if(validate('orderId', true, 'numeric', 9, 9, 0, 0) > 0){
			return;
		};
		
		$('#createEmployeeModalId').removeClass('hide');
    	$('#shield').show();
    	
    	// �����籣������������
		loadHtml('.addEmployee_sbItem', 'clientOrderSBAction.do?proc=list_object_options_byOrderHeaderId_ajax&orderHeaderId=' + $('#orderId').val(), null, function (){
			if($('.addEmployee_sbItem option').size() == 2){
				$('.addEmployee_sbItem option').eq(1).attr('selected', true);
			};
		});
    	
		// �����̱�������
		loadHtml('.addEmployee_cbItem', 'clientOrderCBAction.do?proc=list_object_options_byOrderHeaderId_ajax&orderHeaderId=' + $('#orderId').val(), null, function (){
			if($('.addEmployee_cbItem option').size() == 2){
				$('.addEmployee_cbItem option').eq(1).attr('selected', true);
			};
		});    	
		
		$(".addEmployee_certificateNumber").blur(function(){
			// ��֤�Ƿ����
			$.post("employeeAction.do?proc=get_count_byCertificateNumber",{"certificateNumber":$(".addEmployee_certificateNumber").val(),"id":$("#id").val()},function(data){
				cleanError('addEmployee_certificateNumber');
				if(data==0){
				}else{
					addError('addEmployee_certificateNumber','���֤�ѱ�ע��');
					$(".addEmployee_certificateNumber").focus();
				}
			},"text");
			
		});
		
	};
	
	// Esc�����¼� - ���ص�����
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#createEmployeeModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>