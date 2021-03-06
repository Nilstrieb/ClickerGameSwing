import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickerView extends JFrame {
    private JButton nicolasButton;
    private JPanel mainPanel;
    private JPanel thingsPanel;
    private JLabel nicolasLabels;
    private JButton factorUpgradeButton;
    private JLabel npsLabel;

    private ClickerPresenter clickerPresenter;

    public ClickerView() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);

        thingsPanel.setLayout(new BoxLayout(thingsPanel, BoxLayout.Y_AXIS));
        nicolasButton.addActionListener(e -> clickerPresenter.nicolasButtonClick());

        factorUpgradeButton.addActionListener(e -> factorUpgradeButton.setText(clickerPresenter.changeFactor()));
    }

    public void setClickerPresenter(ClickerPresenter clickerPresenter) {
        this.clickerPresenter = clickerPresenter;
    }

    public void addUpgrade(UpgradePanel upgradePanel) {
        thingsPanel.add(upgradePanel);
    }

    public void setNicolasAmount(String amount) {
        nicolasLabels.setText(amount);
    }
    public void setNPSAmount(String amount) {
        npsLabel.setText(amount);
    }
}
