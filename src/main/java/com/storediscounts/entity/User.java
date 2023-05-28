package com.storediscounts.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
	
	@Id
	@SequenceGenerator(name = "user_id_sequence",
    sequenceName = "user_id_sequence",initialValue = 1, allocationSize=1
     )
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "user_id_sequence")
	private Long id;
	@NotBlank(message = "User Name is required field !!")
	private String userName;
	private String password;
	@NotBlank(message = "Address is required field !!")
	private String address;
	private Long contactNumber;
	@NotNull
	@NotBlank(message = "Role is required field !!")
	private String Role;
	private Date createDate;
}
