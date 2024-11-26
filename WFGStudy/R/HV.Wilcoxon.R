write("", "HV.Wilcoxon.tex",append=FALSE)
resultDirectory<-"../data"
latexHeader <- function() {
  write("\\documentclass{article}", "HV.Wilcoxon.tex", append=TRUE)
  write("\\title{StandardStudy}", "HV.Wilcoxon.tex", append=TRUE)
  write("\\usepackage{amssymb}", "HV.Wilcoxon.tex", append=TRUE)
  write("\\author{A.J.Nebro}", "HV.Wilcoxon.tex", append=TRUE)
  write("\\begin{document}", "HV.Wilcoxon.tex", append=TRUE)
  write("\\maketitle", "HV.Wilcoxon.tex", append=TRUE)
  write("\\section{Tables}", "HV.Wilcoxon.tex", append=TRUE)
  write("\\", "HV.Wilcoxon.tex", append=TRUE)
}

latexTableHeader <- function(problem, tabularString, latexTableFirstLine) {
  write("\\begin{table}", "HV.Wilcoxon.tex", append=TRUE)
  write("\\caption{", "HV.Wilcoxon.tex", append=TRUE)
  write(problem, "HV.Wilcoxon.tex", append=TRUE)
  write(".HV.}", "HV.Wilcoxon.tex", append=TRUE)

  write("\\label{Table:", "HV.Wilcoxon.tex", append=TRUE)
  write(problem, "HV.Wilcoxon.tex", append=TRUE)
  write(".HV.}", "HV.Wilcoxon.tex", append=TRUE)

  write("\\centering", "HV.Wilcoxon.tex", append=TRUE)
  write("\\setlength\\tabcolsep{1pt}", "HV.Wilcoxon.tex", append=TRUE)
  write("\\begin{scriptsize}", "HV.Wilcoxon.tex", append=TRUE)
  write("\\begin{tabular}{", "HV.Wilcoxon.tex", append=TRUE)
  write(tabularString, "HV.Wilcoxon.tex", append=TRUE)
  write("}", "HV.Wilcoxon.tex", append=TRUE)
  write(latexTableFirstLine, "HV.Wilcoxon.tex", append=TRUE)
  write("\\hline ", "HV.Wilcoxon.tex", append=TRUE)
}

printTableLine <- function(indicator, algorithm1, algorithm2, i, j, problem) { 
  file1<-paste(resultDirectory, algorithm1, sep="/")
  file1<-paste(file1, problem, sep="/")
  file1<-paste(file1, indicator, sep="/")
  data1<-scan(file1)
  file2<-paste(resultDirectory, algorithm2, sep="/")
  file2<-paste(file2, problem, sep="/")
  file2<-paste(file2, indicator, sep="/")
  data2<-scan(file2)
  if (i == j) {
    write("--", "HV.Wilcoxon.tex", append=TRUE)
  }
  else if (i < j) {
    if (is.finite(wilcox.test(data1, data2)$p.value) & wilcox.test(data1, data2)$p.value <= 0.05) {
      if (median(data1) >= median(data2)) {
        write("$\\blacktriangle$", "HV.Wilcoxon.tex", append=TRUE)
}
      else {
        write("$\\triangledown$", "HV.Wilcoxon.tex", append=TRUE)
}
}
    else {
      write("$-$", "HV.Wilcoxon.tex", append=TRUE)
}
  }
  else {
    write(" ", "HV.Wilcoxon.tex", append=TRUE)
  }
}

latexTableTail <- function() { 
  write("\\hline", "HV.Wilcoxon.tex", append=TRUE)
  write("\\end{tabular}", "HV.Wilcoxon.tex", append=TRUE)
  write("\\end{scriptsize}", "HV.Wilcoxon.tex", append=TRUE)
  write("\\end{table}", "HV.Wilcoxon.tex", append=TRUE)
}

latexTail <- function() { 
  write("\\end{document}", "HV.Wilcoxon.tex", append=TRUE)
}

### START OF SCRIPT 
# Constants
problemList <-c("WFG1_2", "WFG2_2", "WFG3_2", "WFG5_2", "WFG4_2", "WFG6_2", "WFG7_2", "WFG8_2", "WFG9_2", "DTLZ1_2", "DTLZ2_2", "DTLZ3_2", "DTLZ4_2", "DTLZ5_2", "DTLZ6_2", "DTLZ7_2", "WFG1_3", "WFG2_3", "WFG3_3", "WFG5_3", "WFG4_3", "WFG6_3", "WFG7_3", "WFG8_3", "WFG9_3", "DTLZ1_3", "DTLZ2_3", "DTLZ3_3", "DTLZ4_3", "DTLZ5_3", "DTLZ6_3", "DTLZ7_3", "WFG1_5", "WFG2_5", "WFG3_5", "WFG5_5", "WFG4_5", "WFG6_5", "WFG7_5", "WFG8_5", "WFG9_5") 
algorithmList <-c("NSGAII", "SPEA2", "MOEADDRA", "MOEADD", "AGE-MOEA-II", "ESPEA", "GWASFGA") 
tabularString <-c("lcccccc") 
latexTableFirstLine <-c("\\hline  & SPEA2 & MOEADDRA & MOEADD & AGE-MOEA-II & ESPEA & GWASFGA\\\\ ") 
indicator<-"HV"

 # Step 1.  Writes the latex header
latexHeader()
tabularString <-c("| l | ccccccccccccccccccccccccccccccccccccccccc | ccccccccccccccccccccccccccccccccccccccccc | ccccccccccccccccccccccccccccccccccccccccc | ccccccccccccccccccccccccccccccccccccccccc | ccccccccccccccccccccccccccccccccccccccccc | ccccccccccccccccccccccccccccccccccccccccc | ") 

latexTableFirstLine <-c("\\hline \\multicolumn{1}{|c|}{} & \\multicolumn{41}{c|}{SPEA2} & \\multicolumn{41}{c|}{MOEADDRA} & \\multicolumn{41}{c|}{MOEADD} & \\multicolumn{41}{c|}{AGE-MOEA-II} & \\multicolumn{41}{c|}{ESPEA} & \\multicolumn{41}{c|}{GWASFGA} \\\\") 

# Step 3. Problem loop 
latexTableHeader("WFG1_2 WFG2_2 WFG3_2 WFG5_2 WFG4_2 WFG6_2 WFG7_2 WFG8_2 WFG9_2 DTLZ1_2 DTLZ2_2 DTLZ3_2 DTLZ4_2 DTLZ5_2 DTLZ6_2 DTLZ7_2 WFG1_3 WFG2_3 WFG3_3 WFG5_3 WFG4_3 WFG6_3 WFG7_3 WFG8_3 WFG9_3 DTLZ1_3 DTLZ2_3 DTLZ3_3 DTLZ4_3 DTLZ5_3 DTLZ6_3 DTLZ7_3 WFG1_5 WFG2_5 WFG3_5 WFG5_5 WFG4_5 WFG6_5 WFG7_5 WFG8_5 WFG9_5 ", tabularString, latexTableFirstLine)

indx = 0
for (i in algorithmList) {
  if (i != "GWASFGA") {
    write(i , "HV.Wilcoxon.tex", append=TRUE)
    write(" & ", "HV.Wilcoxon.tex", append=TRUE)

    jndx = 0
    for (j in algorithmList) {
      for (problem in problemList) {
        if (jndx != 0) {
          if (i != j) {
            printTableLine(indicator, i, j, indx, jndx, problem)
          }
          else {
            write("  ", "HV.Wilcoxon.tex", append=TRUE)
          } 
          if (problem == "WFG9_5") {
            if (j == "GWASFGA") {
              write(" \\\\ ", "HV.Wilcoxon.tex", append=TRUE)
            } 
            else {
              write(" & ", "HV.Wilcoxon.tex", append=TRUE)
            }
          }
     else {
    write("&", "HV.Wilcoxon.tex", append=TRUE)
     }
        }
      }
      jndx = jndx + 1
}
    indx = indx + 1
  }
} # for algorithm

  latexTableTail()

#Step 3. Writes the end of latex file 
latexTail()

