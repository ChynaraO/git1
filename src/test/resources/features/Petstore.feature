Feature: Petstore API

  Scenario: List pets API
    Given user has petstore endpoint
    When user sends GET to list pets
    Then status code is 200

    And response contains list of pets

  Scenario Outline: Get non-existing pet
    Given user has petstore endpoint
    When user sends GET request to list <petIdType> pet by id
    Then status code is 404
    And error message is '<errorMessage>'
    Examples:
      | petIdType    | errorMessage                                                      |
      | non-existing | Pet not found                                                     |
      | invalid      | java.lang.NumberFormatException: For input string: "5675669870ju" |
