Feature: Login
    Perform login on apiKey and apiSecret are inputted

    Scenario Outline: Input apiKey and apiSecret in correct format
        Given I have a LoginActivity
        When I input apiKey <apiKey>
        And I input apiSecret "<apiSecret>"
        And I press start button
        Then I should <see> auth error

    Examples:
        | apiKey             | apiSecret      | see   |
        | fake_apiKey        | fake_apiSecret | true  |