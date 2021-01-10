import java.math.BigDecimal;
import java.util.ArrayList;

public class ClickerModel {

    private BigDecimal nicolas;
    private final ArrayList<UpgradePanel> upgradePanels;


    public ClickerModel() {
        upgradePanels = new ArrayList<>();
        nicolas = BigDecimal.ZERO;
    }

    public BigDecimal getNicolas() {
        return nicolas;
    }

    public void setNicolas(BigDecimal nicolas) {
        this.nicolas = nicolas;
    }

    public void addUpgrade(UpgradePanel panel) {
        upgradePanels.add(panel);
    }

    public void refresh() {
        upgradePanels.forEach(UpgradePanel::refresh);
    }

    public BigDecimal getNPS() {
        BigDecimal nps = BigDecimal.ZERO;
        for(UpgradePanel p : upgradePanels){
            nps = nps.add(p.getNPS());
        }
        return nps;
    }

    public void addNicolas(long value) {
        addNicolas(BigDecimal.valueOf(value));
    }

    public void addNicolas(BigDecimal value){
        nicolas = nicolas.add(value);
    }

    public void removeNicolas(BigDecimal amount) {
        nicolas = nicolas.subtract(amount);
    }

    public ArrayList<UpgradePanel> getUpgradePanels() {
        return upgradePanels;
    }
}
