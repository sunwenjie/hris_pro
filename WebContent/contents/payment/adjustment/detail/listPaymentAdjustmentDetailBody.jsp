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
			<!-- Include Form JSP 包含Form对应的jsp文件 --> 
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
			<!-- Include Form JSP 包含Form对应的jsp文件 --> 
			<jsp:include page="/contents/payment/adjustment/detail/form/managePaymentAdjustmentDetailForm.jsp" flush="true"></jsp:include>
		</div>																		
		<html:form action="paymentAdjustmentDetailAction.do?proc=list_object" styleClass="listPaymentAdjustmentDetail_form">
			<input type="hidden" name="adjustmentHeaderId" id="adjustmentHeaderId" value="<bean:write name="paymentAdjustmentHeaderForm" property="encodedId"/>"/>
			<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="paymentAdjustmentHeaderForm" property="selectedIds" />" />
			<input type="hidden" name="subAction" id="subAction" value="" />					
		</html:form>	
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

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/searchContract.jsp"></jsp:include>
</div>		
	
<script type="text/javascript">
	(function($) {
		// 初始化菜单样式
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
	    
	    // 提交按钮事件
		$('#btnSubmit').click( function () { 
			if(validate_manage_primary_form() == 0){
				// 更改当前Form的SubAction
				if($('.managePaymentAdjustmentHeader_form input#subAction').val() != 'createObject'){
					$('.managePaymentAdjustmentHeader_form').attr('action', 'paymentAdjustmentHeaderAction.do?proc=submit_object');
				}
				$('.managePaymentAdjustmentHeader_form input#subAction').val('submitObject');
		    	enableForm('managePaymentAdjustmentHeader_form');
		    	submit('managePaymentAdjustmentHeader_form');
			}
		});
	    
	    // 提交按钮事件
		$('#btnIssue').click( function () { 
			if(validate_manage_primary_form() == 0){
				// 更改当前Form的SubAction
				if($('.managePaymentAdjustmentHeader_form input#subAction').val() != 'createObject'){
					$('.managePaymentAdjustmentHeader_form').attr('action', 'paymentAdjustmentHeaderAction.do?proc=issue_object');
				}
				$('.managePaymentAdjustmentHeader_form input#subAction').val('issueObject');
		    	enableForm('managePaymentAdjustmentHeader_form');
		    	submit('managePaymentAdjustmentHeader_form');
			}
		});
		
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
		
		// 工资调整主表保存、编辑点击事件
		$('#btnEdit').click(function() {
		    if( getSubAction() == 'viewObject') {
		    	// Enable Form
		        enableForm('managePaymentAdjustmentHeader_form');
		    	// 更改SubAction
		        $('.managePaymentAdjustmentHeader_form input#subAction').val('modifyObject');
		    	// 更改Action
		        $('.managePaymentAdjustmentHeader_form').attr('action', 'paymentAdjustmentHeaderAction.do?proc=modify_object');
		    	// 更改按钮显示名称
		        $('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		    	// 更改Title
		        $('#pageTitle').html('<bean:message bundle="payment" key="payment.salary.adjustment" /> <bean:message bundle="public" key="oper.edit" />');
		    	// Disable界面按钮
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
		
		// 工资调整主表列表点击事件
		$('#btnList').click(function(){
			if (agreest())
			link('paymentAdjustmentHeaderAction.do?proc=list_object');
		});
		
		// 工资调整从表保存、编辑点击事件
		$('#btnEditPaymentAdjustmentDetail').click(function(){
			if($('.managePaymentAdjustmentHeader_form #adjustmentHeaderId').val() != ''){
				var detailSubAction = $('.managePaymentAdjustmentDetail_form input#subAction').val();
				
				// 添加 
				if( detailSubAction == '' ){
					// 设置默认值
					$('.managePaymentAdjustmentDetail_form .managePaymentAdjustmentDetail_status').val('1');
					// 显示Cancel按钮
					$('#btnCancelPaymentAdjustmentDetail').show();
					// 显示List Detail Form
					$('#detailFormWrapper').show();	
					// 装载从表新建界面
					loadHtml('#detailFormWrapper', 'paymentAdjustmentDetailAction.do?proc=to_objectNew_ajax&adjustmentHeaderId=<bean:write name="paymentAdjustmentHeaderForm" property="encodedId"/>', false );
					// 设置SubAction为新建
					$('.managePaymentAdjustmentDetail_form input#subAction').val('createObject');
					// 修改按钮显示名称
					$('#btnEditPaymentAdjustmentDetail').val('<bean:message bundle="public" key="button.save" />');
				}
				// 查看
				else if( detailSubAction == 'viewObject'){
					// Enable Form
					enableForm('managePaymentAdjustmentDetail_form');
					// 设置SubAction为编辑
					$('.managePaymentAdjustmentDetail_form input#subAction').val('modifyObject');
					// 更改Form Action
	        		$('.managePaymentAdjustmentDetail_form').attr('action', 'paymentAdjustmentDetailAction.do?proc=modify_object');
	        		// 修改按钮显示名称
					$('#btnEditPaymentAdjustmentDetail').val('<bean:message bundle="public" key="button.save" />');
				}
				// 编辑
				else{
					// 在此做添加或修改的验证
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
		
		// 工资调整从表取消点击事件
		$('#btnCancelPaymentAdjustmentDetail').click(function(){
			if(agreest())
			link('paymentAdjustmentDetailAction.do?proc=list_object&adjustmentHeaderId=<bean:write name="paymentAdjustmentHeaderForm" property="encodedId" />');
		});
		
		// 初始化JS
		if ( getSubAction() != 'createObject' ) {
			// Disable Form
		    disableForm('managePaymentAdjustmentHeader_form');
			// 初始化SubAction
		    $('.managePaymentAdjustmentHeader_form input.subAction').val('viewObject');
		 	// 更改按钮显示名称
		    $('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		 	// 更改Title
		   $('#pageTitle').html('<bean:message bundle="payment" key="payment.salary.adjustment" /> <bean:message bundle="public" key="oper.view" />');
			 // 触发ContractId Keyup事件
		    $('#contractId').trigger('keyup');
		    // 显示调整明细
		    $('#paymentAdjustmentDetail-information').show();
		} else if ( getSubAction() == 'createObject' ) {
		 	// Disable界面按钮
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
	
	// 点击超链接，Ajax调用去修改页面
	function paymentAdjustmentDetailModify( adjustmentDetailId ){
		// 显示Cancel按钮
		$('#btnCancelPaymentAdjustmentDetail').show();
		// 显示List Detail Form
		$('#detailFormWrapper').show();	
		// 装载从表编辑界面
		loadHtml('#detailFormWrapper', 'paymentAdjustmentDetailAction.do?proc=to_objectModify_ajax&id=' + adjustmentDetailId, true );
		// 修改按钮显示名称
		$('#btnEditPaymentAdjustmentDetail').val('<bean:message bundle="public" key="button.edit" />');		
	};
	
	// 获取Status
	function getStatus(){
		return $('.managePaymentAdjustmentHeader_form .managePaymentAdjustmentHeader_status').val();
	};
	
	// 获取SubAction
	function getSubAction(){
		return $('.managePaymentAdjustmentHeader_form input#subAction').val();
	};
</script>

