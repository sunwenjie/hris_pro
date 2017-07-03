<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.util.KANAccountConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.domain.define.TableDTO"%>
<%@ page import="com.kan.base.domain.define.ImportDTO"%>
<%@ page import="com.kan.base.domain.define.ImportHeaderVO"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="importExcelModalId">
    <div class="modal-header" id="clientHeader" title="<bean:message bundle="public" key="button.close" />">
        <a class="close" data-dismiss="modal" onclick="closeModal();">×</a>
        <label id="importExcelTitleLableId">Excel导入</label>
    </div>
    <div class="modal-body">
    	<div class="top">
<%
	ImportHeaderVO importHeaderVO = null;
	final String accessAction = request.getParameter("accessAction");
// 	String closeRefesh = 'false';
	final String needRemark = request.getParameter("needRemark");//是否需要录备注
	
	final KANAccountConstants accountConstants =  KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) );
	final TableDTO tableDTO = accountConstants.getTableDTOByAccessAction(accessAction);
	
	if (tableDTO != null && tableDTO.getTableVO() != null && ListRender.hasImportRight( request, tableDTO.getTableVO().getTableId() ) )
	{
	 	// 初始化ImportDTO
      	final ImportDTO importDTO = accountConstants.getImportDTOByTableId( tableDTO.getTableVO().getTableId(), ( String ) BaseAction.getCorpId( request, null ) );

      	if ( importDTO != null && importDTO.getImportHeaderVO() != null && KANUtil.filterEmpty( importDTO.getImportHeaderVO().getImportHeaderId() ) != null )
      	{
        	importHeaderVO = importDTO.getImportHeaderVO();
	  	}
	}
	
   if ( importHeaderVO == null )
   {
%>  
	<p>导入模板还未配置，请联系管理员到“自定义 - 导入”中进行配置！ </p>
<%
   	} 
   else
   {
%>

		<%
		   if ( StringUtils.isNotBlank( importHeaderVO.getDescription() ) )
		      {
		%>
			<div id="importDescription" class="message importDescription success " style="font-size: 10px;">
				<div style="margin-right: 25px;">
					<%=importHeaderVO.getDescription()%>
				</div>
				<div>
					<a onclick="$('#importDescription').hide();" class="messageCloseButton" style="top: 5px;">&nbsp;</a>
				</div>
			</div>
		<%
		   }
		%>
		<%
		   if ( StringUtils.isNotBlank( needRemark ) && "true".equals( needRemark ) )
		      {
			%>
			<label style="float: left;"><bean:message bundle="public" key="public.note" />：</label>
				<textarea id="description" name="description" style="width: 400px;height: 100px;"></textarea>
			<br/>
			<br/>
		<%
		}
		%>
		<input type="button" class="save" name="btnImportExcelFile" id="btnImportExcelFile" value="<bean:message bundle="public" key="button.upload.file" />" />
		<input type="button" class="reset" name="btnHandle" id="btnHandle" value="<bean:message bundle="public" key="button.system.operation" />" style="display: none" disabled />
		<input type="button" class="function" name="btnDownload" id="btnDownload" value="<bean:message bundle="public" key="button.download.template" />" onclick="link('downloadFileAction.do?proc=exportImportTemplate&fileType=excel&importHeaderId=<%=importHeaderVO.getEncodedId() %>');" />
<%
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
	<%if ( importHeaderVO != null )
         {%>  
	var uploadExcel = createUploadExcel('btnImportExcelFile', 'uploadFileAction.do?proc=importExcel&importHeaderId=<%=importHeaderVO.getEncodedId() %>&accessAction=<%=accessAction%>', 'uploadFileAction.do?proc=getStatusMessage', 'common', 'tempExcelFolder');
	<%}%>

	// 弹出模态窗口
	function popupExcelImport(){
		$('#importExcelModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	// 关闭模态窗口，是否刷新页面
	function closeModal(){
		$('#importExcelModalId').addClass('hide');$('#shield').hide();
		var importSuccess = $('#importSuccess').val();
	    if('1'==importSuccess){
			location.reload();
	    }
	}
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#importExcelModalId').addClass('hide');
	    	$('#shield').hide();
	    	var importSuccess = $('#importSuccess').val();
	    	if('1'==importSuccess){
				location.reload();
	    	}
	    } 
	});
</script>