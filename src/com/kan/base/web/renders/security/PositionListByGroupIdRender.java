package com.kan.base.web.renders.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.util.KANException;

public class PositionListByGroupIdRender
{

   public static String getPositionListByGroupId( final Locale locale, final List<Object> positionList, final List<Object> relationsByGroupId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      List<String> positionIdList = new ArrayList<String> ();
      for(Object positionGroupRelationVOObject: relationsByGroupId){
         PositionGroupRelationVO positionGroupRelationVO = ( PositionGroupRelationVO ) positionGroupRelationVOObject;
         if("1".equals( positionGroupRelationVO.getDeleted() )){
            positionIdList.add( positionGroupRelationVO.getPositionId() );
         }
      }
      for(Object positionObject: positionList){
         PositionVO positionVO = ( PositionVO ) positionObject;
         rs.append( "<li>" );
         if(positionIdList.contains( positionVO.getPositionId() ) ){
            rs.append( "<input checked=\"checked\" class=\"position\" type=\"checkbox\" name=\"positionId\" id=\"" + positionVO.getPositionId() + "\" value=\"" + positionVO.getPositionId() + "\" />" + positionVO.getTitleZH() + "" );
         }else{
            rs.append( "<input class=\"position\" type=\"checkbox\" name=\"positionId\" id=\"" + positionVO.getPositionId() + "\" value=\"" + positionVO.getPositionId() + "\" />" + positionVO.getTitleZH() + "" );
         }
         rs.append( "</li>" );
      }
      return rs.toString();
   }
}
