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

<!-- �޸����͹��������� -->
<logic:equal name="historyVO" property="remark5" value="3">
	<div id="tabContent1"  class="kantab"  >
		<ol id="detailOL" class="auto">
			<%
				out.print( KANUtil.getCompareHTML( passObject.getEmployeeNameZH(), originalObject.getEmployeeNameZH(), ( inHouse ? "Ա��" : "��Ա" ) + "���������ģ�" ) );
				out.print( KANUtil.getCompareHTML( passObject.getEmployeeNameEN(), originalObject.getEmployeeNameEN(), ( inHouse ? "Ա��" : "��Ա" ) + "������Ӣ�ģ�" ) );
				out.print( KANUtil.getCompareHTML( passObject.getEmployeeId(), originalObject.getEmployeeId(), ( inHouse ? "Ա��" : "��Ա" ) + "ID" ) );
				out.print( KANUtil.getCompareHTML( passObject.getCertificateNumber(), originalObject.getCertificateNumber(), "֤������" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeDepartment(), originalObject.getDecodeDepartment(), "��λ" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodePositionId(), originalObject.getDecodePositionId(), "��λ" ) );
				out.print( KANUtil.getCompareHTML( passObject.getContractNameZH(), originalObject.getContractNameZH(), ( inHouse ? "�Ͷ���ͬ" : "������Ϣ" ) + "���ƣ����ģ�" ) );
				out.print( KANUtil.getCompareHTML( passObject.getContractNameEN(), originalObject.getContractNameEN(), ( inHouse ? "�Ͷ���ͬ" : "������Ϣ" ) + "���ƣ�Ӣ�ģ�" ) );
				out.print( KANUtil.getCompareHTML( passObject.getContractStartDate(), originalObject.getContractStartDate(), "��ʼʱ��" ) );
				out.print( KANUtil.getCompareHTML( passObject.getContractEndDate(), originalObject.getContractEndDate(), "����ʱ��" ) );
				out.print( KANUtil.getCompareHTML( passObject.getSbSolutionId(), originalObject.getSbSolutionId(), "�籣�����𷽰�ID" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeSbSolutionId(), originalObject.getDecodeSbSolutionId(), "�籣�����𷽰�����" ) );
				out.print( KANUtil.getCompareHTML( passObject.getVendorName(), originalObject.getVendorName(), "��Ӧ������" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeVendorServiceIds(), originalObject.getDecodeVendorServiceIds(), "��Ӧ�̷���Χ" ) );
				out.print( KANUtil.getCompareHTML( passObject.getStartDate(), originalObject.getStartDate(), "�ӱ�����" ) );
				out.print( KANUtil.getCompareHTML( passObject.getEndDate(), originalObject.getEndDate(), "�˱�����" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeNeedMedicalCard(), originalObject.getDecodeNeedMedicalCard(), "��Ҫ��ҽ����" ) );
				out.print( KANUtil.getCompareHTML( passObject.getMedicalNumber(), originalObject.getMedicalNumber(), "ҽ�����˺�" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeNeedSBCard(), originalObject.getDecodeNeedSBCard(), "��Ҫ�����籣������" ) );  
				out.print( KANUtil.getCompareHTML( passObject.getSbNumber(), originalObject.getSbNumber(), "�籣�������˺�" ) );
				out.print( KANUtil.getCompareHTML( passObject.getFundNumber(), originalObject.getFundNumber(), "�������˺�" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodePersonalSBBurden(), originalObject.getDecodePersonalSBBurden(), "��˾�е������籣������" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeOwner(), originalObject.getDecodeOwner(), "�ͷ���Ա" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeStatus(), originalObject.getDecodeStatus(), "״̬" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDescription(), originalObject.getDescription(), "����" ,"textarea" ) );
			%>
		</ol>
	</div>
	<div id="tabContent2"  class="kantab" style="display: none" >
		<ol id="detail2OL" class="auto">
			<li><label><%=( inHouse ? "Ա��" : "��Ա" ) + "���������ģ�" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNameZH"/>' /></li>
			<li><label><%=( inHouse ? "Ա��" : "��Ա" ) + "������Ӣ�ģ�" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNameEN"/>' /></li>
			<li><label><%=( inHouse ? "Ա��" : "��Ա" ) + "ID" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeId"/>' /></li>
			<li><label>֤������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateNumber"/>' /></li>
			<li><label>��λ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeDepartment"/>' /></li>
			<li><label>��λ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodePositionId"/>' /></li>
			<li><label><%=( inHouse ? "�Ͷ���ͬ" : "������Ϣ" ) + "���ƣ����ģ�" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNameZH"/>' /></li>
			<li><label><%=( inHouse ? "�Ͷ���ͬ" : "������Ϣ" ) + "���ƣ�Ӣ�ģ�" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNameEN"/>' /></li>
			<li><label>��ʼʱ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractStartDate"/>' /></li>
			<li><label>����ʱ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractEndDate"/>' /></li>
			<li><label>�籣�����𷽰�ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="sbSolutionId"/>' /></li>
			<li><label>�籣�����𷽰�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeSbSolutionId"/>' /></li>
			<li><label>��Ӧ������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="vendorName"/>' /></li>
			<li><label>��Ӧ�̷���Χ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeVendorServiceIds"/>' /></li>
			<li><label>�ӱ�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startDate"/>' /></li>
			<li><label>�˱�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="endDate"/>' /></li>
			<li><label>��Ҫ��ҽ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeNeedMedicalCard"/>' /></li>
			<li><label>ҽ�����˺�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="medicalNumber"/>' /></li>
			<li><label>��Ҫ�����籣������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeNeedSBCard"/>' /></li>
			<li><label>�籣�������˺�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="sbNumber"/>' /></li>
			<li><label>�������˺�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="fundNumber"/>' /></li>
			<li><label>��˾�е������籣������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodePersonalSBBurden"/>' /></li>
			<li><label>�ͷ���Ա</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeOwner"/>' /></li>
			<li><label>״̬</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeStatus"/>' /></li>
			<li><label>����</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
		</ol>
	</div>
</logic:equal>

<%-- ���޸����͵��������� --%>
<logic:notEqual name="historyVO" property="remark5" value="3">
	<div id="tabContent1"  class="kantab"  >
		<ol id="detailOL" class="auto">
			<li><label><%=( inHouse ? "Ա��" : "��Ա" ) + "���������ģ�" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNameZH"/>' /></li>
			<li><label><%=( inHouse ? "Ա��" : "��Ա" ) + "������Ӣ�ģ�" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNameEN"/>' /></li>
			<li><label><%=( inHouse ? "Ա��" : "��Ա" ) + "ID" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeId"/>' /></li>
			<li><label>֤������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateNumber"/>' /></li>
			<li><label>��λ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeDepartment"/>' /></li>
			<li><label>��λ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodePositionId"/>' /></li>
			<li><label><%=( inHouse ? "�Ͷ���ͬ" : "������Ϣ" ) + "���ƣ����ģ�" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNameZH"/>' /></li>
			<li><label><%=( inHouse ? "�Ͷ���ͬ" : "������Ϣ" ) + "���ƣ�Ӣ�ģ�" %></label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNameEN"/>' /></li>
			<li><label>��ʼʱ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractStartDate"/>' /></li>
			<li><label>����ʱ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractEndDate"/>' /></li>
			<li><label>�籣�����𷽰�ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="sbSolutionId"/>' /></li>
			<li><label>�籣�����𷽰�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeSbSolutionId"/>' /></li>
			<li><label>��Ӧ������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="vendorName"/>' /></li>
			<li><label>��Ӧ�̷���Χ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeVendorServiceIds"/>' /></li>
			<li><label>�ӱ�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startDate"/>' /></li>
			<li><label>�˱�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="endDate"/>' /></li>
			<li><label>��Ҫ��ҽ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeNeedMedicalCard"/>' /></li>
			<li><label>ҽ�����˺�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="medicalNumber"/>' /></li>
			<li><label>��Ҫ�����籣������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeNeedSBCard"/>' /></li>
			<li><label>�籣�������˺�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="sbNumber"/>' /></li>
			<li><label>�������˺�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="fundNumber"/>' /></li>
			<li><label>��˾�е������籣������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodePersonalSBBurden"/>' /></li>
			<li><label>�ͷ���Ա</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeOwner"/>' /></li>
			<li><label>״̬</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeStatus"/>' /></li>
			<li><label>����</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
		</ol>
		<br>
		<div id="special_info_tab" ></div>
	</div>
</logic:notEqual>

<script type="text/javascript">
	//�����籣��������ϸTabҳ
	loadHtml('#special_info_tab', 'employeeContractSBAction.do?proc=list_special_info_html&noTab=true&employeeSBId=<%= employeeSBId%>&sbSolutionId=<%=sbSolutionId%>', true, null);
</script>



