<%@page import="com.kan.hro.domain.biz.client.ClientVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- 修改类型的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			ClientVO passObject = (ClientVO)request.getAttribute("passObject");
			ClientVO originalObject = (ClientVO)request.getAttribute("originalObject");
			out.print(KANUtil.getCompareHTML(passObject.getNumber(),originalObject.getNumber(),"客户编号"));
			out.print(KANUtil.getCompareHTML(passObject.getNameZH(),originalObject.getNameZH(),"客户中文名"));
			out.print(KANUtil.getCompareHTML(passObject.getNameEN(),originalObject.getNameEN(),"客户英文名"));
			out.print(KANUtil.getCompareHTML(passObject.getGroupName(),originalObject.getGroupName(),"集团名称"));
			out.print(KANUtil.getCompareHTML(passObject.getMainContact(),originalObject.getMainContact(),"联系人（默认）"));
			out.print(KANUtil.getCompareHTML(passObject.getIndustry(),originalObject.getIndustry(),"行业"));
			out.print(KANUtil.getCompareHTML(passObject.getSize(),originalObject.getSize(),"公司规模"));
			out.print(KANUtil.getCompareHTML(passObject.getType(),originalObject.getType(),"企业性质"));
			out.print(KANUtil.getCompareHTML(passObject.getInvoiceDate(),originalObject.getInvoiceDate(),"发票日期"));
			out.print(KANUtil.getCompareHTML(passObject.getPaymentTerms(),originalObject.getPaymentTerms(),"付款周期（天）"));
			out.print(KANUtil.getCompareHTML(passObject.getProvinceId(),originalObject.getProvinceId(),"省份 - 城市"));
			out.print(KANUtil.getCompareHTML(passObject.getAddress(),originalObject.getAddress(),"地址"));
			out.print(KANUtil.getCompareHTML(passObject.getPostcode(),originalObject.getPostcode(),"邮编"));
			out.print(KANUtil.getCompareHTML(passObject.getMobile(),originalObject.getMobile(),"手机"));
			out.print(KANUtil.getCompareHTML(passObject.getPhone(),originalObject.getPhone(),"电话"));
			out.print(KANUtil.getCompareHTML(passObject.getFax(),originalObject.getFax(),"传真"));
			out.print(KANUtil.getCompareHTML(passObject.getWebsite(),originalObject.getWebsite(),"网址"));
			out.print(KANUtil.getCompareHTML(passObject.getEmail(),originalObject.getEmail(),"邮箱"));
			out.print(KANUtil.getCompareHTML(passObject.getIm1(),originalObject.getIm1(),"IM1类型"));
			out.print(KANUtil.getCompareHTML(passObject.getIm1Type(),originalObject.getIm1Type(),"IM1号码"));
			out.print(KANUtil.getCompareHTML(passObject.getIm2(),originalObject.getIm2(),"IM2类型"));
			out.print(KANUtil.getCompareHTML(passObject.getIm2Type(),originalObject.getIm2Type(),"IM2号码"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDescription(),"公司简介","textarea"));
			out.print(KANUtil.getCompareHTML(passObject.getStatus(),originalObject.getStatus(),"状态"));
			out.print(KANUtil.getCompareHTML(passObject.getLegalEntity(),originalObject.getLegalEntity(),"法务实体"));
			out.print(KANUtil.getCompareHTML(passObject.getBranch(),originalObject.getBranch(),"所属部门"));
			out.print(KANUtil.getCompareHTML(passObject.getOwner(),originalObject.getOwner(),"所属人"));
			out.print(KANUtil.getCompareHTML(passObject.getRecommendPerson(),originalObject.getRecommendPerson(),"推荐人"));
			out.print(KANUtil.getCompareHTML(passObject.getRecommendBranch(),originalObject.getRecommendBranch(),"推荐部门"));
			out.print(KANUtil.getCompareHTML(passObject.getRecommendPosition(),originalObject.getRecommendPosition(),"推荐职位"));
			
		%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label>客户编号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="number"/>' /></li>
		<li><label>客户中文名</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameZH"/>' /></li>
		<li><label>客户英文名</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameEN"/>' /></li>
		<li><label>集团名称</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="groupName"/>' /></li>
		<li><label>联系人（默认）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="mainContact"/>' /></li>
		<li><label>行业</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="industry"/>' /></li>
		<li><label>公司规模</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="size"/>' /></li>
		<li><label>公司性质</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="type"/>' /></li>
		<li><label>发票日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="invoiceDate"/>' /></li>
		<li><label>付款周期（天）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="paymentTerms"/>' /></li>
		<li><label>省份 - 城市</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="provinceId"/>' /></li>
		<li><label>地址</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="address"/>' /></li>
		<li><label>邮编</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="postcode"/>' /></li>
		<li><label>手机</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="mobile"/>' /></li>
		<li><label>电话</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="phone"/>' /></li>
		<li><label>传真</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="fax"/>' /></li>
		<li><label>网址</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="website"/>' /></li>
		<li><label>邮箱</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="email"/>' /></li>
		<li><label>IM1类型</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im1"/>' /></li>
		<li><label>IM1号码</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im1Type"/>' /></li>
		<li><label>IM2类型</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im2"/>' /></li>
		<li><label>IM2号码</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im2Type"/>' /></li>
		<li><label>公司简介</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
		<li><label>状态</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="status"/>' /></li>
		<li><label>法务实体</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="legalEntity"/>' /></li>
		<li><label>所属部门</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="branch"/>' /></li>
		<li><label>所属人</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="owner"/>' /></li>
		<li><label>推荐人</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="recommendPerson"/>' /></li>
		<li><label>推荐部门</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="recommendBranch"/>' /></li>
		<li><label>推荐职位</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="recommendPosition"/>' /></li>
	</ol>
</div>
</logic:equal>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
<%-- 非修改类型的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<li><label>客户编号</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="number"/>' /></li>
		<li><label>客户中文名</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameZH"/>' /></li>
		<li><label>客户英文名</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameEN"/>' /></li>
		<li><label>集团名称</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="groupName"/>' /></li>
		<li><label>联系人（默认）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="mainContact"/>' /></li>
		<li><label>行业</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="industry"/>' /></li>
		<li><label>公司规模</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="size"/>' /></li>
		<li><label>公司性质</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="type"/>' /></li>
		<li><label>发票日期</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="invoiceDate"/>' /></li>
		<li><label>付款周期（天）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="paymentTerms"/>' /></li>
		<li><label>省份 - 城市</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="provinceId"/>' /></li>
		<li><label>地址</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="address"/>' /></li>
		<li><label>邮编</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="postcode"/>' /></li>
		<li><label>手机</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="mobile"/>' /></li>
		<li><label>电话</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="phone"/>' /></li>
		<li><label>传真</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="fax"/>' /></li>
		<li><label>网址</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="website"/>' /></li>
		<li><label>邮箱</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="email"/>' /></li>
		<li><label>IM1类型</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im1"/>' /></li>
		<li><label>IM1号码</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im1Type"/>' /></li>
		<li><label>IM2类型</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im2"/>' /></li>
		<li><label>IM2号码</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im2Type"/>' /></li>
		<li><label>公司简介</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
		<li><label>状态</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="status"/>' /></li>
		<li><label>法务实体</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="legalEntity"/>' /></li>
		<li><label>所属部门</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="branch"/>' /></li>
		<li><label>所属人</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="owner"/>' /></li>
		<li><label>推荐人</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="recommendPerson"/>' /></li>
		<li><label>推荐部门</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="recommendBranch"/>' /></li>
		<li><label>推荐职位</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="recommendPosition"/>' /></li>
	</ol>
</div>
</logic:notEqual>