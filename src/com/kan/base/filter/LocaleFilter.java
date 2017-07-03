/*
 * Created on 2013-5-6 TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.kan.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.action.LocaleRequestWrapper;

/**
 * @author Kevin Jin
 */
public class LocaleFilter extends BaseAction implements Filter
{
   protected String[] languages = null;

   protected LocaleRequestWrapper requestWrapper = null;

   @Override
   public void init( FilterConfig filterConfig ) throws ServletException
   {
      // No Use
   }

   @Override
   public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException
   {
      try
      {
         final String accountId = getAccountId( ( HttpServletRequest ) request, ( HttpServletResponse ) response );
         if ( accountId != null && !accountId.trim().equalsIgnoreCase( "" ) && !KANConstants.getKANAccountConstants( accountId ).OPTIONS_USE_BS_LANGUAGE )
         {
            if ( KANConstants.getKANAccountConstants( accountId ).OPTIONS_LANGUAGE != null )
            {
               languages = KANConstants.getKANAccountConstants( accountId ).OPTIONS_LANGUAGE.split( "_" );
               if ( languages.length > 1 )
               {
                  requestWrapper = new LocaleRequestWrapper( ( HttpServletRequest ) request );
                  requestWrapper.setLanguage( languages[ 0 ] );
                  requestWrapper.setCounrty( languages[ 1 ] );
                  chain.doFilter( requestWrapper, response );
                  return;
               }
            }
         }
      }
      catch ( final Exception e )
      {
         e.printStackTrace();
      }
      chain.doFilter( request, response );
   }

   @Override
   public void destroy()
   {
      this.languages = null;
      this.requestWrapper = null;
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

}