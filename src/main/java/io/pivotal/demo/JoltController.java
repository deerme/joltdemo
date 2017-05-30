package io.pivotal.demo;

import bea.jolt.JoltRemoteService;
import bea.jolt.JoltSession;
import bea.jolt.JoltSessionAttributes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("jolt")
public class JoltController {

    @Value("${jolt.idle.timeout}")
    Integer joltIdleTimeout = 300;

    @Value("${jolt.default.message}")
    String joltDefaultMessage = "Howdy Jolt!";

    @Value("${jolt.app.address}")
    String joltAppAddress = "//127.0.0.1:8080";

    @Value("${jolt.app.pass}")
    String joltAppPassword = null;

    @Value("${jolt.user.name}")
    String joltUserName = null;

    @Value("${jolt.user.pass}")
    String joltUserPass = null;

    @Value("${jolt.user.role")
    String joltUserRole = "myapp";

    @RequestMapping("howdy/{message}")
    public Map<String, String> howdy(@PathVariable String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Howdy " + message);
        return response;
    }

    @RequestMapping("ip")
    public Map<String, String> getIp() {
        Map<String, String> response = new HashMap<>();
        response.put("ip", AppUtils.getIp().toString());
        return response;
    }

    @RequestMapping("datetime")
    public Map<String, String> getDateTime() {
        Map<String, String> response = new HashMap<>();
        response.put("datetime", AppUtils.now());
        return response;
    }

    @RequestMapping("vcap/application")
    public Map<String, Object> getVcapApplication() {
        return CfUtils.getVcapApplication();
    }

    @RequestMapping("vcap/services")
    public Map<String, Object> getVcapServices() {
        return CfUtils.getVcapServices();
    }

    @RequestMapping("fake/{message}")
    public Map<String, String> toUpperFake(@PathVariable String message) {
        Map<String, String> response = new HashMap<>();
        response.put("result", message.toUpperCase());
        return response;
    }

    @RequestMapping("toupper/{message}")
    public Map<String, String> toUpper(@PathVariable String message) {

        if(StringUtils.isEmpty(message)) {
            message = joltDefaultMessage;
        }

        JoltSession session = createSession();

        try {
            JoltRemoteService toupper = new JoltRemoteService("TOUPPER", session);
            toupper.setString("STRING", message);
            toupper.call(null);
            String result = toupper.getStringDef("STRING", null);
            if (!StringUtils.isEmpty(result)) {
                System.out.println(result);
                Map<String, String> response = new HashMap<>();
                response.put("result", result);
                return response;
            }
        } finally {
            session.endSession();
        }

        return Collections.emptyMap();
    }

    private JoltSession createSession() {
        JoltSessionAttributes attributes = new JoltSessionAttributes();
        attributes.setString(JoltSessionAttributes.APPADDRESS, joltAppAddress);
        attributes.setInt(JoltSessionAttributes.IDLETIMEOUT, joltIdleTimeout);
        return createSession(attributes);
    }

    private JoltSession createSession(JoltSessionAttributes attributes) {
        JoltSession session = new JoltSession(attributes,
            joltUserName, joltUserRole, joltUserPass, joltAppPassword);

        return session;
    }


}
