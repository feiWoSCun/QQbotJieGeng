package cn.mikulink.luobin.command;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
@Data
@Component
public class HelloCommand {
    Set<String> command;
}
