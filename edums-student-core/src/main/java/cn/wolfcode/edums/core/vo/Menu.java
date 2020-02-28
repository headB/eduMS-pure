package cn.wolfcode.edums.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author Leon
 * @date 2019-12-24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long parentId;
    private String title;
    private String name;
    private String jump;
    private String icon;
    private Integer sequence;
    private List<Menu> list;
}
