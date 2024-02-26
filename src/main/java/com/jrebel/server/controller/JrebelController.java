package com.jrebel.server.controller;

import com.jrebel.server.util.License;
import com.jrebel.server.util.SignatureUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.UUID;

/**
 * Jrebel
 *
 * @author wy
 */
@RestController
public class JrebelController {

    @RequestMapping("/jrebel/leases")
    License leases(@RequestParam String randomness, @RequestParam String username, @RequestParam String guid,
                   @RequestParam(required = false) Boolean offline, @RequestParam(required = false) Long clientTime) {
        if (offline == null) {
            offline = false;
        }
        License license = buildLicense();
        license.setOffline(offline);
        license.setCompany(username);
        if (offline) {
            license.setValidFrom(clientTime);
            license.setValidUntil(clientTime + 180L * 24 * 60 * 60 * 1000);
        }
        createSignature(randomness, guid, license);
        return license;
    }

    @RequestMapping("/jrebel/leases/1")
    License leases1(@RequestParam String username) {
        License license = buildLicense();
        license.setCompany(username);
        return license;
    }

    @RequestMapping("/agent/leases")
    License leasesx(@RequestParam String randomness, @RequestParam String username, @RequestParam String guid,
                    @RequestParam(required = false) Boolean offline, @RequestParam(required = false) Long clientTime) {
        return leases(randomness, username, guid, offline, clientTime);
    }

    @RequestMapping("/agent/leases/1")
    License leasesx1(@RequestParam String username) {
        return leases1(username);
    }

    private License buildLicense() {
        License license = new License();
        license.setServerVersion("3.2.4");
        license.setServerProtocolVersion("1.1");
        license.setServerGuid(UUID.randomUUID().toString());
        license.setGroupType("managed");
        license.setId(1);
        license.setLicenseType(1);
        license.setEvaluationLicense(false);
        license.setServerRandomness(getNonce());
        license.setSeatPoolType("standalone");
        license.setStatusCode("SUCCESS");
        license.setOrderId("");
        license.setZeroIds(Collections.emptyList());
        license.setLicenseValidFrom(1490544001000L);
        license.setLicenseValidUntil(4102329600000L);
        return license;
    }

    private void createSignature(String clientRandomness, String guid, License license) {
        Boolean offline = license.getOffline();
        StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append(clientRandomness).append(";")
                .append(license.getServerRandomness()).append(";")
                .append(guid).append(";")
                .append(offline);
        if (offline) {
            stringBuilder.append(";").append(license.getValidFrom())
                    .append(";").append(license.getValidUntil());
        }
        license.setSignature(SignatureUtil.createSignature(stringBuilder.toString()));
    }

    private String getNonce() {
        int length = 12;
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length - 1; ++i) {
            char c = (char) (Math.random() * 62);
            if (c < 10) {
                c += 48;
            } else if (c < 36) {
                c += 55;
            } else {
                c += 61;
            }
            stringBuilder.append(c);
        }
        stringBuilder.append("=");
        return stringBuilder.toString();
    }
}
