package cn.jiegeng.luobin.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CpMapper {
    int addCp(@Param("key") String key, @Param("value") String value, @Param("number") long number);
}
