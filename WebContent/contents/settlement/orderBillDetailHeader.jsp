<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,2)" class="hover first">客户信息</li>
			<li id="tabMenu2" onClick="changeTab(2,2)">订单信息</li> 
		</ul> 
	</div>
	
	<div class="tabContent" id="tabContent"> 
		<div id="tabContent1" class="kantab">
			<form action="#">
				<ol class="auto">
					<li><label>客户ID：</label>
						<span><bean:write name="clientVO" property="clientId"/></span>
					</li>
				</ol>
				<ol class="auto">
					<li><label>客户名称（中文）：</label>
						<span><bean:write name="clientVO" property="nameZH"/></span>
					</li>
					<li><label>客户名称（英文）：</label>
						<span><bean:write name="clientVO" property="nameEN"/></span>
					</li>
					<li><label>客户编号：</label>
						<span><bean:write name="clientVO" property="number"/></span>
					</li>
					<li><label>集团名称：</label>
						<span><bean:write name="clientVO" property="groupName"/></span>
					</li>
				</ol>
				<ol class="auto">
					<li><label>法务实体：</label>
						<span><bean:write name="clientVO" property="decodeLegalEntity"/></span>
					</li>
				</ol>
				<ol class="auto">
					<li><label>联系地址：</label>
						<span><bean:write name="clientVO" property="address"/></span>
					</li>
					<li><label>联系电话：</label>
						<span><bean:write name="clientVO" property="phone"/></span>
					</li>
				</ol>
			</form>
		</div>
		<div id="tabContent2" class="kantab" style="display: none">
			<form action="#">
				<ol class="auto">
					<li><label>订单ID：</label>
						<span><bean:write name="clientOrderHeaderVO" property="orderHeaderId"/></span>
					</li>
					<li><label>商务合同：</label>
						<span><bean:write name="clientOrderHeaderVO" property="contractNameZH"/></span>
					</li>
				</ol>
				<ol class="auto">
					<li><label>法务实体：</label>
						<span><bean:write name="clientOrderHeaderVO" property="decodeEntityId"/></span>
					</li>
					<li><label>业务类型：</label>
						<span><bean:write name="clientOrderHeaderVO" property="decodeBusinessTypeId"/></span>
					</li>
					<li><label>开始时间：</label>
						<span><bean:write name="clientOrderHeaderVO" property="startDate"/></span>
					</li>
					<li><label>结束时间：</label>
						<span><bean:write name="clientOrderHeaderVO" property="endDate"/></span>
					</li>
					<li><label>操作月份：</label>
						<span><bean:write name="clientOrderHeaderVO" property="monthly"/></span>
					</li>
				</ol>
			</form>
		</div>
	</div> 
</div>


<script type="text/javascript">
	(function($) {

	})(jQuery);
	
</script>
