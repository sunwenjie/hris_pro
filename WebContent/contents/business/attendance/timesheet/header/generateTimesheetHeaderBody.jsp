<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="timesheetHeader" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">��������</label>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="addbutton" name="btnSave" id="btnSave" value="����" /> 
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
			</div>
			<html:form action="timesheetHeaderAction.do?proc=add_object" styleClass="timesheetHeader_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="timesheetHeaderForm" property="subAction" />" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label>�·�<em> *</em></label> 
							<html:select property="monthly" styleClass="timesheetHeader_monthly">
								<html:optionsCollection property="last2Months" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>����ʵ��</label> 
							<html:select property="entityId" styleClass="timesheetHeader_entityId">
								<html:optionsCollection property="entities" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>ҵ������</label> 
							<html:select property="businessTypeId" styleClass="timesheetHeader_businessTypeId">
								<html:optionsCollection property="businessTypies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li <logic:equal name="role" value="2">style="display :none;"</logic:equal>>
							<label>�ͻ�ID</label> 
							<html:text property="clientId" maxlength="10" styleClass="timesheetHeader_clientId" styleId="clientId" /> 
							<a onclick="popupClientSearch();" class="kanhandle"><img src="images/search.png" title="�����ͻ���¼" /></a>
						</li>
						<li>
							<label><logic:equal name="role" value="1">����</logic:equal><logic:equal name="role" value="2">�������</logic:equal>ID</label>
							<logic:equal name="role" value="1">
								<html:text property="orderId" maxlength="10" styleClass="timesheetHeader_orderId" styleId="orderId" /> 
								<a onclick="popupOrderSearch();" class="kanhandle"><img src="images/search.png" title="����������¼" /></a>
							</logic:equal>
							<logic:equal name="role" value="2">
								<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="timesheetHeader_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
		   						</logic:notEmpty>
							</logic:equal>
						</li>
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>ID</label> 
							<html:text property="employeeId" maxlength="10" styleClass="timesheetHeader_employeeId" styleId="employeeId"/> 
							<a onclick="popupEmployeeSearch();" class="kanhandle"><img src="images/search.png" title="����<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>��¼" /></a>
						</li>
						<li>
							<label><logic:equal name="role" value="1">����Э��</logic:equal><logic:equal name="role" value="2">�Ͷ���ͬ</logic:equal>ID</label> 
							<html:text property="contractId" maxlength="10" styleId="contractId" styleClass="timesheetHeader_contractId" /> 
							<a onclick="popupContractSearch()" class="kanhandle"><img src="images/search.png" title="����<logic:equal name="role" value="1">����Э��</logic:equal><logic:equal name="role" value="2">�Ͷ���ͬ</logic:equal>��¼" /></a>
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="timesheetHeader_description"></html:textarea>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>

<!-- Employee Popup Box -->
<div id="popupWrapper">
	<jsp:include page="/popup/searchClient.jsp"></jsp:include>
	<jsp:include page="/popup/searchOrder.jsp"></jsp:include>
	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include>
	<jsp:include page="/popup/searchContract.jsp"></jsp:include>
</div>
							
<script type="text/javascript">
	(function($) {
		// ��ʼ���˵�
		$('#menu_attendance_Modules').addClass('current');
		$('#menu_attendance_Timesheet').addClass('selected');
	
     	// ���水ť����¼�
		$('#btnSave').click( function () { 
			var flag = 0;
			
    		flag = flag + validate('timesheetHeader_monthly', true, 'select', 0, 0);
    		
			if(flag == 0){
				submit('timesheetHeader_form');
			}
		});
		
		// �б�ť����¼�
		$('#btnList').click( function () {
			if (agreest())
			link('timesheetHeaderAction.do?proc=list_object');
		});
	})(jQuery);	
</script>
