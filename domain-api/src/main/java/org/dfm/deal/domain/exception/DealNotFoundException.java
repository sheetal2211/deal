package org.dfm.deal.domain.exception;

public class DealNotFoundException extends RuntimeException {

  public DealNotFoundException(Long id) {
    super("Deal with code " + id + " does not exist");
  }
}

