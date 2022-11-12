package cn.jiegeng.luobin.domain.vo;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode
public class UserStore {
    int id;
    String commandKey;
    Date date;
    String creatUser;
}
