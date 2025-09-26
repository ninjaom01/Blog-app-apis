package com.govind.blogging.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer CategoryId;
	
	@NotBlank
	@Size(min = 4,message = "Minimum size should be 4")
	private String CategoryTitle;
	
	@NotBlank
	@Size(min = 14,message = "Minimum size should be 14")
	private String CategoryDescription;
	
	

}
