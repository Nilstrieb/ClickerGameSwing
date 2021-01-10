import javax.swing.*;

public class ClickerPresenter {

    public static final int TARGET_FPS = 30;

    private final ClickerView clickerView;
    private final ClickerModel clickerModel;

    private double upgradeFactor = 1;

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


        Timer loop = new Timer(1000 / TARGET_FPS, e -> refresh());
        loop.start();
    }

    private void addPanel(UpgradePanel panel) {
        clickerModel.addUpgrade(panel);
        clickerView.addUpgrade(panel);
    }


    public void nicolasButtonClick() {
        clickerModel.setNicolas(clickerModel.getNicolas() + 1);
    }

    private void refresh() {
        clickerView.setNicolasAmount(formatter.formatBigNumber(clickerModel.getNicolas()) + " Nicolas");
        clickerView.setNPSAmount(formatter.formatBigNumber(clickerModel.getNPS()) + " Nicolas per Second");
        clickerModel.refresh();
    }

    public void removeNicolas(double amount) {
        clickerModel.setNicolas(clickerModel.getNicolas() - amount);
    }

    public double getNicolas() {
        return clickerModel.getNicolas();
    }

    public void addNicolas(double gain) {
        clickerModel.setNicolas(clickerModel.getNicolas() + gain);
    }

    public String changeFactor() {
        upgradeFactor *= 10;
        if (upgradeFactor > 1000) {
            upgradeFactor = 1;
        }
        return String.format("%,.0fx", upgradeFactor);
    }

    public double getUpgradeFactor() {
        return upgradeFactor;
    }

    public static void main(String[] args) {
        new ClickerPresenter(new ClickerView());
    }
}
