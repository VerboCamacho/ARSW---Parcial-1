package eci.arsw.covidanalyzer.model;

import java.util.UUID;

public class Result {
    private UUID id;
    private String firstName,lastName,gender,email,birthString,testString;
    private ResultType type;
    private boolean result;
    private double testSpecifity;
    private int numberOfTestsMade;
    public Result(UUID id, String firstName, String lastName, String gender, String email, String birthString, ResultType type, String testString, boolean result, double testSpecifity){
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.gender=gender;
        this.email=email;
        this.birthString=birthString;
        this.type=type;
        this.testString=testString;
        this.result=result;
        this.testSpecifity=testSpecifity;
        this.numberOfTestsMade=0;

    }




    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return ((Result) o).getId().equals(this.id);
    }

    public String getTestDate() {
        return testString;
    }

    @Override
    public String toString() {
        return this.id.toString() + " - " + this.firstName + " - " + this.lastName;
    }
    public ResultType getResultType(){
        return type;
    }
    public void increment(){
        numberOfTestsMade+=1;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthString() {
        return birthString;
    }

    public void setBirthString(String birthString) {
        this.birthString = birthString;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public ResultType getType() {
        return type;
    }

    public void setType(ResultType type) {
        this.type = type;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public double getTestSpecifity() {
        return testSpecifity;
    }

    public void setTestSpecifity(double testSpecifity) {
        this.testSpecifity = testSpecifity;
    }

    public int getNumberOfTestsMade() {
        return numberOfTestsMade;
    }

    public void setNumberOfTestsMade(int numberOfTestsMade) {
        this.numberOfTestsMade = numberOfTestsMade;
    }
}
