package woodcutter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.bot.event.listener.PaintListener;

@Manifest(authors = { "Cant Defence" }, name = "CantChopTrees", description = "Chops trees on Daemonhiem island", version = 0.1, website = "")

public class CantChopWood extends ActiveScript implements PaintListener{

    CantChopWoodGui g = new CantChopWoodGui();

    @Override
    protected void setup() {

        g.setVisible(true);

        provide(new Chop());
        provide(new Drop());
    }

    private final Color color1 = new Color(51, 255, 0);
    private final Font font1 = new Font("Arial", 0, 15);
    private final Image img1 = methods.getImage("http://img577.imageshack.us/img577/4360/newmouse.png");

    @Override
    public void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;
        g.drawImage(img1, Mouse.getX(), Mouse.getY(), null);
        g.setFont(font1);
        g.setColor(color1);
    }

    private static class Chop extends Strategy implements Runnable {

        @Override
        public void run() {
           SceneObject tree = SceneEntities.getNearest(stor.treeType);
            if(tree.isOnScreen()) {
                tree.interact("Chop down");
                Time.sleep(1000, 2000);
            }  else {
               if(tree.getLocation().distance(Players.getLocal()) > 7) {
                   Walking.walk(tree);
                   Time.sleep(1200, 2400);
               } else {
                   Camera.turnTo(tree);
                   Time.sleep(400, 800);
               }
            }
        }

        @Override
        public boolean validate() {
            SceneObject tree = SceneEntities.getNearest(stor.treeType);
            return methods.DoesHaveAxe() && Players.getLocal().getAnimation() == -1 && !Inventory.isFull()
                    && !Players.getLocal().isMoving() && stor.AREA_TREES.contains(Players.getLocal())
                    && !stor.guiWait && tree != null;
        }
    }

    private static class Drop extends Strategy implements Runnable {

        @Override
        public void run() {
           setLock(true);
            for(Item i : Inventory.getItems()) {
                    if(!i.equals(Inventory.getItem(stor.AXES))) {
                        i.getWidgetChild().interact("Drop");
                        Time.sleep(50, 100);
                }
            }
        }

        @Override
        public boolean validate() {
            return Inventory.isFull()
                    && !stor.guiWait;
        }
    }


    public class CantChopWoodGui extends JFrame {
        public CantChopWoodGui() {
            initComponents();
        }

        private void button1ActionPerformed(ActionEvent e) {
            String chosen = comboBox1.getSelectedItem().toString();
            if(chosen.equals("Willows")) {
                stor.theLogs = stor.WILLOW_LOGS;
                stor.treeType = stor.WILLOW_TREES;
            }  else {
                stor.theLogs = stor.MAPLE_LOGS;
                stor.treeType = stor.MAPLE_TREES;
            }
                g.dispose();
            stor.guiWait = false;
        }

        private void initComponents() {
            label1 = new JLabel();
            label2 = new JLabel();
            comboBox1 = new JComboBox();
            button1 = new JButton();

            //======== this ========
            Container contentPane = getContentPane();

            //---- label1 ----
            label1.setText("CantChopTrees");
            label1.setFont(new Font("Times New Roman", Font.PLAIN, 20));

            //---- label2 ----
            label2.setText("Select Tree Type: ");

            //---- comboBox1 ----
            comboBox1.setModel(new DefaultComboBoxModel(new String[] {
                    "Willows",
                    "Maples"
            }));

            //---- button1 ----
            button1.setText("Start");
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button1ActionPerformed(e);
                }
            });
            GroupLayout contentPaneLayout = new GroupLayout(contentPane);
            contentPane.setLayout(contentPaneLayout);
            contentPaneLayout.setHorizontalGroup(
                    contentPaneLayout.createParallelGroup()
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                            .addGroup(contentPaneLayout.createSequentialGroup()
                                                    .addGroup(contentPaneLayout.createParallelGroup()
                                                            .addGroup(contentPaneLayout.createSequentialGroup()
                                                                    .addContainerGap()
                                                                    .addComponent(label2)
                                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(contentPaneLayout.createSequentialGroup()
                                                                    .addGap(26, 26, 26)
                                                                    .addComponent(label1)))
                                                    .addGap(0, 2, Short.MAX_VALUE))
                                            .addGroup(contentPaneLayout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(button1, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)))
                                    .addContainerGap())
            );
            contentPaneLayout.setVerticalGroup(
                    contentPaneLayout.createParallelGroup()
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                    .addGap(8, 8, 8)
                                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label2)
                                            .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button1)
                                    .addContainerGap(2, Short.MAX_VALUE))
            );
            pack();
            setLocationRelativeTo(getOwner());
        }
        private JLabel label1;
        private JLabel label2;
        private JComboBox<String> comboBox1;
        private JButton button1;
    }
}
