@Deal
Feature: User would like to get deals
  Background:
    Given the following deals exists in the library
      | code | description                 |
      | 1    | Twinkle twinkle little star |
      | 2    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get all deals
    When user requests for all deals
    Then the user gets the following deals
      | code | description                 |
      | 1    | Twinkle twinkle little star |
      | 2    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get deals by code
    When user requests for deals by code "1"
    Then the user gets the following deals
      | code | description                 |
      | 1    | Twinkle twinkle little star |

  Scenario: User should get an appropriate NOT FOUND message while trying get deals by an invalid code
    When user requests for deals by id "10000" that does not exists
    Then the user gets an exception "Deal with code 10000 does not exist"