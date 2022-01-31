public class Square {
    private boolean set = false;
    private int groupId;
    private int value;

    public int getId() {
        return groupId;
    }

    public void setId(int x) {
        groupId = x;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int x) {
        set = true;
        value = x;
    }

    public boolean isSet() {
        return set;
    }

    public void setSet(boolean x) {
        set = x;
    }
}
