package cn.wolfcode.edums.core.support.http;

import cn.wolfcode.edums.core.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Leon
 * @date 2019-12-25
 */
@Getter
@Setter
@NoArgsConstructor
public class SessionUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public SessionUser(Long id, String username, String email, String realName, String sex,
                       String duty, String tel, LocalDate birthDay, Long deptId, String deptName, String qq) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.realName = realName;
        this.sex = sex;
        this.duty = duty;
        this.tel = tel;
        this.birthDay = birthDay;
        this.deptId = deptId;
        this.deptName = deptName;
        this.qq = qq;
    }

    private Long id;
    private String username;
    private String email;
    private String realName;
    private String sex;
    private String duty;
    private String tel;
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PARTTERN)
    private LocalDate birthDay;
    private Long deptId;
    private String deptName;
    private String qq;
    private Set<String> roles = new HashSet<>();
    private Set<String> permissions = new HashSet<>();
}
