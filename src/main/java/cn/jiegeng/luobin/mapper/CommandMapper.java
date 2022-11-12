package cn.jiegeng.luobin.mapper;

import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface CommandMapper {
    Set<String> getCommand();
}
