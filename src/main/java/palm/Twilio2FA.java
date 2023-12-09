package palm;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.entity.Challenge;
import com.twilio.rest.verify.v2.service.entity.Factor;
import com.twilio.rest.verify.v2.service.entity.NewFactor;

public class Twilio2FA {
    public static final String VERIFICATION_SERVICE_SID = System.getenv("VERIFICATION_SERVICE_SID");
    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

    private Twilio2FA() {
    } // Utility class

    /**
     * Creates new TOTP factor based on user's UUID
     *
     * @param uuid         Unique user ID
     * @param friendlyName Normal username
     * @return Response from API
     */
    protected static NewFactor createNewTOTPFactor(String uuid, String friendlyName) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        return NewFactor.creator(VERIFICATION_SERVICE_SID, uuid, friendlyName, NewFactor.FactorTypes.TOTP).create();
    }

    /**
     * Verifies TOTP code based on given parameters (ONLY FOR WHEN CREATING ACCOUNT)
     *
     * @param uuid        Unique user ID
     * @param responseSid SID from response when TOTP factor was created
     * @param code        verification code being verified
     * @return True if successful
     */
    protected static boolean verifyTOTPFactor(String uuid, String responseSid, int code) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Factor factor = Factor.updater(VERIFICATION_SERVICE_SID, uuid, responseSid)
                .setAuthPayload(Integer.toString(code)).update();
        return factor.getStatus() == Factor.FactorStatuses.VERIFIED;
    }

    /**
     * Validates existing token
     *
     * @param uuid        Unique user ID
     * @param responseSid SID from response when TOTP factor was created
     * @param code        verification code being verified
     * @return True if valid
     */
    protected static boolean validateToken(String uuid, String responseSid, int code) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Challenge challenge = Challenge.creator(VERIFICATION_SERVICE_SID, uuid, responseSid)
                .setAuthPayload(Integer.toString(code)).create();
        return challenge.getStatus() == Challenge.ChallengeStatuses.APPROVED;
    }
}
