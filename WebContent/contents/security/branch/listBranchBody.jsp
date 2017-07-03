<%@ page import="com.kan.base.web.renders.security.BranchRender"%>
<%@ page import="com.kan.base.web.actions.security.BranchAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div class="box searchForm toggableForm" id="branch-information">
	<div class="head">
		<label><bean:message bundle="security" key="security.branch.search.title" /></label>
	</div>
</div>
<!-- Branch-information -->
<div class="box noHeader" id="search-results">
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
			<kan:auth right="new" action="<%=BranchAction.accessAction%>">
				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('branchAction.do?proc=to_objectNew&rootBranchId='+$('#rootBranchId').val());" />
			</kan:auth>
			<!-- 如果为2：列表显示，1：法务实体架构图，3：部门架构图 -->
			<logic:notEqual value="2" name="branchPrefer">
				<kan:auth right="export" action="<%=BranchAction.accessAction%>">
					<input type="button" class="" id="btnExport" name="btnExport" value="<bean:message bundle="public" key="button.export.orgpdf" />" onclick="exportPdf();" />
<%-- 					<input type="button" class="" id="btnExcel" name="btnExcel" value="<bean:message bundle="public" key="button.export.excel" />" onclick="exportExcel();" /> --%>
				</kan:auth>
				<!-- <input type="button" class="save" id="btnCopy" name="btnCopy" value="Copy" onclick="show_copyBranchO_ChartPage();" /> -->
				<img title="<bean:message bundle="security" key="security.branch.view.history.organization.chart" />" src="images/icons/save.png" onclick="saveOrgShoot();" style="float: right;cursor: pointer;">
				<img title="<bean:message bundle="security" key="security.branch.save.current.organization.chart" />" src="images/icons/save_history.png" onclick="listHistoryOrgShoot();" style="float: right;margin-right: 10px;cursor: pointer;">
			</logic:notEqual>
			<logic:equal value="2" name="branchPrefer">
				<kan:auth right="delete" action="<%=BranchAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listbranch_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</logic:equal>
		</div>
		<!-- top -->
		<html:form action="branchAction.do?proc=list_object" styleClass="listbranch_form">
			<input type="hidden" name="branchId" id="branchId" value="" /> 
			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="branchHolder" property="sortColumn" />" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="branchHolder" property="sortOrder" />" />
			<input type="hidden" name="page" id="page" value="<bean:write name="branchHolder" property="page" />" />
			<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="branchHolder" property="selectedIds" />" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<input type="hidden" name="rootBranchId" id="rootBranchId" value="" /> 
			<input type="hidden" name="pageType" id="pageType" value="branch" /> 
		</html:form>
		<div id="tableWrapper">
			<!-- Include table jsp 包含tabel对应的jsp文件  -->
			<logic:equal value="2" name="branchPrefer">
				<jsp:include page="/contents/security/branch/table/listBranchTable.jsp" flush="true"/>
			</logic:equal>
			<logic:notEqual value="2" name="branchPrefer">
			<!-- 组织架构图 -->
			<div id="tab">
				<div class="tabMenu"> 
					<ul>
						<li id="tabMenu1" onClick="changeTab(1,99)" class="first hover"><bean:message bundle="security" key="security.branch.current.chart" /></li>
					</ul>
				</div>  
				<div class="tabContent"> 
					<div id="tabContent1" class="kantab">
						<%=BranchRender.getSelectHtml(request)%>
						<div id="showOrgChartDiv" align="center">
					
						</div>
					</div>
				</div>
			</div>
			</logic:notEqual>
		</div>
		<!-- tableWrapper -->
		<div class="bottom">
			<p>
		</div>
		<!-- List Component -->
	</div>
	<!-- inner -->
</div>
<!-- search-results -->
<div id="popupWrapper">
	<div id="copyBranchOChart">
		<jsp:include page="/popup/copyBranchO_Chart.jsp"></jsp:include>
	</div>
	<jsp:include page="/popup/saveOrgShoot.jsp"></jsp:include>
	<jsp:include page="/popup/listOrgShoots.jsp"></jsp:include>
	<jsp:include page="/popup/listHistoryStaff.jsp"></jsp:include>
</div>	

<script type="text/javascript">
	(function($) {
		$('#menu_security_Modules').addClass('current');			
		$('#menu_security_OrgManagement').addClass('selected');
		$('#menu_security_Branch').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
		
		$("#orgChartSel").change(function(){
			var rootBranchId = encodeURI(encodeURI($(this).val()));
			$("#rootBranchId").val(rootBranchId);
			loadHtml("#showOrgChartDiv", "branchAction.do?proc=getOrgChart&branchId="+rootBranchId,false,"$('.showOrgChartLabel').text('"+$('#orgChartSel').find('option:selected').text()+"')");
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
			var rootBranchId = encodeURI(encodeURI($(this).val()));
			var shootId = $(this).next("input").val();
			var tabIndex = $(this).next("input").next("input").val();
			var createDate = $(this).next("input").next("input").next("input").val();
			loadHtml("#showOrgChartDiv"+tabIndex, "shootAction.do?proc=getOrgShootChart&pageType=branch&shootId="+shootId+"&branchId="+rootBranchId,false,"$('.showOrgChartLabel"+shootId+"').text('"+$(this).find('option:selected').text()+"（shot at "+createDate+"）')");
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

	function resetForm(){
		$('.searchbranch_branchCode').val('');
		$('.searchbranch_nameZH').val('');
		$('.searchbranch_nameEN').val('');
		$('.searchbranch_status').val('0');
	};
	
	function deleteBranch(even){
		$(".listbranch_form #selectedIds").val(even);
		if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')){
			var reloadTarget = "tableWrapper";
			if(1 == "<bean:write name='branchPrefer'/>"){
				reloadTarget = "showOrgChartDiv";
			}
			submitForm('listbranch_form', 'deleteObject', null, null, null, reloadTarget);
		}
	};
	
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
				var branchId = $(this).find("[name=hiddenBranchId]").val();
				var parentId = $(this).find("[name=hiddenParentBranchId]").val();
				var branchNameZH = $(this).children('div').children('a').justtext().replace(/\u00a0/g, "");//去除&nbsp;已经被text转义了，只能这么写
				var branchNameEN = $(this).children('div').children('a').children('p').text().replace(/\u00a0/g, "");
				data = data+"{";
				data = data+"\"left\":"+left+",";
				data = data+"\"top\":"+top+",";
				data = data+"\"width\":"+width+",";
				data = data+"\"height\":"+height+",";
				data = data+"\"branchId\":\""+branchId+"\",";
				data = data+"\"parentId\":\""+parentId+"\",";
				data = data+"\"branchNameZH\":\""+branchNameZH+"\",";
				data = data+"\"branchNameEN\":\""+branchNameEN+"\"";
				data = data+"},";
			}
		});
		data = data.substring(0,data.length-1);
		data = data+"]";
		data = encodeURI(encodeURI(data,"utf-8"),"utf-8");
		//$.post("branchAction.do?proc=exportPdf",{'data':data},function(){},"text");
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
		form.attr("action","branchAction.do?proc=exportPdf&maxWidth="+maxWidth+"&maxHeight="+maxHeight);
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
		$(".orgShootForm #description").val("部门架构图快照 - branch organization chart shoot");
		popupOrgShootSave();
	};
	
	// 查看架构图快照
	function listHistoryOrgShoot(){
		$.post("shootAction.do?proc=list_OrgShoot&pageType=branch",{},function(data){
			var array = data;
			var html = "";
			for(var i = 0 ; i<array.length;i++){
				var obj = array[i];
				var name = obj.name;
				var id = obj.id;
				var description = obj.description;
				var createDate = obj.createDate;
				html += "<li style=\"width:50%;float:left;margin-top:10px;\">";
				//html += "<a href='#'  target='_blank' onclick='link(\"shootAction.do?proc=to_orgShoot_detail&pageType=branch&id="+id+"\")' title='"+description+"' style='cursor: pointer;text-decoration: none'>"+name+"</a>";
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
		$.post("shootAction.do?proc=to_orgShoot_detail&pageType=branch&id="+id,{},function(data){
			var selHtml = "<select class='orgShootChartSel' id='orgChartSel"+curTabIndex+"'>";
			for(var i = 0 ; i<data.length;i++){
				var branchObj = data[i];
				selHtml = selHtml + "<option value='" + branchObj.branchId + "'>" + branchObj.decodeBranchName + "</option>";
			}
			selHtml = selHtml + "</select>";
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
	};
	
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
					link("branchAction.do?proc=exportExcel&rootBranchId="+$("#orgChartSel").val());
				}else{
					// 否则是历史部门架构
					var shootId = $(this).find("input").val();
					var curIndex = curId.replace(/tabMenu/, "");
					var shootBranchId = $("#orgChartSel"+curIndex).val();
					link("shootAction.do?proc=exportExcel&pageType=branch&shootId="+shootId+"&id="+shootBranchId);
				}
			}
		});
	 };
	 
	 // 显示复制页面
	 function show_copyBranchO_ChartPage(){
		 if($('#orgChartSel').val() != ''){
			 var callback = "$('#copyBranchOCModalId').removeClass('hide');$('#shield').show();";
	    	 loadHtmlWithRecall('#copyBranchOChart', 'branchAction.do?proc=show_copyBranchO_ChartPage_ajax&rootId=' + $('#orgChartSel').val(), false, callback );
		 }else{
			 validate("orgChartSel", true, "common", 0, 0);
		 }
	 };
</script>