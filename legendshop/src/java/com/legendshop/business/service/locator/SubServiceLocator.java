package com.legendshop.business.service.locator;

import com.legendshop.core.exception.ErrorCodes;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.model.entity.Sub;
import com.legendshop.spi.locator.GenericServiceLocator;
import com.legendshop.spi.service.timer.SubService;

/**
 * The Class SubServiceLocator.
 */
public class SubServiceLocator extends GenericServiceLocator<SubService> {

	/** The sub service. */
	private SubService subService;

	/**
	 * Gets the sub.
	 * 
	 * @param subId
	 *            the sub id
	 * @return the sub
	 */
	public Sub getSub(Long subId) {
		Sub sub = subService.getSubById(subId);
		if (sub == null) {
			throw new NotFoundException("Sub can not be empty",
					ErrorCodes.ENTITY_NO_FOUND);
		}
		return sub;
	}

	/**
	 * Gets the sub service.
	 * 
	 * @return the sub service
	 */
	public SubService getSubService() {
		return subService;
	}

	/**
	 * Sets the sub service.
	 * 
	 * @param subService
	 *            the new sub service
	 */
	public void setSubService(SubService subService) {
		this.subService = subService;
	}

}
