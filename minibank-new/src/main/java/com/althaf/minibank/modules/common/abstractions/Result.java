package com.althaf.minibank.modules.common.abstractions;

public class Result<T> {

    private final T value;
    private final boolean isSuccess;
    private final Error error;

    private Result(
        T value,
        boolean isSuccess,
        Error error
    ) {
        if (isSuccess && error != Error.NONE ||
            !isSuccess && error == Error.NONE) {
            throw new IllegalArgumentException("Invalid error");
        }

        this.value = value;
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isFailure() {
        return !isSuccess;
    }

    public T getValue() {
        return value;
    }

    public Error getError() {
        return error;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(
            value,
            true,
            Error.NONE
        );
    }

    public static <T> Result<T> failure(Error error) {
        return new Result<>(
            null,
            false,
            error
        );
    }
}
