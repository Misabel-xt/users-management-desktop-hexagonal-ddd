package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidClienteEmailException;
import java.util.Objects;
import java.util.regex.Pattern;

public record ClienteEmail(String value) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public ClienteEmail {
        String normalized = Objects.requireNonNull(value, "Email cannot be null").trim().toLowerCase();
        if (normalized.isEmpty()) throw new InvalidClienteEmailException("Email cannot be empty");
        if (!EMAIL_PATTERN.matcher(normalized).matches()) throw new InvalidClienteEmailException("Invalid email format");
    }
}