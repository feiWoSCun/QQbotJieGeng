package cn.jiegeng.luobin.command;

import cn.jiegeng.luobin.domain.vo.UserPri;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Data
@Component
public class HelloCommand {
    //用户指令
    Set<String> command;
    //用户权限
    List<UserPri> userPris;
}
