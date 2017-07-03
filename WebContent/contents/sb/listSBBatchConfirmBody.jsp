<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.sb.SBAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder sbBatchHolder = (PagedListHolder) request.getAttribute("sbBatchHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="sbBatch-information">
		<div class="head"><label><bean:message bundle="sb" key="sb.title.confirm" /></label></div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="sbAction.do?proc=list_estimation" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbBatchHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbBatchHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="sbBatchHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbBatchHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="sbBatchForm" property="pageFlag" />" />
				<input type="hidden" name="statusFlag"id="statusFlag" value="<bean:write name="sbBatchForm" property="statusFlag" />" />
				<input type="hidden" name="vendorId" id="searchVendorId" value="" />
				<input type="hidden" name="statusAddHidden" id="statusAddHidden" value="" />
				<input type="hidden" name="statusBackHidden" id="statusBackHidden" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="sb" key="sb.batch.id" /></label> 
							<html:text property="batchId" maxlength="10" styleClass="searchSBBatch_batchId" /> 
						</li>
						<li>
							<label><bean:message bundle="security" key="security.entity" /></label> 
							<html:select property="entityId" styleClass="searchSBBatch_entityId">
								<html:optionsCollection property="entitys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.business.type" /></label> 
							<html:select property="businessTypeId" styleClass="searchSBBatch_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.province.city" /></label> 
							<html:select property="provinceId" styleClass="searchSBBatch_provinceId">
								<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
							</html:select>
							<input type="hidden" name="cityIdTemp" id="cityIdTemp" class="searchSBBatch_cityIdTemp" value="<bean:write name="sbBatchForm" property="cityId" />" />
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>客户ID</label>
							<html:text property="clientId" maxlength="10" styleClass="searchSBBatch_clientId" />
						</li>
						<logic:equal name="role" value="1">
							<li>
								<label><bean:message bundle="public" key="public.order1.id" /></label>
								<html:text property="orderId" maxlength="10" styleClass="searchSBBatch_orderId" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="2">
							<li>
								<label><bean:message bundle="public" key="public.order2.id" /></label>
			   					<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="searchSBBatch_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
			   					</logic:notEmpty>
			   					<logic:empty name="clientOrderHeaderMappingVOs">
			   						<html:text property="orderId" maxlength="10" styleClass="searchSBBatch_orderId" /> 
			   					</logic:empty>
		   					</li>
		   				</logic:equal>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
							</label>
							<html:text property="contractId" maxlength="10" styleClass="searchSBBatch_contractId" />
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.monthly" /></label>
							<html:select property="monthly" styleClass="searchSBBatch_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.payment.status" /></label> 
							<html:select property="flag" styleClass="searchSBBatch_flag">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.type" /></label> 
							<html:select property="sbType" styleClass="searchSBBatch_sbType">
								<html:optionsCollection property="sbTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- SBBatch-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">保存成功！</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">编辑成功！</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="confirm" action="<%=SBAction.ACCESS_ACTION_CONFIRM%>">
	            	<input type="button" class="function" name="btnConfirm" id="btnConfirm" value="<bean:message bundle="public" key="button.confirm" />" />
	            </kan:auth>
	            <kan:auth right="back" action="<%=SBAction.ACCESS_ACTION_CONFIRM%>">
	            	<input type="button" class="delete" name="btnRollback" id="btnRollback" value="<bean:message bundle="public" key="button.return" />" />
	            </kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
				<logic:equal name="isExportExcel" value="1">
					<kan:auth right="export" action="<%=SBAction.ACCESS_ACTION_CONFIRM%>">
						<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="popupSelectTemplate('<%=SBAction.javaObjectName %>');"><img src="images/appicons/excel_16.png" /></a> 
					</kan:auth>
				</logic:equal>
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/sb/table/listSBBatchTableConfirm.jsp" flush="true"/> 
			</div>
			<div class="bottom">
				<p>
			</div>
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
<script type="text/javascript">
	(function($) {
		$('#menu_sb_Modules').addClass('current');			
		$('#menu_sb_Process').addClass('selected');
		$('#menu_sb_DeclarationConfirm').addClass('selected');
		$('#searchDiv').hide();

		// 根据"StatusFlag"设置顶部菜单选择样式
		<logic:equal name="sbBatchForm" property="statusFlag" value="preview">
			$('#subAction').val('approveObjects');
		</logic:equal>
		<logic:equal name="sbBatchForm" property="statusFlag" value="confirm">
			$('#subAction').val('confirmObjects');
		</logic:equal>
		<logic:equal name="sbBatchForm" property="statusFlag" value="submit">
			$('#subAction').val('submitObjects');
		</logic:equal>
		
		/**
		 *	按钮绑定事件
		 */
		// 确认事件
		$('#btnConfirm').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.confirm.selected.batch" />')){
					$('.list_form').attr('action', 'sbAction.do?proc=submit_confirmation');
					submitForm('list_form', "confirmObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});

		// 退回事件
		$('#btnRollback').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				
				$.ajax({
					url : "sbAction.do?proc=checkSBStatus", 
					dataType : "json",
					type: 'POST',
					async:false,
					data:'selectedIds='+$('#selectedIds').val(),
					success : function(data){
						if(data.addCount == "0" && data.backCount == "0"){
							if(confirm('<bean:message bundle="public" key="popup.confirm.return.selected.batch" />')){
								$('.list_form').attr('action', 'sbAction.do?proc=rollback');
								submitForm('list_form', "rollbackObjects", null, null, null, 'tableWrapper');
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

		// 初始化省份控件
		provinceChange('searchSBBatch_provinceId', 'searchObject', $('.searchSBBatch_cityIdTemp').val(), 'cityId');
		
		// 绑定省Change事件
		$('.searchSBBatch_provinceId').change( function () { 
			provinceChange('searchSBBatch_provinceId', 'searchObject', 0, 'cityId');
		});
		
		<%
		final Boolean messageInfo = (Boolean) request.getAttribute("messageInfo");
			if(messageInfo!=null&&messageInfo){
		%>
			$('#messageWrapper').html('<div class="message success fadable">该批次下没有数据！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
	 			messageWrapperFada();
		<%	}%>
	})(jQuery);
	
	function confirmSBSubmitForm(){
		if(confirm('<bean:message bundle="public" key="popup.confirm.return.selected.batch" />')){
			$("#statusAddHidden").val($("#sbStatusAdd").val());
			$("#statusBackHidden").val($("#sbStatusBack").val());
			$('#selectSBStatus').addClass('hide');
	    	$('#shield').hide();
			$('.list_form').attr('action', 'sbAction.do?proc=rollback');
			submitForm('list_form', "rollbackObjects", null, null, null, 'tableWrapper');
			$('#selectedIds').val('');
		}
	}

	function resetForm() {
		$('.searchSBBatch_batchId').val('');
		$('.searchSBBatch_entityId').val('0');
		$('.searchSBBatch_businessTypeId').val('0');
		$('.searchSBBatch_provinceId').val('0');
		$('#cityId').val('0');
		$('.searchSBBatch_clientId').val('');
		<logic:equal name="role" value="1">
			$('.searchSBBatch_orderId').val('');
		</logic:equal>
		<logic:equal name="role" value="2">
			$('.searchSBBatch_orderId').val('0');
		</logic:equal>
		$('.searchSBBatch_contractId').val('');
		$('.searchSBBatch_monthly').val('0');
		$('.searchSBBatch_flag').val('0');
		$('.searchSBBatch_sbType').val('0');
	};
</script>
