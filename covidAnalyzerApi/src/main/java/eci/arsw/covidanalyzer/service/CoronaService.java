package eci.arsw.covidanalyzer.service;
import eci.arsw.covidanalyzer.CovidAggregateController;
import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

    @Component
    @Qualifier("coronaService19")
    public class CoronaService implements ICovidAggregateService {
        ArrayList<Result> listOfResults = new ArrayList<Result>();

        public CoronaService() {
            Result testResult=new Result(UUID.randomUUID(),"prueba","prueba","male","prueba@gmail.com","01/01/1999",ResultType.FALSE_POSITIVE,"test",true,01);
            listOfResults.add(testResult);

        }

        @Override
        public boolean aggregateResult(Result result, ResultType type) {
            boolean flagRepeatedEntry = false;
            for (int i = 0; i < listOfResults.size(); i++) {
                if (listOfResults.get(i).getId().equals(result.getId())) {
                    flagRepeatedEntry = true;
                }
            }
            if (!flagRepeatedEntry) {
                result.setType(type);
                listOfResults.add(result);

            }
            return !flagRepeatedEntry;
        }

        @Override
        public ArrayList<Result> getResult(ResultType type) {
            ArrayList<Result> listOfResultsType = new ArrayList<Result>();
            for (int i = 0; i < listOfResults.size(); i++) {
                if (listOfResults.get(i).getResultType().equals(type)) {
                    listOfResultsType.add(listOfResults.get(i));
                }
            }
            return listOfResultsType;
        }

        @Override
        public boolean upsertPersonWithMultipleTests(UUID id, ResultType type) {
            int usuario=-2;
            boolean encontrado = false;
            for (int i = 0; i < listOfResults.size(); i++) {
                if (listOfResults.get(i).getId().equals(id) && listOfResults.get(i).getResultType().equals(type)) {
                    usuario = i;
                    encontrado = true;
                }
            }
            if (encontrado) {
                listOfResults.get(usuario).increment();
            }
            return encontrado;
        }



    }