package org.highload.api_gateway.configuration;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Scopes {
    ADMIN("SCOPE_admin"),
    USER("SCOPE_user");

    final String scopeName;
}