import javax.swing.*;

public class UpgradePanel extends JPanel {

    private JPanel mainPanel;

    private JButton upgradeButton;
    private JLabel nameLabel;
    private JLabel levelLabel;
    private JLabel gainLabel;

    private String name;

    private final double costMultiplier;
    private final double baseGain;

    private double cost;
    private double gain = 0;
    private int level;

    private long lastAddedTimeStamp = 0;
    private long lastFrameTimeStamp = System.currentTimeMillis();

    private final ClickerPresenter presenter;

    public UpgradePanel(String name, double baseCost, double costMultiplier, double baseGain, ClickerPresenter presenter) {
        add(mainPanel);

        this.name = name;
        this.cost = baseCost;
        this.costMultiplier = costMultiplier;
        this.baseGain = baseGain;
        this.presenter = presenter;

        this.nameLabel.setText(name);

        upgradeButton.addActionListener(e -> upgrade(1));
    }

    public void upgrade(int amount) {
        for (int i = 0; i < amount; i++) {
            presenter.removeNicolas(cost);
            gain += baseGain;

            cost = cost * costMultiplier;
            level++;
        }
    }

    public void refresh() {
        upgradeButton.setEnabled(presenter.getNicolas() >= cost);

        //should nicolas be added?
        long currentTime = System.currentTimeMillis();
        long lastAddDeltaTime = currentTime - lastAddedTimeStamp;
        double timePerNicolas = 1 / gain * 1000;

        if (timePerNicolas < lastAddDeltaTime) {
            long frameDeltaTime = currentTime - lastFrameTimeStamp;

            System.out.printf("tpn=%.2f ladt=%d Δt=%d", timePerNicolas, lastAddDeltaTime, frameDeltaTime);
            lastAddedTimeStamp = currentTime;


            if (timePerNicolas < frameDeltaTime) {
                System.out.printf(" FN");
                double missedNicolas = frameDeltaTime / timePerNicolas;
                System.out.printf(" mn=%.2f gain=%.2f add=%.2f", missedNicolas, gain, missedNicolas);
                presenter.addNicolas(missedNicolas);
            } else {
                presenter.addNicolas(1);
            }

            System.out.print("\n");
        }
        lastFrameTimeStamp = currentTime;

        levelLabel.setText("Level: " + level);
        if(gain < 10){
            gainLabel.setText(String.format("%.2f  Nicolas", gain));
        } else {
            gainLabel.setText(String.format("%.0f  Nicolas", gain));
        }
        upgradeButton.setText(String.format("Upgrade:  %.0f Nicolas", cost));

    }
}
