package com.althaf.minibank.modules.common.valueObjects;

import java.util.List;
import java.util.Locale;

import com.althaf.minibank.modules.common.abstractions.Result;
import com.althaf.minibank.modules.common.abstractions.ValueObjectBase;
import com.althaf.minibank.modules.common.constants.EmailConstant;
import static com.althaf.minibank.modules.common.constants.EmailConstant.ALLOWED_DOMAIN;
import static com.althaf.minibank.modules.common.constants.EmailConstant.EMAIL_PATTERN;
import com.althaf.minibank.modules.common.errors.EmailError;
import static com.althaf.minibank.modules.common.helpers.StringHelper.isNullOrWhitespace;

public final class Email extends ValueObjectBase {
    
    // attributes
    private final String address;

    // getter
    public String getAddress() { return address; }

    // constructor
    private Email(String addr) {
        address = addr;
    }

    // factory
    public static Result<Email> create(String addr) {
        Result validation = validate(addr);
        if (validation.isFailure()) {
            return Result.failure(validation.getError());
        }

        return Result.success(new Email(addr.toLowerCase(Locale.ROOT)));
    }

    // validator
    private static Result<Void> validate(String addr) {
        if (isNullOrWhitespace(addr)) {
            return Result.failure(EmailError.EMAIL_REQUIRED);
        }

        if (addr.length() < EmailConstant.MIN_LENGTH) {
            return Result.failure(EmailError.BELOW_MIN_LENGTH);
        }

        if (addr.length() > EmailConstant.MAX_LENGTH) {
            return Result.failure(EmailError.EXCEED_MAX_LENGTH);
        }

        if (!EMAIL_PATTERN.matcher(addr).matches()) {
            return Result.failure(EmailError.INVALID_FORMAT);
        }

        String domain = addr.substring(addr.lastIndexOf('@') + 1).toLowerCase(Locale.ROOT);
        if (!ALLOWED_DOMAIN.contains(domain)) {
            return Result.failure(EmailError.UNKNOWN_DOMAIN);
        }

        return Result.success(null);
    }

    @Override
    protected List<Object> getAtomicValues() {
        return List.of(address);
    }
}
