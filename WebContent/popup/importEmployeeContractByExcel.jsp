<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.util.KANAccountConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.domain.define.TableDTO"%>
<%@ page import="com.kan.base.domain.define.ImportDTO"%>
<%@ page import="com.kan.base.domain.define.ImportHeaderVO"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
   String accessActionArray[] = { "contractAccessAction", "contractSalaryAccessAction", "contractSBAccessAction", "contractCBAccessAction", "contractLeaveAccessAction","contractOTAccessAction", "contractOtherAccessAction" };
   String accessNameArray[] = { "�Ͷ���ͬ", "н�귽��", "�籣�����𷽰�", "�̱�����", "�������","�ݼ�����", "��������" };
   Map< String, String > accessActionMap = new HashMap< String, String >();

   final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) );
   for ( String accessActionParameter : accessActionArray )
   {
      ImportHeaderVO importHeaderVO = null;
      String accessAction = request.getParameter( accessActionParameter );
      if ( accessAction != null )
      {
         final TableDTO tableDTO = accountConstants.getTableDTOByAccessAction( accessAction );
         if ( tableDTO != null && tableDTO.getTableVO() != null && ListRender.hasImportRight( request, tableDTO.getTableVO().getTableId() ) )
         {
            // ��ʼ��ImportDTO
            final ImportDTO importDTO = accountConstants.getImportDTOByTableId( tableDTO.getTableVO().getTableId(), ( String ) BaseAction.getClientId( request, null ) );

            if ( importDTO != null && importDTO.getImportHeaderVO() != null && KANUtil.filterEmpty( importDTO.getImportHeaderVO().getImportHeaderId() ) != null )
            {
               importHeaderVO = importDTO.getImportHeaderVO();
            }
         }
      }
      if ( importHeaderVO != null )
      {
         accessActionMap.put( accessActionParameter, importHeaderVO.getEncodedId() );
      }
      else
      {
         accessActionMap.put( accessActionParameter, null );
      }
   }
   String contractAccessImportHederEncodeId = accessActionMap.get(accessActionArray[0]);
   if(contractAccessImportHederEncodeId!=null){
%>
<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="importExcelModalId">
	<div class="modal-header" id="clientHeader" style="cursor: move;">
		<a class="close" data-dismiss="modal"
			onclick="$('#importExcelModalId').addClass('hide');$('#shield').hide();">��</a>
		<label id="importExcelTitleLableId"> Excel����</label>

	</div>
	<div class="modal-body">
		<div class="top">
			<input type="button" class="save" name="btnImportExcelFile"
				id="btnImportExcelFile" value="�ϴ��ļ�" onclick="uploadExcel_employeeContract();" /><!-- uploadExcel.submit(); -->
			<a href="#" onclick="linkDownloadFile();">����ģ��</a> <input
				type="button" class="function" name="btnDownload" id="btnDownload"
				value="����ģ��" onclick="linkDownloadFile();" />
		</div>
		<div id="extendImportModules">
			<%
				out.print("<label><input type='radio' name='accessImportHederEncodeId' checked = 'true' value='"+accessActionMap.get(accessActionArray[0])+"' /> "+accessNameArray[0]+"</label>");
				for(int i = 1;i<accessActionArray.length;i++)
				{
				   String encoedId = accessActionMap.get(accessActionArray[i]);
				   if(encoedId!=null)
				   {
						out.print("<label><input type='radio' name='accessImportHederEncodeId' value='"+encoedId+"' />"+accessNameArray[i]+"</label>");
				   }
				}
			%>
		</div>
		<div id="attachmentsDiv"></div>
		<div id="uploadProgressPompDIV"></div>
	</div>
	<div class="modal-footer"></div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// ����ģ̬����
	function popupExcelImport() {
		$('#importExcelModalId').removeClass('hide');
		$('#shield').show();
	};

	// �ϴ�Excel�ļ�
	function uploadExcel_employeeContract(){
		var encodedId = $("input[name=accessImportHederEncodeId]:checked").val();
		if(encodedId){
			// TODO ----- mark �����ٵ������ϴ���ť��ʱ�ϴ��ļ������޷��������upload·��
			createUploadExcel('btnImportExcelFile', 'uploadFileAction.do?proc=importExcel&importHeaderId='+encodedId, 'uploadFileAction.do?proc=getStatusMessage', 'common', 'tempExcelFolder').submit();
		}else{
			alert("����ѡ��һ��ģ��");
		}
	}
	
	// �����ļ�
	function linkDownloadFile(){
		var encodedId = $("input[name=accessImportHederEncodeId]:checked").val();
		if(encodedId){
			link('downloadFileAction.do?proc=exportImportTemplate&fileType=excel&importHeaderId='+encodedId );
		}else{
			alert("����ѡ��һ��ģ��");
		}
	}
	
	// Esc�����¼� - ���ص�����
	$(document).keyup(function(e) {
		if (e.keyCode == 27) {
			$('#importExcelModalId').addClass('hide');
			$('#shield').hide();
		}
	});
	
	$(document).ready(function(){
		// ���°� ����ͼƬ����popup����
		//$("#_importExcel_link").unbind('click');
		$("#_importExcel_link").attr('onclick','');
		$("#_importExcel_link").click(function(){
			popupExcelImport();
		});
	});
	
</script>
<%  
   }
%>