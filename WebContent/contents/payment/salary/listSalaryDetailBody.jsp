<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>

<%@ page import=" com.kan.hro.web.actions.biz.payment.SalaryAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>


<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle">���ʽ���</label>
	        <label class="recordId">&nbsp;(ID: <bean:write name="salaryHeaderForm" property="salaryHeaderId"/>)</label>
	    </div>
	    <div class="inner">
	        <div id="messageWrapper"></div>
	        <html:form action="salaryAction.do?proc=to_salaryDetail" styleClass="list_form">
		        <div class="top">
		            <input type="button" class="reset" name="btnList" id="btnList" value="������һ��" />
		            
		            <logic:equal name="salaryHeaderForm" property="status" value="1">
						<kan:auth right="submit" action="<%=SalaryAction.accessAction%>">
							<input type="button" class="function" name="btnApprove" id="btnApprove" value="<bean:message bundle="public" key="button.submit" />" />
						</kan:auth>
						<kan:auth right="back" action="<%=SalaryAction.accessAction%>">
							<input type="button" class="delete" name="btnRollback" id="btnRollback" value="�˻�" />
						</kan:auth>
					</logic:equal>
	            	
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="salaryDTOHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="salaryDTOHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="salaryDTOHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="salaryDTOHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="pageFlag" id="pageFlag" value="header" />
					<input type="hidden" name="salaryHeaderId" id="salaryHeaderId" value="<bean:write name="salaryHeaderForm" property="encodedId" />" />	
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="salaryHeaderForm" property="encodedBatchId" />" />	
		        </div>
	            <fieldset>
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover">�������� (ID:<span><bean:write name="salaryHeaderForm" property="salaryHeaderId" /></span>)</li> 
						</ul> 
					</div> 
		            <div class="tabContent"> 
						<div id="tabContent1" class="kantab" >
							<ol class="auto">
								<li><label>�����·�</label><span><bean:write name="salaryHeaderForm" property="monthly"/></span></li>
								<li><label>������Ա</label><span><bean:write name="salaryHeaderForm" property="decodeCreateBy"/></span></li>
							</ol>
							<ol class="auto">
								<li><label>���ʿ�ʼ����</label><span><bean:write name="salaryHeaderForm" property="startDate"/></span></li>
								<li><label>���ʽ�������</label><span><bean:write name="salaryHeaderForm" property="endDate"/></span></li>
							</ol>
							<ol class="auto">
								<logic:notEmpty name="salaryHeaderForm" property="decodeEntityId">
		                			<li><label>����ʵ��</label><span><bean:write name="salaryHeaderForm" property="decodeEntityId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="salaryHeaderForm" property="decodeBusinessTypeId">
		                			<li><label>ҵ������</label><span><bean:write name="salaryHeaderForm" property="decodeBusinessTypeId"/></span></li>
		                		</logic:notEmpty>
	                		</ol>
	                		<ol class="auto" <logic:equal name="role" value="2">style="display:none"</logic:equal>>
	                			<logic:notEmpty name="salaryHeaderForm" property="clientId">
		                			<li><label>�ͻ�ID</label><span><bean:write name="salaryHeaderForm" property="clientId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="salaryHeaderForm" property="orderId">
		                			<li><label><logic:equal name="role" value="1">����ID</logic:equal><logic:equal name="role" value="2">�������ID</logic:equal></label>
		                			<span><bean:write name="salaryHeaderForm" property="orderId"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li><label><logic:equal name="role" value="1">��ԱID</logic:equal><logic:equal name="role" value="2">Ա��ID</logic:equal></label><span>
		                		<bean:write name="salaryHeaderForm" property="employeeId"/></span></li>
		                	</ol>
		                	<ol class="auto">
		                		<logic:notEmpty name="salaryHeaderForm" property="description">
		                			<li><label>����</label><span><bean:write name="salaryHeaderForm" property="description"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li><label>��˾�ɱ�</label><span><bean:write name="salaryHeaderForm" property="costAmountCompany"/></span></li>
		                	</ol>
							<ol class="auto">
		                		<li><label>��������</label><span><bean:write name="salaryHeaderForm" property="billAmountPersonal"/></span></li>
		                		<li><label>����֧��</label><span><bean:write name="salaryHeaderForm" property="costAmountPersonal"/></span></li>
		                		<li><label>��˰�ϼ�</label><span><bean:write name="salaryHeaderForm" property="taxAmountPersonal"/></span></li>
		                	</ol>
							<ol class="auto">
		                		<li><label>��������</label><span>
		                			<bean:write name="salaryHeaderForm" property="bankNameZH"/>
		                			 <logic:notEqual name="salaryHeaderForm" property="bankNameEN" value="">
		                			��<bean:write name="salaryHeaderForm" property="bankNameEN"/>��
		                			</logic:notEqual>
		                		</span></li>
		                		<li><label>�����˻�</label><span><bean:write name="salaryHeaderForm" property="bankAccount"/></span></li>
		                	</ol>
						</div>
					</div>
               	</fieldset>
            </html:form>
	        <!-- �������������б���Ϣ -->
			<div id="tableWrapper">
				<jsp:include page="table/listSalaryDetailTable.jsp" flush="true"/> 
			</div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// ���ö����˵�ѡ����ʽ
		$('#menu_salary_Modules').addClass('current');
		
		if($('#statusFlag').val() == 'preview'){
			$('#menu_salary_Process').addClass('selected');
			$('#menu_salary_Estimate').addClass('selected');
		}else if($('#statusFlag').val() == 'submit'){
			$('#menu_salary_Process').addClass('selected');
			$('#menu_salary_Confirm').addClass('selected');
			$('#pageTitle').html('����̨��');
		}else if($('#statusFlag').val() == 'issue'){
			$('#menu_salary_PayConfirm').addClass('selected');
			$('#pageTitle').html('����ȷ��');
		}
		
		
		// ��׼�¼�
		$('#btnApprove').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���ύ���ʣ�")){
					$('.list_form').attr('action', 'salaryAction.do?proc=submit_salary');
					$('#subAction').val('approveObjects');
					submitForm('list_form');
				}
				}else{
					alert("��ѡ��Ҫ�ύ�ļ�¼��");
				}
			
		});
		
		
		$('#btnRollback').click(function(){
			
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���˻ع��ʣ�")){
					$('.list_form').attr('action', 'salaryAction.do?proc=submit_salary');
					$('#subAction').val('rollback');
					submitForm('list_form');
				}
			}else{
				alert("��ѡ��Ҫ�˻صļ�¼��");
			}
		});

		// �ύ�¼�
		$('#btnSubmit').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���ύ���Σ�")){
					$('.list_form').attr('action', 'paymentAction.do?proc=submit_estimation');
					submitForm('list_form', "submitObjects", null, null, null, null);
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ�ύ�ļ�¼��");
			}
		});
		
		// �����¼�
		$('#btnIssue').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���������Σ�")){
					$('.list_form').attr('action', 'paymentAction.do?proc=issue_Actual');
					submitForm('list_form', "issueObjects", null, null, null, null);
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ���ŵļ�¼��");
			}
		});

		// �˻��¼�
/* 		$('#btnRollback').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���˻����Σ�")){
					$('.list_form').attr('action', 'paymentAction.do?proc=rollback_estimation');
					submitForm('list_form', "rollbackObjects", null, null, null, null);
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ�˻صļ�¼��");
			}
		}); */
		
		// �б��¼�
		$('#btnList').click(function(){
			link('salaryAction.do?proc=to_salaryHeader&batchId=<bean:write name="salaryHeaderForm" property="encodedBatchId"/>');
		});
	})(jQuery);
</script>

