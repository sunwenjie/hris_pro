<%@	page import="com.kan.base.domain.define.ImportHeaderVO"%>
<%@	page import="com.kan.base.util.KANUtil"%>
<%@	page import="com.kan.base.web.renders.util.ListRender"%>
<%@	page import="com.kan.base.domain.define.ImportDTO"%>
<%@	page import="com.kan.base.domain.define.TableDTO"%>
<%@	page import="com.kan.base.util.KANConstants"%>
<%@	page import="com.kan.base.util.KANAccountConstants"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	ImportHeaderVO importHeaderVO = null;
	String importHeaderId = null;
	final KANAccountConstants accountConstants =  KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) );
	final TableDTO tableDTO = accountConstants.getTableDTOByAccessAction( "HRO_BIZ_ATTENDANCE_RECORD" );
	if (tableDTO != null && tableDTO.getTableVO() != null && ListRender.hasImportRight( request, tableDTO.getTableVO().getTableId() ) )
	{
	 	// 初始化ImportDTO
      	final ImportDTO importDTO = accountConstants.getImportDTOByTableId( tableDTO.getTableVO().getTableId(), ( String ) BaseAction.getCorpId( request, null ) );

      	if ( importDTO != null && importDTO.getImportHeaderVO() != null && KANUtil.filterEmpty( importDTO.getImportHeaderVO().getImportHeaderId() ) != null )
      	{
        	importHeaderVO = importDTO.getImportHeaderVO();
        	importHeaderId = importHeaderVO.getEncodedId();
	  	}
	}
%>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="importExcelModalId">
    <div class="modal-header" id="clientHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="closeModal();">×</a>
        <label id="importExcelTitleLableId">导入打卡记录</label>
    </div>
    <div class="modal-body">
    	<div class="top">
    		<%
    			if( importHeaderVO == null )
    			{
    			   out.println( "<p>导入模板还未配置或没有权限，请联系管理员到“自定义 - 导入”中进行配置！ </p>" );
    			}
    			else{ 
    		%>
				<input type="button" class="save" name="btnImportExcelFile" id="btnImportExcelFile" value="上传文件" />
				<input type="button" class="reset" name="btnHandle" id="btnHandle" value="操作中..." style="display: none" disabled />
				<input type="button" class="function" name="btnDownload" id="btnDownload" value="下载模板" onclick="link('downloadFileAction.do?proc=exportImportTemplate&fileType=excel&importHeaderId=<%=importHeaderId %>');" />
			<%} %>
		</div>
        <div id="attachmentsDiv"></div>
        <div id="uploadProgressPompDIV"></div>
    </div>
    <div class="modal-footer"></div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// 弹出模态窗口
	function popupExcelImport(){
		$('#importExcelModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	<%
	if(importHeaderVO != null){
	%>  
		var uploadExcel = createUploadExcel('btnImportExcelFile', 'uploadFileAction.do?proc=importExcel&importHeaderId=<%=importHeaderVO.getEncodedId() %>&accessAction=HRO_BIZ_ATTENDANCE_RECORD', 'uploadFileAction.do?proc=getStatusMessage', 'common', 'tempExcelFolder');
	<%} 	
	%>
	
	// 关闭模态窗口，是否刷新页面
	function closeModal(){
		$('#importExcelModalId').addClass('hide');
    	$('#shield').hide();
    	link('recordAction.do?proc=list_object');
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#importExcelModalId').addClass('hide');
	    	$('#shield').hide();
	    } 
	});
</script>