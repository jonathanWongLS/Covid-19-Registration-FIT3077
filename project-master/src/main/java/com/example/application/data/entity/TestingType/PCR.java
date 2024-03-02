package com.example.application.data.entity.TestingType;

public class PCR implements TestingType{

    // Returns "PCR" as the type of COVID test
    @Override
    public String getType() {
        return "PCR";
    }
}
