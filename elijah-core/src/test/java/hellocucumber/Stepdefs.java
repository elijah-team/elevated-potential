package hellocucumber;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class Stepdefs {
    private final StepDefinitions stepDefinitions;
    private String today;
    private String actualAnswer;

    public Stepdefs() {
        stepDefinitions = null;
    }

    public Stepdefs(StepDefinitions aStepDefinitions) {
        stepDefinitions = aStepDefinitions;
    }

    @Given("today is Sunday")
    public void today_is_Sunday() {
        today = "Sunday";
    }

    @Given("today is {string}")
    public void today_is(String string) {
        today = string;
    }

    @When("I ask whether it's Friday yet")
    public void i_ask_whether_it_s_Friday_yet() {
        actualAnswer = IsItFriday.isItFriday(today);
    }

    @Then("I should be told {string}")
    public void i_should_be_told(String expectedAnswer) {
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Given("today is Friday")
    public void today_is_Friday() {
        today = "Friday";
    }

}
