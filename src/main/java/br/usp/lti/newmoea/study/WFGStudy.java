package br.usp.lti.newmoea.study;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.agemoeaii.AGEMOEAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.espea.ESPEABuilder;
import org.uma.jmetal.algorithm.multiobjective.gwasfga.GWASFGA;
import org.uma.jmetal.algorithm.multiobjective.moead.AbstractMOEAD;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder;
import org.uma.jmetal.lab.experiment.Experiment;
import org.uma.jmetal.lab.experiment.ExperimentBuilder;
import org.uma.jmetal.lab.experiment.component.impl.ComputeQualityIndicators;
import org.uma.jmetal.lab.experiment.component.impl.ExecuteAlgorithms;
import org.uma.jmetal.lab.experiment.component.impl.GenerateBoxplotsWithR;
import org.uma.jmetal.lab.experiment.component.impl.GenerateFriedmanTestTables;
import org.uma.jmetal.lab.experiment.component.impl.GenerateLatexTablesWithStatistics;
import org.uma.jmetal.lab.experiment.component.impl.GenerateWilcoxonTestTablesWithR;
import org.uma.jmetal.lab.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.lab.experiment.util.ExperimentProblem;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ2;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ3;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ4;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ5;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ6;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ7;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.problem.multiobjective.wfg.WFG2;
import org.uma.jmetal.problem.multiobjective.wfg.WFG3;
import org.uma.jmetal.problem.multiobjective.wfg.WFG4;
import org.uma.jmetal.problem.multiobjective.wfg.WFG5;
import org.uma.jmetal.problem.multiobjective.wfg.WFG6;
import org.uma.jmetal.problem.multiobjective.wfg.WFG7;
import org.uma.jmetal.problem.multiobjective.wfg.WFG8;
import org.uma.jmetal.problem.multiobjective.wfg.WFG9;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistancePlus;
import org.uma.jmetal.qualityindicator.impl.hypervolume.impl.PISAHypervolume;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

/**
 * Example of experimental study based on solving the problems (configured with
 * 3 objectives) with the algorithms NSGAII, SPEA2, and SMPSO
 * <p>
 * This org.uma.jmetal.experiment assumes that the reference Pareto front are
 * known and stored in files whose names are different from the default name
 * expected for every problem. While the default would be "problem_name.pf"
 * (e.g. DTLZ1.pf), the references are stored in files following the
 * nomenclature "problem_name.3D.pf" (e.g. DTLZ1.3D.pf). This is indicated when
 * creating the ExperimentProblem instance of each of the evaluated poblems by
 * using the method changeReferenceFrontTo()
 * <p>
 * Six quality indicators are used for performance assessment.
 * <p>
 * The steps to carry out the org.uma.jmetal.experiment are: 1. Configure the
 * org.uma.jmetal.experiment 2. Execute the algorithms 3. Compute que quality
 * indicators 4. Generate Latex tables reporting means and medians 5. Generate R
 * scripts to produce latex tables with the result of applying the Wilcoxon Rank
 * Sum Test 6. Generate Latex tables with the ranking obtained by applying the
 * Friedman test 7. Generate R scripts to obtain boxplots
 */
public class WFGStudy {

    private static final int INDEPENDENT_RUNS = 5;
    public static final int L = 20;
    public static final int[] M = {2, 3, 5};
    //int[] M = {2, 3, 5, 8, 10};
    public static final String experimentBaseDirectory = "/Users/viniciusdecarvalho/PJT/NewMOEA";
    public static final String StudyName = "WFGStudy";
    public static double epsilon = 0.01;

    public static void main(String[] args) throws IOException {
        Files.createDirectories(Paths.get(experimentBaseDirectory + "/" + StudyName));
        List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();
        for (int i = 0; i < M.length; i++) {
            int m = M[i];
            int k = 2 * (m - 1);
            addToProblemList(problemList, m, k);
        }

        List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList
                = configureAlgorithmList(problemList);

        Experiment<DoubleSolution, List<DoubleSolution>> experiment
                = new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>(StudyName)
                        .setAlgorithmList(algorithmList)
                        .setProblemList(problemList)
                        .setReferenceFrontDirectory("pareto_fronts/csv")
                        .setExperimentBaseDirectory(experimentBaseDirectory)
                        .setOutputParetoFrontFileName("FUN")
                        .setOutputParetoSetFileName("VAR")
                        .setIndicatorList(Arrays.asList(
                                new PISAHypervolume(),
                                //new NormalizedHypervolume(),
                                //new InvertedGenerationalDistance(),
                                new InvertedGenerationalDistancePlus()))
                        .setIndependentRuns(INDEPENDENT_RUNS)
                        .setNumberOfCores(8)
                        .build();

        new ExecuteAlgorithms<>(experiment).run();
        new ComputeQualityIndicators<>(experiment).run();
        new GenerateLatexTablesWithStatistics(experiment).run();
        new GenerateWilcoxonTestTablesWithR<>(experiment).run();
        new GenerateFriedmanTestTables<>(experiment).run();
        new GenerateBoxplotsWithR<>(experiment).setRows(3).setColumns(3).setDisplayNotch().run();
    }

    static void addToProblemList(List problemList, int m, int k) {
        problemList.add(new ExperimentProblem<>(new WFG1(k, L, m), "WFG1_" + m).setReferenceFront("WFG1." + m + "D.csv"));

        problemList.add(new ExperimentProblem<>(new WFG2(k, L, m), "WFG2_" + m).setReferenceFront("WFG2." + m + "D.csv"));
        problemList.add(new ExperimentProblem<>(new WFG3(k, L, m), "WFG3_" + m).setReferenceFront("WFG3." + m + "D.csv"));
        problemList.add(new ExperimentProblem<>(new WFG5(k, L, m), "WFG5_" + m).setReferenceFront("WFG5." + m + "D.csv"));

        problemList.add(new ExperimentProblem<>(new WFG4(k, L, m), "WFG4_" + m).setReferenceFront("WFG4." + m + "D.csv"));

        problemList.add(new ExperimentProblem<>(new WFG6(k, L, m), "WFG6_" + m).setReferenceFront("WFG6." + m + "D.csv"));
        problemList.add(new ExperimentProblem<>(new WFG7(k, L, m), "WFG7_" + m).setReferenceFront("WFG7." + m + "D.csv"));
        problemList.add(new ExperimentProblem<>(new WFG8(k, L, m), "WFG8_" + m).setReferenceFront("WFG8." + m + "D.csv"));
        problemList.add(new ExperimentProblem<>(new WFG9(k, L, m), "WFG9_" + m).setReferenceFront("WFG9." + m + "D.csv"));

        if (m <= 3) {
            problemList.add(new ExperimentProblem<>(new DTLZ1(L, m), "DTLZ1_" + m).setReferenceFront("DTLZ1." + m + "D.csv"));

            problemList.add(new ExperimentProblem<>(new DTLZ2(L, m), "DTLZ2_" + m).setReferenceFront("DTLZ2." + m + "D.csv"));
            problemList.add(new ExperimentProblem<>(new DTLZ3(L, m), "DTLZ3_" + m).setReferenceFront("DTLZ3." + m + "D.csv"));
            problemList.add(new ExperimentProblem<>(new DTLZ4(L, m), "DTLZ4_" + m).setReferenceFront("DTLZ4." + m + "D.csv"));
            problemList.add(new ExperimentProblem<>(new DTLZ5(L, m), "DTLZ5_" + m).setReferenceFront("DTLZ5." + m + "D.csv"));
            problemList.add(new ExperimentProblem<>(new DTLZ6(L, m), "DTLZ6_" + m).setReferenceFront("DTLZ6." + m + "D.csv"));
            problemList.add(new ExperimentProblem<>(new DTLZ7(L, m), "DTLZ7_" + m).setReferenceFront("DTLZ7." + m + "D.csv"));
        }

    }

    /**
     * The algorithm list is composed of pairs
     * {@link Algorithm} + {@link Problem} which form part of a
     * {@link ExperimentAlgorithm}, which is a decorator for class
     * {@link Algorithm}.
     */
    static List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureAlgorithmList(
            List<ExperimentProblem<DoubleSolution>> problemList) {
        List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();
        for (int run = 0; run < INDEPENDENT_RUNS; run++) {

            for (int i = 0; i < problemList.size(); i++) {
                int populationSize = StaticParams.getPopulationSize(problemList.get(i).getProblem().numberOfObjectives());
                Problem problem = problemList.get(i).getProblem();

                CrossoverOperator crossover = new SBXCrossover(1.0, 20.0);
                MutationOperator mutation = new PolynomialMutation(1.0 / problem.numberOfVariables(), 20.0);
                CrossoverOperator de = new DifferentialEvolutionCrossover();

                algorithms.add(new ExperimentAlgorithm<>(buildNSGAII(problem, populationSize, crossover, mutation), problemList.get(i), run));
                algorithms.add(new ExperimentAlgorithm<>(buildSPEA2(problem, populationSize, crossover, mutation), problemList.get(i), run));
                algorithms.add(new ExperimentAlgorithm<>(buildMOEADDRA(problem, populationSize, de, mutation), problemList.get(i), run));
                algorithms.add(new ExperimentAlgorithm<>(buildMOEADD(problem, populationSize, crossover, mutation), problemList.get(i), run));
                algorithms.add(new ExperimentAlgorithm<>(buildAGE2(problem, populationSize, crossover, mutation), problemList.get(i), run));
                algorithms.add(new ExperimentAlgorithm<>(buildESPEA(problem, populationSize, crossover, mutation), problemList.get(i), run));
                algorithms.add(new ExperimentAlgorithm<>(buidGWASFGA(problem, populationSize, crossover, mutation), problemList.get(i), run));
            }

        }
        return algorithms;
    }

    static Algorithm buildNSGAII(Problem problem, int populationSize, CrossoverOperator crossover, MutationOperator mutation) {
        if (populationSize % 2 != 0) {
            populationSize++;
        }
        NSGAIIBuilder builder = new NSGAIIBuilder<>(
                problem,
                crossover,
                mutation,
                populationSize);
        builder.setMaxEvaluations(populationSize * StaticParams.getMaxGenerations(problem.numberOfObjectives(), problem.name()));
        Algorithm<List<DoubleSolution>> algorithm = builder.build();
        return algorithm;
    }

    static Algorithm buildSPEA2(Problem problem, int populationSize, CrossoverOperator crossover, MutationOperator mutation) {
        if (populationSize % 2 != 0) {
            populationSize++;
        }
        SPEA2Builder builder = new SPEA2Builder<>(
                problem,
                crossover,
                mutation);
        builder.setMaxIterations(StaticParams.getMaxGenerations(problem.numberOfObjectives(), problem.name()));

        return builder.build();
    }

    static Algorithm buildMOEADDRA(Problem problem, int populationSize, CrossoverOperator crossover, MutationOperator mutation) {
        MOEADBuilder moead = new MOEADBuilder(problem, MOEADBuilder.Variant.MOEADDRA)
                .setCrossover(crossover)
                .setMutation(mutation)
                .setMaxEvaluations(StaticParams.getMaxGenerations(problem.numberOfObjectives(), problem.name()) * populationSize)
                .setPopulationSize(populationSize)
                .setResultPopulationSize(populationSize)
                .setNeighborhoodSelectionProbability(0.9)
                .setMaximumNumberOfReplacedSolutions(2)
                .setNeighborSize(20)
                .setDataDirectory("MOEAD_Weights")
                .setFunctionType(AbstractMOEAD.FunctionType.PBI);

        return moead.build();
    }

    static Algorithm buildAGE2(Problem problem, int populationSize, CrossoverOperator crossover, MutationOperator mutation) {
        if (populationSize % 2 != 0) {
            populationSize++;
        }
        AGEMOEAIIBuilder age = new AGEMOEAIIBuilder(problem);
        age.setMaxIterations(StaticParams.getMaxGenerations(problem.numberOfObjectives(), problem.name()));
        age.setCrossoverOperator(crossover);
        age.setMutationOperator(mutation);
        age.setPopulationSize(populationSize);
        return age.build();
    }

    static Algorithm buildMOEADD(Problem problem, int populationSize, CrossoverOperator crossover, MutationOperator mutation) {
        MOEADBuilder moead = new MOEADBuilder(problem, MOEADBuilder.Variant.MOEADD)
                .setCrossover(crossover)
                .setMutation(mutation)
                .setMaxEvaluations(StaticParams.getMaxGenerations(problem.numberOfObjectives(), problem.name()) * populationSize)
                .setPopulationSize(populationSize)
                .setResultPopulationSize(populationSize)
                .setNeighborhoodSelectionProbability(0.9)
                .setMaximumNumberOfReplacedSolutions(2)
                .setNeighborSize(20)
                .setDataDirectory("MOEAD_Weights")
                .setFunctionType(AbstractMOEAD.FunctionType.PBI);

        return moead.build();
    }

    static Algorithm buidGWASFGA(Problem problem, int populationSize, CrossoverOperator crossover, MutationOperator mutation) {
        SolutionListEvaluator evaluator = new SequentialSolutionListEvaluator<>();
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selectionOperator = new BinaryTournamentSelection<>(
                new RankingAndCrowdingDistanceComparator<>());
        String dataFileName;
        dataFileName = "W" + problem.numberOfObjectives() + "D_"
                + populationSize + ".dat";
        return new GWASFGA(problem, populationSize, StaticParams.getMaxGenerations(problem.numberOfObjectives(), problem.name()), crossover, mutation, selectionOperator, evaluator, epsilon, "MOEAD_Weights/" + dataFileName);

    }

    static Algorithm buildESPEA(Problem problem, int populationSize, CrossoverOperator crossover, MutationOperator mutation) {
        ESPEABuilder builder = new ESPEABuilder(problem, crossover, mutation);
        builder.setMaxEvaluations(StaticParams.getMaxGenerations(problem.numberOfObjectives(), problem.name()) * populationSize);
        builder.setPopulationSize(populationSize);
        return builder.build();
    }
}
