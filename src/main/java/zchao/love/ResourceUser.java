package zchao.love;

public class ResourceUser {

    private String name;

    private Double needed;

    private Double weight;

    public ResourceUser() {
    }

    public ResourceUser(String name, Double needed, Double weight) {
        this.name = name;
        this.needed = needed;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNeeded() {
        return needed;
    }

    public void setNeeded(Double needed) {
        this.needed = needed;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
