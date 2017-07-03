package com.kan.base.util.jCaptcha;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.octo.captcha.image.ImageCaptcha;

/**
 * 改写Gimpy.class，支持“忽略大小写”功能
 */

public class MyGimpy extends ImageCaptcha implements Serializable {
	private static final long serialVersionUID = -1222476851434718830L;
	private String response;
    MyGimpy(String question, BufferedImage challenge, String response) {
        super(question, challenge);
        this.response = response;
    }
    public final Boolean validateResponse(final Object response) {
        return (null != response && response instanceof String)
                ? validateResponse((String) response) : Boolean.FALSE;
    }
    private final Boolean validateResponse(final String response) {
        // 主要改的这里
        return new Boolean(response.toLowerCase().equals(this.response.toLowerCase()));
    }
}
