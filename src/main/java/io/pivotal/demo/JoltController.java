package io.pivotal.demo;

import bea.jolt.JoltRemoteService;
import bea.jolt.JoltSession;
import bea.jolt.JoltSessionAttributes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jolt")
public class JoltController {

    @Value("${jolt.idle.timeout}")
    Integer joltIdleTimeout = 300;

    @Value("${jolt.default.message}")
    String joltDefaultMessage = "Howdy Jolt!";

    @Value("${jolt.app.address}")
    String joltAppAddress = "//127.0.0.1:8080";

    @Value("${jolt.app.password}")
    String joltAppPassword = "appPassword";

    @Value("${jolt.user.name}")
    String joltUserName = "myname";

    @Value("${jolt.user.pass}")
    String joltUserPass = "mysecret";

    @Value("${jolt.user.role")
    String joltUserRole = "myapp";

    @RequestMapping("toupper/{message}")
    public JoltResponse toUpper(@PathVariable String message) {

        if(StringUtils.isEmpty(message)) {
            message = joltDefaultMessage;
        }

        JoltSession session = createSession();
        String result;

        try {
            JoltRemoteService toupper = new JoltRemoteService("TOUPPER", session);
            toupper.setString("STRING", message);
            toupper.call(null);
            result = toupper.getStringDef("STRING", null);
            if (!StringUtils.isEmpty(result)) {
                System.out.println(result);
            }
        } finally {
            session.endSession();
        }

        return JoltResponse.simple(result);
    }

    private JoltSession createSession() {
        JoltSessionAttributes attributes = new JoltSessionAttributes();
        attributes.setString(JoltSessionAttributes.APPADDRESS, joltAppAddress);
        attributes.setInt(JoltSessionAttributes.IDLETIMEOUT, joltIdleTimeout);
        return createSession(attributes);
    }

    private JoltSession createSession(JoltSessionAttributes attributes) {
        // NoAuth is default
        JoltSession session = new JoltSession(attributes, null, null, null, null);

        switch (attributes.checkAuthenticationLevel()) {
            case JoltSessionAttributes.APPASSWORD:
                session = new JoltSession(attributes,
                        null, null, null, joltAppPassword);
                break;
            case JoltSessionAttributes.USRPASSWORD:
                session = new JoltSession(attributes,
                        joltUserName, joltUserRole, joltUserPass, joltAppPassword);
                break;
        }

        return session;
    }

}
