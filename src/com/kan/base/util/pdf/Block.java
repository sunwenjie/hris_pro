package com.kan.base.util.pdf;

import java.awt.Graphics;
import java.util.List;

public class Block
{
   private int posX;

   private int posY;

   private int width;

   private int height;

   private int level;

   private int zIndex;

   private String objectId;

   private String parentObjectId;

   private String objectNameZH;

   private String objectNameEN;

   private String objectNO;

   private List< Block > childBlocks;

   public static int PAGEBLANKX = 34;

   public static int PAGEBLANKY = 220;

   public Block()
   {
      super();
   }

   public Block( int posX, int posY, int width, int height, int level, int zIndex, String objectId, String parentObjectId, String objectNameZH, String objectNameEN,
         String objectNO, List< Block > childBlocks )
   {
      super();
      this.posX = posX;
      this.posY = posY;
      this.width = width;
      this.height = height;
      this.level = level;
      this.zIndex = zIndex;
      this.objectId = objectId;
      this.parentObjectId = parentObjectId;
      this.objectNameZH = objectNameZH;
      this.objectNameEN = objectNameEN;
      this.objectNO = objectNO;
      this.childBlocks = childBlocks;
   }

   public Block( int posX, int posY, int width, int height )
   {
      super();
      this.posX = posX;
      this.posY = posY;
      this.width = width;
      this.height = height;
   }

   public int getPosX()
   {
      return posX;
   }

   public void setPosX( int posX )
   {
      this.posX = posX;
   }

   public int getPosY()
   {
      return posY;
   }

   public void setPosY( int posY )
   {
      this.posY = posY;
   }

   public int getWidth()
   {
      return width;
   }

   public void setWidth( int width )
   {
      this.width = width;
   }

   public int getHeight()
   {
      return height;
   }

   public void setHeight( int height )
   {
      this.height = height;
   }

   public int getLevel()
   {
      return level;
   }

   public void setLevel( int level )
   {
      this.level = level;
   }

   public int getzIndex()
   {
      return zIndex;
   }

   public void setzIndex( int zIndex )
   {
      this.zIndex = zIndex;
   }

   public String getObjectId()
   {
      return objectId;
   }

   public void setObjectId( String objectId )
   {
      this.objectId = objectId;
   }

   public String getParentObjectId()
   {
      return parentObjectId;
   }

   public void setParentObjectId( String parentObjectId )
   {
      this.parentObjectId = parentObjectId;
   }

   public String getObjectNameZH()
   {
      return objectNameZH;
   }

   public void setObjectNameZH( String objectNameZH )
   {
      this.objectNameZH = objectNameZH;
   }

   public String getObjectNameEN()
   {
      return objectNameEN;
   }

   public void setObjectNameEN( String objectNameEN )
   {
      this.objectNameEN = objectNameEN;
   }

   public String getObjectNO()
   {
      return objectNO;
   }

   public void setObjectNO( String objectNO )
   {
      this.objectNO = objectNO;
   }

   public List< Block > getChildBlocks()
   {
      return childBlocks;
   }

   public void setChildBlocks( List< Block > childBlocks )
   {
      this.childBlocks = childBlocks;
   }

   public int paddingLeft( int length )
   {
      int paddingLeft = 0;
      paddingLeft = this.width / 2 - ( length / 2 ) * 11;
      return paddingLeft;
   }

   public void paintThis( Graphics g, Block block )
   {
      g.drawRect( block.getPosX(), block.getPosY(), block.getWidth() + 10, block.getHeight() );
      g.drawString( block.getObjectNO().trim(), block.getPosX() + 10, block.getPosY() + 5 );
      g.drawString( block.getObjectNameZH().trim(), block.getPosX() + 10, block.getPosY() + 20 );
      g.drawString( block.getObjectNameEN().trim(), block.getPosX() + 10, block.getPosY() + 35 );
   }

   public static void blockToBlock( Graphics g, Block block, Block block1 )
   {
      int tempY = ( block1.getPosY() - block.getPosY() - block.getHeight() ) / 2 + block.getHeight();
      BlockPoint point1 = new BlockPoint( block.getPosX() + block.getWidth() / 2, block.getPosY() + +tempY );
      BlockPoint point2 = new BlockPoint( block1.getPosX() + block1.getWidth() / 2, block.getPosY() + tempY );
      BlockPoint pointOnBlock1 = new BlockPoint( point1.getX(), block.getPosY() + block.getHeight() );
      BlockPoint pointOnBlock2 = new BlockPoint( point2.getX(), block1.getPosY() );
      // 左右线
      g.drawLine( point1.getX(), point1.getY(), point2.getX(), point2.getY() );
      // 上下线 1
      g.drawLine( pointOnBlock1.getX(), pointOnBlock1.getY(), point1.getX(), point1.getY() );
      // 上下线 2
      g.drawLine( pointOnBlock2.getX(), pointOnBlock2.getY(), point2.getX(), point2.getY() );
   }

   public static int minLeft( List< Block > blocks )
   {
      int minLeft = 0;
      if ( blocks != null && blocks.size() > 0 )
      {
         minLeft = blocks.get( 0 ).getPosX();
      }
      for ( Block block : blocks )
      {
         if ( block.getPosX() < minLeft )
         {
            minLeft = block.getPosX();
         }
      }
      return minLeft;
   }

   public static void reSize( List< Block > blocks, int minLeft )
   {
      for ( int i = 0; i < blocks.size(); i++ )
      {
         blocks.get( i ).setPosX( blocks.get( i ).getPosX() - minLeft + 20 );
      }
   }

   public static void paintTitle( Graphics g, String title, int left, int top )
   {
      g.drawString( title, left, top );
   }
}
