package cn.wolfcode.edums.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Leon
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDomain implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
}
