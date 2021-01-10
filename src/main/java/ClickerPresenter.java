import javax.swing.*;
import java.math.BigDecimal;

public class ClickerPresenter {

    public static final int TARGET_FPS = 30;

    public static final int MAX_UPGRADE_FACTOR = 1000;

    private final ClickerView clickerView;
    private final ClickerModel clickerModel;

    private int upgradeFactor = 1;

    LargeFormatter formatter = new LargeFormatter();

    public ClickerPresenter(ClickerView clickerView) {
        this.clickerModel = new ClickerModel();
        this.clickerView = clickerView;
        this.clickerView.setClickerPresenter(this);

        addPanel(new UpgradePanel("Illision", 10, 1.05, 0.1, this));
        addPanel(new UpgradePanel("Klonmaschine", 100, 1.1, 1, this));
        addPanel(new UpgradePanel("Mysteriöse Höhle", 500, 1.1, 10, this));
        addPanel(new UpgradePanel("Massenfertigungsanstalt", 3000, 1.1, 150, this));
        addPanel(new UpgradePanel("Herblingen", 100000, 1.1, 6969, this));
        addPanel(new UpgradePanel("Sihlcity", 1000000, 1.1, 50000, this));

        addPanel(new UpgradePanel("Debugger", 1, 1, 1000000000, this));

        clickerModel.getUpgradePanels().forEach(UpgradePanel::recalculateUpgradeButtonText);
        Timer loop = new Timer(1000 / TARGET_FPS, e -> refresh());
        loop.start();
    }

    private void addPanel(UpgradePanel panel) {
        clickerModel.addUpgrade(panel);
        clickerView.addUpgrade(panel);
    }


    public void nicolasButtonClick() {
        clickerModel.addNicolas(1);
    }

    private void refresh() {
        clickerView.setNicolasAmount(formatter.formatBigNumber(clickerModel.getNicolas()) + " Nicolas");
        clickerView.setNPSAmount(formatter.formatBigNumber(clickerModel.getNPS()) + " Nicolas per Second");
        clickerModel.refresh();
    }

    public void removeNicolas(BigDecimal amount) {
        clickerModel.removeNicolas(amount);
    }

    public BigDecimal getNicolas() {
        return clickerModel.getNicolas();
    }

    public void addNicolas(long value) {
        addNicolas(BigDecimal.valueOf(value));
    }

    public void addNicolas(BigDecimal gain) {
        clickerModel.addNicolas(gain);
    }

    public String changeFactor() {
        upgradeFactor *= 10;
        if (upgradeFactor > MAX_UPGRADE_FACTOR) {
            upgradeFactor = 1;
        }
        clickerModel.getUpgradePanels().forEach(UpgradePanel::recalculateUpgradeButtonText);
        return String.format("%,dx", upgradeFactor);
    }

    public int getUpgradeFactor() {
        return upgradeFactor;
    }

    public static void main(String[] args) {
        new ClickerPresenter(new ClickerView());
    }
}
