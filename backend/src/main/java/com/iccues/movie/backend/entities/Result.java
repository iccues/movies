package com.iccues.movie.backend.entities;

import com.iccues.movie.backend.utils.GsonProvider;

public class Result<T> {
    private boolean ok;
    private String error;
    private T value;

    private Result(boolean ok, String error, T value) {
        this.ok = ok;
        this.error = error;
        this.value = value;
    }

    public static<T> Result<T> Ok(T value) {
        return new Result<T>(true, null, value);
    }

    public static<T> Result<T> Ok() {
        return new Result<T>(true, null, null);
    }

    public static<T> Result<T> Err(String error) {
        return new Result<T>(false, error, null);
    }

    @Override
    public String toString() {
        return GsonProvider.GSON.toJson(this);
    }
}
