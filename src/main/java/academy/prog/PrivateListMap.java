package academy.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
public class PrivateListMap {
    private final Gson gson;
    private static final Map<String, List<Message>> privateMap = new HashMap<>();
    public static final PrivateListMap prvMap = new PrivateListMap();
    private List<Message> list = new LinkedList<>();

    private PrivateListMap () {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }
    public static PrivateListMap getInstance() {
        return prvMap;
    }

    public synchronized void add(String login, Message msg) {
        List<Message> lst = new LinkedList<>();
        if (privateMap.get(login) != null) {
            lst = privateMap.get(login);
            lst.add(msg);
        } else {
            lst.add(msg);
        }
        privateMap.put(login, lst);
    }

    public synchronized String toJSON(int p, String login) {
        List<Message> list = list = privateMap.get(login);
        if(list != null) {
            if (p >= list.size()) return null;
            return gson.toJson(new JsonMessages(list, p));
        } else return null;
    }

}

