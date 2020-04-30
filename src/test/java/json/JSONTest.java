package json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teeqee.spring.dispatcher.servlet.login.entity.Site;
import com.teeqee.spring.dispatcher.servlet.login.entity.Taskdata;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Description:
 * @Author: zhengsongjie
 * @File: JSONTest
 * @Version: 1.0.0
 * @Time: 2020-04-27 上午 11:10
 * @Project: java_chaochong
 * @Package: json
 * @Software: IntelliJ IDEA
 */
public class JSONTest {
    public static void main(String[] args) {

    }

    private void getJsonKey(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        jsonObject.forEach((k,v)-> System.out.println(k));
    }
    @Test
    public void Test(){
        String json ="{\"avatar\":\"\",\"biggestanimalid\":1,\"diamond\":0,\"gold\":0,\"lasttime\":1587955513103,\"lastweekrank\":1,\"lastweekreward\":0,\"newgamer\":0,\"nickname\":\"未授权的玩家\",\"openid\":\"zsjj\",\"phonefare\":0,\"phonefarenumber\":0,\"rankchallengenum\":10,\"rankpermission\":0,\"refreshworldnum\":3,\"rounds\":0,\"sound\":1,\"speedincubate\":0,\"stock\":30,\"stockmax\":30,\"todaysign\":false,\"totalincubate\":0,\"viplv\":0,\"vipreward\":0,\"weeksign\":0}";
        getJsonKey(json);
    }


    @Test
    public void test(){
        String json = "[{\"siteid \":0,\"animalid \":1},{\"siteid \":1,\"animalid \":1},{\"siteid \":2,\"animalid \":0},{\"siteid \":3,\"animalid \":0},{\"siteid \":4,\"animalid \":0},{\"siteid \":5,\"animalid \":0},{\"siteid \":6,\"animalid \":0},{\"siteid \":7,\"animalid \":0},{\"siteid \":8,\"animalid \":0},{\"siteid \":9,\"animalid \":0},{\"siteid \":10,\"animalid \":-1},{\"siteid \":11,\"animalid \":-1},{\"siteid \":12,\"animalid \":-1},{\"siteid \":13,\"animalid \":-1},{\"siteid \":14,\"animalid \":-1},{\"siteid \":15,\"animalid \":-1},{\"siteid \":16,\"animalid \":-1},{\"siteid \":17,\"animalid \":-1},{\"siteid \":18,\"animalid \":-1},{\"siteid \":19,\"animalid \":-1}]";
        List<Site> sites = JSONArray.parseArray(json, Site.class);
        JSONArray jsonArray = new JSONArray();
        for (Site site : sites) {
            String s = JSONObject.toJSONString(site);
            jsonArray.add(s);
        }
        System.out.println(jsonArray.toJSONString());
    }



}
