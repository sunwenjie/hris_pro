<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="socialBenefitHeader" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">个税起征添加</label>
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
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.add" />" /> 
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
			</div>
			<!-- Include Form JSP 包含Form对应的jsp文件 -->  
			<jsp:include page="/contents/system/incomeTaxBase/form/manageIncomeTaxBaseForm.jsp" flush="true"/>
		</div>
	</div>
</div>
<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_IncomeTax').addClass('selected');
		$('#menu_system_IncomeTaxBase').addClass('selected');

		// 更改按钮显示名
		$('#btnEdit').val('保存');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageIncomeTaxBase_form input#subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageIncomeTaxBase_form');
				// 更改SubAction
        		$('.manageIncomeTaxBase_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('保存');
        		// 更换Page Title
        		$('#pageTitle').html('起征编辑');
				// 更改Form Action
        		$('.manageIncomeTaxBase_form').attr('action', 'incomeTaxBaseAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageIncomeTaxBase_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageIncomeTaxBase_nameEN", false, "common", 100, 0);
    			flag = flag + validate("manageIncomeTaxBase_base", false, "currency", 20, 0);
    			flag = flag + validate("manageIncomeTaxBase_baseForeigner", false, "currency", 20, 0);
    			flag = flag + validate("manageIncomeTaxBase_description", false, "common", 500, 0);
    			flag = flag + validate("manageIncomeTaxBase_status", true, "select", 0, 0);
    			if(flag == 0){
    				submit('manageIncomeTaxBase_form');
    			}
        	}
		});
		
		$('.manageIncomeTaxRangeHeader_startDate').focus(function(){
			WdatePicker({ 
				maxDate: '#F{$dp.$D(\'endDate\')}' 
			});
		});
		
		$('.manageIncomeTaxRangeHeader_endDate').focus(function(){
			WdatePicker({ 
				minDate: '#F{$dp.$D(\'startDate\')}',
				maxDate:'2020-10-01'
			});
		});
		
		// 查看模式
		if($('.manageIncomeTaxBase_form input.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('manageIncomeTaxBase_form');
			// 更换Page Title
			$('#pageTitle').html('起征查询');
			// 更换按钮Value
			$('#btnEdit').val('编辑');
		}

		// 取消按钮点击事件
		$('#btnList').click( function () {
			if (agreest())
			link('incomeTaxBaseAction.do?proc=list_object');
		});	
	})(jQuery);
</script>