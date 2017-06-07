package com.aaric.alimobile.consts;

/**
 * Application constants.
 * 
 * @author Aaric
 * 
 */
public final class Ali {

	/**
	 * Local host.
	 */
	public static final String DEFAULT_LOCALHOST = "10.0.2.2";

	/**
	 * Local host server URL
	 */
	public static final String DEFAULT_URL_LOCALHOST_SERVER_ROOT = "http://"
			+ DEFAULT_LOCALHOST + ":8280";

	/**
	 * Update application base URL.
	 */
	// public static final String DEFAULT_URL_RELEASE_UPDATE =
	// "https://github.com/github-aaric/ali-client/";
	public static final String DEFAULT_URL_RELEASE_UPDATE = DEFAULT_URL_LOCALHOST_SERVER_ROOT
			+ "/release/";

	/**
	 * Operate SSO login URL.
	 */
	public static final String DEFAULT_URL_OPERATE_SSO = "";

	/**
	 * Operate WWW load base URL.
	 */
	public static final String DEFAULT_URL_OPERATE_WWW = "";

	/**
	 * Operate LOTTO load base URL.
	 */
	public static final String DEFAULT_URL_OPERATE_LOTTO = "";

	/**
	 * The private constructor.
	 */
	private Ali() {
		super();
	}

}
