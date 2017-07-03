<%@page import="java.util.Map"%>
<%@page import="com.kan.hro.domain.biz.employee.EmployeeReportVO"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/kan.js"></script>
<link rel="stylesheet" href="mobile/css/bootstrap.min.css">
<link href="plugins/PluginDatetime/css/mobiscroll.origin.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="plugins/PluginDatetime/js/mobiscroll.custom-2.5.2.min.js" data-main="scripts/main"></script>
<style type="text/css">
	.container{
		padding-top: 10px;
	}
	.btn{
		margin-top:15px;
		width: 100%;
	}
	.operButton{
		margin-bottom: 20px;
	}
	.back
</style>
<title><bean:message bundle="wx" key="wx.logon.title" /></title>
</head>
<body>
	<div class="container">
	<%
			final EmployeeReportVO employeeReportVO = (EmployeeReportVO)request.getAttribute("employeeReportVO");
	%>
	<form action="staffAction.do?proc=modify_object_mobile" method="post" class="main_form">
		<input type="hidden" name="subAction" class="subAction" id="subAction" value="viewObject"/>
		<input type="hidden" name="id" class="id" id="id" value="<bean:write name='staffForm' property='encodedId'/>"/>
		<%= BaseAction.addToken(request) %>
		<fieldset disabled>
		<div class="form-group">
			<label for="nameZH">员工姓名(Chinese Name)</label>
			<input type="text" class="form-control" id="nameZH" value="<bean:write name='staffForm' property='nameZH' />" >
		</div>
		<div class="form-group">
			<label for="nameEN">EmployeeName(English Name)</label>
			<input type="text" class="form-control" id="nameEN" value="<bean:write name='staffForm' property='nameEN' />" >
		</div>
		<div class="form-group">
			<label for="HRO_BIZ_EMPLOYEE_IN_HOUSE_jiancheng">姓名简称(Short Name)</label>
			<%
				final Map<String,Object> remark1 = employeeReportVO.getDynaColumns();
				final String jiancheng = String.valueOf(remark1.get("jiancheng"));
			%>
			<input type="text" class="form-control" id="HRO_BIZ_EMPLOYEE_IN_HOUSE_jiancheng" value="<%=jiancheng %>" >
		</div>
		<div class="form-group">
			<label for="parentBranchNameEN">BU/Function</label>
			<input type="text" class="form-control" id="parentBranchNameEN" value="<bean:write name="employeeReportVO" property="parentBranchNameEN" />" >
		</div>
		<div class="form-group">
			<label for="branchNameZH">部门</label>
			<input type="text" class="form-control" id="branchNameZH" value="<bean:write name="employeeReportVO" property="branchNameZH" />" >
		</div>
		<div class="form-group">
			<label for="branchNameEN">Department</label>
			<input type="text" class="form-control" id="branchNameEN" value="<bean:write name="employeeReportVO" property="branchNameEN" />" >
		</div>
		<div class="form-group">
			<label for="bangongdidian">办公地点/Location</label>
			<%
				final String bangongdidian = String.valueOf(remark1.get("bangongdidian"));
			%>
			<input type="text" class="form-control" id="bangongdidian" value="<%=bangongdidian %>" >
		</div>
		<div class="form-group">
			<label for="decode_tempPositionIdsZH">职位(Working Title/Position)</label>
			<input type="text" class="form-control" id="decode_tempPositionIdsZH" value="<bean:write name="employeeReportVO" property="decode_tempPositionIdsZH" />" >
		</div>	
		<div class="form-group">
			<label for="decode_tempPositionIdsEN">Job Role</label>
			<%
				final String jobrole = String.valueOf(remark1.get("jobrole"));
			%>
			<input type="text" class="form-control" id="decode_tempPositionIdsEN" value="<%=jobrole %>" >
		</div>
		<div class="form-group">
			<label for="decode_tempPositionGradeIds">职级/Job Grade</label>
			<input type="text" class="form-control" id="decode_tempPositionGradeIds" value="<bean:write name="employeeReportVO" property="decode_tempPositionGradeIds" />" >
		</div>
		<div class="form-group">
			<label for="yewuhuibaoxianjingli">直接汇报线/Direct Report Manager</label>
			<%
				final String yewuhuibaoxianjingli = String.valueOf(remark1.get("yewuhuibaoxianjingli"));
			%>
			<input type="text" class="form-control" id="yewuhuibaoxianjingli" value="<%=yewuhuibaoxianjingli %>" >
		</div>
		<div class="form-group" style="display: none;">
			<label for="decodeCertificateType">证件类型/ID Type</label>
			<input type="text" class="form-control" id="decodeCertificateType" value="<bean:write name="employeeReportVO" property="decodeCertificateType" />" >
		</div>	
		<div class="form-group" style="display: none;">
			<label for="certificateNumber">证件号码/ID NO.</label>
			<input type="text" class="form-control" id="certificateNumber" value="<bean:write name="employeeReportVO" property="certificateNumber" />" >
		</div>
		<div class="form-group">
			<label for="certificateEndDate">证件到期日期/Visa Expired Date</label>
			<input type="text" class="form-control" id="certificateEndDate" value="<bean:write name="employeeReportVO" property="certificateEndDate" />" >
		</div>
		<div class="form-group">
			<label for="hasForeignerWorkLicence">外国人就业许可证/Working Visa</label>
			<%
				final String hasForeignerWorkLicence = employeeReportVO.getDecodeFlag(employeeReportVO.getHasForeignerWorkLicence());
			%>
			<input type="text" class="form-control" id="hasForeignerWorkLicence" value="<%=hasForeignerWorkLicence %>" >
		</div>
		<div class="form-group">
			<label for="hasResidenceLicence">居住证/Resident Visa</label>
			<%
				final String hasResidenceLicence = employeeReportVO.getDecodeFlag(employeeReportVO.getHasResidenceLicence());
			%>
			<input type="text" class="form-control" id="hasResidenceLicence" value="<%=hasResidenceLicence %>" >
		</div>									
		<div class="form-group" style="display: none;">
			<label for="birthday">生日/Date of Birth</label>
			<input type="text" class="form-control" id="birthday" value="<bean:write name="employeeReportVO" property="birthday" />" >
		</div>
		</fieldset>
		<!-- editable -->
		<div class="form-group">
			<label for="maritalStatus">婚姻状况/Marital Status</label>
			<select class="form-control form-editable" name="maritalStatus" id="maritalStatus" disabled>
			<%=KANUtil.getOptionHTML(employeeReportVO.getMaritalStatuses(),"maritalStatus_options_"+employeeReportVO.getMaritalStatus(),employeeReportVO.getMaritalStatus())%>
			</select>
		</div>
		<div class="form-group">
			<label for="highestEducation">最高学历/Education Degree</label>
			<input type="text" class="form-control" id="highestEducation" name="highestEducation" value="<bean:write name="employeeReportVO" property="decodeHighestEducation" />" disabled>
		</div>
		<div class="form-group">
			<label for="schoolNames">学校名称/School</label>
			<input type="text" class="form-control" id="schoolNames" name="schoolNames" value="<bean:write name="employeeReportVO" property="schoolNames" />" disabled>
		</div>
		<div class="form-group">
			<label for="majors">专业/Major</label>
			<input type="text" class="form-control" id="majors" name="majors" value="<bean:write name="employeeReportVO" property="majors" />" disabled>
		</div>
		<div class="form-group">
			<label for="graduateDates">毕业年度/Graduate Year</label>
			<input type="text" class="form-control" id="graduateDates" name="graduateDates" value="<bean:write name="employeeReportVO" property="decodeGraduateDates" />" disabled>
		</div>
		<div class="form-group">
			<label for="residencyAddress">户籍地址/Address (Home Town)</label>
			<input type="text" class="form-control form-editable" id="residencyAddress" name="residencyAddress" value="<bean:write name="employeeReportVO" property="residencyAddress" />" disabled>
		</div>
		<div class="form-group">
			<label for="personalAddress">当前住址/Address (Current)</label>
			<input type="text" class="form-control form-editable" id="personalAddress" name="personalAddress" value="<bean:write name="employeeReportVO" property="personalAddress" />" disabled>
		</div>
		<div class="form-group">
			<label for="mobile1">手机号码/Mobile No.</label>
			<input type="text" class="form-control form-editable" id="mobile1" name="mobile1" value="<bean:write name="employeeReportVO" property="mobile1" />" disabled>
		</div>
		<div class="form-group">
			<label for="phone1">家居电话/Home No.</label>
			<input type="text" class="form-control form-editable" id="phone1" name="phone1" value="<bean:write name="employeeReportVO" property="phone1" />" disabled>
		</div>
		<div class="form-group">
			<label for="emergencyNames">紧急联系人/Emergency Contact</label>
			<input type="text" class="form-control" id="emergencyNames" name="emergencyNames" value="<bean:write name="employeeReportVO" property="emergencyNames" />" disabled>
		</div>
		<div class="form-group">
			<label for="relationshipIds">与紧急联系人关系/Relations</label>
			<input type="text" class="form-control" id="relationshipIds" name="relationshipIds" value="<bean:write name="employeeReportVO" property="decodeRelationshipIds" />" disabled>
		</div>
		<div class="form-group">
			<label for="decodeContactType">联系电话/Contact No.</label>
			<input type="text" class="form-control" id="decodeContactType" name="decodeContactType" value="<bean:write name="employeeReportVO" property="decodeContactType" />" disabled>
		</div>
		<div class="form-group">
			<label for="bizEmail">公司邮箱/iClick Mail Account</label>
			<input type="text" class="form-control" id="bizEmail" name="bizEmail" value="<bean:write name="employeeReportVO" property="bizEmail" />" disabled>
		</div>
		<div class="form-group">
			<label for="personalEmail">个人邮箱/Individual Mail Address</label>
			<input type="text" class="form-control form-editable" id="personalEmail" name="personalEmail" value="<bean:write name="employeeReportVO" property="personalEmail" />" disabled>
		</div>
		<div class="form-group" style="display: none;">
			<label for="bankId">银行名称/Bank</label>
			<select class="form-control" name="bankId" id="bankId" disabled>
			<%=KANUtil.getOptionHTML(employeeReportVO.getBanks(),"bankId_options_"+employeeReportVO.getBankId(),employeeReportVO.getBankId())%>
			</select>
			
			
		</div>
		<div class="form-group" style="display: none;">
			<label for="bankAccount">银行帐号/Bank Account</label>
			<input type="text" class="form-control" id="bankAccount" name="bankAccount" value="<bean:write name="employeeReportVO" property="bankAccount" />" disabled>
		</div>
		
		<!-- editable -->
		<div class="form-group">
			<label for="startWorkDate">首次进入集团时间/Seniority Date</label>
			<input type="text" class="form-control" id="startWorkDate" value="<bean:write name="employeeReportVO" property="startWorkDate" />" disabled>
		</div>	
		<div class="form-group">
			<label for="dushenzinvzhenglingquri">独生子女证领取日/Date for granting one-child certificate</label>
			<%
				final String dushenzinvzhenglingquri = String.valueOf(remark1.get("dushenzinvzhenglingquri"));
			%>
			<input type="text" class="form-control form-editable" name="dushenzinvzhenglingquri" id="dushenzinvzhenglingquri" value="<%=dushenzinvzhenglingquri %>" disabled>
		</div>
		<div class="form-group">
			<label for="jiehunzhenglingquri">结婚证领取日/Date for granting marriage certificate</label>
			<%
				final String jiehunzhenglingquri = String.valueOf(remark1.get("jiehunzhenglingquri"));
			%>
			<input type="text" class="form-control form-editable" name="jiehunzhenglingquri" id="jiehunzhenglingquri" value="<%=jiehunzhenglingquri %>" >
		</div>
		<fieldset disabled>									
		<div class="form-group" style="display: none;">
			<label for="zuijingongzuogongsi">最近工作公司/Last Employer</label>
			<%
				final String zuijingongzuogongsi = String.valueOf(remark1.get("zuijingongzuogongsi"));
			%>
			<input type="text" class="form-control" id="zuijingongzuogongsi" value="<%=zuijingongzuogongsi %>" >
		</div>
		<div class="form-group" style="display: none;">
			<label for="zuijingongzuozhiwei">最近工作职位/Last Position</label>
			<%
				final String zuijingongzuozhiwei = String.valueOf(remark1.get("zuijingongzuozhiwei"));
			%>
			<input type="text" class="form-control" id="zuijingongzuozhiwei" value="<%=zuijingongzuozhiwei %>" >
		</div>	

		<span id="helpBlock" class="help-block">===About Salary=============</span>		
		<logic:present name="salarys">
			<logic:iterate id="item" name="salarys" indexId="number">
			<!-- 年终奖,年终年%,手机补贴不显示 -->
			    <logic:notEqual value="18" name="item" property="mappingId">
			    <logic:notEqual value="10291" name="item" property="mappingId">
			    <logic:notEqual value="10143" name="item" property="mappingId">
				<logic:iterate id="employeeContractSalary" name="employeeReportVO" property="salarys">
					<logic:equal name="employeeContractSalary" property="mappingId" value="${item.mappingId}">
						<!-- start -->
						<div class="form-group">
							<label class="control-label">${item.mappingValue}</label>
							<div class="">
						      <p class="form-control-static"><bean:write name="employeeContractSalary" property="mappingValue" /></p>
						    </div>
						</div>
						<!-- end -->
					</logic:equal>
				</logic:iterate>
				</logic:notEqual>
				</logic:notEqual>
				</logic:notEqual>
			</logic:iterate>
		</logic:present>
		</fieldset>	
		<button type="button" class="btn btn-warning backButton" onclick="btnBack();">Back</button>
		<button type="submit" class="btn btn-success operButton">Submit</button>
	</form>
	</div>
</body>

<script type="text/javascript">
	(function($) {	
		enableStaffForm();
		
		var optDate = {  
 	            preset: 'date', //日期  datetim带小时分钟
 	            theme: 'android-ics light', //皮肤样式  
 	            display: 'bottom', //显示方式   
 	            mode: 'scroller', //日期选择模式  
 	            showNow: true,  
 	            nowText: "Today",  
 				yearText: "Year",
 				monthText: "Month",
 				dayText: "Day",
 				setText: 'Confirm',
 				cancelText: 'Cancel',
 				dateFormat: 'yy-mm-dd',
 				dateOrder: 'yymmdd'
 	        };  

		$("#jiehunzhenglingquri").mobiscroll(optDate);
		$('#dushenzinvzhenglingquri').mobiscroll(optDate);
		$("#jiehunzhenglingquri").removeAttr('readonly');
		$("#dushenzinvzhenglingquri").removeAttr('readonly');
		
		
	})(jQuery);
	
	function enableStaffForm(){
		$('.form-editable').each(function(){
			$(this).removeAttr('disabled');
			$(this).parent().addClass('has-success');
		});
	};
	
	function btnClick(){
		// 如果操作是编辑
		 if($("#operButton").hasClass("btn-warning")){
			$("#operButton").removeClass("btn-warning").addClass("btn-success");
			$("#operButton").text("Submit");
			enableStaffForm();
		}else{
			$(".main_form").submit();
		}
	};
	
	function btnBack(){
		link('securityAction.do?proc=index_mobile');
	};
</script>
</html>
