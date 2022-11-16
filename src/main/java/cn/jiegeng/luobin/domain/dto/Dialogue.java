package cn.jiegeng.luobin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:feiWoSCun
 * @Email:2825097536@qq.com
 * @Creat:2022/11/16 17:13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dialogue {
    String nr;
    int dialogueTime;
    int answerId;
}
