/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.plugins;

/**
 * The Interface PluginConfig.
 */
public class PluginConfig {

	/** The pulgin id. */
	private String pulginId;

	/** The pulgin version. */
	private String pulginVersion;

	/** The status. */
	private PluginStatusEnum status;

	/** The description. */
	private String description;

	/** The is required. */
	private boolean isRequired;

	/** The spring configuration. */
	private String springConfiguration;

	/** The provider. */
	private String provider;

	/** The i18n. */
	private String i18n;

	/**
	 * Gets the pulgin id.
	 * 
	 * @return the pulgin id
	 */
	public String getPulginId() {
		return pulginId;
	}

	/**
	 * Sets the pulgin id.
	 * 
	 * @param pulginId
	 *            the new pulgin id
	 */
	public void setPulginId(String pulginId) {
		this.pulginId = pulginId;
	}

	/**
	 * Gets the pulgin version.
	 * 
	 * @return the pulgin version
	 */
	public String getPulginVersion() {
		return pulginVersion;
	}

	/**
	 * Sets the pulgin version.
	 * 
	 * @param pulginVersion
	 *            the new pulgin version
	 */
	public void setPulginVersion(String pulginVersion) {
		this.pulginVersion = pulginVersion;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public PluginStatusEnum getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(PluginStatusEnum status) {
		this.status = status;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Checks if is required.
	 * 
	 * @return true, if is required
	 */
	public boolean isRequired() {
		return isRequired;
	}

	/**
	 * Sets the required.
	 * 
	 * @param isRequired
	 *            the new required
	 */
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	/**
	 * Gets the spring configuration.
	 * 
	 * @return the spring configuration
	 */
	public String getSpringConfiguration() {
		return springConfiguration;
	}

	/**
	 * Sets the spring configuration.
	 * 
	 * @param springConfiguration
	 *            the new spring configuration
	 */
	public void setSpringConfiguration(String springConfiguration) {
		this.springConfiguration = springConfiguration;
	}

	/**
	 * Gets the i18n.
	 * 
	 * @return the i18n
	 */
	public String getI18n() {
		return i18n;
	}

	/**
	 * Sets the i18n.
	 * 
	 * @param i18n
	 *            the new i18n
	 */
	public void setI18n(String i18n) {
		this.i18n = i18n;
	}

	/**
	 * Gets the provider.
	 * 
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * Sets the provider.
	 * 
	 * @param provider
	 *            the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

}
