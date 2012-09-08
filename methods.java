package woodcutter;

import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class methods {

    public static boolean DoesHaveAxe() {
        for(int i : stor.AXES){
            if (!(Players.getLocal().getAppearance()[3] == i)) {
                return true;
            }
            if(Inventory.getCount(i) > 0) {
                return true;
            }
        }
        return false;
    }
    public static Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch(IOException e) {
            return null;
        }
    }
}
