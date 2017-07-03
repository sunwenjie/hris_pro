<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANConstants"%> 
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.domain.security.StaffDTO"%> 
<%@ page import="com.kan.base.web.renders.system.ModuleRender"%> 
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<script type="text/javascript">
	function clickMenu(){
		if( $('.menuSSUl').is(":visible") ) {
			$('.menuSSUl').hide();
		} else {
			$('.menuSSUl').show();
		}
	};
</script>

<div class="kanMenuDiv menu_s">
	<div></div>
	<a href="#" class="menuLink" onclick="clickMenu();" style="display: none"><bean:message bundle="system" key="menu.default" /></a>
	<ul class="menuSSUl">
		<logic:equal name="userId" value="1">
			<li id="menu_system_Modules"><a href="#">ϵͳ</a>
				<ul>
					<li id="menu_system_Account"><a href="#" onclick="link('accountAction.do?proc=list_object');">�˻�</a></li>
					<li id="menu_system_Module"><a href="#" onclick="link('moduleAction.do?proc=list_object');">ģ��</a></li>
					<li id="menu_system_Right"><a href="#" onclick="link('rightAction.do?proc=list_object');">Ȩ��</a></li>
					<li id="menu_system_Rule"><a href="#" onclick="link('ruleAction.do?proc=list_object');">����</a></li>
					<li id="menu_system_City"><a href="#" onclick="link('countryAction.do?proc=list_object');">����</a></li>
					<li id="menu_system_Calendar"><a href="#" onclick="link('calendarHeaderAction.do?proc=list_object')">����</a></li>
					<li id="menu_system_WorkShift"><a href="#" onclick="link('shiftHeaderAction.do?proc=list_object')">�Ű�</a></li>
					<li id="menu_system_SB"><a href="#" onclick="link('socialBenefitHeaderAction.do?proc=list_object');">�籣������</a></li>
					<li id="menu_system_CB"><a href="#" onclick="link('commercialBenefitSolutionHeaderAction.do?proc=list_object');">�̱�</a></li>
					<li id="menu_system_IncomeTax"><a href="#" class="arrow">��˰</a>
						<ul>
							<li id="menu_system_IncomeTaxBase"><a href="#" onclick="link('incomeTaxBaseAction.do?proc=list_object')">����</a></li>
							<li id="menu_system_IncomeTaxRate"><a href="#" onclick="link('incomeTaxRangeHeaderAction.do?proc=list_object')">˰��</a></li>
						</ul>
					</li>
					<li id="menu_system_Item"><a href="#" onclick="link('itemAction.do?proc=list_object')">��Ŀ</a></li>
					<li id="menu_system_Bank"><a href="#" onclick="link('bankAction.do?proc=list_object')">����</a></li>
					<li id="menu_system_SMSConfig"><a href="#" onclick="link('smsConfigAction.do?proc=list_object');">����</a></li>
					<li id="menu_system_Constant"><a href="#" onclick="link('constantAction.do?proc=list_object');">����</a></li>
				</ul> 
			</li>
			<li id="menu_define_Modules"><a href="#">�Զ���</a>
				<ul>
					<li id="menu_define_Table"><a href="#" onclick="link('tableAction.do?proc=list_object');">ģ���ֵ�</a></li>
					<li id="menu_define_Column"><a href="#" onclick="link('columnAction.do?proc=list_object');">�ֶ�</a></li>
					<li id="menu_define_ColumnGroup"><a href="#" onclick="link('columnGroupAction.do?proc=list_object');">�ֶ���</a></li>
					<li id="menu_define_table_relationt"><a href="#" onclick="link('tableRelationAction.do?proc=list_object');">�����</a></li>
				</ul> 
			</li>
			<li id="menu_workflow_modules"><a href="#" onclick="link('workflowModuleAction.do?proc=list_object');"><b>������</b></a></li>
			<li id="menu_bill_modules"><a href="#">�˵�</a></li>
		</logic:equal>
		<logic:notEqual name="userId" value="1">
			
			<!-- ��Ӧ�̵�¼�˵� -->
			<logic:equal name="role" value="3" >
				<li id="menu_vendor_Modules"><a href="#">��Ӧ��</a>
					<ul>
						<li id="menu_vendor_Vendor"><a href="#" onclick="link('vendorAction.do?proc=to_objectModify_inVendor');">��Ӧ��</a></li>
						<li id="menu_vendor_VendorImport"><a href="#" onclick="link('vendorSBAction.do?proc=list_object');">��Ӧ���籣��������</a></li>
					</ul> 
				</li>
			</logic:equal>
			
			<!-- �ͻ���¼�˵� -->
			<logic:equal name="role" value="4" >
				<%= ModuleRender.getClientModuleMenu(request) %>
			</logic:equal>
			<logic:equal name="role" value="5" >
				<%= ModuleRender.getClientModuleMenu(request) %>
			</logic:equal>
			
			<%-- <!-- Ա����¼�˵� -->
			<logic:equal name="role" value="5" >
						<li id="menu_employee_BaseInfo"><a href="#" onclick="link('employeeAction.do?proc=to_objectModify');">��Ա��Ϣ</a></li>
						<li id="menu_employee_Contract"><a href="#" onclick="link('employeeContractAction.do?proc=list_object&flag=2&sortColumn=contractId&sortOrder=desc');">����Э��</a></li>
						<li id="menu_employee_Leave"><a href="#" onclick="link('leaveHeaderAction.do?proc=list_object');">���</a></li>
						<li id="menu_employee_OT"><a href="#" onclick="link('otHeaderAction.do?proc=list_object');">�Ӱ�</a></li>
						<li id="menu_employee_Salary"><a href="#" onclick="link('payslipViewAction.do?proc=list_object');">���ʵ�</a></li>
						<li id="menu_employee_Salary"><a href="#" onclick="link('sbBillViewAction.do?proc=list_object');">�籣��</a></li>
						
			</logic:equal> --%>
			
			<!-- ������¼�˵� -->
			<logic:notEqual name="role" value="3">
				<logic:notEqual name="role" value="4">
					<logic:notEqual name="role" value="5">
						<%= ModuleRender.getModuleMenu(request) %>
					</logic:notEqual>
				</logic:notEqual>
			</logic:notEqual>
			
			
		</logic:notEqual>
	</ul>
	<!-- first level -->
	
	<ul class="noticeNumber" style="cursor: pointer">
	<logic:notEqual name="role" value="4">
		<logic:notEqual name="role" value="5">
			<li><img src="images/home.png" width="15" height="14" title="<bean:message bundle="public" key="img.title.tips.home" />" onclick="link('dashboardAction.do?proc=showDashboard');" /> &nbsp; </li>
			<li><img src="images/task.png" width="16" height="14" title="<bean:message bundle="public" key="img.title.tips.todo" />" onclick="link('workflowActualAction.do?proc=list_object_unfinished&actualStepStatus=2');" /></li>
			<li id="sys_taskInfo" class="info" onclick="link('workflowActualAction.do?proc=list_object_unfinished&actualStepStatus=2');">(0)</li>
		</logic:notEqual>
		<li><img src="images/info.png" width="14" height="14" title="<bean:message bundle="public" key="img.title.tips.system.message" />" onclick="link('messageInfoAction.do?proc=list_receive&receptionStatus=2');" /></li>
		<li id="sys_messageInfo" onclick="link('messageInfoAction.do?proc=list_receive&receptionStatus=2');"></li>
	</logic:notEqual>
	</ul>
	
<script type="text/javascript">
var MESSAGE_HANDLER = function (){
	$.ajax({
		url: 'messageInfoAction.do?proc=get_notReadCount&date='+new Date(),
		dataType:'json',
		success: function(data){
			var sum = 0 ;
			var title = "";
			for(var i = 0; i < data.length;i++){
				var accountData = data[i];
				sum+=accountData.count;
				title+=accountData.accountId+"("+accountData.count+")\r\n";
			}
			$('#sys_messageInfo').html("<span title='"+title+"'>(" + sum + ")</span>");
		}
	});
};

var TASKINFO_HANDLER =  function (){
	$.ajax({
		url: 'workflowActualAction.do?proc=get_notReadCount&date='+new Date(),
		dataType:'json',
		success: function(data){
			var sum = 0 ;
			var title = "";
			for(var i = 0; i < data.length;i++){
				var accountData = data[i];
				sum+=accountData.count;
				title+=accountData.accountId+"("+accountData.count+")\r\n";
			}
			$('#sys_taskInfo').html("<span title='"+title+"'>(" + sum + ")</span>");
		}
	});
};
	
	(function($) {
		// ���룬3���Ӷ�ʱˢ��һ��
		var refreshTime = 1000 * 60 * 3;

		// ���ؼ�ִ��
		MESSAGE_HANDLER();
		TASKINFO_HANDLER();
		// �ӳ�һ��ʱ�����
		setInterval( MESSAGE_HANDLER, refreshTime );
		setInterval( TASKINFO_HANDLER, refreshTime );   
	})(jQuery);
</script>
</div>

<!-- menu performance -->

