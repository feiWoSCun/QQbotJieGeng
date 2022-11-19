package cn.jiegeng.luobin.mapper;

import cn.jiegeng.luobin.domain.vo.Command;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface CommandMapper {
    Set<String> getCommand();
    int addCommand(Command command);

    int getId(@Param(("str")) String str);
}
