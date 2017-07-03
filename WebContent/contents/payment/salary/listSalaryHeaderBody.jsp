<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>

<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle"><bean:message bundle="payment" key="payment.salary.import.batch.search.title" /></label>
	        <label class="recordId">&nbsp;(ID: <bean:write name="commonBatchVO" property="batchId"/>)</label>
	    </div>
	    <div class="inner">
	        <div id="messageWrapper"></div>
	        <html:form action="salaryAction.do?proc=to_salaryHeader" styleClass="list_form" styleId="list_form">
		        <div class="top">
		        	<logic:equal name="commonBatchVO" property="status" value="1">
			        	<input type="button" class="function" name="btnApprove" id="btnApprove" value="<bean:message bundle="public" key="button.submit" />"  />
		            	<input type="button" class="delete" name="btnRollback" id="btnRollback" value="<bean:message bundle="public" key="button.return" />"  />
	            	</logic:equal>
	            	<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
	            	<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="salaryDTOHolder" property="selectedIds" />" />
		        	<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="salaryDTOHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="salaryDTOHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="salaryDTOHolder" property="page" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="pageFlag" id="pageFlag" value="header" />
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="commonBatchVO" property="encodedId" />" />	
		        </div>
	            <fieldset>
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover"><bean:message bundle="payment" key="payment.salary.import.batch.id" /> (ID:<span><bean:write name="commonBatchVO" property="batchId" /></span>)</li> 
						</ul> 
					</div> 
		            <div class="tabContent"> 
						<div id="tabContent1" class="kantab" >
							<ol class="auto">
								<li><label><bean:message bundle="payment" key="payment.salary.import.excel.name" /></label><span><bean:write name="commonBatchVO" property="importExcelName"/></span></li>
								<li><label><bean:message bundle="public" key="public.note" /></label><span><bean:write name="commonBatchVO" property="remark2"/></span></li>
								<li><label><bean:message bundle="payment" key="payment.salary.import.uploader" /></label><span><bean:write name="commonBatchVO" property="decodeCreateBy"/></span></li>
								<li><label><bean:message bundle="payment" key="payment.salary.import.upload.time" /></label><span><bean:write name="commonBatchVO" property="decodeCreateDate"/></span></li>
							</ol>
						</div>
					</div>
               	</fieldset>
            </html:form>
	        <!-- 包含工资详情列表信息 -->
			<div id="tableWrapper">
				<jsp:include page="table/listSalaryDetailTable.jsp"></jsp:include>   
			</div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Process').addClass('selected');
		$('#menu_salary_Import').addClass('selected');

		// 列表事件
		$('#btnList').click(function(){
			if (agreest())
			link('salaryAction.do?proc=list_object');
		});
		
		$('#menu_cb_SubmitToSettlement').addClass('selected');

		// 批准事件
		$('#btnApprove').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.submit.records" />')){
					$('.list_form').attr('action', 'salaryAction.do?proc=submit_salary');
					$('#subAction').val('approveObjects');
					submitForm('list_form');
					//submitForm('list_form', "approveObjects", null, null, null, "tableWrapper");
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
		
		$('#btnRollback').click(function(){
			
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.return.records" />')){
					$('.list_form').attr('action', 'salaryAction.do?proc=rollback_salary');
					$('#subAction').val('rollbackObject');
					
					if(($('#page').val()!=1&&$('#pageCount').val()-1)== $('#page').val()&& $('#kanList_chkSelectAll').attr( "checked" )=="checked"){
						 $('#page').val( $('#page').val()-1);
					}
					submitForm('list_form');
					//submitForm('list_form', "rollbackObject", null, null, null,"tableWrapper","salaryAction.do?proc=rollback_salary");
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
		
	})(jQuery);
</script>

