package resources;

public class Pair {
    private Object x;
    private Object y;

    public Pair(String first, String second)
    {
        this.x = first;
        this.y = second;
    }

    public String getFirst() {
        return (String)x;
    }

    public void setFirst(String x) {
        this.x = x;
    }

    public String getSecond() {
        return (String)y;
    }

    public void setSecond(String y) {
        this.y = y;
    }


}
