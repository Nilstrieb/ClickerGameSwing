import java.util.ArrayList;

public class ClickerModel {

    private double nicolas;
    private final ArrayList<UpgradePanel> upgradePanels;


    public ClickerModel() {
        upgradePanels = new ArrayList<>();
    }

    public double getNicolas() {
        return nicolas;
    }

    public void setNicolas(double nicolas) {
        this.nicolas = nicolas;
    }

    public void addUpgrade(UpgradePanel panel) {
        upgradePanels.add(panel);
    }

    public void refresh() {
        upgradePanels.forEach(UpgradePanel::refresh);
    }

    public double getNPS() {
        double nps = 0;
        for(UpgradePanel p : upgradePanels){
            nps += p.getNPS();
        }
        return nps;
    }
}
