<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final String javaObjectName = "com.kan.hro.domain.biz.sb.SBDTO";
%>

<div id="content">
<div class="box searchForm toggableForm" id="Search-Information">
		 <div class="head">
	        <label id="pageTitle">社保公积金</label>
	        <label class="recordId">&nbsp; (ID: <bean:write name="sbBatchForm" property="batchId" />)</label>
	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchSBHeader_form', 'searchObject', null, null, null, null, null, null, true);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="sbHeaderAction.do?proc=list_object" method="post" styleClass="searchSBHeader_form listHeader_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="sbHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value="<bean:write name="sbBatchForm" property="encodedId" />" />	
				<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="sbBatchForm" property="pageFlag"/>" />
				<input type="hidden" name="vendorId" id="searchVendorId" value="" />
				<input type="hidden" name="searchHeader" id="searchHeader" class="searchHeader" value="true"/>
				<input type="hidden" name="statusFlag" id="statusFlag" value="<bean:write name="sbBatchForm" property="statusFlag"/>" />
				<input type="hidden" name="statusAddHidden" id="statusAddHidden" value="" />
				<input type="hidden" name="statusBackHidden" id="statusBackHidden" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label>
							<html:text property="employeeId" maxlength="10" styleClass="searchSBHeader_employeeId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.no" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.no" /></logic:equal>		
							</label>
							<html:text property="employeeNo" maxlength="50" styleClass="searchSBHeader_employeeNo" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>		
							</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchSBHeader_employeeNameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>	
							</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchSBHeader_employeeNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.employee.residency.type" /></label> 
							<html:select property="residencyType" styleClass="searchSBHeader_residencyType">
								<html:optionsCollection property="residencyTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.certificate.number" /></label>
							<html:text property="certificateNumber" maxlength="100" styleClass="searchSBHeader_certificateNumber" /> 
						</li> 
						<logic:equal name="role" value="2">
								<logic:notEmpty name="orderDescription">
									<li>
										<label><bean:message bundle="public" key="public.order2" /></label>
										<span><html:text property="orderDescription" styleClass="searchSBHeader_orderDescription"></html:text></span>
									</li>
								</logic:notEmpty>
						</logic:equal>
		   				<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>	
							</label>
							<html:text property="contractId" maxlength="10" styleClass="searchSBHeader_contractId" /> 
						</li> 
		   				<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="sb" key="sb.employee.contract1.status" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="sb" key="sb.employee.contract2.status" /></logic:equal>	
							</label>
							<html:select property="contractStatus" styleClass="searchSBHeader_contractStatus">
								<html:optionsCollection property="contractStatuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li> 
						<li>
							<label><bean:message bundle="sb" key="sb.solution" /></label> 
							<html:select property="sbSolutionId" styleClass="searchSBHeader_sbSolutionId">
								<html:optionsCollection property="socialBenefitSolutions" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.vendor" /></label> 
							<html:select property="vendorId" styleClass="searchSBHeader_vendorId">
								<html:optionsCollection property="vendors" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.monthly" /></label> 
							<html:select property="monthly" styleClass="searchSBHeader_detailMonthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.start.date" /></label> 
							<input name="startDate" class="startDate  Wdate " id="startDate" onfocus="WdatePicker()" type="text" maxlength="10" value="<bean:write name="sbHeaderForm" property="startDate" />">
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.payment.status" /></label> 
							<html:select property="flag" styleClass="searchSBHeader_flag">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.status" /></label> 
							<div style="width: 235px;">
								<logic:iterate id="sbStatus" name="sbHeaderForm" property="sbStatuses" indexId="index">
									<label class="auto">
										<input type="checkbox" name="sbStatusArray" id="sbStatus_<bean:write name="sbStatus" property="mappingId" />" class="sbStatus_<bean:write name="sbStatus" property="mappingId" />" value="<bean:write name="sbStatus" property="mappingId" />" />
										<bean:write name="sbStatus" property="mappingValue" />
									</label>
								</logic:iterate>
							</div>
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	<!-- Information Manage Form -->
	<div class="box noHeader">
	    <div class="inner">
	        <div id="messageWrapper"></div>
		        <div class="top">
		        	<%String authAccessAction = (String)request.getAttribute("authAccessAction");%>
		        	<kan:auth right="approve" action="<%=authAccessAction%>">
		            	<input type="button" class="function" name="btnApprove" id="btnApprove" value="<bean:message bundle="public" key="button.approve" />" style="display: none;" />
		            </kan:auth>
		            <kan:auth right="confirm" action="<%=authAccessAction%>">
		            	<input type="button" class="function" name="btnConfirm" id="btnConfirm" value="<bean:message bundle="public" key="button.confirm" />" style="display: none;" />
		            </kan:auth>
		            <kan:auth right="submit" action="<%=authAccessAction%>">
		            	<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" style="display: none;" />
		            </kan:auth>
		            <kan:auth right="back" action="<%=authAccessAction%>">
		            	<input type="button" class="delete" name="btnRollback" id="btnRollback" value="<bean:message bundle="public" key="button.return" />" style="display: none;" />
		            </kan:auth>
		            <kan:auth right="list" action="<%=authAccessAction%>">
		            	<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
		            </kan:auth>
		            <logic:notEqual value="preview" name="sbBatchForm" property="statusFlag" >
		            <kan:auth right="import" action="<%=authAccessAction%>">
		            	<input type="button" class="function" name="btnImport" id="btnImport" value="已缴纳导入">
		            </kan:auth>
		            </logic:notEqual>
		            <logic:equal name="isExportExcel" value="1">
		            	<kan:auth right="export" action="<%=authAccessAction%>">
							<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="popupSelectTemplate('<%=javaObjectName %>');"><img src="images/appicons/excel_16.png" /></a> 
						</kan:auth>
					</logic:equal>
					<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
				</div>
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
			            	<ol class="auto" >
			            		<logic:notEmpty name="sbBatchForm" property="decodeEntityId">
			            			<logic:notEqual name="sbBatchForm" property="decodeEntityId" value="0">
		                				<li><label><bean:message bundle="security" key="security.entity" /></label><span><bean:write name="sbBatchForm" property="decodeEntityId"/></span></li>
		                			</logic:notEqual>
		                		</logic:notEmpty>
			            		<logic:notEmpty name="sbBatchForm" property="decodeBusinessTypeId">
			            			<logic:notEqual name="sbBatchForm" property="businessTypeId" value="0">
		                				<li><label><bean:message bundle="security" key="security.business.type" /></label><span><bean:write name="sbBatchForm" property="decodeBusinessTypeId"/></span></li>
		                			</logic:notEqual>
		                		</logic:notEmpty>
			            	</ol>
			            	<ol class="auto">
			            		<logic:notEmpty name="sbBatchForm" property="decodeCityId">
			               			<li><label><bean:message bundle="public" key="public.city" /></label><span><bean:write name="sbBatchForm" property="decodeCityId"/></span></li>
			               		</logic:notEmpty>
			               	</ol>
			            	<ol class="auto" >
		                		<logic:notEmpty name="sbBatchForm" property="orderId">
			               			<li>
			               				<label>
			               					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
			               					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
			               				</label>
			               				<span><bean:write name="sbBatchForm" property="orderId"/></span></li>
			               		</logic:notEmpty>
			            	</ol>
			            	<ol class="auto">
			            		<logic:equal name="role" value="1">
			                		<li><label>客户数量</label><span><bean:write name="sbBatchForm" property="countClientId"/></span></li>
			                	</logic:equal>
		                		<li><label><logic:equal name="role" value="1"><bean:message bundle="sb" key="sb.order1.number" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="sb" key="sb.order2.number" /></logic:equal></label><span><bean:write name="sbBatchForm" property="countOrderId"/></span></li>
		                		<li><label><logic:equal name="role" value="1">派送信息</logic:equal><logic:equal name="role" value="2"><bean:message bundle="sb" key="sb.people.number" /></logic:equal></label><span><bean:write name="sbBatchForm" property="countContractId"/></span></li>
		                		<li><label><logic:equal name="role" value="1"><bean:message bundle="sb" key="sb.employee1.number" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="sb" key="sb.employee2.number" /></logic:equal></label><span><bean:write name="sbBatchForm" property="countEmployeeId"/></span></li>
		                		<li class="hide"><label>社保公积金方案数量</label><span><bean:write name="sbBatchForm" property="countHeaderId"/></span></li>
		                		<li class="hide"><label>科目数量</label><span><bean:write name="sbBatchForm" property="countItemId"/></span></li>
		                	</ol>
			            	<ol class="auto">
			            		<logic:notEmpty name="sbBatchForm" property="description">
			               			<li><label><bean:message bundle="public" key="public.description" /></label><span><bean:write name="sbBatchForm" property="description"/></span></li>
			               		</logic:notEmpty>
			            	</ol>
			            	<ol class="auto">
			            		<li><label></label></li>
			            	</ol>
			            	<ol class="auto">
			            		<li><label><bean:message bundle="sb" key="sb.company" /></label><span><bean:write name="sbBatchForm" property="decodeAmountCompany"/></span></li>
			            		<li><label><bean:message bundle="sb" key="sb.personal" /></label><span><bean:write name="sbBatchForm" property="decodeAmountPersonal"/></span></li>
			            	</ol>
			           	 </div>
	                	<!-- Tab1-Batch Info -->
               		</div>
               	</fieldset>
            </html:form>
	         <!-- 包含社保公积金方案列表信息 -->
			<div id="tableWrapper">
				<jsp:include page="/contents/sb/table/listHeaderTable.jsp"></jsp:include>          
			</div>
			<div class="bottom"><p/></div>
         </div>
  	</div>
</div>
<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/selectVendor.jsp"></jsp:include>
</div>
<div id="popupSBStatus">
	<jsp:include page="/popup/selectSBStatus.jsp"></jsp:include>
</div>
<div class="modal midsize content hide" id="importPaid">
    <div class="modal-header" id="paid" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#importPaid').addClass('hide');$('#shield').hide();">×</a>
        <label>已缴纳批量导入</label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" name="btnImportPaid" id="btnImportPaid" class="" value="导入" onclick="btnImportPaidClick();" />
	    	<input type="button" name="btnImportPaidClean" id="btnImportPaidClean" class="reset" value="清空" onclick="btnImportPaidCleanClick()" />
	    </div>
        <html:form action="sbHeaderAction.do?proc=updatePaid" styleClass="updatePaid_form">
			<input type="hidden" name="batchId" id="batchId" value="<bean:write name="sbBatchForm" property="encodedId" />" /> 
			<input type="hidden" name="statusFlag" id="statusFlag" value="<bean:write name="sbBatchForm" property="statusFlag"/>" />
			<label>待修改雇员身份证号码<img title="请用回车区分身份证号码" src="images/tips.png"/></label> <br/>
			<textarea rows="20" cols="70" id="certificateIds" name="certificateIds"></textarea>
		</html:form >
    </div>
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

