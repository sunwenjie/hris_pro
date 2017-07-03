<%@page import="java.net.URLDecoder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>JSON Validator</title>

    <link rel="stylesheet" href="jsonlintpro-master/css/screen.css" type="text/css" media="screen, projection">
    <link href="jsonlintpro-master/css/jquery-linedtextarea.css" type="text/css" rel="stylesheet">
</head>
    <body>
		<input type="hidden" id="reformat" value="1" />
		<input type="hidden" id="compress" value="0" />

		<div id="json-composite-placeholder">

        <div id="results_header" class="hide">
            <h3>
                Results <img title="Loading..." class="reset" alt="Loading" src="jsonlintpro-master/images/loadspinner.gif" id="loadSpinner" name="loadSpinner">
            </h3>
        </div>

        <script type="text/javascript">
	        
            if (typeof JSON === 'undefined') {
                document.write('<sc' + 'ript type="text/javascript" src="jsonlintpro-master/js/utils/json2.js"></sc' + 'ript>');
            }
            
        </script>
        <script src="jsonlintpro-master/js/main.js"></script>
        <script src="jsonlintpro-master/js/jsonlint/jsl.format.js"></script>
        <script src="jsonlintpro-master/js/jsonlint/jsl.parser.js"></script>
        <script src="js/jquery-1.8.2.min.js"></script>
        
		<script type="text/javascript">
		
			$(document).ready(function(){ 
				<%
					int i = 1;
					int count = (Integer)request.getAttribute( "count" );
					String content = (String)request.getAttribute( "content" );
					String preContent = (String)request.getAttribute( "preContent" );
				%>
				var i =1;
			　　$(".JSONValidate").each(function(){
					if(i==1){
						var tmp = decodeURI(decodeURI("<%=content%>")).replace(new RegExp(/(%3A)/g),'\:').replace(new RegExp(/(%2C)/g),',').replace(new RegExp(/(%2B)/g),' ');
						$(this).find(".json_input").text(tmp);
					}
					else if(i==2){
						var tmp = decodeURI(decodeURI("<%=preContent%>")).replace(new RegExp(/(%3A)/g),'\:').replace(new RegExp(/(%2C)/g),',').replace(new RegExp(/(%2B)/g),' ');
						$(this).find(".json_input").text(tmp);
					}
					i++;
			　　});
			
			}); 
			
	    </script>
	</body>
</html>s