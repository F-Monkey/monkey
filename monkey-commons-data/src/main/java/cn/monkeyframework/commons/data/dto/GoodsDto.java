package cn.monkeyframework.commons.data.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class GoodsDto implements Serializable {
    @NotBlank(message = "${name.notBlank}")
    private String name;
    @Max(value = 100, message = "${price.max.require}")
    @Min(value = 0, message = "${price.min.require}")
    private Double price;

    private String[] imgSrc;

    @NotBlank(message = "${type.notBlank}")
    private String type;

    @NotBlank(message = "${unit.notBlank}")
    private String unit;
    @NotBlank(message = "${currency.notBlank}")
    private String currency;

    @NotBlank(message = "${desc.notBlank}")
    private String desc;
}
