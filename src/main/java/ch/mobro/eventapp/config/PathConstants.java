package ch.mobro.eventapp.config;

public class PathConstants {
    public static final String EVENT = "/api/v1/event";
    public static final String ID = "{id}";
    public static final String EVENT_CATEGORY = "/category";
    public static final String CATEGORY_ID = "/{" + Variables.CATEGORY_ID + "}";
    public static final String EVENT_REGISTRATION = "/registration";
    public static final String EVENT_INVITATION = "/invitation";
    public static final String USER = "/api/v1/user";
    public static final String USER_USER_ID = USER + "/{id}";
    public static final String ROLE = "/api/v1/role";
    public static final String ROLE_ROLE_ID = ROLE + "/{id}";
    public static final String USER_CREATE_TWO_FA_SECRET = "/2fa/secret/create";
    public static final String USER_VERIFY_TWO_FA_SECRET = "/2fa/secret/verify";
    public static final String USER_RESET_TWO_FA_SECRET = "/2fa/secret/reset";
    public static final String USER_VERIFY_TWO_FA_OTP = "/2fa/otp/verify";
    public static final String USER_RESET_TWO_FA_CODES = "/2fa/code/reset";
    public static final String USER_TWO_FA_CODES = "/2fa/codes";
    public static final String AUTH = "/auth";
    public static final String AUTH_LOGIN = "/authenticate";
}
