<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractSBVO"%>
<%@ page import="com.kan.base.util.KANUtil"%>

<%
	final boolean inHouse = request.getAttribute( "role" ).equals( "2" );
	final EmployeeContractSBVO passObject = ( EmployeeContractSBVO )request.getAttribute( "passObject");
	final EmployeeContractSBVO originalObject = ( EmployeeContractSBVO )request.getAttribute( "originalObject" );
	String employeeSBId = null;
	String sbSolutionId = null;
	if( passObject != null )
	{
	   employeeSBId = passObject.getEmployeeSBId();
	   sbSolutionId = passObject.getSbSolutionId();
	}
%>

<!-- 修改类型工作流审批 -->
<logic:equal name="historyVO" property="remark5" value="3">
	<div id="tabContent1"  class="kantab"  >
		<ol id="detailOL" class="auto">
			<%
				out.print( KANUtil.getCompareHTML( passObject.getEmployeeNameZH(), originalObject.getEmployeeNameZH(), ( inHouse ? "员工" : "雇员" ) + "姓名（中文）" ) );
				out.print( KANUtil.getCompareHTML( passObject.getEmployeeNameEN(), originalObject.getEmployeeNameEN(), ( inHouse ? "员工" : "雇员" ) + "姓名（英文）" ) );
				out.print( KANUtil.getCompareHTML( passObject.getEmployeeId(), originalObject.getEmployeeId(), ( inHouse ? "员工" : "雇员" ) + "ID" ) );
				out.print( KANUtil.getCompareHTML( passObject.getCertificateNumber(), originalObject.getCertificateNumber(), "证件号码" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeDepartment(), originalObject.getDecodeDepartment(), "单位" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodePositionId(), originalObject.getDecodePositionId(), "岗位" ) );
				out.print( KANUtil.getCompareHTML( passObject.getContractNameZH(), originalObject.getContractNameZH(), ( inHouse ? "劳动合同" : "派送信息" ) + "名称（中文）" ) );
				out.print( KANUtil.getCompareHTML( passObject.getContractNameEN(), originalObject.getContractNameEN(), ( inHouse ? "劳动合同" : "派送信息" ) + "名称（英文）" ) );
				out.print( KANUtil.getCompareHTML( passObject.getContractStartDate(), originalObject.getContractStartDate(), "开始时间" ) );
				out.print( KANUtil.getCompareHTML( passObject.getContractEndDate(), originalObject.getContractEndDate(), "结束时间" ) );
				out.print( KANUtil.getCompareHTML( passObject.getSbSolutionId(), originalObject.getSbSolutionId(), "社保公积金方案ID" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeSbSolutionId(), originalObject.getDecodeSbSolutionId(), "社保公积金方案名称" ) );
				out.print( KANUtil.getCompareHTML( passObject.getVendorName(), originalObject.getVendorName(), "供应商名称" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeVendorServiceIds(), originalObject.getDecodeVendorServiceIds(), "供应商服务范围" ) );
				out.print( KANUtil.getCompareHTML( passObject.getStartDate(), originalObject.getStartDate(), "加保日期" ) );
				out.print( KANUtil.getCompareHTML( passObject.getEndDate(), originalObject.getEndDate(), "退保日期" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeNeedMedicalCard(), originalObject.getDecodeNeedMedicalCard(), "需要办医保卡" ) );
				out.print( KANUtil.getCompareHTML( passObject.getMedicalNumber(), originalObject.getMedicalNumber(), "医保卡账号" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeNeedSBCard(), originalObject.getDecodeNeedSBCard(), "需要办理社保公积金卡" ) );  
				out.print( KANUtil.getCompareHTML( passObject.getSbNumber(), originalObject.getSbNumber(), "社保公积金卡账号" ) );
				out.print( KANUtil.getCompareHTML( passObject.getFundNumber(), originalObject.getFundNumber(), "公积金账号" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodePersonalSBBurden(), originalObject.getDecodePersonalSBBurden(), "公司承担个人社保公积金" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeOwner(), originalObject.getDecodeOwner(), "客服人员" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeStatus(), originalObject.getDecodeStatus(), "状态" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDescription(), originalObject.getDescription(), "描述" ,"textarea" ) );
			%>
		</ol>
	</div>
	<div id="tabContent2"  class="kantab" style="display: none" >
		<ol id="detail2OL" class="auto">
			<li><label><%=( inHouse ? "员工" : "雇员" ) + "姓名（中文）" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNameZH"/>' /></li>
			<li><label><%=( inHouse ? "员工" : "雇员" ) + "姓名（英文）" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNameEN"/>' /></li>
			<li><label><%=( inHouse ? "员工" : "雇员" ) + "ID" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeId"/>' /></li>
			<li><label>证件号码</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateNumber"/>' /></li>
			<li><label>单位</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeDepartment"/>' /></li>
			<li><label>岗位</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodePositionId"/>' /></li>
			<li><label><%=( inHouse ? "劳动合同" : "派送信息" ) + "名称（中文）" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNameZH"/>' /></li>
			<li><label><%=( inHouse ? "劳动合同" : "派送信息" ) + "名称（英文）" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNameEN"/>' /></li>
			<li><label>开始时间</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractStartDate"/>' /></li>
			<li><label>结束时间</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractEndDate"/>' /></li>
			<li><label>社保公积金方案ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="sbSolutionId"/>' /></li>
			<li><label>社保公积金方案名称</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeSbSolutionId"/>' /></li>
			<li><label>供应商名称</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="vendorName"/>' /></li>
			<li><label>供应商服务范围</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeVendorServiceIds"/>' /></li>
			<li><label>加保日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startDate"/>' /></li>
			<li><label>退保日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="endDate"/>' /></li>
			<li><label>需要办医保卡</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeNeedMedicalCard"/>' /></li>
			<li><label>医保卡账号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="medicalNumber"/>' /></li>
			<li><label>需要办理社保公积金卡</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeNeedSBCard"/>' /></li>
			<li><label>社保公积金卡账号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="sbNumber"/>' /></li>
			<li><label>公积金账号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="fundNumber"/>' /></li>
			<li><label>公司承担个人社保公积金</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodePersonalSBBurden"/>' /></li>
			<li><label>客服人员</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeOwner"/>' /></li>
			<li><label>状态</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeStatus"/>' /></li>
			<li><label>描述</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
		</ol>
	</div>
</logic:equal>

<%-- 非修改类型的审批流程 --%>
<logic:notEqual name="historyVO" property="remark5" value="3">
	<div id="tabContent1"  class="kantab"  >
		<ol id="detailOL" class="auto">
			<li><label><%=( inHouse ? "员工" : "雇员" ) + "姓名（中文）" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNameZH"/>' /></li>
			<li><label><%=( inHouse ? "员工" : "雇员" ) + "姓名（英文）" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNameEN"/>' /></li>
			<li><label><%=( inHouse ? "员工" : "雇员" ) + "ID" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeId"/>' /></li>
			<li><label>证件号码</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateNumber"/>' /></li>
			<li><label>单位</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeDepartment"/>' /></li>
			<li><label>岗位</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodePositionId"/>' /></li>
			<li><label><%=( inHouse ? "劳动合同" : "派送信息" ) + "名称（中文）" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNameZH"/>' /></li>
			<li><label><%=( inHouse ? "劳动合同" : "派送信息" ) + "名称（英文）" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNameEN"/>' /></li>
			<li><label>开始时间</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractStartDate"/>' /></li>
			<li><label>结束时间</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractEndDate"/>' /></li>
			<li><label>社保公积金方案ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="sbSolutionId"/>' /></li>
			<li><label>社保公积金方案名称</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeSbSolutionId"/>' /></li>
			<li><label>供应商名称</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="vendorName"/>' /></li>
			<li><label>供应商服务范围</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeVendorServiceIds"/>' /></li>
			<li><label>加保日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startDate"/>' /></li>
			<li><label>退保日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="endDate"/>' /></li>
			<li><label>需要办医保卡</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeNeedMedicalCard"/>' /></li>
			<li><label>医保卡账号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="medicalNumber"/>' /></li>
			<li><label>需要办理社保公积金卡</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeNeedSBCard"/>' /></li>
			<li><label>社保公积金卡账号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="sbNumber"/>' /></li>
			<li><label>公积金账号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="fundNumber"/>' /></li>
			<li><label>公司承担个人社保公积金</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodePersonalSBBurden"/>' /></li>
			<li><label>客服人员</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeOwner"/>' /></li>
			<li><label>状态</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeStatus"/>' /></li>
			<li><label>描述</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
		</ol>
		<br>
		<div id="special_info_tab" ></div>
	</div>
</logic:notEqual>

<script type="text/javascript">
	//加载社保公积金明细Tab页
	loadHtml('#special_info_tab', 'employeeContractSBAction.do?proc=list_special_info_html&noTab=true&employeeSBId=<%= employeeSBId%>&sbSolutionId=<%=sbSolutionId%>', true, null);
</script>



