import javax.swing.*;

public class ClickerView extends JFrame {
    private JButton nicolasButton;
    private JPanel mainPanel;
    private JPanel thingsPanel;
    private JLabel nicolasLabels;

    private ClickerPresenter clickerPresenter;

    public ClickerView() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);

        thingsPanel.setLayout(new BoxLayout(thingsPanel, BoxLayout.Y_AXIS));
        nicolasButton.addActionListener(e -> clickerPresenter.nicolasButtonClick());
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
}
