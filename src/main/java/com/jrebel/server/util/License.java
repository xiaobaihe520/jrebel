package com.jrebel.server.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author wangyi
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class License {
    private String serverVersion;
    private String serverProtocolVersion;
    private String serverGuid;
    private String groupType;
    private Integer id;
    private Integer licenseType;
    private Boolean evaluationLicense;
    private String signature;
    private String serverRandomness;
    private String seatPoolType;
    private String statusCode;
    private Boolean offline;
    private Long validFrom;
    private Long validUntil;
    private String company;
    private String orderId;
    private List<Integer> zeroIds;
    private Long licenseValidFrom;
    private Long licenseValidUntil;
}
