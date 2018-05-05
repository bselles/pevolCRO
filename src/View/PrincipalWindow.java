package View;

import Controller.Controller;
import Controller.EvaluateValues;
import Population.Chromosome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class PrincipalWindow extends JFrame implements Controller.Callback {

    private Controller controller;
    private PlotPanel grafica;
    private RectBoardGameView cuadrado;

    public PrincipalWindow(Controller controller) {
        this.controller = controller;
        controller.setCallback(this);

        initGUI();
    }

    public void initGUI() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int option = JOptionPane.showConfirmDialog(null,
                        "Quieres salir de la ventana?",
                        "Salir",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        setLayout(new BorderLayout());

        JPanel leftPanel = crearPanelIzquierdo();
        JPanel rigthPanel = crearPanelDerecho();
        add(leftPanel, BorderLayout.LINE_START);
        add(rigthPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        grafica = new PlotPanel();
        cuadrado = new RectBoardGameView();

        panel.add(grafica);
        panel.add(cuadrado);
        return panel;
    }

    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JPanel firstPanel = new JPanel();
        firstPanel.setBorder(BorderFactory.createTitledBorder("Matrix"));

        JPanel nPanel = new JPanel();
        nPanel.add(new JLabel("Size (row):"));
        SpinnerNumberModel nSpinnerModel = new SpinnerNumberModel(10, 1, 100, 1);
        JSpinner nSpinner = new JSpinner(nSpinnerModel);
        nSpinner.setEnabled(true);
        nPanel.add(nSpinner);

        JPanel mPanel = new JPanel();
        mPanel.add(new JLabel("Size (column):"));
        SpinnerNumberModel mSpinnerModel = new SpinnerNumberModel(10, 1, 100, 1);
        JSpinner mSpinner = new JSpinner(mSpinnerModel);
        mSpinner.setEnabled(true);
        mPanel.add(mSpinner);

        JPanel generation = new JPanel();
        generation.add(new JLabel("Generation"));
        SpinnerNumberModel generationSpinner = new SpinnerNumberModel(100, 1, 1000, 1);
        JSpinner geneSpinner = new JSpinner(generationSpinner);
        mSpinner.setEnabled(true);
        generation.add(geneSpinner);

        firstPanel.add(nPanel);
        firstPanel.add(mPanel);
        firstPanel.add(generation);

        JPanel secondPanel = new JPanel();
        secondPanel.setBorder(BorderFactory.createTitledBorder("Riff"));

        JPanel CeldasOcuppadas = new JPanel();
        CeldasOcuppadas.add(new JLabel("Celdas iniciales ocupadas: "));
        SpinnerNumberModel occupationRatio = new SpinnerNumberModel(0.32, 0, 1, 0.1);
        JSpinner occupationSpinner = new JSpinner(occupationRatio);
        occupationSpinner.setEnabled(true);
        CeldasOcuppadas.add(occupationSpinner);

        JPanel Intentos = new JPanel();
        Intentos.add(new JLabel("Intentos de supervivencia: "));
        SpinnerNumberModel survivingAttempts = new SpinnerNumberModel(3, 0, 100, 1);
        JSpinner surviving = new JSpinner(survivingAttempts);
        mSpinner.setEnabled(true);
        Intentos.add(surviving);

        JPanel iRreproduction = new JPanel();
        iRreproduction.add(new JLabel("Intentos de reproduccion: "));
        SpinnerNumberModel iReproduction = new SpinnerNumberModel(0.5, 0, 1, 0.1);
        JSpinner iReproductionSpinner = new JSpinner(iReproduction);
        mSpinner.setEnabled(true);
        iRreproduction.add(iReproductionSpinner);

        JPanel panelBrooding = new JPanel();
        panelBrooding.add(new JLabel("Ind. Reproduccion sexual externa: "));
        SpinnerNumberModel modelBrooding = new SpinnerNumberModel(0.5, 0, 1, 0.1);
        JSpinner spinnerBroding = new JSpinner(modelBrooding);
        spinnerBroding.setEnabled(true);
        panelBrooding.add(spinnerBroding);

        secondPanel.add(CeldasOcuppadas);
        secondPanel.add(Intentos);
        secondPanel.add(iRreproduction);
        secondPanel.add(panelBrooding);

        JPanel thridPanel = new JPanel();
        thridPanel.setBorder(BorderFactory.createTitledBorder("Depredation"));

        JPanel depredationsPer = new JPanel();
        depredationsPer.add(new JLabel("Depredation Percentage: "));
        SpinnerNumberModel dePer = new SpinnerNumberModel(0.4, 0, 1, 0.1);
        JSpinner percentage = new JSpinner(dePer);
        percentage.setEnabled(true);
        depredationsPer.add(percentage);

        JPanel depredationsProbability = new JPanel();
        depredationsProbability.add(new JLabel("Depredation Probability: "));
        SpinnerNumberModel probability = new SpinnerNumberModel(0.5, 0, 1, 0.1);
        JSpinner Dprobability = new JSpinner(probability);
        Dprobability.setEnabled(true);
        depredationsProbability.add(Dprobability);

        thridPanel.add(depredationsPer);
        thridPanel.add(depredationsProbability);

        JPanel fourthPanel = new JPanel();
        fourthPanel.setBorder(BorderFactory.createTitledBorder("Problem Panel"));

        JPanel numberOfGene = new JPanel();
        numberOfGene.add(new JLabel("Number Of Genes: "));
        SpinnerNumberModel nModelSpinner = new SpinnerNumberModel(2, 1, 7, 1);
        JSpinner nProblem5Spinner = new JSpinner(nModelSpinner);
        nProblem5Spinner.setEnabled(false);
        numberOfGene.add(nProblem5Spinner);

        String[] problems = new String[] {"Problem 1", "Problem 2", "Problem 3", "Problem 4", "Problem 5"};
        JComboBox<String> problemComboBox = new JComboBox<>(problems);
        problemComboBox.addItemListener(e -> numberOfGene.setEnabled("Problem 5".equals(e.getItem())));

        JPanel mutationPanel = new JPanel();
        mutationPanel.setBorder(BorderFactory.createTitledBorder("Mutation Algorithm"));
        String[] mutations = new String[] {"Mutation 1", "Mutation 2"};
        JComboBox<String> mutationComboBox = new JComboBox<>(mutations);
        mutationPanel.add(mutationComboBox);

        JPanel reproductionPanel = new JPanel();
        reproductionPanel.setBorder(BorderFactory.createTitledBorder("Mutation Algorithm"));
        String[] reproductions = new String[] {"Reproduction 1", "Reproduction 2","Reproduction 3","Reproduction 4"};
        JComboBox<String> reproductionComboBox = new JComboBox<>(reproductions);
        reproductionPanel.add(reproductionComboBox);





        fourthPanel.add(numberOfGene);
        fourthPanel.add(problemComboBox);
        fourthPanel.add(mutationPanel);
        fourthPanel.add(reproductionPanel);


        panel.add(firstPanel);
        panel.add(secondPanel);
        panel.add(thridPanel);
        panel.add(fourthPanel);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(a -> {
            int n = (int) nSpinner.getValue();
            controller.setN(n);
            int m = (int) mSpinner.getValue();
            controller.setM(m);
            cuadrado.setSize(n, m);
            int genertions = (int) generationSpinner.getValue();
            grafica.init(genertions);
            controller.setGenerations(genertions);
            double ocRatio = (double) occupationRatio.getValue();
            controller.setOccupationRatio(ocRatio);
            int survivingAt = (int) survivingAttempts.getValue();
            controller.setSurvivingAttempts(survivingAt);
            double arRatio = (double) iReproductionSpinner.getValue();
            controller.setArRatio(arRatio);
            double depredatioP = (double) percentage.getValue();
            controller.setDepredationPercentage(depredatioP);
            double depredatioPr = (double) Dprobability.getValue();
            controller.setDepredationProbability(depredatioPr);
            double brooding = (double) spinnerBroding.getValue();
            controller.setBroodingRatio(brooding);

            int mutationType = mutationComboBox.getSelectedIndex() + 1;
            if (mutationType > 2 || mutationType < 1){
                mutationType = 1;
            }
            controller.setMutationType(mutationType);
            int problemType = problemComboBox.getSelectedIndex() + 1;
            int values = 2;
            if (problemType == 5 ){
                values = (int) nProblem5Spinner.getValue();
            }
            else if (problemType > 5 || problemType < 1){
                problemType = 0;//Problema Base
            }
            controller.setProblem(problemType);
            controller.setn(values);
            int reproductionType = reproductionComboBox.getSelectedIndex();

            if (reproductionType < 0 || reproductionType > 4){
                reproductionType = 0; //Reproduction Base
            }
            controller.setCrossType(reproductionType);
            controller.realizarCalculos();
        });
        panel.add(okButton);
        return panel;
    }

    @Override
    public void onGenerationOver(int generation, EvaluateValues evaluateValues, List<Chromosome> population) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                grafica.update(generation, evaluateValues);
                cuadrado.setPopulation(population);
            }
        });
    }
}