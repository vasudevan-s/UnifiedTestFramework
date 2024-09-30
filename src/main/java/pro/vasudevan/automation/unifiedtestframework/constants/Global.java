package pro.vasudevan.automation.unifiedtestframework.constants;

/*
Created By: Vasudevan Sampath

 Global.java has global constants
 */
public class Global {

    public static final String iOS = "xcuitest";
    public static final String Android_UIAutomator = "UiAutomator2";
    public static final String REPORTNG_ESCAPE_OUTPUT = "org.uncommons.reportng.escape-output";
    public static final String SCREENSHOTS_FOLDER = "target/screenshots";

    public enum SwipeScrollDirection {
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

    public enum FindType {PropertyKey, SimpleTextDelimiterSeparated}

    public enum SwipeScrollPercentage{
        PERCENT25(25),
        PERCENT50(50),
        PERCENT75(75),
        PERCENT100(100);

        private final int value;

        SwipeScrollPercentage(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
    }
}
