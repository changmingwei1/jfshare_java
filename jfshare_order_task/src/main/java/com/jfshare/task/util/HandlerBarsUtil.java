package com.jfshare.task.util;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import java.io.IOException;
import java.util.HashMap;
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

    public static void main(String[] str) {
        String handlebarsStr = "{\n" +
                "\t\"from:\"{{#if from}}{{from}}{{else}}0{{/if}},\n" +
                "\t\"size:\"{{#if size}}{{size}}{{else}}1{{/if}},\n" +
                "}";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("time1", "2015-05-16");
        map.put("name", "小明");
        map.put("from", 100);
//        map.put("size", 200);
        String replace = replace(handlebarsStr, map);
        System.out.println(replace);
    }
}
