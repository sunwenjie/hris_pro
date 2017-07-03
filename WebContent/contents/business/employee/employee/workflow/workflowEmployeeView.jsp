<%@ page import="com.kan.hro.domain.biz.employee.EmployeeVO"%>
<%@page import="com.kan.hro.domain.biz.employee.EmployeeContractVO"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- 雇员 修改的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			EmployeeVO passObject = (EmployeeVO)request.getAttribute("passObject");
			EmployeeVO originalObject = (EmployeeVO)request.getAttribute("originalObject");
			out.print(KANUtil.getCompareHTML(passObject.getEmployeeNo(),originalObject.getEmployeeNo(),"雇员编号"));
			out.print(KANUtil.getCompareHTML(passObject.getRecordNo(),originalObject.getRecordNo(),"档案编号"));
			out.print(KANUtil.getCompareHTML(passObject.getRecordAddress(),originalObject.getRecordAddress(),"档案所在地"));
			out.print(KANUtil.getCompareHTML(passObject.getNameZH(),originalObject.getNameZH(),"雇员姓名（中文）"));
			out.print(KANUtil.getCompareHTML(passObject.getNameEN(),originalObject.getNameEN(),"雇员姓名（英文）"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeSalutation(),originalObject.getDecodeSalutation(),"称呼"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeMaritalStatus(),originalObject.getDecodeMaritalStatus(),"婚姻状况"));
			out.print(KANUtil.getCompareHTML(passObject.getBirthday(),originalObject.getBirthday(),"出生年月"));
			out.print(KANUtil.getCompareHTML(passObject.getBirthdayPlace(),originalObject.getBirthdayPlace(),"出生地"));
			
			out.print(KANUtil.getCompareHTML(passObject.getNationNality(),originalObject.getNationNality(),"国籍"));
			out.print(KANUtil.getCompareHTML(passObject.getResidencyCityId(),originalObject.getResidencyCityId(),"籍贯"));
			out.print(KANUtil.getCompareHTML(passObject.getResidencyType(),originalObject.getResidencyType(),"户口性质"));
			out.print(KANUtil.getCompareHTML(passObject.getResidencyAddress(),originalObject.getResidencyAddress(),"户籍地址"));
			out.print(KANUtil.getCompareHTML(passObject.getPersonalAddress(),originalObject.getPersonalAddress(),"当前住址"));
			out.print(KANUtil.getCompareHTML(passObject.getPersonalPostcode(),originalObject.getPersonalPostcode(),"住址邮编"));
			out.print(KANUtil.getCompareHTML(passObject.getGraduationDate(),originalObject.getGraduationDate(),"毕业日期"));
			out.print(KANUtil.getCompareHTML(passObject.getHighestEducation(),originalObject.getHighestEducation(),"最高学历"));
			out.print(KANUtil.getCompareHTML(passObject.getStartWorkDate(),originalObject.getStartWorkDate(),"首次参加工作日期"));
			out.print(KANUtil.getCompareHTML(passObject.getPhone1(),originalObject.getPhone1(),"电话"));
			out.print(KANUtil.getCompareHTML(passObject.getMobile1(),originalObject.getMobile1(),"手机"));
			out.print(KANUtil.getCompareHTML(passObject.getEmail1(),originalObject.getEmail1(),"邮箱"));
			out.print(KANUtil.getCompareHTML(passObject.getWebsite1(),originalObject.getWebsite1(),"个人主页"));
			out.print(KANUtil.getCompareHTML(passObject.getPhone2(),originalObject.getPhone2(),"电话（备用）"));
			out.print(KANUtil.getCompareHTML(passObject.getMobile2(),originalObject.getMobile2(),"手机（备用）"));
			out.print(KANUtil.getCompareHTML(passObject.getEmail2(),originalObject.getEmail2(),"邮箱（备用）"));
			out.print(KANUtil.getCompareHTML(passObject.getWebsite2(),originalObject.getWebsite2(),"个人主页（备用）"));
			out.print(KANUtil.getCompareHTML(passObject.getIm1Type(),originalObject.getIm1Type(),"即时通讯 - 1"));
			out.print(KANUtil.getCompareHTML(passObject.getIm1(),originalObject.getIm1(),"即时通讯号码 - 1"));
			out.print(KANUtil.getCompareHTML(passObject.getIm2Type(),originalObject.getIm2Type(),"即时通讯 - 2"));
			out.print(KANUtil.getCompareHTML(passObject.getIm2(),originalObject.getIm2(),"即时通讯号码 - 2"));
			out.print(KANUtil.getCompareHTML(passObject.getIm3Type(),originalObject.getIm3Type(),"即时通讯 - 3"));
			out.print(KANUtil.getCompareHTML(passObject.getIm3(),originalObject.getIm3(),"即时通讯号码 - 3"));
			out.print(KANUtil.getCompareHTML(passObject.getIm4Type(),originalObject.getIm4Type(),"即时通讯 - 4"));
			out.print(KANUtil.getCompareHTML(passObject.getIm4(),originalObject.getIm4(),"即时通讯号码 - 4"));
			out.print(KANUtil.getCompareHTML(passObject.getHasForeignerWorkLicence(),originalObject.getHasForeignerWorkLicence(),"外国人就业许可证"));
			out.print(KANUtil.getCompareHTML(passObject.getHasResidenceLicence(),originalObject.getHasResidenceLicence(),"居住证"));
			out.print(KANUtil.getCompareHTML(passObject.getCertificateType(),originalObject.getCertificateType(),"证件类型"));
			out.print(KANUtil.getCompareHTML(passObject.getCertificateNumber(),originalObject.getCertificateNumber(),"证件号码"));
			out.print(KANUtil.getCompareHTML(passObject.getCertificateStartDate(),originalObject.getCertificateStartDate(),"证件生效日期"));
			out.print(KANUtil.getCompareHTML(passObject.getCertificateEndDate(),originalObject.getCertificateEndDate(),"证件失效日期"));
			out.print(KANUtil.getCompareHTML(passObject.getCertificateAwardFrom(),originalObject.getCertificateAwardFrom(),"证件颁发机构"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDescription(),"备注","textarea"));
			out.print(KANUtil.getCompareHTML(passObject.getStatus(),originalObject.getStatus(),"雇员状态"));
			out.print(KANUtil.getCompareHTML(passObject.getPhoto(),originalObject.getPhoto(),"形象照片"));
			out.print(KANUtil.getCompareHTML(passObject.getBranch(),originalObject.getBranch(),"所属部门"));
			out.print(KANUtil.getCompareHTML(passObject.getOwner(),originalObject.getOwner(),"所属人"));
			
			%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label>雇员编号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNo"/>' /></li>
		<li><label>档案编号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="recordNo"/>' /></li>
		<li><label>档案所在地</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="recordAddress"/>' /></li>
		<li><label>雇员姓名（中文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameZH"/>' /></li>
		<li><label>雇员姓名（英文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameEN"/>' /></li>
		<li><label>称呼</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeSalutation"/>' /></li>
		<li><label>婚姻状况</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeMaritalStatus"/>' /></li>
		<li><label>出生年月</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="birthday"/>' /></li>
		<li><label>出生地</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="birthdayPlace"/>' /></li>
		
		<li><label>国籍</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nationNality"/>' /></li>
		<li><label>籍贯</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="residencyCityId"/>' /></li>
		<li><label>户口性质</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="residencyType"/>' /></li>
		<li><label>户籍地址</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="residencyAddress"/>' /></li>
		<li><label>当前住址</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="personalAddress"/>' /></li>
		<li><label>住址邮编</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="personalPostcode"/>' /></li>
		<li><label>毕业日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="graduationDate"/>' /></li>
		<li><label>最高学历</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="highestEducation"/>' /></li>
		<li><label>首次参加工作日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startWorkDate"/>' /></li>
		<li><label>电话</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="phone1"/>' /></li>
		<li><label>手机</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="mobile1"/>' /></li>
		<li><label>邮箱</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="email1"/>' /></li>
		<li><label>个人主页</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="website1"/>' /></li>
		<li><label>电话（备用）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="phone2"/>' /></li>
		<li><label>手机（备用）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="mobile2"/>' /></li>
		<li><label>邮箱（备用）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="email2"/>' /></li>
		<li><label>个人主页（备用）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="website2"/>' /></li>
		<li><label>即时通讯 - 1</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im1Type"/>' /></li>
		<li><label>即时通讯号码 - 1</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im1"/>' /></li>
		<li><label>即时通讯 - 2</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im2Type"/>' /></li>
		<li><label>即时通讯号码 - 2</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im2"/>' /></li>
		<li><label>即时通讯 - 3</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im3Type"/>' /></li>
		<li><label>即时通讯号码 - 3</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im3"/>' /></li>
		<li><label>即时通讯 - 4</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im4Type"/>' /></li>
		<li><label>即时通讯号码 - 4</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im4"/>' /></li>
		<li><label>外国人就业许可证</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="hasForeignerWorkLicence"/>' /></li>
		<li><label>居住证</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="hasResidenceLicence"/>' /></li>
		<li><label>证件类型</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateType"/>' /></li>
		<li><label>证件号码</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateNumber"/>' /></li>
		<li><label>证件生效日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateStartDate"/>' /></li>
		<li><label>证件失效日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateEndDate"/>' /></li>
		<li><label>证件颁发机构</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateAwardFrom"/>' /></li>
		<li><label>备注</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
		<li><label>雇员状态</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="status"/>' /></li>
		<li><label>形象照片</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="photo"/>' /></li>
		<li><label>所属部门</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="branch"/>' /></li>
		<li><label>所属人</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="owner"/>' /></li>
		
	</ol>
</div>
</logic:equal>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
<%-- 非修改类型的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<li><label>雇员编号</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeNo"/>' /></li>
		<li><label>档案编号</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="recordNo"/>' /></li>
		<li><label>档案所在地</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="recordAddress"/>' /></li>
		<li><label>雇员姓名（中文）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameZH"/>' /></li>
		<li><label>雇员姓名（英文）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameEN"/>' /></li>
		<li><label>称呼</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeSalutation"/>' /></li>
		<li><label>婚姻状况</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeMaritalStatus"/>' /></li>
		<li><label>出生年月</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="birthday"/>' /></li>
		<li><label>出生地</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="birthdayPlace"/>' /></li>
		
		<li><label>国籍</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nationNality"/>' /></li>
		<li><label>籍贯</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="residencyCityId"/>' /></li>
		<li><label>户口性质</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="residencyType"/>' /></li>
		<li><label>户籍地址</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="residencyAddress"/>' /></li>
		<li><label>当前住址</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="personalAddress"/>' /></li>
		<li><label>住址邮编</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="personalPostcode"/>' /></li>
		<li><label>毕业日期</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="graduationDate"/>' /></li>
		<li><label>最高学历</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="highestEducation"/>' /></li>
		<li><label>首次参加工作日期</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="startWorkDate"/>' /></li>
		<li><label>电话</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="phone1"/>' /></li>
		<li><label>手机</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="mobile1"/>' /></li>
		<li><label>邮箱</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="email1"/>' /></li>
		<li><label>个人主页</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="website1"/>' /></li>
		<li><label>电话（备用）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="phone2"/>' /></li>
		<li><label>手机（备用）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="mobile2"/>' /></li>
		<li><label>邮箱（备用）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="email2"/>' /></li>
		<li><label>个人主页（备用）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="website2"/>' /></li>
		<li><label>即时通讯 - 1</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im1Type"/>' /></li>
		<li><label>即时通讯号码 - 1</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im1"/>' /></li>
		<li><label>即时通讯 - 2</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im2Type"/>' /></li>
		<li><label>即时通讯号码 - 2</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im2"/>' /></li>
		<li><label>即时通讯 - 3</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im3Type"/>' /></li>
		<li><label>即时通讯号码 - 3</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im3"/>' /></li>
		<li><label>即时通讯 - 4</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im4Type"/>' /></li>
		<li><label>即时通讯号码 - 4</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im4"/>' /></li>
		<li><label>外国人就业许可证</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="hasForeignerWorkLicence"/>' /></li>
		<li><label>居住证</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="hasResidenceLicence"/>' /></li>
		<li><label>证件类型</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="certificateType"/>' /></li>
		<li><label>证件号码</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="certificateNumber"/>' /></li>
		<li><label>证件生效日期</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="certificateStartDate"/>' /></li>
		<li><label>证件失效日期</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="certificateEndDate"/>' /></li>
		<li><label>证件颁发机构</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="certificateAwardFrom"/>' /></li>
		<li><label>备注</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
		<li><label>雇员状态</label><textarea disabled="disabled"><bean:write name="passObject" property="status"/></textarea></li>
		<li><label>形象照片</label><textarea disabled="disabled"><bean:write name="passObject" property="photo"/></textarea></li>
		<li><label>所属部门</label><textarea disabled="disabled"><bean:write name="passObject" property="branch"/></textarea></li>
		<li><label>所属人</label><textarea disabled="disabled"><bean:write name="passObject" property="owner"/></textarea></li>
	</ol>
</div>
</logic:notEqual>
