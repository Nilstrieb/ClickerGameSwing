import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;

public class UpgradePanel extends JPanel {

    private JPanel mainPanel;

    private JButton upgradeButton;
    private JLabel nameLabel;
    private JLabel levelLabel;
    private JLabel gainLabel;

    private String name;

    private final BigDecimal costMultiplier;
    private final BigDecimal baseGain;

    private BigDecimal cost;
    private BigDecimal gain = BigDecimal.ZERO;
    private int level;

    private long lastAddedTimeStamp = 0;
    private long lastFrameTimeStamp = System.currentTimeMillis();

    private final ClickerPresenter presenter;
    private final LargeFormatter lf = new LargeFormatter();

    private BigDecimal upgradeCost = BigDecimal.ZERO;

    public UpgradePanel(String name, long baseCost, double costMultiplier, double baseGain, ClickerPresenter presenter) {
        this(name, BigDecimal.valueOf(baseCost), costMultiplier, BigDecimal.valueOf(baseGain), presenter);
    }

    public UpgradePanel(String name, BigDecimal baseCost, double costMultiplier, BigDecimal baseGain, ClickerPresenter presenter) {
        add(mainPanel);

        this.name = name;
        this.cost = baseCost;
        this.costMultiplier = BigDecimal.valueOf(costMultiplier);
        this.baseGain = baseGain;
        this.presenter = presenter;

        this.nameLabel.setText(name);

        upgradeButton.addActionListener(e -> upgrade(presenter.getUpgradeFactor()));
    }

    public void upgrade(int amount) {

        presenter.removeNicolas(calculateExp(cost, costMultiplier, amount));
        gain = gain.add(baseGain.multiply(new BigDecimal(amount)));
        level += amount;

        cost = cost.multiply(costMultiplier.pow(amount));

        recalculateUpgradeButtonText();
    }

    public void refresh() {

        upgradeButton.setEnabled(presenter.getNicolas().compareTo(upgradeCost) >= 0);

        long currentTime = System.currentTimeMillis();
        if (gain.compareTo(BigDecimal.ZERO) > 0) {
            //should nicolas be added?
            double lastAddDeltaTime = currentTime - lastAddedTimeStamp;
            double inverseLastAddDeltaTime = 1 / (lastAddDeltaTime / 1000);

            //gain=2 tpn=0.5 ladt=1000 laps=1
            if (gain.compareTo(BigDecimal.valueOf(inverseLastAddDeltaTime)) > 0) {
                double frameDeltaTime = currentTime - lastFrameTimeStamp;
                BigDecimal inverseFrameDeltaTime = BigDecimal.valueOf(1 / (frameDeltaTime / 1000));
                lastAddedTimeStamp = currentTime;

                if (gain.compareTo(inverseFrameDeltaTime) > 0) {
                    //normal: dt/tpn   inverse: gain/idt
                    BigDecimal missedNicolas = gain.divide(inverseFrameDeltaTime, 0, RoundingMode.HALF_UP); /*frameDeltaTime / timePerNicolas*/
                    presenter.addNicolas(missedNicolas);
                } else {
                    presenter.addNicolas(1);
                }
            }
        }

        lastFrameTimeStamp = currentTime;

        levelLabel.setText("Level: " + level);
        gainLabel.setText(lf.formatBigNumber(gain) + " Nicolas");

        upgradeButton.setText(String.format("%,dx Upgrade: %s Nicolas",
                presenter.getUpgradeFactor(), lf.formatBigNumber(upgradeCost)));


    }

    private BigDecimal calculateExp(BigDecimal c, BigDecimal fac, long amount) {
        //x10 cost = c + c*x + c*x*x + c*x*x*x... = c * x^1 + c*x^2 + c*x^3 + c*x^4 + c*x^5... =
        // c * (x^0 + x^1 + x^2 + x^3...)
        //new cost = c * x^amount
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < amount; i++) {
            result = result.add(fac.pow(i));

        }
        return result.multiply(c);
    }

    public BigDecimal getNPS() {
        return gain;
    }

    public void recalculateUpgradeButtonText() {
        Thread t = new Thread(() -> {
            setUpgradeCost(calculateExp(cost, costMultiplier, presenter.getUpgradeFactor()));
        });
        t.start();
    }

    public void setUpgradeCost(BigDecimal cost) {
        synchronized (this) {
            upgradeCost = cost;
        }
    }
}
