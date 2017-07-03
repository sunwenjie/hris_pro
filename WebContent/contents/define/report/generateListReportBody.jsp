<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.web.renders.define.ReportRender"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final String headerId = ( String )request.getAttribute( "headerId" );
	final String tableId = ( String )request.getAttribute( "tableId" );
%>	

<div id="content">
	<!-- Information Report Search Form -->
	<%= ReportRender.generateReportSearch( request, tableId, headerId ) %>
	
	<!-- Information Report Table Page -->
	<%= ReportRender.generateReportList( request, tableId, headerId ) %>
</div>
<script type="text/javascript">
	(function($) {
		// ��ʼ���˵���ʽ 
		<logic:notEmpty name="moduleName">
			var subNodeId = '<bean:write name="moduleName" />';
			var parentNodeId = $('#' + subNodeId).parents('li').attr('id');
			if( subNodeId == 'null'){
				$('#menu_define_Modules').addClass('current');
				$('#menu_define_Report').addClass('selected');
			}else{
				$('#' + parentNodeId).parents('li').addClass('current');
				$('#' + parentNodeId).addClass('selected');
				$('#' + subNodeId).addClass('selected');
			}
		</logic:notEmpty>
		<logic:empty name="moduleName">
			$('#menu_define_Modules').addClass('current');
			$('#menu_define_Report').addClass('selected');
			$('#btnList').show();
			$('#btnList').click( function(){
				link('reportHeaderAction.do?proc=list_object');
			});
		</logic:empty>
// 		var provinceId = $('#cityId').val();
		$("select[id$='_provinceId']").each(function(){  
			  var provinceId = $(this).attr("id");
			  var cityId = provinceId.replace("_provinceId","");
		 	$('.'+provinceId).change( function () { 
			  provinceChange(provinceId, 'searchObject',  $('#'+cityId+"_city_value").val(), cityId);
	 		});
// 		 	if($('#'+cityId).val()!=null){
		 		
// 		 	}
		 	$('.'+provinceId).trigger('change');
				//��ʼ��ʡ�ݿؼ�
		}); 
		
// 		if( cityId != undefined){
// 			//��ʼ��ʡ�ݿؼ�
// 			provinceChange('REPORT_provinceId', 'searchObject', cityId, 'cityId');
// 		}
		
// 		//��ʡ��Change�¼�
// 		$('.REPORT_provinceId').change( function () { 
// 			provinceChange('REPORT_provinceId', 'searchObject', 0, 'cityId');
// 		});
		
		// ����form
		$('#resetBtn').click(function(){
			// �����ı���
			$('.list_form input:visible').each(function(){
				$(this).val('');
			});
			
			// ����������
			$('.list_form select:visible').each(function(){
				$(this).val('0');
				// �����ʡ��
				if($(this).attr('id')=='provinceId'){
					$('#cityId').val('0');
					$('.REPORT_provinceId').trigger('change');
				}
			});
		});
	})(jQuery);
</script>