package cn.mikulink.luobin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialogueMapper {
   List<String> getDialogues(@Param("nowTime") int nowTime);

}
