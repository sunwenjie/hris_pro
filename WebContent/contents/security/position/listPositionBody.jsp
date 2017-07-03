<%@page import="java.net.URLDecoder"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@page import="com.kan.base.domain.security.PositionDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.kan.base.web.actions.security.PositionAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.renders.security.PositionRender"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box toggableForm" id="position-information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="security" key="security.position.search.title" /></label>
		</div>
	</div>
	<!-- Position-information -->
	<div class="box noHeader" id="search-results">
		<!-- inner -->
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
				<kan:auth right="new" action="<%=PositionAction.accessAction%>">
					<input type="button" class="save" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('positionAction.do?proc=to_objectNew&rootPositionId='+$('#rootPositionId').val()+'&tempPositionPrefer='+$('#tempPositionPrefer').val());" />
				</kan:auth>				<%--按架构图显示职位 --%>
				<logic:equal value="1" name="positionPrefer">
					<kan:auth right="export" action="<%=PositionAction.accessAction%>">
						<input type="button" class="" id="btnExport" name="btnExport" value="<bean:message bundle="public" key="button.export.orgpdf" />" onclick="exportPdf();" />
<%-- 						<input type="button" class="" id="btnExcel" name="btnExcel" value="<bean:message bundle="public" key="button.export.excel" />" onclick="exportExcel();" /> --%>
					</kan:auth>
					<img title="<bean:message bundle="security" key="security.branch.save.current.organization.chart" />" src="images/icons/save.png" onclick="saveOrgShoot();" style="float: right;cursor: pointer;">
					<img title="<bean:message bundle="security" key="security.branch.view.history.organization.chart" />" src="images/icons/save_history.png" onclick="listHistoryOrgShoot();" style="float: right;margin-right: 10px;cursor: pointer;">
					<img title="<bean:message bundle="security" key="security.position.switch.t.chart" />" src="images/icons/transfer.png" class="displayImage" onclick="changeDisplay();" style="float: right;margin-right: 10px;cursor: pointer;">
				</logic:equal>
				
				<%--按树状图显示职位 --%>
				<logic:equal value="2" name="positionPrefer">
					<kan:auth right="delete" action="<%=PositionAction.accessAction%>">
						<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanTree_deleteConfirm('kantree','<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitFormWithActionAndCallback('position_tree_form', 'deleteObjects', null, null, null, '', null, 'window.location.reload();');" />
					</kan:auth>
					<input type="button" class="function" id="btnShowAllTree" name="btnShowAllTree" onclick="btnShowAllTreeClick(this);" value="<bean:message bundle="public" key="button.display.all" />" />
				    <kan:auth right="transfer" action="<%=PositionAction.accessAction%>">
				        <input type="button" class="function" id="btnTransferHROwner" value="<bean:message bundle='security' key='transfer.hr.owner' />" onclick="popupTransferHROwner();" />
					</kan:auth>
					<img title="<bean:message bundle="security" key="security.position.switch.o.chart" />" src="images/icons/transfer.png" class="displayImage" onclick="changeDisplay();" style="float: right;cursor: pointer;">
					<!--  导出Excel -->
					<a id="exportExcel" name="exportExcel" class="commonTools" title="按部门等级导出" onclick="linkForm('position_tree_form', 'downloadObjects', null, 'fileType=excel&&exportType=branch');" style="float: right;margin-right: 10px;cursor: pointer;"><img src="images/appicons/excel_16_other.png" /></a> 
					<a id="exportExcel" name="exportExcel" class="commonTools" title="按职位等级导出" onclick="linkForm('position_tree_form', 'downloadObjects', null, 'fileType=excel&&exportType=position');" style="float: right;margin-right: 10px;cursor: pointer;"><img src="images/appicons/excel_16.png" /></a> 
					<!--  <a id="exportExcel" name="exportExcel" class="commonTools" title="按职位等级导出" onclick="linkForm('position_tree_form', 'downloadObjects', null, 'fileType=excel&&exportType=tree');" style="float: right;margin-right: 10px;cursor: pointer;"><img src="images/appicons/excel_16.png" /></a> -->
				</logic:equal>
			</div>
			<!-- top -->
			
			<html:form action="positionAction.do?proc=list_object" styleClass="position_tree_form">
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="rootPositionId" id="rootPositionId" value="" /> 
				<input type="hidden" name="PositionId" id="PositionId" value="" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="" />
				<input type="hidden" name="tempPositionPrefer" id="tempPositionPrefer" value="<bean:write name='positionPrefer'/>" />

				<%--按树状图显示职位 --%>
				<logic:equal value="2" name="positionPrefer">
					 <div id="position_info" class="kantab kantree">
						<ol class="static">
							<%= PositionRender.getPositionTree( request ) %> 
						</ol>
					</div> 
				</logic:equal>
				
				<%--按架构图显示职位 --%>
				<logic:notEqual value="2" name="positionPrefer">
					<div id="tab">
						<div class="tabMenu"> 
							<ul>
								<li id="tabMenu1" onClick="changeTab(1,99)" class="first hover"><bean:message bundle="security" key="security.position.current.chart" /></li>
							</ul>
						</div>  
						<div class="tabContent"> 
							<div id="tabContent1" class="kantab">
								<%=PositionRender.getSelectHtml(request)%>
								<div id="showOrgChartDiv" align="center">
							
								</div>
							</div>
						</div>
					</div>
				</logic:notEqual>
			</html:form>
			<!-- List Component -->
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->
</div>

<!-- include popup -->
<div id="popupWrapper">
	<jsp:include page="/popup/saveOrgShoot.jsp"></jsp:include>
	<jsp:include page="/popup/listOrgShoots.jsp"></jsp:include>
	<jsp:include page="/popup/listHistoryStaff.jsp"></jsp:include>
	<jsp:include page="/popup/transferHROwner.jsp"></jsp:include>
</div>	

<script type="text/javascript">
	(function($) {
		$('#menu_security_Modules').addClass('current');			
		$('#menu_security_PositionManagement').addClass('selected');
		$('#menu_security_Position').addClass('selected');
		
		kanList_init();
		kanCheckbox_init();
		
		$("#orgChartSel").change(function(){
			var rootPositionId = encodeURI(encodeURI($(this).val()));
			$("#rootPositionId").val(rootPositionId);
			loadHtml("#showOrgChartDiv", "positionAction.do?proc=getOrgChart&positionId="+rootPositionId,false);
		});
		$("#orgChartSel").change();
		
		$(".node").live("mouseover",function(){
			$(this).css("border-color","#9d9be1");
			$(this).find(".operaImgAdd").show();
			$(this).find(".operaImgDel").show();
		});
		
		$(".node").live("mouseout",function(){
			$(this).css("border-color","#ffffff");
			$(this).find(".operaImgAdd").hide();
			$(this).find(".operaImgDel").hide();
		});
		
		jQuery.fn.justtext = function() {
		    return $(this).clone().children().remove().end().text();
		};
		
		$(".orgShootChartSel").live("change",function(){
			var rootPositionId = encodeURI(encodeURI($(this).val()));
			var shootId = $(this).next("input").val();
			var tabIndex = $(this).next("input").next("input").val();
			var createDate = $(this).next("input").next("input").next("input").val();
			loadHtml("#showOrgChartDiv"+tabIndex, "shootAction.do?proc=getOrgShootChart&pageType=position&shootId="+shootId+"&positionId="+rootPositionId,false,"$('.showOrgChartLabel"+shootId+"').text('"+$(this).find('option:selected').text()+"（shot at "+createDate+"）')");
		});	
		
		// 删除图片的显示和隐藏 
		$(".tabMenu ul li").live("mouseover",function(){
			$(this).find(".tabMenu_history").hide();
			$(this).find(".tabMenu_disable").show();
		}).live("mouseout",function(){
			$(this).find(".tabMenu_history").show();
			$(this).find(".tabMenu_disable").hide();
		});
	})(jQuery);
	
	// 修改或新建Position
	function managePosition(positionId){
		link('positionAction.do?proc=to_objectModify&tempPositionPrefer='+$('#tempPositionPrefer').val()+'&rootPositionId='+$('#rootPositionId').val()+'&positionId=' + positionId);
	};
	
	function deletePosition(id){
		$(".position_tree_form #selectedIds").val(id);
		// 提交删除操作
		if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')){
			var reloadTarget = "tableWrapper";
			if(1 == "<bean:write name='positionPrefer'/>"){
				reloadTarget = "showOrgChartDiv";
			}
			submitForm('position_tree_form', 'deleteObject', null, null, null, reloadTarget);
		}
	}
	
	function exportPdf(){
		var shootId = "";
		//获取当前所有tab中有hover的
		$(".tabMenu li[id^='tabMenu']").each(function(){
			if($(this).hasClass("hover")){
				var thisClass = $(this).attr("class");
				if(thisClass=='first hover'){
					
				}else{
					shootId = $(this).find("input").val();
				}
			}
		});
		
		var data="[";
		$("#chart_"+shootId+"_0").find(".node").each(function(){
			var vis = $(this).parent().parent().css("visibility");
			if(vis != 'hidden'){
				var left = $(this).offset().left;
				var top = $(this).offset().top;
				var width = $(this).width();
				var height = $(this).height();
				var positionId = $(this).find("[name=hiddenPositionId]").val();
				var parentId = $(this).find("[name=hiddenParentPositionId]").val();
				var positionNameZH = $(this).children('div').children('a').justtext().replace(/\u00a0/g, "");//去除&nbsp;已经被text转义了，只能这么写
				var positionNameEN = $(this).children('div').children('a').children('p').text().replace(/\u00a0/g, "");
				data = data+"{";
				data = data+"\"left\":"+left+",";
				data = data+"\"top\":"+top+",";
				data = data+"\"width\":"+width+",";
				data = data+"\"height\":"+height+",";
				data = data+"\"positionId\":\""+positionId+"\",";
				data = data+"\"parentId\":\""+parentId+"\",";
				data = data+"\"positionNameZH\":\""+positionNameZH+"\",";
				data = data+"\"positionNameEN\":\""+positionNameEN+"\"";
				data = data+"},";
			}
		});
		data = data.substring(0,data.length-1);
		data = data+"]";
		data = encodeURI(encodeURI(data,"utf-8"),"utf-8");
		var maxWidth = 0;
		$(".orgChart").each(function(){
			tempWidth = $(this).find(".node-cells").width();
			if(tempWidth>maxWidth){
				maxWidth = tempWidth;
			}
		});
		
		var maxHeight = 0;
		$(".orgChart").each(function(){
			maxHeight = (maxHeight + $(this).height());
		});
		var titleText =	$('.showOrgChartLabel'+shootId).text();
		var titleLeft = $('.showOrgChartLabel'+shootId).offset().left;
		var titleTop = $('.showOrgChartLabel'+shootId).offset().top;
		var form=$("<form>");//定义一个form表单
		form.attr("style","display:none");
		form.attr("target","");
		form.attr("method","post");
		form.attr("action","positionAction.do?proc=exportPdf&maxWidth="+maxWidth+"&maxHeight="+maxHeight);
		var input1=$("<input>");
		input1.attr("type","hidden");
		input1.attr("name","data");
		input1.attr("value",data);
		var input2=$("<input>");
		input2.attr("type","hidden");
		input2.attr("name","title");
		var title = "{'titleName':'"+titleText+"',titleLeft:"+titleLeft+",titleTop:"+titleTop+"}";
		input2.attr("value",encodeURI(encodeURI(title,"utf-8"),"utf-8"));
		$("body").append(form);//将表单放置在web中
		form.append(input1);
		form.append(input2);
		form.submit();//表单提交
	};
	
	function changeDisplay(){
		var prefer = $("#tempPositionPrefer").val();		
		if(prefer==2){
			// 切换到组织架构
			$("#tempPositionPrefer").val('1');
		}else{
			// 切换到树
			$("#tempPositionPrefer").val('2');
		}
		link("positionAction.do?proc=list_object&rootPositionId="+$("#rootPositionId").val()+"&tempPositionPrefer="+$("#tempPositionPrefer").val());
	};
	
	// 保存当前架构图快照
	function saveOrgShoot(){
		// 赋值默认名字为当前的日期
		var myDate = new Date();
		var year = myDate.getFullYear();
		var month = (myDate.getMonth()+1)<10?("0"+(myDate.getMonth()+1)):myDate.getMonth()+1;
		var day = myDate.getDate()<10?"0"+myDate.getDate():myDate.getDate();
		var hour = myDate.getHours()<10?"0"+myDate.getHours():myDate.getHours();
		var minute = myDate.getMinutes()<10?"0"+myDate.getMinutes():myDate.getMinutes();
		var second = myDate.getSeconds()<10?"0"+myDate.getSeconds():myDate.getSeconds();
		var nowDate = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
		$(".orgShootForm #nameZH").val(nowDate);
		$(".orgShootForm #nameEN").val(nowDate);
		$(".orgShootForm #description").val("职位架构图快照 - position organization chart shoot");
		popupOrgShootSave();
	};
	
	// 查看架构图快照
	function listHistoryOrgShoot(){
		$.post("shootAction.do?proc=list_OrgShoot&pageType=position",{},function(data){
			var array = data;
			var html = "";
			for(var i = 0 ; i<array.length;i++){
				var obj = array[i];
				var name = obj.name;
				var id = obj.id;
				var description = obj.description;
				var createDate = obj.createDate;
				html += "<li style=\"width:50%;float:left;margin-top:10px;\">";
				//html += "<a href='#'  target='_blank' onclick='link(\"shootAction.do?proc=to_orgShoot_detail&pageType=position&id="+id+"\")' title='"+description+"' style='cursor: pointer;text-decoration: none'>"+name+"</a>";
				html += "<a href='#' onclick=\"addTabMenu("+id+",'"+name+"','"+description+"','"+createDate+"');\"  style='cursor: pointer;text-decoration: none'>"+name+"</a>";
				html += "</li>";
			}
			$("#list_orgShoot_content").html(html);
			popupOrgShootList();
		},'json');
	};
	
	function addTabMenu(id,name,description,createDate){
		// 判断当前是否已存在tab
		if($(".li_"+id).length>0){
			$(".li_"+id).parent().click();
			$('#listOrgShootModalId').addClass('hide');
			$('#shield').hide();
			return;
		}
		var curTabLength = $(".tabMenu li[id^='tabMenu']").length;
		var curTabIndex = curTabLength + 1;
		$.post("shootAction.do?proc=to_orgShoot_detail&pageType=position&id="+id,{},function(data){
			var selHtml = "<select class='orgShootChartSel' id='orgChartSel"+curTabIndex+"'>";
			for(var i = 0 ; i<data.length;i++){
				var positionObj = data[i];
				selHtml = selHtml + "<option value='" + positionObj.positionId + "'>" + positionObj.title + "</option>";
			}
			selHtml = selHtml + "</select>";
			//selHtml = selHtml + "<label style='font-size: 13px; font-family: 黑体, Calibri;'>(<font color='#086912'>“绿色字体”</font>表示编制未满，<font color='#782918'>“红色字体”</font>表示超出编制，<span class='deleted'>“红色背景”</span>表示职位已停用）)</label>";
			selHtml = selHtml + "<input type='hidden' value='"+id+"'>";
			selHtml = selHtml + "<input type='hidden' value='"+curTabIndex+"' id='tabIndex'>";
			selHtml = selHtml + "<input type='hidden' value='"+createDate+"' >";
			$(".tabMenu ul").append("<li id='tabMenu"+curTabIndex+"' onClick='changeTab("+curTabIndex+",99)'>"+name+" "+"<image class='tabMenu_history' src='images/icons/history.png' title='"+description+"'/><image class='tabMenu_disable' style='display:none;' src='images/disable.png' onclick=\"removeTab(this.event,'"+curTabIndex+"')\"/><input type='hidden' class='li_"+id+"' value='"+id+"' /></li>");
			$(".tabContent").append("<div id='tabContent"+curTabIndex+"' class='kantab' style='display:none'>"+selHtml+"<div id='showOrgChartDiv"+curTabIndex+"' align='center'></div></div>");
			$("#orgChartSel"+curTabIndex).change();
			$("#tabMenu"+curTabIndex).click();
			$('#listOrgShootModalId').addClass('hide');
			$('#shield').hide();
		},"json");
	}
	
	 function removeTab(e,curTabIndex){
		stopBubble(e);
		if($("#tabMenu"+curTabIndex).hasClass("hover")){
			$("#tabMenu1").click();
		}
		$("#tabMenu"+curTabIndex).remove();
		$("#tabContent"+curTabIndex).remove();
	 };
	
	 function stopBubble(e){  
	        // 如果传入了事件对象，那么就是非ie浏览器  
	        if(e&&e.stopPropagation){  
	            //因此它支持W3C的stopPropagation()方法  
	            e.stopPropagation();  
	        }else{  
	            //否则我们使用ie的方法来取消事件冒泡  
	            window.event.cancelBubble = true;  
	        }  
	 };  
	 
	 function exportExcel(){
		 $("ul li[id^='tabMenu']").each(function(){
				if($(this).hasClass("hover")){
					var curId = $(this).attr("id");
					if(curId=='tabMenu1'){
						// 如果是当前的部门架构
						 link("positionAction.do?proc=exportExcel&rootPositionId="+$("#orgChartSel").val());
					}else{
						// 否则是历史部门架构
						var shootId = $(this).find("input").val();
						var curIndex = curId.replace(/tabMenu/, "");
						var shootPositionId = $("#orgChartSel"+curIndex).val();
						link("shootAction.do?proc=exportExcel&pageType=position&shootId="+shootId+"&id="+shootPositionId);
					}
				}
			});
	 };
	 
	// 树节点展开与收缩
	 function btnShowAllTreeClick(dom) {
	 	var $liList = $('#position_info li[id^="_N"]');
	 	$liList.each( function(){
	 		var thisLiId = $(this).attr('id');
	 		if(dom.value=='<bean:message bundle="public" key="button.display.all" />'){
	 			$(this).show();
	 			$('#IMG' + thisLiId).attr('src', 'images/minus.gif');
	 		}else{
	 			if(!$(this).hasClass('firstlevel') && !$(this).hasClass('secondlevel')){
		 			$(this).hide();
		 			$('#IMG' + thisLiId).attr('src', 'images/minus.gif');
	 			}else{
	 				if( !$(this).hasClass('independent') )
	 					$('#IMG' + thisLiId).attr('src', 'images/plus.gif');
	 			}
	 		}
	 	});
	 	if(dom.value=='<bean:message bundle="public" key="button.display.all" />'){
	 		$('#btnShowAllTree').val('<bean:message bundle="public" key="button.hide.branch" />');
	 	}else{
	 		$('#btnShowAllTree').val('<bean:message bundle="public" key="button.display.all" />');
	 	}
	 };
</script>