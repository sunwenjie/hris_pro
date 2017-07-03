<%@ page import="com.kan.hro.domain.biz.employee.EmployeeVO"%>
<%@page import="com.kan.hro.domain.biz.employee.EmployeeContractVO"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- ��Ա �޸ĵ��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			EmployeeVO passObject = (EmployeeVO)request.getAttribute("passObject");
			EmployeeVO originalObject = (EmployeeVO)request.getAttribute("originalObject");
			out.print(KANUtil.getCompareHTML(passObject.getEmployeeNo(),originalObject.getEmployeeNo(),"��Ա���"));
			out.print(KANUtil.getCompareHTML(passObject.getRecordNo(),originalObject.getRecordNo(),"�������"));
			out.print(KANUtil.getCompareHTML(passObject.getRecordAddress(),originalObject.getRecordAddress(),"�������ڵ�"));
			out.print(KANUtil.getCompareHTML(passObject.getNameZH(),originalObject.getNameZH(),"��Ա���������ģ�"));
			out.print(KANUtil.getCompareHTML(passObject.getNameEN(),originalObject.getNameEN(),"��Ա������Ӣ�ģ�"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeSalutation(),originalObject.getDecodeSalutation(),"�ƺ�"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeMaritalStatus(),originalObject.getDecodeMaritalStatus(),"����״��"));
			out.print(KANUtil.getCompareHTML(passObject.getBirthday(),originalObject.getBirthday(),"��������"));
			out.print(KANUtil.getCompareHTML(passObject.getBirthdayPlace(),originalObject.getBirthdayPlace(),"������"));
			
			out.print(KANUtil.getCompareHTML(passObject.getNationNality(),originalObject.getNationNality(),"����"));
			out.print(KANUtil.getCompareHTML(passObject.getResidencyCityId(),originalObject.getResidencyCityId(),"����"));
			out.print(KANUtil.getCompareHTML(passObject.getResidencyType(),originalObject.getResidencyType(),"��������"));
			out.print(KANUtil.getCompareHTML(passObject.getResidencyAddress(),originalObject.getResidencyAddress(),"������ַ"));
			out.print(KANUtil.getCompareHTML(passObject.getPersonalAddress(),originalObject.getPersonalAddress(),"��ǰסַ"));
			out.print(KANUtil.getCompareHTML(passObject.getPersonalPostcode(),originalObject.getPersonalPostcode(),"סַ�ʱ�"));
			out.print(KANUtil.getCompareHTML(passObject.getGraduationDate(),originalObject.getGraduationDate(),"��ҵ����"));
			out.print(KANUtil.getCompareHTML(passObject.getHighestEducation(),originalObject.getHighestEducation(),"���ѧ��"));
			out.print(KANUtil.getCompareHTML(passObject.getStartWorkDate(),originalObject.getStartWorkDate(),"�״βμӹ�������"));
			out.print(KANUtil.getCompareHTML(passObject.getPhone1(),originalObject.getPhone1(),"�绰"));
			out.print(KANUtil.getCompareHTML(passObject.getMobile1(),originalObject.getMobile1(),"�ֻ�"));
			out.print(KANUtil.getCompareHTML(passObject.getEmail1(),originalObject.getEmail1(),"����"));
			out.print(KANUtil.getCompareHTML(passObject.getWebsite1(),originalObject.getWebsite1(),"������ҳ"));
			out.print(KANUtil.getCompareHTML(passObject.getPhone2(),originalObject.getPhone2(),"�绰�����ã�"));
			out.print(KANUtil.getCompareHTML(passObject.getMobile2(),originalObject.getMobile2(),"�ֻ������ã�"));
			out.print(KANUtil.getCompareHTML(passObject.getEmail2(),originalObject.getEmail2(),"���䣨���ã�"));
			out.print(KANUtil.getCompareHTML(passObject.getWebsite2(),originalObject.getWebsite2(),"������ҳ�����ã�"));
			out.print(KANUtil.getCompareHTML(passObject.getIm1Type(),originalObject.getIm1Type(),"��ʱͨѶ - 1"));
			out.print(KANUtil.getCompareHTML(passObject.getIm1(),originalObject.getIm1(),"��ʱͨѶ���� - 1"));
			out.print(KANUtil.getCompareHTML(passObject.getIm2Type(),originalObject.getIm2Type(),"��ʱͨѶ - 2"));
			out.print(KANUtil.getCompareHTML(passObject.getIm2(),originalObject.getIm2(),"��ʱͨѶ���� - 2"));
			out.print(KANUtil.getCompareHTML(passObject.getIm3Type(),originalObject.getIm3Type(),"��ʱͨѶ - 3"));
			out.print(KANUtil.getCompareHTML(passObject.getIm3(),originalObject.getIm3(),"��ʱͨѶ���� - 3"));
			out.print(KANUtil.getCompareHTML(passObject.getIm4Type(),originalObject.getIm4Type(),"��ʱͨѶ - 4"));
			out.print(KANUtil.getCompareHTML(passObject.getIm4(),originalObject.getIm4(),"��ʱͨѶ���� - 4"));
			out.print(KANUtil.getCompareHTML(passObject.getHasForeignerWorkLicence(),originalObject.getHasForeignerWorkLicence(),"����˾�ҵ���֤"));
			out.print(KANUtil.getCompareHTML(passObject.getHasResidenceLicence(),originalObject.getHasResidenceLicence(),"��ס֤"));
			out.print(KANUtil.getCompareHTML(passObject.getCertificateType(),originalObject.getCertificateType(),"֤������"));
			out.print(KANUtil.getCompareHTML(passObject.getCertificateNumber(),originalObject.getCertificateNumber(),"֤������"));
			out.print(KANUtil.getCompareHTML(passObject.getCertificateStartDate(),originalObject.getCertificateStartDate(),"֤����Ч����"));
			out.print(KANUtil.getCompareHTML(passObject.getCertificateEndDate(),originalObject.getCertificateEndDate(),"֤��ʧЧ����"));
			out.print(KANUtil.getCompareHTML(passObject.getCertificateAwardFrom(),originalObject.getCertificateAwardFrom(),"֤���䷢����"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDescription(),"��ע","textarea"));
			out.print(KANUtil.getCompareHTML(passObject.getStatus(),originalObject.getStatus(),"��Ա״̬"));
			out.print(KANUtil.getCompareHTML(passObject.getPhoto(),originalObject.getPhoto(),"������Ƭ"));
			out.print(KANUtil.getCompareHTML(passObject.getBranch(),originalObject.getBranch(),"��������"));
			out.print(KANUtil.getCompareHTML(passObject.getOwner(),originalObject.getOwner(),"������"));
			
			%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label>��Ա���</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNo"/>' /></li>
		<li><label>�������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="recordNo"/>' /></li>
		<li><label>�������ڵ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="recordAddress"/>' /></li>
		<li><label>��Ա���������ģ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameZH"/>' /></li>
		<li><label>��Ա������Ӣ�ģ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameEN"/>' /></li>
		<li><label>�ƺ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeSalutation"/>' /></li>
		<li><label>����״��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeMaritalStatus"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="birthday"/>' /></li>
		<li><label>������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="birthdayPlace"/>' /></li>
		
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nationNality"/>' /></li>
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="residencyCityId"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="residencyType"/>' /></li>
		<li><label>������ַ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="residencyAddress"/>' /></li>
		<li><label>��ǰסַ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="personalAddress"/>' /></li>
		<li><label>סַ�ʱ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="personalPostcode"/>' /></li>
		<li><label>��ҵ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="graduationDate"/>' /></li>
		<li><label>���ѧ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="highestEducation"/>' /></li>
		<li><label>�״βμӹ�������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startWorkDate"/>' /></li>
		<li><label>�绰</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="phone1"/>' /></li>
		<li><label>�ֻ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="mobile1"/>' /></li>
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="email1"/>' /></li>
		<li><label>������ҳ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="website1"/>' /></li>
		<li><label>�绰�����ã�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="phone2"/>' /></li>
		<li><label>�ֻ������ã�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="mobile2"/>' /></li>
		<li><label>���䣨���ã�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="email2"/>' /></li>
		<li><label>������ҳ�����ã�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="website2"/>' /></li>
		<li><label>��ʱͨѶ - 1</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im1Type"/>' /></li>
		<li><label>��ʱͨѶ���� - 1</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im1"/>' /></li>
		<li><label>��ʱͨѶ - 2</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im2Type"/>' /></li>
		<li><label>��ʱͨѶ���� - 2</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im2"/>' /></li>
		<li><label>��ʱͨѶ - 3</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im3Type"/>' /></li>
		<li><label>��ʱͨѶ���� - 3</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im3"/>' /></li>
		<li><label>��ʱͨѶ - 4</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im4Type"/>' /></li>
		<li><label>��ʱͨѶ���� - 4</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im4"/>' /></li>
		<li><label>����˾�ҵ���֤</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="hasForeignerWorkLicence"/>' /></li>
		<li><label>��ס֤</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="hasResidenceLicence"/>' /></li>
		<li><label>֤������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateType"/>' /></li>
		<li><label>֤������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateNumber"/>' /></li>
		<li><label>֤����Ч����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateStartDate"/>' /></li>
		<li><label>֤��ʧЧ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateEndDate"/>' /></li>
		<li><label>֤���䷢����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateAwardFrom"/>' /></li>
		<li><label>��ע</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
		<li><label>��Ա״̬</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="status"/>' /></li>
		<li><label>������Ƭ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="photo"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="branch"/>' /></li>
		<li><label>������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="owner"/>' /></li>
		
	</ol>
</div>
</logic:equal>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
<%-- ���޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<li><label>��Ա���</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeNo"/>' /></li>
		<li><label>�������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="recordNo"/>' /></li>
		<li><label>�������ڵ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="recordAddress"/>' /></li>
		<li><label>��Ա���������ģ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameZH"/>' /></li>
		<li><label>��Ա������Ӣ�ģ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameEN"/>' /></li>
		<li><label>�ƺ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeSalutation"/>' /></li>
		<li><label>����״��</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeMaritalStatus"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="birthday"/>' /></li>
		<li><label>������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="birthdayPlace"/>' /></li>
		
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nationNality"/>' /></li>
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="residencyCityId"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="residencyType"/>' /></li>
		<li><label>������ַ</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="residencyAddress"/>' /></li>
		<li><label>��ǰסַ</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="personalAddress"/>' /></li>
		<li><label>סַ�ʱ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="personalPostcode"/>' /></li>
		<li><label>��ҵ����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="graduationDate"/>' /></li>
		<li><label>���ѧ��</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="highestEducation"/>' /></li>
		<li><label>�״βμӹ�������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="startWorkDate"/>' /></li>
		<li><label>�绰</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="phone1"/>' /></li>
		<li><label>�ֻ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="mobile1"/>' /></li>
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="email1"/>' /></li>
		<li><label>������ҳ</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="website1"/>' /></li>
		<li><label>�绰�����ã�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="phone2"/>' /></li>
		<li><label>�ֻ������ã�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="mobile2"/>' /></li>
		<li><label>���䣨���ã�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="email2"/>' /></li>
		<li><label>������ҳ�����ã�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="website2"/>' /></li>
		<li><label>��ʱͨѶ - 1</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im1Type"/>' /></li>
		<li><label>��ʱͨѶ���� - 1</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im1"/>' /></li>
		<li><label>��ʱͨѶ - 2</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im2Type"/>' /></li>
		<li><label>��ʱͨѶ���� - 2</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im2"/>' /></li>
		<li><label>��ʱͨѶ - 3</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im3Type"/>' /></li>
		<li><label>��ʱͨѶ���� - 3</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im3"/>' /></li>
		<li><label>��ʱͨѶ - 4</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im4Type"/>' /></li>
		<li><label>��ʱͨѶ���� - 4</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im4"/>' /></li>
		<li><label>����˾�ҵ���֤</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="hasForeignerWorkLicence"/>' /></li>
		<li><label>��ס֤</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="hasResidenceLicence"/>' /></li>
		<li><label>֤������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="certificateType"/>' /></li>
		<li><label>֤������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="certificateNumber"/>' /></li>
		<li><label>֤����Ч����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="certificateStartDate"/>' /></li>
		<li><label>֤��ʧЧ����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="certificateEndDate"/>' /></li>
		<li><label>֤���䷢����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="certificateAwardFrom"/>' /></li>
		<li><label>��ע</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
		<li><label>��Ա״̬</label><textarea disabled="disabled"><bean:write name="passObject" property="status"/></textarea></li>
		<li><label>������Ƭ</label><textarea disabled="disabled"><bean:write name="passObject" property="photo"/></textarea></li>
		<li><label>��������</label><textarea disabled="disabled"><bean:write name="passObject" property="branch"/></textarea></li>
		<li><label>������</label><textarea disabled="disabled"><bean:write name="passObject" property="owner"/></textarea></li>
	</ol>
</div>
</logic:notEqual>
