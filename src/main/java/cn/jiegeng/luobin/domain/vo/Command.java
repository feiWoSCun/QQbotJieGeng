package cn.jiegeng.luobin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:feiWoSCun
 * @Email:2825097536@qq.com
 * @Creat:2022/11/16 17:46
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Command {
    String key;
    String date;
    String user;
    int id;
}
