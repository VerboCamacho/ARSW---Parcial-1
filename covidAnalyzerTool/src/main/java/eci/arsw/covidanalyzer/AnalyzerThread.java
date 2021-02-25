package eci.arsw.covidanalyzer;
import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
public class AnalyzerThread extends Thread{
    private AtomicBoolean pausado;
    private int minFile;
    private int maxFile;
    private AtomicInteger amountOfFilesProcessed;
    private  TestReader testReader;
    private ResultAnalyzer resultAnalyzer;
    private List<File>archivos;


    public AnalyzerThread(AtomicInteger amountOfFilesProcessed, TestReader tReader,ResultAnalyzer rAnalizer, List<File>Files,int min, int max){
        this.minFile=min-1;
        this.maxFile=max-1;
        this.amountOfFilesProcessed=amountOfFilesProcessed;
        this.resultAnalyzer=rAnalizer;
        this.testReader=tReader;
        this.archivos=Files;
        this.pausado=new AtomicBoolean(false);
        //System.out.println(minFile+"  "+maxFile+" nuevo hilo "+archivos.size());

    }
    @Override
    public void run(){
        while(pausado.get()) {
            for (int archivo=minFile;archivo<=maxFile;archivo++) {
                List<Result> results = testReader.readResultsFromFile(archivos.get(archivo));
                for (Result result : results) {
                    resultAnalyzer.addResult(result);
                }
                amountOfFilesProcessed.incrementAndGet();
            }
        }
    }

}
