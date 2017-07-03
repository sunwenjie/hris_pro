<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,2)" class="hover first">�ͻ���Ϣ</li>
			<li id="tabMenu2" onClick="changeTab(2,2)">������Ϣ</li> 
		</ul> 
	</div>
	
	<div class="tabContent" id="tabContent"> 
		<div id="tabContent1" class="kantab">
			<form action="#">
				<ol class="auto">
					<li><label>�ͻ�ID��</label>
						<span><bean:write name="clientVO" property="clientId"/></span>
					</li>
				</ol>
				<ol class="auto">
					<li><label>�ͻ����ƣ����ģ���</label>
						<span><bean:write name="clientVO" property="nameZH"/></span>
					</li>
					<li><label>�ͻ����ƣ�Ӣ�ģ���</label>
						<span><bean:write name="clientVO" property="nameEN"/></span>
					</li>
					<li><label>�ͻ���ţ�</label>
						<span><bean:write name="clientVO" property="number"/></span>
					</li>
					<li><label>�������ƣ�</label>
						<span><bean:write name="clientVO" property="groupName"/></span>
					</li>
				</ol>
				<ol class="auto">
					<li><label>����ʵ�壺</label>
						<span><bean:write name="clientVO" property="decodeLegalEntity"/></span>
					</li>
				</ol>
				<ol class="auto">
					<li><label>��ϵ��ַ��</label>
						<span><bean:write name="clientVO" property="address"/></span>
					</li>
					<li><label>��ϵ�绰��</label>
						<span><bean:write name="clientVO" property="phone"/></span>
					</li>
				</ol>
			</form>
		</div>
		<div id="tabContent2" class="kantab" style="display: none">
			<form action="#">
				<ol class="auto">
					<li><label>����ID��</label>
						<span><bean:write name="clientOrderHeaderVO" property="orderHeaderId"/></span>
					</li>
					<li><label>�����ͬ��</label>
						<span><bean:write name="clientOrderHeaderVO" property="contractNameZH"/></span>
					</li>
				</ol>
				<ol class="auto">
					<li><label>����ʵ�壺</label>
						<span><bean:write name="clientOrderHeaderVO" property="decodeEntityId"/></span>
					</li>
					<li><label>ҵ�����ͣ�</label>
						<span><bean:write name="clientOrderHeaderVO" property="decodeBusinessTypeId"/></span>
					</li>
					<li><label>��ʼʱ�䣺</label>
						<span><bean:write name="clientOrderHeaderVO" property="startDate"/></span>
					</li>
					<li><label>����ʱ�䣺</label>
						<span><bean:write name="clientOrderHeaderVO" property="endDate"/></span>
					</li>
					<li><label>�����·ݣ�</label>
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
