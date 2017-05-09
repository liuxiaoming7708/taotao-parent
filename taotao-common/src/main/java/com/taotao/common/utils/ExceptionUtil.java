package com.taotao.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 获取异常堆栈信息
 * Created by Administrator on 2017/4/5.
 */
public class ExceptionUtil {
    public static String getStackTrace(Throwable t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            t.printStackTrace(pw);
            return sw.toString();
        }finally {
            pw.close();
        }
    }
}
