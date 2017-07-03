<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractCBVO"%>
<%@ page import="com.kan.base.util.KANUtil"%>

<%
	final boolean inHouse = request.getAttribute( "role" ).equals( "2" );
	final EmployeeContractCBVO passObject = ( EmployeeContractCBVO )request.getAttribute( "passObject" );
	final EmployeeContractCBVO originalObject = ( EmployeeContractCBVO )request.getAttribute( "originalObject" );
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
				out.print( KANUtil.getCompareHTML( passObject.getSolutionId(), originalObject.getSolutionId(), "商保方案ID" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeSolutionId(), originalObject.getDecodeSolutionId(), "商保方案名称" ) );
				out.print( KANUtil.getCompareHTML( passObject.getStartDate(), originalObject.getStartDate(), "加保日期" ) );
				out.print( KANUtil.getCompareHTML( passObject.getEndDate(), originalObject.getEndDate(), "退保日期" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeFreeShortOfMonth(), originalObject.getDecodeFreeShortOfMonth(), "不全月免费" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeChargeFullMonth(), originalObject.getDecodeChargeFullMonth(), "按全月计费" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeOwner(), originalObject.getDecodeOwner(), "客服人员" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeStatus(), originalObject.getDecodeStatus(), "状态" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDescription(), originalObject.getDescription(), "描述", "textarea" ) );
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
			<li><label>商保方案ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="solutionId"/>' /></li>
			<li><label>商保方案名称</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeSolutionId"/>' /></li>
			<li><label>加保日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startDate"/>' /></li>
			<li><label>退保日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="endDate"/>' /></li>
			<li><label>不全月免费</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeFreeShortOfMonth"/>' /></li>
			<li><label>按全月计费</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeChargeFullMonth"/>' /></li>
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
			<li><label>商保方案ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="solutionId"/>' /></li>
			<li><label>商保方案名称</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeSolutionId"/>' /></li>
			<li><label>加保日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startDate"/>' /></li>
			<li><label>退保日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="endDate"/>' /></li>
			<li><label>不全月免费</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeFreeShortOfMonth"/>' /></li>
			<li><label>按全月计费</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeChargeFullMonth"/>' /></li>
			<li><label>客服人员</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeOwner"/>' /></li>
			<li><label>状态</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeStatus"/>' /></li>
			<li><label>描述</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
		</ol>
	</div>
</logic:notEqual>
