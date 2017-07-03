<%@ page pageEncoding="GB2312"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
	final PagedListHolder listDetailHolder = (PagedListHolder) request.getAttribute("listDetailHolder");
%>
<div id="content">
	<!-- Information Manage Form -->
	<div class="box toggableForm">
	   <div class="head">
	        <label id="pageTitle">ϵͳ��Ʊ  - <span id="titles"></span> </label>
	        <label class="recordId">&nbsp;(ID: <bean:write name="HeaderForm" property="invoiceId"/>)</label>
	    </div>
	    <div class="inner">
	        <div id="messageWrapper"></div>
	       <html:form action="systemInvoiceDetailAction.do?proc=list_object" styleClass="listDetail_form">
		        <div class="top">
		            <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="listDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="listDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="listDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="listDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="batchForm" property="encodedId" />" />	
					<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="pageFlag" />" />
					<input type="hidden" name="invoiceId" id="invoiceId" value="<bean:write name="HeaderForm" property="encodedId" />" />
		        </div>
	            <fieldset>
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,3)" class="first">���� (ID: <span><bean:write name="batchForm" property="batchId" /></span>)</li> 
							<li id="tabMenu2" onClick="changeTab(2,3)" class="hover">��Ʊ (ID: <span><bean:write name="HeaderForm" property="invoiceId" /></span>)</li> 
						</ul> 
					</div> 
		            <div class="tabContent"> 
						<div id="tabContent1" class="kantab" style="display:none">
							<ol class="auto">
								<li><label>�����·�</label><span><bean:write name="batchForm" property="monthly"/></span></li>
							</ol>
							<ol class="auto">
								<li><label>���㿪ʼʱ��</label><span><bean:write name="batchForm" property="startDate"/></span></li>
								<li><label>�������ʱ��</label><span><bean:write name="batchForm" property="endDate"/></span></li>
		                	</ol>
							<ol class="auto">
		                			<li><label>����ʵ��</label><span><bean:write name="batchForm" property="decodeEntityId"/></span></li>
		                			<li><label>ҵ������</label><span><bean:write name="batchForm" property="decodeBusinessTypeId"/></span></li>
			                		<li><label>�ͻ�ID</label><span><bean:write name="clientVO" property="clientId"/></span></li>
			                		<li><label>�ͻ����ƣ����ģ�</label><span><bean:write name="clientVO" property="nameZH"/></span></li>
			                		<li><label>�ͻ����ƣ�Ӣ�ģ�</label><span><bean:write name="clientVO" property="nameEN"/></span></li>
		                			<li><label>����ID</label><span><bean:write name="batchForm" property="orderId"/></span></li>
		                	</ol>
		                	<ol class="auto">
		                			<li><label>����</label><span><bean:write name="batchForm" property="description"/></span></li>
		                	</ol>
							<ol class="auto">
		                		<li><label>��˾Ӫ��</label><span><bean:write name="batchForm" property="billAmountCompany"/></span></li>
		                		<li><label>��˾�ɱ�</label><span><bean:write name="batchForm" property="costAmountCompany"/></span></li>
		                		<li><label>��˰</label><span><bean:write name="batchForm" property="taxAmount"/></span></li>
		                	</ol>
						</div>
						<div id="tabContent2" class="kantab" >
							<ol class="auto">
								<li><label>�����·�</label><span><bean:write name="HeaderForm" property="monthly"/></span></li>
							</ol>
							<ol class="auto">
		                			<li><label>����ʵ��</label><span><bean:write name="HeaderForm" property="decodeEntityId"/></span></li>
		                			<li><label>ҵ������</label><span><bean:write name="HeaderForm" property="decodeBusinessTypeId"/></span></li>
			                		<li><label>�ͻ�ID</label><span><bean:write name="HeaderForm" property="clientId"/></span></li>
			                		<li><label>�ͻ�����</label><span><bean:write name="HeaderForm" property="clientName"/></span></li>
			                		<li><label>����ID</label><span><bean:write name="HeaderForm" property="orderId"/></span></li>
		                	</ol>
							<ol class="auto">
		                			<li><label>����</label><span><bean:write name="HeaderForm" property="description"/></span></li>
		                	</ol>
		                	<ol class="auto">
		                			<li><label>��������</label><span><bean:write name="HeaderForm" property="decodeBranch"/></span></li>
		                			<li><label>������</label><span><bean:write name="HeaderForm" property="decodeOwner"/></span></li>
		                	</ol>
							<ol class="auto">
		                		<li><label>��˾Ӫ��</label><span><bean:write name="HeaderForm" property="billAmountCompany"/></span></li>
		                		<li><label>��˾�ɱ�</label><span><bean:write name="HeaderForm" property="costAmountCompany"/></span></li>
		                		<li><label>��˰</label><span><bean:write name="HeaderForm" property="taxAmount"/></span></li>
							</ol>
						</div>
					</div>
               	</fieldset>
             </html:form>
			<!-- -->
			<div id="tableWrapper">
				<jsp:include page="table/listInvoiceDetailTable.jsp"></jsp:include>
			</div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_finance_Modules').addClass('current');			
		if($('#pageFlag').val()=="Preview"){
			$('#menu_finance_Preview').addClass('selected');
			$('#titles').text('Ԥ��');
		}else if($('#pageFlag').val()=="Split"){
			$('#menu_finance_Split').addClass('selected');
			$('#titles').text('���');
		}else if($('#pageFlag').val()=="Merge"){
			$('#menu_finance_Merge').addClass('selected');
			$('#titles').text('�ϲ�');
		}
		// ȡ���¼�
		$('#btnList').click(function(){
			link('systemInvoiceHeaderAction.do?proc=list_object&pageFlag=<bean:write name="pageFlag" />&batchId=<bean:write name="batchForm" property="encodedId"/>');
		});
	})(jQuery);
</script>

