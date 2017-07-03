<%@ page pageEncoding="GBK"%>
<%@ page import="net.sf.json.JSONObject"%> 
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
%> 

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="GBK">
<title><tiles:getAsString name="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="baidu-tc-verification" content="29da782ffa1d0074beb8aa1f4c4f66ed" />

<link rel="shortcut icon" href="images/icons/iclick-ico.ico">

<!-- Loading Kanpower Style -->
<link href="css/kanpower.css" rel="stylesheet">

<!-- Loading Tip Tip Style -->
<link href="css/tipTip.css" rel="stylesheet">

<!-- Loading JQuery Auto Complate Style -->
<link href="css/jquery.autocomplete.css" rel="stylesheet">

<link href="css/jquery.jOrgChart.css" rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
    <![endif]-->

<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script src="js/jquery-ui-1.10.0.custom.min.js"></script>
<script src="js/custom_checkbox_and_radio.js"></script>
<script src="js/custom_radio.js"></script>
<script src="js/jquery.tagsinput.js"></script>
<script src="js/bootstrap-tooltip.js"></script>
<script src="js/jquery.placeholder.js"></script>
<script src="js/jquery.clickoutside.js"></script>
<script src="js/jquery.tipTip.minified.js"></script>
<script src="js/ajaxupload.3.6.js"></script>
<script src="js/kan.js"></script>
<script src="js/kan.list.js"></script>
<script src="js/kan.validate.js"></script>
<script src="js/kan.thinking.js"></script>
<script src="js/kan.tree.js"></script>
<script src="js/kan.attachment.js"></script>
<script src="js/FixTable.js"></script>
<script src="plugins/My97DatePicker/WdatePicker.js"></script>
<script src="plugins/fusionCharts/FusionCharts.js"></script>
<script src="plugins/fusionCharts/highcharts.js"></script>
<script src="plugins/fusionCharts/FusionChartsExportComponent.js"></script>
<%-- by siuvan
<script src="https://s.meiqia.com/js/mechat.js?unitid=5291" charset="UTF-8"></script>
<script src="https://s.meiqia.com/js/mechat.js?unitid=5291" charset="UTF-8"></script>
 --%>
<!--[if lt IE 8]>
      <script src="js/icon-font-ie7.js"></script>
      <script src="js/icon-font-ie7-24.js"></script>
    <![endif]-->
<script type="text/javascript">
	window.onresize = function () {
		resize_ol_li();
	};
	
	agreest= function () {
		var language = '<%=request.getLocale().getLanguage()%>';
		var msg = '<bean:message bundle="public" key="popup.confirm.back.to.list" />';
		var subAction = $(".subAction").val();
		if(subAction == 'createObject' || subAction == 'modifyObject'){
			if(language != 'en'){
				msg = msg.replace('XX',$('.subAction').val() == 'createObject'?'新增':'修改');
			}
			return confirm(msg);
		}
		return true;;
	};
</script>
</head>

<%
	final JSONObject jsonObject = BaseAction.getUserFromClient(request, response);
		
	if( jsonObject == null )
	{
		if( request.getAttribute("username") == null )
		{
%>
			<script type="text/javascript">
				window.location.href = 'logon.do'; 
			</script>
<%
		}
	}
	else
	{
	   	request.setAttribute("accountId", (String) jsonObject.get("accountId"));
	   	request.setAttribute("userId", (String) jsonObject.get("userId"));
	   	request.setAttribute("staffId", (String) jsonObject.get("staffId"));
	   	request.setAttribute("username", (String) jsonObject.get("username"));
	   	request.setAttribute("entityName", (String) jsonObject.get("entityName"));
	   	request.setAttribute("userToken", (String) jsonObject.get("userToken"));
	   	request.setAttribute("clientId", (String) jsonObject.get("clientId"));
	   	request.setAttribute("clientName", (String) jsonObject.get("clientName"));
	   	request.setAttribute("corpId", (String) jsonObject.get("corpId"));
	   	request.setAttribute("role", (String) jsonObject.get("role"));
	   	
	   	final String positionId = (String) jsonObject.get("positionId");
	   	
	   	if(positionId != null && !positionId.isEmpty()){
	   		request.setAttribute("positionName", (String) jsonObject.get("positionName"));
		   	request.setAttribute("positionId", (String) jsonObject.get("positionId"));
	   	}
	}
%>

<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0">
	<div id="shield" style="position: fixed; left: 0px; top: 0px; display: none; z-index: 9998; opacity: 0.8; background: #7D7159; width: 100%; height: 100%;"><img src="images/loading_s.gif" style="position: absolute; top: 300px; left: 48%;" /></div>
	<div id="wrapper">
		<%
			String pageStyle = KANConstants.getKANAccountConstants( BaseAction.getAccountId(request, response) ).OPTIONS_PAGE_STYLE;
			
			// 设置默认Page Style
			if( KANUtil.filterEmpty( pageStyle ) == null ){
			   pageStyle = "2";
			}
		
			// 设置Page Style至Attribute
			request.setAttribute("pageStyle", pageStyle);
		
			if( ( (String) request.getAttribute("pageStyle") ).equals("1") )
			{
		%>
			<tiles:insert attribute="header" />
			<tiles:insert attribute="menu" />
		<%
			} else if ( ( (String) request.getAttribute("pageStyle") ).equals("2") )
			{
		%>
			<tiles:insert attribute="menu" />
			<tiles:insert attribute="header" />
		<%
			}
		%>
		<tiles:insert attribute="body" />
		<tiles:insert attribute="footer" />
	</div>	

	<script type="text/javascript">
		(function($) {
			if(window.screen.availHeight >= document.body.scrollHeight){
				$('#shield').height(window.screen.availHeight);
			}else{
				$('#shield').height(document.body.scrollHeight);
			}
			$('#shield img').hide();
			
			<%
				if(( (String) request.getAttribute("pageStyle") ).equals("2")){
			%>
				$("#content").css("padding-top", "0px");
				$(".box").css("margin", "0px 20px 20px 20px");
			<%
				}
			%>

			/* Enabling tooltips */
			$(".tiptip").tipTip();
			//panelTrigger option
			$(".sys_option").click(function() {
				$('.panelContainer_positions').stop(true, true).slideUp('fast');
				$('.panelContainer_connectCorpUserIds').stop(true, true).slideUp('fast');
				$('.panelContainer').slideToggle("fast");
				return false;
			});

			$(".position").click(function() {
				$('.panelContainer').stop(true, true).slideUp('fast');
				$('.panelContainer_connectCorpUserIds').stop(true, true).slideUp('fast');
				$('.panelContainer_positions').slideToggle("fast");
				return false;
			});
			
			$(".connectCorpUserId").click(function() {
				$('.panelContainer').stop(true, true).slideUp('fast');
				$('.panelContainer_positions').stop(true, true).slideUp('fast');
				$('.panelContainer_connectCorpUserIds').slideToggle("fast");
				return false;
			});
			
			$('.panelTrigger').outside('click', function() {
				$('.panelContainer').stop(true, true).slideUp('fast');
				$('.panelContainer_positions').stop(true, true).slideUp('fast');
				$('.panelContainer_connectCorpUserIds').stop(true, true).slideUp('fast');
				
			});
			
			/* 
			 * Button hovering effects 
			 * Note: we are not using pure css using :hover because :hover applies to even disabled elements.
			 * The pseudo class :enabled is not supported in IE < 9.
			 */
			$(document).on({
				mouseenter : function() {
					$(this).addClass('hover');
				},
				mouseleave : function() {
					$(this).removeClass('hover');
				}

			}, 'input[type=button], input[type=submit], input[type=reset]');

			/* Fading out main messages */
			$(document).on({
				click : function() {
					$(this).parent('div.message').fadeOut("slow");
				}
			}, '.message a.messageCloseButton');

			/* Toggling search form: Begins */
			$(".toggableForm .toggle").click(function() {
				$(".toggableForm .inner").slideToggle('slow', function() {
					if ($(this).is(':hidden')) {
						$('.toggableForm .tiptip').tipTip({
							content : '<bean:message key="public.openoptions"/>'
						});
					} else {
						$('.toggableForm .tiptip').tipTip({
							content : '<bean:message key="public.hideoptions"/>'
						});
					}
				});
				$(this).toggleClass("activated");
			});
			/* Toggling search form: Ends */
			
			$('#filterRecords').click( function () {
				if(!$('#searchDiv').is(':hidden')){
					$('#filterRecords').html('<bean:message bundle="public" key="set.filerts" />');
				}else{
					$('#filterRecords').html('<bean:message bundle="public" key="close.filerts" />');
				}
				$('.tiptip').trigger("click");
			});
			
			resize_ol_li();
			
			<logic:present name="MESSAGE">
				messageWrapperFada();
			</logic:present>
			
			<logic:present name="SUCCESS_MESSAGE">
				messageWrapperFada();
			</logic:present>
			
			<logic:present name="SUCCESS_MESSAGE_HEADER">
				messageWrapperFada();
			</logic:present>
		
			<logic:present name="SUCCESS_MESSAGE_DETAIL">
				messageWrapperFada();
			</logic:present>
			
			<logic:present name="USER_DEFINE_MESSAGE">
				messageWrapperFada();
			</logic:present>
		})(jQuery);
		
		function bindEvent(){
			$('div.menu>ul>li, div.menu_s>ul>li, div.menu_s_s>ul>li').unbind();
			$('.kanMenuDiv').unbind();
			
			/* 点击一级菜单，则二级菜单会显示 */
			$('div.menu>ul>li, div.menu_s>ul>li, div.menu_s_s>ul>li').click(function() {
				/* 显示二级菜单 */
				$('div.menu>ul>li>ul, div.menu_s>ul>li>ul, div.menu_s_s>ul>li>ul').hide();
				$(this).children('ul').show();
				/* 选中一级菜单 */
				$('div.menu>ul>li, div.menu_s>ul>li, div.menu_s_s>ul>li').removeClass('current');
				$(this).addClass('current');
			});
			
			/* 鼠标悬停一级菜单，则二级菜单会显示 */
			$('div.menu>ul>li, div.menu_s>ul>li').mouseover(function() {
				/* 显示二级菜单 */
				$('div.menu>ul>li>ul, div.menu_s>ul>li>ul').hide();
				$(this).children('ul').show();
			});
			
			<%
				if(( (String) request.getAttribute("pageStyle") ).equals("1")){
			%>
				/* 鼠标悬停二级菜单之外，则二级菜单隐藏 ，当前选中的二级菜单显示 */
				$('div.menu>ul>li').mouseout(function() {
					$('div.menu>ul>li>ul').hide();
					$('div.menu>ul>li.current>ul').show();
				});
			<%
				}else if(( (String) request.getAttribute("pageStyle") ).equals("2")){
			%>
				/* 鼠标悬停二级菜单之外，则二级菜单隐藏 */
				$('div.menu_s>ul>li').mouseout(function() {
					$('div.menu_s>ul>li>ul').hide();
				});
				
				$('.menu_s_s').outside('click', function() {
					$('.menu_s_s .menuSSUl').stop(true, true).slideUp('fast');
				});
			<%
				}
			%>
		};
	</script>
</body>
</html>

