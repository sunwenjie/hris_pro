<%@page import="com.kan.base.domain.MappingVO"%>
<%@page import="com.kan.base.domain.security.BranchVO"%>
<%@page import="com.kan.base.domain.security.PositionVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page pageEncoding="GBK" %>
<%@	page import="com.kan.base.web.action.BaseAction" %>
<%@ page import="com.kan.base.util.KANUtil" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<style type="text/css">
	/* 效果CSS开始 */
	.selectbox{width:555px;height:220px;margin:0px auto;}
	.selectbox div{float:left;}
	.selectbox .select-bar{padding:0 20px;}
	.selectbox .select-bar select{width:180px;height:200px;border:1px #A0A0A4 solid;padding:4px;font-size:14px;font-family:"microsoft yahei";}
	.btn-bar{}
	.btn-bar p{margin-top:16px;}
	.btn-bar p .btn{width:50px;height:30px;cursor:pointer;font-family:simsun;font-size:14px;}
	/* 效果CSS结束 */
</style>

<%
	final Map< String, List< PositionVO> > maps = (Map)request.getAttribute( "branch_position_map" );
%>

<!-- Module Box HTML: Begins -->
<div class="popup modal midsize content hide" id="copyBranchOCModalId">
    <div class="modal-header" id="copyBranchOCHeader">
        <a class="close" data-dismiss="modal" onclick="hidePopup();">×</a>
        <label>Copy Branch O-Chart</label>
    </div>
    
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnPopupSave" id="btnPopupSave" value="<bean:message bundle="public" key="button.save" />" onclick="validate_popup_form();" />
	    </div>
        <html:form action="branchAction.do?proc=copyBranchO_Chart" styleClass="popupBranch_from">
	       	<%= BaseAction.addToken( request ) %>
        	<fieldset>
        		<logic:notEmpty name="BUFunction">
        			<input type="hidden" name="rootId" value="<bean:write name="BUFunction" property="branchId" />" />
        			<input type="hidden" name="copyBranchIdArray" value="<bean:write name="BUFunction" property="branchId" />" />
	        		<ol class="auto">
	        			<li>
	        				<label>New BU/Function Name (CN)</label>
	        				<input type="text" name="copyBranchNameZHArray" class="copyBranchNameZHArray_<bean:write name="BUFunction" property="branchId" />" value="<bean:write name="BUFunction" property="nameZH" />" />
	        			</li>
	        			<li>
	        				<label>New BU/Function Name (EN)</label>
	        				<input type="text" name="copyBranchNameENArray" class="copyBranchNameENArray_<bean:write name="BUFunction" property="branchId" />" value="<bean:write name="BUFunction" property="nameEN" />" />
	        			</li>
	        		</ol>
        		</logic:notEmpty>
        		<ol class="auto">
        			<logic:iterate id="copyBranchVO" name="branchForm" property="copyBranchVOs" >
        			<input type="hidden" name="copyBranchIdArray" value="<bean:write name="copyBranchVO" property="mappingId" />" />
        				<li>
        					<label>EX- Department (CN)</label>
        					<input type="text" value="<bean:write name="copyBranchVO" property="mappingValue" />" disabled="disabled" />
        				</li>
        				<li>
        					<label>New Department (CN)</label>
        					<input type="text" name="copyBranchNameZHArray" class="copyBranchNameZHArray_<bean:write name="copyBranchVO" property="mappingId" />" value="<bean:write name="copyBranchVO" property="mappingValue" />" />
        				</li>
        				<li>
        					<label>EX- Department (EN)</label>
        					<input type="text" value="<bean:write name="copyBranchVO" property="mappingTemp" />" disabled="disabled" />
        				</li>
        				<li>
        					<label>New Department (EN)</label>
        					<input type="text" name="copyBranchNameENArray" class="copyBranchNameENArray_<bean:write name="copyBranchVO" property="mappingId" />" value="<bean:write name="copyBranchVO" property="mappingTemp" />" />
        				</li>
        				<div class="selectbox">
        					<div class="select-bar">
        						<select multiple="multiple" id="leftSelect">
        							<%
        								MappingVO tempMappingVO = (MappingVO)pageContext.getAttribute( "copyBranchVO" );
        								if( tempMappingVO != null && maps.get( tempMappingVO.getMappingId() ) != null )
        								{
        								   final List< PositionVO > positionVOs = (List< PositionVO >)maps.get( tempMappingVO.getMappingId() );
        								   for( PositionVO temp : positionVOs )
        								   {
        								      out.println( "<option>" + temp.getTitleZH() + "</otpion>" );
        								   }
        								}
        							%>
        						</select>
        					</div>	
       						<div class="btn-bar">
							    <p><span onclick="moveToRight();"><input type="button" class="btn" value="&gt;" title="移动选择项到右侧"/></span></p>
							    <p><span onclick="moveAllToRight();"><input type="button" class="btn" value="&raquo;" title="全部移到右侧"/></span></p>
							    <p><span onclick="moveToLeft();"><input type="button" class="btn" value="&lt;" title="移动选择项到左侧"/></span></p>
							    <p><span onclick="moveAllToLeft();"><input type="button" class="btn" value="&laquo;" title="全部移到左侧"/></span></p>
							</div>
							<div class="select-bar">	
								<select multiple="multiple" id="rightSelect"></select>
        					</div>
        					<div class="clear"></div>
        				</div>
					</logic:iterate>
        		</ol>
        	</fieldset>
		</html:form >
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	(function($) {
		$('#btnPopupSave').click( function(){
			if( validate_popup_form() == 0 ) {
				if(confirm('Once saved, can not be changed, continue to operate?')){
					submit('popupBranch_from');
					//submitForm('popupBranch_from', null, null, null, null, 'search-results', null, 'hidePopup()');
				}
			}
		});
		
		moveToRightForDoubleClick();
		moveToLeftForDoubleClick();
	})(jQuery);
	
	// 校验form
	function validate_popup_form(){
		var flag = 0;
		$('input[class^="copyBranchNameZHArray_"]').each( function(i){
			validate($(this).attr('class'), true, "common", 0, 0);
		});
		$('input[class^="copyBranchNameENArray_"]').each( function(i){
			validate($(this).attr('class'), true, "common", 0, 0);
		});
		
		if( $('.popupBranch_from ol li label').hasClass('error') ){
			flag ++;
		}
		return flag;
	};
	
	// 隐藏弹出框
	function hidePopup(){
		$('#copyBranchOCModalId').addClass('hide');
    	$('#shield').hide();
	};
</script>