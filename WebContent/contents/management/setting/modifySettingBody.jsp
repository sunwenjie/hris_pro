<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="options" class="box toggableForm">
		<div class="head">
			<label><bean:message key="header.option.setting" /></label>
		</div>
		
		<div class="inner">	
	        <div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" />
				<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" />
			</div>
			
			<div id="tableWrapper">
				<!-- Include Form JSP 包含Form对应的jsp文件 --> 
				<%
					if(BaseAction.isInHouseRole(request,null)&&!BaseAction.isHRFunction(request,null)){
					   %>
							<jsp:include page="/contents/management/setting/listSettingTableBase.jsp" flush="true"></jsp:include>
					   <%
					}else{
					   %>
							<jsp:include page="/contents/management/setting/listSettingTable.jsp" flush="true"></jsp:include>
				   		<%
					}
				%>
			</div>
			
			<div class="bottom">
				<p></p>
			</div>
	    </div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 将Form设为Disable
		disableForm('modifySetting_form');
		// 取消按钮点击事件
		$('#btnCancel').click( function () {
			if(agreest())
			back();
		});
		// btnEdit按钮点击事件
		$('#btnEdit').click(function(){
			<%
				if(!BaseAction.isHRFunction(request,response)){
				   %>
				   	checkBase();
				   	return;
				   <%
				}
			%>
			if(getSubAction()=='viewObject'){
				enableForm('modifySetting_form');
				$('.modifySetting_form input#subAction').val('modifyObject');
				$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
			}else{
				var flag = 0;
				flag = flag + validate_nowords("manageSetting_baseInfoRank", true, "numeric", 0,0,true,0,0);
    			flag = flag + validate_nowords("manageSetting_messageRank", true, "numeric", 0,0,true,0,0);
    			flag = flag + validate_nowords("manageSetting_dataViewRank", true, "numeric", 0,0,true,0,0);
    			<logic:equal value="1" name="role">
    			flag = flag + validate_nowords("manageSetting_clientContractRank", true, "numeric", 0,0,true,0,0);
    			flag = flag + validate_nowords("manageSetting_ordersRank", true, "numeric", 0,0,true,0,0);
    			flag = flag + validate_nowords("manageSetting_settlementRank", true, "numeric", 0,0,true,0,0);
    			</logic:equal>
    			flag = flag + validate_nowords("manageSetting_contractServiceRank", true, "numeric", 0,0,true,0,0);
    			
    			flag = flag + validate_nowords("manageSetting_attendanceRank", true, "numeric", 0,0,true,0,0);
    			flag = flag + validate_nowords("manageSetting_sbRank", true, "numeric", 0,0,true,0,0);
    			flag = flag + validate_nowords("manageSetting_cbRank", true, "numeric", 0,0,true,0,0);
    			flag = flag + validate_nowords("manageSetting_paymentRank", true, "numeric", 0,0,true,0,0);
    			
    			flag = flag + validate_nowords("manageSetting_incomeRank", true, "numeric", 0,0,true,0,0);
    			if(flag==0){
					submitForm('modifySetting_form');
    			}else{
    			}
			}
		});
	})(jQuery);
	
	function getSubAction(){
		return $('.modifySetting_form input#subAction').val();
	}
	
	// 小权限验证
	function checkBase(){
		if(getSubAction()=='viewObject'){
			enableForm('modifySetting_form');
			$('.modifySetting_form input#subAction').val('modifyObject');
			$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
		}else{
			var flag = 0;
			
			flag = flag + validate_nowords("manageSetting_attendanceRank", true, "numeric", 0,0,true,0,0);
			flag = flag + validate_nowords("manageSetting_sbRank", true, "numeric", 0,0,true,0,0);
			flag = flag + validate_nowords("manageSetting_cbRank", true, "numeric", 0,0,true,0,0);
			flag = flag + validate_nowords("manageSetting_paymentRank", true, "numeric", 0,0,true,0,0);
				
			if(flag==0){
				submitForm('modifySetting_form');
			}
		}
	}
</script>
