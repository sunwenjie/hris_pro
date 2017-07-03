<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final String javaObjectName = "com.kan.hro.domain.biz.sb.SBDTO";
%>

	<!-- Information Manage Form -->
<html:form action="sbHeaderAction.do?proc=list_object" styleClass="listHeader_form">	
	<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbHeaderHolder" property="sortColumn" />" /> 
	<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbHeaderHolder" property="sortOrder" />" />
	<input type="hidden" name="page" id="page" value="<bean:write name="sbHeaderHolder" property="page" />" />
	<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbHeaderHolder" property="selectedIds" />" />
	<input type="hidden" name="subAction" id="subAction" value="" />
	<input type="hidden" name="batchId" id="batchId" value="<bean:write name="sbBatchForm" property="encodedId" />" />	
	<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="sbBatchForm" property="pageFlag"/>" />
	<input type="hidden" name="statusFlag" id="statusFlag" value="<bean:write name="sbBatchForm" property="statusFlag"/>" />
	<input type="hidden" name="vendorId" id="searchVendorId" value="" />
          <fieldset>
         		<div class="tabMenu"> 
			<ul> 
				<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover"><bean:message bundle="sb" key="sb.batch" /> (ID: <bean:write name="sbBatchForm" property="batchId" />)</li> 
			</ul> 
		</div>
		<div class="tabContent"> 
			<!-- Tab1-Batch Info -->
			<div id="tabContent1" class="kantab" >
            	<ol class="auto" >
            		<li><label><bean:message bundle="sb" key="sb.bill.monthly" /></label><span><bean:write name="sbBatchForm" property="monthly"/></span></li>
            	</ol>
            	<ol class="auto">
					<li><label><bean:message bundle="sb" key="sb.oper.start.time" /></label><span><bean:write name="sbBatchForm" property="startDate"/></span></li>
					<li><label><bean:message bundle="sb" key="sb.oper.end.time" /></label><span><bean:write name="sbBatchForm" property="endDate"/></span></li>
               	</ol>
<!--             	<ol class="auto"> -->
<%--                		<li><label><logic:equal name="role" value="1"><bean:message bundle="sb" key="sb.order1.number" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="sb" key="sb.order2.number" /></logic:equal></label><span><bean:write name="sbBatchForm" property="countOrderId"/></span></li> --%>
<%--                		<li><label><logic:equal name="role" value="1">派送信息</logic:equal><logic:equal name="role" value="2"><bean:message bundle="sb" key="sb.people.number" /></logic:equal></label><span><bean:write name="sbBatchForm" property="countContractId"/></span></li> --%>
<%--                		<li><label><logic:equal name="role" value="1"><bean:message bundle="sb" key="sb.employee1.number" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="sb" key="sb.employee2.number" /></logic:equal></label><span><bean:write name="sbBatchForm" property="countEmployeeId"/></span></li> --%>
<%--                		<li class="hide"><label>社保公积金方案数量</label><span><bean:write name="sbBatchForm" property="countHeaderId"/></span></li> --%>
<%--                		<li class="hide"><label>科目数量</label><span><bean:write name="sbBatchForm" property="countItemId"/></span></li> --%>
<!--                	</ol> -->
<!--             	<ol class="auto"> -->
<%--             		<logic:notEmpty name="sbBatchForm" property="description"> --%>
<%--                			<li><label><bean:message bundle="public" key="public.description" /></label><span><bean:write name="sbBatchForm" property="description"/></span></li> --%>
<%--                		</logic:notEmpty> --%>
<!--             	</ol> -->
<!--             	<ol class="auto"> -->
<%--             		<li><label><bean:message bundle="sb" key="sb.company" /></label><span><bean:write name="sbBatchForm" property="decodeAmountCompany"/></span></li> --%>
<%--             		<li><label><bean:message bundle="sb" key="sb.personal" /></label><span><bean:write name="sbBatchForm" property="decodeAmountPersonal"/></span></li> --%>
<!--             	</ol> -->
           	 </div>
              	<!-- Tab1-Batch Info -->
            		</div>
            	</fieldset>
         </html:form>
       <!-- 包含社保公积金方案列表信息 -->
<div id="tableWrapper">
	<jsp:include page="/contents/workflow/sb/table/listHeaderTable.jsp"></jsp:include>          
</div>
							
<script type="text/javascript">
	(function($) {
		// 初始化菜单样式、按钮
		$('#menu_sb_Modules').addClass('current');			
		$('#menu_sb_Process').addClass('selected');
		<logic:equal name="sbBatchForm" property="statusFlag" value="preview">
			$('#menu_sb_DeclarationPreview').addClass('selected');
			$('#pageTitle').html('<bean:message bundle="sb" key="sb.title.preview" />');
			$('#btnApprove').show();
			$('#btnRollback').show();
			// 批准事件
			$('#btnApprove').click(function(){
				if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
					if(confirm('<bean:message bundle="public" key="popup.confirm.approve.records" />')){
						$('.searchSBHeader_form').attr('action', 'sbAction.do?proc=submit_estimation');
						$('#subAction').val('approveObjects');
						submitForm('searchSBHeader_form');
					}
				}else{
					alert('<bean:message bundle="public" key="popup.not.selected.records" />');
				}
			});
		</logic:equal>
		<logic:equal name="sbBatchForm" property="statusFlag" value="confirm">
			$('#menu_sb_DeclarationConfirm').addClass('selected');
			$('#pageTitle').html('<bean:message bundle="sb" key="sb.title.confirm" />');
			$('#btnConfirm').show();
			$('#btnRollback').show();
			// 确认事件
			$('#btnConfirm').click(function(){
				if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
					if(confirm('<bean:message bundle="public" key="popup.confirm.confirm.records" />')){
						$('.searchSBHeader_form').attr('action', 'sbAction.do?proc=submit_confirmation');
						$('#subAction').val('confirmObjects');
						submitForm('searchSBHeader_form');
					}
				}else{
					alert('<bean:message bundle="public" key="popup.not.selected.records" />');
				}
			});
		</logic:equal>
		<logic:equal name="sbBatchForm" property="statusFlag" value="submit">
			$('#menu_sb_SubmitToSettlement').addClass('selected');
			$('#pageTitle').html('<bean:message bundle="sb" key="sb.title.submit" />');
			$('#btnSubmit').show();
			// 提交事件
			$('#btnSubmit').click(function(){
				if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
					if(confirm('<bean:message bundle="public" key="popup.confirm.submit.records" />')){
						$('.searchSBHeader_form').attr('action', 'sbAction.do?proc=submit_settlement');
						$('#subAction').val('submitObjects');
						submitForm('searchSBHeader_form');
					}
				}else{
					alert('<bean:message bundle="public" key="popup.not.selected.records" />');
				}
			});
		</logic:equal>
		
		// JS of the List
		kanList_init();
		kanCheckbox_init();
		
		// 退回事件
		$('#btnRollback').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				
				$.ajax({
					url : "sbHeaderAction.do?proc=checkSBStatus", 
					dataType : "json",
					type: 'POST',
					async:false,
					data:'selectedIds='+$('#selectedIds').val(),
					success : function(data){
						if(data.addCount == "0" && data.backCount == "0"){
							if(confirm('<bean:message bundle="public" key="popup.confirm.return.records" />')){
								$('.searchSBHeader_form').attr('action', 'sbAction.do?proc=rollback');
								submitForm('searchSBHeader_form');
								$('#selectedIds').val('');
							}
						}else{
							$('#selectSBStatus').removeClass('hide');
					    	$('#shield').show();
					    	$('#sbToApplyForMoreDiv').addClass("hide");
					    	$('#sbToApplyForResigningDiv').addClass("hide");
					    	$('#sbStatusChangeWarningDiv').html("");
					    	if (data.addCount != "0"){
					    		$('#sbToApplyForMoreDiv').removeClass('hide');
					    		$('#sbStatusChangeWarningDiv').html("<bean:message bundle="sb" key="sb.status.select.title1" />");
					    	}
					    	if (data.backCount != "0"){
					    		$('#sbToApplyForResigningDiv').removeClass('hide');
					    		$('#sbStatusChangeWarningDiv').html("<bean:message bundle="sb" key="sb.status.select.title2" />");
					    	}
					    	if (data.addCount != "0" && data.backCount != "0"){
					    		$('#sbStatusChangeWarningDiv').html("<bean:message bundle="sb" key="sb.status.select.title3" />");
					    	}
						}
					}
				});
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
		var sbStatus = '<bean:write name="sbHeaderForm" property="sbStatus" />';
		if( sbStatus != null && sbStatus != ''){
			var sbStatusArray = sbStatus.split(',');
			for( var arr in sbStatusArray){
				$('#sbStatus_' + sbStatusArray[arr]).attr('checked',true);
			}
		}
		
		// 列表按钮返回list页面
		$('#btnList').click(function(){
			if (agreest())
			link('sbAction.do?proc=list_estimation&statusFlag=' + '<bean:write name="sbBatchForm" property="statusFlag"/>');
		});
		
		$('.searchSBHeader_vendorId').change(function(){
			$('#searchVendorId').val($('.searchSBHeader_vendorId').val());
		});
		if($('.searchSBHeader_vendorId').val()!=null&&$('.searchSBHeader_vendorId').val()!='0'){
			$('#searchVendorId').val($('.searchSBHeader_vendorId').val());
		}
		
		$("#btnImport").click(function(){
			btnImportClick();
		});
	})(jQuery);
	
	function confirmSBSubmitForm(){
		if(confirm('<bean:message bundle="public" key="popup.confirm.return.records" />')){
			$("#statusAddHidden").val($("#sbStatusAdd").val());
			$("#statusBackHidden").val($("#sbStatusBack").val());
			$('#selectSBStatus').addClass('hide');
	    	$('#shield').hide();
	    	$('.searchSBHeader_form').attr('action', 'sbAction.do?proc=rollback');
			submitForm('searchSBHeader_form');
		}
	}
	
	function resetForm() {
	    $('.searchSBHeader_employeeId').val('');
	    $('.searchSBHeader_employeeNo').val('');
	    $('.searchSBHeader_employeeNameZH').val('');
	    $('.searchSBHeader_employeeNameEN').val('');
	    $('.searchSBHeader_certificateNumber').val('');
	    $('.searchSBHeader_residencyType').val('0');
	    $('.searchSBHeader_orderId').val('0');
	    $('.searchSBHeader_contractId').val('');
	    $('.searchSBHeader_sbSolutionId').val('0');
	    $('.searchSBHeader_vendorId').val('0');
	    $('.searchSBHeader_detailMonthly').val('0');
	    $('.searchSBHeader_sbStatus').val('0');
	    $('.searchSBHeader_status').val('0');
	    $('.searchSBHeader_contractStatus').val('0');
	    $('.searchSBHeader_flag').val('0');
	    $('#startDate').val('');
	    $('div input[id^="sbStatus_"][type="checkbox"]').attr('checked',false);
	};
	
	function btnImportClick(){
		$('#importPaid').removeClass('hide');
		$('#shield').show();
	}
	
	function btnImportPaidCleanClick(){
		$("#certificateIds").val("");
	}
	
	function btnImportPaidClick(){
		$(".updatePaid_form").submit();
	}
</script>

