package ch.mobro.eventapp.config;

public class PathConstants {
    public static final String EVENT = "/api/v1/event";
    public static final String ID = "{id}";
    public static final String USERNAME = "{username}";
    public static final String EVENT_CATEGORY = "/category";
    public static final String CATEGORY_ID = "/{" + Variables.CATEGORY_ID + "}";
    public static final String EMAIL_ID = "/{" + Variables.EMAIL_ID + "}";
    public static final String USER_ID = "/{" + Variables.USER_ID + "}";
    public static final String EVENT_REGISTRATION = "/registration";
    public static final String EVENT_INVITATION = "/invitation";
    public static final String USER = "/api/v1/user";
    public static final String USER_USER_ID = USER + "/{id}";
    public static final String USER_CHANGE_PASSWORD = "/change-password";
    public static final String ROLE = "/api/v1/role";
    public static final String ROLE_ROLE_ID = ROLE + "/{id}";
    public static final String AUTH = "/auth";
    public static final String AUTH_LOGIN = "/login";
}
