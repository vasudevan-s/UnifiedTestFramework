package pro.vasudevan.constants;

/*
Created By: Vasudevan Sampath

 Global.java has global constants
 */
public class Global {
    public static final String REPORTNG_ESCAPE_OUTPUT = "org.uncommons.reportng.escape-output";
    public static final String SCREENSHOTS_FOLDER = "target/screenshots";

    public enum SwipeOptions {
        UP {
            @Override
            public String toString() {
                return "up";
            }
        },
        DOWN {
            @Override
            public String toString() {
                return "down";
            }
        },
        LEFT {
            @Override
            public String toString() {
                return "left";
            }
        },
        RIGHT {
            @Override
            public String toString() {
                return "right";
            }
        }
    }
}
