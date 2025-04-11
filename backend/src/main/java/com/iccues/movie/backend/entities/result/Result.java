package com.iccues.movie.backend.entities.result;

import com.iccues.movie.backend.utils.GsonProvider;

public class Result<T> {
    private Status status;
    private String message;
    private T data;

    private Result(Status status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static<T> Result<T> Success(T data) {
        return new Result<T>(Status.SUCCESS, null, data);
    }

    public static<T> Result<T> Failure(String message) {
        return new Result<T>(Status.FAILURE, message, null);
    }

    public static<T> Result<T> Error(String message) {
        return new Result<T>(Status.ERROR, message, null);
    }

    @Override
    public String toString() {
        return GsonProvider.GSON.toJson(this);
    }
}
