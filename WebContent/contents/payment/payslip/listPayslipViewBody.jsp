<%@page import="com.kan.base.web.renders.util.ListRender"%>
<%@page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<!-- Information Search Form -->	
	<%= SearchRender.generateSpecialSearch( request, "JAVA_OBJECT_PAYSLIP", "com.kan.hro.domain.biz.payment.PayslipDTO", "payslipDetailViewForm" ) %>
	 
	<!-- Information Search Result -->
	<%= ListRender.generateSpecialList( request, "com.kan.hro.domain.biz.payment.PayslipDTO" ,"JAVA_OBJECT_PAYSLIP" ) %>
</div>

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/selectBankTemplate.jsp"></jsp:include>
</div>		

<script type="text/javascript">
	(function($) {
		// ���ö����˵�ѡ����ʽ
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Payslip').addClass('selected');
		<logic:equal name="role" value="5">
			$('#menu_salary_Payslip').addClass('current');
        </logic:equal>
		$('#searchDiv').hide();
		<logic:equal name="role" value="5">
			$('#employeeId').parent().remove();
			$('#employeeNameZH').parent().remove();
			$('#employeeNameEN').parent().remove();
			$('#clientId').parent().remove();
			$('#clientNameZH').parent().remove();
			$('#clientNameEN').parent().remove();
			$('#clientNO').parent().remove();
			$('#orderId').parent().remove();
		</logic:equal>
		
		useFixColumn();
		
		// ѡ��ģ�嵼���¼�
		$('#btnConrirm').click( function() {
			$('.close').trigger('click');
	        linkForm('list_form', 'downloadObjects', null, 'fileType=excel&javaObjectName=' + $('#javaObjectName').val() + '&templateType=1&templateId=' + $('#bankTemplateId').val());
		});
		
		// ��������������
		loadHtml('#special_select', 'payslipViewAction.do?proc=load_special_html&monthlyBegin=<bean:write name="payslipDetailViewForm" property="monthlyBegin" />&monthlyEnd=<bean:write name="payslipDetailViewForm" property="monthlyEnd" />&status=<bean:write name="payslipDetailViewForm" property="status" />&orderId=<bean:write name="payslipDetailViewForm" property="orderId" />&entityId=<bean:write name="payslipDetailViewForm" property="entityId" />', false );
	})(jQuery);
	
	// Reset Form
	function resetForm(){
		var currDate = new Date();
		var monthly = currDate.getFullYear() + '/';
		if( parseInt(currDate.getMonth()) + 1 >= 10 ){
			monthly = monthly + (parseInt(currDate.getMonth()) + 1);
		}else{
			monthly = monthly + "0" + (parseInt(currDate.getMonth()) + 1);
		}
		
		$('#searchDiv input[type="text"]').val('');
		$('#searchDiv select').not('#monthlyBegin,#monthlyEnd').val('0');
		$('#monthlyBegin,#monthlyEnd').val(monthly)
	};
	<logic:equal name="role" value="5">
	 function popupSelectTemplate(){
	  linkForm('list_form', 'downloadObjects', 'payslipViewAction.do?proc=list_object', 'fileType=excel&javaObjectName=com.kan.hro.domain.biz.payment.PayslipDTO&templateType=1');
	 }
	</logic:equal>
	<logic:equal name="role" value="4">
	 function popupSelectTemplate(){
	  linkForm('list_form', 'downloadObjects', 'payslipViewAction.do?proc=list_object', 'fileType=excel&javaObjectName=com.kan.hro.domain.biz.payment.PayslipDTO&templateType=1');
	 }
	</logic:equal>
	
	// ����Apc�ļ�
	function exportApc(){
		if( $('#monthlyBegin').val() != $('#monthlyEnd').val()  ){
			alert('ֻ�ܵ���һ���·ݵ����ݣ������˵��·���ʼ�ͽ�ֹ��');
		}else if( $('#entityId').val() != '91' && $('#entityId').val() != '93' && $('#entityId').val() != '95' && $('#entityId').val() != '99'  ){
			alert('���x���Ռ��w���܌���APC��');
		}else{
			linkForm( 'list_form', 'downloadObjects', null, 'apc=1' );
		}
	};
	
	function checkMonthly(){
		if( compareMonthly($('#monthlyBegin').val(),$('#monthlyEnd').val()) ){
			alert('�˵��·ݽ�ֹ���������˵��·���ʼ��');
		}
	};
	
	// �Ƚ�������monthly����С
	function compareMonthly( startMontly, endMonthly ){
		var oYearBegin = parseInt(startMontly.split('/')[0]);
		var oYearEnd = parseInt(endMonthly.split('/')[0]);
		var oMonthBegin = parseInt(startMontly.split('/')[1]);
		var oMonthEnd =  parseInt(endMonthly.split('/')[1]);
		return oYearBegin*12 + oMonthBegin > oYearEnd*12 + oMonthEnd;
	};
	
	// ʹ�ù̶���
	function useFixColumn(){
		<logic:notEqual name="pagedListHolder" property="holderSize" value="0">
			$("#resultTable").fixTable({
				fixColumn: 2,//�̶�����
				width:$(this).find("thead").outerWidth(true),//��ʾ���
				height: $(this).outerHeight()-$(this).find("tfoot").outerHeight()//��ʾ�߶�
			});
		</logic:notEqual>
	};
</script>
