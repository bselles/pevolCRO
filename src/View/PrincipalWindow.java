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
    private JTextField bestText,worstText,averageText;

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
        nPanel.add(new JLabel("Size (rows):"));
        SpinnerNumberModel nSpinnerModel = new SpinnerNumberModel(16, 1, 100, 1);
        JSpinner nSpinner = new JSpinner(nSpinnerModel);
        nSpinner.setEnabled(true);
        nPanel.add(nSpinner);

        JPanel mPanel = new JPanel();
        mPanel.add(new JLabel("Size (columns):"));
        SpinnerNumberModel mSpinnerModel = new SpinnerNumberModel(16, 1, 100, 1);
        JSpinner mSpinner = new JSpinner(mSpinnerModel);
        mSpinner.setEnabled(true);
        mPanel.add(mSpinner);

        JPanel generation = new JPanel();
        generation.add(new JLabel("Generations"));
        SpinnerNumberModel generationSpinner = new SpinnerNumberModel(100, 1, 1000, 1);
        JSpinner geneSpinner = new JSpinner(generationSpinner);
        geneSpinner.setEnabled(true);
        generation.add(geneSpinner);
        
        
        firstPanel.add(nPanel);
        firstPanel.add(mPanel);
        firstPanel.add(generation);

        
        //JPanel secondPanel = new JPanel();
        //secondPanel.setBorder(BorderFactory.createTitledBorder("Reef"));
        
        JPanel secondPanelA = new JPanel();
        secondPanelA.setBorder(BorderFactory.createTitledBorder("Ratios"));
        JPanel secondPanelB = new JPanel();
        secondPanelB.setBorder(BorderFactory.createTitledBorder("Probabilities"));
        
        JPanel CeldasOcupadas = new JPanel();
        CeldasOcupadas.add(new JLabel("Initial occupation ratio: "));
        SpinnerNumberModel occupationRatio = new SpinnerNumberModel(0.4, 0, 10, 0.05);
        JSpinner occupationSpinner = new JSpinner(occupationRatio);
        occupationSpinner.setEnabled(true);
        CeldasOcupadas.add(occupationSpinner);
        
        JPanel panelRatioExterna = new JPanel();
        panelRatioExterna.add( new JLabel("Extern reprod ratio"));
        SpinnerNumberModel ratioExtInt = new SpinnerNumberModel(0.8, 0.0, 10, 0.05);
        JSpinner extIntSpinner = new JSpinner(ratioExtInt);
        extIntSpinner.setEnabled(true);
        panelRatioExterna.add(extIntSpinner);
        
        JPanel ProbCruce = new JPanel();
        ProbCruce.add(new JLabel("Broadcast cross prob: "));
        SpinnerNumberModel crossProb = new SpinnerNumberModel(0.5, 0, 10, 0.05);
        JSpinner crossProbSpinner = new JSpinner(crossProb);
        mSpinner.setEnabled(true);
        ProbCruce.add(crossProbSpinner);

        JPanel panelProbBrooding = new JPanel();
        panelProbBrooding.add(new JLabel("Brooding mutation prob: "));
        SpinnerNumberModel broodProb = new SpinnerNumberModel(0.5, 0, 10, 0.05);
        JSpinner broodProbSpinner = new JSpinner(broodProb);
        broodProbSpinner.setEnabled(true);
        panelProbBrooding.add(broodProbSpinner);
        
        JPanel panelAsexRepr = new JPanel();
        panelAsexRepr.add(new JLabel("Asexual repr ratio: "));
        SpinnerNumberModel cloneRatio = new SpinnerNumberModel(0.1, 0, 10, 0.02);
        JSpinner asexReprSpinner =  new JSpinner(cloneRatio);
        asexReprSpinner.setEnabled(true);
        panelAsexRepr.add(asexReprSpinner);
        
        JPanel Intentos = new JPanel();
        Intentos.add(new JLabel("Survival attempts: "));
        SpinnerNumberModel survivingAttempts = new SpinnerNumberModel(3, 0, 20, 1);
        JSpinner surviving = new JSpinner(survivingAttempts);
        surviving.setEnabled(true);
        Intentos.add(surviving);

        /*
        secondPanel.add(CeldasOcupadas);
        secondPanel.add(panelRatioExterna);
        secondPanel.add(ProbCruce);
        secondPanel.add(panelProbBrooding);
        secondPanel.add(panelAsexRepr);
        secondPanel.add(Intentos);
        */
        
        secondPanelA.add(CeldasOcupadas);
        secondPanelA.add(panelRatioExterna);
        secondPanelA.add(panelAsexRepr);
        secondPanelB.add(Intentos);
        secondPanelB.add(ProbCruce);
        secondPanelB.add(panelProbBrooding);
        
        
        
        
        //secondPanel.add(secondPanelA);
        //secondPanel.add(secondPanelB);

        
        JPanel thirdPanel = new JPanel();
        thirdPanel.setBorder(BorderFactory.createTitledBorder("Depredation"));

        JPanel depredationsPer = new JPanel();
        depredationsPer.add(new JLabel("Depredation Percentage: "));
        SpinnerNumberModel dePer = new SpinnerNumberModel(0.4, 0, 10, 0.05);
        JSpinner percentage = new JSpinner(dePer);
        percentage.setEnabled(true);
        depredationsPer.add(percentage);

        JPanel depredationsProbability = new JPanel();
        depredationsProbability.add(new JLabel("Depredation Probability: "));
        SpinnerNumberModel probability = new SpinnerNumberModel(0.5, 0, 10, 0.05);
        JSpinner Dprobability = new JSpinner(probability);
        Dprobability.setEnabled(true);
        depredationsProbability.add(Dprobability);

        thirdPanel.add(depredationsPer);
        thirdPanel.add(depredationsProbability);

        JPanel fourthPanel = new JPanel();
        fourthPanel.setBorder(BorderFactory.createTitledBorder("Problem Panel"));

        JPanel numberOfGene = new JPanel();
        numberOfGene.add(new JLabel("Number Of Genes: "));
        SpinnerNumberModel nModelSpinner = new SpinnerNumberModel(3, 1, 7, 1);
        JSpinner nProblem5Spinner = new JSpinner(nModelSpinner);
        nProblem5Spinner.setEnabled(false);
        numberOfGene.add(nProblem5Spinner);

        String[] problems = new String[] {"Problem 1", "Problem 2", "Problem 3", "Problem 4", "Problem 5"};
        JComboBox<String> problemComboBox = new JComboBox<>(problems);
        problemComboBox.addItemListener(e -> nProblem5Spinner.setEnabled("Problem 5".equals(e.getItem())));

        JPanel mutationPanel = new JPanel();
        mutationPanel.setBorder(BorderFactory.createTitledBorder("Mutation Algorithm"));
        String[] mutations = new String[] {"Mutation 1", "Mutation 2"};
        JComboBox<String> mutationComboBox = new JComboBox<>(mutations);
        mutationComboBox.setSelectedIndex(1);
        mutationPanel.add(mutationComboBox);

        JPanel reproductionPanel = new JPanel();
        reproductionPanel.setBorder(BorderFactory.createTitledBorder("Mutation Algorithm"));
        String[] reproductions = new String[] {"Reproduction 1", "Reproduction 2","Reproduction 3","Reproduction 4"};
        JComboBox<String> reproductionComboBox = new JComboBox<>(reproductions);
        reproductionComboBox.setSelectedIndex(3);
        reproductionPanel.add(reproductionComboBox);



        fourthPanel.add(numberOfGene);
        fourthPanel.add(problemComboBox);
        fourthPanel.add(mutationPanel);
        fourthPanel.add(reproductionPanel);
        
        JPanel fifthPanel = new JPanel();
        fifthPanel.setBorder(BorderFactory.createTitledBorder("Results"));

        JPanel best = new JPanel();
        best.add(new JLabel("Best: "));
        bestText = new JTextField();
        bestText.setText("                        ");
        best.add(bestText);
        

        JPanel worst = new JPanel();
        worst.add(new JLabel("Worst: "));   
        worstText = new JTextField();
        worstText.setText("                        ");
        worst.add(worstText);
        
        JPanel average = new JPanel();
        average.add(new JLabel("Average: "));
        averageText = new JTextField();
        averageText.setText("                        ");
        average.add(averageText);

        fifthPanel.add(best);
        fifthPanel.add(worst);
        fifthPanel.add(average);


        panel.add(firstPanel);
        panel.add(secondPanelA);
        panel.add(secondPanelB);
        panel.add(thirdPanel);
        panel.add(fourthPanel);
        panel.add(fifthPanel);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(a -> {
        	
            int n = (int) nSpinner.getValue();
            controller.setRows(n);
            int m = (int) mSpinner.getValue();
            controller.setCols(m);
            cuadrado.setSize(n, m);
            
            int generations = (int) generationSpinner.getValue();
            grafica.init(generations);
            controller.setGenerations(generations);
            
            double ocRatio = (double) occupationRatio.getValue();
            controller.setOccupationRatio(ocRatio);
            
            double exterInter = (double) ratioExtInt.getValue() ; 
            controller.setBroadcastRatio(exterInter);
            
            double cruce = (double) crossProb.getValue();
            controller.setCrossProbability(cruce);
            
            int reproductionType = reproductionComboBox.getSelectedIndex() + 1;

            if (reproductionType < 1 || reproductionType > 4){
                reproductionType = 1; //Reproduction Base
            }
            controller.setCrossType(reproductionType);
            
            double brooding = (double) broodProb.getValue();
            controller.setBroodingProbability(brooding);
            
            int mutationType = mutationComboBox.getSelectedIndex() + 1;
            if (mutationType > 2 || mutationType < 1){
                mutationType = 1;
            }
            controller.setMutationType(mutationType);
            
            
            int survivingAt = (int) survivingAttempts.getValue();
            controller.setSurvivingAttempts(survivingAt);
            
            double clonacion = (double) asexReprSpinner.getValue(); //////////////
            controller.setAsexReprRatio(clonacion);

            double depredatioP = (double) percentage.getValue();
            controller.setDepredationPercentage(depredatioP);
            
            double depredatioPr = (double) Dprobability.getValue();
            controller.setDepredationProbability(depredatioPr);
            
            
            int problemType = problemComboBox.getSelectedIndex() + 1;
            int values = 2;
            if (problemType == 5 ){
                values = (int) nProblem5Spinner.getValue();
            }
            else if (problemType > 5 || problemType < 1){
                problemType = 0;//Problema Base
            }
            controller.setnGenes(values);
            controller.setProblem(problemType);
            
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
                bestText.setText(String.format("%.4f", evaluateValues.bestFitness));
                worstText.setText(String.format("%.4f", evaluateValues.worstFitness));
                averageText.setText(String.format("%.4f", evaluateValues.averageFitness));
            }
        });
    }
}
