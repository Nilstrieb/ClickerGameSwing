import javax.swing.*;

public class UpgradePanel extends JPanel {

    private JPanel mainPanel;

    private JButton upgradeButton;
    private JLabel nameLabel;
    private JLabel levelLabel;
    private JLabel gainLabel;
    private JButton x10Button;
    private JButton x100Button;

    private String name;

    private final double costMultiplier;
    private final double baseGain;

    private double cost;
    private double gain = 0;
    private int level;

    private long lastAddedTimeStamp = 0;
    private long lastFrameTimeStamp = System.currentTimeMillis();

    private final ClickerPresenter presenter;
    private final LargeFormatter lf = new LargeFormatter();

    public UpgradePanel(String name, double baseCost, double costMultiplier, double baseGain, ClickerPresenter presenter) {
        add(mainPanel);

        this.name = name;
        this.cost = baseCost;
        this.costMultiplier = costMultiplier;
        this.baseGain = baseGain;
        this.presenter = presenter;

        this.nameLabel.setText(name);

        upgradeButton.addActionListener(e -> upgrade(1));
        x10Button.addActionListener(e -> upgrade(10));
        x100Button.addActionListener(e -> upgrade(100));
    }

    public void upgrade(int amount) {

        presenter.removeNicolas(calculateExp(cost, costMultiplier, amount));
        gain += baseGain * amount;
        level += amount;

        cost *= Math.pow(costMultiplier, amount);

        System.err.println(calculateExp(cost, costMultiplier, amount));
/*
        for (int i = 0; i < amount; i++) {
            presenter.removeNicolas(cost);
            gain += baseGain;

            cost = cost * costMultiplier;
            level++;
        }*/
    }

    public void refresh() {
        upgradeButton.setEnabled(presenter.getNicolas() >= cost);
        x10Button.setEnabled(presenter.getNicolas() >= calculateExp(cost, costMultiplier, 10));
        x100Button.setEnabled(presenter.getNicolas() >= calculateExp(cost, costMultiplier, 100));

        //should nicolas be added?
        long currentTime = System.currentTimeMillis();
        long lastAddDeltaTime = currentTime - lastAddedTimeStamp;
        double timePerNicolas = 1 / gain * 1000;

        if (timePerNicolas < lastAddDeltaTime) {
            //add nicolas
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
        gainLabel.setText(lf.formatBigNumber(gain) + " Nicolas");

        upgradeButton.setText("Upgrade:  " + lf.formatBigNumber(cost) + " Nicolas");
        x10Button.setText("x10:  " + lf.formatBigNumber(calculateExp(cost, costMultiplier, 10)) + " Nicolas");
        x100Button.setText("x100:  " + lf.formatBigNumber(calculateExp(cost, costMultiplier, 100)) + " Nicolas");

    }

    private double calculateExp(double c, double fac, double amount) {
        //x10 cost = c + c*x + c*x*x + c*x*x*x... = c * x^1 + c*x^2 + c*x^3 + c*x^4 + c*x^5... =
        // c * (x^0 + x^1 + x^2 + x^3...)
        //new cost = c * x^amount
        double result = 0;
        for (int i = 0; i < amount; i++) {
            result += Math.pow(fac, i);
        }
        return result * c;
    }
}
