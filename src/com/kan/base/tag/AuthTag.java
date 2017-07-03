package com.kan.base.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.kan.base.util.KANException;

/**
 * 
 * modify by jack.sun rightMap只需创建一遍
 *
 */
public class AuthTag extends TagSupport
{

   private static final long serialVersionUID = 1L;

   private String action;

   private String right;

   private String owner;

   public static final Map< String, String > rightMap = new HashMap< String, String >();

   static
   {
      rightMap.put( "new", AuthConstants.RIGHT_NEW );
      rightMap.put( "view", AuthConstants.RIGHT_VIEW );
      rightMap.put( "modify", AuthConstants.RIGHT_MODIFY );
      rightMap.put( "delete", AuthConstants.RIGHT_DELETE );
      rightMap.put( "list", AuthConstants.RIGHT_LIST );
      rightMap.put( "submit", AuthConstants.RIGHT_SUBMIT );
      rightMap.put( "sickleave", AuthConstants.RIGHT_SICK_LEAVE );
      rightMap.put( "back", AuthConstants.RIGHT_BACK );
      rightMap.put( "approve", AuthConstants.RIGHT_APPROVE );
      rightMap.put( "confirm", AuthConstants.RIGHT_CONFIRM );
      rightMap.put( "posting", AuthConstants.RIGHT_POSTING );
      rightMap.put( "salarysetting", AuthConstants.RIGHT_SALARY_SETTING );
      rightMap.put( "grant", AuthConstants.RIGHT_GRANT );
      rightMap.put( "import", AuthConstants.RIGHT_IMPORT );
      rightMap.put( "export", AuthConstants.RIGHT_EXPORT );
      rightMap.put( "previous", AuthConstants.RIGHT_PREVIOUS );
      rightMap.put( "next", AuthConstants.RIGHT_NEXT );
      rightMap.put( "sealed", AuthConstants.RIGHT_SEALED );
      rightMap.put( "archive", AuthConstants.RIGHT_ARCHIVE );
      rightMap.put( "cancel", AuthConstants.RIGHT_CANCEL );
      rightMap.put( "stop", AuthConstants.RIGHT_STOP );
      rightMap.put( "execute", AuthConstants.RIGHT_EXECUTE );
      rightMap.put( "testlink", AuthConstants.RIGHT_TEST_LINK );
      rightMap.put( "agree", AuthConstants.RIGHT_AGREE );
      rightMap.put( "refuse", AuthConstants.RIGHT_REFUSE );
      rightMap.put( "dimission", AuthConstants.RIGHT_DIMISSION );
      rightMap.put( "print", AuthConstants.RIGHT_PRINT );
      rightMap.put( "quickcreate", AuthConstants.RIGHT_QUICK_CREATE );
      rightMap.put( "sortlist", AuthConstants.RIGHT_SORT_LIST );
      rightMap.put( "generate", AuthConstants.RIGHT_GENERATE );
      rightMap.put( "sync", AuthConstants.RIGHT_SYNC );
      rightMap.put( "transfer", AuthConstants.RIGHT_TRANSFER_HR_OWNER );
   }

   @Override
   public int doStartTag() throws JspException
   {

      int returnValue = TagSupport.SKIP_BODY;

      final HttpServletRequest request = ( HttpServletRequest ) pageContext.getRequest();
      final HttpServletResponse response = ( HttpServletResponse ) pageContext.getResponse();

      try
      {
         if ( AuthUtils.hasAuthority( action, rightMap.get( right.toLowerCase() ), owner, request, response ) )
         {
            returnValue = TagSupport.EVAL_BODY_INCLUDE;
         }
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }

      return returnValue;
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   public String getRight()
   {
      return right;
   }

   public void setRight( String right )
   {
      this.right = right;
   }

   public String getAction()
   {
      return action;
   }

   public void setAction( String action )
   {
      this.action = action;
   }
}
