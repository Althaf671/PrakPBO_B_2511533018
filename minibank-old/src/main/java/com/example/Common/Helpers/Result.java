package com.example.Common.Helpers;

public class Result<T> {

    private final T value;
    private final boolean isSuccess;
    private final AppError error;

    private Result(
        T value,
        boolean isSuccess,
        AppError error
    ) {
        if (isSuccess && error != AppError.NONE ||
            !isSuccess && error == AppError.NONE) {
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

    public AppError getError() {
        return error;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(
            value,
            true,
            AppError.NONE
        );
    }

    public static <T> Result<T> failure(AppError error) {
        return new Result<>(
            null,
            false,
            error
        );
    }
}