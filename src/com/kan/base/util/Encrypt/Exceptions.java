package com.kan.base.util.Encrypt;



import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * �����쳣�Ĺ�����.
 * 
 * �ο���guava��Throwables��
 * 
 * @author calvin
 */
public class Exceptions {

	/**
	 * ��CheckedExceptionת��ΪUncheckedException.
	 */
	public static RuntimeException unchecked(Throwable ex) {
		if (ex instanceof RuntimeException) {
			return (RuntimeException) ex;
		} else {
			return new RuntimeException(ex);
		}
	}

	/**
	 * ��ErrorStackת��ΪString.
	 */
	public static String getStackTraceAsString(Throwable ex) {
		StringWriter stringWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * ��ȡ��ϱ��쳣��Ϣ��ײ��쳣��Ϣ���쳣����, �����ڱ��쳣Ϊͳһ��װ�쳣�࣬�ײ��쳣���Ǹ���ԭ��������
	 */
	public static String getErrorMessageWithNestedException(Throwable ex) {
		Throwable nestedException = ex.getCause();
		return new StringBuilder().append(ex.getMessage()).append(" nested exception is ")
				.append(nestedException.getClass().getName()).append(":").append(nestedException.getMessage())
				.toString();
	}

	/**
	 * ��ȡ�쳣��Root Cause.
	 */
	public static Throwable getRootCause(Throwable ex) {
		Throwable cause;
		while ((cause = ex.getCause()) != null) {
			ex = cause;
		}
		return ex;
	}

	/**
	 * �ж��쳣�Ƿ���ĳЩ�ײ���쳣����.
	 */
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex;
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
}
