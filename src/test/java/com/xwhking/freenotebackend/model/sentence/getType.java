package com.xwhking.freenotebackend.model.sentence;

import lombok.Data;
import org.apache.commons.collections4.MapUtils;

import java.util.*;

@Data
public class getType {
    public static String get(String type){
        Map<String,String> types = new HashMap<>();
        types.put("a","动画");
        types.put("b","漫画");
        types.put("c","游戏");
        types.put("d","文学");
        types.put("e","原创");
        types.put("f","网络");
        types.put("g","其他");
        types.put("h","影视");
        types.put("i","诗词");
        types.put("j","网易云");
        types.put("k","哲学");
        types.put("l","抖机灵");
        types.put("m","鸡汤");
        return types.get(type);
    }
}
