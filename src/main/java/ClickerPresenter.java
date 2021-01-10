import javax.swing.*;
import java.util.ArrayList;

public class ClickerPresenter {

    public static final int TARGET_FPS = 30;

    private final ClickerView clickerView;
    private final ClickerModel clickerModel;
    private final ArrayList<UpgradePanel> upgradePanels;

    LargeFormatter formatter = new LargeFormatter();

    private final Timer loop;

    public ClickerPresenter(ClickerView clickerView) {
        this.clickerView = clickerView;
        this.clickerView.setClickerPresenter(this);

        UpgradePanel illusion = new UpgradePanel("Illision", 10, 1.05, 0.1, this);
        UpgradePanel cloneMachine = new UpgradePanel("Klonmaschine", 100, 1.1, 1, this);
        UpgradePanel mysteriousCave = new UpgradePanel("Mysteriöse Höhle", 500, 1.1, 10, this);
        UpgradePanel factory = new UpgradePanel("Massenfertigungsanstalt", 3000, 1.1, 150, this);
        UpgradePanel herblingen = new UpgradePanel("Herblingen", 100000, 1.1, 6969, this);
        UpgradePanel sihlcity = new UpgradePanel("Sihlcity", 1000000, 1.1, 50000, this);


        UpgradePanel debugger = new UpgradePanel("Debugger", 1, 1, 1000000000, this);
        clickerView.addUpgrade(illusion);
        clickerView.addUpgrade(cloneMachine);
        clickerView.addUpgrade(mysteriousCave);
        clickerView.addUpgrade(factory);
        clickerView.addUpgrade(herblingen);
        clickerView.addUpgrade(sihlcity);
        clickerView.addUpgrade(debugger);

        upgradePanels = new ArrayList<>();
        upgradePanels.add(illusion);
        upgradePanels.add(cloneMachine);
        upgradePanels.add(mysteriousCave);
        upgradePanels.add(factory);
        upgradePanels.add(herblingen);
        upgradePanels.add(sihlcity);
        upgradePanels.add(debugger);

        clickerModel = new ClickerModel();

        loop = new Timer(1000 / TARGET_FPS, e -> refresh());
        loop.start();
    }

    public static void main(String[] args) {
        new ClickerPresenter(new ClickerView());
    }

    public void nicolasButtonClick() {
        clickerModel.setNicolas(clickerModel.getNicolas() + 1);
    }

    private void refresh() {
        clickerView.setNicolasAmount(formatter.formatBigNumber(clickerModel.getNicolas()) + " Nicolas");
        upgradePanels.forEach(UpgradePanel::refresh);
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
}
