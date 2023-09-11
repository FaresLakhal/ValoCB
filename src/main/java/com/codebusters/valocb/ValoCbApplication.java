package com.codebusters.valocb;

import com.codebusters.valocb.config.DefaultProfileUtil;
import com.codebusters.valocb.config.SpringProfileConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
public class ValoCbApplication {

    private static final Logger log = LoggerFactory.getLogger(ValoCbApplication.class);

    private final Environment env;

    public ValoCbApplication(Environment env) {
        this.env = env;
    }

//    @PostConstruct
//    public void initApplication() {
//        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
//        if (activeProfiles.contains(SpringProfileConstants.SPRING_PROFILE_DEVELOPMENT)
//                && activeProfiles.contains(SpringProfileConstants.SPRING_PROFILE_PRODUCTION)) {
//            log.error("You have misconfigured your application! It should not run "
//                    + "with both the 'dev' and 'prod' profiles at the same time.");
//        }
//    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ValoCbApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();

        log.warn("\n---------------");
        log.warn("\n Application is running!");
        log.warn("Profile(s): {}", env.getActiveProfiles());
        log.warn("\n--------------------");
    }

}
