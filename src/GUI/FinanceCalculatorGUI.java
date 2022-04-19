package GUI;

import Models.Balance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FinanceCalculatorGUI extends JFrame {
    private JPanel mainPanel;

    private JLabel cashFlowLabel;

    private JLabel salesLabel;
    private JTextField salesTextField;

    private JLabel salaryLabel;
    private JTextField salaryTextField;

    private JLabel trainingLabel;
    private JTextField trainingTextField;

    private JLabel advertisingLabel;
    private JTextField advertisingTextField;

    private JLabel officeRentLabel;
    private JTextField officeRentTextField;

    private JLabel OfficeMaintenanceLabel;
    private JTextField officeMaintenanceTextField;

    private JLabel EBITDALabel;
    private JButton EBITDAButton;

    private JLabel deprecationLabel;
    private JTextField deprecationTextField;

    private JButton EBITButton;
    private JLabel EBITLabel;

    private JButton incomeTaxButton;
    private JLabel incomeTaxLabel;

    private JButton CFOButton;
    private JLabel CFOLabel;

    private JLabel CFILabel;
    private JTextField CFITextField;

    private JLabel CFFLabel;
    private JTextField CFFTextField;

    private JButton cumulativeCFButton;
    private JLabel cumulativeCFLabel;

    private JLabel unitsLabel1;
    private JLabel unitsLabel2;
    private JLabel unitsLabel3;
    private JLabel unitsLabel4;
    private JLabel unitsLabel5;

    private JLabel balanceLabel;
    private JTable assetsTable;
    private JTable liabilitiesTable;

    private JLabel assetsSumLabel;
    private JLabel liabilitiesSumLabel;

    private JLabel profitabilityRatiosLabel;

    private JButton ROEButton;
    private JLabel ROELabel;

    private JButton ROSButton;
    private JLabel ROSLabel;

    private JButton RonEBITDAButton;
    private JLabel RonEBITDALabel;

    private JButton RonEBITButton;
    private JLabel RonEBITLabel;

    private JButton reportButton;

    private JButton exitButton;

    private JLabel iconLabel;

    public FinanceCalculatorGUI(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        loadBalanceTables();  // Загрузка таблиц "актив" "пассив"

        /* Закрытие окна */
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        /* Вычисление EBITDA */
        EBITDAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double sales = Double.parseDouble(salesTextField.getText());
                    double salary = Double.parseDouble(salaryTextField.getText());
                    double training = Double.parseDouble(trainingTextField.getText());
                    double advertising = Double.parseDouble(advertisingTextField.getText());
                    double officeRent = Double.parseDouble(officeRentTextField.getText());
                    double officeMaintenance = Double.parseDouble(officeMaintenanceTextField.getText());

                    double EBITDA = sales - salary - training - advertising - officeRent - officeMaintenance;

                    EBITDALabel.setText(String.valueOf(EBITDA));
                } catch (NumberFormatException exception) {
                    showErrorMessage();
                }
            }
        });

        /* Вычисление EBIT */
        EBITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double EBITDA = Double.parseDouble(EBITDALabel.getText());
                    double deprecation = Double.parseDouble(deprecationTextField.getText());

                    double EBIT = EBITDA - deprecation;

                    EBITLabel.setText(String.valueOf(EBIT));
                } catch (NumberFormatException exception) {
                    showErrorMessage();
                }
            }
        });

        /* Вычисление налога на прибыль */
        incomeTaxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double EBIT = Double.parseDouble(EBITLabel.getText());
                    double tax = EBIT * 0.2;

                    incomeTaxLabel.setText(String.valueOf(tax));
                } catch (NumberFormatException exception) {
                    showErrorMessage();
                }
            }
        });

        /* Вычисление операционного ден. потока */
        CFOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double EBIT = Double.parseDouble(EBITLabel.getText());
                    double tax = Double.parseDouble(incomeTaxLabel.getText());

                    double CFO = EBIT - tax;

                    CFOLabel.setText(String.valueOf(CFO));
                } catch (NumberFormatException exception) {
                    showErrorMessage();
                }
            }
        });

        /* Вычисление совокупного денежного потока */
        cumulativeCFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double CFO = Double.parseDouble(CFOLabel.getText());
                    double CFI = Double.parseDouble(CFITextField.getText());
                    double CFF = Double.parseDouble(CFFTextField.getText());

                    double cumulativeCF = CFO + CFI + CFF;

                    cumulativeCFLabel.setText(String.valueOf(cumulativeCF));
                } catch (NumberFormatException exception) {
                    showErrorMessage();
                }
            }
        });

        /* Вычисление коэффициента рентабельности собственного капитала */
        ROEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double CFO = Double.parseDouble(CFOLabel.getText());
                    double liabilitiesSum = Double.parseDouble(liabilitiesSumLabel.getText());

                    double ROE = CFO / liabilitiesSum * 100;

                    ROELabel.setText(String.format("%.2f %%", ROE));
                } catch (NumberFormatException exception) {
                    showErrorMessage();
                }
            }
        });

        /* Вычисление коэффициента рентабельности продаж */
        ROSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double CFO = Double.parseDouble(CFOLabel.getText());
                    double sales = Double.parseDouble(salesTextField.getText());

                    double ROS = CFO / sales * 100;

                    ROSLabel.setText(String.format("%.2f %%", ROS));
                } catch (NumberFormatException exception) {
                    showErrorMessage();
                }
            }
        });

        /* Вычисление коэффициента рентабельности по EBITDA */
        RonEBITDAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double EBITDA = Double.parseDouble(EBITDALabel.getText());
                    double sales = Double.parseDouble(salesTextField.getText());

                    double marginEBTDA = EBITDA / sales * 100;

                    RonEBITDALabel.setText(String.format("%.2f %%", marginEBTDA));
                } catch (NumberFormatException exception) {
                    showErrorMessage();
                }
            }
        });

        /* Вычисление коэффициента рентабельности по EBIT */
        RonEBITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double EBIT = Double.parseDouble(EBITLabel.getText());
                    double sales = Double.parseDouble(salesTextField.getText());

                    double marginEBIT = EBIT / sales * 100;

                    RonEBITLabel.setText(String.format("%.2f %%", marginEBIT));
                } catch (NumberFormatException exception) {
                    showErrorMessage();
                }
            }
        });

        /* Получение итогового отчета */
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReport();
            }
        });
    }

    public static void main(String[] args) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        ImageIcon appIcon = new ImageIcon("./src/Resources/accounts.png");

        JFrame jFrame = new FinanceCalculatorGUI("Простой калькулятор отчетности");
        jFrame.setBounds(dimension.width / 2 - 500, dimension.height / 2 - 400, 1000, 800);
        jFrame.setIconImage(appIcon.getImage());
        jFrame.setVisible(true);
    }

    private void showErrorMessage() {
        JOptionPane.showMessageDialog(mainPanel,
                "Введите корректные данные в поля",
                "Попробуйте снова",
                JOptionPane.ERROR_MESSAGE);
    }

    private void createReport() {
        String cumulativeCF = cumulativeCFLabel.getText();
        String EBITDA = EBITDALabel.getText();
        String EBIT = EBITLabel.getText();
        String ROE = ROELabel.getText();
        String ROS = ROSLabel.getText();
        String marginEBITDA = RonEBITDALabel.getText();
        String marginEBIT = RonEBITLabel.getText();

        String message = String.format("""
                        Итоговый денежный поток:\s

                            CF = %s тыс. руб\s 

                        Промежуточные результаты деятельности:\s

                            EBITDA = %s тыс. руб\s 

                            EBIT = %s тыс. руб\s 

                        Коэффициенты рентабельности:\s

                            ROE = %s %%\s 

                            ROS = %s %%\s

                            Рентабельность по EBITDA = %s %%\s

                            Рентабельность по EBIT = %s %%\s
                        """,
                cumulativeCF, EBITDA, EBIT, ROE, ROS, marginEBITDA, marginEBIT);

        JOptionPane.showMessageDialog(mainPanel,
                message,
                "Итоговый отчет",
                JOptionPane.INFORMATION_MESSAGE);

        try (PrintWriter printWriter = new PrintWriter("./src/Reports/Report.txt")) {
            printWriter.println("**********************************");
            printWriter.println(message);
            printWriter.println("**********************************");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(mainPanel,
                    "Файл не найден!",
                    "Попробуйте снова",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBalanceTables() {
        Balance balance = new Balance();

        String[][] assetsData = balance.getAssetsAsStringArray();
        String[][] liabilitiesData = balance.getLiabilitiesAsStringArray();

        assetsTable.setModel(new DefaultTableModel(
                assetsData,
                new String[] {"Актив", "тыс. руб"}
        ));

        liabilitiesTable.setModel(new DefaultTableModel(
                liabilitiesData,
                new String[] {"Пассив", "тыс. руб"}
        ));

        double assetsSum = balance.getAssetsSum();
        double liabilitiesSum = balance.getLiabilitiesSum();

        assetsSumLabel.setText(String.valueOf(assetsSum));
        liabilitiesSumLabel.setText(String.valueOf(liabilitiesSum));
    }
}
