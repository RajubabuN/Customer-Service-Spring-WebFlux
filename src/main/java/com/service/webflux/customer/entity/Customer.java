package com.service.webflux.customer.entity;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class Customer.
 */
@Table("CUSTOMER")
public class Customer implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@JsonProperty("Id")
	@Column(value = "ID")
	private Long id;

	/** The name. */
	@JsonProperty("Name")
	@Column(value = "NAME")
	private String name;

	/** The email. */
	@JsonProperty("Email")
	@Column(value = "EMAIL")
	private String email;

	/** The contact no. */
	@JsonProperty("Contact No")
	@Column(value = "CONTACT_NO")
	private String contactNo;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */

	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Gets the contact no.
	 *
	 * @return the contact no
	 */
	public String getContactNo() {
		return contactNo;
	}

	/**
	 * Sets the contact no.
	 *
	 * @param contactNo the new contact no
	 */
	public void setContactNo(final String contactNo) {
		this.contactNo = contactNo;
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Customer other = (Customer) obj;
		return Objects.equals(id, other.id);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {

		final StringBuilder sb = new StringBuilder();
		sb.append("Customer [id=");
		sb.append(id);
		sb.append(", name=");
		sb.append(name);
		sb.append(", email=");
		sb.append(contactNo);
		sb.append(", contactNo=");
		sb.append(id);
		sb.append("]");
		return sb.toString();
	}

}
