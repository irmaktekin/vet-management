package dev.patika.vetmanagament.core.result;


import lombok.Getter;

@Getter
public class ResultData<T> extends Result {
    private T data;
    public ResultData(String message, String responseCode, boolean status,T data) {
        super(message, responseCode, status);
        this.data=data;
    }
}
