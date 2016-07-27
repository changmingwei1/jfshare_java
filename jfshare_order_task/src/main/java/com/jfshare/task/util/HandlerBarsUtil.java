package com.jfshare.task.util;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Lenovo on 2016/7/27.
 */
public class HandlerBarsUtil {
    private static Handlebars handlebars = new Handlebars();

    public static String replace(String handlebarsStr, Map<String, Object> obj) {
        String result = "";
        try {
            Template template = handlebars.compileInline(handlebarsStr);
            result = template.apply(obj);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
