<%@page import="com.kan.base.domain.MappingVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%-- ��Ҫ���� {
				scopeType���������ͣ�
			 	CKEditElementId(manageLaborContractTemplate_content)��CKEdit�����ID
			 }
 --%>
<%
	String scopeType = request.getParameter("scopeType");
	String CKEditElementId = request.getParameter("CKEditElementId");
	List<MappingVO> constants = new ArrayList<MappingVO>();
	constants.add(KANUtil.getEmptyMappingVO( request.getLocale() ));
	List<MappingVO> tempConstants = KANConstants.getKANAccountConstants( (String)request.getAttribute("accountId") ).getConstantsByScopeType( request.getLocale().getLanguage(), scopeType);
	if(tempConstants!=null){
	   constants.addAll(tempConstants);
	}
	request.setAttribute("constants",constants);
%>
<!-- Module Box HTML: Begins -->
<div class="modal hide" id="selectModalId">
    <div class="modal-header">
        <a class="close" data-dismiss="modal" onclick="$('#selectModalId').addClass('hide');$('#shield').hide();" title="<bean:message bundle="public" key="button.close" />"></a>
        <label><bean:message bundle="message" key="message.paramerer.title.add" /></label>
    </div>
    <div class="modal-body">
       	<ol class="static">
        	<li>
           		<label><bean:message bundle="message" key="message.parameter.insert" /></label>
				<select name="constants" id="constants" class="constandId">
				<logic:notEmpty name="constants">
					<logic:iterate id="constant" name="constants" >
						<option value='<bean:write name="constant" property="mappingId"/>'> <bean:write name="constant" property="mappingValue"/> </option>
					</logic:iterate>
				</logic:notEmpty>
				</select>
				
			</li>
        </ol>
    </div>
    <div class="modal-footer">
    	<input type="button" id="btn_modal_confirm" name="btn_modal_confirm" value="<bean:message bundle="public" key="button.confirm" />" onclick="addProperties()"/>
    	<input type="button" id="btn_modal_cancel" name="btn_cancel_addValue" class="reset" value="<bean:message bundle="public" key="button.cancel" />" />
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
(function($) {

//Esc�����¼� - ���ص�����
$(document).keyup(function(e){
    if (e.keyCode == 27) 
    {
    	$('#selectModalId').addClass('hide');
    	$('#shield').hide();
    } 
});
//Module Cancel����ȡ���¼�
$('#btn_modal_cancel').click(function(){
	$('#selectModalId').addClass('hide');
	$('#shield').hide();
});

})(jQuery);
//	��ʾ������¼�
function showPropertiesModal(){
	$('#selectModalId').removeClass('hide');
	$('#shield').show();
};
 
//����ֵ�¼�
function addProperties(){
	//	��ȡѡ�����ֵ������
	var constandId = $('.constandId').val();
	//	��ȡselected���ı�ֵ
	var constant = $('.constandId option:selected').text();

	//	���ûѡ���������ͣ���ʾ��Ϣ
	if( constandId == 0)
	{
		alert('<bean:message bundle="public" key="popup.not.selected.option" />');
		return;
	};
	
	//	��������������ַ���
	var content = ($('#<%=CKEditElementId%>').val() == null) ? '' : $('#<%=CKEditElementId%>').val();

	//	����������ʽ������ѯ
	var reg = new RegExp("{" + constant + "_" + constandId, "g") ;
	//	���ҳ��ֵĴ���
	var num = (content.match(reg) == null) ? 0 : content.match(reg).length;
	CKEDITOR.instances.<%=CKEditElementId%>.insertHtml("$" + "{" + constant + "_" + constandId + "_" + (num + 1) + "}");
	// ���������ɺ�ر�ģ̬��
	$('#btn_modal_cancel').trigger('click');
};	
</script>