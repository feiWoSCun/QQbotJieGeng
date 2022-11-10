package cn.mikulink.luobin.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface CommandMapper {
    Set<String> getCommand();
}
