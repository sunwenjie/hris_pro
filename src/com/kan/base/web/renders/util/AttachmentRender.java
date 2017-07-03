package com.kan.base.web.renders.util;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class AttachmentRender
{
   // Return the <li> of attachments, no CSS in the LI tag
   public static String getAttachments( final HttpServletRequest request, final String[] attachmentArray, final String id_name ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      String inputName = "attachmentArray";

      if ( id_name != null && !id_name.trim().equals( "" ) )
      {
         inputName = id_name;
      }

      if ( attachmentArray != null && attachmentArray.length > 0 )
      {
         for ( String attachment : attachmentArray )
         {
            if ( attachment.indexOf( "##" ) > 0 )
            {
               rs.append( "<li><input type='hidden' id='" + inputName + "' name='" + inputName + "' value='" + attachment + "' />" );
               if ( !"workflow".equalsIgnoreCase( request.getParameter( "comeFrom" ) ) )
               {
                  rs.append( "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\">"
                        + "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" style=\"display: none;\" onclick='removeAttachment(this);'> &nbsp; " );
               }
               String fileName = attachment.split( "##" )[ 1 ].split( "&#160;" )[ 0 ].trim();
               rs.append( "<a onclick=\"encodedLink('downloadFileAction.do?proc=download&fileString=" + attachment.split( "##" )[ 0 ] + "&fileName=" + fileName + "');\">"
                     + attachment.split( "##" )[ 1 ] + "</a></li>" );
            }
         }
      }

      return rs.toString();
   }

   public static String getPhotos( final HttpServletRequest request, final String imageFileString, final String id_name ) throws KANException
   {
      return getPhotos( request, imageFileString, id_name, 140, 110, 120, 90 );
   }

   public static String getPhotos( final HttpServletRequest request, final String imageFileString, final String id_name, final int width1, final int height1, final int width2,
         final int height2 ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      try
      {
         String inputName = "imageFileArray";
         String[] imageFileArray = null;
         if ( KANUtil.filterEmpty( imageFileString ) != null )
         {
            imageFileArray = imageFileString.split( "##" );
         }
         if ( id_name != null && !id_name.trim().equals( "" ) )
         {
            inputName = id_name;
         }

         final String onclickAction = "downloadFileAction.do?proc=download&fileString=/";

         if ( imageFileArray != null && imageFileArray.length > 0 )
         {
            for ( String attachment : imageFileArray )
            {
               attachment = attachment.replaceAll( "\\\\", "/" );
               int index = attachment.lastIndexOf( "/" );
               String imgName = attachment.substring( index + 1, attachment.length() );
               String imgSrc = attachment.substring( 0, index + 1 ) + java.net.URLEncoder.encode( java.net.URLEncoder.encode( imgName, "UTF-8" ), "UTF-8" );
               rs.append( "<li style='width:" + width1 + "px !important; height:" + height1 + "px !important;'><input type='hidden' id='" + inputName + "' name='" + inputName
                     + "' value='" + attachment + "' />" + "<img src='images/close_pop.png' width='32' height='32' class='deleteImg' onclick='removeAttachment(this);'>"
                     + "<a class='tooltip' href='" + onclickAction + imgSrc + "'  >" + "<img class='imgtooltip' src='" + onclickAction + imgSrc + "' width='" + width2
                     + "' height='" + height2 + "' />" + "</a></li>" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return rs.toString();
   }
}
