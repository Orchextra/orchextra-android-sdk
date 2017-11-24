Feature: Login
    Perform login on apiKey and apiSecret are inputted

    Scenario: Input fake apiKey and fake apiSecret
        Given I have a login view
        When I input apiKey fake_apiKey
        And I input apiSecret fake_apiSecret
        And I press start button
        Then I should see auth error

    Scenario: Input real apiKey and real apiSecret
        Given I have a login view
        When I input apiKey 34a4654b9804eab82aae05b2a5f949eb2a9f412c
        And I input apiSecret 2d5bce79e3e6e9cabf6d7b040d84519197dc22f3
        And I press start button
        Then I should see scanner view