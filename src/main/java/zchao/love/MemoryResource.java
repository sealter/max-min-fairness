package zchao.love;

import java.util.*;

/*
    最大最小公平分配算法的形式化定义如下：
	• 资源按照需求递增的顺序进行分配
	• 不存在用户得到的资源超过自己的需求
    • 未得到满足的用户等价的分享资源

    通过定义带权的最大最小公平分配来扩展最大最小公平分配的概念以使其包含这样的权重:
	• 资源按照需求递增的顺序进行分配,通过权重来标准化
	• 不存在用户得到的资源超过自己的需求
	• 未得到满足的用户按照权重分享资源

 */

public class MemoryResource implements Resource<Double> {

    private double maxResource;
    private List<ResourceUser> resourceUsers;

    public MemoryResource(Double maxResource, List<ResourceUser> resourceUsers) {
        this.maxResource = maxResource;
        this.resourceUsers = resourceUsers;
    }

    public Double getResource(String name) {
        Map<String, Double> resoures = getResources();

        return resoures.get(name);
    }

    public Double getMaxResource() {

        Map<String, Double> resoures = getResources();
        List<Double> list = new ArrayList<Double>(resoures.values());
        Collections.sort(list);
        return list.get(list.size() - 1);
    }

    public Double getMinResource() {
        Map<String, Double> resoures = getResources();
        List<Double> list = new ArrayList<>(resoures.values());
        Collections.sort(list);
        return list.get(0);
    }

    public Map<String, Double> getResources() {

        assert resourceUsers != null;


        List<ResourceUser> resUsersSeqByWeigth = getResourceUsersSeq(resourceUsers, new Comparator<ResourceUser>() {
            public int compare(ResourceUser o1, ResourceUser o2) {
                return o1.getWeight() - o2.getWeight() > 0 ? 1 : (o1.getWeight() - o2.getWeight() == 0 ? 0 : -1);
            }
        });

        double firstWeight = resUsersSeqByWeigth.get(0).getWeight();

        if (firstWeight != 1) {

            for (ResourceUser user : resUsersSeqByWeigth) {

                user.setWeight(user.getWeight() / firstWeight);

            }

        }

        List<ResourceUser> resUsersSeqByNeeded = getResourceUsersSeq(resUsersSeqByWeigth, new Comparator<ResourceUser>() {
            public int compare(ResourceUser o1, ResourceUser o2) {
                return o1.getNeeded() - o2.getNeeded() > 0 ? 1 : (o1.getNeeded() - o2.getNeeded() == 0 ? 0 : -1);
            }
        });

        Map<String, Double> result = new HashMap<>();


        for (ResourceUser user : resourceUsers) {

            result.put(user.getName(), 0.0);
        }


        allocate(result, maxResource, resUsersSeqByNeeded.toArray(new ResourceUser[resUsersSeqByNeeded.size()]));


        return result;
    }

    private List<ResourceUser> getResourceUsersSeq(List<ResourceUser> resourceUsers, Comparator<ResourceUser> comparator) {

        List<ResourceUser> seqResourceUsers = new ArrayList<>(resourceUsers);

        Collections.sort(seqResourceUsers, comparator);

        return seqResourceUsers;
    }

    private void allocate(Map<String, Double> result, Double memory, ResourceUser... resourceUsersByNeeded) {


        if ( memory <= 0) {
            return;
        }

        int totalWeight = 0;

        for (ResourceUser user : resourceUsersByNeeded) {
            totalWeight += user.getWeight();
        }

        double retainedMemory = 0;
        List<ResourceUser> toBeAllocated = new ArrayList<>();

        for (ResourceUser user : resourceUsersByNeeded) {

            if (result.get(user.getName()) > user.getNeeded()) {

                retainedMemory += result.get(user.getName()) - user.getNeeded();

                result.put(user.getName(), user.getNeeded());
                continue;

            }

            double getMemory = memory * (user.getWeight() / totalWeight);

            if (result.get(user.getName()) + getMemory >= user.getNeeded()) {
                retainedMemory += result.get(user.getName()) + getMemory - user.getNeeded();
                result.put(user.getName(), user.getNeeded());
            } else {
                result.put(user.getName(), result.get(user.getName()) + getMemory);
                toBeAllocated.add(user);
            }


        }

        if (toBeAllocated.size() == 1) {

            if (result.get(toBeAllocated.get(0).getName()) + retainedMemory > toBeAllocated.get(0).getNeeded()) {
                result.put(toBeAllocated.get(0).getName(), toBeAllocated.get(0).getNeeded());
            } else {

                result.put(toBeAllocated.get(0).getName(), result.get(toBeAllocated.get(0).getName()) + retainedMemory);
            }


        } else if (toBeAllocated.size() > 1 && retainedMemory > 0) {

            allocate(result, retainedMemory, toBeAllocated.toArray(new ResourceUser[toBeAllocated.size()]));

        }



    }
}
