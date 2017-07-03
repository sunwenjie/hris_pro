package com.kan.base.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.system.RuleVO;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class AuthUtils
{
   public static boolean hasAuthority( final String accessAction, final String right, final String owner, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      if ( KANConstants.ROLE_CLIENT.equals( BaseAction.getRole( request, response ) ) )
      {
         if ( right.equals( AuthConstants.RIGHT_LIST ) || right.equals( AuthConstants.RIGHT_EXPORT ) || right.equals( AuthConstants.RIGHT_VIEW )
               || right.equals( AuthConstants.RIGHT_NEXT ) || right.equals( AuthConstants.RIGHT_PREVIOUS ) )
         {
            return true;
         }
         else
         {
            return false;
         }
      }
      else if ( KANConstants.ROLE_EMPLOYEE.equals( BaseAction.getRole( request, response ) ) )
      {
         if ( "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT".equals( accessAction ) && AuthConstants.RIGHT_DIMISSION.equals( right ) )
         {
            return false;
         }
         return true;
      }
      if ( AuthConstants.RIGHT_LIST.equals( right.toLowerCase() ) )
      {
         return true;
      }

      boolean hasRight = false;

      try
      {
         // 初始化AccountId
         final String accountId = BaseAction.getAccountId( request, response );
         hasRight = KANConstants.getKANAccountConstants( accountId ).hasAuthority( BaseAction.getUserVOFromClient( request, response ), accessAction, right );

         if ( accountId.equals( KANConstants.SUPER_ACCOUNT_ID ) )
         {
            return true;
         }

         if ( KANUtil.filterEmpty( owner ) != null )
         {
            // 初始化AccountId
            final UserVO userVO = BaseAction.getUserVOFromClient( request, response );

            if ( right.equals( AuthConstants.RIGHT_VIEW ) )
            {
               hasRight = hasRight || getTagStatusByRule( AuthConstants.RULE_VIEW, userVO, accessAction, owner );
            }
            else if ( right.equals( AuthConstants.RIGHT_NEW ) )
            {
               hasRight = hasRight || getTagStatusByRule( AuthConstants.RULE_NEW, userVO, accessAction, owner );
            }
            else if ( right.equals( AuthConstants.RIGHT_MODIFY ) )
            {
               hasRight = hasRight || getTagStatusByRule( AuthConstants.RULE_MODIFY, userVO, accessAction, owner );
            }
            else if ( right.equals( AuthConstants.RIGHT_DELETE ) )
            {
               hasRight = hasRight || getTagStatusByRule( AuthConstants.RULE_DELETE, userVO, accessAction, owner );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return hasRight;
   }

   private static boolean getTagStatusByRule( String ruleName, UserVO userVO, String action, String owner )
   {
      boolean returnValue = false;
      KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( userVO.getAccountId() );
      Set< RuleVO > listRule = accountConstants.getRuleVOsByOwner( owner, action );

      if ( listRule.size() == 0 )
      {
         returnValue = false;
      }
      else
      {
         StaffDTO staffDTO = KANConstants.getKANAccountConstants( userVO.getAccountId() ).getStaffDTOByStaffId( userVO.getStaffId() );
         for ( RuleVO ruleVO : listRule )
         {
            String ruleType = ruleVO.getRuleType();
            String[] rule = KANUtil.jasonArrayToStringArray( ruleVO.getRemark1() );
            if ( ArrayUtils.contains( rule, ruleName ) )
            {

               if ( ruleType.equals( AuthConstants.RULE_PUBLIC_TYPE ) )
               {
                  returnValue = true;
                  break;
               }

               if ( ruleType.equals( AuthConstants.RULE_BRANCH_TYPE ) && staffDTO.getBranchPositions().contains( owner ) )
               {
                  returnValue = true;
                  break;
               }
               if ( ruleType.equals( AuthConstants.RULE_SUPER_TYPE ) && staffDTO.getParentPositions().contains( owner ) )
               {
                  returnValue = true;
                  break;
               }
               if ( ruleType.equals( AuthConstants.RULE_SUB_TYPE ) && staffDTO.getChildPositions().contains( owner ) )
               {
                  returnValue = true;
                  break;
               }

               if ( ruleType.equals( AuthConstants.RULE_PRIVATE_TYPE ) && getCatchPositionByUserId( userVO ).contains( owner ) )
               {
                  returnValue = true;
                  break;

               }
            }
         }
      }

      return returnValue;
   }

   private static List< String > getCatchPositionByUserId( UserVO userVO )
   {
      List< MappingVO > list = userVO.getPositions();
      List< String > returnList = new ArrayList< String >();

      if ( list == null || list.size() == 0 )
      {
         return returnList;
      }

      for ( MappingVO mapping : list )
      {
         returnList.add( mapping.getMappingId() );
      }

      return returnList;
   }
}
