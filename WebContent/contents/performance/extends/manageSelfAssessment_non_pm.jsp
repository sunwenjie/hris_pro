<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.kan.hro.domain.biz.performance.SelfAssessmentVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	SelfAssessmentVO sa = ( SelfAssessmentVO )request.getAttribute( "selfAssessmentForm" );
	if( sa != null && sa.getPm1Content() != null )
	{
	    if( sa.getPm1Content() != null )
	    {
	       JSONObject jo = JSONObject.fromObject( sa.getPm1Content() );
	       if(jo!=null){
	 	   request.setAttribute( "map1", jo );
	       }
	    }
	    if( sa.getPm2Content() != null )
	    {
	       JSONObject jo = JSONObject.fromObject( sa.getPm2Content() );
	       if(jo!=null){
	 		request.setAttribute( "map2", jo );
	       }
	    }
	    if( sa.getPm3Content() != null )
	    {
	       JSONObject jo = JSONObject.fromObject( sa.getPm3Content() );
	       if(jo!=null){
	 		request.setAttribute( "map3", jo );
	       }
	    }
	    if( sa.getPm4Content() != null )
	    {
	       JSONObject jo = JSONObject.fromObject( sa.getPm4Content() );
	       if(jo!=null){
	 		request.setAttribute( "map4", jo );
	       }
	    }
	}

%>

<logic:notEmpty name="selfAssessmentForm" property="pm1PositionId">
<%-----------------¢Ù People Manager Start----------------%>	
							<div id="tabContent7" class="kantab hide">
								<ol>
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.accomplishments" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd4" /></dd>
									</dl>
									<li>
										<textarea name="accomplishments_pm1" class="accomplishments_pm1" id="accomplishments_pm1">${map1.accomplishments_pm1}</textarea>
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd3" /></dd>
									</dl>
									<li>
										<textarea name="areasOfStrengths_pm1" class="areasOfStrengths_pm1" id="areasOfStrengths_pm1">${map1.areasOfStrengths_pm1}</textarea>
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd4" /></dd>
									</dl>
									<li>
										<textarea name="areasOfImprovement_pm1" class="areasOfImprovement_pm1" id="areasOfImprovement_pm1">${map1.areasOfImprovement_pm1}</textarea>
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm" />
									</h2>
									<li class="radio_li">
										<label><input type="radio" name="isPromotion_pm1" class="isPromotion_pm1"  id="isPromotion_pm1" value="1" <logic:notEmpty name="map1"><logic:equal name="map1" property="isPromotion_pm1" value="1">checked="checked"</logic:equal></logic:notEmpty>  /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option1" /></label>
										<label><input type="radio" name="isPromotion_pm1" class="isPromotion_pm1"  id="isPromotion_pm1" value="2" <logic:notEmpty name="map1"><logic:equal name="map1" property="isPromotion_pm1" value="2">checked="checked"</logic:equal></logic:notEmpty>  /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option2" /></label>
										<label><input type="radio" name="isPromotion_pm1" class="isPromotion_pm1"  id="isPromotion_pm1" value="3" <logic:notEmpty name="map1"><logic:equal name="map1" property="isPromotion_pm1" value="3">checked="checked"</logic:equal></logic:notEmpty>  /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option3" /></label>
									</li>
									<li>
										<label><bean:message bundle="performance" key="emp.self.assessment.promotionReason_pm" /></label>
										<textarea name="promotionReason_pm1" class="promotionReason_pm1" id="promotionReason_pm1">${map1.promotionReason_pm1}</textarea>
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.successors_pm" />
										<img src="images/tips.png" title="<bean:message bundle="performance" key="emp.self.assessment.successors_pm.tips" />" />
									</h2>
									<li>
										<input type="text" name="successors_pm1" class="successors_pm1" id="successors_pm1" value="${map1.successors_pm1}" />
									</li>
									
									<dl class="special_DL">
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.rating" /></h2></a></dt>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating5" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark4" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark5" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating4" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark4" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark5" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating3" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark4" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating2" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark3" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating1" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark3" /></dd>
											</dl>
										</dd>
									</dl>
									<table>
										<thead>
											<tr>
												<th></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.one" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.two" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.three" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.four" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.five" /></th>
											</tr>
										</thead>
										<tbody>
											<tr id="rating_pm1_row" class="even">
												<td>Rating</td>
												<td>
													<label><input type="radio" name="rating_pm1" class="rating_pm1" value="5" <logic:notEmpty name="map1"><logic:equal name="map1" property="rating_pm1" value="5">checked="checked"</logic:equal></logic:notEmpty> >5</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm1" class="rating_pm1" value="4.5" <logic:notEmpty name="map1"><logic:equal name="map1" property="rating_pm1" value="4.5">checked="checked"</logic:equal></logic:notEmpty> >4.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><input type="radio" name="rating_pm1" class="rating_pm1" value="4" <logic:notEmpty name="map1"><logic:equal name="map1" property="rating_pm1" value="4">checked="checked"</logic:equal></logic:notEmpty> >5</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm1" class="rating_pm1" value="3.5" <logic:notEmpty name="map1"><logic:equal name="map1" property="rating_pm1" value="3.5">checked="checked"</logic:equal></logic:notEmpty> >3.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><input type="radio" name="rating_pm1" class="rating_pm1" value="3" <logic:notEmpty name="map1"><logic:equal name="map1" property="rating_pm1" value="3">checked="checked"</logic:equal></logic:notEmpty> >3</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm1" class="rating_pm1" value="2.5" <logic:notEmpty name="map1"><logic:equal name="map1" property="rating_pm1" value="2.5">checked="checked"</logic:equal> </logic:notEmpty>>2.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><input type="radio" name="rating_pm1" class="rating_pm1" value="2" <logic:notEmpty name="map1"><logic:equal name="map1" property="rating_pm1" value="2">checked="checked"</logic:equal> </logic:notEmpty>>2</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm1" class="rating_pm1" value="1" <logic:notEmpty name="map1"><logic:equal name="map1" property="rating_pm1" value="1">checked="checked"</logic:equal> </logic:notEmpty>>1</label>
												</td>
											</tr>
										</tbody>
									</table>
								</ol>
							</div>
<%-----------------¢Ù People Manager end----------------%>
</logic:notEmpty>




<logic:notEmpty name="selfAssessmentForm" property="pm2PositionId">
<%-----------------¢Ú People Manager Start----------------%>	
							<div id="tabContent8" class="kantab hide">
								<ol>
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.accomplishments" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd4" /></dd>
									</dl>
									<li>
										<textarea name="accomplishments_pm2" class="accomplishments_pm2" id="accomplishments_pm2">${map2.accomplishments_pm2}</textarea>
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd3" /></dd>
									</dl>
									<li>
										<textarea name="areasOfStrengths_pm2" class="areasOfStrengths_pm2" id="areasOfStrengths_pm2">${map2.areasOfStrengths_pm2}</textarea>
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd4" /></dd>
									</dl>
									<li>
										<textarea name="areasOfImprovement_pm2" class="areasOfImprovement_pm2" id="areasOfImprovement_pm2">${map2.areasOfImprovement_pm2}</textarea>
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm" />
									</h2>
									<li class="radio_li">
										<label><input type="radio" name="isPromotion_pm2" class="isPromotion_pm2"  id="isPromotion_pm2" value="1" <logic:notEmpty name="map2"><logic:equal name="map2" property="isPromotion_pm2" value="1">checked="checked"</logic:equal></logic:notEmpty>  /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option1" /></label>
										<label><input type="radio" name="isPromotion_pm2" class="isPromotion_pm2"  id="isPromotion_pm2" value="2" <logic:notEmpty name="map2"><logic:equal name="map2" property="isPromotion_pm2" value="2">checked="checked"</logic:equal></logic:notEmpty>  /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option2" /></label>
										<label><input type="radio" name="isPromotion_pm2" class="isPromotion_pm2"  id="isPromotion_pm2" value="3" <logic:notEmpty name="map2"><logic:equal name="map2" property="isPromotion_pm2" value="3">checked="checked"</logic:equal></logic:notEmpty>  /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option3" /></label>
									</li>
									<li>
										<label><bean:message bundle="performance" key="emp.self.assessment.promotionReason_pm" /></label>
										<textarea name="promotionReason_pm2" class="promotionReason_pm2" id="promotionReason_pm2">${map2.promotionReason_pm2}</textarea>
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.successors_pm" />
										<img src="images/tips.png" title="<bean:message bundle="performance" key="emp.self.assessment.successors_pm.tips" />" />
									</h2>
									<li>
										<input type="text" name="successors_pm2" class="successors_pm2" id="successors_pm2" value="${map2.successors_pm2}" />
									</li>
									
									<dl class="special_DL">
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.rating" /></h2></a></dt>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating5" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark4" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark5" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating4" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark4" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark5" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating3" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark4" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating2" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark3" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating1" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark3" /></dd>
											</dl>
										</dd>
									</dl>
									<table>
										<thead>
											<tr>
												<th></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.one" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.two" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.three" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.four" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.five" /></th>
											</tr>
										</thead>
										<tbody>
											<tr id="rating_pm2_row" class="even">
												<td>Rating</td>
												<td>
													<label><input type="radio" name="rating_pm2" class="rating_pm2" value="5" <logic:notEmpty name="map2"><logic:equal name="map2" property="rating_pm2" value="5">checked="checked"</logic:equal></logic:notEmpty> >5</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm2" class="rating_pm2" value="4.5" <logic:notEmpty name="map2"><logic:equal name="map2" property="rating_pm2" value="4.5">checked="checked"</logic:equal></logic:notEmpty> >4.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><input type="radio" name="rating_pm2" class="rating_pm2" value="4" <logic:notEmpty name="map2"><logic:equal name="map2" property="rating_pm2" value="4">checked="checked"</logic:equal></logic:notEmpty> >5</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm2" class="rating_pm2" value="3.5" <logic:notEmpty name="map2"><logic:equal name="map2" property="rating_pm2" value="3.5">checked="checked"</logic:equal></logic:notEmpty> >3.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><input type="radio" name="rating_pm2" class="rating_pm2" value="3" <logic:notEmpty name="map2"><logic:equal name="map2" property="rating_pm2" value="3">checked="checked"</logic:equal></logic:notEmpty> >3</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm2" class="rating_pm2" value="2.5" <logic:notEmpty name="map2"><logic:equal name="map2" property="rating_pm2" value="2.5">checked="checked"</logic:equal></logic:notEmpty> >2.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><input type="radio" name="rating_pm2" class="rating_pm2" value="2" <logic:notEmpty name="map2"><logic:equal name="map2" property="rating_pm2" value="2">checked="checked"</logic:equal></logic:notEmpty> >2</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm2" class="rating_pm2" value="1" <logic:notEmpty name="map2"><logic:equal name="map2" property="rating_pm2" value="1">checked="checked"</logic:equal></logic:notEmpty> >1</label>
												</td>
											</tr>
										</tbody>
									</table>
								</ol>
							</div>
<%-----------------¢Ú People Manager end----------------%>
</logic:notEmpty>




<logic:notEmpty name="selfAssessmentForm" property="pm3PositionId">
<%-----------------¢Û People Manager Start----------------%>	
							<div id="tabContent9" class="kantab hide">
								<ol>
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.accomplishments" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd4" /></dd>
									</dl>
									<li>
										<textarea name="accomplishments_pm3" class="accomplishments_pm3" id="accomplishments_pm3">${map3.accomplishments_pm3}</textarea>
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd3" /></dd>
									</dl>
									<li>
										<textarea name="areasOfStrengths_pm3" class="areasOfStrengths_pm3" id="areasOfStrengths_pm3">${map3.areasOfStrengths_pm3}</textarea>
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd4" /></dd>
									</dl>
									<li>
										<textarea name="areasOfImprovement_pm3" class="areasOfImprovement_pm3" id="areasOfImprovement_pm3">${map3.areasOfImprovement_pm3}</textarea>
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm" />
									</h2>
									<li class="radio_li">
										<label><input type="radio" name="isPromotion_pm3" class="isPromotion_pm3"  id="isPromotion_pm3" value="1" <logic:notEmpty name="map3"><logic:equal name="map3" property="isPromotion_pm3" value="1">checked="checked"</logic:equal></logic:notEmpty>  /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option1" /></label>
										<label><input type="radio" name="isPromotion_pm3" class="isPromotion_pm3"  id="isPromotion_pm3" value="2" <logic:notEmpty name="map3"><logic:equal name="map3" property="isPromotion_pm3" value="2">checked="checked"</logic:equal></logic:notEmpty>  /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option2" /></label>
										<label><input type="radio" name="isPromotion_pm3" class="isPromotion_pm3"  id="isPromotion_pm3" value="3" <logic:notEmpty name="map3"><logic:equal name="map3" property="isPromotion_pm3" value="3">checked="checked"</logic:equal></logic:notEmpty>  /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option3" /></label>
									</li>
									<li>
										<label><bean:message bundle="performance" key="emp.self.assessment.promotionReason_pm" /></label>
										<textarea name="promotionReason_pm3" class="promotionReason_pm3" id="promotionReason_pm3">${map3.promotionReason_pm3}</textarea>
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.successors_pm" />
										<img src="images/tips.png" title="<bean:message bundle="performance" key="emp.self.assessment.successors_pm.tips" />" />
									</h2>
									<li>
										<input type="text" name="successors_pm3" class="successors_pm3" id="successors_pm3" value="${map3.successors_pm3}" />
									</li>
									
									<dl class="special_DL">
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.rating" /></h2></a></dt>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating5" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark4" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark5" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating4" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark4" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark5" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating3" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark4" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating2" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark3" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating1" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark3" /></dd>
											</dl>
										</dd>
									</dl>
									<table>
										<thead>
											<tr>
												<th></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.one" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.two" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.three" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.four" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.five" /></th>
											</tr>
										</thead>
										<tbody>
											<tr id="rating_pm3_row" class="even">
												<td>Rating</td>
												<td>
													<label><input type="radio" name="rating_pm3" class="rating_pm3" value="5" <logic:notEmpty name="map3"><logic:equal name="map3" property="rating_pm3" value="5">checked="checked"</logic:equal></logic:notEmpty> >5</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm3" class="rating_pm3" value="4.5" <logic:notEmpty name="map3"><logic:equal name="map3" property="rating_pm3" value="4.5">checked="checked"</logic:equal></logic:notEmpty> >4.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><input type="radio" name="rating_pm3" class="rating_pm3" value="4" <logic:notEmpty name="map3"><logic:equal name="map3" property="rating_pm3" value="4">checked="checked"</logic:equal></logic:notEmpty> >5</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm3" class="rating_pm3" value="3.5" <logic:notEmpty name="map3"><logic:equal name="map3" property="rating_pm3" value="3.5">checked="checked"</logic:equal></logic:notEmpty> >3.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><input type="radio" name="rating_pm3" class="rating_pm3" value="3" <logic:notEmpty name="map3"><logic:equal name="map3" property="rating_pm3" value="3">checked="checked"</logic:equal></logic:notEmpty> >3</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm3" class="rating_pm3" value="2.5" <logic:notEmpty name="map3"><logic:equal name="map3" property="rating_pm3" value="2.5">checked="checked"</logic:equal></logic:notEmpty> >2.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><input type="radio" name="rating_pm3" class="rating_pm3" value="2" <logic:notEmpty name="map3"><logic:equal name="map3" property="rating_pm3" value="2">checked="checked"</logic:equal></logic:notEmpty> >2</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm3" class="rating_pm3" value="1" <logic:equal name="map3" property="rating_pm3" value="1">checked="checked"</logic:equal>>1</label>
												</td>
											</tr>
										</tbody>
									</table>
								</ol>
							</div>
<%-----------------¢Û People Manager end----------------%>
</logic:notEmpty>




<logic:notEmpty name="selfAssessmentForm" property="pm4PositionId">
<%-----------------¢Û People Manager Start----------------%>	
							<div id="tabContent10" class="kantab hide">
								<ol>
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.accomplishments" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.accomplishments_pm.dd4" /></dd>
									</dl>
									<li>
										<textarea name="accomplishments_pm4" class="accomplishments_pm4" id="accomplishments_pm4">${map4.accomplishments_pm4}</textarea>
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfStrengths_pm.dd3" /></dd>
									</dl>
									<li>
										<textarea name="areasOfStrengths_pm4" class="areasOfStrengths_pm4" id="areasOfStrengths_pm4">${map4.areasOfStrengths_pm4}</textarea>
									</li>
									
									<dl>
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm" /></h2></a></dt>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd1" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd2" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd3" /></dd>
										<dd><bean:message bundle="performance" key="emp.self.assessment.areasOfImprovement_pm.dd4" /></dd>
									</dl>
									<li>
										<textarea name="areasOfImprovement_pm4" class="areasOfImprovement_pm4" id="areasOfImprovement_pm4">${map4.areasOfImprovement_pm4}</textarea>
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm" />
									</h2>
									<li class="radio_li">
										<label><input type="radio" name="isPromotion_pm4" class="isPromotion_pm4"  id="isPromotion_pm4" value="1" <logic:notEmpty name="map4"><logic:equal name="map4" property="isPromotion_pm4" value="1">checked="checked"</logic:equal></logic:notEmpty>  /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option1" /></label>
										<label><input type="radio" name="isPromotion_pm4" class="isPromotion_pm4"  id="isPromotion_pm4" value="2" <logic:notEmpty name="map4"><logic:equal name="map4" property="isPromotion_pm4" value="2">checked="checked"</logic:equal></logic:notEmpty>  /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option2" /></label>
										<label><input type="radio" name="isPromotion_pm4" class="isPromotion_pm4"  id="isPromotion_pm4" value="3" <logic:notEmpty name="map4"><logic:equal name="map4" property="isPromotion_pm4" value="3">checked="checked"</logic:equal></logic:notEmpty>  /><bean:message bundle="performance" key="emp.self.assessment.isPromotion_pm.option3" /></label>
									</li>
									<li>
										<label><bean:message bundle="performance" key="emp.self.assessment.promotionReason_pm" /></label>
										<textarea name="promotionReason_pm4" class="promotionReason_pm4" id="promotionReason_pm4">${map4.promotionReason_pm3}</textarea>
									</li>
									<h2>
										<bean:message bundle="performance" key="emp.self.assessment.successors_pm" />
										<img src="images/tips.png" title="<bean:message bundle="performance" key="emp.self.assessment.successors_pm.tips" />" />
									</h2>
									<li>
										<input type="text" name="successors_pm4" class="successors_pm4" id="successors_pm4" value="${map4.successors_pm4}" />
									</li>
									
									<dl class="special_DL">
										<dt><a onclick="shrinkDD(this);"><h2><bean:message bundle="performance" key="emp.self.assessment.rating" /></h2></a></dt>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating5" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark4" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating5.ramark5" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating4" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark4" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating4.ramark5" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating3" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark3" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating3.ramark4" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating2" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating2.ramark3" /></dd>
											</dl>
										</dd>
										<dd class="hide">
											<dl class="special_DL">
												<dt><bean:message bundle="performance" key="emp.self.assessment.rating1" /></dt>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark1" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark2" /></dd>
												<dd><bean:message bundle="performance" key="emp.self.assessment.rating1.ramark3" /></dd>
											</dl>
										</dd>
									</dl>
									<table>
										<thead>
											<tr>
												<th></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.one" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.two" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.three" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.four" /></th>
												<th><bean:message bundle="performance" key="emp.self.assessment.rating.five" /></th>
											</tr>
										</thead>
										<tbody>
											<tr id="rating_pm4_row" class="even">
												<td>Rating</td>
												<td>
													<label><input type="radio" name="rating_pm4" class="rating_pm4" value="5" <logic:notEmpty name="map4"><logic:equal name="map4" property="rating_pm4" value="5">checked="checked"</logic:equal></logic:notEmpty> >5</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm4" class="rating_pm4" value="4.5" <logic:notEmpty name="map4"><logic:equal name="map4" property="rating_pm4" value="4.5">checked="checked"</logic:equal></logic:notEmpty> >4.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><input type="radio" name="rating_pm4" class="rating_pm4" value="4" <logic:notEmpty name="map4"><logic:equal name="map4" property="rating_pm4" value="4">checked="checked"</logic:equal></logic:notEmpty> >5</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm4" class="rating_pm4" value="3.5" <logic:notEmpty name="map4"><logic:equal name="map4" property="rating_pm4" value="3.5">checked="checked"</logic:equal></logic:notEmpty> >3.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><input type="radio" name="rating_pm4" class="rating_pm4" value="3" <logic:notEmpty name="map4"><logic:equal name="map4" property="rating_pm4" value="3">checked="checked"</logic:equal></logic:notEmpty> >3</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm4" class="rating_pm4" value="2.5" <logic:notEmpty name="map4"><logic:equal name="map4" property="rating_pm4" value="2.5">checked="checked"</logic:equal></logic:notEmpty> >2.5</label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<label><input type="radio" name="rating_pm4" class="rating_pm4" value="2" <logic:notEmpty name="map4"><logic:equal name="map4" property="rating_pm4" value="2">checked="checked"</logic:equal></logic:notEmpty> >2</label>
												</td>
												<td>
													<label><input type="radio" name="rating_pm4" class="rating_pm4" value="1" <logic:notEmpty name="map4"><logic:equal name="map4" property="rating_pm4" value="1">checked="checked"</logic:equal></logic:notEmpty> >1</label>
												</td>
											</tr>
										</tbody>
									</table>
								</ol>
							</div>
<%-----------------¢Û People Manager end----------------%>
</logic:notEmpty>