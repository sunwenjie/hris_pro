<%@page import="java.net.URLDecoder"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@page import="com.kan.base.domain.security.PositionDTO"%>
<%@page import="java.util.List"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.renders.security.PositionRender"%>

<div id="content">
	<div class="box toggableForm" id="position-information">
		<div class="head">
			<label id="pageTitle">职位快照   shoot at <bean:write name="orgShootVO" property="decodeCreateDate"/></label>
		</div>
	</div>
	<!-- Position-information -->
	<div class="box noHeader" id="search-results">
		<!-- inner -->
		<div class="inner">
			<div class="top">
					<input type="button" class="" id="btnExport" name="btnExport" value="导出PDF" onclick="exportPdf();" />
					<input type="hidden" class="" id="isShoot" name="isShoot" value="true" />
			</div>
			<!-- top -->
			<div id="scrollWrapper">
				<div id="scrollContainer"></div>
			</div>
			<%=PositionRender.getSelectHtml(request)%>
			<div id="showOrgChartDiv" align="center">
				
			</div>
			<!-- List Component -->
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->
</div>

<script type="text/javascript">
	(function($) {
		$('#menu_security_Modules').addClass('current');			
		$('#menu_security_PositionManagement').addClass('selected');
		$('#menu_security_Position').addClass('selected');
		
		kanList_init();
		kanCheckbox_init();
		
		$("#orgChartSel").change(function(){
			var positionId = encodeURI(encodeURI($(this).val()));
			var shootId = <%=request.getAttribute("shootId")%>
			loadHtml("#showOrgChartDiv", "shootAction.do?proc=getOrgShootChart&pageType=position&shootId="+shootId+"&positionId="+positionId,false,"$('.showOrgChartLabel').text('"+$('#orgChartSel').find('option:selected').text()+"')");
		});
		$("#orgChartSel").change();
	})(jQuery);
	
(function($) {
		
		$(".node").live("mouseover",function(){
			$(this).css("border-color","#9d9be1");
		});
		
		$(".node").live("mouseout",function(){
			$(this).css("border-color","#ffffff");
		});
		
		jQuery.fn.justtext = function() {
		    return $(this).clone().children().remove().end().text();
		};
		
	})(jQuery);
	
	function exportPdf(){
		var data="[";
		$(".node").each(function(){
			var vis = $(this).parent().parent().css("visibility");
			if(vis != 'hidden'){
				var left = $(this).offset().left;
				var top = $(this).offset().top;
				var width = $(this).width();
				var height = $(this).height();
				var positionId = $(this).find("[name=hiddenPositionId]").val();
				var parentId = $(this).find("[name=hiddenParentPositionId]").val();
				var positionNameZH = $(this).children('div').children('a').justtext();
				var positionNameEN = $(this).children('div').children('a').children('p').text();
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
		var titleText =	$('.showOrgChartLabel').text();
		var titleLeft = $('.showOrgChartLabel').offset().left;
		var titleTop = $('.showOrgChartLabel').offset().top;
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
	}
	
</script>