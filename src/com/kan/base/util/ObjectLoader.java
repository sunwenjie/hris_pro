package com.kan.base.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Kevin Jin
 */

public class ObjectLoader
{

   public static Object loadObject( final String fileName ) throws KANException
   {
      return loadObject( new File( fileName ) );
   }

   public static Object loadObject( File file ) throws KANException
   {
      if ( !file.exists() || !file.isFile() )
      {
         throw new KANException( new FileNotFoundException( String.valueOf( file ) ) );
      }

      Object obj = null;
      FileInputStream fis = null;
      ObjectInputStream ois = null;

      try
      {
         fis = new FileInputStream( file );
         BufferedInputStream bufferedIn = new BufferedInputStream( fis );
         ois = new ObjectInputStream( bufferedIn );
         obj = ois.readObject();
      }
      catch ( IOException e )
      {
         throw new KANException( "Error loading object from file : " + file, e );
      }
      catch ( ClassNotFoundException e )
      {
         throw new KANException( "Class not found when loading object from file : " + file, e );
      }
      finally
      {
         if ( ois != null )
         {
            try
            {
               ois.close();
            }
            catch ( IOException e )
            {
               throw new KANException( "Error to close ObjectInputStream : " + e );
            }
         }

         if ( fis != null )
         {
            try
            {
               fis.close();
            }
            catch ( IOException e )
            {
               throw new KANException( "Error to close FileInputStream : " + e );
            }
         }
      }

      return obj;
   }

   public static Object loadObject( final URL url ) throws KANException
   {
      Object obj = null;
      InputStream is = null;
      ObjectInputStream ois = null;

      try
      {
         is = url.openStream();
         ois = new ObjectInputStream( is );
         obj = ois.readObject();
      }
      catch ( IOException e )
      {
         throw new KANException( "Error loading object from URL : " + url, e );
      }
      catch ( ClassNotFoundException e )
      {
         throw new KANException( "Class not found when loading object from URL : " + url, e );
      }
      finally
      {
         if ( ois != null )
         {
            try
            {
               ois.close();
            }
            catch ( IOException e )
            {
               throw new KANException( "Error to close ObjectInputStream : " + e );
            }
         }

         if ( is != null )
         {
            try
            {
               is.close();
            }
            catch ( IOException e )
            {
               throw new KANException( "Error to close FileInputStream : " + e );
            }
         }
      }

      return obj;
   }

   public static Object loadObject( final InputStream is ) throws KANException
   {
      Object obj = null;
      ObjectInputStream ois = null;

      try
      {
         ois = new ObjectInputStream( is );
         obj = ois.readObject();
      }
      catch ( IOException e )
      {
         throw new KANException( "Error loading object from InputStream", e );
      }
      catch ( ClassNotFoundException e )
      {
         throw new KANException( "Class not found when loading object from InputStream", e );
      }
      finally
      {
         if ( ois != null )
         {
            try
            {
               ois.close();
            }
            catch ( IOException e )
            {
               throw new KANException( "Error to close ObjectInputStream : " + e );
            }
         }
      }

      return obj;
   }

   public static byte[] loadBytes( final File file ) throws KANException
   {
      ByteArrayOutputStream baos = null;
      FileInputStream fis = null;

      try
      {
         fis = new FileInputStream( file );
         baos = new ByteArrayOutputStream();

         byte[] bytes = new byte[ 10000 ];
         int ln = 0;
         while ( ( ln = fis.read( bytes ) ) > 0 )
         {
            baos.write( bytes, 0, ln );
         }

         baos.flush();
      }
      catch ( IOException e )
      {
         throw new KANException( "Error loading byte data : " + file, e );
      }
      finally
      {
         if ( baos != null )
         {
            try
            {
               baos.close();
            }
            catch ( IOException e )
            {
               throw new KANException( "Error to close ByteArrayOutputStream : " + e );
            }
         }

         if ( fis != null )
         {
            try
            {
               fis.close();
            }
            catch ( IOException e )
            {
               throw new KANException( "Error to close FileInputStream : " + e );
            }
         }
      }

      return baos.toByteArray();
   }

   public static byte[] loadBytes( final URL url ) throws KANException
   {
      ByteArrayOutputStream baos = null;
      InputStream is = null;

      try
      {
         is = url.openStream();
         baos = new ByteArrayOutputStream();

         byte[] bytes = new byte[ 10000 ];
         int ln = 0;
         while ( ( ln = is.read( bytes ) ) > 0 )
         {
            baos.write( bytes, 0, ln );
         }

         baos.flush();
      }
      catch ( IOException e )
      {
         throw new KANException( "Error loading byte data : " + url, e );
      }
      finally
      {
         if ( baos != null )
         {
            try
            {
               baos.close();
            }
            catch ( IOException e )
            {
               throw new KANException( "Error to close ByteArrayOutputStream : " + e );
            }
         }

         if ( is != null )
         {
            try
            {
               is.close();
            }
            catch ( IOException e )
            {
               throw new KANException( "Error to close InputStream : " + e );
            }
         }
      }

      return baos.toByteArray();
   }

   public static byte[] loadBytes( final InputStream is ) throws KANException
   {
      ByteArrayOutputStream baos = null;

      try
      {
         baos = new ByteArrayOutputStream();

         byte[] bytes = new byte[ 10000 ];
         int ln = 0;
         while ( ( ln = is.read( bytes ) ) > 0 )
         {
            baos.write( bytes, 0, ln );
         }

         baos.flush();
      }
      catch ( IOException e )
      {
         throw new KANException( "Error loading byte data from input stream.", e );
      }
      finally
      {
         if ( baos != null )
         {
            try
            {
               baos.close();
            }
            catch ( IOException e )
            {
               throw new KANException( "Error to close ByteArrayOutputStream : " + e );
            }
         }
      }

      return baos.toByteArray();
   }

   public static InputStream getFileInputStream( final String filename ) throws KANException
   {
      InputStream is = null;

      File file = new File( filename );
      if ( file.exists() && file.isFile() )
      {
         try
         {
            is = new FileInputStream( file );
         }
         catch ( FileNotFoundException e )
         {
            throw new KANException( "Error opening file " + filename );
         }
      }
      return is;
   }

   public static InputStream getURLInputStream( final String spec ) throws KANException
   {
      InputStream is = null;

      try
      {
         URL url = new URL( spec );
         is = url.openStream();
      }
      catch ( MalformedURLException e )
      {
         throw new KANException( "Error Malformed URL " + spec );
      }
      catch ( IOException e )
      {
         throw new KANException( "Error opening URL " + spec );
      }

      return is;
   }
}