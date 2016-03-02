package com.gigigo.orchextra.domain.interactors.error;

import com.gigigo.gggjavalib.business.model.BusinessContentType;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public abstract class ServiceErrorChecker {

  private final AuthenticationService authenticationService;

  protected ServiceErrorChecker(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  public InteractorResponse checkErrors(BusinessError businessError) {

    if (businessError.getBusinessContentType() == BusinessContentType.BUSINESS_ERROR_CONTENT) {
      //BusinessError management
      InteractorResponse response = checkBusinessErrors(businessError);

      if (!response.hasError()) {
        return response;
      }

      return checkConcreteBusinessErrors(businessError);
    } else {

      //Exception management BusinessContentType.EXCEPTION_CONTENT
      InteractorResponse response = checkExceptions(businessError);

      if (!response.hasError()) {
        return response;
      }

      return checkConcreteException(businessError);
    }
  }

  private InteractorResponse checkExceptions(BusinessError businessError) {
    return new InteractorResponse(new GenericError(businessError));
  }

  private InteractorResponse checkBusinessErrors(BusinessError businessError) {
    OrchextraBusinessErrors error =
        OrchextraBusinessErrors.getEnumTypeFromInt(businessError.getCode());

    try {

      switch (error) {
        case NO_AUTH_CREDENTIALS:
        case NO_AUTH_EXPIRED:
          return authenticationService.authenticate();

        default:
          return new InteractorResponse(new GenericError(businessError));
      }
    } catch (Exception e) {
      return new InteractorResponse(new GenericError(businessError));
    }
  }

  protected abstract InteractorResponse checkConcreteException(BusinessError businessError);

  protected abstract InteractorResponse checkConcreteBusinessErrors(BusinessError businessError);
}
