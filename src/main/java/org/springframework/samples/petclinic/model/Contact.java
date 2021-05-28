package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="contact")
public class Contact  extends BaseEntity{

	@Column(name = "name")
	@NotBlank
	private String name;
	
	@Column(name = "email")
	@NotBlank
	private String email;
	
	@Column(name = "phone")
	@NotBlank
	private String phone;

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Contact(@NotBlank String name, @NotBlank String email, @NotBlank String phone) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Contact [name=" + name + ", email=" + email + ", phone=" + phone + "]";
	}
	
}
