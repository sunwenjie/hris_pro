package com.kan.base.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.AbstractManageableImageCaptchaService;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

public class CaptchaServiceSingleton
{
   private static ImageCaptchaService instance = new DefaultManageableImageCaptchaService( new FastHashMapCaptchaStore(), new CaptchaEngine(), 180, 100000, 75000 );

   public static ImageCaptchaService getInstance()
   {
      return instance;
   }
   
   private CaptchaServiceSingleton(){}
   public static BufferedImage generate(String sessionId) throws IOException {
          return ((AbstractManageableImageCaptchaService) instance).getImageChallengeForID(sessionId);
      }
      public static boolean validate(String sessionId, String authCode) {
          return instance.validateResponseForID(sessionId, authCode);
      }
}
