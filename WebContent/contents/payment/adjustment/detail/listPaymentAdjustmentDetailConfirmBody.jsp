<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<!-- PaymentAdjustmentHeader - information -->
<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle"><bean:message bundle="payment" key="payment.salary.adjustment.confirm" /></label>
	        <label class="recordId"> &nbsp; (ID: <bean:write name="paymentAdjustmentHeaderForm" property="adjustmentHeaderId" />)</label>
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
				<kan:auth right="approve" action="HRO_PAYMENT_ADJUSTMENT_CONFIRM">
					<input type="button" class="function" name="btnApprove" id="btnApprove" value="<bean:message bundle="public" key="button.approve" />" />
				</kan:auth>
				<kan:auth right="back" action="HRO_PAYMENT_ADJUSTMENT_CONFIRM">
					<input type="button" class="delete" name="btnRollback" id="btnRollback" value="<bean:message bundle="public" key="button.return" />" />
				</kan:auth>
				<kan:auth right="list" action="HRO_PAYMENT_ADJUSTMENT_CONFIRM">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
			</div>
			<!-- Include Form JSP 包含Form对应的jsp文件 --> 
			<jsp:include page="/contents/payment/adjustment/header/form/managePaymentAdjustmentHeaderForm.jsp" flush="true"></jsp:include>		
	    </div>
	</div>
</div>

<!-- PaymentAdjustmentHeader - information -->
<div class="box" id="paymentAdjustmentDetail-information">		
	<!-- Inner -->	
	<div class="head">
		<label><bean:message bundle="payment" key="payment.salary.adjustment.confirm.detail.search.title" /></label>
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
			<input type="button" class="reset" name="btnCancelPaymentAdjustmentDetail" id="btnCancelPaymentAdjustmentDetail" value="<bean:message bundle="public" key="button.reset" />" style="display: none" />
		</div>	
		<div id="detailFormWrapper"  style="display: none;">
			<!-- Include Form JSP 包含Form对应的jsp文件 --> 
			<jsp:include page="/contents/payment/adjustment/detail/form/managePaymentAdjustmentDetailForm.jsp" flush="true"></jsp:include>
		</div>	
		<logic:notEmpty name="paymentAdjustmentDetailHolder">	
			<html:form action="paymentAdjustmentDetailAction.do?proc=list_object" styleClass="listPaymentAdjustmentDetail_form">
					<input type="hidden" name="adjustmentHeaderId" value="<bean:write name="paymentAdjustmentHeaderForm" property="encodedId"/>"/>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="paymentAdjustmentDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="paymentAdjustmentDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="paymentAdjustmentDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="paymentAdjustmentDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
			</html:form>	
		</logic:notEmpty>
		<div id="tableWrapper">
			<!-- Include table jsp 包含table对应的jsp文件 -->  
			<jsp:include page="/contents/payment/adjustment/detail/table/listPaymentAdjustmentDetailTable.jsp" flush="true"></jsp:include>
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
		// 初始化菜单样式
		$('#menu_salary_Modules').addClass('current');	
		$('#menu_salary_Process').addClass('selected');
		$('#menu_salary_AdjustmentConfirm').addClass('selected');
		
		disableForm('managePaymentAdjustmentHeader_form');
		
		// 绑定服务协议keyup事件
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
								$('.managePaymentAdjustmentHeader_employeeId').val(data.employeeId);
								$('.managePaymentAdjustmentHeader_employeeNameZH').val(data.employeeNameZH);
								$('.managePaymentAdjustmentHeader_employeeNameEN').val(data.employeeNameEN);
								$('.managePaymentAdjustmentHeader_contractIdLI label').append('<a onclick="link(\'clientContractAction.do?proc=to_objectModify&id=' + data.encodedId + '\');" ><img src="images/find.png" title="查看记录" /></a>');
							}else if(data.success == 'false'){
								$('.managePaymentAdjustmentHeader_clientId').val('');
								$('.managePaymentAdjustmentHeader_employeeId').val('');
								$('.managePaymentAdjustmentHeader_employeeNameZH').val('');
								$('.managePaymentAdjustmentHeader_employeeNameEN').val('');
								addError('managePaymentAdjustmentHeader_contractId', '<logic:equal name='role' value='1'>派送信息</logic:equal><logic:equal name='role' value='2'>劳动合同</logic:equal>ID无效');
							}
						 }
					});
				}
			}
		});
		
		// 工资调整主表列表点击事件
		$('#btnList').click(function(){
			if (agreest())
			link('paymentAdjustmentHeaderAction.do?proc=list_object_confirm');
		});
		
		// 工资调整主表批准点击事件
		$('#btnApprove').click(function(){
		    $('.managePaymentAdjustmentHeader_form').attr('action', 'paymentAdjustmentHeaderAction.do?proc=approve_object&selectedIds=' + $('.managePaymentAdjustmentHeader_form #adjustmentHeaderId').val()); 
		    submitForm('managePaymentAdjustmentHeader_form');
		});
		
		// 工资调整主表退回点击事件
		$('#btnRollback').click(function(){
		    $('.managePaymentAdjustmentHeader_form').attr('action', 'paymentAdjustmentHeaderAction.do?proc=rollback_object&selectedIds=' + $('.managePaymentAdjustmentHeader_form #adjustmentHeaderId').val()); 
		    submitForm('managePaymentAdjustmentHeader_form');
		});
		
		// 工资调整从表取消点击事件
		$('#btnCancelPaymentAdjustmentDetail').click(function(){
			if(agreest())
			link('paymentAdjustmentDetailAction.do?proc=list_object_confirm&adjustmentHeaderId=<bean:write name="paymentAdjustmentHeaderForm" property="encodedId" />');
		});
		
		if( getSubAction() == 'viewObject' ){
			$('#contractId').keyup();
		}
	})(jQuery);
	
	// 点击超链接，ajax调用去修改页面
	function paymentAdjustmentDetailModify( detailId ){
		// 显示Cancel按钮
		$('#btnCancelPaymentAdjustmentDetail').show();
		// 显示List Detail Form
		$('#detailFormWrapper').show();	
		// Ajax加载工资调整从表修改页面
		loadHtml('#detailFormWrapper', 'paymentAdjustmentDetailAction.do?proc=to_objectModify_ajax&id=' + detailId, true );
	};
	
	// 获取subAction
	function getSubAction(){
		return $('.managePaymentAdjustmentHeader_form input#subAction').val();
	};
</script>

