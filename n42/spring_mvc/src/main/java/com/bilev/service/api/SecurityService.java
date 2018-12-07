package com.bilev.service.api;

import java.security.Principal;

public interface SecurityService {

    boolean hasAccess(Principal principal, int contractId);
}
