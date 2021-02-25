package eci.arsw.covidanalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Camel Application
 */
public class CovidAnalyzerTool {

    private static ResultAnalyzer resultAnalyzer;
    private static TestReader testReader;
    private static int amountOfFilesTotal;
    private static AtomicInteger amountOfFilesProcessed;
    private static ArrayList<AnalyzerThread> analyzerThreads;
    private static boolean pausados=false;

    public CovidAnalyzerTool() {
        resultAnalyzer = new ResultAnalyzer();
        testReader = new TestReader();
        amountOfFilesProcessed = new AtomicInteger();
    }

    public void processResultData() {
        amountOfFilesProcessed.set(0);
        List<File> resultFiles = getResultFileList();

        for (File resultFile : resultFiles) {
            List<Result> results = testReader.readResultsFromFile(resultFile);
            for (Result result : results) {
                resultAnalyzer.addResult(result);
            }
            amountOfFilesProcessed.incrementAndGet();
        }
    }

    private static List<File> getResultFileList() {
        List<File> csvFiles = new ArrayList<>();
        try (Stream<Path> csvFilePaths = Files.walk(Paths.get("src/main/resources/")).filter(path -> path.getFileName().toString().endsWith(".csv"))) {
            csvFiles = csvFilePaths.map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvFiles;
    }


    public Set<Result> getPositivePeople() {
        return resultAnalyzer.listOfPositivePeople();
    }

    /**
     *  divisorThreads() metodo para hacer la division de archivos para cada thread
     */
    public static void divisorThreads(int numHilos) throws InterruptedException {
        amountOfFilesProcessed.set(0);
        List<File> transactionFiles = getResultFileList();
        amountOfFilesTotal = transactionFiles.size();
        analyzerThreads=new ArrayList<AnalyzerThread>();
        int threads=numHilos;
        int resi=transactionFiles.size()/threads;
        int hilos= 0;
        int min=0;
        for (int i=1;i<transactionFiles.size()+1;i++){
            if(min==0){
                min=i;
            }
            if(i%resi==0&&hilos<threads-1){
                hilos+=1;
                AnalyzerThread re=new AnalyzerThread(amountOfFilesProcessed,testReader,resultAnalyzer,transactionFiles,min,i);
                re.start();
                analyzerThreads.add(re);
                min=0;
            }
            if(hilos==threads-1&&i== transactionFiles.size()){
                AnalyzerThread re=new AnalyzerThread(amountOfFilesProcessed,testReader,resultAnalyzer,transactionFiles,min,i);
                re.start();
                analyzerThreads.add(re);
                min=0;
            }

        }
    }


    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        CovidAnalyzerTool covidAnalyzerTool = new CovidAnalyzerTool();
        covidAnalyzerTool.divisorThreads(5);
//        Thread processingThread = new Thread(() -> covidAnalyzerTool.processResultData());
//        processingThread.start();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.contains("")){
                for(AnalyzerThread hilo:analyzerThreads){
                    hilo.pausa();
                    pausados=!pausados;
                }
                if(pausados){
                    System.out.println("________PAUSADOS________");
                    String message = "Processed %d out of %d files.\nFound %d positive people:\n%s";
                    Set<Result> positivePeople = covidAnalyzerTool.getPositivePeople();
                    String affectedPeople = positivePeople.stream().map(Result::toString).reduce("", (s1, s2) -> s1 + "\n" + s2);
                    message = String.format(message, covidAnalyzerTool.amountOfFilesProcessed.get(), covidAnalyzerTool.amountOfFilesTotal, positivePeople.size(), affectedPeople);
                    System.out.println(message);
                }else{System.out.println("________INICIADOSS________");}
            }


        }
    }

}

