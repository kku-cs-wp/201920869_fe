package kr.ac.kku.cs.wp.wsd.user.dto;


import java.util.Date;
import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * UserDTO
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
public class UserDTO {
	private String id;
	
	
	@Pattern(regexp = "^[\\p{L}\\p{M}\\p{N} ]*$", message="이름에는 특수문자를 사용할 수 없습니다.")
	private String name;
	
	@NotBlank(message="유효한 이메일 주소를 입력해주세요.")
	@Email(message = "유효한 이메일 주소를 입력해주세요.")
	private String email;
	
	@NotBlank (message = "비밀번호는 특수문자, 영문자, 숫자를 포함하여 8자리 이상이어야 합니다.")
	@Pattern(
	        regexp = "^$|(?=.*[A-Za-z])(?=.*\\d)(?=.*[~`!@#$%^&*()_\\-+=\\[\\]{}|\\\\;:'\",.<>/?]).{8,}$",
	        message = "비밀번호는 특수문자, 영문자, 숫자를 포함하여 8자리 이상이어야 합니다."
	    )
	private String password;
	
	 @Pattern(regexp = "재직중|퇴직", message="사용자 상태에서는 \"재직중|퇴직\"중에서 선택해야 합니다.")
	 @NotBlank(message="사용자 상태에서는 \"재직중|퇴직\"중에서 선택해야 합니다.")
	private String status;
	private String photo;
	private Date createdAt;
	private Date updatedAt;
	private List<UserRoleDTO> userRoles;

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<UserRoleDTO> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRoleDTO> userRoles) {
		this.userRoles = userRoles;
	}
}