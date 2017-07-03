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
	 	// ��ʼ��ImportDTO
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
        <a class="close" data-dismiss="modal" onclick="closeModal();">��</a>
        <label id="importExcelTitleLableId">����򿨼�¼</label>
    </div>
    <div class="modal-body">
    	<div class="top">
    		<%
    			if( importHeaderVO == null )
    			{
    			   out.println( "<p>����ģ�廹δ���û�û��Ȩ�ޣ�����ϵ����Ա�����Զ��� - ���롱�н������ã� </p>" );
    			}
    			else{ 
    		%>
				<input type="button" class="save" name="btnImportExcelFile" id="btnImportExcelFile" value="�ϴ��ļ�" />
				<input type="button" class="reset" name="btnHandle" id="btnHandle" value="������..." style="display: none" disabled />
				<input type="button" class="function" name="btnDownload" id="btnDownload" value="����ģ��" onclick="link('downloadFileAction.do?proc=exportImportTemplate&fileType=excel&importHeaderId=<%=importHeaderId %>');" />
			<%} %>
		</div>
        <div id="attachmentsDiv"></div>
        <div id="uploadProgressPompDIV"></div>
    </div>
    <div class="modal-footer"></div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// ����ģ̬����
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
	
	// �ر�ģ̬���ڣ��Ƿ�ˢ��ҳ��
	function closeModal(){
		$('#importExcelModalId').addClass('hide');
    	$('#shield').hide();
    	link('recordAction.do?proc=list_object');
	};
	
	// Esc�����¼� - ���ص�����
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#importExcelModalId').addClass('hide');
	    	$('#shield').hide();
	    } 
	});
</script>