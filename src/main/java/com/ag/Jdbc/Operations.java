package com.ag.Jdbc;

public enum Operations {
    SELECT ("select"," record/s was/were selected"),
    INSERT("insert"," record/s was/were inserted"),
    UPDATE("update"," record/s was/were updated"),
    DELETE("delete"," record/s was/were deleted"),
    DROP("drop"," object was dropped"),
    CREATE ("create"," object was created");

    private String operator;
    private String message;

    Operations(String operator,String message) {
        this.operator=operator;
        this.message = message;
    }

    public String getName (){
        return operator;
    }
    public String getMessage(){
        return message;
    }
    public static Operations getByName(String name) {
        Operations[] operations = values();
        for (Operations operation : operations) {
            if (operation.operator.equalsIgnoreCase(name)) {
                return operation;
            }
        }
        throw new IllegalArgumentException("Command " + name + " was not recognized");
    }
}
