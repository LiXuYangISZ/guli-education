package com.rg.demo.demo2;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/15 19:02
 */
public class Test {
    public static void main(String[] args) {
        Map <String, Object> map = new HashMap <>();
        System.out.println(map);
        map.put("lxy",132);
        System.out.println(map.get("lxy"));
    }
}
