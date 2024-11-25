package br.usp.lti.newmoea.study;

/**
 *
 * @author vinixnan
 */
public class StaticParams {

    public static int getMaxGenerations(int m, String problem) {
        switch (m) {
            case 3:
                switch (problem) {
                    case "DTLZ1" -> {
                        return 400; // DTLZ1
                    }
                    case "DTLZ2" -> {
                        return 250; // DTLZ2
                    }
                    case "DTLZ3" -> {
                        return 1000; // DTLZ3
                    }
                    case "DTLZ4" -> {
                        return 600; // DTLZ4
                    }
                    case "DTLZ7" -> {
                        return 1000; // DTLZ7
                    }
                    case "WFG1", "WFG2", "WFG3", "WFG4", "WFG5", "WFG6", "WFG7", "WFG8", "WFG9" -> {
                        return 400; // WFG
                    }
                    default -> {
                        return 1000;
                    }
                }

            case 5:
                switch (problem) {
                    case "DTLZ1" -> {
                        return 600; // DTLZ1
                    }
                    case "DTLZ2" -> {
                        return 350; // DTLZ2
                    }
                    case "DTLZ3" -> {
                        return 1000; // DTLZ3
                    }
                    case "DTLZ4" -> {
                        return 1000; // DTLZ4
                    }
                    case "DTLZ7" -> {
                        return 1000; // DTLZ7
                    }
                    case "WFG1", "WFG2", "WFG3", "WFG4", "WFG5", "WFG6", "WFG7", "WFG8", "WFG9" -> {
                        return 750; // WFG
                    }
                    default -> {
                        return 1000;
                    }
                }
            case 8:
                switch (problem) {
                    case "DTLZ1" -> {
                        return 750; // DTLZ1
                    }
                    case "DTLZ2" -> {
                        return 500; // DTLZ2
                    }
                    case "DTLZ3" -> {
                        return 1000; // DTLZ3
                    }
                    case "DTLZ4" -> {
                        return 1250; // DTLZ4
                    }
                    case "DTLZ7" -> {
                        return 1000; // DTLZ7
                    }
                    case "WFG1", "WFG2", "WFG3", "WFG4", "WFG5", "WFG6", "WFG7", "WFG8", "WFG9" -> {
                        return 1500; // WFG
                    }
                    default -> {
                        return 1000;
                    }
                }
            case 10:
                switch (problem) {
                    case "DTLZ1" -> {
                        return 1000;  // DTLZ1
                    }
                    case "DTLZ2" -> {
                        return 750;   // DTLZ2
                    }
                    case "DTLZ3" -> {
                        return 1500;  // DTLZ3
                    }
                    case "DTLZ4" -> {
                        return 2000;  // DTLZ4
                    }
                    case "DTLZ7" -> {
                        return 1500; // DTLZ7
                    }
                    case "WFG1", "WFG2", "WFG3", "WFG4", "WFG5", "WFG6", "WFG7", "WFG8", "WFG9" -> {
                        return 2000; // WFG
                    }

                }
            case 15:
                switch (problem) {
                    case "DTLZ1" -> {
                        return 1500;  // DTLZ1
                    }
                    case "DTLZ2" -> {
                        return 1000;  // DTLZ2
                    }
                    case "DTLZ3" -> {
                        return 2000;  // DTLZ3
                    }
                    case "DTLZ4" -> {
                        return 3000;  // DTLZ4
                    }
                    case "DTLZ7" -> {
                        return 2000; // DTLZ7
                    }
                    case "WFG1", "WFG2", "WFG3", "WFG4", "WFG5", "WFG6", "WFG7", "WFG8", "WFG9" -> {
                        return 3000; // WFG
                    }
                }
            default:
                return 1000;
        }
    }

    public static int getPopulationSize(int qtdObj) {
        switch (qtdObj) {
            case 2:
                return 100;
            case 3:
                return 91;
            case 5:
                return 210;
            case 8:
                return 156;
            //9
            case 10:
                return 275;
            case 15:
                return 135;
        }
        return 100;
    }

}
