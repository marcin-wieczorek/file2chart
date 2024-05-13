package com.file2chart.service.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class ApiKeyAuthentication extends AbstractAuthenticationToken {

    public ApiKeyAuthentication() {
        super(AuthorityUtils.NO_AUTHORITIES);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
