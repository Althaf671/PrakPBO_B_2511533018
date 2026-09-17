package com.althaf.minibank.modules.common.valueObjects;

import java.util.List;

import com.althaf.minibank.modules.common.abstractions.Result;
import com.althaf.minibank.modules.common.abstractions.ValueObjectBase;
import com.althaf.minibank.modules.common.constants.MoneyConstant;
import com.althaf.minibank.modules.common.enums.MoneyCurrency;
import com.althaf.minibank.modules.common.errors.MoneyError;
import static com.althaf.minibank.modules.common.helpers.Formatter.formatCurrency;

public final class Money extends ValueObjectBase {
    
    // attributes
    private final double nominal;
    private final MoneyCurrency currency;

    // getter
    public double getNominal() { return nominal; }
    public MoneyCurrency getCurrency() { return currency; }

    // constructor
    private Money(double val, MoneyCurrency curr) {
        nominal = val;
        currency = curr;
    }

    // factory
    public static Result<Money> create(double val, MoneyCurrency curr) {
        Result<Void> validation = validate(val);
        if (validation.isFailure()) {
            return Result.failure(validation.getError());
        }

        return Result.success(new Money(val, curr));
    }

    // behaviour
    public Result<Money> add(double newVal) {
        double result = nominal + newVal;

        Result<Void> preValidation = validate(newVal);
        if (preValidation.isFailure()) {
            return Result.failure(preValidation.getError());
        }

        Result<Void> finalValidation = validate(result);
        if (finalValidation.isFailure()) {
            return Result.failure(finalValidation.getError());
        }

        return Result.success(new Money(result, currency));
    }

    public Result<Money> sub(double newVal) {
        double result = nominal - newVal;

        Result<Void> preValidation = validate(newVal);
        if (preValidation.isFailure()) {
            return Result.failure(preValidation.getError());
        }

        Result<Void> finalValidation = validate(result);
        if (finalValidation.isFailure()) {
            return Result.failure(finalValidation.getError());
        }

        return Result.success(new Money(result, currency));
    }

    // validator
    private static Result<Void> validate(double val) {
        if (val < MoneyConstant.MIN_NOMINAL) {
            return Result.failure(MoneyError.BELOW_MIN_NOMINAL);
        }

        if (val > MoneyConstant.MAX_NOMINAL) {
            return Result.failure(MoneyError.EXCEED_MAX_NOMINAL);
        }

        return Result.success(null);
    }

    @Override
    protected List<Object> getAtomicValues() {
        return List.of(nominal, currency);
    }

    // helper
    @Override 
    public String toString() {
        return formatCurrency(nominal, currency);
    }
}
