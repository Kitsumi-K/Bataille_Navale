package bataille.addon;

public class Position {
    private Integer x;
    private Integer y;

    public Position(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }
    public Position(){
        x = 0;
        y = 0;
    }

    public Integer getX() {
        return x;
    }
    public Integer getY() {
        return y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }
    public void addX(Integer dx){
        x += dx;
    }
    public void addY(Integer dy){
        y += dy;
    }

    @Override
    public String toString() {
        return "x:"+x+",y:"+y;
    }
}
