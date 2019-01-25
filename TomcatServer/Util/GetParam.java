package TomcatServer.Util;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class GetParam {

    /*
     * @auther: Ragty
     * @describe: 分割请求参数
     * @param: [property]
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @date: 2019/1/24
     */
    public Map<String,String> getParam(String property) {
        Map<String,String> map = new HashMap<>();
        String[] split = property.split("&");

        for (int i=0; i<split.length; i++) {
            String[] split2 = split[i].split("=");
            map.put(split2[0], URLDecoder.decode(split2[1]));
        }
        return map;
    }

}
