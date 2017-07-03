<%@page import="org.apache.struts.Globals"%>
<%@page import="com.kan.base.domain.management.PositionGradeVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="systemGrade" class="box toggableForm">
	<div class="head">
		<label id="pageTitle" >
			<logic:equal value="1" name="role">职位等级（外部）添加</logic:equal>
			<logic:equal value="2" name="role">职位等级添加</logic:equal>
		</label>
	</div>	
	<div class="inner">
		<!-- Include Form JSP 包含Form对应的jsp文件 -->  
		<jsp:include page="/contents/management/positionGrade/positionGrade/form/managePositionGradeForm.jsp" flush="true"/>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		//	初始化菜单
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_PositionGrades').addClass('selected');
		
		// 更改按钮显示名
		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		
		// 查看模式
		if($('.subAction').val() == 'viewObject'){
			// 更换Page Title
			if('<bean:write name="role"/>' == 1 ){
				$('#pageTitle').html('职位等级（外部）查询');
			}else{
				$('#pageTitle').html('职位等级查询');
			}
		}
	})(jQuery);
</script>