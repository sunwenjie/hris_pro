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
   String accessNameArray[] = { "劳动合同", "薪酬方案", "社保公积金方案", "商保方案", "请假设置","休假设置", "其他设置" };
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
            // 初始化ImportDTO
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
			onclick="$('#importExcelModalId').addClass('hide');$('#shield').hide();">×</a>
		<label id="importExcelTitleLableId"> Excel导入</label>

	</div>
	<div class="modal-body">
		<div class="top">
			<input type="button" class="save" name="btnImportExcelFile"
				id="btnImportExcelFile" value="上传文件" onclick="uploadExcel_employeeContract();" /><!-- uploadExcel.submit(); -->
			<a href="#" onclick="linkDownloadFile();">下载模板</a> <input
				type="button" class="function" name="btnDownload" id="btnDownload"
				value="下载模板" onclick="linkDownloadFile();" />
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
	// 弹出模态窗口
	function popupExcelImport() {
		$('#importExcelModalId').removeClass('hide');
		$('#shield').show();
	};

	// 上传Excel文件
	function uploadExcel_employeeContract(){
		var encodedId = $("input[name=accessImportHederEncodeId]:checked").val();
		if(encodedId){
			// TODO ----- mark 不能再点击这个上传按钮后及时上传文件，且无法更改这个upload路径
			createUploadExcel('btnImportExcelFile', 'uploadFileAction.do?proc=importExcel&importHeaderId='+encodedId, 'uploadFileAction.do?proc=getStatusMessage', 'common', 'tempExcelFolder').submit();
		}else{
			alert("请先选择一个模块");
		}
	}
	
	// 下载文件
	function linkDownloadFile(){
		var encodedId = $("input[name=accessImportHederEncodeId]:checked").val();
		if(encodedId){
			link('downloadFileAction.do?proc=exportImportTemplate&fileType=excel&importHeaderId='+encodedId );
		}else{
			alert("请先选择一个模块");
		}
	}
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e) {
		if (e.keyCode == 27) {
			$('#importExcelModalId').addClass('hide');
			$('#shield').hide();
		}
	});
	
	$(document).ready(function(){
		// 重新绑定 导入图片弹出popup函数
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