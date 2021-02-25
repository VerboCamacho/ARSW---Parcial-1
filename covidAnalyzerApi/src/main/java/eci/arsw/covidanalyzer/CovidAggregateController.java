package eci.arsw.covidanalyzer;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.service.ICovidAggregateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class CovidAggregateController {
    @Autowired
    @Qualifier("coronaService19")
    ICovidAggregateService covidAggregateService;

    //TODO: Implemente todos los metodos POST que hacen falta.

    //GETs

    @RequestMapping(value = "/covid/result/true-positive", method = RequestMethod.GET)
    public ArrayList<Result> getTruePositiveResult() {
        //TODO

        return covidAggregateService.getResult(ResultType.TRUE_POSITIVE);
    }


    @RequestMapping(value = "/covid/result/false-positive", method = RequestMethod.GET)
    public ArrayList<Result> getFalsePositiveResult() {

        return covidAggregateService.getResult(ResultType.FALSE_POSITIVE);

    }

    @RequestMapping(value = "/covid/result/true-negative", method = RequestMethod.GET)
    public ArrayList<Result> getTrueNegativeResult() {

        return covidAggregateService.getResult(ResultType.TRUE_NEGATIVE);


    }

    @RequestMapping(value = "/covid/result/false-negative", method = RequestMethod.GET)
    public ArrayList<Result> getFalseNegativeResult() {

        return covidAggregateService.getResult(ResultType.FALSE_NEGATIVE);


    }

    //POSTS
    @RequestMapping(value = "/covid/result/true-positive", method = RequestMethod.POST)
    public ResponseEntity<?>addTruePositiveResult(@RequestBody Result resultToAdd) {

        if(covidAggregateService.aggregateResult(resultToAdd,ResultType.TRUE_POSITIVE)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/covid/result/true-negative", method = RequestMethod.POST)
    public ResponseEntity<?>addTrueNegativeResult(@RequestBody Result resultToAdd) {

        if(covidAggregateService.aggregateResult(resultToAdd,ResultType.TRUE_NEGATIVE)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/covid/result/false-positive", method = RequestMethod.POST)
    public ResponseEntity<?>addFalsePositiveResult(@RequestBody Result resultToAdd) {

        if(covidAggregateService.aggregateResult(resultToAdd,ResultType.FALSE_POSITIVE)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/covid/result/false-negative", method = RequestMethod.POST)
    public ResponseEntity<?>addFalseNegativeResult(@RequestBody Result resultToAdd) {
        if(covidAggregateService.aggregateResult(resultToAdd,ResultType.FALSE_NEGATIVE)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }



    //TODO: Implemente el método.

    @RequestMapping(value = "/covid/result/persona/{id}", method = RequestMethod.PUT)
    public ResponseEntity savePersonaWithMultipleTests(@PathVariable UUID id, @RequestBody ResultType type) {
        if(covidAggregateService.upsertPersonWithMultipleTests(id,type)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    
}