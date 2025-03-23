package io.quarkus.it.vault;

import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import io.quarkus.vault.VaultSystemBackendEngine;
import io.quarkus.vault.VaultTransitSecretEngine;

@Path("/vault")
public class VaultTestResource {

    @Inject
    VaultTestService vaultTestService;

    @Inject
    VaultTransitSecretEngine transit;

    @Inject
    VaultSystemBackendEngine vaultSystemBackendEngine;

    @PostConstruct
    void init() {

    }

    @GET
    @Produces(TEXT_PLAIN)
    public String test() {
        return vaultTestService.test();
    }

    @POST
    @Path("decryptUnknownTransitKey")
    public void decryptUnknownTransitKey() {
        transit.decrypt("unknown-transit-key", "Hello World");
    }

    @POST
    @Path("encryptUnknownTransitKey")
    public String encryptUnknownTransitKey() {
        //        String rules = """
        //                path "transit/*" {
        //                    capabilities = ["read", "delete", "list"]
        //                }
        //                """;
        //        String policyName = "deny_transit_create";
        //        vaultSystemBackendEngine.createUpdatePolicy(policyName, rules);
        //        vaultSystemBackendEngine.getPolicies();
        //        transit.listKeys().stream().filter("deny_transit-create"::equals)
        //                        .findFirst().ifPresent(transitKey -> {
        //                    transit.deleteKey(transitKey);// putain pk ???
        //                });
        //        transit.listKeys().forEach(System.out::println);
        final String encrypted = transit.encrypt("unknown-transit-key", "Hello World");
        transit.listKeys().forEach(System.out::println);
        return encrypted;
    }
}
