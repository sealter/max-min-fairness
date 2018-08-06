package zchao.love.test;

import zchao.love.MemoryResource;
import zchao.love.ResourceUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestMemoryResource {

    public static void main(String[] args) {
        // 4,2,10,4,
        // 2.5,4,0.5,1

        ResourceUser user1 = new ResourceUser("user-1", 4.0, 2.5);
        ResourceUser user2 = new ResourceUser("user-2", 2.0, 4.0);
        ResourceUser user3 = new ResourceUser("user-3", 10.0, 0.5);
        ResourceUser user4 = new ResourceUser("user-4", 4.0, 1.0);

        List<ResourceUser> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);

        MemoryResource memoryResource = new MemoryResource(16.0, list);

        Map<String, Double> result = memoryResource.getResources();

        for (Map.Entry<String, Double> entry : result.entrySet()) {

            System.out.println(entry.getKey() + ":" + entry.getValue());

        }

    }
}
