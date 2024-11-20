package itaf.tools.config;

import com.itaf.core.driver.CucumberOptionsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ToolsInitializer implements ApplicationRunner {

    @Autowired
    private CucumberOptionsProvider cucumberOptionsProvider;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
