package com.kan.base.util.jCaptcha;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.octo.captcha.image.ImageCaptcha;

/**
 * ��дGimpy.class��֧�֡����Դ�Сд������
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
        // ��Ҫ�ĵ�����
        return new Boolean(response.toLowerCase().equals(this.response.toLowerCase()));
    }
}
