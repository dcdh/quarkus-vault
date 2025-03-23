package io.quarkus.it.vault;

import static org.hamcrest.Matchers.startsWith;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.vault.test.VaultTestLifecycleManager;
import io.restassured.RestAssured;

@QuarkusTest
@DisabledOnOs(OS.WINDOWS) // https://github.com/quarkusio/quarkus/issues/3796
@QuarkusTestResource(VaultTestLifecycleManager.class)
public class VaultTest {

    //    @Test
    //    public void test() throws Exception {
    //        RestAssured.when().get("/vault").then().body(is("OK"));
    //    }
    //
    //    @Test
    //    public void testHealthCheck() {
    //        RestAssured.when().get("/q/health/ready").then()
    //                .assertThat()
    //                .body("status", equalTo("UP"))
    //                .body("checks.size()", is(2));
    //    }
    //
    //    @Test
    //    public void testDecryptUnknownTransitKey() {
    //        RestAssured.when().post("/vault/decryptUnknownTransitKey")
    //                .then()
    //                .log().all()
    //                .statusCode(500)
    //                .body(startsWith("Something wrong happened"));
    //    }

    @Test
    public void testEncryptUnknownTransitKey() {
        RestAssured.when().post("/vault/encryptUnknownTransitKey")
                .then()
                .log().all()
                .statusCode(500)
                .body(startsWith("Something wrong happened"));
        // jvm
        //Something wrong happened VAULT [SECRETS (transit)] Encrypt Batch' at path 'http://localhost:8200/v1/transit/encrypt/unknown-transit-key' with status 403
        //errors:
        //1 error occurred:
        //     * permission denied
        //
        // - VaultClientException

        // native
        //Something wrong happened VAULT [SECRETS (transit)] Encrypt Batch' at path 'http://localhost:8200/v1/transit/encrypt/unknown-transit-key' with status 403 - VaultClientException
    }
    // il me faut une policy ... pour interdire la creation ...
    // vault policy write deny_transit_create - <<< 'path "transit/keys/*" { capabilities = ["read", "update", "delete", "list"] }'
}
