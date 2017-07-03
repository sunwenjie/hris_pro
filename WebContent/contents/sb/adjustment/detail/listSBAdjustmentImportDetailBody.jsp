<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.web.actions.biz.sb.SBAdjustmentImportBatchAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<!-- SBAdjustmentHeader - information -->
<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle">�걨��������</label>
	        <logic:notEmpty name="sbAdjustmentImportHeaderForm" property="adjustmentHeaderId" >
	        	<label class="recordId"> &nbsp; (ID: <bean:write name="sbAdjustmentImportHeaderForm" property="adjustmentHeaderId" />)</label>
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
				<kan:auth right="list" action="<%=SBAdjustmentImportBatchAction.accessAction%>">
					 <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />"/>
				</kan:auth>
			</div>
			<form action="">
				<fieldset>
					<ol class="auto">	
						<li>
							<label>�����·�</label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="monthly" /></span>
						</li>
					</ol>
					<ol class="auto">	
						<li id="contractIdLI">
							<label><logic:equal name="role" value="1">����Э��</logic:equal><logic:equal name="role" value="2">�Ͷ���ͬ</logic:equal>ID</label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="contractId" /></span>
						</li>
						<li>
							<label>�籣������</label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="decodeSbSolutionId" /></span>
						</li>
					</ol>
					<ol class="auto">
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>����ʵ��</label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="decodeLegalEntity" /></span>
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>ҵ������</label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="decodeBusinessType" /></span>
						</li>	
					</ol>
					<ol class="auto">	
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>ID</label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="employeeId" /></span>
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>���������ģ�</label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="employeeNameZH" /></span>
						</li>
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>������Ӣ�ģ�</label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="employeeNameEN" /></span>
						</li>
					</ol>
					<ol>	
						<li >
							<label>�ͻ�ID</label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="clientId" /></span>
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>����ID</label>
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="orderId" /></span>
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>�ͻ����ƣ����ģ�</label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="clientNameZH" /></span>
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>�ͻ����ƣ�Ӣ�ģ�</label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="clientNameEN" /></span>
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label>��������˾��</label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="amountCompany" /></span>
						</li>
						<li>
							<label>���������ˣ�</label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="amountPersonal" /></span>
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="description" /></span>
						</li>	
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<span><bean:write name="sbAdjustmentImportHeaderForm" property="decodeStatus" /></span>
						</li>				
					</ol>	
				</fieldset>
			</form>
	    </div>
	</div>
</div>

<!-- SBAdjustmentDetail - information -->
<div class="box" id="SBAdjustmentDetail-information">		
	<div class="head">
		<label>������ϸ</label>
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
		<div id="detailFormWrapper" style="display: none;">
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
			<%-- <jsp:include page="/contents/sb/adjustment/detail/form/manageSBAdjustmentDetailForm.jsp" flush="true"></jsp:include> --%>
		</div>	
		<div id="helpText" class="helpText"></div>	
		<div id="scrollWrapper">
			<div id="scrollContainer"></div>
		</div>																	
		<logic:notEmpty name="sbAdjustmentDetailHolder">	
			<html:form action="sbAdjustmentDetailAction.do?proc=list_object" styleClass="listSBAdjustmentDetail_form">
				<input type="hidden" name="id" value="<bean:write name="sbAdjustmentImportHeaderForm" property="encodedId"/>"/>
				<input type="hidden" name="employeeSBId" value="<bean:write name="sbAdjustmentImportHeaderForm" property="encodedEmployeeSBId"/>"/>
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbAdjustmentDetailHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbAdjustmentDetailHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="sbAdjustmentDetailHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbAdjustmentDetailHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />					
			</html:form>	
		</logic:notEmpty>	
		<div id="tableWrapper">
			<!-- Include table jsp ����table��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/sb/adjustment/detail/table/listSBAdjustmentImportDetailTable.jsp" flush="true"></jsp:include>
		</div>	
		<!-- tableWrapper -->	
		<div class="bottom">
				<p>
		</div>	
	</div>		
	<!-- inner -->		
</div>		
<script type="text/javascript">
	(function($) {
		// ��ʼ���˵���ʽ
		$('#menu_sb_Modules').addClass('current');
		$('#menu_sb_DeclarationAdjustment').addClass('menu_sb_DeclarationAdjustment_Import');
		
		// SBAdjustment Header �б�Click
		$('#btnList').click(function(){
			if (agreest())
			link('sbAdjustmentImportHeaderAction.do?proc=list_object&batchId=<bean:write name="sbAdjustmentImportHeaderForm" property="encodedBatchId" />');
		});
		
	})(jQuery);
</script>

