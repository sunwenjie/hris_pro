<%@ page pageEncoding="GB2312"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>

<div id="content">
<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
	        <label id="pageTitle">ϵͳ��Ʊ  - <span id="titles"></span></label>
	        <label class="recordId">&nbsp;(ID: <bean:write name="batchForm" property="batchId"/>)</label>
	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchHeader_form', 'searchObject', null, null, null, null, null, null, true);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="systemInvoiceHeaderAction.do?proc=list_object" method="post" styleClass="searchHeader_form">
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="headerListHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="headerListHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="headerListHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="headerListHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="batchForm" property="encodedId" />" />	
					<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="pageFlag" />" /> 
				<fieldset>
					<ol class="auto">
							<li>
								<label>�ͻ�ID</label>
								<html:text property="clientId" maxlength="10" styleClass="searchHeader_clientId" /> 
							</li>
							<li>
								<label>�ͻ����ƣ����ģ�</label>
								<html:text property="clientNameZH" maxlength="100" styleClass="searchHeader_clientNameZH" /> 
							</li>
							<li>
								<label>�ͻ����ƣ�Ӣ�ģ�</label>
								<html:text property="clientNameEN" maxlength="100" styleClass="searchHeader_clientNameEN" /> 
							</li>
							<li>
								<label>����ID</label>
								<html:text property="orderId" maxlength="100" styleClass="searchHeader_orderId" /> 
							</li> 
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="inner">
	        <div id="messageWrapper"></div>
	        <html:form action="systemInvoiceHeaderAction.do?proc=list_object" styleClass="listHeader_form">
		        <div class="top">
		            <input type="button" class="function" name="btnSub" id="btnSub" value="���" style="display:none;"/>
		            <input type="button" class="function" name="btnCompound" id="btnCompound" value="�ϲ�" style="display:none;"/>
		            <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
		            <input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="headerListHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="headerListHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="headerListHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="headerListHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="batchForm" property="encodedId" />" />	
					<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="pageFlag" />" /> 
		            <a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
		        </div>
	            <fieldset>
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover">���� (ID: <span><bean:write name="batchForm" property="batchId" /></span>)</li> 
						</ul> 
					</div> 
		            <div class="tabContent"> 
						<div id="tabContent1" class="kantab" >
							<ol class="auto">
								<li><label>�����·�:</label><span><bean:write name="batchForm" property="monthly"/></span></li>
							</ol>	
							<ol class="auto">
								<li><label>���㿪ʼʱ��</label><span><bean:write name="batchForm" property="startDate"/></span></li>
								<li><label>�������ʱ��</label><span><bean:write name="batchForm" property="endDate"/></span></li>
		                	</ol>
							<ol class="auto">
								<logic:notEmpty name="batchForm" property="decodeEntityId">
		                			<li><label>����ʵ��</label><span><bean:write name="batchForm" property="decodeEntityId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="batchForm" property="decodeBusinessTypeId">
		                			<li><label>ҵ������</label><span><bean:write name="batchForm" property="decodeBusinessTypeId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:present name="clientVO">
			                		<li><label>�ͻ�ID</label><span><bean:write name="clientVO" property="clientId"/></span></li>
			                		<li><label>�ͻ����ƣ����ģ�</label><span><bean:write name="clientVO" property="nameZH"/></span></li>
			                		<li><label>�ͻ����ƣ�Ӣ�ģ�</label><span><bean:write name="clientVO" property="nameEN"/></span></li>
		                		</logic:present>
		                		<logic:notEmpty name="batchForm" property="orderId">
		                			<li><label>����ID</label><span><bean:write name="batchForm" property="orderId"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li><label>�ͻ�����</label><span><bean:write name="batchForm" property="countClientId"/></span></li>
		                		<li><label>��������</label><span><bean:write name="batchForm" property="countOrderId"/></span></li>
		                	</ol>
		                	<ol class="auto">
		                		<logic:notEmpty name="batchForm" property="description">
		                			<li><label>����</label><span><bean:write name="batchForm" property="description"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
							<ol class="auto">
		                		<li><label>��˾Ӫ��</label><span><bean:write name="batchForm" property="billAmountCompany"/></span></li>
		                		<li><label>��˾�ɱ�</label><span><bean:write name="batchForm" property="costAmountCompany"/></span></li>
		                		<li><label>��˰</label><span><bean:write name="batchForm" property="taxAmount"/></span></li>
		                	</ol>
						</div>
					</div>
               	</fieldset>
               	 </html:form>
			<div id="tableWrapper">
				<jsp:include page="table/listInvoiceHeaderTable.jsp"></jsp:include>          
			</div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_finance_Modules').addClass('current');			
		
		if($('#pageFlag').val().trim()=="Preview"){
			$('#menu_finance_Preview').addClass('selected');
			$('#btnCompound').show();
			$('#btnSub').show();
			$('#titles').text('Ԥ��');
		}else if($('#pageFlag').val().trim()=="Split"){
			$('#menu_finance_Split').addClass('selected');
			$('#btnCompound').hide();
			$('#btnSub').show();
			$('#titles').text('���');
		}else if($('#pageFlag').val().trim()=="Merge"){
			$('#menu_finance_Merge').addClass('selected');
			$('#btnSub').hide();
			$('#btnCompound').show();
			$('#titles').text('�ϲ�');
		}
		
		// �б����¼�
		$('#btnList').click(function(){
			link('systemInvoiceAction.do?proc=list_object&pageFlag='+$('#pageFlag').val());
		});
		//���
		$('#btnSub').click(function(){
			var selectedIds=$('#selectedIds').val();
			if(selectedIds.trim()==''){
				alert("��ѡ��Ҫ��ֵ�һ����¼");
				return ;
			}else{
				var selectedIdArray=selectedIds.split(",");
				if(selectedIdArray.length>1){
					alert("��ѡ��Ҫ��ֵ�һ����¼");
					return ;
				}
			}
			link("systemInvoiceHeaderAction.do?proc=list_objectByHeaderId&invoiceId="+selectedIds);
		});
		//�ϲ�
		$('#btnCompound').click(function(){
			var selectedIds=$('#selectedIds').val();
			if(selectedIds.trim()==''){
				alert("��ѡ��Ҫ�ϲ��ļ�¼");
				return ;
			}else{
				var selectedIdArray=selectedIds.split(",");
				if(selectedIdArray.length<2){
					alert("��ѡ��Ҫ�ϲ��ļ�¼,����2��");
					return ;
				}
			}
			link('systemInvoiceHeaderAction.do?proc=combineInvoice&invoiceId='+selectedIds);
		});
	})(jQuery);
	
	
	function resetForm(){
		$(".searchHeader_clientId").val("");
		$(".searchHeader_clientNameZH").val("");
		$(".searchHeader_clientNameEN").val("");
		$(".searchHeader_orderId").val("");
	}
</script>

