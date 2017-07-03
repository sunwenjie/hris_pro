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
				out.print( KANUtil.getCompareHTML( passObject.getSolutionId(), originalObject.getSolutionId(), "�̱�����ID" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeSolutionId(), originalObject.getDecodeSolutionId(), "�̱���������" ) );
				out.print( KANUtil.getCompareHTML( passObject.getStartDate(), originalObject.getStartDate(), "�ӱ�����" ) );
				out.print( KANUtil.getCompareHTML( passObject.getEndDate(), originalObject.getEndDate(), "�˱�����" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeFreeShortOfMonth(), originalObject.getDecodeFreeShortOfMonth(), "��ȫ�����" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeChargeFullMonth(), originalObject.getDecodeChargeFullMonth(), "��ȫ�¼Ʒ�" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeOwner(), originalObject.getDecodeOwner(), "�ͷ���Ա" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDecodeStatus(), originalObject.getDecodeStatus(), "״̬" ) );
				out.print( KANUtil.getCompareHTML( passObject.getDescription(), originalObject.getDescription(), "����", "textarea" ) );
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
			<li><label>�̱�����ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="solutionId"/>' /></li>
			<li><label>�̱���������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeSolutionId"/>' /></li>
			<li><label>�ӱ�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startDate"/>' /></li>
			<li><label>�˱�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="endDate"/>' /></li>
			<li><label>��ȫ�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeFreeShortOfMonth"/>' /></li>
			<li><label>��ȫ�¼Ʒ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeChargeFullMonth"/>' /></li>
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
			<li><label>�̱�����ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="solutionId"/>' /></li>
			<li><label>�̱���������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeSolutionId"/>' /></li>
			<li><label>�ӱ�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startDate"/>' /></li>
			<li><label>�˱�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="endDate"/>' /></li>
			<li><label>��ȫ�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeFreeShortOfMonth"/>' /></li>
			<li><label>��ȫ�¼Ʒ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeChargeFullMonth"/>' /></li>
			<li><label>�ͷ���Ա</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeOwner"/>' /></li>
			<li><label>״̬</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeStatus"/>' /></li>
			<li><label>����</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
		</ol>
	</div>
</logic:notEqual>
