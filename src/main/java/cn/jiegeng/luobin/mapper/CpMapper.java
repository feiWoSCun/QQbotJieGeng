package cn.jiegeng.luobin.mapper;

import org.apache.ibatis.annotations.Param;

public interface CpMapper {
    boolean addCp(@Param("key") String key, @Param("value") String value);
}
