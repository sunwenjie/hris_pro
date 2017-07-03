<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/kan.js"></script>
<link rel="stylesheet" href="mobile/css/mobile.css" />
<title><bean:message bundle="wx" key="wx.logon.title" /></title>
</head>
<body>
<div id="layout">
	<html:form action="staffAction.do?proc=modify_object_mobile" method="post" styleClass="manageStaff_staffForm">
	<input type="hidden" name="subAction" class="subAction" id="subAction" value="viewObject"/>
	<input type="hidden" name="id" class="id" id="id" value="<bean:write name='staffForm' property='encodedId'/>"/>
	<%= BaseAction.addToken(request) %>
		<div class="waikuang">
	          <div class="neirong1"><sup><bean:message bundle="wx" key="wx.staff.name.zh" />£º</sup><html:text property="nameZH" name="staffForm" styleClass="staffForm_nameZH shurukuang1" />
			  </div>
	          <div class="neirong1"><span><bean:message bundle="wx" key="wx.staff.name.en" />£º</span><html:text property="nameEN" name="staffForm" styleClass="staffForm_nameEN shurukuang2" />
			  </div>
			  <div class="neirong1"><span><bean:message bundle="wx" key="wx.staff.sex" />£º</span>
			  	<html:select property="salutation" name="staffForm" styleClass="staffForm_salutation shurukuang1">
			  		<html:optionsCollection name="staffForm" property="salutations" label="mappingValue" value="mappingId"/>
			  	</html:select>
			  </div>
			  <div class="neirong1"><span><bean:message bundle="wx" key="wx.staff.id.card" />£º</span><html:text property="certificateNumber" name="staffForm" styleClass="staffForm_certificateNumber shurukuang2" />
			  </div>
			  <div class="neirong1"><span><bean:message bundle="wx" key="wx.staff.bank" />£º</span>
			  	<html:select property="bankId" name="staffForm" styleClass="staffForm_banks shurukuang1">
			  		<html:optionsCollection name="staffForm" property="banks" label="mappingValue" value="mappingId"/>
			  	</html:select>
			  </div>
			  <div class="neirong1"><span><bean:message bundle="wx" key="wx.staff.bank.account" />£º</span><html:text property="bankAccount" name="staffForm" styleClass="staffForm_bankAccount shurukuang1" />
			  </div>
			  <div class="neirong1"><span><bean:message bundle="wx" key="wx.staff.mobilephone" />£º</span><html:text property="personalMobile" name="staffForm" styleClass="staffForm_personalMobile shurukuang1" />
			  </div>
			  <div class="neirong1"><span><bean:message bundle="wx" key="wx.staff.telephone" />£º</span><html:text property="personalPhone" name="staffForm" styleClass="staffForm_personalPhone shurukuang1" />
			  </div>
			  <div class="neirong3"><span><bean:message bundle="wx" key="wx.staff.email" />£º</span><html:textarea property="bizEmail" name="staffForm" styleClass="staffForm_personalEmail shurukuang3" />
			  </div>
			  <div class="neirong3"><span><bean:message bundle="wx" key="wx.staff.address" />£º</span><html:textarea property="personalAddress" name="staffForm" styleClass="staffForm_personalAddress shurukuang3" />
			  </div>
			  
		</div>
	<a href="javascript:void(0);" onclick="btnEditClick();" class="button gray" id="btnEdit"><bean:message bundle="wx" key="wx.btn.edit" /></a>
	<br clear="all" />
	</html:form>
</div>
</body>
<script type="text/javascript">
	(function($) {	
		// Ä¬ÈÏ²»¿É±à¼­
		disableStaffFrom();
		
		btnEditClick();
		
		if($("#subAction").val()=="viewObject"){
			$("#btnEdit").removeClass();
			$("#btnEdit").addClass("button gray");
		}else{
			enableStaffForm();
			$("#btnEdit").removeClass();
			$("#btnEdit").addClass("button orange");
		}
		
	})(jQuery);
	
	function btnEditClick(){
		if($("#subAction").val()=="viewObject"){
			enableStaffForm();
			$("#subAction").val("modifyObject");
			$("#btnEdit").text("<bean:message bundle="wx" key="wx.btn.save" />");
			$("#btnEdit").removeClass();
			$("#btnEdit").addClass("button orange");
		}else{
			$(".manageStaff_staffForm").submit();
		}
	};
	
	function enableStaffForm(){
		$('form.manageStaff_staffForm input, form.manageStaff_staffForm select, form.manageStaff_staffForm textarea').removeAttr('disabled');
	};
	
	function disableStaffFrom(){
		$('form.manageStaff_staffForm input, form.manageStaff_staffForm select, form.manageStaff_staffForm textarea').attr('disabled', 'disabled');
	}

</script>

