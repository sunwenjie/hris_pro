package com.kan.base.domain.security;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class PositionStaffRelationVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 3167443276834578755L;

   private String relationId;

   private String positionId;

   private String staffId;

   private String staffType = "1";

   private String agentStart;

   private String agentEnd;

   private String description;

   /**
    * For Application
    */
   private List< MappingVO > staffTypes;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.staffTypes = KANUtil.getMappings( this.getLocale(), "security.position.staff.relation.staff.type" );
   }

   public void update( Object object )
   {
      final PositionStaffRelationVO positionStaffRelationVO = ( PositionStaffRelationVO ) object;
      this.setPositionId( positionStaffRelationVO.getPositionId() );
      this.setStaffId( positionStaffRelationVO.getStaffId() );
      super.setStatus( positionStaffRelationVO.getStatus() );
      super.setModifyBy( positionStaffRelationVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public void reset()
   {
      super.setStatus( "0" );
   }

   public String getRelationId()
   {
      return relationId;
   }

   public void setRelationId( String relationId )
   {
      this.relationId = relationId;
   }

   public String getPositionId()
   {
      return positionId;
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
   }

   public String getStaffId()
   {
      return staffId;
   }

   public void setStaffId( String staffId )
   {
      this.staffId = staffId;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return null;
   }

   public String getStaffType()
   {
      return staffType;
   }

   public void setStaffType( String staffType )
   {
      this.staffType = staffType;
   }

   public String getAgentStart()
   {
      if ( "null".equals( this.agentStart ) )
      {
         return null;
      }
      return agentStart;
   }

   public void setAgentStart( String agentStart )
   {
      this.agentStart = agentStart;
   }

   public String getAgentEnd()
   {
      if ( "null".equals( this.agentEnd ) )
      {
         return null;
      }
      return agentEnd;
   }

   public void setAgentEnd( String agentEnd )
   {
      this.agentEnd = agentEnd;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getStaffTypes()
   {
      return staffTypes;
   }

   public void setStaffTypes( List< MappingVO > staffTypes )
   {
      this.staffTypes = staffTypes;
   }

}
