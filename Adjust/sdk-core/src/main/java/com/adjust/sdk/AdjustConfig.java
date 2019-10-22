package com.adjust.sdk;

import android.content.Context;

import java.util.List;

/**
 * Created by pfms on 06/11/14.
 */
public class AdjustConfig {
    String basePath;
    String gdprPath;
    String disableThirdPartySharingPath;
    Context context;
    String appToken;
    String environment;
    String processName;
    String sdkPrefix;
    boolean eventBufferingEnabled;
    String defaultTracker;
    OnAttributionChangedListener onAttributionChangedListener;
    Boolean deviceKnown;
    Class deepLinkComponent;
    OnEventTrackingSucceededListener onEventTrackingSucceededListener;
    OnEventTrackingFailedListener onEventTrackingFailedListener;
    OnSessionTrackingSucceededListener onSessionTrackingSucceededListener;
    OnSessionTrackingFailedListener onSessionTrackingFailedListener;
    OnDeeplinkResponseListener onDeeplinkResponseListener;
    boolean sendInBackground;
    Double delayStart;
    List<IRunActivityHandler> preLaunchActionsArray;
    ILogger logger;
    String userAgent;
    String pushToken;
    Boolean startEnabled;
    boolean startOffline;
    String secretId;
    String appSecret;

    public static final String ENVIRONMENT_SANDBOX = "sandbox";
    public static final String ENVIRONMENT_PRODUCTION = "production";

    public static final String AD_REVENUE_MOPUB = "mopub";
    public static final String AD_REVENUE_ADMOB = "admob";
    public static final String AD_REVENUE_FB_NATIVE_AD = "facebook_native_ad";
    public static final String AD_REVENUE_IRONSOURCE = "ironsource";
    public static final String AD_REVENUE_FYBER = "fyber";
    public static final String AD_REVENUE_AERSERV = "aerserv";
    public static final String AD_REVENUE_APPODEAL = "appodeal";
    public static final String AD_REVENUE_ADINCUBE = "adincube";
    public static final String AD_REVENUE_FUSE_POWERED = "fusepowered";
    public static final String AD_REVENUE_ADDAPTR = "addapptr";
    public static final String AD_REVENUE_MILLENNIAL_MEDITATION = "millennial_mediation";
    public static final String AD_REVENUE_FLURRY = "flurry";
    public static final String AD_REVENUE_ADMOST = "admost";
    public static final String AD_REVENUE_DELTADNA = "deltadna";
    public static final String AD_REVENUE_UPSIGHT = "upsight";
    public static final String AD_REVENUE_UNITYADS = "unityads";
    public static final String AD_REVENUE_ADTOAPP = "adtoapp";
    public static final String AD_REVENUE_TAPDAQ = "tapdaq";

    public AdjustConfig(Context context, String appToken, String environment) {
        init(context, appToken, environment, false);
    }

    public AdjustConfig(Context context, String appToken, String environment, boolean allowSuppressLogLevel) {
        init(context, appToken, environment, allowSuppressLogLevel);
    }

    // Beware that some of these values might be null. isValid() would check their validity later.
    private void init(Context context, String appToken, String environment, boolean allowSuppressLogLevel) {
        logger = AdjustFactory.getLogger();
        // default values
        if (allowSuppressLogLevel && AdjustConfig.ENVIRONMENT_PRODUCTION.equals(environment)) {
            setLogLevel(LogLevel.SUPRESS, environment);
        } else {
            setLogLevel(LogLevel.INFO, environment);
        }

        // Always use application context
        if (context != null) {
            context = context.getApplicationContext();
        }

        this.context = context;
        this.appToken = appToken;
        this.environment = environment;

        // default values
        this.eventBufferingEnabled = false;
        this.sendInBackground = false;
    }

    public void setEventBufferingEnabled(Boolean eventBufferingEnabled) {
        if (eventBufferingEnabled == null) {
            this.eventBufferingEnabled = false;
            return;
        }
        this.eventBufferingEnabled = eventBufferingEnabled;
    }

    public void setSendInBackground(boolean sendInBackground) {
        this.sendInBackground = sendInBackground;
    }

    public void setLogLevel(LogLevel logLevel) {
        setLogLevel(logLevel, environment);
    }

    public void setSdkPrefix(String sdkPrefix) {
        this.sdkPrefix = sdkPrefix;
    }

    public void setProcessName(String processName) { this.processName = processName; }

    public void setDefaultTracker(String defaultTracker) {
        this.defaultTracker = defaultTracker;
    }

    public void setOnAttributionChangedListener(OnAttributionChangedListener onAttributionChangedListener) {
        this.onAttributionChangedListener = onAttributionChangedListener;
    }

    public void setDeviceKnown(boolean deviceKnown) {
        this.deviceKnown = deviceKnown;
    }

    public void setDeepLinkComponent(Class deepLinkComponent) {
        this.deepLinkComponent = deepLinkComponent;
    }

    public void setOnEventTrackingSucceededListener(OnEventTrackingSucceededListener onEventTrackingSucceededListener) {
        this.onEventTrackingSucceededListener = onEventTrackingSucceededListener;
    }

    public void setOnEventTrackingFailedListener(OnEventTrackingFailedListener onEventTrackingFailedListener) {
        this.onEventTrackingFailedListener = onEventTrackingFailedListener;
    }

    public void setOnSessionTrackingSucceededListener(OnSessionTrackingSucceededListener onSessionTrackingSucceededListener) {
        this.onSessionTrackingSucceededListener = onSessionTrackingSucceededListener;
    }

    public void setOnSessionTrackingFailedListener(OnSessionTrackingFailedListener onSessionTrackingFailedListener) {
        this.onSessionTrackingFailedListener = onSessionTrackingFailedListener;
    }

    public void setOnDeeplinkResponseListener(OnDeeplinkResponseListener onDeeplinkResponseListener) {
        this.onDeeplinkResponseListener = onDeeplinkResponseListener;
    }

    public void setDelayStart(double delayStart) {
        this.delayStart = delayStart;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setAppSecret(long secretId, long info1, long info2, long info3, long info4) {
        this.secretId = Util.formatString("%d", secretId);
        this.appSecret = Util.formatString("%d%d%d%d", info1, info2, info3, info4);
    }

    @Deprecated
    public void setReadMobileEquipmentIdentity(boolean readMobileEquipmentIdentity) {
        logger.warn("This method has been deprecated and shouldn't be used anymore");
    }

    public boolean isValid() {
        if (!checkAppToken(appToken)) return false;
        if (!checkEnvironment(environment)) return false;
        if (!checkContext(context)) return false;

        return true;
    }

    private void setLogLevel(LogLevel logLevel, String environment) {
        logger.setLogLevel(logLevel, AdjustConfig.ENVIRONMENT_PRODUCTION.equals(environment));
    }

    private boolean checkContext(Context context) {
        if (context == null) {
            logger.error("Missing context");
            return false;
        }

        if (!Util.checkPermission(context, android.Manifest.permission.INTERNET)) {
            logger.error("Missing permission: INTERNET");
            return false;
        }

        return true;
    }

    private boolean checkAppToken(String appToken) {
        if (appToken == null) {
            logger.error("Missing App Token");
            return false;
        }

        if (appToken.length() != 12) {
            logger.error("Malformed App Token '%s'", appToken);
            return false;
        }

        return true;
    }

    private boolean checkEnvironment(String environment) {
        if (environment == null) {
            logger.error("Missing environment");
            return false;
        }

        if (environment.equals(AdjustConfig.ENVIRONMENT_SANDBOX)) {
            logger.warnInProduction("SANDBOX: Adjust is running in Sandbox mode. " +
                    "Use this setting for testing. " +
                    "Don't forget to set the environment to `production` before publishing!");
            return true;
        }
        if (environment.equals(AdjustConfig.ENVIRONMENT_PRODUCTION)) {
            logger.warnInProduction(
                    "PRODUCTION: Adjust is running in Production mode. " +
                            "Use this setting only for the build that you want to publish. " +
                            "Set the environment to `sandbox` if you want to test your app!");
            return true;
        }

        logger.error("Unknown environment '%s'", environment);
        return false;
    }
}