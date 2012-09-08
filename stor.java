package woodcutter;

import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class stor {

    //SceneObjects
    public static final int[]  WILLOW_TREES = {38627, 38616};
    public static final int[] MAPLE_TREES = {51843};

    //Area
    public final static Area AREA_TREES = new Area(
            new Tile(3510, 3606, 0), new Tile(3486, 3641, 0)
    );

    //Items
    public static final int WILLOW_LOGS = 1519;
    public static final int MAPLE_LOGS = 1517;
    public static final int[] AXES = {1351, 1349, 1353, 1355, 1357, 1359, 1361, 6739, 13661};

    //Strings
    public final static String CHOPPED_WILLOW = "You get some willow logs.";
    public final static String CHOPPED_MAPLE = "You get some maple logs.";

    //Animations
    public static final int WOODCUTTING_ANIMATION = 867;

    //Variables
    public static int[] treeType;
    public static int theLogs;
    public static int willowLogsChopped;
    public static int mapleogsChopped;

    //Boolean
    public static boolean guiWait = true;
}
