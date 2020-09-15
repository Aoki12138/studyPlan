package hust.group20.se.Entity;

public enum Priority {
    //数字越大，优先级越高。
    p1("极高"),
    p2("高"),
    p3("一般"),
    p4("低"),
    p5("极低");

    private String text;

    private Priority(String text){
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static Priority fromString(String text) {
        if (text != null) {
            for (Priority priority : Priority.values()) {
                if (text.equalsIgnoreCase(priority.text)) {
                    return priority;
                }
            }
        }
        return null;
    }
}
