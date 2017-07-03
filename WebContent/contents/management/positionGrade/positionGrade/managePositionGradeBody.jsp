<%@page import="org.apache.struts.Globals"%>
<%@page import="com.kan.base.domain.management.PositionGradeVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="systemGrade" class="box toggableForm">
	<div class="head">
		<label id="pageTitle" >
			<logic:equal value="1" name="role">ְλ�ȼ����ⲿ�����</logic:equal>
			<logic:equal value="2" name="role">ְλ�ȼ����</logic:equal>
		</label>
	</div>	
	<div class="inner">
		<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
		<jsp:include page="/contents/management/positionGrade/positionGrade/form/managePositionGradeForm.jsp" flush="true"/>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		//	��ʼ���˵�
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_PositionGrades').addClass('selected');
		
		// ���İ�ť��ʾ��
		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		
		// �鿴ģʽ
		if($('.subAction').val() == 'viewObject'){
			// ����Page Title
			if('<bean:write name="role"/>' == 1 ){
				$('#pageTitle').html('ְλ�ȼ����ⲿ����ѯ');
			}else{
				$('#pageTitle').html('ְλ�ȼ���ѯ');
			}
		}
	})(jQuery);
</script>