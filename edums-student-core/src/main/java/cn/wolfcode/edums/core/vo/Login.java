package cn.wolfcode.edums.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Login implements Serializable {

    private String account;
    private String password;
    private Boolean rememberMe = false;
}
