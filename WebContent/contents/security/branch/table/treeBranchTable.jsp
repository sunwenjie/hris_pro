<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@page import="com.kan.base.domain.security.BranchDTO"%>
<%@page import="java.util.List"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.renders.security.BranchRender"%>
	<link rel="stylesheet" href="css/jquery.jOrgChart.css" />
	<link rel="stylesheet" href="css/custom.css" />
	<link href="css/prettify.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="css/jquery.jOrgChart.css" />
	<link rel="stylesheet" href="css/custom.css" />
	<link href="css/prettify.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="js/prettify.js" ></script>
    <script type="text/javascript" src="js/jquery.jOrgChart.js"></script>
	<script type="text/javascript" src="js/prettify.js"></script>
	<style>
		.option-opera-hide{
			display: none;
		}
		.option-opera-show {
			display: block;
		}
		.option-opera {
			position: absolute;
			margin-left:0px;
			padding: 10px;
			background: #caced6;
			z-index: 100;
			font-size: 13px;
			font-family: 黑体, Calibri;
			-webkit-border-radius: 3px;
			-moz-border-radius: 3px;
			border-radius: 3px;
		}
		.option-opera ul {
			borde: 0;
			padding: 0;
			margin: 0;
		}
		.option-opera a img {
			margin-left: 10px;
			margin-right: 10px;
		}
	</style>
	
	<%
		final List< BranchDTO > branchDTOs = (List< BranchDTO >)request.getAttribute("branchDTOs"); 
		if(branchDTOs==null || (branchDTOs!=null && branchDTOs.size()==0)){
		   out.print("<label><a class=\"errorData\" href=\"#\" onclick=\"link('branchAction.do?proc=list_object');\">数据不对？刷新试试</a></label>");
		}
		String shootId = "";
		if(KANUtil.filterEmpty(request.getAttribute("isShoot"))!=null){
		   shootId = (String)request.getAttribute("shootId");
		}
	%>
	<br/>
	<label class="showOrgChartLabel<%=shootId %>"></label>
	<script>
         jQuery(document).ready(function() {
        	 <%
	     		for(int i = 0;i<branchDTOs.size();i++){
	     	 %>
	     		  $("<%="#org_"+shootId+"_"+i%>").jOrgChart({
	                  chartElement: '<%="#chart_"+shootId+"_"+i%>',
	                  dragAndDrop: false
	              });
	     	 <%
	     		}
     		%>
            
         });
         
         $(document).ready(function() {
             
             $("#show-list").click(function(e) {
                 e.preventDefault();

                 $('#list-html').toggle('fast',
                 function() {
                     if ($(this).is(':visible')) {
                         $('#show-list').text('Hide underlying list.');
                         $(".topbar").fadeTo('fast', 0.9);
                     } else {
                         $('#show-list').text('Show underlying list.');
                         $(".topbar").fadeTo('fast', 1);
                     }
                 });
             });

             $('#list-html').text($('#org').html());

             $("#org").bind("DOMSubtreeModified",
             function() {
                 $('#list-html').text('');

                 $('#list-html').text($('#org').html());

             });
        });
    </script>
    <div id="tableWrappe">
    <%
    	for(int i = 0;i<branchDTOs.size();i++){
    	   final List<BranchDTO> tempBranchDTOs = new ArrayList<BranchDTO>();
    	   tempBranchDTOs.add(branchDTOs.get(i));
    	   %>
	    	   <ul id="<%="org_"+shootId+"_"+i%>" style="display:none">
					<%= BranchRender.getBranchTree( request,tempBranchDTOs,KANUtil.filterEmpty(request.getAttribute("isShoot"))!=null ) %>
				</ul>
    	   <%
    	}
    %>
		
	</div>  
	<%
		for(int i = 0;i<branchDTOs.size();i++){
		   %>
		   	<div id="<%="chart_"+shootId+"_"+i%>" class="orgChart">
    		</div>
		   <%
		}
	%>
    
<script>
	function showHistoryEmployeeNames(shootId,type,id){
		$.post("shootAction.do?proc=getHistoryEmployeeNames",{"shootId":shootId,"type":type,"id":id},function(data){
			var html = "<ol class='auto'>";
			for(var i = 0 ; i <data.length;i++){
				var mappingVO = data[i];
				html+="<li style='width:33%;float:left;margin-top:10px'>";
				html+=mappingVO.mappingValue;
				if(mappingVO.mappingTemp!='1'){
					html+="(代)";
				}
				html+="</li>";
			}
			html+="</ol>";
			$(".historyStaffNameDIV").html(html);
			$("#listHistoryStaffId_label").html("历史部门架构图员工列表(合计:"+data.length+"人,代理人员不参与统计)");
			popupListHistoryStaff();
		},"json");
	};
</script>