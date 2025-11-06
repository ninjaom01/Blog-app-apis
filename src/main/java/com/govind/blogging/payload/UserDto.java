package com.govind.blogging.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	


	private int id;
	
	@NotEmpty
	@Size(min = 4, message = "Name should be more than 4 char !!")
	private String name;
	
	@Email(message = "use valid Email Id !!")
	private String email;
	
	@NotEmpty
	@Size(min=4  ,max = 10 ,message = "Passwaord should be of min 4 and max 10 char.")
	private String password;

	
	@NotEmpty
	private String about;
	

}
