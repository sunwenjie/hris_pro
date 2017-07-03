package com.kan.base.web.renders.setting;

public class BaseSetting
{
   /**
    * 是否有CheckBox控件
    */
   private boolean hasCheckBox = true;
   /***
    * 
    */
   private String checkBoxName;
   /**
    * 复选框单击回调函数名
    */
   private String checkBoxClickFunName;
   /**
    * 节点单击事件函数名
    */
   private String noteClickFunName;
   /**
    * 单击事件函数需要回传的对象属性
    */
   private String onClickFunParametes[];

   


   public BaseSetting()
   {
      // 默认方法设置
   }
   public BaseSetting(boolean hasCheckBox)
   {
      // 默认方法设置
      this.hasCheckBox = hasCheckBox;
   }

   public boolean isHasCheckBox()
   {
      return hasCheckBox;
   }

   public void setHasCheckBox( boolean hasCheckBox )
   {
      this.hasCheckBox = hasCheckBox;
   }

   public String getNoteClickFunName()
   {
      return noteClickFunName;
   }

   public void setNoteClickFunName( String onClickFunName )
   {
      this.noteClickFunName = onClickFunName;
   }

   public String[] getOnClickFunParametes()
   {
      return onClickFunParametes;
   }
   /***
    * 设置节点回调函数回传对象所需属性
    * @param noteClickFunParametes 属性名集合
    */
   public void setOnClickFunParametes( String[] noteClickFunParametes )
   {
      this.onClickFunParametes = noteClickFunParametes;
   }
   /***
    * 设置节点回调函数回传对象所需属性
    * @param noteClickFunParametes  属性名集合字符串
    * @param split  中间用split隔开
    */
   public void setOnClickFunParametes( String noteClickFunParametes,String split )
   {
      this.onClickFunParametes = noteClickFunParametes.split( split );
   }
   /**
    * 设置节点回调函数回传对象所需属性，中间用“,”隔开
    * @param noteClickFunParametes  属性名集合字符串
    */
   public void setOnClickFunParametes( String noteClickFunParametes )
   {
      this.onClickFunParametes = noteClickFunParametes.split( "," );
   }

   public String getCheckBoxClickFunName()
   {
      return checkBoxClickFunName;
   }

   public void setCheckBoxClickFunName( String checkBoxClickFunName )
   {
      this.checkBoxClickFunName = checkBoxClickFunName;
   }
   public String getCheckBoxName()
   {
      return checkBoxName;
   }
   public void setCheckBoxName( String checkBoxName )
   {
      this.checkBoxName = checkBoxName;
   }
  
}
