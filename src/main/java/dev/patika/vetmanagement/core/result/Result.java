package dev.patika.vetmanagament.core.result;

import lombok.Getter;

@Getter
public class Result {
    private boolean status;
    private String message;
    private String responseCode;

    public Result(String message, String responseCode, boolean status) {
        this.message = message;
        this.responseCode = responseCode;
        this.status = status;
    }

}
