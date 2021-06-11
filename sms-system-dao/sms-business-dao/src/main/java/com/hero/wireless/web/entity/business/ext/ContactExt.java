package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.Contact;
import com.hero.wireless.web.entity.business.ContactGroup;

public class ContactExt extends Contact {
	private ContactGroup contactGroup;

	public ContactGroup getContactGroup() {
		return contactGroup;
	}

	public void setContactGroup(ContactGroup contactGroup) {
		this.contactGroup = contactGroup;
	}
}
