package cn.jiegeng.luobin.domain.vo;

import lombok.*;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode
public class UserPri {
    int id;
    String number;
    int privilege;
}
