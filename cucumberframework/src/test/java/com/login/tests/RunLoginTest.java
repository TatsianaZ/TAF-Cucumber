package com.login.tests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/org.bsuir.features/",
        plugin = { "pretty", "html:target/cucumber-html-report", "junit:target/junit_cucumber.xml", "json:target/cucumber.json" },
        tags = {"~@wip", "~@manual"}
)
public class RunLoginTest {
    @BeforeClass
    public static void reporting() {
    }
}

