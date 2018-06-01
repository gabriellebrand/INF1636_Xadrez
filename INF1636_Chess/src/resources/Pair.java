package resources;

public class Pair {
    private Object x;
    private Object y;

    public Pair(Object first, Object second)
    {
        this.x = first;
        this.y = second;
    }

    public Object getFirst() {
        return x;
    }

    public void setFirst(Object x) {
        this.x = x;
    }

    public Object getSecond() {
        return (Object)y;
    }

    public void setSecond(Object y) {
        this.y = y;
    }


}
